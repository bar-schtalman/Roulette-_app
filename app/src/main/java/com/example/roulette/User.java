package com.example.roulette;

import java.util.ArrayList;
import java.util.HashMap;

public class User {
    public String email, full_name, password,balance,games, wins,wins_money,bets_money,img_count , biggest_win, biggest_bet ;

    public User(){};
    public User(String full_name,  String email,String password) {
        this.full_name = full_name;
        this.email = email;
        this.password = password;
        this.balance = "200";
        this.games = "0";
        this.wins = "0";
        this.wins_money = "0";
        this.bets_money = "0";
        this.img_count = "0";
        this.biggest_win = "0";
        this.biggest_bet = "0";
    }



}
