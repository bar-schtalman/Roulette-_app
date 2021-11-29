package com.example.roulette;

import java.util.ArrayList;
import java.util.HashMap;

public class User {
    //    public Users_bet bet;
    public String email, full_name, password,random_bet,balance,games, wins;


    String a1,a2,a3,a4,a0,a5,a6,a7,a8,a9,a10,a11,a12,a13,a14,a15,a16,a17,a18,a19,
            a20,a21,a22,a23,a24,a25,a26,a27,a28,a29,a30,a31,a32,a33,a34,a35,a36;
//    public int [] bet;


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
