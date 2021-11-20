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

public class betTable extends AppCompatActivity {
    private Button b1 ,b2 ,b3 ,b4 ,b5 ,b6 ,b7 ,b8 ,b9 ,b10 ,b11 ,b0 ,b12 ,b13 ,b14 ,b15 ,b16 ,b17 ,
            b18 ,b19,b20 ,b21 ,b22 ,b23 ,b24 ,b25 ,b26 ,b27 ,b28 ,b29 ,b30 ,b31 ,b32 ,b33 ,b34 ,b35
            ,b36,btn5,btn25,btn100,btn500, btn1000,submit;
    private TextView bet_amount,user_amount,user_bet,selected_chip;
    String BET_STRING,UserID;
    private FirebaseUser user;
    private DatabaseReference reference;
    private int CHIP = 0,BET_SUM = 0;
    int old_val;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bet_table);
        ////////////////////////////////////////////////////////////
        reference = FirebaseDatabase.getInstance().getReference("Users");
        user = FirebaseAuth.getInstance().getCurrentUser();
        UserID = user.getUid();
        ///////////////////////////////////////////////////////////
        BET_STRING = "";
        submit = (Button)findViewById(R.id.submit_bet);
        selected_chip = (TextView)findViewById(R.id.textView17);
        user_bet = (TextView)findViewById(R.id.user_bet);
        bet_amount = (TextView)findViewById(R.id.betsumtextview);
        b0 = (Button) findViewById(R.id.b0);
        b0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reference.child(UserID).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        User user1 = snapshot.getValue(User.class);
                        if(user1 != null) {
                            if (user1.MAP.containsKey(0))
                                old_val = user1.MAP.get(0);
                            user1.MAP.put(0, CHIP+old_val );
                            BET_SUM += CHIP;
                            String a = "" + BET_SUM;
                            bet_amount.setText(a);
                        }
                            else {
                                user1.MAP.put(0,CHIP);
                            BET_SUM += CHIP;
                            String a = "" + BET_SUM;
                            bet_amount.setText(a);
                        }
                            BET_STRING += ("0 - "+CHIP+"| ");
                            user_bet.setText(BET_STRING);


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
        b1 = (Button) findViewById(R.id.b1);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reference.child(UserID).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        User user1 = snapshot.getValue(User.class);
                        if(user1 != null) {
                            if (user1.MAP.containsKey(1))
                                old_val = user1.MAP.get(1);
                            user1.MAP.put(1, CHIP+old_val );
                            BET_SUM += CHIP;
                            String a = "" + BET_SUM;
                            bet_amount.setText(a);
                        }
                        else {
                            user1.MAP.put(1,CHIP);
                            BET_SUM += CHIP;
                            String a = "" + BET_SUM;
                            bet_amount.setText(a);
                        }
                        BET_STRING += ("1 - "+CHIP+"| ");
                        user_bet.setText(BET_STRING);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
        b2 = (Button) findViewById(R.id.b2);
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reference.child(UserID).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        User user1 = snapshot.getValue(User.class);
                        if(user1 != null) {
                            if (user1.MAP.containsKey(2))
                                old_val = user1.MAP.get(2);
                            user1.MAP.put(2, CHIP+old_val );
                            BET_SUM += CHIP;
                            String a = "" + BET_SUM;
                            bet_amount.setText(a);
                        }
                        else {
                            user1.MAP.put(2,CHIP);
                            BET_SUM += CHIP;
                            String a = "" + BET_SUM;
                            bet_amount.setText(a);
                        }
                        BET_STRING += ("2 - "+CHIP+"| ");
                        user_bet.setText(BET_STRING);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
        b3 = (Button) findViewById(R.id.b3);
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reference.child(UserID).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        User user1 = snapshot.getValue(User.class);
                        if(user1 != null) {
                            if (user1.MAP.containsKey(3))
                                old_val = user1.MAP.get(3);
                            user1.MAP.put(3, CHIP+old_val );
                            BET_SUM += CHIP;
                            String a = "" + BET_SUM;
                            bet_amount.setText(a);
                        }
                        else {
                            user1.MAP.put(3,CHIP);
                            BET_SUM += CHIP;
                            String a = "" + BET_SUM;
                            bet_amount.setText(a);
                        }
                        BET_STRING += ("3 - "+CHIP+"| ");
                        user_bet.setText(BET_STRING);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
        b4 = (Button) findViewById(R.id.b4);
        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reference.child(UserID).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        User user1 = snapshot.getValue(User.class);
                        if(user1 != null) {
                            if (user1.MAP.containsKey(4))
                                old_val = user1.MAP.get(4);
                            user1.MAP.put(4, CHIP+old_val );
                            BET_SUM += CHIP;
                            String a = "" + BET_SUM;
                            bet_amount.setText(a);
                        }
                        else {
                            user1.MAP.put(4,CHIP);
                            BET_SUM += CHIP;
                            String a = "" + BET_SUM;
                            bet_amount.setText(a);
                        }
                        BET_STRING += ("4 - "+CHIP+"| ");
                        user_bet.setText(BET_STRING);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
        b5 = (Button) findViewById(R.id.b5);
        b5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reference.child(UserID).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        User user1 = snapshot.getValue(User.class);
                        if(user1 != null) {
                            if (user1.MAP.containsKey(5))
                                old_val = user1.MAP.get(5);
                            user1.MAP.put(5, CHIP+old_val );
                            BET_SUM += CHIP;
                            String a = "" + BET_SUM;
                            bet_amount.setText(a);
                        }
                        else {
                            user1.MAP.put(5,CHIP);
                            BET_SUM += CHIP;
                            String a = "" + BET_SUM;
                            bet_amount.setText(a);
                        }
                        BET_STRING += ("5 - "+CHIP+"| ");
                        user_bet.setText(BET_STRING);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
        b6 = (Button) findViewById(R.id.b6);
        b6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reference.child(UserID).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        User user1 = snapshot.getValue(User.class);
                        if(user1 != null) {
                            if (user1.MAP.containsKey(6))
                                old_val = user1.MAP.get(6);
                            user1.MAP.put(6, CHIP+old_val );
                            BET_SUM += CHIP;
                            String a = "" + BET_SUM;
                            bet_amount.setText(a);
                        }
                        else {
                            user1.MAP.put(6,CHIP);
                            BET_SUM += CHIP;
                            String a = "" + BET_SUM;
                            bet_amount.setText(a);
                        }
                        BET_STRING += ("6 - "+CHIP+"| ");
                        user_bet.setText(BET_STRING);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
        b7 = (Button) findViewById(R.id.b7);
        b7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reference.child(UserID).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        User user1 = snapshot.getValue(User.class);
                        if(user1 != null) {
                            if (user1.MAP.containsKey(7))
                                old_val = user1.MAP.get(7);
                            user1.MAP.put(7, CHIP+old_val );
                            BET_SUM += CHIP;
                            String a = "" + BET_SUM;
                            bet_amount.setText(a);
                        }
                        else {
                            user1.MAP.put(7,CHIP);
                            BET_SUM += CHIP;
                            String a = "" + BET_SUM;
                            bet_amount.setText(a);
                        }
                        BET_STRING += ("7 - "+CHIP+"| ");
                        user_bet.setText(BET_STRING);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
        b8 = (Button) findViewById(R.id.b8);
        b8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reference.child(UserID).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        User user1 = snapshot.getValue(User.class);
                        if(user1 != null) {
                            if (user1.MAP.containsKey(8))
                                old_val = user1.MAP.get(8);
                            user1.MAP.put(8, CHIP+old_val );
                            BET_SUM += CHIP;
                            String a = "" + BET_SUM;
                            bet_amount.setText(a);
                        }
                        else {
                            user1.MAP.put(8,CHIP);
                            BET_SUM += CHIP;
                            String a = "" + BET_SUM;
                            bet_amount.setText(a);
                        }
                        BET_STRING += ("8 - "+CHIP+"| ");
                        user_bet.setText(BET_STRING);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
        b9 = (Button) findViewById(R.id.b9);
        b9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reference.child(UserID).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        User user1 = snapshot.getValue(User.class);
                        if(user1 != null) {
                            if (user1.MAP.containsKey(9))
                                old_val = user1.MAP.get(9);
                            user1.MAP.put(9, CHIP+old_val );
                            BET_SUM += CHIP;
                            String a = "" + BET_SUM;
                            bet_amount.setText(a);
                        }
                        else {
                            user1.MAP.put(9,CHIP);
                            BET_SUM += CHIP;
                            String a = "" + BET_SUM;
                            bet_amount.setText(a);
                        }
                        BET_STRING += ("9 - "+CHIP+"| ");
                        user_bet.setText(BET_STRING);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
        b10 = (Button) findViewById(R.id.b10);
        b10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reference.child(UserID).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        User user1 = snapshot.getValue(User.class);
                        if(user1 != null) {
                            if (user1.MAP.containsKey(10))
                                old_val = user1.MAP.get(10);
                            user1.MAP.put(10, CHIP+old_val );
                            BET_SUM += CHIP;
                            String a = "" + BET_SUM;
                            bet_amount.setText(a);
                        }
                        else {
                            user1.MAP.put(10,CHIP);
                            BET_SUM += CHIP;
                            String a = "" + BET_SUM;
                            bet_amount.setText(a);
                        }
                        BET_STRING += ("10 - "+CHIP+"| ");
                        user_bet.setText(BET_STRING);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
        b11 = (Button) findViewById(R.id.b11);
        b11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reference.child(UserID).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        User user1 = snapshot.getValue(User.class);
                        if(user1 != null) {
                            if (user1.MAP.containsKey(11))
                                old_val = user1.MAP.get(11);
                            user1.MAP.put(11, CHIP+old_val );
                            BET_SUM += CHIP;
                            String a = "" + BET_SUM;
                            bet_amount.setText(a);
                        }
                        else {
                            user1.MAP.put(11,CHIP);
                            BET_SUM += CHIP;
                            String a = "" + BET_SUM;
                            bet_amount.setText(a);
                        }
                        BET_STRING += ("11 - "+CHIP+"| ");
                        user_bet.setText(BET_STRING);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

        b12 = (Button) findViewById(R.id.b12);
        b12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reference.child(UserID).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        User user1 = snapshot.getValue(User.class);
                        if(user1 != null) {
                            if (user1.MAP.containsKey(12))
                                old_val = user1.MAP.get(12);
                            user1.MAP.put(12, CHIP+old_val );
                            BET_SUM += CHIP;
                            String a = "" + BET_SUM;
                            bet_amount.setText(a);
                        }
                        else {
                            user1.MAP.put(12,CHIP);
                            BET_SUM += CHIP;
                            String a = "" + BET_SUM;
                            bet_amount.setText(a);
                        }
                        BET_STRING += ("12 - "+CHIP+"| ");
                        user_bet.setText(BET_STRING);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
        b13 = (Button) findViewById(R.id.b13);
        b13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reference.child(UserID).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        User user1 = snapshot.getValue(User.class);
                        if(user1 != null) {
                            if (user1.MAP.containsKey(13))
                                old_val = user1.MAP.get(13);
                            user1.MAP.put(13, CHIP+old_val );
                            BET_SUM += CHIP;
                            String a = "" + BET_SUM;
                            bet_amount.setText(a);
                        }
                        else {
                            user1.MAP.put(13,CHIP);
                            BET_SUM += CHIP;
                            String a = "" + BET_SUM;
                            bet_amount.setText(a);
                        }
                        BET_STRING += ("13 - "+CHIP+"| ");
                        user_bet.setText(BET_STRING);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
        b14 = (Button) findViewById(R.id.b14);
        b14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reference.child(UserID).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        User user1 = snapshot.getValue(User.class);
                        if(user1 != null) {
                            if (user1.MAP.containsKey(14))
                                old_val = user1.MAP.get(14);
                            user1.MAP.put(14, CHIP+old_val );
                            BET_SUM += CHIP;
                            String a = "" + BET_SUM;
                            bet_amount.setText(a);
                        }
                        else {
                            user1.MAP.put(14,CHIP);
                            BET_SUM += CHIP;
                            String a = "" + BET_SUM;
                            bet_amount.setText(a);
                        }
                        BET_STRING += ("14 - "+CHIP+"| ");
                        user_bet.setText(BET_STRING);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
        b15 = (Button) findViewById(R.id.b15);
        b15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reference.child(UserID).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        User user1 = snapshot.getValue(User.class);
                        if(user1 != null) {
                            if (user1.MAP.containsKey(15))
                                old_val = user1.MAP.get(15);
                            user1.MAP.put(15, CHIP+old_val );
                            BET_SUM += CHIP;
                            String a = "" + BET_SUM;
                            bet_amount.setText(a);
                        }
                        else {
                            user1.MAP.put(15,CHIP);
                            BET_SUM += CHIP;
                            String a = "" + BET_SUM;
                            bet_amount.setText(a);
                        }
                        BET_STRING += ("15 - "+CHIP+"| ");
                        user_bet.setText(BET_STRING);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
        b16 = (Button) findViewById(R.id.b16);
        b16.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reference.child(UserID).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        User user1 = snapshot.getValue(User.class);
                        if(user1 != null) {
                            if (user1.MAP.containsKey(16))
                                old_val = user1.MAP.get(16);
                            user1.MAP.put(16, CHIP+old_val );
                            BET_SUM += CHIP;
                            String a = "" + BET_SUM;
                            bet_amount.setText(a);
                        }
                        else {
                            user1.MAP.put(16,CHIP);
                            BET_SUM += CHIP;
                            String a = "" + BET_SUM;
                            bet_amount.setText(a);
                        }
                        BET_STRING += ("16 - "+CHIP+"| ");
                        user_bet.setText(BET_STRING);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
        b17 = (Button) findViewById(R.id.b17);
        b17.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reference.child(UserID).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        User user1 = snapshot.getValue(User.class);
                        if(user1 != null) {
                            if (user1.MAP.containsKey(17))
                                old_val = user1.MAP.get(17);
                            user1.MAP.put(17, CHIP+old_val );
                            BET_SUM += CHIP;
                            String a = "" + BET_SUM;
                            bet_amount.setText(a);
                        }
                        else {
                            user1.MAP.put(17,CHIP);
                            BET_SUM += CHIP;
                            String a = "" + BET_SUM;
                            bet_amount.setText(a);
                        }
                        BET_STRING += ("17 - "+CHIP+"| ");
                        user_bet.setText(BET_STRING);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
        b18 = (Button) findViewById(R.id.b18);
        b18.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reference.child(UserID).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        User user1 = snapshot.getValue(User.class);
                        if(user1 != null) {
                            if (user1.MAP.containsKey(18))
                                old_val = user1.MAP.get(18);
                            user1.MAP.put(18, CHIP+old_val );
                            BET_SUM += CHIP;
                            String a = "" + BET_SUM;
                            bet_amount.setText(a);
                        }
                        else {
                            user1.MAP.put(18,CHIP);
                            BET_SUM += CHIP;
                            String a = "" + BET_SUM;
                            bet_amount.setText(a);
                        }
                        BET_STRING += ("18 - "+CHIP+"| ");
                        user_bet.setText(BET_STRING);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
        b19 = (Button) findViewById(R.id.b19);
        b19.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reference.child(UserID).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        User user1 = snapshot.getValue(User.class);
                        if(user1 != null) {
                            if (user1.MAP.containsKey(19))
                                old_val = user1.MAP.get(19);
                            user1.MAP.put(19, CHIP+old_val );
                            BET_SUM += CHIP;
                            String a = "" + BET_SUM;
                            bet_amount.setText(a);
                        }
                        else {
                            user1.MAP.put(19,CHIP);
                            BET_SUM += CHIP;
                            String a = "" + BET_SUM;
                            bet_amount.setText(a);
                        }
                        BET_STRING += ("19 - "+CHIP+"| ");
                        user_bet.setText(BET_STRING);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
        b20 = (Button) findViewById(R.id.b20);
        b20.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reference.child(UserID).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        User user1 = snapshot.getValue(User.class);
                        if(user1 != null) {
                            if (user1.MAP.containsKey(20))
                                old_val = user1.MAP.get(20);
                            user1.MAP.put(20, CHIP+old_val );
                            BET_SUM += CHIP;
                            String a = "" + BET_SUM;
                            bet_amount.setText(a);
                        }
                        else {
                            user1.MAP.put(20,CHIP);
                            BET_SUM += CHIP;
                            String a = "" + BET_SUM;
                            bet_amount.setText(a);
                        }
                        BET_STRING += ("20 - "+CHIP+"| ");
                        user_bet.setText(BET_STRING);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
        b21 = (Button) findViewById(R.id.b21);
        b21.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reference.child(UserID).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        User user1 = snapshot.getValue(User.class);
                        if(user1 != null) {
                            if (user1.MAP.containsKey(21))
                                old_val = user1.MAP.get(21);
                            user1.MAP.put(21, CHIP+old_val );
                            BET_SUM += CHIP;
                            String a = "" + BET_SUM;
                            bet_amount.setText(a);
                        }
                        else {
                            user1.MAP.put(21,CHIP);
                            BET_SUM += CHIP;
                            String a = "" + BET_SUM;
                            bet_amount.setText(a);
                        }
                        BET_STRING += ("21 - "+CHIP+"| ");
                        user_bet.setText(BET_STRING);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
        b22 = (Button) findViewById(R.id.b22);
        b22.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reference.child(UserID).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        User user1 = snapshot.getValue(User.class);
                        if(user1 != null) {
                            if (user1.MAP.containsKey(22))
                                old_val = user1.MAP.get(22);
                            user1.MAP.put(22, CHIP+old_val );
                            BET_SUM += CHIP;
                            String a = "" + BET_SUM;
                            bet_amount.setText(a);
                        }
                        else {
                            user1.MAP.put(22,CHIP);
                            BET_SUM += CHIP;
                            String a = "" + BET_SUM;
                            bet_amount.setText(a);
                        }
                        BET_STRING += ("22 - "+CHIP+"| ");
                        user_bet.setText(BET_STRING);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
        b23 = (Button) findViewById(R.id.b23);
        b23.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reference.child(UserID).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        User user1 = snapshot.getValue(User.class);
                        if(user1 != null) {
                            if (user1.MAP.containsKey(23))
                                old_val = user1.MAP.get(23);
                            user1.MAP.put(23, CHIP+old_val );
                            BET_SUM += CHIP;
                            String a = "" + BET_SUM;
                            bet_amount.setText(a);
                        }
                        else {
                            user1.MAP.put(23,CHIP);
                            BET_SUM += CHIP;
                            String a = "" + BET_SUM;
                            bet_amount.setText(a);
                        }
                        BET_STRING += ("23 - "+CHIP+"| ");
                        user_bet.setText(BET_STRING);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
        b24 = (Button) findViewById(R.id.b24);
        b24.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reference.child(UserID).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        User user1 = snapshot.getValue(User.class);
                        if(user1 != null) {
                            if (user1.MAP.containsKey(24))
                                old_val = user1.MAP.get(24);
                            user1.MAP.put(24, CHIP+old_val );
                            BET_SUM += CHIP;
                            String a = "" + BET_SUM;
                            bet_amount.setText(a);
                        }
                        else {
                            user1.MAP.put(24,CHIP);
                            BET_SUM += CHIP;
                            String a = "" + BET_SUM;
                            bet_amount.setText(a);
                        }
                        BET_STRING += ("24 - "+CHIP+"| ");
                        user_bet.setText(BET_STRING);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
        b25 = (Button) findViewById(R.id.b25);
        b25.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reference.child(UserID).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        User user1 = snapshot.getValue(User.class);
                        if(user1 != null) {
                            if (user1.MAP.containsKey(25))
                                old_val = user1.MAP.get(25);
                            user1.MAP.put(25, CHIP+old_val );
                            BET_SUM += CHIP;
                            String a = "" + BET_SUM;
                            bet_amount.setText(a);
                        }
                        else {
                            user1.MAP.put(25,CHIP);
                            BET_SUM += CHIP;
                            String a = "" + BET_SUM;
                            bet_amount.setText(a);
                        }
                        BET_STRING += ("25 - "+CHIP+"| ");
                        user_bet.setText(BET_STRING);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
        b26 = (Button) findViewById(R.id.b26);
        b26.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reference.child(UserID).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        User user1 = snapshot.getValue(User.class);
                        if(user1 != null) {
                            if (user1.MAP.containsKey(26))
                                old_val = user1.MAP.get(26);
                            user1.MAP.put(26, CHIP+old_val );
                            BET_SUM += CHIP;
                            String a = "" + BET_SUM;
                            bet_amount.setText(a);
                        }
                        else {
                            user1.MAP.put(26,CHIP);
                            BET_SUM += CHIP;
                            String a = "" + BET_SUM;
                            bet_amount.setText(a);
                        }
                        BET_STRING += ("26 - "+CHIP+"| ");
                        user_bet.setText(BET_STRING);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
        b27 = (Button) findViewById(R.id.b27);
        b27.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reference.child(UserID).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        User user1 = snapshot.getValue(User.class);
                        if(user1 != null) {
                            if (user1.MAP.containsKey(27))
                                old_val = user1.MAP.get(27);
                            user1.MAP.put(27, CHIP+old_val );
                            BET_SUM += CHIP;
                            String a = "" + BET_SUM;
                            bet_amount.setText(a);
                        }
                        else {
                            user1.MAP.put(27,CHIP);
                            BET_SUM += CHIP;
                            String a = "" + BET_SUM;
                            bet_amount.setText(a);
                        }
                        BET_STRING += ("27 - "+CHIP+"| ");
                        user_bet.setText(BET_STRING);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
        b28 = (Button) findViewById(R.id.b28);
        b28.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reference.child(UserID).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        User user1 = snapshot.getValue(User.class);
                        if(user1 != null) {
                            if (user1.MAP.containsKey(28))
                                old_val = user1.MAP.get(28);
                            user1.MAP.put(28, CHIP+old_val );
                            BET_SUM += CHIP;
                            String a = "" + BET_SUM;
                            bet_amount.setText(a);
                        }
                        else {
                            user1.MAP.put(28,CHIP);
                            BET_SUM += CHIP;
                            String a = "" + BET_SUM;
                            bet_amount.setText(a);
                        }
                        BET_STRING += ("28 - "+CHIP+"| ");
                        user_bet.setText(BET_STRING);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
        b29 = (Button) findViewById(R.id.b29);
        b29.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reference.child(UserID).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        User user1 = snapshot.getValue(User.class);
                        if(user1 != null) {
                            if (user1.MAP.containsKey(29))
                                old_val = user1.MAP.get(29);
                            user1.MAP.put(29, CHIP+old_val );
                            BET_SUM += CHIP;
                            String a = "" + BET_SUM;
                            bet_amount.setText(a);
                        }
                        else {
                            user1.MAP.put(29,CHIP);
                            BET_SUM += CHIP;
                            String a = "" + BET_SUM;
                            bet_amount.setText(a);
                        }
                        BET_STRING += ("29 - "+CHIP+"| ");
                        user_bet.setText(BET_STRING);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
        b30 = (Button) findViewById(R.id.b30);
        b30.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reference.child(UserID).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        User user1 = snapshot.getValue(User.class);
                        if(user1 != null) {
                            if (user1.MAP.containsKey(30))
                                old_val = user1.MAP.get(30);
                            user1.MAP.put(30, CHIP+old_val );
                            BET_SUM += CHIP;
                            String a = "" + BET_SUM;
                            bet_amount.setText(a);
                        }
                        else {
                            user1.MAP.put(30,CHIP);
                            BET_SUM += CHIP;
                            String a = "" + BET_SUM;
                            bet_amount.setText(a);
                        }
                        BET_STRING += ("30 - "+CHIP+"| ");
                        user_bet.setText(BET_STRING);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
        b31 = (Button) findViewById(R.id.b31);
        b31.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reference.child(UserID).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        User user1 = snapshot.getValue(User.class);
                        if(user1 != null) {
                            if (user1.MAP.containsKey(31))
                                old_val = user1.MAP.get(31);
                            user1.MAP.put(31, CHIP+old_val );
                            BET_SUM += CHIP;
                            String a = "" + BET_SUM;
                            bet_amount.setText(a);
                        }
                        else {
                            user1.MAP.put(31,CHIP);
                            BET_SUM += CHIP;
                            String a = "" + BET_SUM;
                            bet_amount.setText(a);
                        }
                        BET_STRING += ("31 - "+CHIP+"| ");
                        user_bet.setText(BET_STRING);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
        b32 = (Button) findViewById(R.id.b32);
        b32.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reference.child(UserID).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        User user1 = snapshot.getValue(User.class);
                        if(user1 != null) {
                            if (user1.MAP.containsKey(32))
                                old_val = user1.MAP.get(32);
                            user1.MAP.put(32, CHIP+old_val );
                            BET_SUM += CHIP;
                            String a = "" + BET_SUM;
                            bet_amount.setText(a);
                        }
                        else {
                            user1.MAP.put(32,CHIP);
                            BET_SUM += CHIP;
                            String a = "" + BET_SUM;
                            bet_amount.setText(a);
                        }
                        BET_STRING += ("32 - "+CHIP+"| ");
                        user_bet.setText(BET_STRING);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
        b33 = (Button) findViewById(R.id.b33);
        b33.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reference.child(UserID).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        User user1 = snapshot.getValue(User.class);
                        if(user1 != null) {
                            if (user1.MAP.containsKey(33))
                                old_val = user1.MAP.get(33);
                            user1.MAP.put(33, CHIP+old_val );
                            BET_SUM += CHIP;
                            String a = "" + BET_SUM;
                            bet_amount.setText(a);
                        }
                        else {
                            user1.MAP.put(33,CHIP);
                            BET_SUM += CHIP;
                            String a = "" + BET_SUM;
                            bet_amount.setText(a);
                        }
                        BET_STRING += ("33 - "+CHIP+"| ");
                        user_bet.setText(BET_STRING);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
        b34 = (Button) findViewById(R.id.b34);
        b34.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reference.child(UserID).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        User user1 = snapshot.getValue(User.class);
                        if(user1 != null) {
                            if (user1.MAP.containsKey(34))
                                old_val = user1.MAP.get(34);
                            user1.MAP.put(34, CHIP+old_val );
                            BET_SUM += CHIP;
                            String a = "" + BET_SUM;
                            bet_amount.setText(a);
                        }
                        else {
                            user1.MAP.put(34,CHIP);
                            BET_SUM += CHIP;
                            String a = "" + BET_SUM;
                            bet_amount.setText(a);
                        }
                        BET_STRING += ("34 - "+CHIP+"| ");
                        user_bet.setText(BET_STRING);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
        b35 = (Button) findViewById(R.id.b35);
        b35.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reference.child(UserID).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        User user1 = snapshot.getValue(User.class);
                        if(user1 != null) {
                            if (user1.MAP.containsKey(35))
                                old_val = user1.MAP.get(35);
                            user1.MAP.put(35, CHIP+old_val );
                            BET_SUM += CHIP;
                            String a = "" + BET_SUM;
                            bet_amount.setText(a);
                        }
                        else {
                            user1.MAP.put(35,CHIP);
                            BET_SUM += CHIP;
                            String a = "" + BET_SUM;
                            bet_amount.setText(a);
                        }
                        BET_STRING += ("35 - "+CHIP+"| ");
                        user_bet.setText(BET_STRING);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
        b36 = (Button) findViewById(R.id.b36);
        b36.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reference.child(UserID).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        User user1 = snapshot.getValue(User.class);
                        if(user1 != null) {
                            if (user1.MAP.containsKey(36))
                                old_val = user1.MAP.get(36);
                            user1.MAP.put(36, CHIP+old_val );
                            BET_SUM += CHIP;
                            String a = "" + BET_SUM;
                            bet_amount.setText(a);
                        }
                        else {
                            user1.MAP.put(36,CHIP);
                            BET_SUM += CHIP;
                            String a = "" + BET_SUM;
                            bet_amount.setText(a);
                        }
                        BET_STRING += ("36 - "+CHIP+"| ");
                        user_bet.setText(BET_STRING);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
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
                        User user1 = snapshot.getValue(User.class);

                        if(user1 != null){
                        long balance = Long.parseLong(user1.balance);
                        balance = balance - (long)BET_SUM;
                        reference.child(UserID).child("balance").setValue(""+balance);
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