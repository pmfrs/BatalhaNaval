package pt.iscte.batalhanaval;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.wifi.p2p.WifiP2pManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.TimerTask;

public class GameActivity extends AppCompatActivity implements View.OnClickListener {
    private GridLayout glp;
    private Player player2;
    private Player player1;
    private TextView B00p;
    private TextView B10p;
    private TextView B20p;
    private TextView B04p;

    private TextView B00P1;
    private TextView B10P1;
    private TextView B20P1;
    private TextView B30P1;
    private TextView B40P1;
    private TextView B50P1;
    private TextView B60P1;
    private TextView B70P1;
    private TextView B80P1;
    private TextView B90P1;










    private TextView B11P1;
    private TextView B21P1;
    private TextView B22P1;
    private TextView B32P1;
    private TextView B42P1;
    private TextView B43P1;
    private TextView B53P1;
    private TextView B63P1;
    private TextView B73P1;
    private TextView B66P1;
    private TextView B76P1;
    private TextView B86P1;
    private TextView B39P1;
    private TextView B49P1;
    private TextView im;


    private Ship s1;
    private Ship s2;
    private Ship s3;
    private Ship s4;
    private Ship s5;

    private int myBoatsDisplay = 99;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        String intentString = getIntent().getStringExtra("BOATS_DISPLAY");

        try {
            myBoatsDisplay = Integer.parseInt(intentString);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "An error occured.", Toast.LENGTH_SHORT).show();
            Intent registerIntent = new Intent(GameActivity.this, Lobby.class);
            GameActivity.this.startActivity(registerIntent);
        }

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
        B04p.setOnClickListener(this);
    }

    public void putShipsP2() {
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


    }






    }


















