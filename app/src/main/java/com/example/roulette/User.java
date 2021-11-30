package com.example.roulette;

import java.util.ArrayList;
import java.util.HashMap;

public class User {
    public String email, full_name, password,random_bet,balance,games, wins;



    public User(){};
    public User(String full_name,  String email,String password) {
        this.full_name = full_name;
        this.email = email;
        this.password = password;
        this.balance = "1000";
        this.games = "0";
        this.wins = "0";


    }



}
