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

public class user_bio extends AppCompatActivity {
    private Button logout, withdrawal;
    private FirebaseUser user;
    private DatabaseReference reference;
    private TextView welcomeMSG, full_name, balancee, email_user,user_games, user_wins, user_wins_money
            , user_bets_money,user_biggest_win, user_biggest_bet;
    private String UserID;
    Dialog dialog;
    private Button deposit,edit,play,face;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_user_bio);

        deposit = findViewById(R.id.depositBTN);
        dialog = new Dialog(this);
        deposit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(user_bio.this,deposit.class));
            }
        });
        logout = findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(user_bio.this,UserLogIn.class));
                finish();
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
//                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
//                lp.copyFrom(dialog.getWindow().getAttributes());
//                lp.width = WindowManager.LayoutParams.MATCH_PARENT;
//                lp.height = WindowManager.LayoutParams.MATCH_PARENT ;
//                int width = (int)(getResources().getDisplayMetrics().widthPixels*0.82);
//                int height = (int)(getResources().getDisplayMetrics().heightPixels*0.85);
//
//
//                dialog.show();
//                dialog.getWindow().setLayout(width,height);
//                dialog.setContentView(R.layout.activity_edit_profile);
//                Button edit_exit = dialog.findViewById(R.id.exit_btn_4);
//                edit_exit.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        dialog.dismiss();
//                    }
//                });
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