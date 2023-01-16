package org.tensorflow.lite.examples.detection;

import android.app.Application;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import org.tensorflow.lite.examples.detection.tflite.Classifier;

import java.util.List;

public class FindKiosk extends Application {

    private List<Classifier.Recognition> results;
    private String scanText;

    @Override
    public void onCreate() {
        super.onCreate();
        this.results = null;
        this.scanText = null;
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

    public void setScanText(String scan){
        this.scanText = scan;
        Log.v("trackResults",""+this.scanText );
    }

    public void FindObject(TTS tts){
        if(this.results.size() == 0){
            Log.v("trackResults","키오스크가 발견되지 않았습니다." );
        }else{
            List<Classifier.Recognition> kiosk = null;
            List<Classifier.Recognition> cardreader = null;
            for(Classifier.Recognition i : this.results){
                Log.v("trackResults", " id : " + i.getId() + ", title : " + i.getTitle() + ", confidence : " + i.getConfidence() + ", location : " + i.getLocation());
                if (i.getConfidence() > 0.8f){
                    if(i.getTitle().equals("kiosk"))
                        kiosk.add(i);
                    else
                        cardreader.add(i);
                }
            }
            Log.v("tracking","  *******    " + kiosk);

            String KioskStr = null;
            String CardReaderStr = null;

            if(kiosk == null)
                KioskStr = "키오스크가 발견되지 않았습니다.";
            else
                KioskStr = "키오스크가 발견되지 않았습니다.";
                tts.speakOut("키오스크가 " + kiosk.size() + "개" + "발견되었습니다");
            if(cardreader == null)
                CardReaderStr = "카드리더기가 발견되지 않았습니다.";
            else
            CardReaderStr = "카드리더기가 " + kiosk.size() + "개" + "발견되었습니다";

            tts.speakOut(KioskStr +"  "+ CardReaderStr);
        }
    }

    public void ReadText(TTS tts){
//        this.tts
    }
}
