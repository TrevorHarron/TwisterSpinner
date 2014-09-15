package com.gurutre.twister;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class WelcomeActivity extends Activity implements View.OnClickListener {
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        Button useButton = (Button) findViewById(R.id.use_button);
        if(useButton != null) useButton.setOnClickListener(this);
        Button useVoice = (Button) findViewById(R.id.use_voice);
        if(useVoice != null) useVoice.setOnClickListener(this);
    }


    @Override
    public void onClick(View v){
        switch (v.getId()){

            case R.id.use_voice:
                intent = new Intent(WelcomeActivity.this,UseVoiceActivity.class);
                WelcomeActivity.this.startActivity(intent);
                break;
            case R.id.use_button:
                intent = new Intent(WelcomeActivity.this,SpinnerUseButtonActivity.class);
                WelcomeActivity.this.startActivity(intent);
                break;
        }
    }

}
