package com.example.roulette;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.admin.DeviceAdminService;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.cast.framework.media.ImagePicker;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Table extends AppCompatActivity {
    private TextView textView,user_amount,bet_view;
    private Button spin,bet,profile,cam;
    private ImageView roulette_image;
    private Uri image;
    public static final int CAMERA_ACTION_CODE = 1;
    private final int CAMERA_CODE = 102;
    private int BET_SUM;
    private Random r;
    int LAST_WIN = 0;
    int LAST_BET = 0;
    private int degree ,new_amount, win ,img_counter;
    private static int NUMBER;
    private boolean isSpinning = false;
    private StorageReference storageRef,mStorage;
    private FirebaseStorage storage;
    private FirebaseUser user;
    private DatabaseReference reference,boss_reference;
    private String UserID,str_bets;
    private static final String [] sectors = {"32 red","15 black","19 red","4 black","21 red","2 black",
            "25 red","17 black","34 red","6 black","27 red","13 black","36 red","11 black","30 red","8 black",
            "23 red","10 black","5 red","24 black","16 red","33 black","1 red","20 black","14 red","31 black",
            "9 red","22 black","18 red","29 black","7 red","28 black","12 red","35 black","3 red","0"};
    private static final int [] numbers = {32,15,19,4,21,2,25,17,34,6,27,13,36,11,30,8,23,10,5,24,
                                            16,33,1,20,14,31,9,22,18,29,7,28,12,35,3,26,0};
    private static final int [] sectorsDegrees = new int [sectors.length];
    private static  final Random random = new Random();
     @Override
    protected void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
         getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
         setContentView(R.layout.activity_table);
////////////////////////////////////////////
         boss_reference = FirebaseDatabase.getInstance().getReference("Boss");
         roulette_image = findViewById(R.id.imageView) ;
         bet_view = findViewById(R.id.User_bet);
         reference = FirebaseDatabase.getInstance().getReference("Users");
         storageRef = FirebaseStorage.getInstance().getReference("users");
         user = FirebaseAuth.getInstance().getCurrentUser();
         user_amount =  findViewById(R.id.user_amount_play);
         UserID = user.getUid();
         textView = findViewById(R.id.textView7) ;
         spin = findViewById(R.id.spin);
         str_bets = "";
         win = 0;
         BET_SUM = 0;

                reference.child(UserID).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        img_counter = (Integer.parseInt(snapshot.child("img_count").getValue().toString())) % 4;
                        //set user balance Textview
                        user_amount.setText(snapshot.child("balance").getValue().toString());
                        //adding bets to bet view textview
                        for(int i = 0; i<37; i++){
                            if(Integer.parseInt(snapshot.child("bet").child(""+i).getValue().toString()) > 0) {
                                str_bets += i +" ";
                                BET_SUM+= Integer.parseInt(snapshot.child("bet").child(""+i).getValue().toString());
                            }
                        }
                        if(Integer.parseInt(snapshot.child("bet").child("odd").getValue().toString()) > 0){str_bets += "odd" +" ";}
                        if(Integer.parseInt(snapshot.child("bet").child("even").getValue().toString()) > 0){str_bets += "even" +" ";}
                        if(Integer.parseInt(snapshot.child("bet").child("red").getValue().toString()) > 0){str_bets += "red" +" ";}
                        if(Integer.parseInt(snapshot.child("bet").child("black").getValue().toString()) > 0){str_bets += "black" +" ";}
                        if(Integer.parseInt(snapshot.child("bet").child("high").getValue().toString()) > 0){str_bets += "19-36" +" ";}
                        if(Integer.parseInt(snapshot.child("bet").child("low").getValue().toString()) > 0){str_bets += "1-18" +" ";}
                        BET_SUM+= Integer.parseInt(snapshot.child("bet").child("odd").getValue().toString());
                        BET_SUM+= Integer.parseInt(snapshot.child("bet").child("even").getValue().toString());
                        BET_SUM+= Integer.parseInt(snapshot.child("bet").child("red").getValue().toString());
                        BET_SUM+= Integer.parseInt(snapshot.child("bet").child("black").getValue().toString());
                        BET_SUM+= Integer.parseInt(snapshot.child("bet").child("high").getValue().toString());
                        BET_SUM+= Integer.parseInt(snapshot.child("bet").child("low").getValue().toString());
                        bet_view.setText(str_bets);

                    }
            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        });
                getDegree();
        spin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //check if user placed bet
                if(bet_view.getText().toString().isEmpty() || bet_view.getText().toString().equals("place bet to play")){
                    Toast.makeText(Table.this,"place a bet to play",Toast.LENGTH_SHORT).show();
                    return;
                }
                //spin
                if(!isSpinning){
                    spin();
                    isSpinning = true;
                }
            }

            private void spin() {
                //image rotation
                degree = random.nextInt(sectors.length - 1);
                RotateAnimation rotate = new RotateAnimation(0, (360 * sectors.length) + sectorsDegrees[degree],
                        RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f);
                rotate.setDuration(3600);
                rotate.setFillAfter(true);
                rotate.setInterpolator(new DecelerateInterpolator());
                rotate.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) { }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        boss_reference.addListenerForSingleValueEvent(new ValueEventListener() {
                            //update the games number for the boos
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                int games = Integer.parseInt(snapshot.child("games").getValue().toString())+1;
                                boss_reference.child("games").setValue(""+games);
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError error) { }
                        });
                        //set and show the drawn number
                        String number = sectors[sectors.length - (degree + 1)];
                        textView.setText(number);
                        NUMBER = numbers[sectors.length - (degree + 1)];

                        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Users");
                        reference.child(UserID).addListenerForSingleValueEvent(new ValueEventListener() {
                            //check if user win
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                //check the money user bet on the drawn number and stats update
                                String str2 = snapshot.child("bet").child(""+NUMBER).getValue().toString();
                                int user_games = Integer.parseInt(snapshot.child("games").getValue().toString()) + 1;
                                reference.child(UserID).child("games").setValue(""+user_games);
                                win = Integer.parseInt(str2)*32;
                                if(NUMBER > 0) {
                                    if (NUMBER % 2 == 0) {
                                        win += 2 * Integer.parseInt(snapshot.child("bet").child("even").getValue().toString().trim());
                                    }
                                    else{
                                        win += 2 * Integer.parseInt(snapshot.child("bet").child("odd").getValue().toString().trim());
                                    }

                                    if(isRed(NUMBER)){
                                        win += 2 * Integer.parseInt(snapshot.child("bet").child("red").getValue().toString().trim());
                                    }
                                    else{
                                        win += 2 * Integer.parseInt(snapshot.child("bet").child("black").getValue().toString().trim());
                                    }
                                    if(NUMBER> 18 && NUMBER <=36){
                                        win += 2 * Integer.parseInt(snapshot.child("bet").child("high").getValue().toString().trim());
                                    }
                                    else{
                                        win += 2 * Integer.parseInt(snapshot.child("bet").child("low").getValue().toString().trim());

                                    }
                                }
                                //if user wins money, updates user balance and stats
                                if(win > 0 ){
                                    int user_wins = Integer.parseInt(snapshot.child("wins").getValue().toString().trim()) + 1;
                                    int user_wins_money = Integer.parseInt(snapshot.child("wins_money").getValue().toString()) + win;
                                    reference.child(UserID).child("wins").setValue(""+user_wins);
                                    reference.child(UserID).child("wins_money").setValue(""+user_wins_money);
                                    new_amount = win + Integer.parseInt(snapshot.child("balance").getValue().toString());
                                    reference.child(UserID).child("balance").setValue(""+new_amount);
                                    Toast.makeText(Table.this,"win!!!, your prize is "+win+"$",Toast.LENGTH_LONG).show();
                                    reference.child(UserID).child("last_win").setValue(""+win);

                                    boss_reference.addListenerForSingleValueEvent(new ValueEventListener() {
                                        //update boss balance and stats
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            long bos_balance = Long.parseLong(snapshot.child("balance").getValue().toString());
                                            long new_sum = bos_balance - win;;
                                            int wins_count = Integer.parseInt(snapshot.child("wins").getValue().toString()) +1;
                                            boss_reference.child("wins").setValue(""+wins_count);
                                            boss_reference.child("balance").setValue(""+new_sum);
                                            int money_won = Integer.parseInt(snapshot.child("money_spent").getValue().toString()) + win;
                                            boss_reference.child("money_spent").setValue(""+money_won);
                                            user_amount.setText(""+new_amount);
                                            bet_view.setText("place bet to play");

                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) { }
                                    });
                                    startActivity(new Intent(Table.this,Winner_screen.class));
                                }
                                else{
//                                    Toast.makeText(Table.this,"LOSER!",Toast.LENGTH_LONG).show();
                                    bet_view.setText("place bet to play");
                                    reference.child(UserID).child("last_win").setValue("0");
                                    startActivity(new Intent(Table.this,Loser_screen.class));

                                }
                                boss_reference.addListenerForSingleValueEvent(new ValueEventListener() {
                                    //update games stats
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        int new_val = Integer.parseInt(snapshot.child("bets").child(""+NUMBER).getValue().toString().trim()) + 1;
                                        boss_reference.child("bets").child(""+NUMBER).setValue(""+new_val);
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) { }
                                });
                                //reset user bets data in firebase
                                for(int i = 0; i<37; i++){
                                    reference.child(UserID).child("bet").child(""+i).setValue("0");
                                }
                                reference.child(UserID).child("bet").child("odd").setValue("0");
                                reference.child(UserID).child("bet").child("even").setValue("0");
                                reference.child(UserID).child("bet").child("red").setValue("0");
                                reference.child(UserID).child("bet").child("black").setValue("0");
                                reference.child(UserID).child("bet").child("high").setValue("0");
                                reference.child(UserID).child("bet").child("low").setValue("0");
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError error) { }
                        });
                        isSpinning = false;
                    }
                    @Override
                    public void onAnimationRepeat(Animation animation) { }
                });
                roulette_image.startAnimation(rotate);
            }
        });
            bet = findViewById(R.id.bet);
            bet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(Table.this,betTable.class));
                }
            });
            profile = findViewById(R.id.profile_btn);
            profile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(Table.this,user_bio.class));
                }
            });

//             @Override
//             public void onActivityResult(ActivityResult result) {
//                if(result.getResultCode() == CAMERA_ACTION_CODE && result.getResultCode() == RESULT_OK && result.getData() != null){
//                    Bundle bundle = result.getData().getExtras();
//                    Bitmap bitmap = (Bitmap) bundle.get("data");
//
//             }
//         }});
//             @Override
//             public void onActivityResult(Uri result) {
//                 if (result != null){
//                     image = result;
//                     storageRef.child(UserID).child("games_images").child(""+img_counter).putFile(image).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                         @Override
//                         public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                             storageRef.child(UserID).child("games_images").child(""+img_counter).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                                 @Override
//                                 public void onSuccess(Uri uri) {
//                                     int pos = img_counter%4;
//                                     reference.child(UserID).child("imgUrl").child(""+pos).setValue(uri.toString());
//                                     img_counter++;
//                                     reference.child(UserID).child("img_count").setValue(""+img_counter);
//                                 }
//                             });
//                         }
//                     });
//                 }
//
//                 }

//         });

            cam = findViewById(R.id.camera);
            cam.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String file_name = "photo";
                    checkPermission();
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent,CAMERA_CODE);
                }
            });

     }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == 102){
            if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,CAMERA_CODE);
            }
        }
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 102){
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            imageBitmap.compress(Bitmap.CompressFormat.JPEG,100,bytes);
            String path = MediaStore.Images.Media.insertImage(getApplicationContext().getContentResolver(),imageBitmap,"val",null);
            Uri img_uri = Uri.parse(path);

            int pos = img_counter%4;
            storageRef.child(UserID).child("games_images").child(""+pos).putFile( img_uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    storageRef.child(UserID).child("games_images").child(""+img_counter).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            reference.child(UserID).child("faces").child(""+pos).child("url").setValue(uri.toString());
                            reference.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    LAST_BET = Integer.parseInt(snapshot.child(UserID).child("last_bet").getValue().toString());
                                    LAST_WIN = Integer.parseInt(snapshot.child(UserID).child("last_win").getValue().toString());
                                    if(LAST_WIN > 0){
                                        reference.child(UserID).child("faces").child(""+pos).child("sum").setValue("WON "+LAST_WIN+"$");
                                    }
                                    if(LAST_WIN == 0)
                                    {
                                        reference.child(UserID).child("faces").child(""+pos).child("sum").setValue("LOST "+LAST_BET+"$");

                                    }

                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                            img_counter+= 1;
                            reference.child(UserID).child("img_count").setValue(""+img_counter);
                        }
                    });

                }
            });
        }
    }

    private void checkPermission(){
         if(ContextCompat.checkSelfPermission(Table.this,Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
             ActivityCompat.requestPermissions(Table.this,new String[]{Manifest.permission.CAMERA},101);
         }

     }


    private void getDegree(){
        int sectorDegree = 360/sectors.length;
        for (int i=0 ; i< sectors.length; i++){
            sectorsDegrees[i] = (i+1) * sectorDegree;
        }

    }

    private boolean isRed(int n){
            if( n == 1|| n == 3|| n == 5||n == 7||n == 9||n == 12||n == 14||n == 16||n == 18||n == 19||
                n == 21||n == 23||n == 25|| n == 27 || n == 30|| n == 32|| n == 34|| n == 36){
                return true;
         }
            else{
                return false;
            }
    }

}
