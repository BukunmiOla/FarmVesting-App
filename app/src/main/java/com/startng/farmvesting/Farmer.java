package com.startng.farmvesting;

public class Farmer {
    public String id, farmerName, category, address, shortDescription, aboutBusiness, returns;
    public boolean verification;

    public Farmer() {
        // Default constructor required for calls to DataSnapshot.getValue(Farmer.class)
    }

    public Farmer(String id, String farmerName, String category, String address, String shortDescription, String aboutBusiness, String returns, boolean verification) {
        this.id = id;
        this.farmerName = farmerName;
        this.category = category;
        this.address = address;
        this.shortDescription = shortDescription;
        this.aboutBusiness = aboutBusiness;
        this.returns = returns;
        this.verification = verification;
    }

}
