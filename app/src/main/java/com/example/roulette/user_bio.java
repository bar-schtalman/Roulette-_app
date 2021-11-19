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
    private TextView welcome, first_name, last_name, email;
    private String UserID;
    private Button button,edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_bio);
        button = (Button) findViewById(R.id.depositBTN);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(user_bio.this,deposit.class));
            }
        });
        logout = (Button) findViewById(R.id.logout);
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
        TextView welcomeMSG = (TextView) findViewById(R.id.welcome);
        TextView full_name = (TextView) findViewById(R.id.first_name_display);
        TextView email_user = (TextView) findViewById(R.id.email_display);
        TextView balancee = (TextView) findViewById(R.id.balance);

        reference.child(UserID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user1 = snapshot.getValue(User.class);
                if(user1 != null){
                    String fu_name = user1.full_name;
                    String email = user1.email;
                    String balance = user1.balance;

                    welcomeMSG.setText("welcome " +fu_name );
                    full_name.setText(fu_name);;
                    email_user.setText(email);
                    balancee.setText(balance+"$");
                    Toast.makeText(user_bio.this,"yea!",Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(user_bio.this,"shit!",Toast.LENGTH_LONG).show();
            }
        });

        edit = (Button) findViewById(R.id.editProfile);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(user_bio.this,EditProfile.class));
            }
        });





    }

    public void openDialog() {

    }
}