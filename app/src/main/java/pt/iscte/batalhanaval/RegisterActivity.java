package pt.iscte.batalhanaval;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final EditText usernameTxt = (EditText) findViewById(R.id.rgUsernameTxt);
        final EditText nascTxt = (EditText) findViewById(R.id.rgPassTxt);
        final EditText mailTxt = (EditText) findViewById(R.id.rgPassTxt);
        final EditText passTxt = (EditText) findViewById(R.id.rgPassTxt);
        final Button regBtn = (Button) findViewById(R.id.rgRegistarBtn);
        final Button cancBtn = (Button) findViewById(R.id.rgCancelarBtn);

        cancBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cancelIntent = new Intent(RegisterActivity.this, LoginActivity.class);
                RegisterActivity.this.startActivity(cancelIntent);
            }
        });



    }
}
