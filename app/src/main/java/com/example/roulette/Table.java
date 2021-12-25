package com.example.roulette;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.roulette.Loser_screen;
import com.example.roulette.R;
import com.example.roulette.Winner_screen;
import com.example.roulette.betTable;
import com.example.roulette.user_bio;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Properties;
import java.util.Random;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Table extends AppCompatActivity {
    private SensorManager sm;
    private float acelVal; //current accelaration value and gravity
    private float acelLast;// last acceleration value and gravity
    private float shake;// acceration value different from gravity
    private String currntPhotoPath;

    private TextView textView,user_amount,bet_view,user_bets_biew;
    private Button spin,bet,profile,cam,last10,mybet;
    private ImageView roulette_image;
    private Uri image;
    public static final int CAMERA_ACTION_CODE = 1;
    private final int CAMERA_CODE = 102;
    private static final float FACTOR = 4.86f;
    private int BET_SUM;
    private String [] nums = new String[10];
    private Random r;
    int LAST_WIN = 0;
    int LAST_BET = 0;
    String LAST_NUM = "";
    private Dialog dialog;
    private MediaPlayer mediaPlayer;
    private int degree, degree_old ,new_amount, win ,img_counter,round_win, index;
    private static int NUMBER;
    private boolean isSpinning = false;
    private static  boolean spinned = false;
    private StorageReference storageRef,mStorage;
    private FirebaseStorage storage;
    private FirebaseUser user;
    private DatabaseReference reference,boss_reference;
    private String UserID,str_bets,user_email,EMAIL,PASS;
    public int [] MAP = new int [43];

    private static final String [] sectors = {"32 red","15 black","19 red","4 black","21 red","2 black",
            "25 red","17 black","34 red","6 black","27 red","13 black","36 red","11 black","30 red","8 black",
            "23 red","10 black","5 red","24 black","16 red","33 black","1 red","20 black","14 red","31 black",
            "9 red","22 black","18 red","29 black","7 red","28 black","12 red","35 black","3 red", "26 black","0"};
    private static final int [] numbers = {32,15,19,4,21,2,25,17,34,6,27,13,36,11,30,8,23,10,5,24,
            16,33,1,20,14,31,9,22,18,29,7,28,12,35,3,26,0};
    private static final int [] sectorsDegrees = new int [sectors.length];
    Handler handler = new Handler();
    private static  final Random random = new Random();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_table);
        sm=(SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sm.registerListener(sensorListener,sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
        acelVal = SensorManager.GRAVITY_EARTH;
        acelLast = SensorManager.GRAVITY_EARTH;
        shake = 0.00f;
        ////////////////////////////////////////////
        dialog = new Dialog(this);
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
        EMAIL = "roulleteboss@gmail.com";
        PASS = "uhgnjmsmvfppdmxz";

        r = new Random();
        degree = 0;
        degree_old = 0;



        boss_reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                LAST_NUM = snapshot.child("last_num").getValue().toString();
                if(Integer.parseInt(LAST_NUM) == 0)
                {
                    textView.setText(LAST_NUM);
                }
                else if(isRed(Integer.parseInt(LAST_NUM))){
                    textView.setText(LAST_NUM+" Red");
                }
                else{
                    textView.setText(LAST_NUM+" Black");

                }
                for(int i =0; i<10; i++){
                    nums[i]= snapshot.child("last10").child(""+i).getValue().toString();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        mybet = findViewById(R.id.Table_user_bets);
        mybet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                lp.copyFrom(dialog.getWindow().getAttributes());
                lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                lp.height = WindowManager.LayoutParams.MATCH_PARENT ;
                int width = (int)(getResources().getDisplayMetrics().widthPixels*1);
                int height = (int)(getResources().getDisplayMetrics().heightPixels*0.40);
                dialog.show();
                dialog.getWindow().setLayout(width,height);
                dialog.setContentView(R.layout.my_bets_view);
                user_bets_biew = dialog.findViewById(R.id.final_bets33);
                String show = "";
                for (int i = 0; i< 37 ; i++) {
                    if (MAP[i] > 0) {
                        show += i + "->" + MAP[i] + "$\n";
                    }
                }
                if(MAP [37] >0){
                    show += "ODD->" +MAP[37] +"$\n";
                }
                if(MAP[38] > 0){
                    show += "EVEN->" + MAP[38]+"$\n";
                }
                if(MAP[39] > 0){
                    show += "RED->" + MAP[39]+"$\n";
                }
                if(MAP[40] > 0){
                    show += "BLACK->" + MAP[40]+"$\n";
                }
                if(MAP[41] > 0){
                    show += "19-36->" + MAP[41]+"$\n";
                }
                if(MAP[42] > 0){
                    show += "1-18->" + MAP[42]+"$\n";
                }
                Button exit_btn = dialog.findViewById(R.id.exit_btn_bets);
                exit_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                user_bets_biew.setText(show);
                user_bets_biew.setMovementMethod(new ScrollingMovementMethod());

            }
        });
        last10 = findViewById(R.id.table_last10);
        last10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                lp.copyFrom(dialog.getWindow().getAttributes());
                lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                lp.height = WindowManager.LayoutParams.MATCH_PARENT ;
                int width = (int)(getResources().getDisplayMetrics().widthPixels*1);
                int height = (int)(getResources().getDisplayMetrics().heightPixels*0.40);
                dialog.show();
                dialog.getWindow().setLayout(width,height);
                dialog.setContentView(R.layout.last_ten_view);
                Button exit = dialog.findViewById(R.id.exit_btn_10);
                exit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                TextView textView1 = dialog.findViewById(R.id.last1);
                int num = Integer.parseInt(nums[0]);
                if(num == 0 ){
                    textView1.setText(nums[0]);
                    textView1.setBackgroundColor(Color.GREEN);
                }
                else if(isRed(num) ){
                    textView1.setText(nums[0]);
                    textView1.setBackgroundColor(Color.RED);
                }
                else{
                    textView1.setText(nums[0]);
                    textView1.setBackgroundColor(Color.BLACK);
                }
                TextView textView2 = dialog.findViewById(R.id.last2);
                int num2 = Integer.parseInt(nums[1]);
                if(num == 0 ){
                    textView2.setText(nums[1]);
                    textView2.setBackgroundColor(Color.GREEN);
                }
                else if(isRed(num2) ){
                    textView2.setText(nums[1]);
                    textView2.setBackgroundColor(Color.RED);
                }
                else{
                    textView2.setText(nums[1]);
                    textView2.setBackgroundColor(Color.BLACK);
                }
                TextView textView3 = dialog.findViewById(R.id.last3);
                int num3 = Integer.parseInt(nums[2]);
                if(num3 == 0 ){
                    textView3.setText(nums[2]);
                    textView3.setBackgroundColor(Color.GREEN);
                }
                else if(isRed(num3) ){
                    textView3.setText(nums[2]);
                    textView3.setBackgroundColor(Color.RED);
                }
                else{
                    textView3.setText(nums[2]);
                    textView3.setBackgroundColor(Color.BLACK);
                }
                TextView textView4 = dialog.findViewById(R.id.last4);
                int num4 = Integer.parseInt(nums[3]);
                if(num4 == 0 ){
                    textView4.setText(nums[3]);
                    textView4.setBackgroundColor(Color.GREEN);
                }
                else if(isRed(num4) ){
                    textView4.setText(nums[3]);
                    textView4.setBackgroundColor(Color.RED);
                }
                else{
                    textView4.setText(nums[3]);
                    textView4.setBackgroundColor(Color.BLACK);
                }
                TextView textView5 = dialog.findViewById(R.id.last5);
                int num5 = Integer.parseInt(nums[4]);
                if(num5 == 0 ){
                    textView5.setText(nums[4]);
                    textView5.setBackgroundColor(Color.GREEN);
                }
                else if(isRed(num5) ){
                    textView5.setText(nums[4]);
                    textView5.setBackgroundColor(Color.RED);
                }
                else{
                    textView5.setText(nums[4]);
                    textView5.setBackgroundColor(Color.BLACK);
                }
                TextView textView6 = dialog.findViewById(R.id.last6);
                int num6 = Integer.parseInt(nums[5]);
                if(num6 == 0 ){
                    textView6.setText(nums[5]);
                    textView6.setBackgroundColor(Color.GREEN);
                }
                else if(isRed(num6) ){
                    textView6.setText(nums[5]);
                    textView6.setBackgroundColor(Color.RED);
                }
                else{
                    textView6.setText(nums[5]);
                    textView6.setBackgroundColor(Color.BLACK);
                }
                TextView textView7 = dialog.findViewById(R.id.last7);
                int num7 = Integer.parseInt(nums[6]);
                if(num7 == 0 ){
                    textView7.setText(nums[6]);
                    textView7.setBackgroundColor(Color.GREEN);
                }
                else if(isRed(num7) ){
                    textView7.setText(nums[6]);
                    textView7.setBackgroundColor(Color.RED);
                }
                else{
                    textView7.setText(nums[6]);
                    textView7.setBackgroundColor(Color.BLACK);
                }
                TextView textView8 = dialog.findViewById(R.id.last8);
                int num8 = Integer.parseInt(nums[7]);
                if(num8 == 0 ){
                    textView8.setText(nums[7]);
                    textView8.setBackgroundColor(Color.GREEN);
                }
                else if(isRed(num8) ){
                    textView8.setText(nums[7]);
                    textView8.setBackgroundColor(Color.RED);
                }
                else{
                    textView8.setText(nums[7]);
                    textView8.setBackgroundColor(Color.BLACK);
                }
                TextView textView9 = dialog.findViewById(R.id.last9);
                int num9 = Integer.parseInt(nums[8]);
                if(num9 == 0 ){
                    textView9.setText(nums[8]);
                    textView9.setBackgroundColor(Color.GREEN);
                }
                else if(isRed(num9) ){
                    textView9.setText(nums[8]);
                    textView9.setBackgroundColor(Color.RED);
                }
                else{
                    textView9.setText(nums[8]);
                    textView9.setBackgroundColor(Color.BLACK);
                }
                TextView textView10 = dialog.findViewById(R.id.last10);
                int num10 = Integer.parseInt(nums[9]);
                if(num10 == 0 ){
                    textView10.setText(nums[9]);
                    textView10.setBackgroundColor(Color.GREEN);
                }
                else if(isRed(num10) ){
                    textView10.setText(nums[9]);
                    textView10.setBackgroundColor(Color.RED);
                }
                else{
                    textView10.setText(nums[9]);
                    textView10.setBackgroundColor(Color.BLACK);
                }
            }
        });

        reference.child(UserID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                img_counter = (Integer.parseInt(snapshot.child("img_count").getValue().toString())) % 4;
                //set user balance Textview
                user_amount.setText(snapshot.child("balance").getValue().toString());
                //adding bets to bet view textview
                for(int i = 0; i<37; i++){
                    MAP[i] = Integer.parseInt(snapshot.child("bet").child(""+i).getValue().toString());
                    if(Integer.parseInt(snapshot.child("bet").child(""+i).getValue().toString()) > 0) {
                        str_bets += i +" ";
                        BET_SUM+= Integer.parseInt(snapshot.child("bet").child(""+i).getValue().toString());
                    }
                }
                if(Integer.parseInt(snapshot.child("bet").child("odd").getValue().toString()) > 0){
                    MAP[37] = Integer.parseInt(snapshot.child("bet").child("odd").getValue().toString());
                    str_bets += "odd" +" ";}
                if(Integer.parseInt(snapshot.child("bet").child("even").getValue().toString()) > 0){
                    MAP[38] = Integer.parseInt(snapshot.child("bet").child("even").getValue().toString());
                    str_bets += "even" +" ";}
                if(Integer.parseInt(snapshot.child("bet").child("red").getValue().toString()) > 0){
                    MAP[39] = Integer.parseInt(snapshot.child("bet").child("red").getValue().toString());
                    str_bets += "red" +" ";}
                if(Integer.parseInt(snapshot.child("bet").child("black").getValue().toString()) > 0){
                    MAP[40] = Integer.parseInt(snapshot.child("bet").child("black").getValue().toString());
                    str_bets += "black" +" ";
                }
                if(Integer.parseInt(snapshot.child("bet").child("high").getValue().toString()) > 0){
                    MAP[41] = Integer.parseInt(snapshot.child("bet").child("high").getValue().toString());
                    str_bets += "19-36" +" ";}
                if(Integer.parseInt(snapshot.child("bet").child("low").getValue().toString()) > 0){
                    MAP[42] = Integer.parseInt(snapshot.child("bet").child("low").getValue().toString());
                    str_bets += "1-18" +" ";}
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
//                check if user placed bet
                if(bet_view.getText().toString().isEmpty() || bet_view.getText().toString().equals("place bet to play")){
                    Toast.makeText(Table.this,"place a bet to play",Toast.LENGTH_SHORT).show();
                    return;
                }
                //spin
                if(!isSpinning){
                    spin();
                    isSpinning = true;
                    spinned = true;

                }
            }

            private void spin() {
                //image rotation

                degree_old = degree % 360;
                degree = r.nextInt(3600) + 720;
                //degree = random.nextInt(sectors.length - 1);
                RotateAnimation rotate = new RotateAnimation(0, degree,
                        RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f);
                rotate.setDuration(11500);
                mediaPlayer = MediaPlayer.create(Table.this,R.raw.spin_sound);
                mediaPlayer.start();
                rotate.setFillAfter(true);
                rotate.setInterpolator(new DecelerateInterpolator());
                rotate.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) { }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        index = getNum(360 - (degree % 360));
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
                        String number = sectors[index];
                        textView.setText(sectors[index]);
                        NUMBER = numbers[index];

                        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Users");
                        reference.child(UserID).addListenerForSingleValueEvent(new ValueEventListener() {
                            //check if user win
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                user_email = snapshot.child("email").getValue().toString();
                                //check the money user bet on the drawn number and stats update
                                String str2 = snapshot.child("bet").child(""+NUMBER).getValue().toString();
                                int user_games = Integer.parseInt(snapshot.child("games").getValue().toString()) + 1;
                                reference.child(UserID).child("games").setValue(""+user_games);
                                win = Integer.parseInt(str2)*32;
                                boss_reference.child("last_num").setValue(""+NUMBER);
                                boss_reference.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        int position = (Integer.parseInt(snapshot.child("last10").child("pos").getValue().toString()))%10 ;
                                        boss_reference.child("last10").child(""+position).setValue(""+NUMBER);
                                        position++;
                                        boss_reference.child("last10").child("pos").setValue(""+position);
                                        LAST_NUM = (snapshot.child("last_num").getValue().toString());
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) { }
                                });
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
                                    round_win = win;
                                    int biggest_win = Integer.parseInt(snapshot.child("biggest_win").getValue().toString());
                                    if(win > biggest_win){
                                        reference.child(UserID).child("biggest_win").setValue("" + win);
                                    }
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
                                            int biggest_win = Integer.parseInt(snapshot.child("biggest_win").getValue().toString());
                                            if(win > biggest_win){
                                                boss_reference.child("biggest_win").setValue("" + win);
                                                Properties props = new Properties();
                                                props.put("mail.smtp.auth", "true");
                                                props.put("mail.smtp.starttls.enable","true");
                                                props.put("mail.smtp.host","smtp.gmail.com");
                                                props.put("mail.smtp.port","587");
                                                Session session = Session.getInstance(props, new javax.mail.Authenticator(){
                                                    @Override
                                                    protected PasswordAuthentication getPasswordAuthentication() {
                                                        return new PasswordAuthentication(EMAIL, PASS);
                                                    }
                                                });
                                                try{
                                                    Message message = new MimeMessage(session);
                                                    message.setFrom(new InternetAddress("EMAIL"));
                                                    message.setRecipients(MimeMessage.RecipientType.TO,  InternetAddress.parse(user_email));
                                                    message.setSubject("New win record!!");
                                                    String str = "new win record, last record was "+biggest_win + "$, new record is "+win+"$";
                                                    message.setText(str);
                                                    if (android.os.Build.VERSION.SDK_INT > 9) {
                                                        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                                                        StrictMode.setThreadPolicy(policy);
                                                    }
                                                    Transport.send(message);
                                                }
                                                catch (MessagingException e){
                                                    throw new RuntimeException(e);
                                                }
                                            }
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
                                    mediaPlayer = MediaPlayer.create(Table.this,R.raw.money_sound_effect);
                                    mediaPlayer.start();
                                    handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            startActivity(new Intent(Table.this,Winner_screen.class));
                                        }
                                    },1500);
                                }
                                else{
//                                    Toast.makeText(Table.this,"LOSER!",Toast.LENGTH_LONG).show();
                                    bet_view.setText("place bet to play");
                                    reference.child(UserID).child("last_win").setValue("0");
                                    mediaPlayer = MediaPlayer.create(Table.this,R.raw.lose_sound_effect);
                                    mediaPlayer.start();
                                    handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            startActivity(new Intent(Table.this, Loser_screen.class));
                                        }
                                    },1500);

                                }
                                boss_reference.addListenerForSingleValueEvent(new ValueEventListener() {
                                    //update games stats
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        if(NUMBER == 0){
                                            int zero_val = Integer.parseInt(snapshot.child("bets").child("0").getValue().toString().trim()) + 1;
                                            boss_reference.child("bets").child("0").setValue("" +zero_val);
                                        }
                                        else if (NUMBER == 32)
                                        {
                                            int val_32 = Integer.parseInt(snapshot.child("bets").child("32").getValue().toString().trim()) + 1;
                                            boss_reference.child("bets").child("32").setValue("" + val_32);
                                        }
                                        else
                                        {
                                            int new_val = Integer.parseInt(snapshot.child("bets").child("" + NUMBER).getValue().toString().trim()) + 1;
                                            boss_reference.child("bets").child("" + NUMBER).setValue("" + new_val);
                                        }
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
                        spinned = true;


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
                if(spinned) {
                    spinned = false;

                    startActivity(new Intent(Table.this, betTable.class));

                }
                if(!spinned){

                    Toast.makeText(Table.this,"Don't waist your money, SPIN!",Toast.LENGTH_SHORT).show();
                }
            }
        });
        profile = findViewById(R.id.profile_btn);
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Table.this, user_bio.class));
            }
        });



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

    private final SensorEventListener sensorListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            float x= sensorEvent.values[0];
            float y= sensorEvent.values[1];
            float z= sensorEvent.values[2];

            acelLast= acelVal;
            acelVal= (float) Math.sqrt((double) (x*x + y*y + z*z));
            float delta= acelVal - acelLast;
            shake =shake * 0.9f +delta;

            if(shake >5) {
                if(bet_view.getText().toString().isEmpty() || bet_view.getText().toString().equals("place bet to play")){

                    return;
                }
                //spin
                if(!isSpinning){
                    spinGravity();
                    spinned = true;

                    isSpinning = true;
                }

            }

        }
        private void spinGravity() {

            //image rotation
            degree_old = degree % 360;
            degree = r.nextInt(3600) + 720;
            RotateAnimation rotate = new RotateAnimation(0, degree,
                    RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f);
            rotate.setDuration(11500);
            mediaPlayer = MediaPlayer.create(Table.this,R.raw.spin_sound);
            mediaPlayer.start();
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
                    String number = sectors[index];
                    textView.setText(sectors[index]);
                    NUMBER = numbers[index];

                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Users");
                    reference.child(UserID).addListenerForSingleValueEvent(new ValueEventListener() {
                        //check if user win
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            //check the money user bet on the drawn number and stats update
                            user_email = snapshot.child("email").getValue().toString();
                            String str2 = snapshot.child("bet").child(""+NUMBER).getValue().toString();
                            int user_games = Integer.parseInt(snapshot.child("games").getValue().toString()) + 1;
                            reference.child(UserID).child("games").setValue(""+user_games);
                            win = Integer.parseInt(str2)*32;
                            boss_reference.child("last_num").setValue(""+NUMBER);
                            boss_reference.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    int position = (Integer.parseInt(snapshot.child("last10").child("pos").getValue().toString()))%10 ;
                                    boss_reference.child("last10").child(""+position).setValue(""+NUMBER);
                                    position++;
                                    boss_reference.child("last10").child("pos").setValue(""+position);
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) { }
                            });
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
                                round_win = win;
                                int biggest_win = Integer.parseInt(snapshot.child("biggest_win").getValue().toString());
                                if(win > biggest_win){
                                    reference.child(UserID).child("biggest_win").setValue("" + win);
                                }
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
                                        int biggest_win = Integer.parseInt(snapshot.child("biggest_win").getValue().toString());
                                        if(win > biggest_win){
                                            boss_reference.child("biggest_win").setValue("" + win);
                                            Properties props = new Properties();
                                            props.put("mail.smtp.auth", "true");
                                            props.put("mail.smtp.starttls.enable","true");
                                            props.put("mail.smtp.host","smtp.gmail.com");
                                            props.put("mail.smtp.port","587");
                                            Session session = Session.getInstance(props, new javax.mail.Authenticator(){
                                                @Override
                                                protected PasswordAuthentication getPasswordAuthentication() {
                                                    return new PasswordAuthentication(EMAIL, PASS);
                                                }
                                            });
                                            try{
                                                Message message = new MimeMessage(session);
                                                message.setFrom(new InternetAddress("EMAIL"));
                                                message.setRecipients(MimeMessage.RecipientType.TO,  InternetAddress.parse(user_email));
                                                message.setSubject("New win record!!");
                                                String str = "new win record, last record was "+biggest_win + "$, new record is "+win+"$";
                                                message.setText(str);
                                                if (android.os.Build.VERSION.SDK_INT > 9) {
                                                    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                                                    StrictMode.setThreadPolicy(policy);
                                                }
                                                Transport.send(message);
                                            }
                                            catch (MessagingException e){
                                                throw new RuntimeException(e);
                                            }
                                        }
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

                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        startActivity(new Intent(Table.this,Winner_screen.class));
                                    }
                                },1500);
                            }
                            else{
//                                    Toast.makeText(Table.this,"LOSER!",Toast.LENGTH_LONG).show();
                                bet_view.setText("place bet to play");
                                reference.child(UserID).child("last_win").setValue("0");
                                mediaPlayer = MediaPlayer.create(Table.this,R.raw.lose_sound_effect);
                                mediaPlayer.start();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        startActivity(new Intent(Table.this, Loser_screen.class));
                                    }
                                },1500);
                            }
                            boss_reference.addListenerForSingleValueEvent(new ValueEventListener() {
                                //update games stats
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    if(NUMBER == 0){
                                        int zero_val = Integer.parseInt(snapshot.child("bets").child("0").getValue().toString().trim()) + 1;
                                        boss_reference.child("bets").child("0").setValue("" +zero_val);
                                    }
                                    else if (NUMBER == 32)
                                    {
                                        int val_32 = Integer.parseInt(snapshot.child("bets").child("32").getValue().toString().trim()) + 1;
                                        boss_reference.child("bets").child("32").setValue("" + val_32);
                                    }
                                    else
                                    {
                                        int new_val = Integer.parseInt(snapshot.child("bets").child("" + NUMBER).getValue().toString().trim()) + 1;
                                        boss_reference.child("bets").child("" + NUMBER).setValue("" + new_val);
                                    }
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
        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };
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
            String path = MediaStore.Images.Media.insertImage(getApplicationContext().getContentResolver(),imageBitmap,"IMG_" + Calendar.getInstance().getTime(),null);
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
                                    int min_img = Integer.parseInt(snapshot.child(UserID).child("faces").child("min_show").getValue().toString()) +1;
                                    reference.child(UserID).child("faces").child("min_show").setValue(""+min_img);
                                    LAST_BET = Integer.parseInt(snapshot.child(UserID).child("last_bet").getValue().toString());
                                    LAST_WIN = Integer.parseInt(snapshot.child(UserID).child("last_win").getValue().toString());
                                    if(LAST_WIN > 0){
                                        reference  .child(UserID).child("faces").child(""+pos).child("sum").setValue("WON "+LAST_WIN+"$");
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
        if(ContextCompat.checkSelfPermission(Table.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(Table.this,new String[]{Manifest.permission.CAMERA},101);
        }

    }

    private int getNum(int degrees){

        if(degrees >= (FACTOR * 1) && degrees < (FACTOR * 3)){
            return 0;
        }
        if(degrees >= (FACTOR * 3) && degrees < (FACTOR * 5)){
            return 1;
        }
        if(degrees >= (FACTOR * 5) && degrees < (FACTOR * 7)){
            return 2;
        }
        if(degrees >= (FACTOR * 7) && degrees < (FACTOR * 9)){
            return 3;
        }
        if(degrees >= (FACTOR * 9) && degrees < (FACTOR * 11)){
            return 4;
        }
        if(degrees >= (FACTOR * 11) && degrees < (FACTOR * 13)){
            return 5;
        }
        if(degrees >= (FACTOR * 13) && degrees < (FACTOR * 15)){
            return 6;
        }
        if(degrees >= (FACTOR * 15) && degrees < (FACTOR * 17)){
            return 7;
        }
        if(degrees >= (FACTOR * 17) && degrees < (FACTOR * 19)){
            return 8;
        }
        if(degrees >= (FACTOR * 19) && degrees < (FACTOR * 21)){
            return 9;
        }
        if(degrees >= (FACTOR * 21) && degrees < (FACTOR * 23)){
            return 10;
        }
        if(degrees >= (FACTOR * 23) && degrees < (FACTOR * 25)){
            return 11;
        }
        if(degrees >= (FACTOR * 25) && degrees < (FACTOR * 27)){
            return 12;
        }
        if(degrees >= (FACTOR * 27) && degrees < (FACTOR * 29)){
            return 13;
        }
        if(degrees >= (FACTOR * 29) && degrees < (FACTOR * 31)){
            return 14;
        }
        if(degrees >= (FACTOR * 31) && degrees < (FACTOR * 33)){
            return 15;
        }
        if(degrees >= (FACTOR * 33) && degrees < (FACTOR * 35)){
            return 16;
        }
        if(degrees >= (FACTOR * 35) && degrees < (FACTOR * 37)){
            return 17;
        }
        if(degrees >= (FACTOR * 37) && degrees < (FACTOR * 39)){
            return 18;
        }
        if(degrees >= (FACTOR * 39) && degrees < (FACTOR * 41)){
            return 19;
        }
        if(degrees >= (FACTOR * 41) && degrees < (FACTOR * 43)){
            return 20;
        }
        if(degrees >= (FACTOR * 43) && degrees < (FACTOR * 45)){
            return 21;
        }
        if(degrees >= (FACTOR * 45) && degrees < (FACTOR * 47)){
            return 22;
        }
        if(degrees >= (FACTOR * 47) && degrees < (FACTOR * 49)){
            return 23;
        }
        if(degrees >= (FACTOR * 49) && degrees < (FACTOR * 51)){
            return 24;
        }
        if(degrees >= (FACTOR * 51) && degrees < (FACTOR * 53)){
            return 25;
        }
        if(degrees >= (FACTOR * 53) && degrees < (FACTOR * 55)){
            return 26;
        }
        if(degrees >= (FACTOR * 55) && degrees < (FACTOR * 57)){
            return 27;
        }
        if(degrees >= (FACTOR * 57) && degrees < (FACTOR * 59)){
            return 28;
        }
        if(degrees >= (FACTOR * 59) && degrees < (FACTOR * 61)){
            return 29;
        }
        if(degrees >= (FACTOR * 61) && degrees < (FACTOR * 63)){
            return 30;
        }
        if(degrees >= (FACTOR * 63) && degrees < (FACTOR * 65)){
            return 31;
        }
        if(degrees >= (FACTOR * 65) && degrees < (FACTOR * 667)){
            return 32;
        }
        if(degrees >= (FACTOR * 67) && degrees < (FACTOR * 69)){
            return 33;
        }
        if(degrees >= (FACTOR * 69) && degrees < (FACTOR * 71)){
            return 34;
        }
        if(degrees >= (FACTOR * 71) && degrees < (FACTOR * 73)){
            return 35;
        }
        if((degrees >= (FACTOR * 73) && degrees < 360) || (degrees >= 0 && degrees < (FACTOR * 1))){
            return 36;
        }
        return -1;
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

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        }
        return false;
    }


}
