package pt.iscte.batalhanaval;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class PrePlace extends AppCompatActivity {
    private TextView place_ships_label;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_place);

        //Label placeships
        place_ships_label = (TextView) findViewById(R.id.place_ships_label);
        place_ships_label.setOnClickListener(clickListener);

    }
        View.OnClickListener clickListener = new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), PlaceShipsActivity.class);
                startActivity(intent);
            }
        };

}
