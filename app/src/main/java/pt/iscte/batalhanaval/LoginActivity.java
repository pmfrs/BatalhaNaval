package pt.iscte.batalhanaval;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

public class LoginActivity extends AppCompatActivity {

    CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText usernameTxt = (EditText) findViewById(R.id.rgUsernameTxt);
        final EditText passTxt = (EditText) findViewById(R.id.passwordTxt);
        final Button logBtn = (Button) findViewById(R.id.loginBtn);
        final TextView registerTv = (TextView) findViewById(R.id.registTv);

        registerTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                LoginActivity.this.startActivity(registerIntent);
            }
        });


        callbackManager = CallbackManager.Factory.create();

        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {

                    }

                    @Override
                    public void onCancel() {
                        Toast.makeText(LoginActivity.this, "Facebook login canceled.", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(FacebookException error) {
                        Toast.makeText(LoginActivity.this, "Error on facebook login..", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        callbackManager.onActivityResult(requestCode,resultCode,data);
    }
}
