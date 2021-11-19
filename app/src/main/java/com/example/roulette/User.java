package com.example.roulette;

public class User {
    public String email, full_name, balance, password;

    public User(){}

    public User(String full_name,  String email,String password){
        this.full_name = full_name;
        this.email = email;
        this.password = password;
        this.balance = "0";
    }


}
