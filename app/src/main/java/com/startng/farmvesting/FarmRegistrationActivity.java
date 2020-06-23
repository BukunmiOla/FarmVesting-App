package com.startng.farmvesting;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

import com.startng.farmvesting.dashBoard.MainActivity;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.UUID;
import java.util.regex.Pattern;

public class FarmRegistrationActivity extends AppCompatActivity {
    Spinner categorySp;
    TextInputEditText addressEt, descriptionEt, returnsEt,farmerEt, businessInfoEt;
    String categoryText,addressText, describeText, returnsText, farmerName, businessInfoText;
    Boolean verification;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farm_registration);

        categorySp = findViewById(R.id.category);

        categorySp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                categoryText = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        mDatabase = FirebaseDatabase.getInstance().getReference();

        descriptionEt= findViewById(R.id.description);
        returnsEt = findViewById(R.id.returns_et);
        farmerEt = findViewById(R.id.farmer);
        addressEt = findViewById(R.id.farmer_address);
        businessInfoEt = findViewById(R.id.business_info_et);

        findViewById(R.id.submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Submit();
            }
        });
    }

    private void Submit() {

        describeText = getText(descriptionEt);
        returnsText = getText(returnsEt);
        farmerName = getText(farmerEt);
        addressText = getText(addressEt);
        businessInfoText = getText(businessInfoEt);

        verification = false;


        if (categoryText.equals("Select farming category")) {
            Toast.makeText(FarmRegistrationActivity.this, "Please select a category.",
                    Toast.LENGTH_SHORT).show();
        }
        else if (areTextsValid()) {
            writeNewFarmer();

        }


    }
    private boolean areTextsValid() {

        TextInputEditText[] allEditTexts = {farmerEt, addressEt, descriptionEt, returnsEt,businessInfoEt};
        for(TextInputEditText textInputEditText : allEditTexts){
            if (getText(textInputEditText).isEmpty()){
                textInputEditText.setError("Field cannot be empty");
                return false;
            }
        }
        if (!isNameValid(farmerEt)) {
            farmerEt.setError("Invalid input!");
            return false;
        }
        return true;
    }
    private void writeNewFarmer() {
        String id = UUID.randomUUID().toString();
        Farmer farmer = new Farmer(id, farmerName, categoryText, addressText, describeText, businessInfoText, returnsText, verification);

        mDatabase.child("farms").push().setValue(farmer).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(FarmRegistrationActivity.this, "Registration Successful.",
                        Toast.LENGTH_SHORT).show();

                TextInputEditText[] allEditTexts = {addressEt, descriptionEt, returnsEt,farmerEt, businessInfoEt};
                for(TextInputEditText editText : allEditTexts){

                    editText.setText("");
                }
                categorySp.setSelection(0);
                Intent goToDashboard = new Intent(FarmRegistrationActivity.this, MainActivity.class);
                startActivity(goToDashboard);
            }

        });
    }
    private boolean isNameValid(TextInputEditText farmerEt){
        return Pattern.compile("[a-z A-z]{0,256}").matcher(getText(farmerEt)).matches();
    }
    private String getText(TextInputEditText textInputEditText){
        return textInputEditText.getText().toString().trim();
    }

}
