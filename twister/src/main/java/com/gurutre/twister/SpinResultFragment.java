package com.gurutre.twister;

import android.app.DialogFragment;
import android.util.Log;
import android.widget.TextView;
import android.os.Bundle;
import android.view.View;
import android.view.LayoutInflater;
import android.view.ViewGroup;

public class SpinResultFragment extends DialogFragment {
    private int color;
    private String limb;

    public SpinResultFragment() { }

    @Override
    public void onCreate(Bundle bundle){
        super.onCreate(bundle);
        if(bundle != null){
            color = bundle.getInt("colorId");
            limb = bundle.getString("limb");
        }
        //here read the string and set the color and limb
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle state) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_result, container, false);
        Log.d("Spin-Result","The view is null: "+(v ==null));
        if(v != null){
            Log.d("Spin-Result", "Limb: "+limb+". colorId number: "+color);
            ((CircleView)v.findViewById(R.id.color_swatch))
                    .setFillColor(getResources().getColor(color));
            ((TextView)v.findViewById(R.id.result_text)).setText(limb);
        }
        return v;
    }

    @Override
    public void onResume(){
        int width = getResources().getDisplayMetrics().widthPixels;
        int height = getResources().getDisplayMetrics().heightPixels;
        getDialog().getWindow().setLayout((3*width)/4,(3*height)/4);
        View v = getView();
        if(v != null){
            int radius = 0;
            if(width >= height)
                radius = (3*height)/4;
            else
                radius = (3*width)/4;

            ((CircleView)v.findViewById(R.id.color_swatch)).setCircleRadius(radius);
        }
        super.onResume();
    }

    public void onDestroy(){
        super.onDestroy();
        try{
            ((View.OnClickListener) getActivity()).onClick(null);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

}
