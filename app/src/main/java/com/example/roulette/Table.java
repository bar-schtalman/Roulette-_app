package com.example.roulette;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.admin.DeviceAdminService;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Random;

public class Table extends AppCompatActivity {
    TextView textView, textView12,user_amount,user_bet;
    Button spin,bet;
    EditText bet_amount;
    ImageView imageView;
    Random r;
    private int degree, old_degree;
    private NumberPicker numberPicker;
    private static int NUMBER;
    private boolean win = false;
    private boolean isSpinning = false;
    private FirebaseUser user;
    private DatabaseReference reference;
    // 0 32 15 19 4 21 2 25 17 34 6 27 13 36 11 30 8 23 10 5 24 16 33 1 20 14 31 9 22 18 29 7 28 12 35 3 26
    private static final String [] sectors = {"32 red","15 black","19 red","4 black","21 red","2 black",
            "25 red","17 black","34 red","6 black","27 red","13 black","36 red","11 black","30 red","8 black",
            "23 red","10 black","5 red","24 black","16 red","33 black","1 red","20 black","14 red","31 black",
            "9 red","22 black","18 red","29 black","7 red","28 black","12 red","35 black","3 red","0"};
    private static final int [] numbers = {32,15,19,4,21,2,25,17,34,6,27,13,36,11,30,8,23,10,5,24,
                                            16,33,1,20,14,31,9,22,18,29,7,28,12,35,3,26,0};
    private static final int [] sectorsDegrees = new int [sectors.length];
    private HashMap<Integer,Integer> MAP;
    private  String BET_STRING,UserID;
    private static  final Random random = new Random();
//    private static final float FACTOR =4.86f;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table);
        BET_STRING = "";
        reference = FirebaseDatabase.getInstance().getReference("Users");
        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        UserID = user.getUid();
        MAP = new HashMap<Integer,Integer>();

        textView12 = (TextView)findViewById(R.id.textView12);

        spin = (Button) findViewById(R.id.spin);
        imageView = (ImageView) findViewById(R.id.imageView);
        textView = (TextView) findViewById(R.id.textView7);
        numberPicker = (NumberPicker)findViewById(R.id.numberPicker);
        numberPicker.setMinValue(0);
        numberPicker.setMaxValue(36);
        bet_amount = (EditText)findViewById(R.id.editTextNumber3);
        user_amount = (TextView) findViewById(R.id.userAmount);
        user_bet = (TextView) findViewById(R.id.user_bet);
        reference.child(UserID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user1 = snapshot.getValue(User.class);
                if(user1 != null){
                    long amount = Long.parseLong(user1.balance);
                    user_amount.setText(user1.balance);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        getDegree();
        r = new Random();

        spin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isSpinning){
                    spin();
                    isSpinning = true;
                }
            }

            private void spin() {
                degree = random.nextInt(sectors.length-1);
                RotateAnimation rotate = new RotateAnimation(0,(360*sectors.length)+sectorsDegrees[degree],
                        RotateAnimation.RELATIVE_TO_SELF,0.5f,RotateAnimation.RELATIVE_TO_SELF,0.5f);
                rotate.setDuration(3600);
                rotate.setFillAfter(true);
                rotate.setInterpolator(new DecelerateInterpolator());
                rotate.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        String number = sectors[sectors.length-(degree +1)];
                        textView.setText(number);
                        NUMBER = numbers[sectors.length-(degree +1)];
                        if(MAP.containsKey(NUMBER)){
                            win = true;
                            int prize = 2*MAP.get(NUMBER);
                            Toast.makeText(Table.this,"win! your prize is "+prize,Toast.LENGTH_LONG).show();
                            reference.child(UserID).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    User user1 = snapshot.getValue(User.class);
                                    long user_balance = Long.parseLong(user1.balance);
                                    if(user1 != null){
                                        if(win){
                                            long new_amount = user_balance + prize;
                                            reference.child(UserID).child("balance").setValue(""+new_amount);
                                            user1.balance = ""+new_amount;
                                            BET_STRING = "";
                                            MAP.clear();
                                            win = false;

                                        }
                                        else{
                                            Toast.makeText(Table.this,"try again",Toast.LENGTH_LONG).show();

                                        }
                                    }

                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                        }



                        isSpinning = false;


                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                imageView.startAnimation(rotate);



            }
        });
            bet = (Button) findViewById(R.id.bet);
            bet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                   int number = numberPicker.getValue();
                   String str = bet_amount.getText().toString().trim();
                   int amount = Integer.parseInt(str);
                   MAP.put(number,amount);
                   BET_STRING += number+" - "+amount+" | ";
                   user_bet.setText(BET_STRING);
                   reference.child(UserID).addListenerForSingleValueEvent(new ValueEventListener() {
                       @Override
                       public void onDataChange(@NonNull DataSnapshot snapshot) {
                           User user1 = snapshot.getValue(User.class);
                           if(user1 != null){
                               long user_balance = Long.parseLong(user1.balance);
                               user_balance -= amount;
                               user1.balance = ""+user_balance;
                               reference.child(UserID).child("balance").setValue(user1.balance);
                               user_amount.setText(user1.balance);
                           }

                       }

                       @Override
                       public void onCancelled(@NonNull DatabaseError error) {

                       }
                   });


                }
            });




    }

    private void getDegree(){
        int sectorDegree = 360/sectors.length;
        for (int i=0 ; i< sectors.length; i++){
            sectorsDegrees[i] = (i+1) * sectorDegree;
        }

    }




}
