package com.example.roulette;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Date;

public class user_bio extends AppCompatActivity {
    private Button logout, withdrawal;
    private FirebaseUser user;
    private DatabaseReference reference;
    private TextView wins_rate_title,wins_rate_t,welcomeMSG, full_name, balancee, email_user,user_games, user_wins, user_wins_money
            , user_bets_money,user_biggest_win, user_biggest_bet,avg_bet_t;
    private String UserID;
    private Dialog dialog;
    private int wins_rate;
    private Button deposit,edit,play,face;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_user_bio);
        wins_rate_t = findViewById(R.id.wins_rate_display);


        deposit = findViewById(R.id.depositBTN);
        dialog = new Dialog(this);
        deposit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(user_bio.this,deposit.class));
            }
        });
        wins_rate_title = findViewById(R.id.user_wins_rate);
        avg_bet_t = findViewById(R.id.avg_bet_amount);
        logout = findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                lp.copyFrom(dialog.getWindow().getAttributes());
                lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                lp.height = WindowManager.LayoutParams.MATCH_PARENT ;
                int width = (int)(getResources().getDisplayMetrics().widthPixels*1);
                int height = (int)(getResources().getDisplayMetrics().heightPixels*0.40);
                dialog.show();
                dialog.getWindow().setLayout(width,height);
                dialog.setContentView(R.layout.costume_dialog);
                Button ok = dialog.findViewById(R.id.ok);
                Button cancel = dialog.findViewById(R.id.cancel);
                TextView textView = dialog.findViewById(R.id.msg_box);
                textView.setText("Are you sure you want to Log out?");
                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        FirebaseAuth.getInstance().signOut();
                        startActivity(new Intent(user_bio.this,UserLogIn.class));
                        finish();
                        dialog.dismiss();
                    }
                });
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

            }
        });
        face = findViewById(R.id.face);
        face.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(user_bio.this,faces.class));
            }
        });

        withdrawal = findViewById(R.id.withdrawal);
        withdrawal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(user_bio.this,withdrawal.class));
            }
        });

        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        UserID = user.getUid();
        Date date = new Date();
        reference.child(UserID).child("last_online").setValue(date.toString());
        welcomeMSG = findViewById(R.id.welcome);
//        full_name = findViewById(R.id.first_name_display);
//        email_user = findViewById(R.id.email_display);
        user_bets_money = findViewById(R.id.user_bets_money_display);
        user_wins_money = findViewById(R.id.user_wins_money_display);
        user_wins = findViewById(R.id.user_wins_display);
        user_games = findViewById(R.id.user_games_display);
        balancee = findViewById(R.id.balance);

        reference.child(UserID).addListenerForSingleValueEvent(new ValueEventListener() {
            //adding the first name to welcome text editor
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                    String ful_name = snapshot.child("full_name").getValue().toString();
                    String email = snapshot.child("email").getValue().toString();
                    String games = snapshot.child("games").getValue().toString();
                    String wins_money = snapshot.child("wins_money").getValue().toString();
                    String wins = snapshot.child("wins").getValue().toString();
                    String bets_money = snapshot.child("bets_money").getValue().toString();
                    String balance = snapshot.child("balance").getValue().toString();
                    int name = 0;
                    for(int i = 0 ; i < ful_name.length(); i++){
                        if(Character.toString(ful_name.charAt(i)).equals(" ") ){
                            name = i;
                            break;

                        }
                    }
                    welcomeMSG.setText("welcome    " +ful_name.substring(0,name) );
                    user_bets_money.setText(bets_money+"$");
                    user_games.setText(games);
                    user_wins.setText(wins);
                    user_wins_money.setText(wins_money+"$");
                    int tmp_games = Integer.parseInt(games);
                    int tmp_wins = Integer.parseInt(wins);
                    String avg = snapshot.child("avg_bet").getValue().toString().trim();
                    avg_bet_t.setText(avg);
                    if( tmp_games>0) {
                        wins_rate_title.setText("wins rate");
                        int tmp = (tmp_wins * 100) / tmp_games;
                        wins_rate_t.setText(tmp + "%");
                    }
                    else{
                        wins_rate_t.setVisibility(View.GONE);
                    }
//                    full_name.setText(ful_name);;
//                    email_user.setText(email);
                    balancee.setText(String.valueOf(balance)+"$");
                    for(int i = 0; i<37; i++){
                        reference.child(UserID).child("bet").child(""+i).setValue("0");
                    }

                }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        edit = findViewById(R.id.editProfile);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(user_bio.this,EditProfile.class));

            }
        });


        play = findViewById(R.id.play);
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(user_bio.this,betTable.class));
            }
        });


    }



}