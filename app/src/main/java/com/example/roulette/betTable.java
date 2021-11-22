package com.example.roulette;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

public class betTable extends AppCompatActivity {
    int [] objectBet = new int[37];


    private Button b1 ,b2 ,b3 ,b4 ,b5 ,b6 ,b7 ,b8 ,b9 ,b10 ,b11 ,b0 ,b12 ,b13 ,b14 ,b15 ,b16 ,b17 ,
            b18 ,b19,b20 ,b21 ,b22 ,b23 ,b24 ,b25 ,b26 ,b27 ,b28 ,b29 ,b30 ,b31 ,b32 ,b33 ,b34 ,b35
            ,b36,btn5,btn25,btn100,btn500, btn1000,submit;
    public int [] MAP = new int [37];
    private TextView bet_amount,user_amount,user_bet,selected_chip,test_text;
    String BET_STRING,UserID,test;
    private FirebaseUser user,user2;
    private DatabaseReference reference,reference2;
    private static int CHIP = 0,BET_SUM = 0;
    int old_val;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bet_table);
        ////////////////////////////////////////////////////////////
        reference = FirebaseDatabase.getInstance().getReference("Users");
        user = FirebaseAuth.getInstance().getCurrentUser();
        user2 = FirebaseAuth.getInstance().getCurrentUser();
        UserID = user.getUid();
        ///////////////////////////////////////////////////////////
        BET_STRING = "";
        test = "";
        submit = (Button)findViewById(R.id.submit_bet);
        selected_chip = (TextView)findViewById(R.id.textView17);
        user_bet = (TextView)findViewById(R.id.user_bet);
        BET_SUM = 0;
        bet_amount = (TextView)findViewById(R.id.betsumtextview);
        b0 = (Button) findViewById(R.id.b0);
        b0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MAP[0] += CHIP;

            }
        });
        b1 = (Button) findViewById(R.id.b1);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MAP [1] += CHIP;
            }
        });
        b2 = (Button) findViewById(R.id.b2);
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MAP [2] += CHIP;
            }
        });
        b3 = (Button) findViewById(R.id.b3);
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MAP [3] += CHIP;
            }
        });
        b4 = (Button) findViewById(R.id.b4);
        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MAP [4] += CHIP;
            }
        });
        b5 = (Button) findViewById(R.id.b5);
        b5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MAP [5] += CHIP;
            }
        });
        b6 = (Button) findViewById(R.id.b6);
        b6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MAP [6] += CHIP;
            }
        });
        b7 = (Button) findViewById(R.id.b7);
        b7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MAP [7] += CHIP;
            }
        });
        b8 = (Button) findViewById(R.id.b8);
        b8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MAP [8] += CHIP;
            }
        });
        b9 = (Button) findViewById(R.id.b9);
        b9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MAP [9] += CHIP;
            }
        });
        b10 = (Button) findViewById(R.id.b10);
        b10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MAP [10] += CHIP;
            }
        });
        b11 = (Button) findViewById(R.id.b11);
        b11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MAP [11] += CHIP;
            }
        });
        b12 = (Button) findViewById(R.id.b12);
        b12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MAP [12] += CHIP;
            }
        });
        b13 = (Button) findViewById(R.id.b13);
        b13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MAP [13] += CHIP;
            }
        });
        b14 = (Button) findViewById(R.id.b14);
        b14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MAP [14] += CHIP;
            }
        });
        b15 = (Button) findViewById(R.id.b15);
        b15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MAP [15] += CHIP;
            }
        });
        b16 = (Button) findViewById(R.id.b16);
        b16.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MAP [16] += CHIP;
            }
        });
        b17 = (Button) findViewById(R.id.b17);
        b17.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MAP [17] += CHIP;
            }
        });
        b18 = (Button) findViewById(R.id.b18);
        b18.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MAP [18] += CHIP;
            }
        });
        b19 = (Button) findViewById(R.id.b19);
        b19.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MAP [19] += CHIP;
            }
        });
        b20 = (Button) findViewById(R.id.b20);
        b20.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MAP [20] += CHIP;
            }
        });
        b21 = (Button) findViewById(R.id.b21);
        b21.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MAP [21] += CHIP;
            }
        });
        b22 = (Button) findViewById(R.id.b22);
        b22.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MAP [22] += CHIP;
            }
        });
        b23 = (Button) findViewById(R.id.b23);
        b23.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MAP [23] += CHIP;
            }
        });
        b24 = (Button) findViewById(R.id.b24);
        b24.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MAP [24] += CHIP;
            }
        });
        b25 = (Button) findViewById(R.id.b25);
        b25.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MAP [25] += CHIP;
            }
        });
        b26 = (Button) findViewById(R.id.b26);
        b26.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MAP [26] += CHIP;
            }
        });
        b27 = (Button) findViewById(R.id.b27);
        b27.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MAP [27] += CHIP;
            }
        });
        b28 = (Button) findViewById(R.id.b28);
        b28.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MAP [28] += CHIP;
            }
        });
        b29 = (Button) findViewById(R.id.b29);
        b29.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MAP [29] += CHIP;
            }
        });
        b30 = (Button) findViewById(R.id.b30);
        b30.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MAP [030] += CHIP;
            }
        });
        b31 = (Button) findViewById(R.id.b31);
        b31.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MAP [31] += CHIP;
            }
        });
        b32 = (Button) findViewById(R.id.b32);
        b32.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MAP [32] += CHIP;
            }
        });
        b33 = (Button) findViewById(R.id.b33);
        b33.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MAP [33] += CHIP;
            }
        });
        b34 = (Button) findViewById(R.id.b34);
        b34.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MAP [34] += CHIP;
            }
        });
        b35 = (Button) findViewById(R.id.b35);
        b35.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MAP [35] += CHIP;
            }
        });
        b36 = (Button) findViewById(R.id.b36);
        b36.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MAP [36] += CHIP;
            }
        });

        btn5 = (Button)findViewById(R.id.bet5);
        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CHIP = 5;
                selected_chip.setText("5");
            }
        });
        btn25 = (Button)findViewById(R.id.bet25);
        btn25.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CHIP=25;
                selected_chip.setText("25");
            }
        });
        btn100 = (Button)findViewById(R.id.bet100);
        btn100.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CHIP=100;
                selected_chip.setText("100");
            }
        });
        btn500 = (Button)findViewById(R.id.bet500);
        btn500.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CHIP=500;
                selected_chip.setText("500");
            }
        });
        btn1000 = (Button) findViewById(R.id.bet1000);
        btn1000.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CHIP=1000;
                selected_chip.setText("1000");
            }
        });
        user_amount = (TextView) findViewById(R.id.user_amount_bet);
        ///////////////////////////////////////////////////////////
        test_text = (TextView)findViewById(R.id.user_amount_play);
        reference = FirebaseDatabase.getInstance().getReference("Users");
        user = FirebaseAuth.getInstance().getCurrentUser();
        UserID = user.getUid();
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

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reference.child(UserID).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        int sum = 0;
                        User user1 = snapshot.getValue(User.class);

                        if(user1 != null){

                            reference.child(UserID).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    User user1 = snapshot.getValue(User.class);
                                    if ( user1 != null){
                                        for(int i = 0 ; i< 37 ; i++){
                                            reference.child(UserID).child("bet").child(""+i).setValue(MAP[i]);
                                            BET_SUM+=MAP[i];
                                        }
                                        long balance = Long.parseLong(user1.balance) - (long)BET_SUM;
                                        reference.child(UserID).child("balance").setValue(String.valueOf(balance));
                                    }

                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });

                            startActivity(new Intent(betTable.this,Table.class));
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });
    }

}