package com.example.roulette;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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
    private Button logout;
    private FirebaseUser user;
    private DatabaseReference reference;
    private TextView welcomeMSG, full_name, balancee, email_user;
    private String UserID;
    private Button deposit,edit,play;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_bio);
        deposit = findViewById(R.id.depositBTN);
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
            }
        });

        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        UserID = user.getUid();
        welcomeMSG = findViewById(R.id.welcome);
        full_name = findViewById(R.id.first_name_display);
        email_user = findViewById(R.id.email_display);
        balancee = findViewById(R.id.balance);

        reference.child(UserID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                    String ful_name = snapshot.child("full_name").getValue().toString();
                    String email = snapshot.child("email").getValue().toString();;
                    String balance = snapshot.child("balance").getValue().toString();
                    welcomeMSG.setText("welcome " +ful_name );
                    full_name.setText(ful_name);;
                    email_user.setText(email);
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
                startActivity(new Intent(user_bio.this,Table.class));
            }
        });


    }


}