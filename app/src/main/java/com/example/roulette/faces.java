package com.example.roulette;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class faces extends AppCompatActivity {
    private ImageView img1,img2,img3,img4;
    private TextView txt1,txt2,txt3,txt4;
    private String UserID,imgURL1,imgURL2,imgURL3,imgURL4;
    private URL url;
    private Bitmap bitmap;
    private final int GREEN = 0x008000;
    private final int RED = 0xFF0000;


    int min_photos;
    private Button profile2,del1,del2,del3,del4,share1,share2,share3,share4;
    int COUNT;

    private FirebaseUser user;
    private DatabaseReference reference;
    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_faces);

        StrictMode.VmPolicy.Builder builder  = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
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
        del1 = findViewById(R.id.pic1delete);
        del2 = findViewById(R.id.pic2delete);
        del3 = findViewById(R.id.pic3delete);
        del4 = findViewById(R.id.pic4delete);
        share1 = findViewById(R.id.pic1share);
        share2 = findViewById(R.id.pic2share);
        share3 = findViewById(R.id.pic3share);
        share4 = findViewById(R.id.pic4share);
        Uri uri;

        int num = 0;


        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                COUNT = Integer.parseInt(snapshot.child(UserID).child("img_count").getValue().toString());
                min_photos = Integer.parseInt(snapshot.child(UserID).child("faces").child("min_show").getValue().toString());
                if(min_photos == 0 ){

                }
                if(min_photos == 1){
                    imgURL1 = snapshot.child(UserID).child("faces").child("0").child("url").getValue().toString();
                    Picasso.get().load(imgURL1).into(img1);
                    txt1.setText(snapshot.child(UserID).child("faces").child("0").child("sum").getValue().toString());
                    char result = txt1.getText().charAt(0);
                    if(result == 'W'){
                        txt1.setTextColor(0xFF02FF01);
                    }
                    else{
                        txt1.setTextColor(Color.RED);
                    }
                }
                if(min_photos == 2) {
                    imgURL1 = snapshot.child(UserID).child("faces").child("0").child("url").getValue().toString();
                    Picasso.get().load(imgURL1).into(img1);
                    txt1.setText(snapshot.child(UserID).child("faces").child("0").child("sum").getValue().toString());
                    imgURL2 = snapshot.child(UserID).child("faces").child("1").child("url").getValue().toString();
                    Picasso.get().load(imgURL2).into(img2);
                    txt2.setText(snapshot.child(UserID).child("faces").child("1").child("sum").getValue().toString());
                    char result = txt1.getText().charAt(0);
                    if(result == 'W'){
                        txt1.setTextColor(0xFF02FF01);
                    }
                    else{
                        txt1.setTextColor(Color.RED);
                    }
                    char result2 = txt2.getText().charAt(0);
                    if(result2 == 'W'){
                        txt2.setTextColor(0xFF02FF01);
                    }
                    else{
                        txt2.setTextColor(Color.RED);
                    }
                }
                if(min_photos == 3) {
                    imgURL1 = snapshot.child(UserID).child("faces").child("0").child("url").getValue().toString();
                    Picasso.get().load(imgURL1).into(img1);
                    txt1.setText(snapshot.child(UserID).child("faces").child("0").child("sum").getValue().toString());
                    imgURL2 = snapshot.child(UserID).child("faces").child("1").child("url").getValue().toString();
                    Picasso.get().load(imgURL2).into(img2);
                    txt2.setText(snapshot.child(UserID).child("faces").child("1").child("sum").getValue().toString());
                    imgURL3 = snapshot.child(UserID).child("faces").child("2").child("url").getValue().toString();
                    Picasso.get().load(imgURL3).into(img3);
                    txt3.setText(snapshot.child(UserID).child("faces").child("2").child("sum").getValue().toString());
                    char result = txt1.getText().charAt(0);
                    if(result == 'W'){
                        txt1.setTextColor(0xFF02FF01);
                    }
                    else{
                        txt1.setTextColor(Color.RED);
                    }
                    char result2 = txt2.getText().charAt(0);
                    if(result2 == 'W'){
                        txt2.setTextColor(0xFF02FF01);
                    }
                    else{
                        txt2.setTextColor(Color.RED);
                    }
                    char result3 = txt3.getText().charAt(0);
                    if(result3 == 'W'){
                        txt3.setTextColor(0xFF02FF01);
                    }
                    else{
                        txt3.setTextColor(Color.RED);
                    }
                }
                if( min_photos >= 4) {
                    imgURL1 = snapshot.child(UserID).child("faces").child("0").child("url").getValue().toString();
                    Picasso.get().load(imgURL1).into(img1);
                    txt1.setText(snapshot.child(UserID).child("faces").child("0").child("sum").getValue().toString());
                    imgURL2 = snapshot.child(UserID).child("faces").child("1").child("url").getValue().toString();
                    Picasso.get().load(imgURL2).into(img2);
                    txt2.setText(snapshot.child(UserID).child("faces").child("1").child("sum").getValue().toString());
                    imgURL3 = snapshot.child(UserID).child("faces").child("2").child("url").getValue().toString();
                    Picasso.get().load(imgURL3).into(img3);
                    txt3.setText(snapshot.child(UserID).child("faces").child("2").child("sum").getValue().toString());
                    imgURL4 = snapshot.child(UserID).child("faces").child("3").child("url").getValue().toString();
                    Picasso.get().load(imgURL4).into(img4);
                    txt4.setText(snapshot.child(UserID).child("faces").child("3").child("sum").getValue().toString());
                    char result = txt1.getText().charAt(0);
                    if(result == 'W'){
                        txt1.setTextColor(0xFF02FF01);
                    }
                    else{
                        txt1.setTextColor(Color.RED);
                    }
                    char result2 = txt2.getText().charAt(0);
                    if(result2 == 'W'){
                        txt2.setTextColor(0xFF02FF01);
                    }
                    else{
                        txt2.setTextColor(Color.RED);
                    }
                    char result3 = txt3.getText().charAt(0);
                    if(result3 == 'W'){
                        txt3.setTextColor(0xFF02FF01);
                    }
                    else{
                        txt3.setTextColor(Color.RED);
                    }
                    char result4 = txt4.getText().charAt(0);
                    if(result4 == 'W'){
                        txt4.setTextColor(0xFF02FF01);
                    }
                    else{
                        txt4.setTextColor(Color.RED);
                    }
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
        del1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reference.child(UserID).child("faces").child("0").child("url").setValue("https://www.exaltedchristchurch.com/wp-content/uploads/2017/02/blank-profile-picture-png-transparent.png");
                reference.child(UserID).child("faces").child("0").child("sum").setValue(" ");
                Picasso.get().load("https://www.exaltedchristchurch.com/wp-content/uploads/2017/02/blank-profile-picture-png-transparent.png").into(img1);
                txt1.setText(" ");
                COUNT--;
                reference.child(UserID).child("img_count").setValue(""+COUNT);
                reference.child(UserID).child("faces").child("min_show").setValue(""+COUNT);

            }
        });
        del2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reference.child(UserID).child("faces").child("1").child("url").setValue("https://www.exaltedchristchurch.com/wp-content/uploads/2017/02/blank-profile-picture-png-transparent.png");
                reference.child(UserID).child("faces").child("1").child("sum").setValue(" ");
                Picasso.get().load("https://www.exaltedchristchurch.com/wp-content/uploads/2017/02/blank-profile-picture-png-transparent.png").into(img2);
                txt2.setText(" ");
                reference.child(UserID).child("img_count").setValue(""+COUNT);
                reference.child(UserID).child("faces").child("min_show").setValue(""+COUNT);
            }
        });
        del3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reference.child(UserID).child("faces").child("2").child("url").setValue("https://www.exaltedchristchurch.com/wp-content/uploads/2017/02/blank-profile-picture-png-transparent.png");
                reference.child(UserID).child("faces").child("2").child("sum").setValue(" ");
                Picasso.get().load("https://www.exaltedchristchurch.com/wp-content/uploads/2017/02/blank-profile-picture-png-transparent.png").into(img3);
                txt3.setText(" ");
                reference.child(UserID).child("img_count").setValue(""+COUNT);
                reference.child(UserID).child("faces").child("min_show").setValue(""+COUNT);
            }
        });
        del4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reference.child(UserID).child("faces").child("3").child("url").setValue("https://www.exaltedchristchurch.com/wp-content/uploads/2017/02/blank-profile-picture-png-transparent.png");
                reference.child(UserID).child("faces").child("3").child("sum").setValue(" ");
                Picasso.get().load("https://www.exaltedchristchurch.com/wp-content/uploads/2017/02/blank-profile-picture-png-transparent.png").into(img4);
                txt4.setText(" ");
                reference.child(UserID).child("img_count").setValue(""+COUNT);
                reference.child(UserID).child("faces").child("min_show").setValue(""+COUNT);
            }
        });
        share1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img1.setDrawingCacheEnabled(true);
                BitmapDrawable bitmapDrawable = (BitmapDrawable) img1.getDrawable();
                Bitmap bitmap = img1.getDrawingCache();
                File root = Environment.getExternalStorageDirectory();
                File cachePath = new File(root.getAbsolutePath() + "/DCIM/Camera/image.jpg");
                try {
                    cachePath.createNewFile();
                    FileOutputStream ostream = new FileOutputStream(cachePath);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, ostream);
                    ostream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Intent share = new Intent(Intent.ACTION_SEND);
                share.setType("image/*");
                share.putExtra(Intent.EXTRA_TEXT, "Played at the roulette, and "+txt1.getText().toString());
                share.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(cachePath));
                startActivity(Intent.createChooser(share,"Share via"));


            }
        });
        share2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img2.setDrawingCacheEnabled(true);
                BitmapDrawable bitmapDrawable = (BitmapDrawable) img2.getDrawable();
                Bitmap bitmap = img2.getDrawingCache();
                File root = Environment.getExternalStorageDirectory();
                File cachePath = new File(root.getAbsolutePath() + "/DCIM/Camera/image.jpg");
                try {
                    cachePath.createNewFile();
                    FileOutputStream ostream = new FileOutputStream(cachePath);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, ostream);
                    ostream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Intent share = new Intent(Intent.ACTION_SEND);
                share.setType("image/*");
                share.putExtra(Intent.EXTRA_TEXT, "Played at the roulette, and "+txt2.getText().toString());
                share.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(cachePath));
                startActivity(Intent.createChooser(share,"Share via"));


            }
        });
        share3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img3.setDrawingCacheEnabled(true);
                BitmapDrawable bitmapDrawable = (BitmapDrawable) img3.getDrawable();
                Bitmap bitmap = img3.getDrawingCache();
                File root = Environment.getExternalStorageDirectory();
                File cachePath = new File(root.getAbsolutePath() + "/DCIM/Camera/image.jpg");
                try {
                    cachePath.createNewFile();
                    FileOutputStream ostream = new FileOutputStream(cachePath);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, ostream);
                    ostream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Intent share = new Intent(Intent.ACTION_SEND);
                share.setType("image/*");
                share.putExtra(Intent.EXTRA_TEXT, "Played at the roulette, and "+txt3.getText().toString());
                share.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(cachePath));
                startActivity(Intent.createChooser(share,"Share via"));


            }
        });
        share4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img4.setDrawingCacheEnabled(true);
                BitmapDrawable bitmapDrawable = (BitmapDrawable) img4.getDrawable();
                Bitmap bitmap = img4.getDrawingCache();
                File root = Environment.getExternalStorageDirectory();
                File cachePath = new File(root.getAbsolutePath() + "/DCIM/Camera/image.jpg");
                try {
                    cachePath.createNewFile();
                    FileOutputStream ostream = new FileOutputStream(cachePath);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, ostream);
                    ostream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Intent share = new Intent(Intent.ACTION_SEND);
                share.setType("image/*");
                share.putExtra(Intent.EXTRA_TEXT, "Played at the roulette, and "+txt4.getText().toString());
                share.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(cachePath));
                startActivity(Intent.createChooser(share,"Share via"));


            }
        });
    }



    private void shareImage(Bitmap bitmap){
        // save bitmap to cache directory
        try {
            File cachePath = new File(getCacheDir(), "img1");
            cachePath.mkdirs(); // don't forget to make the directory
            FileOutputStream stream = new FileOutputStream(cachePath + "/image.png"); // overwrites this image every time
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            stream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        File imagePath = new File(getCacheDir(), "imageview");
        File newFile = new File(imagePath, "image.png");
        Uri contentUri = FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID + ".fileprovider", newFile);

        if (contentUri != null) {
            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION); // temp permission for receiving app to read this file
            shareIntent.setDataAndType(contentUri, getContentResolver().getType(contentUri));
            shareIntent.putExtra(Intent.EXTRA_STREAM, contentUri);
            shareIntent.setType("image/png");
            startActivity(Intent.createChooser(shareIntent, "Choose an app"));
        }
    }

}

//    private void shareImage() {
//        try {
//            String s = "played at the RouletteApp and, "+txt1.getText().toString();
//            File file = new File(getExternalCacheDir(),"sample.png");
//            FileOutputStream fOut = new FileOutputStream(file);
//            bitmap.compress(Bitmap.CompressFormat.PNG,100,fOut);
//            fOut.flush();
//            fOut.close();
//            file.setReadable(true,false);
//            Intent intent = new Intent(Intent.ACTION_SEND);
//            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            intent.putExtra(Intent.EXTRA_TEXT,s);
//            intent.putExtra(Intent.EXTRA_STREAM,Uri.fromFile(file));
//            intent.setType("image/png");
//            startActivity(Intent.createChooser(intent,"Share via"));
//        }
//        catch (Exception e){
//            Toast.makeText(this,e.getMessage(),Toast.LENGTH_LONG).show();
//
//        }
//    }
//
//    private void shareImageAndText(Bitmap bitmap) throws URISyntaxException {
//
//        Uri uri = getmageToShare(bitmap);
//        Intent intent = new Intent(Intent.ACTION_SEND);
//        // putting uri of image to be shared
//        intent.putExtra(Intent.EXTRA_STREAM, uri);
//        // adding text to share
//        intent.putExtra(Intent.EXTRA_TEXT, "played at the roulette, and "+txt1.getText().toString());
//        // Add subject Here
//        intent.putExtra(Intent.EXTRA_SUBJECT, "Subject Here");
//        // setting type to image
//        intent.setType("image/png");
//        // calling startactivity() to share
//        startActivity(Intent.createChooser(intent, "Share Via"));
//    }
//    private Uri getmageToShare(Bitmap bitmap) {
//        File imagefolder = new File(getCacheDir(), "images");
//        Uri uri = null;
//        try {
//            imagefolder.mkdirs();
//            File file = new File(imagefolder, "shared_image.png");
//            FileOutputStream outputStream = new FileOutputStream(file);
//            bitmap.compress(Bitmap.CompressFormat.PNG, 90, outputStream);
//            outputStream.flush();
//            outputStream.close();
//            uri = FileProvider.getUriForFile(this, "com.example.roulette.fileprovider", file);
//        } catch (Exception e) {
//            Toast.makeText(this, "" + e.getMessage(), Toast.LENGTH_LONG).show();
//            System.out.println(e.getMessage());
//        }
//        return uri;
//    }
    


