package com.example.user.application_screentest;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.util.List;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final View view = findViewById(R.id.view);

        view.setOnTouchListener(new View.OnTouchListener(){

            float originX, originY, finX, finY;
             /*
            * originX, originY: When user touch the screen this variable is stored the coordinates
            * finX, finY: When user finished the swipe motion this variable is stored the final coordinates
            */

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                Context c = v.getContext();

                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    originX = event.getX();
                    originY = event.getY();
                }

                else if(event.getAction() == MotionEvent.ACTION_UP) {
                    finX = event.getX();
                    finY = event.getY();

                    Log.d("check_loc", "ACTION_UP\norigin: "+originX+", "+originY+"\nfin: "+finX+", "+finY);

                    /*
                    * This conditional statement is related to 'Coordinate Plane'.
                    * It should be noted that on the Android mobile phone screen, the x-axis is bigger the right
                    * and the y-axis is bigger the lower.
                    * In addition, the coordinates at the top left end of the screen and view are (0,0).
                    *
                    * If user swipe 'up', the final y-coordinate is smaller than origin y-coordinate.
                    * The x-coordinate is distinguished by condition method.
                    */
                    if(finY < originY && "up".equals(condition(originX, originY, finX, finY))){
                        Log.d("check_loc","ACTION_UP--> up");
                        Toast.makeText(c,"up",Toast.LENGTH_SHORT).show();
                    }
                    else if(finX < originX && "left".equals(condition(originX, originY, finX, finY))){
                        Log.d("check_loc","ACTION_UP--> left");
                        Toast.makeText(c,"left",Toast.LENGTH_SHORT).show();
                    }
                    else if(finY > originY && "down".equals(condition(originX, originY, finX, finY))){
                        Log.d("check_loc","ACTION_UP--> down");
                        Toast.makeText(c,"down",Toast.LENGTH_SHORT).show();
                    }
                    else if(finX > originX && "right".equals(condition(originX, originY, finX, finY))){
                        Log.d("check_loc","ACTION_UP--> right");
                        Toast.makeText(c,"right",Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Log.d("check_loc","ACTION_UP--> error");
                        Toast.makeText(c,"Try again",Toast.LENGTH_SHORT).show();
                    }
                }
                return true;
            }


            /*
            * It is hard to know what user's meant to do swipe motion is 'up' or 'right' because of their ambiguous swiping.
            * Divided the coordinate plane by two diagonal lines which is inclined to 45 degrees to solve this problem.
            * In other words, use the y = x, y = -x functions based on origin (0, 0).
            * For this code to be applied, origin (0,0) should be considered on behalf of (originX, originY).
            * As a result, the formula changes to y = x - originX + originY, y = - x + originX + originY.
            *
            * Compare the position of the first touch with the point at which it was finally released,
            * and distinguish the swipe motion as shown below.
            *
            * If user swipe 'up', the final x-coordinate is located on the larger side of the graph
            * based on a graph such as y = x - originX + originY, y = - x + originX + originY.
            * */
            public String condition(float originX, float originY, float finX, float finY){
                if(finY > finX - originX + originY){
                    if(finY > - finX + originX + originY)
                        return "down";
                    else
                        return "left";
                }
                else{
                    if(finY > - finX + originX + originY)
                        return "right";
                    else
                        return "up";
                }
            }
        });

    }//end of onCreate method

}//end of MainActivity class
