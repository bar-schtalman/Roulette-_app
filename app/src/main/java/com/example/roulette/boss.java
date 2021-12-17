package com.example.roulette;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class boss extends AppCompatActivity {
    private ListView list_view1;
    private DatabaseReference reference;
    private TextView nameT,balanceT,gamesT,winsT,wins_moneyT,bets_moneyT,biggest_betT,biggest_winT;
    ArrayList<String> list = new ArrayList<>();
    String str = "";
    String name = " ";
    HashMap<String, User> map = new HashMap<String, User>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_boss);
        reference = FirebaseDatabase.getInstance().getReference("Users");
        list_view1 = findViewById(R.id.list_view);
        nameT = findViewById(R.id.full_name_textview);
        balanceT = findViewById(R.id.balance_textview);
        gamesT = findViewById(R.id.games_textview);
        winsT = findViewById(R.id.wins_textview);
        wins_moneyT = findViewById( R.id.wins_money_textview);
        bets_moneyT = findViewById(R.id.bets_money_textview);
        biggest_betT = findViewById(R.id.biggest_bet_textview);
        biggest_winT = findViewById(R.id.biggest_win_textview);
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
                    String User_id = item.getKey();
                    User tmp_user = new User(name,balance,games,wins,wins_money,bets_money,biggest_bet,biggest_win,User_id);
                    System.out.println(tmp_user.full_name);
                    map.put(name,tmp_user);
                }
                addToList();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

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
                String [] user_details = new String[9];
                user_details[0] = "User ID: "+tmp.User_id ;
                user_details[1] = "Full name: "+tmp.full_name ;
                user_details[2] = "Balance: "+tmp.balance ;
                user_details[3] = "Games: "+tmp.games ;
                user_details[4] = "Wins "+tmp.wins ;
                user_details[5] = "Wins money: "+tmp.wins_money ;
                user_details[6] = "Bets money: "+tmp.bets_money ;
                user_details[7] = "Biggest win: "+tmp.biggest_win ;
                user_details[8] = "Biggest bet: "+tmp.biggest_bet ;
                nameT.setText(user_details[1]);
                balanceT.setText(user_details[2]);
                gamesT.setText(user_details[3]);
                winsT.setText(user_details[4]);
                wins_moneyT.setText(user_details[5]);
                bets_moneyT.setText(user_details[6]);
                biggest_winT.setText(user_details[7]);
                biggest_betT.setText(user_details[8]);



            }
        });
    }
}