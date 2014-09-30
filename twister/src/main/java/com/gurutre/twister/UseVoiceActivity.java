package com.gurutre.twister;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

import util.TwistSpinner;

public class UseVoiceActivity extends Activity implements View.OnClickListener, TextToSpeech.OnInitListener {

    protected static final int RESULT_SPEECH = 1;
    private static String TAG = "Voice-Activity";
    private boolean started = false;
    private TextToSpeech tts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_use_voice);
        Button useVoice = (Button) findViewById(R.id.use_voice_button);
        if(useVoice != null) useVoice.setOnClickListener(this);
        tts = new TextToSpeech(this, this);
    }


    @Override
    public void onClick(View v) {
        Log.v(TAG,"onClick has been called");
        listenToVoice();
    }

    private void listenToVoice() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, "en-US");
            try {
                Log.v(TAG,"Starting up the voice recognition");
                startActivityForResult(intent, RESULT_SPEECH);
            } catch (ActivityNotFoundException e) {
                Toast.makeText(this,"Error: Speech to Text not supported", Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
    }

    @Override
    public void onResume(){
        super.onResume();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        switch (requestCode) {
            case RESULT_SPEECH: {
                if (resultCode == RESULT_OK && data != null) {
                    ArrayList<String> text = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

                    String words = "";
                    for(String word: text) words += word+".";
                    Log.i(TAG,words);
                    if(!started && text.contains("start")){
                        started = true;
                        spinAndShow();
                    } else if(text.contains("spin") || text.contains("go")){
                        started = true;
                        spinAndShow();
                    } else if(text.contains("stop") || text.contains("done")
                            || text.contains("polo")){
                        started = false;
                        return;
                    } else {
                        started = false;
                        Toast.makeText(this,"Please say spin, go, start, stop, or end",
                                Toast.LENGTH_LONG).show();
                        return;
                    }
                }
                break;
            }
        }
    }

    private void spinAndShow() {
        final FragmentManager f = this.getFragmentManager();
        final int spin = new Random(System.currentTimeMillis()).nextInt(16);

        if (!tts.isSpeaking())
            tts.speak(TwistSpinner.getSpinString(spin), TextToSpeech.QUEUE_FLUSH,null);

        TwistSpinner.getSpinAndShow(spin,f);
        final Timer t =new Timer();
        t.schedule(new TimerTask() {
            @Override
            public void run() {
                Log.d(TAG, "In the timer task");
                TwistSpinner.close();
                t.cancel();
            }
        },5000);
    }

    @Override
    public void onInit(int code) {
        if (code == TextToSpeech.SUCCESS) {
            tts.setLanguage(Locale.getDefault());
        } else {
            tts = null;
            Toast.makeText(this, "Failed to initialize Text-To-Speech.",Toast.LENGTH_SHORT).show();
        }

    }
}
