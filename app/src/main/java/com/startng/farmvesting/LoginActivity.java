package com.startng.farmvesting;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.startng.farmvesting.dashBoard.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    TextInputEditText emailEt, passwordEt;
    String emailText, passwordText;
    private FirebaseAuth mAuth;
    private static final String TAG = "EmailPassword";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailEt = findViewById(R.id.email);
        passwordEt = findViewById(R.id.password);

        mAuth = FirebaseAuth.getInstance();

        findViewById(R.id.sign_up).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignUp();
            }
        });

        findViewById(R.id.buttonLogin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogIn();
            }
        });
    }

    private void LogIn() {
        emailText = getText(emailEt);
        passwordText = getText(passwordEt);

        if (areTextsValid()) {
            mAuth.signInWithEmailAndPassword(emailText, passwordText)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "signInWithEmail:success");
                                Toast.makeText(LoginActivity.this, "Logging in.....",
                                        Toast.LENGTH_SHORT).show();
                                Intent goToDashboard = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(goToDashboard);
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "signInWithEmail:failure", task.getException());
                                Toast.makeText(LoginActivity.this, "Invalid email or Password.",
                                        Toast.LENGTH_SHORT).show();
                            }

                            // ...

                        }
                    });
        }
    }
    private void SignUp() {
        Intent goToSignUp = new Intent(LoginActivity.this, SignUpActivity.class);
        startActivity(goToSignUp);
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();

    }
    private boolean areTextsValid() {

        TextInputEditText[] allEditTexts = { emailEt, passwordEt};
        for(TextInputEditText textInputEditText  : allEditTexts){
            if (getText(textInputEditText).isEmpty()){
                textInputEditText.setError("Field cannot be empty");
                return false;
            }
        }
        if (!isEmailValid(emailEt)){
            emailEt.setError("Invalid email");
            return false;
        }

        return true;
    }
    private boolean isEmailValid(TextInputEditText emailEt){
        return Patterns.EMAIL_ADDRESS.matcher(getText(emailEt)).matches();
    }
    private String getText(TextInputEditText textInputEditText){
        return textInputEditText.getText().toString().trim();
    }

}