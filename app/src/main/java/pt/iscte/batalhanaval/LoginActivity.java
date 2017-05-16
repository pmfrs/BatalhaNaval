package pt.iscte.batalhanaval;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    CallbackManager callbackManager;

    private EditText usernameTxt;
    private EditText passTxt;
    private Button logBtn;
    private TextView registerTv;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;
    private LoginButton loginFB;
    private FirebaseAuth.AuthStateListener mAuthListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_login);

        firebaseAuth = FirebaseAuth.getInstance();

        if (firebaseAuth.getCurrentUser() != null){
            //finish();
            //Toast.makeText(this,firebaseAuth.getCurrentUser().getEmail().toString().trim(), Toast.LENGTH_LONG).show();
             startActivity(new Intent(getApplicationContext(),Lobby.class));
        }
        usernameTxt = (EditText) findViewById(R.id.rgUsernameTxt);
        passTxt = (EditText) findViewById(R.id.passwordTxt);
        logBtn = (Button) findViewById(R.id.loginBtn);
        registerTv = (TextView) findViewById(R.id.registTv);
        progressDialog = new ProgressDialog(this);


        registerTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                LoginActivity.this.startActivity(registerIntent);
            }
        });

        logBtn.setOnClickListener(this);

        callbackManager = CallbackManager.Factory.create();
        loginFB = (LoginButton) findViewById(R.id.login_button);
        loginFB.setReadPermissions("email","public_profile");
        loginFB.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
              handleFacebookAccessToken(loginResult.getAccessToken());
            }



            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });


    }

    private void handleFacebookAccessToken(AccessToken accessToken) {
        AuthCredential credential = FacebookAuthProvider.getCredential(accessToken.getToken());
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(getApplicationContext(),"Login Accepted",Toast.LENGTH_LONG).show();
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            Toast.makeText(getApplicationContext(),user.getEmail().toLowerCase().toString(),Toast.LENGTH_LONG).show();
                            if (user != null) {
                                startActivity(new Intent(getApplicationContext(), Lobby.class));
                                finish();
                            }
                        } else if (task.isComplete()){
                            Toast.makeText(getApplicationContext(),"Login Failed!",Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        callbackManager.onActivityResult(requestCode,resultCode,data);
    }

    @Override
    public void onClick(View view) {
        if (view == logBtn) {
            userLogin();
        }
    }

    private void userLogin() {
        String email = usernameTxt.getText().toString().trim();
        String password = passTxt.getText().toString().trim();

        if (TextUtils.isEmpty(email)){
            Toast.makeText(this,"Please enter email",Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(password)){
            Toast.makeText(this,"Please enter password",Toast.LENGTH_LONG).show();
            return;
        }

        progressDialog.setMessage("Login in Progress!!");
        progressDialog.show();
        firebaseAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                      progressDialog.dismiss();
                        if (task.isSuccessful()){
                            finish();
                            startActivity(new Intent(getApplicationContext(),Lobby.class));
                        } else if (task.isComplete()){
                            Toast.makeText(getApplicationContext(),"Login Failed!",Toast.LENGTH_LONG).show();
                        }
                    }
                });

    }
}
