import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.util.function.Consumer;



public class Client extends Thread{

    String ip;
    int portNum;
    Socket socketClient;

    ObjectOutputStream out;
    ObjectInputStream in;

    MorraInfo gameData = new MorraInfo();

    private Consumer<Serializable> callback;

    //Init Client
    Client(int port, String addr, Consumer<Serializable> call){
        portNum = port;
        ip = addr;
        callback = call;
        callback.accept("Port configured to " + portNum);
    }

    //get how many players
    public int getNumPlayers () {
        return gameData.getNumConnected();
    }

    //get the player number
    public int getPlayerNum() {
        return gameData.getPlayerNum();
    }

        //run the client
    public void run() {

        try {
            socketClient= new Socket(ip,portNum);
            out = new ObjectOutputStream(socketClient.getOutputStream());
            in = new ObjectInputStream(socketClient.getInputStream());
            socketClient.setTcpNoDelay(true);
        }
        catch(Exception e) {}

        while(true) {

            try {
                String message = in.readObject().toString();
                callback.accept(message);
                gameData = (MorraInfo) in.readObject();
            }
            catch(Exception e) {}
        }

    }

    public void send(String data) {//send data to server

        try {
            gameData.setPlay(data);
            out.reset();
            out.flush();
            out.writeObject(gameData);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}