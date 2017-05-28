package pt.iscte.batalhanaval;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import org.w3c.dom.Text;

public class Lobby extends AppCompatActivity implements View.OnClickListener{

    private TextView LogOutTV;
    private Button singleplayerBtn;
    private Button multiplayerBtn;
    private Button instBtn;
    private String multiplayer = "OFF";

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lobby);

        LogOutTV = (TextView) findViewById(R.id.LogOutTV);
        singleplayerBtn = (Button) findViewById(R.id.play_pc);
        multiplayerBtn = (Button) findViewById(R.id.play_multiplayer);
        instBtn = (Button) findViewById(R.id.instBtn);

        firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() == null){
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }

        LogOutTV.setOnClickListener(this);
        singleplayerBtn.setOnClickListener(this);
        multiplayerBtn.setOnClickListener(this);
        instBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        if (view == LogOutTV){
           logOut();
       } else if (view == singleplayerBtn){
            singleGame();
        } else if(view == multiplayerBtn){
            multiGame();
        } else if(view == instBtn){
            instructions();
        }

    }

    public void onBackPressed(){

    }

    private void logOff() {
        if (firebaseAuth.getCurrentUser() != null) {
            firebaseAuth.signOut();
            finish();
            startActivity(new Intent(getApplicationContext(),LoginActivity.class));
        }
    }
    private void singleGame(){
        final CharSequence options[] = new CharSequence[] {"Fácil", "Intermédio", "Difícil"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Escolha a dificuldade:");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int dif = 0;

                if(options[which].equals("Fácil")){
                    dif = 0;
                } else if(options[which].equals("Intermédio")){
                    dif = 20;
                } else if(options[which].equals("Difícil")){
                    dif = 40;
                }

                Intent registerIntent = new Intent(Lobby.this, PlaceShipsActivity.class);
                registerIntent.putExtra("MULTIPLAYER",multiplayer);
                registerIntent.putExtra("DIFICULTY",Integer.toString(dif));
                Lobby.this.startActivity(registerIntent);
            }
        });
        builder.show();


    }

    private void instructions(){
        Intent registerIntent = new Intent(Lobby.this, InstructionsActivity.class);
        Lobby.this.startActivity(registerIntent);

    }

    private void multiGame() {
        AlertDialog.Builder alertD = new AlertDialog.Builder(Lobby.this);
        alertD.setMessage("O multiplayer não se encontra operacional. Deseja continuar?").setCancelable(false)
                .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        multiplayer = "ON";
                        Intent registerIntent = new Intent(Lobby.this, PlaceShipsActivity.class);
                        registerIntent.putExtra("MULTIPLAYER",multiplayer);
                        Lobby.this.startActivity(registerIntent);
                    }
                })
                .setNegativeButton("Não", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        AlertDialog check = alertD.create();
        check.setTitle("Aviso");
        check.show();
    }

    private void logOut(){
        AlertDialog.Builder alertD = new AlertDialog.Builder(Lobby.this);
        alertD.setMessage("Tem a certeza que quer terminar sessão?").setCancelable(false)
                .setPositiveButton("Sim", new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int which){
                        logOff();
                    }
                })
                .setNegativeButton("Não", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        AlertDialog check = alertD.create();
        check.setTitle("Terminar sessão");
        check.show();
    }
}
