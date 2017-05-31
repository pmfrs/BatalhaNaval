package pt.iscte.batalhanaval;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private String TAG = "RegisterActivity";
    private EditText usernameTxt ;
    private EditText nascTxt ;
    private EditText mailTxt ;
    private EditText passTxt;
    private EditText nickname;
    private Button regBtn ;
    private Button cancBtn ;
    private ProgressDialog pgb;
    private FirebaseAuth firebaseAuth;

    private DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mailTxt = (EditText) findViewById(R.id.rgEmailTxt);
        passTxt = (EditText) findViewById(R.id.rgPassTxt);
        nickname = (EditText) findViewById(R.id.nickname);

        regBtn = (Button) findViewById(R.id.rgRegistarBtn);
        cancBtn = (Button) findViewById(R.id.rgCancelarBtn);
        pgb = new ProgressDialog(this);

        //Firebase.setAndroidContext(this);
        //FirebaseApp.initializeApp(this);
        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("users");

        cancBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cancelIntent = new Intent(RegisterActivity.this, LoginActivity.class);
                RegisterActivity.this.startActivity(cancelIntent);
            }
        });
        regBtn.setOnClickListener(this);

    }
    public void onBackPressed(){

    }
    @Override
    public void onClick(View v) {
      registerUser();
    }

    private void registerUser() {

        String email = mailTxt.getText().toString().trim();
        String password = passTxt.getText().toString();
        final String name = nickname.getText().toString();

        //String nickname = nickname.getText()

        if(TextUtils.isEmpty(email)){
            Toast.makeText(this, "Por favor insira o e-mail.", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(password)){
            Toast.makeText(this, "Por favor insira uma password.", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(name)){
            Toast.makeText(this, "Por favor insira um nickname..", Toast.LENGTH_SHORT).show();
            return;
        }


        pgb.setMessage("Registering on progress");
        pgb.show();

        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            pgb.cancel();
                            Toast.makeText(RegisterActivity.this, "Registado com sucesso.", Toast.LENGTH_SHORT).show();
                            saveUserInformation(name);
                            finish();
                            startActivity(new Intent(getApplicationContext(),Lobby.class));
                        }else{
                            //task.getException().getMessage();
                            pgb.cancel();
                            try {
                                throw task.getException();
                            } catch(FirebaseAuthWeakPasswordException e) {
                                Toast.makeText(RegisterActivity.this, "Introduziu uma password insegura.", Toast.LENGTH_LONG).show();
                            } catch(FirebaseAuthInvalidCredentialsException e) {
                                Toast.makeText(RegisterActivity.this, "O e-mail introduzido não é válido.", Toast.LENGTH_LONG).show();
                            } catch(Exception e) {
                                Toast.makeText(RegisterActivity.this, task.getException().toString(), Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                });

    }

    private void saveUserInformation(String nickname){
        UserInformation userInfo = new UserInformation(nickname,0,0);

        FirebaseUser user = firebaseAuth.getCurrentUser();

        databaseReference.child(user.getUid()).setValue(userInfo);
        //databaseReference.child(user.getUid()).getDatabase();
    }
}
