package com.example.roulette;

public class User {
    public String email, full_name, balance;

    public User(){}

    public User(String full_name,  String email,String balance){
        this.full_name = full_name;
        this.email = email;
        this.balance = "0";
    }


}
