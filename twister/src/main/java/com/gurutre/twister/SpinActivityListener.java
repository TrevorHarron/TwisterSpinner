package com.gurutre.twister;

import android.app.Activity;
import android.app.FragmentManager;
import android.util.Log;
import android.view.View;
import java.util.Random;

import util.TwistSpinner;

public class SpinActivityListener implements View.OnClickListener {

    final static String TAG = "Spin-Listener";
    Random rng;
    FragmentManager manager;

    public SpinActivityListener(Random r, Activity a){
        rng = r;
        manager = a.getFragmentManager();
    }

    @Override
    public void onClick(View view) {
        int spin = rng.nextInt(16);
        Log.v(TAG, "The number spun is: "+spin);
        switch(view.getId()){
            case R.id.spin_button:
                Log.v(TAG, "Spin Button has been clicked");
                TwistSpinner.getSpinAndShow(spin, manager);
                break;
            default:
                Log.i(TAG, "Another button was clicked.");
                break;
        }
    }

}
