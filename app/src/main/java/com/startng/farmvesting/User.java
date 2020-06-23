package com.startng.farmvesting;

public class User {
    public  String  id,Name , email , phone, dateOfBirth ,
           userCategory, nextOfKinFullName,nextOfKinEmail,notification, timeStamp;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }
    public User(String notification, String timeStamp) {
        this.notification = notification;
        this.timeStamp = timeStamp;
    }

    public User(String id, String Name , String email , String phone, String dateOfBirth ,
                  String userCategory, String nextOfKinFullName, String nextOfKinEmail){
        this.id = id;
        this.Name = Name;
        this.email = email;
        this.phone = phone;
        this.dateOfBirth = dateOfBirth;
        this.userCategory = userCategory;
        this.nextOfKinFullName = nextOfKinFullName;
        this.nextOfKinEmail = nextOfKinEmail;

    }
}
