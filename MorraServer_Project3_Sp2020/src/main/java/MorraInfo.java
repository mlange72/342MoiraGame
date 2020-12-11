import java.io.Serializable;






public class MorraInfo implements Serializable {
    private int p1Points; //Variables are self explanatory
    private int p2Points;
    private String p1Plays;
    private String p2Plays;
    private String p1Guess;
    private String p2Guess;
    private String p1Count;
    private String p2Count;
    private int total;
    private int playerNum;
    private int numConnected;
    private int winner;
    private int gameWinner;
    private int trigger;

    public int getTrigger(){
        return trigger;
    }

    public void setTrigger(int trig){
        trigger = trig;
    }

    public int getGameWinner(){
        return gameWinner;
    }

    public void setgameWinner(int win){
        gameWinner = win;
    }

    public int getWinner(){
        return winner;
    }

    public void setWinner(int win){
        winner = win;
    }


    public int getNumConnected(){
        return numConnected;
    }

    public void setNumConnected(int connected){
        numConnected = connected;
    }




    public int getP1Points(){
        return p1Points;
    }

    public void setP1Points(){
        p1Points++;
    }

    public void resetPoints() {
        p1Points = 0;
        p2Points = 0;
    }


    public int getP2Points(){
        return p2Points;
    }

    public void setP2Points(){
        p2Points++;
    }

    public void clear(){
        p1Points = 0;
        p2Points = 0;
    }

    public String getP1Count(){
        return p1Count;
    }

    public String getP2Guess(){
        return p2Guess;
    }

    public String getP2Count(){
        return p2Count;
    }

    public String getP1Guess(){
        return p1Guess;
    }

    public String getP1Plays(){
        return p1Plays;
    }

    public String getP2Plays(){
        return p2Plays;
    }




    public int getTotalNum(){
        return total;
    }

    public void setTotalNum(){
        String substr = p1Plays.substring(0,1);
        int p1Int = Integer.parseInt(substr);
        String substr2 = p2Plays.substring(0,1);
        int p2Int = Integer.parseInt(substr2);

        total = p1Int + p2Int;

    }

    public int getPlayerNum(){
        return playerNum;
    }

    public void setPlayerNum(int num){
        playerNum = num;
    }

    public void setPlay(String choice){
        if(playerNum == 1){
            p1Plays = choice;
            p1Count = p1Plays.substring(0,1);
            p1Guess = p1Plays.substring(2);
        }
        else if(playerNum == 2){
            p2Plays = choice;
            p2Count = p2Plays.substring(0,1);
            p2Guess = p2Plays.substring(2);
        }
        else{
            System.out.println("Player Number did not get updated!!");
        }
    }

}