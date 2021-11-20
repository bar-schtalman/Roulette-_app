package com.example.roulette;

import java.util.HashMap;

public class User {
    public String email, full_name, balance, password;
    HashMap<Integer,Integer> MAP = new HashMap<Integer,Integer>();

    public User(){}

    public User(String full_name,  String email,String password){
        this.full_name = full_name;
        this.email = email;
        this.password = password;
        this.balance = "0";
    }


}
