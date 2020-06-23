package com.startng.farmvesting;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.FirebaseDatabase;
import com.startng.farmvesting.dashBoard.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.UUID;
import java.util.regex.Pattern;


public class SignUpActivity extends AppCompatActivity {
    Spinner userCategorySp;
    TextInputEditText fullNameEt, emailEt, phoneEt, dateOfBirthEt, passwordEt,
            confirmPasswordEt, nextOfKinFullNameEt, nextOfKinEmailEt;
    String fullNameText, emailText, phoneText, dateOfBirthText, passwordText, confirmPasswordText,
            userCategoryText, nextOfKinFullNameText,nextOfKinEmailText, notification,timeStamp;
    private FirebaseAuth mAuth;
    private static final String TAG = "EmailPassword";
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mAuth = FirebaseAuth.getInstance();

        userCategorySp = findViewById(R.id.spinner);
        userCategorySp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                userCategoryText = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mDatabase = FirebaseDatabase.getInstance().getReference();


        fullNameEt = findViewById(R.id.fullName);
        emailEt = findViewById(R.id.mail);
        phoneEt = findViewById(R.id.phone);
        dateOfBirthEt = findViewById(R.id.date_of_birth);
        passwordEt = findViewById(R.id.password);
        confirmPasswordEt = findViewById(R.id.confirm_password);
        nextOfKinFullNameEt = findViewById(R.id.next_of_kin_fullName);
        nextOfKinEmailEt = findViewById(R.id.next_of_kin_email);


        findViewById(R.id.button_signUp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignUp();
            }
        });

        findViewById(R.id.login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToLogIn();
            }
        });
    }


    private void SignUp(){

        fullNameText = getText(fullNameEt);
        emailText = getText(emailEt);
        phoneText = getText(phoneEt);
        dateOfBirthText = getText(dateOfBirthEt);
        passwordText = getText(passwordEt);
        confirmPasswordText = getText(confirmPasswordEt);
        nextOfKinFullNameText = getText(nextOfKinFullNameEt);
        nextOfKinEmailText = getText(nextOfKinEmailEt);
        notification = "Hurray! a new "+ userCategoryText.toLowerCase() +" just joined our community, we're gradually reaching our goal.";
        timeStamp = getCurrentTime();
        if (!areTextsValid()) showToast("Something is wrong, check inputs");

        if (areTextsValid()){

            mAuth.createUserWithEmailAndPassword(emailText, passwordText)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {

                                Log.d(TAG, "createUserWithEmail:success");
                                writeUser();

                                if (userCategoryText.equals("Farmer")) {
                                    Intent registerFarm = new Intent(SignUpActivity.this, FarmRegistrationActivity.class);
                                    startActivity(registerFarm);
                                } else if (userCategoryText.equals("Investor")) {

                                    // Sign in success, update UI with the signed-in user's information
                                    Intent goToDashboard = new Intent(SignUpActivity.this, LoginActivity.class);
                                    startActivity(goToDashboard);
                                    showToast("You're almost there...Please Login here.");
                                }
                            }else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                showToast("Authentication failed.");
                            }

                            // ...
                        }
                    });


        }

    }

    private String getCurrentTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("h:mm a, MMM d, yyyy");
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC+1"));
        Date today = Calendar.getInstance().getTime();
        return dateFormat.format(today);
    }


    private void goToLogIn(){
        Intent loginIntent = new Intent(SignUpActivity.this, LoginActivity.class);
        startActivity(loginIntent);
    }

    //Pass the message to the function to create a toast
    private void showToast(String message){
        Toast.makeText(SignUpActivity.this, message, Toast.LENGTH_SHORT).show();
    }

    // This is an easier method than the long if statement
    // It puts all the edittexts into an array and loops through.
    // Whenever it encounters an empty edittext it shows error and returns false

    private boolean areTextsValid() {

        TextInputEditText[] allEditTexts = {fullNameEt, emailEt, phoneEt, dateOfBirthEt, passwordEt,
                confirmPasswordEt, nextOfKinFullNameEt, nextOfKinEmailEt};
        for(TextInputEditText textInputEditText  : allEditTexts){
            if (getText(textInputEditText).isEmpty()){
                textInputEditText.setError("Field cannot be empty");
                return false;
            }
        }
        if (isNameInvalid(fullNameEt)) {
            fullNameEt.setError("Invalid input!");
            return false;
        }

        else if (isEmailInvalid(emailEt)){
            emailEt.setError("Invalid email");
            return false;
        }
        else if (passwordText.length()<8){
            passwordEt.setError("password must be at least 8 characters");
            return false;
        }
        else if (!passwordText.equals(confirmPasswordText)) {
            confirmPasswordEt.setError("password do not match");
            return false;
        }
        else if (userCategoryText.equals("Sign Up as")) {
            showToast("Please select a sign up category");
            return false;
        }

        else if (isNameInvalid(nextOfKinFullNameEt)) {
            nextOfKinFullNameEt.setError("Invalid input!");
            return false;
        }
        else if (isPhoneInvalid(phoneEt)) {
            phoneEt.setError("Invalid input!");
            return false;
        }
        else if (isDateInvalid(dateOfBirthEt)) {
            dateOfBirthEt.setError("Invalid input!");
            return false;
        }
        else if (isEmailInvalid(nextOfKinEmailEt)){
            nextOfKinEmailEt.setError("Invalid email");
            return false;
        }
        return true;
    }
    private boolean isEmailInvalid(TextInputEditText emailEt){
        return !Patterns.EMAIL_ADDRESS.matcher(getText(emailEt)).matches();
    }

    private boolean isNameInvalid(TextInputEditText fullNameEt){
        return !Pattern.compile("[a-z A-z]{0,256}").matcher(getText(fullNameEt)).matches();
    }
    private boolean isPhoneInvalid(TextInputEditText phoneEt){
        return !Pattern.compile("[+0-9]{0,14}").matcher(getText(phoneEt)).matches();
    }
    private boolean isDateInvalid(TextInputEditText dateOfBirthEt){
        return !Pattern.compile("[/0-9]{0,10}").matcher(getText(dateOfBirthEt)).matches();
    }

    private String getText(TextInputEditText textInputEditText){
        return textInputEditText.getText().toString().trim();
    }
    private void writeUser() {
        String id = UUID.randomUUID().toString();
        User user = new User(id, fullNameText , emailText , phoneText, dateOfBirthText ,
                userCategoryText, nextOfKinFullNameText,nextOfKinEmailText);
        User notify = new User(notification,timeStamp);

        mDatabase.child("users").push().setValue(user).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                showToast("Registration Successful.");

                TextInputEditText[] allEditTexts = {fullNameEt, emailEt, phoneEt, dateOfBirthEt, passwordEt,
                        confirmPasswordEt, nextOfKinFullNameEt, nextOfKinEmailEt};
                for(TextInputEditText editText : allEditTexts){

                    editText.setText("");
                }
                userCategorySp.setSelection(0);
            }

        });
        mDatabase.child("notifications").push().setValue(notify);
    }

}