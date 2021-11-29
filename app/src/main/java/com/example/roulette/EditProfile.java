package com.example.roulette;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthEmailException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EditProfile extends AppCompatActivity {
    private String current_email, current_name, current_password;
    private EditText user_full_name, user_email, user_password;
    Button button;
    String  UserID;
    private FirebaseUser user;
    private DatabaseReference reference;
    private boolean name_changed, email_changed, password_change;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_edit_profile);
        button = findViewById(R.id.update);

        user_full_name = findViewById(R.id.full_name);
        user_email = findViewById(R.id.email);
        user_password = findViewById(R.id.password);




        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        UserID = user.getUid();

        // access the user details from firebase
        reference.child(UserID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                    current_name = snapshot.child("full_name").getValue().toString();
                    current_email = snapshot.child("email").getValue().toString();
                    current_password = snapshot.child("password").getValue().toString();
                    user_full_name.setText(current_name);
                    user_email.setText(current_email);
                    user_password.setText(current_password);




                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            name_changed = false;
                            email_changed = false;
                            password_change = false;
                            // convert info to strings
                            String s_name, s_mail, s_pass;
                            s_name = user_full_name.getText().toString().trim();
                            s_mail = user_email.getText().toString().trim();
                            s_pass = user_password.getText().toString().trim();

                            if( !s_pass.equals(current_password) && !s_pass.isEmpty() && s_pass.length() >=6 ){
                                user.updatePassword(s_pass).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(!task.isSuccessful()){
                                            Toast.makeText(EditProfile.this,"failed to update password",Toast.LENGTH_SHORT).show();
                                        }
                                        reference.child(UserID).child("password").setValue(s_pass);
                                        password_change = true;
                                    }
                                });
                            }
                            if( !s_name.equals(current_name ) && !s_name.isEmpty()){
                                reference.child(UserID).child("full_name").setValue(s_name);
                                name_changed = true;
                            }
                            if( !s_mail.equals(current_email) && Patterns.EMAIL_ADDRESS.matcher(s_mail).matches()) {
                                user.updateEmail(s_mail).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(!task.isSuccessful()){
                                            user_email.setError("email address already in use, enter different email ");
                                            user_email.requestFocus();
                                            return;
                                        }
                                        email_changed = true;
                                        reference.child(UserID).child("email").setValue(s_mail);
                                    }
                                });
                            }
                            if(!email_changed && !password_change && !name_changed){
                                Toast.makeText(EditProfile.this,"Nothing has changed",Toast.LENGTH_SHORT).show();
                            }
                            if(!email_changed && !password_change && name_changed){
                                Toast.makeText(EditProfile.this,"Name has changed",Toast.LENGTH_SHORT).show();
                            }
                            if(!email_changed && password_change && !name_changed){
                                Toast.makeText(EditProfile.this,"Password has changed",Toast.LENGTH_SHORT).show();
                            }
                            if(!email_changed && password_change && name_changed){
                                Toast.makeText(EditProfile.this,"Name and Password has changed",Toast.LENGTH_SHORT).show();
                            }
                            if(email_changed && !password_change && !name_changed){
                                Toast.makeText(EditProfile.this,"Email has changed",Toast.LENGTH_SHORT).show();
                            }
                            if(email_changed && !password_change && name_changed){
                                Toast.makeText(EditProfile.this,"Name and Email has changed",Toast.LENGTH_SHORT).show();
                            }
                            if(email_changed && password_change && !name_changed){
                                Toast.makeText(EditProfile.this,"Email and Password has changed",Toast.LENGTH_SHORT).show();
                            }
                            if(email_changed && password_change && name_changed){
                                Toast.makeText(EditProfile.this,"Name, Email and Password has changed",Toast.LENGTH_SHORT).show();
                            }

                            startActivity(new Intent(EditProfile.this,user_bio.class));
                        }
                    });





                }


            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        });











    }
    public void update (View view){

    }
}