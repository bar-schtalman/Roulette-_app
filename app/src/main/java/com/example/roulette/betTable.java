package com.example.roulette;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.WindowManager;
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

    private Button b1 ,b2 ,b3 ,b4 ,b5 ,b6 ,b7 ,b8 ,b9 ,b10 ,b11 ,b0 ,b12 ,b13 ,b14 ,b15 ,b16 ,b17 ,
            b18 ,b19,b20 ,b21 ,b22 ,b23 ,b24 ,b25 ,b26 ,b27 ,b28 ,b29 ,b30 ,b31 ,b32 ,b33 ,b34 ,b35
            ,b36,btn5,btn25,btn100,btn500, btn1000,submit,reset_btn,calculator,odd,even,red,black,low,high;
    public int [] MAP = new int [43];
    private TextView bet_amount,user_amount,selected_chip,test_text,final_bet_txt;
    String BET_STRING,UserID;
    private FirebaseUser user;
    public long BALANCE;
    private DatabaseReference reference,boss_reference;
    private static int CHIP = 0,BET_SUM = 0, ADD_SUM = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_bet_table);
        ////////////////////////////////////////////////////////////

        reference = FirebaseDatabase.getInstance().getReference("Users");
        user = FirebaseAuth.getInstance().getCurrentUser();
        UserID = user.getUid();
        boss_reference = FirebaseDatabase.getInstance().getReference("Boss");

        ///////////////////////////////////////////////////////////
        BET_STRING = "";

        submit = (Button)findViewById(R.id.submit_bet);
        selected_chip = (TextView)findViewById(R.id.textView17);
        ADD_SUM = 0;
        BET_SUM = 0;
        bet_amount = (TextView)findViewById(R.id.betsumtextview);

        /////////////////////////////////////////////////////////////
        user_amount = (TextView) findViewById(R.id.user_amount_bet);
        test_text = (TextView)findViewById(R.id.user_amount_play);

        reference = FirebaseDatabase.getInstance().getReference("Users");
        user = FirebaseAuth.getInstance().getCurrentUser();
        UserID = user.getUid();

        reference.child(UserID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                user_amount.setText(snapshot.child("balance").getValue().toString().trim());
                }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        reset_btn = findViewById(R.id.reset);
        reset_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i< 43; i++){
                    MAP[i] = 0;
                }
                BET_SUM = 0;
                bet_amount.setText(""+BET_SUM);
                final_bet_txt.setText("");
            }
        });
        final_bet_txt = findViewById(R.id.final_bet);

        calculator = findViewById(R.id.calculate);
        calculator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String show = " ";
                for (int i = 0; i< 37 ; i++) {
                    if (MAP[i] > 0) {
                        show += i + "->" + MAP[i] + "$  ";

                    }
                }
                if(MAP [37] >0){
                    show += "ODD->" +MAP[37] +"$  ";
                }
                if(MAP[38] > 0){
                    show += "EVEN->" + MAP[38]+"$  ";
                }
                if(MAP[39] > 0){
                    show += "RED->" + MAP[39]+"$  ";
                }
                if(MAP[40] > 0){
                    show += "BLACK->" + MAP[40]+"$  ";
                }
                if(MAP[41] > 0){
                    show += "19-36->" + MAP[41]+"$  ";
                }
                if(MAP[42] > 0){
                    show += "1-18->" + MAP[42]+"$  ";
                }

                    final_bet_txt.setText(show.substring(0,show.length()-2));
                    final_bet_txt.setMovementMethod(new ScrollingMovementMethod());
                }

        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reference.child(UserID).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        int sum = 0;
                        if(BET_SUM > Integer.parseInt(snapshot.child("balance").getValue().toString())){
                            BET_SUM = 0;
                            for(int i = 0; i<37; i++){
                                MAP[i] = 0;
                                Toast.makeText(betTable.this,"Error, not enough money,enter new bet",Toast.LENGTH_SHORT).show();
                            }
                        }
                        else if( BET_SUM == 0){

                            Toast.makeText(betTable.this,"place a bet to play",Toast.LENGTH_SHORT).show();
                        }
                        else{
                            for(int i = 0 ; i< 37 ; i++){
                                reference.child(UserID).child("bet").child(""+i).setValue(MAP[i]);
                            }
                            reference.child(UserID).child("bet").child("odd").setValue(""+MAP[37]);
                            reference.child(UserID).child("bet").child("even").setValue(""+MAP[38]);
                            reference.child(UserID).child("bet").child("red").setValue(""+MAP[39]);
                            reference.child(UserID).child("bet").child("black").setValue(""+MAP[40]);
                            reference.child(UserID).child("bet").child("high").setValue(""+MAP[41]);
                            reference.child(UserID).child("bet").child("low").setValue(""+MAP[42]);

                            boss_reference.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    long bos_balance = Long.parseLong(snapshot.child("balance").getValue().toString());
                                    long new_sum = BET_SUM + bos_balance;
                                    boss_reference.child("balance").setValue(""+new_sum);

                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                            BALANCE = Long.parseLong(snapshot.child("balance").getValue().toString()) - (long)BET_SUM;
                            long user_bets_money = Integer.parseInt(snapshot.child("bets_money").getValue().toString()) +(long) BET_SUM;
                            reference.child(UserID).child("bets_money").setValue(""+user_bets_money);
                            reference.child(UserID).child("balance").setValue(""+BALANCE);
                            startActivity(new Intent(betTable.this,Table.class));
                        }



                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });

        b0 = (Button) findViewById(R.id.b0);
        b0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MAP[0] += CHIP;
                BET_SUM += CHIP;
                int new_sum = BET_SUM+ADD_SUM;
                bet_amount.setText(""+new_sum);

            }
        });
        b1 = (Button) findViewById(R.id.b1);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MAP [1] += CHIP;
                BET_SUM += CHIP;
                int new_sum = BET_SUM+ADD_SUM;
                bet_amount.setText(""+new_sum);

            }
        });
        b2 = (Button) findViewById(R.id.b2);
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MAP [2] += CHIP;
                BET_SUM += CHIP;
                int new_sum = BET_SUM+ADD_SUM;
                bet_amount.setText(""+new_sum);
            }
        });
        b3 = (Button) findViewById(R.id.b3);
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MAP [3] += CHIP;
                BET_SUM += CHIP;
                int new_sum = BET_SUM+ADD_SUM;
                bet_amount.setText(""+new_sum);
            }
        });
        b4 = (Button) findViewById(R.id.b4);
        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MAP [4] += CHIP;
                BET_SUM += CHIP;
                int new_sum = BET_SUM+ADD_SUM;
                bet_amount.setText(""+new_sum);
            }
        });
        b5 = (Button) findViewById(R.id.b5);
        b5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MAP [5] += CHIP;
                BET_SUM += CHIP;
                int new_sum = BET_SUM+ADD_SUM;
                bet_amount.setText(""+new_sum);
            }
        });
        b6 = (Button) findViewById(R.id.b6);
        b6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MAP [6] += CHIP;
                BET_SUM += CHIP;
                int new_sum = BET_SUM+ADD_SUM;
                bet_amount.setText(""+new_sum);
            }
        });
        b7 = (Button) findViewById(R.id.b7);
        b7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MAP [7] += CHIP;
                BET_SUM += CHIP;
                int new_sum = BET_SUM+ADD_SUM;
                bet_amount.setText(""+new_sum);
            }
        });
        b8 = (Button) findViewById(R.id.b8);
        b8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MAP [8] += CHIP;
                BET_SUM += CHIP;
                int new_sum = BET_SUM+ADD_SUM;
                bet_amount.setText(""+new_sum);
            }
        });
        b9 = (Button) findViewById(R.id.b9);
        b9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MAP [9] += CHIP;
                BET_SUM += CHIP;
                int new_sum = BET_SUM+ADD_SUM;
                bet_amount.setText(""+new_sum);
            }
        });
        b10 = (Button) findViewById(R.id.b10);
        b10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MAP [10] += CHIP;
                BET_SUM += CHIP;
                int new_sum = BET_SUM+ADD_SUM;
                bet_amount.setText(""+new_sum);
            }
        });
        b11 = (Button) findViewById(R.id.b11);
        b11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MAP [11] += CHIP;
                BET_SUM += CHIP;
                int new_sum = BET_SUM+ADD_SUM;
                bet_amount.setText(""+new_sum);
            }
        });
        b12 = (Button) findViewById(R.id.b12);
        b12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MAP [12] += CHIP;
                BET_SUM += CHIP;
                int new_sum = BET_SUM+ADD_SUM;
                bet_amount.setText(""+new_sum);
            }
        });
        b13 = (Button) findViewById(R.id.b13);
        b13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MAP [13] += CHIP;
                BET_SUM += CHIP;
                int new_sum = BET_SUM+ADD_SUM;
                bet_amount.setText(""+new_sum);
            }
        });
        b14 = (Button) findViewById(R.id.b14);
        b14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MAP [14] += CHIP;
                BET_SUM += CHIP;
                int new_sum = BET_SUM+ADD_SUM;
                bet_amount.setText(""+new_sum);
            }
        });
        b15 = (Button) findViewById(R.id.b15);
        b15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MAP [15] += CHIP;
                BET_SUM += CHIP;
                int new_sum = BET_SUM+ADD_SUM;
                bet_amount.setText(""+new_sum);
            }
        });
        b16 = (Button) findViewById(R.id.b16);
        b16.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MAP [16] += CHIP;
                BET_SUM += CHIP;
                int new_sum = BET_SUM+ADD_SUM;
                bet_amount.setText(""+new_sum);
            }
        });
        b17 = (Button) findViewById(R.id.b17);
        b17.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MAP [17] += CHIP;
                BET_SUM += CHIP;
                int new_sum = BET_SUM+ADD_SUM;
                bet_amount.setText(""+new_sum);

            }
        });
        b18 = (Button) findViewById(R.id.b18);
        b18.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MAP [18] += CHIP;
                BET_SUM += CHIP;
                int new_sum = BET_SUM+ADD_SUM;
                bet_amount.setText(""+new_sum);
            }
        });
        b19 = (Button) findViewById(R.id.b19);
        b19.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MAP [19] += CHIP;
                BET_SUM += CHIP;
                int new_sum = BET_SUM+ADD_SUM;
                bet_amount.setText(""+new_sum);
            }
        });
        b20 = (Button) findViewById(R.id.b20);
        b20.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MAP [20] += CHIP;
                BET_SUM += CHIP;
                int new_sum = BET_SUM+ADD_SUM;
                bet_amount.setText(""+new_sum);
            }
        });
        b21 = (Button) findViewById(R.id.b21);
        b21.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MAP [21] += CHIP;
                BET_SUM += CHIP;
                int new_sum = BET_SUM+ADD_SUM;
                bet_amount.setText(""+new_sum);
            }
        });
        b22 = (Button) findViewById(R.id.b22);
        b22.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MAP [22] += CHIP;
                BET_SUM += CHIP;
                int new_sum = BET_SUM+ADD_SUM;
                bet_amount.setText(""+new_sum);
            }
        });
        b23 = (Button) findViewById(R.id.b23);
        b23.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MAP [23] += CHIP;
                BET_SUM += CHIP;
                int new_sum = BET_SUM+ADD_SUM;
                bet_amount.setText(""+new_sum);
            }
        });
        b24 = (Button) findViewById(R.id.b24);
        b24.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MAP [24] += CHIP;
                BET_SUM += CHIP;
                int new_sum = BET_SUM+ADD_SUM;
                bet_amount.setText(""+new_sum);
            }
        });
        b25 = (Button) findViewById(R.id.b25);
        b25.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MAP [25] += CHIP;
                BET_SUM += CHIP;
                int new_sum = BET_SUM+ADD_SUM;
                bet_amount.setText(""+new_sum);
            }
        });
        b26 = (Button) findViewById(R.id.b26);
        b26.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MAP [26] += CHIP;
                BET_SUM += CHIP;
                int new_sum = BET_SUM+ADD_SUM;
                bet_amount.setText(""+new_sum);
            }
        });
        b27 = (Button) findViewById(R.id.b27);
        b27.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MAP [27] += CHIP;
                BET_SUM += CHIP;
                int new_sum = BET_SUM+ADD_SUM;
                bet_amount.setText(""+new_sum);
            }
        });
        b28 = (Button) findViewById(R.id.b28);
        b28.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MAP [28] += CHIP;
                BET_SUM += CHIP;
                int new_sum = BET_SUM+ADD_SUM;
                bet_amount.setText(""+new_sum);
            }
        });
        b29 = (Button) findViewById(R.id.b29);
        b29.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MAP [29] += CHIP;
                BET_SUM += CHIP;
                int new_sum = BET_SUM+ADD_SUM;
                bet_amount.setText(""+new_sum);
            }
        });
        b30 = (Button) findViewById(R.id.b30);
        b30.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MAP [30] += CHIP;
                BET_SUM += CHIP;
                int new_sum = BET_SUM+ADD_SUM;
                bet_amount.setText(""+new_sum);

            }
        });
        b31 = (Button) findViewById(R.id.b31);
        b31.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MAP [31] += CHIP;
                BET_SUM += CHIP;
                int new_sum = BET_SUM+ADD_SUM;
                bet_amount.setText(""+new_sum);

            }
        });
        b32 = (Button) findViewById(R.id.b32);
        b32.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MAP [32] += CHIP;
                BET_SUM += CHIP;
                int new_sum = BET_SUM+ADD_SUM;
                bet_amount.setText(""+new_sum);
            }
        });
        b33 = (Button) findViewById(R.id.b33);
        b33.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MAP [33] += CHIP;
                BET_SUM += CHIP;
                int new_sum = BET_SUM+ADD_SUM;
                bet_amount.setText(""+new_sum);

            }
        });
        b34 = (Button) findViewById(R.id.b34);
        b34.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MAP [34] += CHIP;
                BET_SUM += CHIP;
                int new_sum = BET_SUM+ADD_SUM;
                bet_amount.setText(""+new_sum);

            }
        });
        b35 = (Button) findViewById(R.id.b35);
        b35.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MAP [35] += CHIP;
                BET_SUM += CHIP;
                int new_sum = BET_SUM+ADD_SUM;
                bet_amount.setText(""+new_sum);
            }
        });
        b36 = (Button) findViewById(R.id.b36);
        b36.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MAP [36] += CHIP;
                BET_SUM += CHIP;
                int new_sum = BET_SUM+ADD_SUM;
                bet_amount.setText(""+new_sum);

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
        odd = findViewById(R.id.odd);
        odd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MAP [37] += CHIP;
                BET_SUM += CHIP;
                int new_sum = BET_SUM+ADD_SUM;
                bet_amount.setText(""+new_sum);
            }
        });
        even = findViewById(R.id.even);
        even.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MAP [38] += CHIP;
                BET_SUM += CHIP;
                int new_sum = BET_SUM+ADD_SUM;
                bet_amount.setText(""+new_sum);
            }
        });
        red = findViewById(R.id.red);
        red.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MAP [39] += CHIP;
                BET_SUM += CHIP;
                int new_sum = BET_SUM+ADD_SUM;
                bet_amount.setText(""+new_sum);
            }
        });
        black = findViewById(R.id.black);
        black.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MAP [40] += CHIP;
                BET_SUM += CHIP;
                int new_sum = BET_SUM+ADD_SUM;
                bet_amount.setText(""+new_sum);
            }
        });
        high = findViewById(R.id.half2);
        high.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MAP [41] += CHIP;
                BET_SUM += CHIP;
                int new_sum = BET_SUM+ADD_SUM;
                bet_amount.setText(""+new_sum);
            }
        });
        low = findViewById(R.id.half1);
        low.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MAP [42] += CHIP;
                BET_SUM += CHIP;
                int new_sum = BET_SUM+ADD_SUM;
                bet_amount.setText(""+new_sum);
            }
        });

    }

}