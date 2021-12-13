package com.example.roulette;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.ktx.Firebase;

public class register extends AppCompatActivity implements View.OnClickListener {
    private FirebaseAuth mAuth;
    private EditText user_full_name, user_email, user_password;
    private Button registerUser;
    private FirebaseUser user;
    private String UserID;
    private DatabaseReference reference;

    private CheckBox box;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_register);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        Button registerUser = findViewById(R.id.button2);
        registerUser.setOnClickListener(this);
        reference = FirebaseDatabase.getInstance().getReference("Users");
        user_full_name = (EditText) findViewById(R.id.register_first_name);
        user_email = (EditText) findViewById(R.id.register_email);
        user_password = (EditText) findViewById(R.id.register_password);
        box = (CheckBox) findViewById(R.id.checkBox);
        progressBar = (ProgressBar)findViewById(R.id.progressBar);
        UserID = mAuth.getUid();

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
        progressBar.setVisibility(View.GONE);

        //empty name check
        if(full_name.isEmpty()){
            user_full_name.setError("full name is required!");
            user_full_name.requestFocus();
            return;
        }
        //valid email check
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            user_email.setError("valid email is required!");
            user_email.requestFocus();
            return;
        }
        //empty email check
        if(email.isEmpty()){
            user_email.setError("email is required!");
            user_email.requestFocus();
            return;
        }
        //valid password check
        if(password.isEmpty() || password.length() < 6){
            user_password.setError("min password should be 6 characters");
            user_password.requestFocus();
            return;
        }
        //age check over 18
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
                        //register successful
                        if(task.isSuccessful()){
                            //new user object
                            User user1 = new User(full_name,email,password);
                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user1).addOnCompleteListener(new OnCompleteListener<Void>() {
                                //adding the new user to data base
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    //added successfully
                                    if(task.isSuccessful()){
                                        progressBar.setVisibility(View.VISIBLE);
                                        Toast.makeText(register.this,"successfully",Toast.LENGTH_LONG).show();
                                        //login with the new user
                                        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                            @Override
                                            public void onComplete(@NonNull Task<AuthResult> task) {
                                                //login success
                                                if(task.isSuccessful()){
                                                    user = FirebaseAuth.getInstance().getCurrentUser();
                                                    UserID = user.getUid();
                                                    reference.child(UserID).child("faces").child("min_show").setValue("0");

                                                    startActivity(new Intent(register.this,user_bio.class));
                                                }
                                                //failed
                                                else{
                                                    Toast.makeText(register.this,"failed ",Toast.LENGTH_LONG).show();
                                                }
                                            }
                                        });
                                    }
                                    //failed to add user
                                    else{
                                        Toast.makeText(register.this,"failed to add user object",Toast.LENGTH_LONG).show();
                                        progressBar.setVisibility(View.GONE);
                                    }
                                }
                            });
                            {

                            }
                        }
                        //failed to register
                        else{
                            Toast.makeText(register.this,"failed to register! please try check your email/password",Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });

    }

}