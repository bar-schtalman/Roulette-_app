
package com.example.roulette;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class UserLogIn extends AppCompatActivity  {
    private Button forgot,regiser,login;
    private EditText user_email, user_password;

    private ProgressBar progressBar;

    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_user_log_in);

        regiser  = findViewById(R.id.player_register);
        regiser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserLogIn.this,register.class));
            }
        });
        login =  findViewById(R.id.player_login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userLogin();
            }
        });
        forgot =  findViewById(R.id.forgot);
        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserLogIn.this,forgot_password.class));

            }
        });

        user_email =  findViewById(R.id.Email);
        progressBar =  findViewById(R.id.progressBar1);
        user_password =  findViewById(R.id.Password);
        mAuth = FirebaseAuth.getInstance();
    }


    private void userLogin() { //after pressing login func
        progressBar.setVisibility(View.VISIBLE);
        String email = user_email.getText().toString().trim();
        String password = user_password.getText().toString().trim();

        //empty email check
        if(email.isEmpty()){
            user_email.setError("email is required!");
            user_email.requestFocus();
            return;
        }
        //valid email check
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            user_email.setError("valid email is required!");
            user_email.requestFocus();
            return;
        }
        //valid password check
        if(password.length() < 6){
            user_password.setError("min password should be 6 characters");
            user_password.requestFocus();
            return;
        }
        //empty password check
        if(password.isEmpty()){
            user_password.setError("password is required!");
            user_password.requestFocus();
            return;
        }
        //login with email and password
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                //login successful
                if(task.isSuccessful()){
                    progressBar.setVisibility(View.GONE);
                    startActivity(new Intent(UserLogIn.this,user_bio.class));
                }
                //login faild
                else{
                    Toast.makeText(UserLogIn.this,"failed to login! please try check your email/password",Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.GONE);
                }
            }
        });
    }
}
