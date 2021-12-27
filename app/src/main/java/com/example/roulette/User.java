package com.example.roulette;

import java.util.ArrayList;
import java.util.HashMap;

public class User {
    public String email, full_name, password,balance,games ,wins,wins_money,bets_money,img_count , biggest_win, biggest_bet,User_id,last_online ;

    public User(){}
    public User(String full_name,String balance ,String games, String wins, String wins_money, String bets_money,String biggest_bet, String biggest_win,String User_id,String last_online, String email){
        this.full_name = full_name;
        this.balance = balance;
        this.games = games;
        this.wins = wins;
        this.wins_money = wins_money;
        this.bets_money = bets_money;
        this.biggest_bet = biggest_bet;
        this.biggest_win = biggest_win;
        this.User_id = User_id;
        this.last_online = last_online;
        this.email = email;
    }
    public User(String full_name, String email,String password) {
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
