package pt.iscte.batalhanaval;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText usernameTxt ;
    private EditText nascTxt ;
    private EditText mailTxt ;
    private EditText passTxt ;
    private Button regBtn ;
    private Button cancBtn ;
    private ProgressDialog pgb;
    private FirebaseAuth firebaseAuth;
    private String test;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        // EditText usernameTxt = (EditText) findViewById(R.id.rgUsernameTxt);
        // EditText nascTxt = (EditText) findViewById(R.id.rgPassTxt);
        mailTxt = (EditText) findViewById(R.id.rgEmailTxt);
        passTxt = (EditText) findViewById(R.id.rgPassTxt);
        regBtn = (Button) findViewById(R.id.rgRegistarBtn);
        cancBtn = (Button) findViewById(R.id.rgCancelarBtn);
        pgb = new ProgressDialog(this);
        //Firebase.setAndroidContext(this);
        //FirebaseApp.initializeApp(this);
        firebaseAuth = FirebaseAuth.getInstance();

        cancBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cancelIntent = new Intent(RegisterActivity.this, LoginActivity.class);
                RegisterActivity.this.startActivity(cancelIntent);
            }
        });
        regBtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
      registerUser();
    }

    private void registerUser() {

        String email = mailTxt.getText().toString().trim();
        String password = passTxt.getText().toString();
        if(TextUtils.isEmpty(email)){
        //    Toast.makeText(this, "Please Enter Email", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(password)){
        //    Toast.makeText(this, "Please Enter Password", Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(RegisterActivity.this, "Success", Toast.LENGTH_SHORT).show();
                        }else{
                           Toast.makeText(RegisterActivity.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }
}
