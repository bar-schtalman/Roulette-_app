package com.example.roulette;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class boss_login extends AppCompatActivity {
    private EditText pass,mail;
    private Button login;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_boss_login);

        mail = findViewById(R.id.boss_email);
        pass = findViewById(R.id.boss_pass);
        login = findViewById(R.id.boss_login);
        mAuth = FirebaseAuth.getInstance();
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bossLogin();
            }
        });


    }
    private void bossLogin() { //after pressing login func
        String email = mail.getText().toString().trim();
        String password = pass.getText().toString().trim();
        if( !email.contains("boss") && !email.contains("Boss")) {
            mail.setError("only boss can sign in!");
            mail.requestFocus();
            return;
        }
        //empty email check
        if(email.isEmpty()){
            mail.setError("email is required!");
            mail.requestFocus();
            return;
        }
        //valid email check
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            mail.setError("valid email is required!");
            mail.requestFocus();
            return;
        }
        //valid password check
        if(password.length() < 6){
            pass.setError("min password should be 6 characters");
            pass.requestFocus();
            return;
        }
        //empty password check
        if(password.isEmpty()){
            pass.setError("password is required!");
            pass.requestFocus();
            return;
        }
        //login with email and password
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                //login successful
                if(task.isSuccessful()){
                    startActivity(new Intent(boss_login.this,boss.class));
                }
                //login faild
                else{
                }
            }
        });
    }
}