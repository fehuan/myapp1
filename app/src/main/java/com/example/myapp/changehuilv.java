package com.example.myapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;


public class ChangeHuilv extends AppCompatActivity implements Runnable{
    private final String TAG = "main";
    float dollarRate = 0.1406f;
    float euroRate = 0.1276f;
    float wonRate = 167.8471f;
    EditText DollarRate2 ;
    EditText EuroRate2 ;
    EditText WonRate2 ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changehuilv);

        DollarRate2 = (EditText)findViewById(R.id.DollarRate);
        EuroRate2 = (EditText)findViewById(R.id.EuroRate);
        WonRate2 = (EditText)findViewById(R.id.WonRate);

        //从huilv.java取数据
        Bundle bdl = getIntent().getExtras();
         dollarRate = bdl.getFloat("key_dollar",0.1f);
         euroRate = bdl.getFloat("key_euro",0.1f);
         wonRate = bdl.getFloat("key_won",0.1f);

        //设置默认汇率显示
        String dollarRate2 = String.valueOf(dollarRate);
        DollarRate2.setText(dollarRate2);//

        String euroRate2 = String.valueOf(euroRate);
        EuroRate2.setText(euroRate2);

        String wonRate2 = String.valueOf(wonRate);
        WonRate2.setText(wonRate2);

    }

    public void  save(View btn){
        String d =DollarRate2.getText().toString();
        float newDollar = Float.parseFloat(d);

        String e =EuroRate2.getText().toString();
        float newEuro = Float.parseFloat(e);

        String w =WonRate2.getText().toString();
        float newWon = Float.parseFloat(w);

        Intent intent = getIntent();
        Bundle bdl = new Bundle();
        bdl.putFloat("key_dollar", newDollar);
        bdl.putFloat("key_euro", newEuro);
        bdl.putFloat("key_won", newWon);

        intent.putExtras(bdl);//传递参数
        setResult(2,intent);//设置resultCode及带回的数据

        finish();//返回到调用页面

        //存储数据到rate.xml
        SharedPreferences sp=getSharedPreferences("rate",MODE_PRIVATE);
        SharedPreferences.Editor editor=sp.edit();
        editor.putFloat("key_dollar",newDollar);
        editor.putFloat("key_euro",newEuro);
        editor.putFloat("key_won",newWon);
        editor.commit();
    }

    public void commit(View btn) {
        Thread t = new Thread(this);
        t.start();
    }

    public void run() {
        Log.i(TAG, "run: run()......");

        try {
            String url = "http://www.usd-cny.com/bankofchina.htm";
            Document doc = Jsoup.connect(url).get();
            Log.i(TAG, "run: " + doc.title());
            Elements tables = doc.getElementsByTag("table");
            Element table6 = tables.get(0);
            // 获取 TD 中的数据
            Elements tds = table6.getElementsByTag("td");
            for(int i=0;i<tds.size();i+=6){
                Element td1 = tds.get(i);
                Element td2 = tds.get(i+5);
                String str1 = td1.text();
                String val = td2.text();
                Log.i(TAG, "run: " + str1 + "==>" + val);

                float vF = 100f / Float.parseFloat(val);
                String vS =String.valueOf(vF);
                if("美元".equals(str1)){
                    DollarRate2.setText(vS);
                }else if("欧元".equals(str1)){
                    EuroRate2.setText(vS);
                }else if("韩元".equals(str1)){
                    WonRate2.setText(vS);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
          }
    }
}

