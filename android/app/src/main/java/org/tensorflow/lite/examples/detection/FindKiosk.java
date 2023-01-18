package org.tensorflow.lite.examples.detection;

import android.app.Application;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import org.tensorflow.lite.examples.detection.tflite.Classifier;

import java.util.ArrayList;
import java.util.List;

public class FindKiosk extends Application {

    private List<Classifier.Recognition> results;
    private String scanText;

    @Override
    public void onCreate() {
        super.onCreate();
        this.results =  new ArrayList<Classifier.Recognition>();
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
//        Log.v("trackResults",""+this.results.size() );
    }

    public void setScanText(String scan){
        this.scanText = scan;
        Log.v("tracking", "this.scanText       " + this.scanText);

    }

    public void FindObject(TTS tts){

        if(this.results != null){
            if(this.results.size() == 0){
//                Log.v("trackResults","키오스크가 발견되지 않았습니다." );
                tts.speakOut("키오스크가 발견되지 않았습니다." );
                return;
            }
            else{
                List<Classifier.Recognition> kiosk = new ArrayList<Classifier.Recognition>();;
                List<Classifier.Recognition> cardreader = new ArrayList<Classifier.Recognition>();;
                for(Classifier.Recognition i : this.results){
//                    Log.v("trackResults", " id : " + i.getId() + ", title : " + i.getTitle() + ", confidence : " + i.getConfidence() + ", location : " + i.getLocation());
                    if (i.getConfidence() > 0.7f){
                        if(i.getTitle().equals("kiosk")){
                            kiosk.add(i);
                        }
                        else if(i.getTitle().equals("card reader")){
                            cardreader.add(i);
                        }
                    }
                }
//            Log.v("tracking","  *******    " + kiosk);
//            Log.v("tracking","  *******    " + cardreader);

                String KioskStr = null;
                String CardReaderStr = null;

                if(kiosk.size() == 0)
                    KioskStr = "키오스크가 발견되지 않았습니다.";
                else
                    KioskStr = "키오스크가 " + kiosk.size() + "개" + "발견되었습니다";
                if(cardreader.size() == 0)
                    CardReaderStr = "카드리더기가 발견되지 않았습니다.";
                else
                    CardReaderStr = "카드리더기가 " + kiosk.size() + "개" + "발견되었습니다";

                tts.speakOut(KioskStr +"  "+ CardReaderStr);
                return;
            }
        }

    }

    public static boolean isInteger(String strValue) {
        try {
            Integer.parseInt(strValue);
            return true;
        } catch (NumberFormatException ex) {
            return false;
        }
    }


    class MyString{
        public String categoryString;
        public String menuString ;
        public String nowCategory;

        public MyString(){
            this.categoryString = new String();
            this.menuString = new String();
            this.nowCategory = new String();
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    public void ReadText(TTS tts) {

        String[] words = this.scanText.split("\\n");
        MyString myString = new MyString();

        myString.categoryString = "카테고리는 ";
        myString.menuString = "메뉴는 ";

        List<String> category = new ArrayList<String>();
//        HashMap menu = new HashMap();;
        List<String> menu = new ArrayList<String>();


        String tempMenu = new String();
        String tempCost = new String();

        String[] coffeeMenu = {
                "아메리카노", "카페라떼", "카라멜모카", "에스프레소",
                "에스프레소 콘파냐", "카라멜카페라떼", "카푸치노", "아포카토",
                "콜드브루라떼", "콜드브루", "말차라떼", "연유라떼", "비엔나커피", "티라미수 라떼"};

        for (String el : words) {


            if (el.equals("커피") == true || el.equals("베이커리") == true || el.equals("구움과자") == true || el.equals("생과일주스") == true) {
                category.add(el);
                Log.v("tracking", "category       " + el);
            } else {
                for (String coffe : coffeeMenu) {
                    if (el.equals(coffe) == true) {
                        Log.v("tracking", "category       " + el);
                        myString.nowCategory = "커피";
                        menu.add(el);
                        tempMenu = el;
//                    } else if (isInteger(el)) {
//                        tempCost = el;
//                        menu.put(tempMenu, tempCost);
//                    }
                    }
                }
            }
        }
        //            Log.v("tracking", "category       " + category.size());
        if (category.size() != 0) {
            for (String ca : category) {

                myString.categoryString += ca + ", ";
            }
            myString.categoryString += "가 있습니다.";
        } else {
            myString.categoryString += "없습니다.";
        }

        if (menu.size() != 0) {
            for (String me : menu) {
                myString.menuString += me + ", ";
            }
            myString.menuString += "가 있습니다.";
        } else {
            myString.menuString += " 없습니다.";
        }


//            menu.forEach((strKey) -> {
//                myString.menuString += String.valueOf(strKey) + ", ";
//                Log.v("tracking","strKey       "+ strKey );
//            });
        tts.speakOut(myString.categoryString + "현재 카테고리는 " + myString.nowCategory + "입니다. " + "  " + myString.menuString);
        Log.v("tracking",myString.categoryString + "현재 카테고리는 " + myString.nowCategory + "입니다. " + "  " + myString.menuString);

    }

}
