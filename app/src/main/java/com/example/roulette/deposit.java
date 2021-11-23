package com.example.roulette;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class deposit extends AppCompatActivity {
    private EditText number,month,year,amount;
    private String c_month, c_year, c_amount,UserID,c_number;
    private Button submit;
    private FirebaseUser user;
    private DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deposit);

        reference = FirebaseDatabase.getInstance().getReference("Users");
        number = (EditText) findViewById(R.id.creditcard);
        month = (EditText)  findViewById(R.id.month);
        year = (EditText) findViewById(R.id.year);
        amount = (EditText) findViewById(R.id.amount);
        submit = (Button) findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c_number = number.getText().toString().trim();
                c_month = month.getText().toString().trim();
                c_year = year.getText().toString().trim();
                c_amount = amount.getText().toString().trim();

                v.setOnClickListener(this);

                if(c_number.length() != 16){
                    number.setError("16 digits required");
                    number.requestFocus();
                    return;
                }
                if(c_month.length() != 2){
                    month.setError("valid month input required! for example 05");
                    month.requestFocus();
                    return;
                }
                if(c_year.length() !=2){
                    year.setError("valid year input required! for example 21");
                    year.requestFocus();
                    return;
                }
                if(Integer.parseInt(c_amount) <= 0){
                    amount.setError("positive amount required!");
                    amount.requestFocus();
                    return;
                }
                user = FirebaseAuth.getInstance().getCurrentUser();
                reference = FirebaseDatabase.getInstance().getReference("Users");
                UserID = user.getUid();
                Long sum = Long.parseLong(c_amount);
                String newSum = String.valueOf(sum);

                reference.child(UserID).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        User user1 = snapshot.getValue(User.class);
                        if(user1 != null){
                            String user_balance = user1.balance;
                            String newAmount = ""+Long.parseLong(user_balance) + Long.parseLong(c_amount);
                            reference.child(UserID).child("balance").setValue(String.valueOf(newAmount));
                            Toast.makeText(deposit.this,"success! "+c_amount+"$ has been added to your account",Toast.LENGTH_LONG).show();
                            startActivity(new Intent(deposit.this,user_bio.class));
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