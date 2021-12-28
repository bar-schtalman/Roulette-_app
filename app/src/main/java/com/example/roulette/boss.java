package com.example.roulette;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class boss extends AppCompatActivity {
    private ListView list_view1;
    private int wins,users_num,games, average_lost_per_win,wins_rate;
    private String common_nums, uncommon_nums, lost_money, balance ;
    private DatabaseReference reference;
    private DatabaseReference boss_reference;
    private int [] nums = new int[37];
    private String UserID = " ";
    private String UserBalance = " ";
    private String EMAIL = "roulleteboss@gmail.com";
    private String PASS = "uhgnjmsmvfppdmxz";
    private String User_Email = " ",User_Date = " ",User_Name = " ";
    private Button user_stats, game_stats, send_bonus;
    private TextView full_text;
    String name = " ";
    HashMap<String, User> map = new HashMap<String, User>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_boss);
        send_bonus = findViewById(R.id.bonus_button);
        send_bonus.setVisibility(View.GONE);
        user_stats = findViewById(R.id.user_stats);
        game_stats = findViewById(R.id.game_stats);
        reference = FirebaseDatabase.getInstance().getReference("Users");
        boss_reference = FirebaseDatabase.getInstance().getReference("Boss");
        list_view1 = findViewById(R.id.list_view);
        list_view1.setVisibility(View.GONE);
        full_text = findViewById(R.id.full_textview);
        map.clear();
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Iterator <DataSnapshot> it = snapshot.getChildren().iterator();
                while(it.hasNext() ){
                    DataSnapshot item = it.next();
                    name = item.child("full_name").getValue().toString().trim();
                    String balance = item.child("balance").getValue().toString().trim();
                    String games = item.child("games").getValue().toString().trim();
                    String wins = item.child("wins").getValue().toString().trim();
                    String wins_money = item.child("wins_money").getValue().toString().trim();
                    String bets_money = item.child("bets_money").getValue().toString().trim();
                    String biggest_bet = item.child("biggest_bet").getValue().toString().trim();
                    String biggest_win = item.child("biggest_win").getValue().toString().trim();
                    String last_online = item.child("last_online").getValue().toString().trim();
                    String avg_bet = item.child("avg_bet").getValue().toString().trim();
                    String User_id = item.getKey();
                    String email = item.child("email").getValue().toString().trim();
                    User tmp_user = new User(name,balance,games,wins,wins_money,bets_money,biggest_bet,biggest_win,User_id,last_online,email,avg_bet);
                    map.put(name,tmp_user);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        user_stats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                full_text.setText("");
                list_view1.setVisibility(View.VISIBLE);
                addToList();
                send_bonus.setVisibility(View.VISIBLE);
                send_bonus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int u_balance = Integer.parseInt(UserBalance) + 200;
                        reference.child(UserID).child("balance").setValue("" + u_balance);
                        Properties props = new Properties();
                        props.put("mail.smtp.auth", "true");
                        props.put("mail.smtp.starttls.enable","true");
                        props.put("mail.smtp.host","smtp.gmail.com");
                        props.put("mail.smtp.port","587");
                        Session session = Session.getInstance(props, new javax.mail.Authenticator(){
                            @Override
                            protected PasswordAuthentication getPasswordAuthentication() {
                                return new PasswordAuthentication(EMAIL, PASS);
                            }
                        });
                        try{
                            Message message = new MimeMessage(session);
                            message.setFrom(new InternetAddress("EMAIL"));
                            message.setRecipients(MimeMessage.RecipientType.TO,  InternetAddress.parse(User_Email));
                            message.setSubject("Long time no see :(");
                            String str = "Hello "+User_Name+",\nWe noticed that you haven't played since "+User_Date+".\n" +
                                    "We would like to see you again soon, here's 200$ gift ";
                            message.setText(str);
                            if (android.os.Build.VERSION.SDK_INT > 9) {
                                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                                StrictMode.setThreadPolicy(policy);
                            }
                            Transport.send(message);
                        }
                        catch (MessagingException e){
                            throw new RuntimeException(e);
                        }
                    }
                });
            }
        });
        game_stats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list_view1.setVisibility(View.GONE);
                boss_reference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        games = Integer.parseInt(snapshot.child("games").getValue().toString());
                        balance = snapshot.child("balance").getValue().toString();
                        lost_money = snapshot.child("money_spent").getValue().toString();
                        String Boss_avg_bet = snapshot.child("avg_bet").getValue().toString();
                        users_num = map.size();
                        wins = Integer.parseInt(snapshot.child("wins").getValue().toString());
                        wins_rate = (wins*100 )/ games;
                        int l_money = Integer.parseInt(lost_money);
                        average_lost_per_win = l_money/wins;
                        int min = 9999;
                        int max = 0;
                        uncommon_nums = "most uncommon numbers: ";
                        common_nums = "most common numbers: ";
                        for(int i=0; i<37; i++){
                            int tmp = Integer.parseInt(snapshot.child("bets").child(""+i).getValue().toString());
                            nums[i] = tmp;
                            if(tmp>max){
                                max = tmp;
                            }
                            if(tmp<min){
                                min = tmp;
                            }
                        }

                        for(int i=0; i<37; i++){
                            if(nums[i] == max){
                                common_nums += i+", ";
                            }
                            if(nums[i] == min){
                                uncommon_nums += i+", ";
                            }
                        }
                        uncommon_nums += "drawn "+min+" times";
                        common_nums += "drawn "+max+" times";

                        String text = "Num of users: " + users_num + "\n" + "Roulette balance: " + balance + "$\n" +
                                "Num of games: " +games + "\n" + "Num of wins: " + wins + "\n" + common_nums + "\n" +
                                uncommon_nums  + "\n" + "Lost money: "+ lost_money + "$\n" + "Wins rate: "+ wins_rate + "%\n" +
                                "Average lost per win: "+average_lost_per_win+ "\nAverage earn per round: "+Boss_avg_bet;
                        full_text.setText(text);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });
    }

    public void addToList(){
        String [] names = new String[map.size()];
        int i = 0;
        for(User tmp : map.values()){
            names[i] = tmp.full_name;
            i++;
        }
        ArrayAdapter adapter = new ArrayAdapter(boss.this, android.R.layout.simple_list_item_1,names);
        list_view1.setAdapter(adapter);
        list_view1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String user_name = names[position];
                User tmp = map.get(names[position]);
                String [] user_details = new String[11];
                user_details[0] = "User ID: "+tmp.User_id + "\n";
                user_details[1] = "Full name: "+tmp.full_name + "\n";
                user_details[2] = "Balance: "+tmp.balance + "\n";
                user_details[3] = "Games: "+tmp.games + "\n";
                user_details[4] = "Wins "+tmp.wins + "\n";
                user_details[5] = "Wins money: "+tmp.wins_money + "\n";
                user_details[6] = "Bets money: "+tmp.bets_money + "\n";
                user_details[7] = "Biggest win: "+tmp.biggest_win + "\n";
                user_details[8] = "Biggest bet: "+tmp.biggest_bet + "\n";
                user_details[9] = "Last online: "+tmp.last_online + "\n";
                user_details[10] = "Average bet: "+tmp.avg_bet;
                UserID = tmp.User_id;
                UserBalance = tmp.balance;
                User_Date = tmp.last_online;
                User_Name = tmp.full_name;
                User_Email = tmp.email;
                String text = "";
                for (int i = 1; i < user_details.length; i++)
                    text += user_details[i];
                full_text.setText(text);
            }
        });
    }
}