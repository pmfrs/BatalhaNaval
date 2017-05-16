package pt.iscte.batalhanaval;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;

public class GameActivity extends AppCompatActivity {
private GridLayout glp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);


        glp = (GridLayout) findViewById(R.id.glPrincipal);


    }

    public void jog ( View view){
        Player p2 = new Player("p2");
        p2.addShipsToGrid(0,0,new Ship(1,2,0,0,1));

        if (view.getParent() == glp){

        }

    }


}
