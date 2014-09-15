package com.gurutre.twister;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import java.util.Random;

public class SpinnerUseButtonActivity extends Activity {

    private static final String TAG = "Main Spinner Activity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SpinActivityListener listener =
                new SpinActivityListener(new Random(System.currentTimeMillis()),this);
        setContentView(R.layout.activity_spinner_use_button);
        Button spinButton = (Button) findViewById(R.id.spin_button);
        if(spinButton != null){
            spinButton.setOnClickListener(listener);
            Log.d(TAG,"Spinner Button configured");
        }
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
    }

}
