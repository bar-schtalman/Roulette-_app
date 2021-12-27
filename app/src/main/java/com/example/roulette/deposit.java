package com.example.roulette;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.TypedValue;
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

public class deposit extends AppCompatActivity {
    private EditText number,month,year,amount;
    private String c_month, c_year, c_amount,UserID,c_number;
    private Button submit,close_deposit, ok, cancel;
    private TextView text_view;
    private FirebaseUser user;
    private DatabaseReference reference;
    private Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_deposit);
        dialog = new Dialog(this);
        reference = FirebaseDatabase.getInstance().getReference("Users");
        number = findViewById(R.id.creditcard);
        number.requestFocus();
        month = findViewById(R.id.month);
        year = findViewById(R.id.year);
        amount = findViewById(R.id.amount);
        submit = findViewById(R.id.submit);
        close_deposit = findViewById(R.id.exit_btn3);
        close_deposit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(deposit.this,user_bio.class));
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { //valid information check before deposit
                c_amount = amount.getText().toString().trim();
                c_number = number.getText().toString().trim();
                c_month = month.getText().toString().trim();
                c_year = year.getText().toString().trim();

                v.setOnClickListener(this);

                if(c_number.length() != 16){
                    number.setError("16 digits required");
                    number.requestFocus();
                    return;
                }
                if(c_month.length() != 2 ) {
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
                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                lp.copyFrom(dialog.getWindow().getAttributes());
                lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                lp.height = WindowManager.LayoutParams.MATCH_PARENT ;
                int width = (int)(getResources().getDisplayMetrics().widthPixels*1);
                int height = (int)(getResources().getDisplayMetrics().heightPixels*0.40);
                dialog.show();
                dialog.getWindow().setLayout(width,height);
                dialog.setContentView(R.layout.costume_dialog);
                ok = dialog.findViewById(R.id.ok);
                cancel = dialog.findViewById(R.id.cancel);
                text_view = dialog.findViewById(R.id.msg_box);
                text_view.setTextSize(TypedValue.COMPLEX_UNIT_SP ,20);
                text_view.setText("Are you sure you want to deposit " + c_amount + "$ ?");
                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        user = FirebaseAuth.getInstance().getCurrentUser();
                        reference = FirebaseDatabase.getInstance().getReference("Users");
                        UserID = user.getUid();
                        reference.child(UserID).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) { //adding the amount to data base user

                                    String user_balance = snapshot.child("balance").getValue().toString();
                                    long user_new_sum = Long.parseLong(user_balance) + Long.parseLong(c_amount);
                                    String newAmount = ""+user_new_sum;
                                    reference.child(UserID).child("balance").setValue(newAmount);
                                    Toast.makeText(deposit.this,"success! "+c_amount+"$ has been added to your account",Toast.LENGTH_LONG).show();
                                    startActivity(new Intent(deposit.this,user_bio.class));

                            }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) { }
                        });
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

    }


}