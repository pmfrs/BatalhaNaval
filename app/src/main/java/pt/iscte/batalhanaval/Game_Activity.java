package pt.iscte.batalhanaval;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;

public class Game_Activity extends AppCompatActivity {

    private Player player1;
    private Player player2;
    private GridLayout board;
    private ImageView player1Ships[];
    private ImageView player2Ships[];

    private Player [] players;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_);

        players = new Player[2];
        player1Ships = new ImageView[5];
        player2Ships = new ImageView[5];


        player1 = (Player)getIntent().getSerializableExtra("Player1");
        player2 = (Player)getIntent().getSerializableExtra("Player2");

        players[0] = player1;
        players[1] = player2;





        player1Ships [0] = (ImageView) findViewById(R.id.ship1r);
        player1Ships [1] = (ImageView) findViewById(R.id.ship2r);
        player1Ships [2] = (ImageView) findViewById(R.id.ship3r);
        player1Ships [3] = (ImageView) findViewById(R.id.ship4r);
        player1Ships [4] = (ImageView) findViewById(R.id.ship5r);

        player2Ships [0] = (ImageView) findViewById(R.id.ship1r);
        player2Ships [1] = (ImageView) findViewById(R.id.ship2r);
        player2Ships [2] = (ImageView) findViewById(R.id.ship3r);
        player2Ships [3] = (ImageView) findViewById(R.id.ship4r);
        player2Ships [4] = (ImageView) findViewById(R.id.ship5r);

        Intent i = new Intent(this, PlaceShipsActivity.class);
        startActivity(i);





    }
}
