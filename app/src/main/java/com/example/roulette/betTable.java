package com.example.roulette;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import android.os.StrictMode;
import android.text.method.ScrollingMovementMethod;
import android.util.TypedValue;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.sql.DataSource;

import java.util.Map;
import java.util.Properties;

public class betTable extends AppCompatActivity {

    private Button b0, b1 ,b2 ,b3 ,b4 ,b5 ,b6 ,b7 ,b8 ,b9 ,b10 ,b11  ,b12 ,b13 ,b14 ,b15 ,b16 ,b17 ,
            b18 ,b19,b20 ,b21 ,b22 ,b23 ,b24 ,b25 ,b26 ,b27 ,b28 ,b29 ,b30 ,b31 ,b32 ,b33 ,b34 ,b35
            ,b36,btn5,btn25,btn100,btn500, btn1000,submit,reset_btn,calculator,odd,even,red,black,
            low,high,btn_allIn,btn10k,btn5000,info,last10 , ok, cancel,remove_bet;
    public int [] MAP = new int [43];
    private String [] nums = new String[10];
    private TextView bet_amount,user_amount,selected_chip,final_bet_txt, text_view;
    String BET_STRING,UserID;
    private FirebaseUser user;
    Dialog dialog;
    public long BALANCE;
    private DatabaseReference reference,boss_reference;
    private static int CHIP = 0,BET_SUM = 0, ADD_SUM = 0,USER_AMOUNT = 0,AVG_BET = 0;
    String EMAIL, PASS , user_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_bet_table);
        ////////////////////////////////////////////////////////////
        dialog = new Dialog(this);
        EMAIL = "roulleteboss@gmail.com";
        PASS = "uhgnjmsmvfppdmxz";

        reference = FirebaseDatabase.getInstance().getReference("Users");
        user = FirebaseAuth.getInstance().getCurrentUser();
        last10 = findViewById(R.id.last_ten_nums);
        UserID = user.getUid();
        boss_reference = FirebaseDatabase.getInstance().getReference("Boss");

        ///////////////////////////////////////////////////////////
        BET_STRING = "";
        info = findViewById(R.id.information);
        submit = (Button)findViewById(R.id.submit_bet);
        selected_chip = (TextView)findViewById(R.id.textView17);
        ADD_SUM = 0;
        BET_SUM = 0;
        bet_amount = (TextView)findViewById(R.id.betsumtextview);
        remove_bet = findViewById(R.id.del_bet);
        /////////////////////////////////////////////////////////////
        user_amount = (TextView) findViewById(R.id.user_amount_bet);

        reference = FirebaseDatabase.getInstance().getReference("Users");
        user = FirebaseAuth.getInstance().getCurrentUser();
        UserID = user.getUid();
        //set balance Textview
        reference.child(UserID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                USER_AMOUNT = Integer.parseInt(snapshot.child("balance").getValue().toString().trim());
                user_amount.setText(""+USER_AMOUNT+"$");
                AVG_BET = Integer.parseInt(snapshot.child("avg_bet").getValue().toString().trim());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
        for(int i = 0; i<37; i++){
            reference.child(UserID).child("bet").child(""+i).setValue("0");
        }
        reference.child(UserID).child("bet").child("odd").setValue("0");
        reference.child(UserID).child("bet").child("even").setValue("0");
        reference.child(UserID).child("bet").child("red").setValue("0");
        reference.child(UserID).child("bet").child("black").setValue("0");
        reference.child(UserID).child("bet").child("high").setValue("0");
        reference.child(UserID).child("bet").child("low").setValue("0");
        reset_btn = findViewById(R.id.reset);
        reset_btn.setOnClickListener(new View.OnClickListener() {
            //reset all bets
            @Override
            public void onClick(View v) {
                for (int i = 0; i< 43; i++){
                    MAP[i] = 0;
                }
                BET_SUM = 0;
                bet_amount.setText(""+BET_SUM+"$");
                final_bet_txt.setText("");
                resetColor();
            }


        });
        remove_bet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CHIP = -1;
                selected_chip.setText("delete");
            }
        });
        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                lp.copyFrom(dialog.getWindow().getAttributes());
                lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                lp.height = WindowManager.LayoutParams.MATCH_PARENT ;
                int width = (int)(getResources().getDisplayMetrics().widthPixels*0.82);
                int height = (int)(getResources().getDisplayMetrics().heightPixels*0.65);
                dialog.show();
                dialog.getWindow().setLayout(width,height);
                dialog.setContentView(R.layout.info_popup);
                Button exit = dialog.findViewById(R.id.exit_btn);
                exit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

            }
        });
        boss_reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(int i =0; i<10; i++){
                    nums[i]= snapshot.child("last10").child(""+i).getValue().toString();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
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

        final_bet_txt = findViewById(R.id.final_bet);
        calculator = findViewById(R.id.calculate);
        calculator.setOnClickListener(new View.OnClickListener() {
            //show the bets to the user
            @Override
            public void onClick(View v) {
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
                final_bet_txt.setText(show);
                final_bet_txt.setMovementMethod(new ScrollingMovementMethod());
            }

        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reference.child(UserID).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        int sum = 0;
                        user_email = snapshot.child("email").getValue().toString();
                        //check if user have enough money
                        if(BET_SUM > Integer.parseInt(snapshot.child("balance").getValue().toString())){
                            //doesnt have money, reset the bets
                            BET_SUM = 0;
                            for(int i = 0; i<37; i++){
                                MAP[i] = 0;
                                Toast.makeText(betTable.this,"Error, not enough money,enter new bet",Toast.LENGTH_SHORT).show();
                            }
                        }
                        //check if user didn't placed a bet
                        else if( BET_SUM == 0){
                            Toast.makeText(betTable.this,"place a bet to play",Toast.LENGTH_SHORT).show();
                        }
                        //bet is legal, upload to firebase
                        else {
                            WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                            lp.copyFrom(dialog.getWindow().getAttributes());
                            lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                            lp.height = WindowManager.LayoutParams.MATCH_PARENT ;
                            int width = (int)(getResources().getDisplayMetrics().widthPixels*1);
                            int height = (int)(getResources().getDisplayMetrics().heightPixels*0.40);
                            dialog.show();
                            dialog.getWindow().setLayout(width,height);
                            dialog.setContentView(R.layout.costume_dialog);
                            ok = dialog.findViewById(R.id.ok);
                            cancel = dialog.findViewById(R.id.cancel);
                            text_view = dialog.findViewById(R.id.msg_box);
                            text_view.setText("Are you sure you want to bet " + BET_SUM + "$ ?");
                            cancel.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialog.dismiss();
                                }
                            });
                            ok.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    for (int i = 0; i < 37; i++) {
                                        reference.child(UserID).child("bet").child("" + i).setValue(MAP[i]);
                                        reference.child(UserID).child("bet_map").child("" + i).setValue(MAP[i]);
                                    }
                                    reference.child(UserID).child("bet").child("odd").setValue("" + MAP[37]);
                                    reference.child(UserID).child("bet").child("even").setValue("" + MAP[38]);
                                    reference.child(UserID).child("bet").child("red").setValue("" + MAP[39]);
                                    reference.child(UserID).child("bet").child("black").setValue("" + MAP[40]);
                                    reference.child(UserID).child("bet").child("high").setValue("" + MAP[41]);
                                    reference.child(UserID).child("bet").child("low").setValue("" + MAP[42]);
                                    reference.child(UserID).child("bet_map").child("odd").setValue("" + MAP[37]);
                                    reference.child(UserID).child("bet_map").child("even").setValue("" + MAP[38]);
                                    reference.child(UserID).child("bet_map").child("red").setValue("" + MAP[39]);
                                    reference.child(UserID).child("bet_map").child("black").setValue("" + MAP[40]);
                                    reference.child(UserID).child("bet_map").child("high").setValue("" + MAP[41]);
                                    reference.child(UserID).child("bet_map").child("low").setValue("" + MAP[42]);
                                    reference.child(UserID).child("last_bet").setValue("" + BET_SUM);
                                    int highest_bet = Integer.parseInt(snapshot.child("biggest_bet").getValue().toString());
                                    if (BET_SUM > highest_bet) {
                                        reference.child(UserID).child("biggest_bet").setValue("" + BET_SUM);
                                    }

                                    boss_reference.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            //adding money to the boss
                                            long bos_balance = Long.parseLong(snapshot.child("balance").getValue().toString());
                                            long new_sum = BET_SUM + bos_balance;
                                            int average_bet = (Integer.parseInt(snapshot.child("avg_bet").getValue().toString().trim()));
                                            average_bet = (average_bet + BET_SUM) / 2;
                                            boss_reference.child("avg_bet").setValue(""+average_bet);
                                            boss_reference.child("balance").setValue(""+new_sum);
                                            int boss_highest_bet = Integer.parseInt(snapshot.child("biggest_bet").getValue().toString());
                                            if(BET_SUM > boss_highest_bet){
                                                isOnline();
                                                boss_reference.child("biggest_bet").setValue("" + BET_SUM);
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
                                                    message.setSubject("New bet record!!");
                                                    String str = "new bet record, last record was "+boss_highest_bet + "$, new record is "+BET_SUM+"$";
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
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) { }
                                    });
                                    //update user balance and bets money
                                    if(AVG_BET == 0){
                                        reference.child(UserID).child("avg_bet").setValue(""+BET_SUM);
                                    }
                                    else{
                                        AVG_BET = (AVG_BET + BET_SUM) / 2;
                                    }
                                    BALANCE = Long.parseLong(snapshot.child("balance").getValue().toString()) - (long)BET_SUM;
                                    long user_bets_money = Integer.parseInt(snapshot.child("bets_money").getValue().toString()) +(long) BET_SUM;
                                    reference.child(UserID).child("bets_money").setValue(""+user_bets_money);
                                    reference.child(UserID).child("balance").setValue(""+BALANCE);

                                    startActivity(new Intent(betTable.this,Table.class));
                                    dialog.dismiss();

                                }

                            });
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });

        b0 = (Button) findViewById(R.id.b0);
        b0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CHIP == -1){
                    int tmp = MAP[0];
                    MAP[0] = 0;
                    BET_SUM -= tmp;
                    bet_amount.setText(""+BET_SUM+"$");
                    b0.setBackgroundResource(R.drawable.greenbet);
                }
                else{
                    b0.setBackgroundResource(R.drawable.greeb_pressd_btn);
                    MAP[0] += CHIP;
                    BET_SUM += CHIP;
                    bet_amount.setText(""+BET_SUM+"$");
                }


            }
        });
        b1 = (Button) findViewById(R.id.b1);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CHIP == -1){
                    int tmp = MAP[1];
                    MAP[1] = 0;
                    BET_SUM -= tmp;
                    bet_amount.setText(""+BET_SUM+"$");
                    b1.setBackgroundResource(R.drawable.btnred);

                }
                else{
                    b1.setBackgroundResource(R.drawable.btn_pressd_red);
                    MAP[1] += CHIP;
                    BET_SUM += CHIP;
                    bet_amount.setText(""+BET_SUM+"$");
                }
            }
        });
        b2 = (Button) findViewById(R.id.b2);
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CHIP == -1){
                    int tmp = MAP[2];
                    MAP[2] = 0;
                    BET_SUM -= tmp;
                    bet_amount.setText(""+BET_SUM+"$");
                    b2.setBackgroundResource(R.drawable.black_btn);

                }
                else{
                    b2.setBackgroundResource(R.drawable.black_pressd_btn);
                    MAP[2] += CHIP;
                    BET_SUM += CHIP;
                    bet_amount.setText(""+BET_SUM+"$");
                }
            }
        });
        b3 = (Button) findViewById(R.id.b3);
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CHIP == -1){
                    int tmp = MAP[3];
                    MAP[3] = 0;
                    BET_SUM -= tmp;
                    bet_amount.setText(""+BET_SUM+"$");
                    b3.setBackgroundResource(R.drawable.btnred);

                }
                else{
                    b3.setBackgroundResource(R.drawable.btn_pressd_red);
                    MAP[3] += CHIP;
                    BET_SUM += CHIP;
                    bet_amount.setText(""+BET_SUM+"$");
                }
            }
        });
        b4 = (Button) findViewById(R.id.b4);
        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CHIP == -1){
                    int tmp = MAP[4];
                    MAP[4] = 0;
                    BET_SUM -= tmp;
                    bet_amount.setText(""+BET_SUM+"$");
                    b4.setBackgroundResource(R.drawable.black_btn);

                }
                else{
                    b4.setBackgroundResource(R.drawable.black_pressd_btn);
                    MAP[4] += CHIP;
                    BET_SUM += CHIP;
                    bet_amount.setText(""+BET_SUM+"$");
                }
            }
        });
        b5 = (Button) findViewById(R.id.b5);
        b5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CHIP == -1){
                    int tmp = MAP[5];
                    MAP[5] = 0;
                    BET_SUM -= tmp;
                    bet_amount.setText(""+BET_SUM+"$");
                    b5.setBackgroundResource(R.drawable.btnred);

                }
                else{
                    b5.setBackgroundResource(R.drawable.btn_pressd_red);
                    MAP[5] += CHIP;
                    BET_SUM += CHIP;
                    bet_amount.setText(""+BET_SUM+"$");
                }
            }
        });
        b6 = (Button) findViewById(R.id.b6);
        b6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CHIP == -1){
                    int tmp = MAP[6];
                    MAP[6] = 0;
                    BET_SUM -= tmp;
                    bet_amount.setText(""+BET_SUM+"$");
                    b6.setBackgroundResource(R.drawable.black_btn);

                }
                else{
                    b6.setBackgroundResource(R.drawable.black_pressd_btn);
                    MAP[6] += CHIP;
                    BET_SUM += CHIP;
                    bet_amount.setText(""+BET_SUM+"$");
                }
            }
        });
        b7 = (Button) findViewById(R.id.b7);
        b7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CHIP == -1){
                    int tmp = MAP[7];
                    MAP[7] = 0;
                    BET_SUM -= tmp;
                    bet_amount.setText(""+BET_SUM+"$");
                    b7.setBackgroundResource(R.drawable.btnred);

                }
                else{
                    b7.setBackgroundResource(R.drawable.btn_pressd_red);
                    MAP[7] += CHIP;
                    BET_SUM += CHIP;
                    bet_amount.setText(""+BET_SUM+"$");
                }
            }
        });
        b8 = (Button) findViewById(R.id.b8);
        b8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CHIP == -1){
                    int tmp = MAP[8];
                    MAP[8] = 0;
                    BET_SUM -= tmp;
                    bet_amount.setText(""+BET_SUM+"$");
                    b8.setBackgroundResource(R.drawable.black_btn);

                }
                else{
                    b8.setBackgroundResource(R.drawable.black_pressd_btn);
                    MAP[8] += CHIP;
                    BET_SUM += CHIP;
                    bet_amount.setText(""+BET_SUM+"$");
                }
            }
        });
        b9 = (Button) findViewById(R.id.b9);
        b9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CHIP == -1){
                    int tmp = MAP[9];
                    MAP[9] = 0;
                    BET_SUM -= tmp;
                    bet_amount.setText(""+BET_SUM+"$");
                    b9.setBackgroundResource(R.drawable.btnred);

                }
                else{
                    b9.setBackgroundResource(R.drawable.btn_pressd_red);
                    MAP[9] += CHIP;
                    BET_SUM += CHIP;
                    bet_amount.setText(""+BET_SUM+"$");
                }
            }
        });
        b10 = (Button) findViewById(R.id.b10);
        b10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CHIP == -1){
                    int tmp = MAP[10];
                    MAP[10] = 0;
                    BET_SUM -= tmp;
                    bet_amount.setText(""+BET_SUM+"$");
                    b10.setBackgroundResource(R.drawable.black_btn);

                }
                else{
                    b10.setBackgroundResource(R.drawable.black_pressd_btn);
                    MAP[10] += CHIP;
                    BET_SUM += CHIP;
                    bet_amount.setText(""+BET_SUM+"$");
                }
            }
        });
        b11 = (Button) findViewById(R.id.b11);
        b11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CHIP == -1){
                    int tmp = MAP[11];
                    MAP[11] = 0;
                    BET_SUM -= tmp;
                    bet_amount.setText(""+BET_SUM+"$");
                    b11.setBackgroundResource(R.drawable.black_btn);

                }
                else{
                    b11.setBackgroundResource(R.drawable.black_pressd_btn);
                    MAP[11] += CHIP;
                    BET_SUM += CHIP;
                    bet_amount.setText(""+BET_SUM+"$");
                }
            }
        });
        b12 = (Button) findViewById(R.id.b12);
        b12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CHIP == -1){
                    int tmp = MAP[12];
                    MAP[12] = 0;
                    BET_SUM -= tmp;
                    bet_amount.setText(""+BET_SUM+"$");
                    b12.setBackgroundResource(R.drawable.btnred);

                }
                else{
                    b12.setBackgroundResource(R.drawable.btn_pressd_red);
                    MAP[12] += CHIP;
                    BET_SUM += CHIP;
                    bet_amount.setText(""+BET_SUM+"$");
                }
            }
        });
        b13 = (Button) findViewById(R.id.b13);
        b13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CHIP == -1){
                    int tmp = MAP[13];
                    MAP[13] = 0;
                    BET_SUM -= tmp;
                    bet_amount.setText(""+BET_SUM+"$");
                    b13.setBackgroundResource(R.drawable.black_btn);

                }
                else{
                    b13.setBackgroundResource(R.drawable.black_pressd_btn);
                    MAP[13] += CHIP;
                    BET_SUM += CHIP;
                    bet_amount.setText(""+BET_SUM+"$");
                }
            }
        });
        b14 = (Button) findViewById(R.id.b14);
        b14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CHIP == -1){
                    int tmp = MAP[14];
                    MAP[14] = 0;
                    BET_SUM -= tmp;
                    bet_amount.setText(""+BET_SUM+"$");
                    b14.setBackgroundResource(R.drawable.btnred);

                }
                else{
                    b14.setBackgroundResource(R.drawable.btn_pressd_red);
                    MAP[14] += CHIP;
                    BET_SUM += CHIP;
                    bet_amount.setText(""+BET_SUM+"$");
                }
            }
        });
        b15 = (Button) findViewById(R.id.b15);
        b15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CHIP == -1){
                    int tmp = MAP[15];
                    MAP[15] = 0;
                    BET_SUM -= tmp;
                    bet_amount.setText(""+BET_SUM+"$");
                    b15.setBackgroundResource(R.drawable.black_btn);

                }
                else{
                    b15.setBackgroundResource(R.drawable.black_pressd_btn);
                    MAP[15] += CHIP;
                    BET_SUM += CHIP;
                    bet_amount.setText(""+BET_SUM+"$");
                }
            }
        });
        b16 = (Button) findViewById(R.id.b16);
        b16.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CHIP == -1){
                    int tmp = MAP[16];
                    MAP[16] = 0;
                    BET_SUM -= tmp;
                    bet_amount.setText(""+BET_SUM+"$");
                    b16.setBackgroundResource(R.drawable.btnred);

                }
                else{
                    b16.setBackgroundResource(R.drawable.btn_pressd_red);
                    MAP[16] += CHIP;
                    BET_SUM += CHIP;
                    bet_amount.setText(""+BET_SUM+"$");
                }
            }
        });
        b17 = (Button) findViewById(R.id.b17);
        b17.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CHIP == -1){
                    int tmp = MAP[17];
                    MAP[17] = 0;
                    BET_SUM -= tmp;
                    bet_amount.setText(""+BET_SUM+"$");
                    b17.setBackgroundResource(R.drawable.black_btn);

                }
                else{
                    b17.setBackgroundResource(R.drawable.black_pressd_btn);
                    MAP[17] += CHIP;
                    BET_SUM += CHIP;
                    bet_amount.setText(""+BET_SUM+"$");
                }

            }
        });
        b18 = (Button) findViewById(R.id.b18);
        b18.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CHIP == -1){
                    int tmp = MAP[18];
                    MAP[18] = 0;
                    BET_SUM -= tmp;
                    bet_amount.setText(""+BET_SUM+"$");
                    b18.setBackgroundResource(R.drawable.btnred);

                }
                else{
                    b18.setBackgroundResource(R.drawable.btn_pressd_red);
                    MAP[18] += CHIP;
                    BET_SUM += CHIP;
                    bet_amount.setText(""+BET_SUM+"$");
                }
            }
        });
        b19 = (Button) findViewById(R.id.b19);
        b19.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CHIP == -1){
                    int tmp = MAP[19];
                    MAP[19] = 0;
                    BET_SUM -= tmp;
                    bet_amount.setText(""+BET_SUM+"$");
                    b19.setBackgroundResource(R.drawable.btnred);

                }
                else{
                    b19.setBackgroundResource(R.drawable.btn_pressd_red);
                    MAP[19] += CHIP;
                    BET_SUM += CHIP;
                    bet_amount.setText(""+BET_SUM+"$");
                }
            }
        });
        b20 = (Button) findViewById(R.id.b20);
        b20.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CHIP == -1){
                    int tmp = MAP[20];
                    MAP[20] = 0;
                    BET_SUM -= tmp;
                    bet_amount.setText(""+BET_SUM+"$");
                    b20.setBackgroundResource(R.drawable.black_btn);

                }
                else{
                    b20.setBackgroundResource(R.drawable.black_pressd_btn);
                    MAP[20] += CHIP;
                    BET_SUM += CHIP;
                    bet_amount.setText(""+BET_SUM+"$");
                }
            }
        });
        b21 = (Button) findViewById(R.id.b21);
        b21.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CHIP == -1){
                    int tmp = MAP[21];
                    MAP[21] = 0;
                    BET_SUM -= tmp;
                    bet_amount.setText(""+BET_SUM+"$");
                    b21.setBackgroundResource(R.drawable.btnred);

                }
                else{
                    b21.setBackgroundResource(R.drawable.btn_pressd_red);
                    MAP[21] += CHIP;
                    BET_SUM += CHIP;
                    bet_amount.setText(""+BET_SUM+"$");
                }
            }
        });
        b22 = (Button) findViewById(R.id.b22);
        b22.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CHIP == -1){
                    int tmp = MAP[22];
                    MAP[22] = 0;
                    BET_SUM -= tmp;
                    bet_amount.setText(""+BET_SUM+"$");
                    b22.setBackgroundResource(R.drawable.black_btn);

                }
                else{
                    b22.setBackgroundResource(R.drawable.black_pressd_btn);
                    MAP[22] += CHIP;
                    BET_SUM += CHIP;
                    bet_amount.setText(""+BET_SUM+"$");
                }
            }
        });
        b23 = (Button) findViewById(R.id.b23);
        b23.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CHIP == -1){
                    int tmp = MAP[23];
                    MAP[23] = 0;
                    BET_SUM -= tmp;
                    bet_amount.setText(""+BET_SUM+"$");
                    b23.setBackgroundResource(R.drawable.btnred);

                }
                else{
                    b23.setBackgroundResource(R.drawable.btn_pressd_red);
                    MAP[23] += CHIP;
                    BET_SUM += CHIP;
                    bet_amount.setText(""+BET_SUM+"$");
                }
            }
        });
        b24 = (Button) findViewById(R.id.b24);
        b24.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CHIP == -1){
                    int tmp = MAP[24];
                    MAP[24] = 0;
                    BET_SUM -= tmp;
                    bet_amount.setText(""+BET_SUM+"$");
                    b24.setBackgroundResource(R.drawable.black_btn);

                }
                else{
                    b24.setBackgroundResource(R.drawable.black_pressd_btn);
                    MAP[24] += CHIP;
                    BET_SUM += CHIP;
                    bet_amount.setText(""+BET_SUM+"$");
                }
            }
        });
        b25 = (Button) findViewById(R.id.b25);
        b25.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CHIP == -1){
                    int tmp = MAP[25];
                    MAP[25] = 0;
                    BET_SUM -= tmp;
                    bet_amount.setText(""+BET_SUM+"$");
                    b25.setBackgroundResource(R.drawable.btnred);

                }
                else{
                    b25.setBackgroundResource(R.drawable.btn_pressd_red);
                    MAP[25] += CHIP;
                    BET_SUM += CHIP;
                    bet_amount.setText(""+BET_SUM+"$");
                }
            }
        });
        b26 = (Button) findViewById(R.id.b26);
        b26.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CHIP == -1){
                    int tmp = MAP[26];
                    MAP[26] = 0;
                    BET_SUM -= tmp;
                    bet_amount.setText(""+BET_SUM+"$");
                    b26.setBackgroundResource(R.drawable.black_btn);

                }
                else{
                    b26.setBackgroundResource(R.drawable.black_pressd_btn);
                    MAP[26] += CHIP;
                    BET_SUM += CHIP;
                    bet_amount.setText(""+BET_SUM+"$");
                }
            }
        });
        b27 = (Button) findViewById(R.id.b27);
        b27.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CHIP == -1){
                    int tmp = MAP[27];
                    MAP[27] = 0;
                    BET_SUM -= tmp;
                    bet_amount.setText(""+BET_SUM+"$");
                    b27.setBackgroundResource(R.drawable.btnred);

                }
                else{
                    b27.setBackgroundResource(R.drawable.btn_pressd_red);
                    MAP[27] += CHIP;
                    BET_SUM += CHIP;
                    bet_amount.setText(""+BET_SUM+"$");
                }
            }
        });
        b28 = (Button) findViewById(R.id.b28);
        b28.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CHIP == -1){
                    int tmp = MAP[28];
                    MAP[28] = 0;
                    BET_SUM -= tmp;
                    bet_amount.setText(""+BET_SUM+"$");
                    b28.setBackgroundResource(R.drawable.black_btn);

                }
                else{
                    b28.setBackgroundResource(R.drawable.black_pressd_btn);
                    MAP[28] += CHIP;
                    BET_SUM += CHIP;
                    bet_amount.setText(""+BET_SUM+"$");
                }
            }
        });
        b29 = (Button) findViewById(R.id.b29);
        b29.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CHIP == -1){
                    int tmp = MAP[29];
                    MAP[29] = 0;
                    BET_SUM -= tmp;
                    bet_amount.setText(""+BET_SUM+"$");
                    b29.setBackgroundResource(R.drawable.black_btn);

                }
                else{
                    b29.setBackgroundResource(R.drawable.black_pressd_btn);
                    MAP[29] += CHIP;
                    BET_SUM += CHIP;
                    bet_amount.setText(""+BET_SUM+"$");
                }
            }
        });
        b30 = (Button) findViewById(R.id.b30);
        b30.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CHIP == -1){
                    int tmp = MAP[30];
                    MAP[30] = 0;
                    BET_SUM -= tmp;
                    bet_amount.setText(""+BET_SUM+"$");
                    b30.setBackgroundResource(R.drawable.btnred);

                }
                else{
                    b30.setBackgroundResource(R.drawable.btn_pressd_red);
                    MAP[30] += CHIP;
                    BET_SUM += CHIP;
                    bet_amount.setText(""+BET_SUM+"$");
                }

            }
        });
        b31 = (Button) findViewById(R.id.b31);
        b31.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CHIP == -1){
                    int tmp = MAP[31];
                    MAP[31] = 0;
                    BET_SUM -= tmp;
                    bet_amount.setText(""+BET_SUM+"$");
                    b31.setBackgroundResource(R.drawable.black_btn);

                }
                else{
                    b31.setBackgroundResource(R.drawable.black_pressd_btn);
                    MAP[31] += CHIP;
                    BET_SUM += CHIP;
                    bet_amount.setText(""+BET_SUM+"$");
                }

            }
        });
        b32 = (Button) findViewById(R.id.b32);
        b32.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CHIP == -1){
                    int tmp = MAP[32];
                    MAP[32] = 0;
                    BET_SUM -= tmp;
                    bet_amount.setText(""+BET_SUM+"$");
                    b32.setBackgroundResource(R.drawable.btnred);

                }
                else{
                    b32.setBackgroundResource(R.drawable.btn_pressd_red);
                    MAP[32] += CHIP;
                    BET_SUM += CHIP;
                    bet_amount.setText(""+BET_SUM+"$");
                }
            }
        });
        b33 = (Button) findViewById(R.id.b33);
        b33.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CHIP == -1){
                    int tmp = MAP[33];
                    MAP[33] = 0;
                    BET_SUM -= tmp;
                    bet_amount.setText(""+BET_SUM+"$");
                    b33.setBackgroundResource(R.drawable.black_btn);

                }
                else{
                    b33.setBackgroundResource(R.drawable.black_pressd_btn);
                    MAP[33] += CHIP;
                    BET_SUM += CHIP;
                    bet_amount.setText(""+BET_SUM+"$");
                }

            }
        });
        b34 = (Button) findViewById(R.id.b34);
        b34.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CHIP == -1){
                    int tmp = MAP[34];
                    MAP[34] = 0;
                    BET_SUM -= tmp;
                    bet_amount.setText(""+BET_SUM+"$");
                    b34.setBackgroundResource(R.drawable.btnred);

                }
                else{
                    b34.setBackgroundResource(R.drawable.btn_pressd_red);
                    MAP[34] += CHIP;
                    BET_SUM += CHIP;
                    bet_amount.setText(""+BET_SUM+"$");
                }

            }
        });
        b35 = (Button) findViewById(R.id.b35);
        b35.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CHIP == -1){
                    int tmp = MAP[35];
                    MAP[35] = 0;
                    BET_SUM -= tmp;
                    bet_amount.setText(""+BET_SUM+"$");
                    b35.setBackgroundResource(R.drawable.black_btn);

                }
                else{
                    b35.setBackgroundResource(R.drawable.black_pressd_btn);
                    MAP[35] += CHIP;
                    BET_SUM += CHIP;
                    bet_amount.setText(""+BET_SUM+"$");
                }
            }
        });
        b36 = (Button) findViewById(R.id.b36);
        b36.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CHIP == -1){
                    int tmp = MAP[36];
                    MAP[36] = 0;
                    BET_SUM -= tmp;
                    bet_amount.setText(""+BET_SUM+"$");
                    b36.setBackgroundResource(R.drawable.btnred);

                }
                else{
                    b36.setBackgroundResource(R.drawable.btn_pressd_red);
                    MAP[36] += CHIP;
                    BET_SUM += CHIP;
                    bet_amount.setText(""+BET_SUM+"$");
                }

            }
        });

        btn5 = (Button)findViewById(R.id.bet5);
        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CHIP = 5;
                selected_chip.setText("5$");
            }
        });
        btn25 = (Button)findViewById(R.id.bet25);
        btn25.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CHIP=25;
                selected_chip.setText("25$");
            }
        });
        btn100 = (Button)findViewById(R.id.bet100);
        btn100.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CHIP=100;
                selected_chip.setText("100$");
            }
        });
        btn500 = (Button)findViewById(R.id.bet500);
        btn500.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CHIP=500;
                selected_chip.setText("500$");
            }
        });
        btn1000 = (Button) findViewById(R.id.bet1000);
        btn1000.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CHIP=1000;
                selected_chip.setText("1000$");
            }
        });
        btn5000 = (Button) findViewById(R.id.bet5000);
        btn5000.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CHIP=5000;
                selected_chip.setText("5000$");
            }
        });
        btn10k = (Button) findViewById(R.id.bet10k);
        btn10k.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CHIP=10000;
                selected_chip.setText("10$K");
            }
        });
        btn_allIn = (Button) findViewById(R.id.betaLLiN);
        btn_allIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CHIP=USER_AMOUNT;
                selected_chip.setText(""+USER_AMOUNT+"$");
            }
        });
        odd = findViewById(R.id.odd);
        odd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CHIP == -1){
                    int tmp = MAP[37];
                    MAP[37] = 0;
                    BET_SUM -= tmp;
                    bet_amount.setText(""+BET_SUM+"$");
                    odd.setBackgroundResource(R.drawable.greenbet);

                }
                else{
                    odd.setBackgroundResource(R.drawable.greeb_pressd_btn);
                    MAP[37] += CHIP;
                    BET_SUM += CHIP;
                    bet_amount.setText(""+BET_SUM+"$");
                }
            }
        });
        even = findViewById(R.id.even);
        even.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CHIP == -1){
                    int tmp = MAP[38];
                    MAP[38] = 0;
                    BET_SUM -= tmp;
                    bet_amount.setText(""+BET_SUM+"$");
                    even.setBackgroundResource(R.drawable.greenbet);

                }
                else{
                    even.setBackgroundResource(R.drawable.greeb_pressd_btn);
                    MAP[38] += CHIP;
                    BET_SUM += CHIP;
                    bet_amount.setText(""+BET_SUM+"$");
                }
            }
        });
        red = findViewById(R.id.red);
        red.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CHIP == -1){
                    int tmp = MAP[39];
                    MAP[39] = 0;
                    BET_SUM -= tmp;
                    bet_amount.setText(""+BET_SUM+"$");
                    red.setBackgroundResource(R.drawable.btnred);

                }
                else{
                    red.setBackgroundResource(R.drawable.btn_pressd_red);
                    MAP[39] += CHIP;
                    BET_SUM += CHIP;
                    bet_amount.setText(""+BET_SUM+"$");
                }
            }
        });
        black = findViewById(R.id.black);
        black.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CHIP == -1){
                    int tmp = MAP[40];
                    MAP[40] = 0;
                    BET_SUM -= tmp;
                    bet_amount.setText(""+BET_SUM+"$");
                    black.setBackgroundResource(R.drawable.black_btn);

                }
                else{
                    black.setBackgroundResource(R.drawable.black_pressd_btn);
                    MAP[40] += CHIP;
                    BET_SUM += CHIP;
                    bet_amount.setText(""+BET_SUM+"$");
                }
            }
        });
        high = findViewById(R.id.half2);
        high.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CHIP == -1){
                    int tmp = MAP[41];
                    MAP[41] = 0;
                    BET_SUM -= tmp;
                    bet_amount.setText(""+BET_SUM+"$");
                    high.setBackgroundResource(R.drawable.greenbet);

                }
                else{
                    high.setBackgroundResource(R.drawable.greeb_pressd_btn);
                    MAP[41] += CHIP;
                    BET_SUM += CHIP;
                    bet_amount.setText(""+BET_SUM+"$");
                }
            }
        });
        low = findViewById(R.id.half1);
        low.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CHIP == -1){
                    int tmp = MAP[42];
                    MAP[42] = 0;
                    BET_SUM -= tmp;
                    bet_amount.setText(""+BET_SUM+"$");
                    low.setBackgroundResource(R.drawable.greenbet);

                }
                else{
                    low.setBackgroundResource(R.drawable.greeb_pressd_btn);
                    MAP[42] += CHIP;
                    BET_SUM += CHIP;
                    bet_amount.setText(""+BET_SUM+"$");
                }
            }
        });


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
    private boolean isRed(int n){
        if( n == 1|| n == 3|| n == 5||n == 7||n == 9||n == 12||n == 14||n == 16||n == 18||n == 19||
                n == 21||n == 23||n == 25|| n == 27 || n == 30|| n == 32|| n == 34|| n == 36){
            return true;
        }
        else{
            return false;
        }
    }
    private void resetColor() {
        b0.setBackgroundResource(R.drawable.greenbet);
        low.setBackgroundResource(R.drawable.greenbet);
        high.setBackgroundResource(R.drawable.greenbet);
        odd.setBackgroundResource(R.drawable.greenbet);
        even.setBackgroundResource(R.drawable.greenbet);
        red.setBackgroundResource(R.drawable.btnred);
        black.setBackgroundResource(R.drawable.black_btn);
        b1.setBackgroundResource(R.drawable.btnred);
        b3.setBackgroundResource(R.drawable.btnred);
        b5.setBackgroundResource(R.drawable.btnred);
        b7.setBackgroundResource(R.drawable.btnred);
        b9.setBackgroundResource(R.drawable.btnred);
        b12.setBackgroundResource(R.drawable.btnred);
        b14.setBackgroundResource(R.drawable.btnred);
        b16.setBackgroundResource(R.drawable.btnred);
        b18.setBackgroundResource(R.drawable.btnred);
        b19.setBackgroundResource(R.drawable.btnred);
        b20.setBackgroundResource(R.drawable.btnred);
        b21.setBackgroundResource(R.drawable.btnred);
        b23.setBackgroundResource(R.drawable.btnred);
        b25.setBackgroundResource(R.drawable.btnred);
        b27.setBackgroundResource(R.drawable.btnred);
        b30.setBackgroundResource(R.drawable.btnred);
        b32.setBackgroundResource(R.drawable.btnred);
        b34.setBackgroundResource(R.drawable.btnred);
        b36.setBackgroundResource(R.drawable.btnred);
        b2.setBackgroundResource(R.drawable.black_btn);
        b4.setBackgroundResource(R.drawable.black_btn);
        b6.setBackgroundResource(R.drawable.black_btn);
        b8.setBackgroundResource(R.drawable.black_btn);
        b10.setBackgroundResource(R.drawable.black_btn);
        b11.setBackgroundResource(R.drawable.black_btn);
        b13.setBackgroundResource(R.drawable.black_btn);
        b15.setBackgroundResource(R.drawable.black_btn);
        b17.setBackgroundResource(R.drawable.black_btn);
        b20.setBackgroundResource(R.drawable.black_btn);
        b22.setBackgroundResource(R.drawable.black_btn);
        b24.setBackgroundResource(R.drawable.black_btn);
        b26.setBackgroundResource(R.drawable.black_btn);
        b28.setBackgroundResource(R.drawable.black_btn);
        b29.setBackgroundResource(R.drawable.black_btn);
        b31.setBackgroundResource(R.drawable.black_btn);
        b33.setBackgroundResource(R.drawable.black_btn);
        b35.setBackgroundResource(R.drawable.black_btn);



    }

}