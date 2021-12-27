package com.example.roulette;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
            low,high,btn_allIn,btn10k,btn5000,info,last10 , ok, cancel;
    public int [] MAP = new int [43];
    private String [] nums = new String[10];
    private TextView bet_amount,user_amount,selected_chip,final_bet_txt, text_view;
    String BET_STRING,UserID;
    private FirebaseUser user;
    Dialog dialog;
    public long BALANCE;
    private DatabaseReference reference,boss_reference;
    private static int CHIP = 0,BET_SUM = 0, ADD_SUM = 0,USER_AMOUNT = 0;
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
                user_amount.setText(""+USER_AMOUNT);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
        reset_btn = findViewById(R.id.reset);
        reset_btn.setOnClickListener(new View.OnClickListener() {
            //reset all bets
            @Override
            public void onClick(View v) {
                for (int i = 0; i< 43; i++){
                    MAP[i] = 0;
                }
                BET_SUM = 0;
                bet_amount.setText(""+BET_SUM);
                final_bet_txt.setText("");
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
                                    }

                                    reference.child(UserID).child("bet").child("odd").setValue("" + MAP[37]);
                                    reference.child(UserID).child("bet").child("even").setValue("" + MAP[38]);
                                    reference.child(UserID).child("bet").child("red").setValue("" + MAP[39]);
                                    reference.child(UserID).child("bet").child("black").setValue("" + MAP[40]);
                                    reference.child(UserID).child("bet").child("high").setValue("" + MAP[41]);
                                    reference.child(UserID).child("bet").child("low").setValue("" + MAP[42]);
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
                MAP[0] += CHIP;
                BET_SUM += CHIP;
                int new_sum = BET_SUM+ADD_SUM;
                bet_amount.setText(""+new_sum);

            }
        });
        b1 = (Button) findViewById(R.id.b1);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MAP [1] += CHIP;
                BET_SUM += CHIP;
                int new_sum = BET_SUM+ADD_SUM;
                bet_amount.setText(""+new_sum);

            }
        });
        b2 = (Button) findViewById(R.id.b2);
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MAP [2] += CHIP;
                BET_SUM += CHIP;
                int new_sum = BET_SUM+ADD_SUM;
                bet_amount.setText(""+new_sum);
            }
        });
        b3 = (Button) findViewById(R.id.b3);
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MAP [3] += CHIP;
                BET_SUM += CHIP;
                int new_sum = BET_SUM+ADD_SUM;
                bet_amount.setText(""+new_sum);
            }
        });
        b4 = (Button) findViewById(R.id.b4);
        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MAP [4] += CHIP;
                BET_SUM += CHIP;
                int new_sum = BET_SUM+ADD_SUM;
                bet_amount.setText(""+new_sum);
            }
        });
        b5 = (Button) findViewById(R.id.b5);
        b5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MAP [5] += CHIP;
                BET_SUM += CHIP;
                int new_sum = BET_SUM+ADD_SUM;
                bet_amount.setText(""+new_sum);
            }
        });
        b6 = (Button) findViewById(R.id.b6);
        b6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MAP [6] += CHIP;
                BET_SUM += CHIP;
                int new_sum = BET_SUM+ADD_SUM;
                bet_amount.setText(""+new_sum);
            }
        });
        b7 = (Button) findViewById(R.id.b7);
        b7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MAP [7] += CHIP;
                BET_SUM += CHIP;
                int new_sum = BET_SUM+ADD_SUM;
                bet_amount.setText(""+new_sum);
            }
        });
        b8 = (Button) findViewById(R.id.b8);
        b8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MAP [8] += CHIP;
                BET_SUM += CHIP;
                int new_sum = BET_SUM+ADD_SUM;
                bet_amount.setText(""+new_sum);
            }
        });
        b9 = (Button) findViewById(R.id.b9);
        b9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MAP [9] += CHIP;
                BET_SUM += CHIP;
                int new_sum = BET_SUM+ADD_SUM;
                bet_amount.setText(""+new_sum);
            }
        });
        b10 = (Button) findViewById(R.id.b10);
        b10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MAP [10] += CHIP;
                BET_SUM += CHIP;
                int new_sum = BET_SUM+ADD_SUM;
                bet_amount.setText(""+new_sum);
            }
        });
        b11 = (Button) findViewById(R.id.b11);
        b11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MAP [11] += CHIP;
                BET_SUM += CHIP;
                int new_sum = BET_SUM+ADD_SUM;
                bet_amount.setText(""+new_sum);
            }
        });
        b12 = (Button) findViewById(R.id.b12);
        b12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MAP [12] += CHIP;
                BET_SUM += CHIP;
                int new_sum = BET_SUM+ADD_SUM;
                bet_amount.setText(""+new_sum);
            }
        });
        b13 = (Button) findViewById(R.id.b13);
        b13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MAP [13] += CHIP;
                BET_SUM += CHIP;
                int new_sum = BET_SUM+ADD_SUM;
                bet_amount.setText(""+new_sum);
            }
        });
        b14 = (Button) findViewById(R.id.b14);
        b14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MAP [14] += CHIP;
                BET_SUM += CHIP;
                int new_sum = BET_SUM+ADD_SUM;
                bet_amount.setText(""+new_sum);
            }
        });
        b15 = (Button) findViewById(R.id.b15);
        b15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MAP [15] += CHIP;
                BET_SUM += CHIP;
                int new_sum = BET_SUM+ADD_SUM;
                bet_amount.setText(""+new_sum);
            }
        });
        b16 = (Button) findViewById(R.id.b16);
        b16.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MAP [16] += CHIP;
                BET_SUM += CHIP;
                int new_sum = BET_SUM+ADD_SUM;
                bet_amount.setText(""+new_sum);
            }
        });
        b17 = (Button) findViewById(R.id.b17);
        b17.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MAP [17] += CHIP;
                BET_SUM += CHIP;
                int new_sum = BET_SUM+ADD_SUM;
                bet_amount.setText(""+new_sum);

            }
        });
        b18 = (Button) findViewById(R.id.b18);
        b18.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MAP [18] += CHIP;
                BET_SUM += CHIP;
                int new_sum = BET_SUM+ADD_SUM;
                bet_amount.setText(""+new_sum);
            }
        });
        b19 = (Button) findViewById(R.id.b19);
        b19.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MAP [19] += CHIP;
                BET_SUM += CHIP;
                int new_sum = BET_SUM+ADD_SUM;
                bet_amount.setText(""+new_sum);
            }
        });
        b20 = (Button) findViewById(R.id.b20);
        b20.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MAP [20] += CHIP;
                BET_SUM += CHIP;
                int new_sum = BET_SUM+ADD_SUM;
                bet_amount.setText(""+new_sum);
            }
        });
        b21 = (Button) findViewById(R.id.b21);
        b21.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MAP [21] += CHIP;
                BET_SUM += CHIP;
                int new_sum = BET_SUM+ADD_SUM;
                bet_amount.setText(""+new_sum);
            }
        });
        b22 = (Button) findViewById(R.id.b22);
        b22.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MAP [22] += CHIP;
                BET_SUM += CHIP;
                int new_sum = BET_SUM+ADD_SUM;
                bet_amount.setText(""+new_sum);
            }
        });
        b23 = (Button) findViewById(R.id.b23);
        b23.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MAP [23] += CHIP;
                BET_SUM += CHIP;
                int new_sum = BET_SUM+ADD_SUM;
                bet_amount.setText(""+new_sum);
            }
        });
        b24 = (Button) findViewById(R.id.b24);
        b24.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MAP [24] += CHIP;
                BET_SUM += CHIP;
                int new_sum = BET_SUM+ADD_SUM;
                bet_amount.setText(""+new_sum);
            }
        });
        b25 = (Button) findViewById(R.id.b25);
        b25.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MAP [25] += CHIP;
                BET_SUM += CHIP;
                int new_sum = BET_SUM+ADD_SUM;
                bet_amount.setText(""+new_sum);
            }
        });
        b26 = (Button) findViewById(R.id.b26);
        b26.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MAP [26] += CHIP;
                BET_SUM += CHIP;
                int new_sum = BET_SUM+ADD_SUM;
                bet_amount.setText(""+new_sum);
            }
        });
        b27 = (Button) findViewById(R.id.b27);
        b27.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MAP [27] += CHIP;
                BET_SUM += CHIP;
                int new_sum = BET_SUM+ADD_SUM;
                bet_amount.setText(""+new_sum);
            }
        });
        b28 = (Button) findViewById(R.id.b28);
        b28.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MAP [28] += CHIP;
                BET_SUM += CHIP;
                int new_sum = BET_SUM+ADD_SUM;
                bet_amount.setText(""+new_sum);
            }
        });
        b29 = (Button) findViewById(R.id.b29);
        b29.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MAP [29] += CHIP;
                BET_SUM += CHIP;
                int new_sum = BET_SUM+ADD_SUM;
                bet_amount.setText(""+new_sum);
            }
        });
        b30 = (Button) findViewById(R.id.b30);
        b30.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MAP [30] += CHIP;
                BET_SUM += CHIP;
                int new_sum = BET_SUM+ADD_SUM;
                bet_amount.setText(""+new_sum);

            }
        });
        b31 = (Button) findViewById(R.id.b31);
        b31.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MAP [31] += CHIP;
                BET_SUM += CHIP;
                int new_sum = BET_SUM+ADD_SUM;
                bet_amount.setText(""+new_sum);

            }
        });
        b32 = (Button) findViewById(R.id.b32);
        b32.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MAP [32] += CHIP;
                BET_SUM += CHIP;
                int new_sum = BET_SUM+ADD_SUM;
                bet_amount.setText(""+new_sum);
            }
        });
        b33 = (Button) findViewById(R.id.b33);
        b33.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MAP [33] += CHIP;
                BET_SUM += CHIP;
                int new_sum = BET_SUM+ADD_SUM;
                bet_amount.setText(""+new_sum);

            }
        });
        b34 = (Button) findViewById(R.id.b34);
        b34.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MAP [34] += CHIP;
                BET_SUM += CHIP;
                int new_sum = BET_SUM+ADD_SUM;
                bet_amount.setText(""+new_sum);

            }
        });
        b35 = (Button) findViewById(R.id.b35);
        b35.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MAP [35] += CHIP;
                BET_SUM += CHIP;
                int new_sum = BET_SUM+ADD_SUM;
                bet_amount.setText(""+new_sum);
            }
        });
        b36 = (Button) findViewById(R.id.b36);
        b36.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MAP [36] += CHIP;
                BET_SUM += CHIP;
                int new_sum = BET_SUM+ADD_SUM;
                bet_amount.setText(""+new_sum);

            }
        });

        btn5 = (Button)findViewById(R.id.bet5);
        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CHIP = 5;
                selected_chip.setText("5");
            }
        });
        btn25 = (Button)findViewById(R.id.bet25);
        btn25.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CHIP=25;
                selected_chip.setText("25");
            }
        });
        btn100 = (Button)findViewById(R.id.bet100);
        btn100.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CHIP=100;
                selected_chip.setText("100");
            }
        });
        btn500 = (Button)findViewById(R.id.bet500);
        btn500.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CHIP=500;
                selected_chip.setText("500");
            }
        });
        btn1000 = (Button) findViewById(R.id.bet1000);
        btn1000.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CHIP=1000;
                selected_chip.setText("1000");
            }
        });
        btn5000 = (Button) findViewById(R.id.bet5000);
        btn5000.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CHIP=5000;
                selected_chip.setText("5000");
            }
        });
        btn10k = (Button) findViewById(R.id.bet10k);
        btn10k.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CHIP=10000;
                selected_chip.setText("10K");
            }
        });
        btn_allIn = (Button) findViewById(R.id.betaLLiN);
        btn_allIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CHIP=USER_AMOUNT;
                selected_chip.setText(""+USER_AMOUNT);
            }
        });
        odd = findViewById(R.id.odd);
        odd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MAP [37] += CHIP;
                BET_SUM += CHIP;
                int new_sum = BET_SUM+ADD_SUM;
                bet_amount.setText(""+new_sum);
            }
        });
        even = findViewById(R.id.even);
        even.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MAP [38] += CHIP;
                BET_SUM += CHIP;
                int new_sum = BET_SUM+ADD_SUM;
                bet_amount.setText(""+new_sum);
            }
        });
        red = findViewById(R.id.red);
        red.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MAP [39] += CHIP;
                BET_SUM += CHIP;
                int new_sum = BET_SUM+ADD_SUM;
                bet_amount.setText(""+new_sum);
            }
        });
        black = findViewById(R.id.black);
        black.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MAP [40] += CHIP;
                BET_SUM += CHIP;
                int new_sum = BET_SUM+ADD_SUM;
                bet_amount.setText(""+new_sum);
            }
        });
        high = findViewById(R.id.half2);
        high.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MAP [41] += CHIP;
                BET_SUM += CHIP;
                int new_sum = BET_SUM+ADD_SUM;
                bet_amount.setText(""+new_sum);
            }
        });
        low = findViewById(R.id.half1);
        low.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MAP [42] += CHIP;
                BET_SUM += CHIP;
                int new_sum = BET_SUM+ADD_SUM;
                bet_amount.setText(""+new_sum);
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

}