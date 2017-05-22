package pt.iscte.batalhanaval;

import android.content.Intent;
import android.graphics.Color;
import android.net.wifi.p2p.WifiP2pManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.GridLayout;
import android.widget.TextView;

public class GameActivity extends AppCompatActivity implements View.OnClickListener {
private GridLayout glp;
    private Player player2;
    private Player player1;
private TextView B00p;
    private TextView B10p;
    private TextView B20p;
    private TextView B04p;

    private Ship s1;
    private Ship s2;
    private Ship s3;
    private Ship s4;
    private Ship s5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        player2 = new Player("player2");


        glp = (GridLayout) findViewById(R.id.glPrincipal);
        B00p = (TextView) findViewById(R.id.button00p);
        B10p =  (TextView) findViewById(R.id.button10p);
        B20p= (TextView) findViewById(R.id.button20p);
        B04p = (TextView) findViewById(R.id.button04p);

        putShipsP2();
       // player2.addShipsToGrid(1,0,s1);


        int childCount = glp.getChildCount();
        Log.d("child" , Integer.toString(childCount));
        for(int i =0; i<glp.getRowCount(); i++){
            for(int j =0; j<glp.getColumnCount(); j++){
               // TextView  v = fi
            }
        }
        B00p.setOnClickListener(this);
        B10p.setOnClickListener(this);
        B20p.setOnClickListener(this);
        B04p.setOnClickListener(this);









    }

    public void putShipsP2 (){
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





    public void jog ( ){
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
        }
        else{
        Log.d("Entrei no else", "Entrei no else");
        v.setBackgroundColor(Color.BLUE);
       // player2.activeUser("player2");
            int [] array = player2.chooseAttack();
            int l = array [0];
            int c = array[1];

            PlaceShipsActivity p = new PlaceShipsActivity();
            glp.setVisibility(View.GONE);

            p.setGrid();





    }



    }


}



