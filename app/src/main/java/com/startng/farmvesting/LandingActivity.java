package com.startng.farmvesting;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class LandingActivity extends AppCompatActivity {
    TextView login, signUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);
        login=findViewById(R.id.login);
        signUp=findViewById(R.id.sign_up);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Login();
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignUp();
            }
        });
    }
    private void Login(){
        Intent logIn=new Intent(LandingActivity.this, LoginActivity.class);
        startActivity(logIn);
    }
    private void SignUp(){
        Intent signUp = new Intent(LandingActivity.this, SignUpActivity.class);
        startActivity(signUp);
    }
}
