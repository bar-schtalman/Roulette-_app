package com.example.roulette;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.admin.DeviceAdminService;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Table extends AppCompatActivity {
    private TextView textView,user_amount,bet_view;
    private Button spin,bet,profile;
    private ImageView roulette_image;
    private Random r;
    private int degree ,new_amount, win ;
    private static int NUMBER;
    private boolean isSpinning = false;
    private FirebaseUser user;
    private DatabaseReference reference,boss_reference;
    private String UserID,str_bets;
    private static final String [] sectors = {"32 red","15 black","19 red","4 black","21 red","2 black",
            "25 red","17 black","34 red","6 black","27 red","13 black","36 red","11 black","30 red","8 black",
            "23 red","10 black","5 red","24 black","16 red","33 black","1 red","20 black","14 red","31 black",
            "9 red","22 black","18 red","29 black","7 red","28 black","12 red","35 black","3 red","0"};
    private static final int [] numbers = {32,15,19,4,21,2,25,17,34,6,27,13,36,11,30,8,23,10,5,24,
                                            16,33,1,20,14,31,9,22,18,29,7,28,12,35,3,26,0};
    private static final int [] sectorsDegrees = new int [sectors.length];
    private static  final Random random = new Random();
     @Override
    protected void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
         getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
         setContentView(R.layout.activity_table);
////////////////////////////////////////////
         boss_reference = FirebaseDatabase.getInstance().getReference("Boss");
         roulette_image = findViewById(R.id.imageView) ;
         bet_view = findViewById(R.id.User_bet);
         reference = FirebaseDatabase.getInstance().getReference("Users");
         user = FirebaseAuth.getInstance().getCurrentUser();
         user_amount =  findViewById(R.id.user_amount_play);
         UserID = user.getUid();
         textView = findViewById(R.id.textView7) ;
         spin = findViewById(R.id.spin);
         str_bets = "";
         win = 0;

                reference.child(UserID).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        //set user balance Textview
                        user_amount.setText(snapshot.child("balance").getValue().toString());
                        //adding bets to bet view textview
                        for(int i = 0; i<37; i++){
                            if(Integer.parseInt(snapshot.child("bet").child(""+i).getValue().toString()) > 0) {
                                str_bets += i +" ";
                            }
                        }
                        if(Integer.parseInt(snapshot.child("bet").child("odd").getValue().toString()) > 0){str_bets += "odd" +" ";}
                        if(Integer.parseInt(snapshot.child("bet").child("even").getValue().toString()) > 0){str_bets += "even" +" ";}
                        if(Integer.parseInt(snapshot.child("bet").child("red").getValue().toString()) > 0){str_bets += "red" +" ";}
                        if(Integer.parseInt(snapshot.child("bet").child("black").getValue().toString()) > 0){str_bets += "black" +" ";}
                        if(Integer.parseInt(snapshot.child("bet").child("high").getValue().toString()) > 0){str_bets += "19-36" +" ";}
                        if(Integer.parseInt(snapshot.child("bet").child("low").getValue().toString()) > 0){str_bets += "1-18" +" ";}
                        bet_view.setText(str_bets);

                    }
            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        });
                getDegree();
        spin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //check if user placed bet
                if(bet_view.getText().toString().isEmpty() || bet_view.getText().toString().equals("place bet to play")){
                    Toast.makeText(Table.this,"place a bet to play",Toast.LENGTH_SHORT).show();
                    return;
                }
                //spin
                if(!isSpinning){
                    spin();
                    isSpinning = true;
                }
            }

            private void spin() {
                //image rotation
                degree = random.nextInt(sectors.length - 1);
                RotateAnimation rotate = new RotateAnimation(0, (360 * sectors.length) + sectorsDegrees[degree],
                        RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f);
                rotate.setDuration(3600);
                rotate.setFillAfter(true);
                rotate.setInterpolator(new DecelerateInterpolator());
                rotate.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) { }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        boss_reference.addListenerForSingleValueEvent(new ValueEventListener() {
                            //update the games number for the boos
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                int games = Integer.parseInt(snapshot.child("games").getValue().toString())+1;
                                boss_reference.child("games").setValue(""+games);
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError error) { }
                        });
                        //set and show the drawn number
                        String number = sectors[sectors.length - (degree + 1)];
                        textView.setText(number);
                        NUMBER = numbers[sectors.length - (degree + 1)];

                        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Users");
                        reference.child(UserID).addListenerForSingleValueEvent(new ValueEventListener() {
                            //check if user win
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                //check the money user bet on the drawn number and stats update
                                String str2 = snapshot.child("bet").child(""+NUMBER).getValue().toString();
                                int user_games = Integer.parseInt(snapshot.child("games").getValue().toString()) + 1;
                                reference.child(UserID).child("games").setValue(""+user_games);
                                win = Integer.parseInt(str2)*32;
                                if(NUMBER > 0) {
                                    if (NUMBER % 2 == 0) {
                                        win += 2 * Integer.parseInt(snapshot.child("bet").child("even").getValue().toString().trim());
                                    }
                                    else{
                                        win += 2 * Integer.parseInt(snapshot.child("bet").child("odd").getValue().toString().trim());
                                    }

                                    if(isRed(NUMBER)){
                                        win += 2 * Integer.parseInt(snapshot.child("bet").child("red").getValue().toString().trim());
                                    }
                                    else{
                                        win += 2 * Integer.parseInt(snapshot.child("bet").child("black").getValue().toString().trim());
                                    }
                                    if(NUMBER> 18 && NUMBER <=36){
                                        win += 2 * Integer.parseInt(snapshot.child("bet").child("high").getValue().toString().trim());
                                    }
                                    else{
                                        win += 2 * Integer.parseInt(snapshot.child("bet").child("low").getValue().toString().trim());

                                    }
                                }
                                //if user wins money, updates user balance and stats
                                if(win > 0 ){
                                    int user_wins = Integer.parseInt(snapshot.child("wins").getValue().toString().trim()) + 1;
                                    int user_wins_money = Integer.parseInt(snapshot.child("wins_money").getValue().toString()) + win;
                                    reference.child(UserID).child("wins").setValue(""+user_wins);
                                    reference.child(UserID).child("wins_money").setValue(""+user_wins_money);
                                    new_amount = win + Integer.parseInt(snapshot.child("balance").getValue().toString());
                                    reference.child(UserID).child("balance").setValue(""+new_amount);
                                    Toast.makeText(Table.this,"win!!!, your prize is "+win+"$",Toast.LENGTH_LONG).show();

                                    boss_reference.addListenerForSingleValueEvent(new ValueEventListener() {
                                        //update boss balance and stats
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            long bos_balance = Long.parseLong(snapshot.child("balance").getValue().toString());
                                            long new_sum = bos_balance - win;;
                                            int wins_count = Integer.parseInt(snapshot.child("wins").getValue().toString()) +1;
                                            boss_reference.child("wins").setValue(""+wins_count);
                                            boss_reference.child("balance").setValue(""+new_sum);
                                            int money_won = Integer.parseInt(snapshot.child("money_spent").getValue().toString()) + win;
                                            boss_reference.child("money_spent").setValue(""+money_won);
                                            user_amount.setText(""+new_amount);
                                            bet_view.setText("place bet to play");

                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) { }
                                    });
                                    startActivity(new Intent(Table.this,Winner_screen.class));
                                }
                                else{
//                                    Toast.makeText(Table.this,"LOSER!",Toast.LENGTH_LONG).show();
                                    bet_view.setText("place bet to play");
                                    startActivity(new Intent(Table.this,Loser_screen.class));

                                }
                                boss_reference.addListenerForSingleValueEvent(new ValueEventListener() {
                                    //update games stats
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        int new_val = Integer.parseInt(snapshot.child("bets").child(""+NUMBER).getValue().toString().trim()) + 1;
                                        boss_reference.child("bets").child(""+NUMBER).setValue(""+new_val);
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) { }
                                });
                                //reset user bets data in firebase
                                for(int i = 0; i<37; i++){
                                    reference.child(UserID).child("bet").child(""+i).setValue("0");
                                }
                                reference.child(UserID).child("bet").child("odd").setValue("0");
                                reference.child(UserID).child("bet").child("even").setValue("0");
                                reference.child(UserID).child("bet").child("red").setValue("0");
                                reference.child(UserID).child("bet").child("black").setValue("0");
                                reference.child(UserID).child("bet").child("high").setValue("0");
                                reference.child(UserID).child("bet").child("low").setValue("0");
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError error) { }
                        });
                        isSpinning = false;
                    }
                    @Override
                    public void onAnimationRepeat(Animation animation) { }
                });
                roulette_image.startAnimation(rotate);
            }
        });
            bet = findViewById(R.id.bet);
            bet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(Table.this,betTable.class));
                }
            });
            profile = findViewById(R.id.profile_btn);
            profile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(Table.this,user_bio.class));
                }
            });

    }
    private void getDegree(){
        int sectorDegree = 360/sectors.length;
        for (int i=0 ; i< sectors.length; i++){
            sectorsDegrees[i] = (i+1) * sectorDegree;
        }

    }

    private boolean isRed(int n){
            if( n == 1|| n == 3|| n == 5||n == 7||n == 9||n == 12||n == 14||n == 16||n == 18||n == 19||
                n == 21||n == 23||n == 25|| n == 27 || n == 30|| n == 32|| n == 34|| n == 36){
                return true;
         }
            else{
                return false;
            }
    }
}
