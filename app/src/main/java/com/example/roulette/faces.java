package com.example.roulette;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class faces extends AppCompatActivity {
    private ImageView img1,img2,img3,img4;
    private TextView txt1,txt2,txt3,txt4;
    private String UserID,imgURL;
    int min_photos;
    private Button profile2;

    private FirebaseUser user;
    private DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_faces);

        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        UserID = user.getUid();
        img1 = findViewById(R.id.pic1);
        img2 = findViewById(R.id.pic2);
        img3 = findViewById(R.id.pic3);
        img4 = findViewById(R.id.pic4);
        txt1 = findViewById(R.id.first_pid_details);
        txt2 = findViewById(R.id.second_pic_details);
        txt3 = findViewById(R.id.third_pic_details);
        txt4 = findViewById(R.id.fourth_pic_details);


        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                min_photos = Integer.parseInt(snapshot.child(UserID).child("faces").child("min_show").getValue().toString());
                if(min_photos == 0 ){

                }
                if(min_photos == 1){
                    imgURL = snapshot.child(UserID).child("faces").child("0").child("url").getValue().toString();
                    Picasso.get().load(imgURL).into(img1);
                    txt1.setText(snapshot.child(UserID).child("faces").child("0").child("sum").getValue().toString());
                }
                if(min_photos == 2) {
                    imgURL = snapshot.child(UserID).child("faces").child("0").child("url").getValue().toString();
                    Picasso.get().load(imgURL).into(img1);
                    txt1.setText(snapshot.child(UserID).child("faces").child("0").child("sum").getValue().toString());
                    imgURL = snapshot.child(UserID).child("faces").child("1").child("url").getValue().toString();
                    Picasso.get().load(imgURL).into(img2);
                    txt2.setText(snapshot.child(UserID).child("faces").child("1").child("sum").getValue().toString());
                }
                if(min_photos == 3) {
                    imgURL = snapshot.child(UserID).child("faces").child("0").child("url").getValue().toString();
                    Picasso.get().load(imgURL).into(img1);
                    txt1.setText(snapshot.child(UserID).child("faces").child("0").child("sum").getValue().toString());
                    imgURL = snapshot.child(UserID).child("faces").child("1").child("url").getValue().toString();
                    Picasso.get().load(imgURL).into(img2);
                    txt2.setText(snapshot.child(UserID).child("faces").child("1").child("sum").getValue().toString());
                    imgURL = snapshot.child(UserID).child("faces").child("2").child("url").getValue().toString();
                    Picasso.get().load(imgURL).into(img3);
                    txt3.setText(snapshot.child(UserID).child("faces").child("2").child("sum").getValue().toString());
                }
                if( min_photos >= 4) {
                    imgURL = snapshot.child(UserID).child("faces").child("0").child("url").getValue().toString();
                    Picasso.get().load(imgURL).into(img1);
                    txt1.setText(snapshot.child(UserID).child("faces").child("0").child("sum").getValue().toString());
                    imgURL = snapshot.child(UserID).child("faces").child("1").child("url").getValue().toString();
                    Picasso.get().load(imgURL).into(img2);
                    txt2.setText(snapshot.child(UserID).child("faces").child("1").child("sum").getValue().toString());
                    imgURL = snapshot.child(UserID).child("faces").child("2").child("url").getValue().toString();
                    Picasso.get().load(imgURL).into(img3);
                    txt3.setText(snapshot.child(UserID).child("faces").child("2").child("sum").getValue().toString());
                    imgURL = snapshot.child(UserID).child("faces").child("3").child("url").getValue().toString();
                    Picasso.get().load(imgURL).into(img4);
                    txt4.setText(snapshot.child(UserID).child("faces").child("3").child("sum").getValue().toString());
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        profile2 = findViewById(R.id.profile_btn2);
        profile2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(faces.this,user_bio.class));
            }
        });


    }
}