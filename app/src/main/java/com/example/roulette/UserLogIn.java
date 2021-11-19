
package com.example.roulette;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class UserLogIn extends AppCompatActivity implements View.OnClickListener {
    private Button button;
    private EditText user_email, user_password;
    private Button signin;
    private ProgressBar progressBar;

    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_log_in);

        Button button = findViewById(R.id.player_register);
        button.setOnClickListener(this);
        Button forgot = findViewById(R.id.forgot);
        forgot.setOnClickListener(this);
        signin = (Button) findViewById(R.id.player_login);
        signin.setOnClickListener(this);

        user_email = (EditText) findViewById(R.id.Email);
        progressBar = (ProgressBar) findViewById(R.id.progressBar1);
        user_password = (EditText) findViewById(R.id.Password);
        mAuth = FirebaseAuth.getInstance();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.player_register:
                startActivity(new Intent(this,register.class));
                break;
            case R.id.player_login:
                userLogin();
                break;
            case R.id.forgot:
                startActivity(new Intent(UserLogIn.this,forgot_password.class));
                break;
        }
            
            
            


    }

    private void userLogin() {
        progressBar.setVisibility(View.VISIBLE);
        String email = user_email.getText().toString().trim();
        String password = user_password.getText().toString().trim();

        if(email.isEmpty()){
            user_email.setError("email is required!");
            user_email.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            user_email.setError("valid email is required!");
            user_email.requestFocus();
            return;
        }
        if(password.length() < 6){
            user_password.setError("min password should be 6 characters");
            user_password.requestFocus();
            return;
        }
        if(password.isEmpty()){
            user_password.setError("password is required!");
            user_password.requestFocus();
            return;
        }
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    progressBar.setVisibility(View.GONE);
                    startActivity(new Intent(UserLogIn.this,user_bio.class));
                }
                else{
                    Toast.makeText(UserLogIn.this,"failed to login! plaese try check your email/assword",Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.GONE);
                }
            }
        });
    }
}