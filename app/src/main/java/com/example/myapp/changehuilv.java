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
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class changehuilv extends AppCompatActivity implements Runnable{
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

        /*从rate.xml取数据
        SharedPreferences share=getSharedPreferences("a",MODE_PRIVATE);
        float dollarRate3=share.getFloat("DollarRate",0.0f);
        float euroRate3=share.getFloat("EuroRate",0.0f);
        float wonrRate3=share.getFloat("WonRate",0.0f);
        */

        //从huilv.java取数据
        Bundle bundle = getIntent().getExtras();
         dollarRate = bundle.getFloat("key_dollar",0.1f);
         euroRate = bundle.getFloat("key_euro",0.1f);
         wonRate = bundle.getFloat("key_won",0.1f);

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

        /*存储数据到rate.xml
        SharedPreferences share=getSharedPreferences("rate",MODE_PRIVATE);
        SharedPreferences.Editor editor=share.edit();
        editor.putFloat("DollarRate",newDollar);
        editor.putFloat("EuroRate",newEuro);
        editor.putFloat("WonRate",newWon);
        editor.commit();
*/

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
                // 获取数据并放入bundle中
                float v = 100f / Float.parseFloat(val);
                Bundle bdl = new Bundle();
                if("美元".equals(str1)){
                    bdl.putFloat("DollarRate", v);
                }else if("欧元".equals(str1)){
                    bdl.putFloat("EuroRate", v);
                }else if("韩元".equals(str1)){
                    bdl.putFloat("WonRate", v);
                }
                // 汇率数据放入Bundle带回
                Intent intent = getIntent();
                Message msg = handler.obtainMessage(5);
                msg.obj = bdl;
                handler.sendMessage(msg);
                intent.putExtras(bdl);//传递参数
                setResult(2,intent);//设置resultCode及带回的数据

                finish();//返回到调用页面

            }
        } catch (IOException e) {
            e.printStackTrace();
          }
    }


    private Handler handler = new  Handler() {
        @Override
        public void handleMessage(Message msg) {
            if(msg.what==5){
                Bundle bdl = (Bundle) msg.obj;
                dollarRate = bdl.getFloat("DollarRate");
                euroRate = bdl.getFloat("EuroRate");
                wonRate = bdl.getFloat("WonRate");

                Log.i(TAG, "handleMessage: dollarRate:" + dollarRate);
                Log.i(TAG, "handleMessage: euroRate:" + euroRate);
                Log.i(TAG, "handleMessage: wonRate:" + wonRate);
                Toast.makeText(changehuilv.this , "汇率已更新", Toast.LENGTH_SHORT).show();
            }
            super.handleMessage(msg);
        }
    };

  /*  另一种获取网络数据方法
  private String inputStream2String(InputStream inputStream)
            throws IOException { final int bufferSize = 1024;
        final char[] buffer = new char[bufferSize];
        final StringBuilder out = new StringBuilder();
        Reader in = new InputStreamReader(inputStream, "gb2312");
        while (true) {
            int rsz = in.read(buffer, 0, buffer.length);
            if (rsz < 0)
                break;
            out.append(buffer, 0, rsz);
        }
        return out.toString();
    }

   private Handler handler = new  Handler() {
        @Override
        public void handleMessage(Message msg){
            if(msg.what==5){
                String str = (String) msg.obj;
                Log.i(TAG, "handleMessage: getMessage msg = " + str);
                EditText DollarRate= findViewById(R.id.DollarRate);//读取xml中对象
                DollarRate.setText(str);
            }
            super.handleMessage(msg);
        }
    };
    */
}

