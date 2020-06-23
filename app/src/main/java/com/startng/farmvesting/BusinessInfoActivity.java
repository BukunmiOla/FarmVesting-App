package com.startng.farmvesting;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

public class BusinessInfoActivity extends AppCompatActivity {
    String farmerName, category, address, aboutBusiness, returns,verification, businessInfo;
    TextView verificationTv, aboutBusinessTv, returnsTv;
    Intent getBusinessInfo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_info);

        verificationTv = findViewById(R.id.verification_tv);
        aboutBusinessTv = findViewById(R.id.about_business_tv);
        returnsTv = findViewById(R.id.returns_tv);

        getBusinessInfo = getIntent();

        farmerName= getBusinessInfo.getExtras().getString("farmerName");
        category = getBusinessInfo.getExtras().getString("category");
        address = getBusinessInfo.getExtras().getString("address");
        aboutBusiness = getBusinessInfo.getExtras().getString("aboutBusiness");
        returns = getBusinessInfo.getExtras().getString("returns");
        verification = getBusinessInfo.getExtras().getString("verification");

        if (verification.equals("Verified")){
            verificationTv.setTextColor(Color.parseColor("#05E10E"));
        }else if (verification.equals("Not Verified")){
            verificationTv.setTextColor(Color.parseColor("#EF0808"));
        }

        businessInfo = farmerName + " is a "+category+" business \n"+aboutBusiness+"\n The Farm is located at "+address;
        String returned = "Returns: "+returns;

        verificationTv.setText(verification);
        aboutBusinessTv.setText(businessInfo);
        returnsTv.setText(returned);
    }
}
