package pt.iscte.batalhanaval;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class Lobby extends AppCompatActivity implements View.OnClickListener{

    private Button logOutButton;
    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lobby);
        logOutButton = (Button) findViewById(R.id.logOutButton);
        firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() == null){
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }
        logOutButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
       if (view == logOutButton){
           logOff();
       }
    }

    private void logOff() {
        if (firebaseAuth.getCurrentUser() != null) {
            firebaseAuth.signOut();
            finish();
            startActivity(new Intent(getApplicationContext(),LoginActivity.class));
        }
    }
}
