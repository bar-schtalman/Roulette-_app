package com.example.roulette;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.style.LineHeightSpan;
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

import org.w3c.dom.Text;

public class withdrawal extends AppCompatActivity {

    private Button submit;
    private TextView user_balance;
    private EditText bank_num, branch_num, bank_account, amount;
    private String user_balance_str, bank_num_str, branch_num_str, bank_account_str,UserID;
    private DatabaseReference reference;
    private FirebaseUser user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_withdrawal);

        submit = findViewById(R.id.submit_w);
        user_balance = findViewById(R.id.user_balance_w);
        bank_num = findViewById(R.id.bank_number);
        branch_num = findViewById(R.id.branch_nubmer);
        bank_account = findViewById(R.id.account_number);
        amount = findViewById(R.id.amount_to_w);
        reference = FirebaseDatabase.getInstance().getReference("Users");
        user = FirebaseAuth.getInstance().getCurrentUser();
        UserID = user.getUid();
        reference.child(UserID).addListenerForSingleValueEvent(new ValueEventListener() {
            //setting the user balance Textview
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                user_balance_str = snapshot.child("balance").getValue().toString();
                user_balance.setText(user_balance_str);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int user_b = Integer.parseInt(user_balance_str);
                bank_num_str = bank_num.getText().toString().trim();
                branch_num_str = branch_num.getText().toString().trim();
                bank_account_str = bank_account.getText().toString().trim();

                // check valid bank number
                if(bank_num_str.length() != 2 || bank_num_str.isEmpty())
                {
                    bank_num.setError("not valid bank number");
                    Toast.makeText(withdrawal.this, " " + bank_num_str.length(), Toast.LENGTH_SHORT).show();
                    bank_num.requestFocus();
                    return;
                }
                // check valid branch number
                if(branch_num_str.length() != 3 || branch_num_str.isEmpty())
                {
                    branch_num.setError("not valid branch number");
                    branch_num.requestFocus();
                    return;
                }
                // check valid account number
                if(bank_account_str.length() < 5 || bank_account_str.length() > 10 || bank_account_str.isEmpty())
                {
                    bank_account.setError("not valid bank account number");
                    bank_account.requestFocus();
                    return;
                }
                //check valid amount
                String amount_str = amount.getText().toString().trim();
                if (amount_str.isEmpty() || Integer.parseInt(amount_str) <= 0 ){
                    amount.setError("enter a positive value");
                    return;
                }
                //check if user have enough money
                int sum = Integer.parseInt(amount_str);
                if(sum > user_b)
                {
                    //user doesn't have enough money to withdrawal
                    amount.setError("cant withdrawal more than: " + user_b);
                    amount.requestFocus();
                    return;
                }
                //update user new balance
                int new_user_b = user_b - sum;
                reference.child(UserID).child("balance").setValue("" + new_user_b);
                startActivity(new Intent(withdrawal.this, user_bio.class));

            }
        });


    }
}