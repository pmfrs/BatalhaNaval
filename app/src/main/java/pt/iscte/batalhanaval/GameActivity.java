package pt.iscte.batalhanaval;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.wifi.p2p.WifiP2pManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.Random;
import java.util.TimerTask;

public class GameActivity extends AppCompatActivity implements View.OnClickListener {
    /*private Ship s1;
    private Ship s2;
    private Ship s3;
    private Ship s4;
    private Ship s5;*/

    private Button goBtn;
    private Button quitBtn;
    private TextView help;

    private int myBoatsDisplay = 99, plays = 0, successfulShots = 0;

    private Boolean multiplayer, myTurn = true;

    private int[] myPlays, othersPlays;
    private Player p2, mainPlayer;

    private int numbShots;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        String intentString = getIntent().getStringExtra("BOATS_DISPLAY");
        String intentString2 = getIntent().getStringExtra("MULTIPLAYER");

        try {
            myBoatsDisplay = Integer.parseInt(intentString);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "An error occured.", Toast.LENGTH_SHORT).show();
            Intent registerIntent = new Intent(GameActivity.this, Lobby.class);
            GameActivity.this.startActivity(registerIntent);
        }

        if(intentString2.equals("ON")) multiplayer = true;
        else multiplayer = false;

        myPlays = new int[100];
        othersPlays = new int[100];

        for(int i = 0; i < 100; i++){
            myPlays[i]=0;
            othersPlays[i]=0;
        }

        help = (TextView)findViewById(R.id.helpTxt);
        goBtn = (Button) findViewById(R.id.go);
        goBtn.setOnClickListener(this);

        quitBtn = (Button) findViewById(R.id.quit);
        quitBtn.setOnClickListener(this);

        cleanGrid();

        mainPlayer = new Player("MainPlayer", myBoatsDisplay);

        Random rand = new Random();

        if(!multiplayer){
            p2 = new Player("PC",rand.nextInt(5));
        }
        goBtn.setVisibility(View.INVISIBLE);

        int[] boats = mainPlayer.getMyBoats();
        for(int i = 0; i < boats.length; i++){
            othersPlays[boats[i]] = 5;
        }
    }

    public void onClick(View v) {

        if(v.equals(goBtn)){
            setupGrid(R.string.yourTurn, myPlays);
            myTurn = true;
            goBtn.setVisibility(View.INVISIBLE);
        } else if(v.equals(quitBtn)){

            quitGame();

        } else {
            if(!myTurn) return;
            //Toast.makeText(GameActivity.this,Integer.toString(v.getId()) ,Toast.LENGTH_SHORT).show();
            int pressedId = v.getId();

            if(myPlays[pressedId] != 0) return;

            boolean result = p2.getShot(pressedId);
            markShot(myPlays, pressedId, result);

            numbShots++;

            if(numbShots > 2){
                setupGrid(R.string.othersTurn, othersPlays);
                myTurn = false;
                SimulatePC();
            }

        }
    }

    private void SimulatePC(){

        for(int i = 0; i < 3; i++){
            int pcShot = p2.shot();
            while(pcShot == -1){
                pcShot = p2.shot();
            }
            boolean answer = mainPlayer.getShot(pcShot);
            markShot(othersPlays,pcShot,answer);
        }
        numbShots = 0;
        goBtn.setVisibility(View.VISIBLE);
        help.setText(R.string.pressPlay);
    }

    private void Shot(){

        plays++;
    }

    public void ReceiveShotFeedback(){

    }


    public void markShot(int[] playerGrid,int shot, boolean success){

        //String cellId = "button" + shot;
        //int resId = getResources().getIdentifier(cellId, "id", getPackageName());
        TextView aux = (TextView) findViewById(shot);

        if(!success){
            aux.setBackgroundResource(R.drawable.shape_button_blue);
            playerGrid[shot] = 2;//2 means miss
        } else{
            aux.setBackgroundResource(R.drawable.shape_button_red);
            playerGrid[shot] = 1; //1 means success
            successfulShots++;
            if(successfulShots > 13){
                String text = "";

                if(myTurn) text = "Parabéns foi o vencedo do jogo!";
                else text = "O seu adversário ganhou, o jogo terminou.";
                endGame(text);
            }
        }
    }

    private void setupGrid(int message, int[] grid){
        successfulShots = 0;
        help.setText(message);
        int i;
        for(i = 0; i<100;i++) {
            TextView aux = (TextView) findViewById(i);

            switch (grid[i]){
                case 0:
                    //No shots on that spot
                    aux.setBackgroundResource(R.drawable.shape_button);
                    break;
                case 1:
                    //Successful shots
                    successfulShots++;
                    aux.setBackgroundResource(R.drawable.shape_button_red);
                    break;
                case 2:
                    //Unsuccessful shots
                    aux.setBackgroundResource(R.drawable.shape_button_blue);
                    break;
                case 5:
                    //There's a bot there not shot
                    aux.setBackgroundResource(R.drawable.shape_button_gray);
                    break;
            }
        }
    }

    private void cleanGrid(){
        int i;
        for(i = 0; i<100;i++) {
            String cellId = "";
            if(i>9)  cellId = "button" + i;
            else cellId = "button0"+i;

            int resId = getResources().getIdentifier(cellId, "id", getPackageName());
            TextView aux = (TextView) findViewById(resId);
            aux.setBackgroundResource(R.drawable.shape_button);
            aux.setOnClickListener(this);
            aux.setId(i);
        }
    }

    private void quitGame(){
        AlertDialog.Builder alertD = new AlertDialog.Builder(GameActivity.this);
        alertD.setMessage("Tem a certeza que quer desistir do jogo?").setCancelable(false)
                .setPositiveButton("Sim", new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int which){
                        Intent registerIntent = new Intent(GameActivity.this, Lobby.class);
                        GameActivity.this.startActivity(registerIntent);
                    }
                })
                .setNegativeButton("Não", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        AlertDialog check = alertD.create();
        check.setTitle("Desistir do jogo");
        check.show();
    }

    private void endGame(String message){
        AlertDialog.Builder alertD = new AlertDialog.Builder(GameActivity.this);
        alertD.setMessage(message).setCancelable(false)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int which){
                        Intent registerIntent = new Intent(GameActivity.this, Lobby.class);
                        GameActivity.this.startActivity(registerIntent);
                    }
                });
        AlertDialog check = alertD.create();
        check.setTitle("Fim do jogo");
        check.show();
    }

    public void onBackPressed() {
        quitGame();
    }

    /* ESTAVA NO ONCREATE

    player2 = new Player("player2");

        glp = (GridLayout) findViewById(R.id.glPrincipal);
        B00p = (TextView) findViewById(R.id.button00p);
        B10p = (TextView) findViewById(R.id.button10p);
        B20p = (TextView) findViewById(R.id.button20p);
        B04p = (TextView) findViewById(R.id.button04p);

        B00P1 = (TextView) findViewById(R.id.button00p1);
        B10P1 = (TextView) findViewById(R.id.button10p1);
        B20P1 = (TextView) findViewById(R.id.button20p1);
        B30P1 = (TextView) findViewById(R.id.button30p1);

        B11P1 = (TextView) findViewById(R.id.button11p1);

        putShipsP2();
        // player2.addShipsToGrid(1,0,s1);

        int childCount = glp.getChildCount();
        Log.d("child", Integer.toString(childCount));
        for (int i = 0; i < glp.getRowCount(); i++) {
            for (int j = 0; j < glp.getColumnCount(); j++) {
                // TextView  v = fi
            }
        }
        B00p.setOnClickListener(this);
        B10p.setOnClickListener(this);
        B20p.setOnClickListener(this);
        B04p.setOnClickListener(this);*/

    /*public void putShipsP2() {
        s1 = new Ship(2, 0, 1, R.drawable.ship1r, R.id.ship1r);
        s2 = new Ship(3, 0, 2, R.drawable.ship2r, R.id.ship2r);
        s3 = new Ship(4, 0, 3, R.drawable.ship3r, R.id.ship3r);
        s4 = new Ship(5, 0, 4, R.drawable.ship4r, R.id.ship4r);
        s5 = new Ship(6, 0, 5, R.drawable.ship5r, R.id.ship5r);

        B00p.setId(s2.getIdShip());
        B10p.setId(s2.getIdShip());
        B20p.setId(s2.getIdShip());
        //player2.addShipsToGrid(1,0,s1);


        //  B00p.setId(s1.getImageID());
        //B10p.setId(s1.getImageID());

    }


    public void onBackPressed() {
        Intent registerIntent = new Intent(GameActivity.this, Lobby.class);
        GameActivity.this.startActivity(registerIntent);
    }


    public void jog() {
        //Player p2 = new Player("p2");

        // p2.addShipsToGrid(0,0,ship1);
        B00p.setId(s1.getIdShip());
        B00p.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                B00p.setBackgroundColor(224);
            }
        });

    }

    @Override
    public void onClick(View v) {

        if ((v.getId() == s2.getIdShip()) || v.getId() == s1.getIdShip() || v.getId() == s3.getIdShip() || v.getId() == s4.getIdShip() || v.getId() == s5.getIdShip()) {
            Log.d("Entrei no if", "IFFFF");
            Log.d(Integer.toString(v.getId()), Integer.toString(s2.getIdShip()));
            v.setBackgroundColor(Color.RED);
        } else {

            Object lockObject = new Object();
            synchronized (lockObject) {
                v.setBackgroundColor(Color.BLUE);
                findViewById(R.id.linearLL).setVisibility(View.GONE);
                findViewById(R.id.linearLp1).setVisibility(View.VISIBLE);

                posicaoBarcos(myBoatsDisplay);
                Log.d("Entrei no else", "Entrei no else");


            }


            // player2.activeUser("player2");
            int[] array = player2.chooseAttack();
            int l = array[0];
            int c = array[1];
            Log.d("l", Integer.toString(l));
            Log.d("c", Integer.toString(c));


            String z = (R.id.button + l+c + "p1");

            String str = "R.id.button".concat(""+1).concat(""+1).concat("p1");
            Log.d("String z : " , str);

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            String id1 = "button" +11+"p1";
            int resID = getResources().getIdentifier(id1, "id", getPackageName());
            im = (TextView) findViewById(resID);


            if (im.getId() == s1.getIdShip()) {
                im.setBackgroundColor(Color.RED);
            }

            else{
                im.setBackgroundColor(Color.BLUE);

            }


        }


    }

    public void posicaoBarcos(int x) {
        if (x == 1) {
            B11P1.setId(s1.getIdShip());

        }


    }*/

}


















