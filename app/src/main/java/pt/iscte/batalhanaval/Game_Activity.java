package pt.iscte.batalhanaval;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Game_Activity extends AppCompatActivity {


    private Button b11;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_);

        b11 = (Button) findViewById(R.id.button2);

        b11.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(b11.getText().equals("")){
                    b11.setText("X");
                }
            }

        });

    }
}
