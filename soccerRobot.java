package com.muohio.soccerRobot;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class soccerRobot extends Activity {
	public static int[] ballColors;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        Button video = (Button) findViewById(R.id.Button02);
    	video.setOnClickListener(new View.OnClickListener() {
    		public void onClick(View v) {
    			
    			Bundle myBundle = new Bundle();
    			myBundle.putIntArray("ball", ballColors);
    			Intent myIntent = new Intent(getBaseContext(), RuntimeJava.class); 
    			myIntent.putExtras(myBundle);
    			startActivity(myIntent);
    			
    		}
    	});
    	
    	Button cal = (Button) findViewById(R.id.Button03);
    	cal.setOnClickListener(new View.OnClickListener() {
    		public void onClick(View v) {
    			
    			Intent myIntent = new Intent(getBaseContext(), Sample1Java.class); 
    			startActivityForResult(myIntent, 0);
    			
    		}
    	});
    	
    }
    
   
}