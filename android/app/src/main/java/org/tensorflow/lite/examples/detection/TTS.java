package org.tensorflow.lite.examples.detection;

import android.content.Context;
import android.os.Build;
import android.speech.tts.TextToSpeech;
import android.util.Log;

import androidx.annotation.RequiresApi;

import java.util.Locale;

public class TTS implements TextToSpeech.OnInitListener {

    private TextToSpeech tts;
    public TTS(Context context){
        this.tts = new TextToSpeech(context, this);
    }
    @Override
    public void onInit(int status) {
        if(status == TextToSpeech.SUCCESS){
            int result = tts.setLanguage(Locale.ENGLISH);
            if(result == TextToSpeech.LANG_NOT_SUPPORTED || result == TextToSpeech.LANG_MISSING_DATA){
                Log.e("TTS","Language not supported");
            }else{
                Log.v("TTS","Language supported");
            }
        }else{
            Log.e("TTS","Initialization failed");
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void speakOut(CharSequence text) {
        tts.setPitch((float) 0.6);
        tts.setSpeechRate((float) 0.1);
        tts.speak(text,TextToSpeech.QUEUE_FLUSH,null,"id1");
    }
}
