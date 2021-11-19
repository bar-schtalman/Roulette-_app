package com.example.roulette;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthEmailException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EditProfile extends AppCompatActivity {
    public static final String TAG = "TAG";
    Intent intent;
    EditText fullName, email, password;
    Button button;
    String U_FullName, U_Mail, UserID;

    private FirebaseUser user;
    private DatabaseReference reference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        fullName = (EditText) findViewById(R.id.full_name);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);

        String s_name, s_mail, s_pass;
        s_name = fullName.getText().toString().trim();
        s_mail = email.getText().toString().trim();

        Intent data = getIntent();
        U_FullName = data.getStringExtra("full_name");
        U_Mail = data.getStringExtra("email");
        ////////////////////////////////////////////////////////////
        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        UserID = user.getUid();




        email.setText(U_Mail);
        fullName.setText(U_FullName);




    }
    public void update (View view){

    }
}