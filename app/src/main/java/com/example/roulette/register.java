package com.example.roulette;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.ktx.Firebase;

public class register extends AppCompatActivity implements View.OnClickListener {
    private FirebaseAuth mAuth;
    private EditText user_full_name, user_email, user_password;
    private Button registerUser;
    private CheckBox box;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        Button registerUser = findViewById(R.id.button2);
        registerUser.setOnClickListener(this);

        user_full_name = (EditText) findViewById(R.id.register_first_name);
        user_email = (EditText) findViewById(R.id.register_email);
        user_password = (EditText) findViewById(R.id.register_password);
        box = (CheckBox) findViewById(R.id.checkBox);
        progressBar = (ProgressBar)findViewById(R.id.progressBar);


    }

    @Override
    public void onClick(View v) {
        registerUser();
        
    }

    private void registerUser() {
        String email, password, full_name,balance;

        email = user_email.getText().toString().trim();
        password = user_password.getText().toString().trim();
        full_name = user_full_name.getText().toString().trim();
        balance = "0";
        progressBar.setVisibility(View.GONE);

        if(full_name.isEmpty()){
            user_full_name.setError("full name is required!");
            user_full_name.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            user_email.setError("valid email is required!");
            user_email.requestFocus();
            return;
        }
        if(email.isEmpty()){
            user_email.setError("email is required!");
            user_email.requestFocus();
            return;
        }
        if(password.isEmpty() || password.length() < 6){
            user_password.setError("min password should be 6 characters");
            user_password.requestFocus();
            return;
        }
        if(!box.isChecked()){
            box.setError("please confirm your age");
            box.requestFocus();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){

                            User user1 = new User(full_name,email,password);
                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user1).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        progressBar.setVisibility(View.VISIBLE);

                                        Toast.makeText(register.this,"successfully",Toast.LENGTH_LONG).show();
                                        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                            @Override
                                            public void onComplete(@NonNull Task<AuthResult> task) {
                                                if(task.isSuccessful()){
                                                    startActivity(new Intent(register.this,user_bio.class));
                                                }
                                                else{
                                                    Toast.makeText(register.this,"failed to register! please try check your email/password",Toast.LENGTH_LONG).show();
                                                }
                                            }
                                        });
                                    }
                                    else{
                                        Toast.makeText(register.this,"failed!",Toast.LENGTH_LONG).show();
                                        progressBar.setVisibility(View.GONE);
                                    }
                                }
                            }); {


                            }


                        }
                        else{
                            Toast.makeText(register.this,"failed!",Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });

    }
}