package util;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;

import com.gurutre.twister.R;
import com.gurutre.twister.SpinResultFragment;

/**
 * Created by gurutre on 8/31/14.
 */
public class TwistSpinner {

    private static String TAG = "Spinner";
    private static FragmentTransaction transaction;
    private static SpinResultFragment fragment;

    private static String getLimb(int spinNumber){
        String base = " on:";
        Log.v(TAG, "The limb number is " + spinNumber / 4);
        switch (spinNumber/4){
            case 0: return  "Left Foot" +base;
            case 1: return "Right Hand"+base;
            case 2: return "Right Foot"+base;
            case 3: return "Left Hand"+base;
            default: return "limb";
        }
    }

    private static int getColor(int spinNumber){
        Log.v(TAG,"The color number is "+ spinNumber%4);
        switch (spinNumber % 4){
            case 0: return R.color.green;
            case 1: return R.color.red;
            case 2: return R.color.yellow;
            case 3: return R.color.blue;
            default: return 0;
        }
    }

    private static String getColor(int spinNumber, boolean asString){
        Log.v(TAG,"The color number is "+ spinNumber%4);
        switch (spinNumber % 4){
            case 0: return "green";
            case 1: return "red";
            case 2: return "yellow";
            case 3: return "blue";
            default: return "blank";
        }
    }

    public static void getSpinAndShow(int spin, FragmentManager manager){

        Bundle state = new Bundle();
        state.putInt("colorId", getColor(spin));
        state.putString("limb", getLimb(spin));

        fragment = new SpinResultFragment();
        fragment.onCreate(state);
        transaction = manager.beginTransaction();
        transaction.add(fragment, fragment.getTag());

        transaction.commitAllowingStateLoss();
    }

    public static void close(){
        if(fragment !=null){
            Log.i(TAG,"close called");
            fragment.dismiss();
            fragment = null;
        }
    }

    public static String getSpinString(int spin) {
        return getLimb(spin) + getColor(spin,true);
    }
}
