package pt.iscte.batalhanaval;

import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class Lobby extends AppCompatActivity implements View.OnClickListener{

    private TextView LogOutTV;
    private Button singleplayerBtn;
    private Button multiplayerBtn;
    private Button instBtn;
    private String multiplayer = "OFF";


    private boolean changeBD;
    private int numbWins=0, numLoses=0;

    private TextView welcomeTxt, statsTxt;

    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lobby);



        //final MediaPlayer mp = MediaPlayer.create(this, R.raw.backsound);
        //mp.start();

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

        welcomeTxt = (TextView) findViewById(R.id.welcomeTxt);
        statsTxt = (TextView) findViewById(R.id.stats);

        FirebaseUser user = firebaseAuth.getCurrentUser();
        welcomeTxt.setText("Bem-vindo");

        databaseReference = FirebaseDatabase.getInstance().getReference();

        final String userUid = user.getUid();
        Toast.makeText(getApplicationContext(), userUid, Toast.LENGTH_SHORT);



        databaseReference.child("users").child(userUid).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        boolean newStats = false;

                        Intent intent = getIntent();
                        Bundle extras = intent.getExtras();
                        if(extras != null){
                            changeBD = true;
                            newStats = getIntent().getExtras().getBoolean("WINS");
                        }

                        String wins = "" , loses = "";
                        String nickname = "Bem-vindo";

                        for(DataSnapshot child : dataSnapshot.getChildren()){
                            if(child.getKey().equals("nickname")){
                                nickname = child.getValue().toString();
                            }
                            if(child.getKey().equals("wins")) {
                                wins = child.getValue().toString();
                            }
                            if(child.getKey().equals("loses")) {
                                loses = child.getValue().toString();
                            }
                        }

                        numbWins = Integer.parseInt(wins);
                        numLoses = Integer.parseInt(loses);

                        if(changeBD){
                            if(newStats) numbWins++;
                            else numLoses++;

                            DatabaseReference databaseReference2 = FirebaseDatabase.getInstance().getReference("users");

                            UserInformation newUser = new UserInformation(nickname,numbWins,numLoses);

                            databaseReference2.child(userUid).setValue(newUser);
                        }

                        welcomeTxt.setText("Bem-vindo "+nickname+"!");
                        statsTxt.setText("Ganhaste: "+Integer.toString(numbWins) + " jogos\nPerdeste: "+Integer.toString(numLoses)+" jogos");
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Toast.makeText(Lobby.this, "Falha ao conectar com a BD", Toast.LENGTH_SHORT).show();
                    }
                });
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
