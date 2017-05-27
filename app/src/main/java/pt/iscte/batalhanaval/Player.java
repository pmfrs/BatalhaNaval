package pt.iscte.batalhanaval;

import android.view.View;
import android.widget.TextView;

import java.io.Serializable;
import java.util.Random;

/**
 * Created by Sara on 13/04/2017.
 */

public class Player implements Serializable {
    private boolean myTurn;
    private String playerName;
    private int[] myPlays;
    private int[] myBoats;

    private int[][] defaultBoats;

    public Ship[] ships;
    private int [][] board;

    //Constructor
    public Player (String playerName, int strategy){
        defaultBoats = new int[][]{
                {11,21,22,32,42,39,49,43,53,63,73,66,76,86},
                {24,25,39,49,59,66,67,68,69,75,85,95,44,45},
                {8,9,22,32,42,35,36,37,38,61,62,63,26,27},
                {0,10,13,14,15,27,37,47,57,32,42,52,64,65},
                {14,24,36,46,56,32,42,52,62,76,77,78,74,84},
        };

        myTurn = false;
        this.playerName = playerName;
        myBoats = new int[14];
        for(int i = 0; i < myBoats.length; i++){
            myBoats[i] = defaultBoats[strategy-1][i];
        }

        myPlays = new int[100];

        cleanMatrixPlays(myPlays);

    }

    private void cleanMatrixPlays(int[] array){
        int i;
        for(i=0;i<100;i++){
            array[i] = 0;
        }
    }

    public boolean getShot(int shot){
        boolean success = false;

        for(int i = 0; i< myBoats.length;i++){

            if(shot == myBoats[i]){
                success = true;
                break;
            }
        }

        return success;
    }

    //For PC bot
    public int shot(){
        int shot;

        Random rand = new Random();
        shot = rand.nextInt(99);

        if(myPlays[shot] == 0){
            myPlays[shot] = 1;
            return shot;
        }
        else return -1;
    }

    public int[] getMyBoats(){
        return myBoats;
    }

    //Maintaned
    public void deleteShip (Ship ship){
        for(int i =0; i<5; i++){
            for (int j = 0 ; j<5; j++){
                if(board[i] [j] == ship.getIdShip())
                    board[i] [j] = 0;
            }
        }

    }

    /*public class Player implements Serializable {
    private boolean turn;
    private String playerName;
    public Ship[] ships;
    private int [][] board;

    public Player (String playerName){
        turn = false;
        this.playerName = playerName;
        //initBoard();
        ships = new Ship[5];
       // board = new int[10][10];



    }

    public void initBoard(){
        for(int x = 0; x<10; x++){
            for(int y=0; y<10; y++){
                board [x][y] = 0;
            }
        }
    }

    public String getName(){
        return playerName;
    }

    public boolean getTurn(){
        return turn;
    }

    public boolean startTurn(){
        return true;
    }

    public boolean endTurn(){
        return false;
    }

    public Ship [] getShips (){ return ships;}
    //public void setShips(Ship [] ships) {
         //ships1= ships;

    //}

   // public void attack(int row, int col){
     //   if()
    //}





    public void deleteShip (Ship ship){
        for(int i =0; i<5; i++){
            for (int j = 0 ; j<5; j++){
                if(board[i] [j] == ship.getIdShip())
                    board[i] [j] = 0;
            }
        }

    }



    public int[][] getBoard(){
        return board;
    }

    public void addShipsToGrid(int row, int col, Ship ship){

        //if(row+ship.getHeight() <= 10 && col+ship.getLenght() <= 10){

            //Fazer metodo para verificar se nao existe nenhum navio no mesmo sitio

          //  for(int i = row; i< row + ship.getHeight(); i++){
                board[row][col] = ship.getIdShip();
            //}

           // for(int j = col; j< col + ship.getLenght() ; j++){
             //   board[row][j] = ship.getIdShip();
            //}

       // }
    }

    public int [] chooseAttack(){
        int l = 0;
        int c = 0;
        int [] array = new int [2];
        Random r = new Random();
        l = r.nextInt(10);
        c = r.nextInt(10);
        array[0] = l;
        array[1] = c;
        return array;
    }

    public String activeUser(String username){
        return username;

    }*/
}
