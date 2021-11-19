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
    public static final String TAG = "TAG";
    Intent intent;
    private EditText user_full_name, user_email, user_password;
    Button button;
    String U_FullName, U_Mail, UserID;

    private FirebaseUser user;
    private DatabaseReference reference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        button = (Button) findViewById(R.id.update);

        // getting input from textBox
        user_full_name = (EditText) findViewById(R.id.full_name);
        user_email = (EditText) findViewById(R.id.email);



//        Intent data = getIntent();
//        U_FullName = data.getStringExtra("full_name");
//        U_Mail = data.getStringExtra("email");

        // get user info
        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        UserID = user.getUid();

        // access the user details from firebase
        reference.child(UserID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user1 = snapshot.getValue(User.class);
                if(user1 != null){
                    String fullName = user1.full_name;
                    String mail = user1.email;


                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // convert info to strings
                            String s_name, s_mail, s_pass;
                            s_name = user_full_name.getText().toString().trim();
                            s_mail = user_email.getText().toString().trim();


                            if( !s_name.equals(fullName) ){
                                reference.child(UserID).child("full_name").setValue(s_name);
                                user1.full_name = s_name;
                            }
                            if( !s_mail.equals(mail) && Patterns.EMAIL_ADDRESS.matcher(s_mail).matches()){
                                reference.child(UserID).child("email").setValue(s_mail);
                                user1.email = s_mail;
                                user.updateEmail(s_mail);

                            }
                            Toast.makeText(EditProfile.this,"new info saved!",Toast.LENGTH_LONG).show();
                            startActivity(new Intent(EditProfile.this,user_bio.class));
                        }
                    });





                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });











    }
    public void update (View view){

    }
}