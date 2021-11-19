package com.example.roulette;

import androidx.appcompat.app.AppCompatActivity;

import android.app.admin.DeviceAdminService;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

public class Table extends AppCompatActivity {
    TextView textView;
    Button spin,bet;
    ImageView imageView;
    Random r;
    private int degree, old_degree;
    private boolean isSpinning = false;
    // 0 32 15 19 4 21 2 25 17 34 6 27 13 36 11 30 8 23 10 5 24 16 33 1 20 14 31 9 22 18 29 7 28 12 35 3 26
    private static final String [] sectors = {"32 red","15 black","19 red","4 black","21 red","2 black",
            "25 red","17 black","34 red","6 black","27 red","13 black","36 red","11 black","30 red","8 black",
            "23 red","10 black","5 red","24 black","16 red","33 black","1 red","20 black","14 red","31 black",
            "9 red","22 black","18 red","29 black","7 red","28 black","12 red","35 black","3 red","0"};
    private static final int [] sectorsDegrees = new int [sectors.length];
    private static  final Random random = new Random();
//    private static final float FACTOR =4.86f;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table);

        spin = (Button) findViewById(R.id.spin);
        imageView = (ImageView) findViewById(R.id.imageView);
        textView = (TextView) findViewById(R.id.textView7);

        getDegree();
        r = new Random();
//        degree = 0;
//        old_degree = 0;


        spin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isSpinning){
                    spin();
                    isSpinning = true;
                }

//                old_degree = degree;
//                degree = r.nextInt(3600)+720;
//                RotateAnimation rotate = new RotateAnimation(old_degree,degree,RotateAnimation.RELATIVE_TO_SELF,0.5f, RotateAnimation.RELATIVE_TO_SELF,0.5f);
//                rotate.setDuration(3600);
//                rotate.setFillAfter(true);
//                rotate.setInterpolator(new DecelerateInterpolator());
//                rotate.setAnimationListener(new Animation.AnimationListener() {
//                    @Override
//                    public void onAnimationStart(Animation animation) {
//                        textView.setText("");
//                    }
//
//                    @Override
//                    public void onAnimationEnd(Animation animation) {
//                        textView.setText(currentNumber(360 - (degree) % 360));
//                    }
//
//                    @Override
//                    public void onAnimationRepeat(Animation animation) {
//                        degree = 0;
//                    }
//                });
//                imageView.startAnimation(rotate);
            }

            private void spin() {
                degree = random.nextInt(sectors.length-1);
                RotateAnimation rotate = new RotateAnimation(0,(360*sectors.length)+sectorsDegrees[degree],
                        RotateAnimation.RELATIVE_TO_SELF,0.5f,RotateAnimation.RELATIVE_TO_SELF,0.5f);
                rotate.setDuration(3600);
                rotate.setFillAfter(true);
                rotate.setInterpolator(new DecelerateInterpolator());
                rotate.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        textView.setText(sectors[sectors.length-(degree +1)]);
                        isSpinning = false;

                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                imageView.startAnimation(rotate);



            }
        });
            bet = (Button) findViewById(R.id.bet);
            bet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(Table.this, bet.class));
                }
            });




    }

    private void getDegree(){
        int sectorDegree = 360/sectors.length;
        for (int i=0 ; i< sectors.length; i++){
            sectorsDegrees[i] = (i+1) * sectorDegree;
        }

    }

//    private  String currentNumber(int degrees){
//        String text = "";
//        if(degrees >= (FACTOR *1)  && degrees < (FACTOR*3)){
//            text = "32 red";
//        }
//        if(degrees >= FACTOR*3  && degrees < (FACTOR*5)){
//            text = "15 black";
//        }
//        if(degrees >= FACTOR*5  && degrees < (FACTOR*7)){
//            text = "19 red";
//        }
//        if(degrees >= FACTOR*7  && degrees < (FACTOR*9)){
//            text = "4 black";
//        }
//        if(degrees >= FACTOR*9  && degrees < (FACTOR*11)){
//            text = "21 red";
//        }
//        if(degrees >= FACTOR*11  && degrees < (FACTOR*13)){
//            text = "2 black";
//        }
//        if(degrees >= FACTOR*13  && degrees < (FACTOR*15)){
//            text = "25 red";
//        }
//        if(degrees >= FACTOR*15  && degrees < (FACTOR*17)){
//            text = "17 black";
//        }
//        if(degrees >= FACTOR*17  && degrees < (FACTOR*19)){
//            text = "34 red";
//        }
//        if(degrees >= FACTOR*19  && degrees < (FACTOR*21)){
//            text = "6 black";
//        }
//        if(degrees >= FACTOR*21  && degrees < (FACTOR*23)){
//            text = "27 red";
//        }
//        if(degrees >= FACTOR*23  && degrees < (FACTOR*25)){
//            text = "13 black";
//        }
//        if(degrees >= FACTOR*25  && degrees < (FACTOR*27)){
//            text = "36 red";
//        }
//        if(degrees >= FACTOR*27  && degrees < (FACTOR*29)){
//            text = "11 black";
//        }
//        if(degrees >= FACTOR*29  && degrees < (FACTOR*31)){
//            text = "30 red";
//        }
//        if(degrees >= FACTOR*31  && degrees < (FACTOR*33)){
//            text = "8 black";
//        }
//        if(degrees >= FACTOR*33  && degrees < (FACTOR*35)){
//            text = "23 red";
//        }
//        if(degrees >= FACTOR*35  && degrees < (FACTOR*37)){
//            text = "10 black";
//        }
//        if(degrees >= FACTOR*37  && degrees < (FACTOR*39)){
//            text = "5 red";
//        }
//        if(degrees >= FACTOR*39  && degrees < (FACTOR*41)){
//            text = "24 black";
//        }
//        if(degrees >= FACTOR*41  && degrees < (FACTOR*43)){
//            text = "16 red";
//        }
//        if(degrees >= FACTOR*43  && degrees < (FACTOR*45)){
//            text = "33 black";
//        }
//        if(degrees >= FACTOR*45  && degrees < (FACTOR*47)){
//            text = "1 red";
//        }
//        if(degrees >= FACTOR*47  && degrees < (FACTOR*49)){
//            text = "20 black";
//        }
//        if(degrees >= FACTOR*49  && degrees < (FACTOR*51)){
//            text = "14 red";
//        }
//        if(degrees >= FACTOR*51  && degrees < (FACTOR*53)){
//            text = "31 black";
//        }
//        if(degrees >= FACTOR*53  && degrees < (FACTOR*55)){
//            text = "9 red";
//        }
//        if(degrees >= FACTOR*55  && degrees < (FACTOR*57)){
//            text = "22 black";
//        }
//        if(degrees >= FACTOR*57  && degrees < (FACTOR*59)){
//            text = "18 red";
//        }
//        if(degrees >= FACTOR*59  && degrees < (FACTOR*61)){
//            text = "29 black";
//        }
//        if(degrees >= FACTOR*61  && degrees < (FACTOR*63)){
//            text = "7 red";
//        }
//        if(degrees >= FACTOR*63  && degrees < (FACTOR*65)){
//            text = "28 black";
//        }
//        if(degrees >= FACTOR*65  && degrees < (FACTOR*67)){
//            text = "12 red";
//        }
//        if(degrees >= FACTOR*67  && degrees < (FACTOR*69)){
//            text = "35 black";
//        }
//        if(degrees >= FACTOR*69  && degrees < (FACTOR*71)){
//            text = "3 red";
//        }
//        if(degrees >= FACTOR*71  && degrees < (FACTOR*73)){
//            text = "26 black ";
//        }
//        if((degrees >= FACTOR*73  && degrees < 360) || (degrees >= 0 && degrees <(FACTOR*1))){
//            text = "0  ";
//        }



//        return text;
//    }
}