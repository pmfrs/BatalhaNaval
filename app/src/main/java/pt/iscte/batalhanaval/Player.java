package pt.iscte.batalhanaval;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.io.Serializable;
import java.util.Random;

/**
 * Created by Sara on 13/04/2017.
 */

public class Player implements Serializable {

    //private String TAG = "Player";

    private int[] myPlays;
    private int[] myBoats;
    private int[] othersBoats;

    private int[][] defaultBoats;


    private int hit = 0;

    public Ship[] ships;
    private int [][] board;


    //Constructor
    public Player (int strategy, int otherStrat){
        defaultBoats = new int[][]{
                {11, 21,22,32,42,43,53,63,73,66,76,86,39,49},
                {24,25,39,49,59,66,67,68,69,75,85,95,44,45},
                {8,9,22,32,42,35,36,37,38,61,62,63,26,27},
                {0,10,13,14,15,27,37,47,57,32,42,52,64,65},
                {14,24,36,46,56,32,42,52,62,76,77,78,74,84},
        };

        myBoats = defaultBoats[strategy-1];
        //for(int i = 0; i < myBoats.length; i++){
        //    myBoats[i] = defaultBoats[strategy-1][i];
        //}

        if(otherStrat != -1){
            othersBoats = defaultBoats[otherStrat-1];
        }

        myPlays = new int[100];

        cleanMatrixPlays(myPlays);

    }

    private void cleanMatrixPlays(int[] array){
        int i;
        for(i=0;i<array.length;i++){
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
    public int shot(int level){
        int shot, odd;
        Random rand = new Random();

        odd = rand.nextInt(100);

        if(odd < level && hit < othersBoats.length){
            shot = othersBoats[hit];
            hit++;
            if(myPlays[shot] == 0)
            {
                myPlays[shot] = 1;
                return shot;
            }
        }

        shot = rand.nextInt(99);

        if(myPlays[shot] == 0){
            myPlays[shot] = 1;
            return shot;
        }

        return -1;
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

}
