import javafx.scene.media.MediaPlayer;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.function.Consumer;

public class Server{
    private static MediaPlayer inPlayer;
    int count = 1;
    int serverPort;
    ArrayList<ClientThread> clients = new ArrayList<ClientThread>();

    //Catches all of the information that is being set between the server and the clients
    MorraInfo gameData = new MorraInfo();
    MorraInfo tempData = new MorraInfo();

    TheServer server;
    private Consumer<Serializable> callback;


    Server(int port, Consumer<Serializable> call){
        serverPort = port;
        callback = call;
        server = new TheServer();
        server.start();
        callback.accept("Port configured to " + serverPort);
    }






    //Sets up the server thread which is used to connect the client threads and allow for communication between server and client
    public class TheServer extends Thread{

        public void run() {

            try(ServerSocket mysocket = new ServerSocket(serverPort);){
                callback.accept("Server is waiting for a client!");


                while(true) {

                    ClientThread c = new ClientThread(mysocket.accept(), count);
                    callback.accept("Client has connected to server: " + "Client #" + count);
                    clients.add(c);
                    c.start();

                    count++;

                }
            }//end of try
            catch(Exception e) {
                callback.accept("Server socket did not launch");
            }
        }//end of while
    }


    class ClientThread extends Thread{


        Socket connection;

        //Sets up the output and input stream between the client and the server
        ObjectInputStream in;
        ObjectOutputStream out;

        //Controls game progression
        int msgCounter = 1;
        int count;
        int roundWinner = 0;

        //Used to trap game progression
        boolean trapState = false;

        ClientThread(Socket s, int count){
            this.connection = s;
            this.count = count;
        }

        //Will update the clients with a message from the server followed by the MorraInfo class.
        public void updateClients(String message) {
            for(int i = 0; i < clients.size(); i++) {
                ClientThread t = clients.get(i);
                try {
                    t.out.writeObject(message);
                    t.out.reset();
                    t.out.flush();
                    gameData.setPlayerNum(i + 1); //Sets the player number
                    t.out.reset();
                    t.out.flush();

                    t.out.writeObject(gameData); //Sends the MorraInfo to the clients
                }
                catch(Exception e) {}
            }
        }

        //Reads in the MorraInfo class received by the server
        public void updateServerData() {
            for(int i = 0; i < clients.size(); i++) {
                ClientThread t = clients.get(i);
                try {
                    tempData = (MorraInfo) t.in.readObject();
                    updateGameData(tempData);
                }
                catch(Exception e) {}
            }
        }

        //Helper method of update server data. Ensures that the server data will get updated without fully overwriting the server's data
        public void updateGameData(MorraInfo tempData){
            gameData.setPlayerNum(tempData.getPlayerNum());

            if(tempData.getPlayerNum() == 1){
                gameData.setPlay(tempData.getP1Plays());
            }
            if(tempData.getPlayerNum() == 2){
                gameData.setPlay(tempData.getP2Plays());
            }
        }

        //Used to evaluate the players' guesses with the total and will return an integer which will determine who won or if there's a draw
        public int evaluate() {
            //callback.accept("P1 Played: " + gameData.getP1Count());
            //callback.accept("P2 Played: " + gameData.getP2Count());
            //callback.accept("Total is: " + gameData.getTotalNum());
            int p1Play = Integer.parseInt(gameData.getP1Count());
            int p2Play = Integer.parseInt(gameData.getP2Count());
            int p1Guess = Integer.parseInt(gameData.getP1Guess());
            int p2Guess = Integer.parseInt(gameData.getP2Guess());





            //If the guess is invalid
            if((p1Guess < 0 || p1Guess > 10) || (p2Guess < 0 || p2Guess > 10)){
                return -1;
            }
                //P1 wins
            if((p1Guess == gameData.getTotalNum()) && (p2Guess != gameData.getTotalNum())){
                gameData.setP1Points();
                return 1;
            }
                //P2 wins
            else if((p2Guess == gameData.getTotalNum()) && (p1Guess != gameData.getTotalNum())){
                gameData.setP2Points();
                return 2;
            }

            return 0;
        }

        public void run(){

            try {
                in = new ObjectInputStream(connection.getInputStream());
                out = new ObjectOutputStream(connection.getOutputStream());
                connection.setTcpNoDelay(true);

            }
            catch(Exception e) {
                System.out.println("Streams not open");
            }

            while(true) {
                try {

                    //Only one client is connected and ready
                    if (count == 1 && !trapState){
                        gameData.setNumConnected(count);
                        updateClients("Awaiting one more player...");
                        msgCounter = 1;
                        trapState = true;
                    }

                    else if (count == 2 && msgCounter == 1) //start game
                    {
                        msgCounter = 2;
                        gameData.setNumConnected(count);
                        updateClients("Game is starting!");

                    }


                    else if(msgCounter == 2){ //start game state
                        updateClients("Your opponent has connected!");
                        updateClients("OH NO! They appear to be invisible");
                        updateClients("Quick! guess the combined number of fingers to find them!");
                           msgCounter = 3;
                    }

                    //Takes in the player number
                    else if(msgCounter == 3){

                        updateServerData();
                        msgCounter = 4;
                    }
                    //Sets the total
                    else if(msgCounter == 4){
                        gameData.setTotalNum();

                        if(gameData.getP1Plays().charAt(0) == '9') { //P2 Disconnect
                            count--; clients.remove(0);
                            msgCounter = count;
                            trapState = false;
                            gameData.setNumConnected(count);
                            updateClients(" ");
                        }
                        if(gameData.getP2Plays().charAt(0) == '9') { //P2 Disconnect
                            count--; clients.remove(1);
                            msgCounter = count;
                            trapState = false;
                            gameData.setNumConnected(count);
                            updateClients(" ");
                        }

                        if(count < 2) {
                            callback.accept(" A player has disconnected mid-game");
                            System.exit(0);
                        }

                        else { //print round data
                            callback.accept("P1 Played: " + gameData.getP1Plays());
                            callback.accept("P2 Played: " + gameData.getP2Plays());
                            callback.accept("Sum is: " + gameData.getTotalNum());
                            gameData.setTrigger(7);
                        }


                        //updateClients("Guess the total (0 - 10) of both numbers!");
                        //updateServerData();
                        roundWinner = evaluate();
                        if(roundWinner == -1) {
                            msgCounter = 6;
                        }
                        else{ //proceed and unfreeze clients
                            msgCounter = 7;
                            if (!trapState) {
                                gameData.setTrigger(7);
                            }
                            else{
                                gameData.setTrigger(2);
                                gameData.setNumConnected(count);
                            }
                            updateClients(" ");
                        }
                    }
                    else if(msgCounter == 6){ //not used
                        updateClients("Invalid Response Input!");
                        msgCounter = 5;
                    }
                    else if(msgCounter == 7){ //end of round
                        if((roundWinner == 1) || (roundWinner == 2)){
                            if(gameData.getP1Points() == 2){
                                gameData.setWinner(1);
                                gameData.setgameWinner(1);
                                updateClients("Player 1 has won the game!");
                                callback.accept("SCORE P1: " + gameData.getP1Points() + " P2: " + gameData.getP2Points());
                                callback.accept("Player 1 has won the game!");
                                msgCounter = 8;
                            }
                            else if(gameData.getP2Points() == 2){
                                gameData.setWinner(2);
                                gameData.setgameWinner(2);
                                updateClients("Player 2 has won the game!");
                                callback.accept("Player 2 has won the game!");
                                msgCounter = 8;
                                callback.accept("SCORE P1: " + gameData.getP1Points() + " P2: " + gameData.getP2Points());
                            }
                            else{
                                gameData.setWinner(roundWinner);
                                gameData.setgameWinner(0);
                                updateClients("Total was " + gameData.getTotalNum() + ", so Player " + roundWinner + " has won the round!");
                                callback.accept("SCORE P1: " + gameData.getP1Points() + " P2: " + gameData.getP2Points());
                                msgCounter = 3;
                            }
                        }
                        else{ //end round
                            gameData.setWinner(0);
                            gameData.setgameWinner(0);
                            updateClients("Total was " + gameData.getTotalNum() + ", so the round ends in a draw!\n");
                            callback.accept("Total was " + gameData.getTotalNum() + ", so the round ends in a draw!");
                            callback.accept("SCORE P1: " + gameData.getP1Points() + " P2: " + gameData.getP2Points());
                            msgCounter = 3;
                        }
                    }
                    else if(msgCounter == 8){ //end game
                        updateClients("Would you like to play again?");
                        updateServerData();
                        gameData.clear();
                        gameData.setTrigger(0);
                        if(gameData.getP1Plays().equals("7 11")){
                            gameData.setTrigger(gameData.getTrigger() + 2);
                        }

                        else {
                            count--;
                            gameData.setNumConnected(count);
                            gameData.setPlayerNum(1);
                            gameData.setPlay("8 11");
                            clients.remove(0);


                        }
                        if(gameData.getP2Plays().equals("7 11")){
                            gameData.setTrigger(gameData.getTrigger() + 2);
                        }

                        else {
                            count--;
                            gameData.setNumConnected(count);
                            gameData.setPlayerNum(2);
                            gameData.setPlay("8 11");
                            clients.remove(1);

                        }
//                        if(count == 1){
//                            trapState = false;
//                        }
                        callback.accept("Count is " + clients.size());

                        if(gameData.getTrigger() == 4) {
                            gameData.resetPoints();
                            msgCounter = 1;
                            //updateClients("The game has started!");
                        }


                        if(count == 0) {
                            System.exit(0);
                        }
                    }
                }
                catch(Exception e) {
                    callback.accept(" Server is shutting  down!");
                    clients.remove(this);
                    System.exit(0);
                }
            }
        }//end of run
    }//end of client thread

}