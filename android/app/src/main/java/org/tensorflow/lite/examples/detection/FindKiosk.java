package org.tensorflow.lite.examples.detection;

import android.app.Application;
import android.util.Log;

import org.tensorflow.lite.examples.detection.tflite.Classifier;

import java.util.List;

public class FindKiosk extends Application {

    private List<Classifier.Recognition> results;

    @Override
    public void onCreate() {
        super.onCreate();
        this.results = null;

    }

    public void Init() {
        this.results = null;

    }

    public List getResult(){
        return this.results;
    }

    public void setResult(final List<Classifier.Recognition> detectResults){
        this.results = detectResults;
        Log.v("trackResults",""+this.results.size() );
    }

    public void FindObject(TTS tts){

        if(this.results.size() == 0){
            tts.speakOut("키오스크가 발견되지 않았습니다.");
            Log.v("trackResults","키오스크가 발견되지 않았습니다." );

        }

    }


}
