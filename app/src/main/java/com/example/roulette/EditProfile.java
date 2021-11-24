package com.example.roulette;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthEmailException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EditProfile extends AppCompatActivity {
    private EditText user_full_name, user_email;
    Button button;
    String  UserID;

    private FirebaseUser user;
    private DatabaseReference reference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        button = findViewById(R.id.update);

        user_full_name = findViewById(R.id.full_name);
        user_email = findViewById(R.id.email);




        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        UserID = user.getUid();

        // access the user details from firebase
        reference.child(UserID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                    String fullName = snapshot.child("full_name").getValue().toString();
                    String mail = snapshot.child("email").getValue().toString();


                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // convert info to strings
                            String s_name, s_mail, s_pass;
                            s_name = user_full_name.getText().toString().trim();
                            s_mail = user_email.getText().toString().trim();


                            if( !s_name.equals(fullName) ){
                                reference.child(UserID).child("full_name").setValue(s_name);
                            }
                            if( !s_mail.equals(mail) && Patterns.EMAIL_ADDRESS.matcher(s_mail).matches()){
                                reference.child(UserID).child("email").setValue(s_mail);
                                user.updateEmail(s_mail);

                            }
                            Toast.makeText(EditProfile.this,"new info saved!",Toast.LENGTH_LONG).show();
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