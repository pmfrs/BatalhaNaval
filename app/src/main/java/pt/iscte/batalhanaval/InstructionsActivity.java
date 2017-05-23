package pt.iscte.batalhanaval;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class InstructionsActivity extends AppCompatActivity implements View.OnClickListener{

    private Button backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_instructions);

        backBtn = (Button) findViewById(R.id.backBtn);

        backBtn.setOnClickListener(this);
    }

    public void onBackPressed(){

    }

    public void onClick(View view){
        if(view == backBtn){
            Intent registerIntent = new Intent(InstructionsActivity.this, Lobby.class);
            InstructionsActivity.this.startActivity(registerIntent);
        }
    }
}
