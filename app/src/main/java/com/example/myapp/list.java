package com.example.myapp;

import android.app.ListActivity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Thread.sleep;

public class list extends ListActivity implements Runnable{
    private  static  String TAG ="Main";
    Elements tds;
    String i;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_rate_list);

        Thread t = new Thread(this);
        t.start();

        //在子进程没有完成时间空等待
        while (i == null) {
            try {
                sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        List<String> list1 = new ArrayList<String>();
        for (int i = 0; i < tds.size(); i += 6) {
            Element td1 = tds.get(i);
            Element td2 = tds.get(i + 5);
            String str1 = td1.text();
            String val = td2.text();
            list1.add(str1 + "==>" + val);
            Log.i(TAG, "run: " + str1 + "==>" + val);
        }
        ListAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list1);
        setListAdapter(adapter);
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
             tds = table6.getElementsByTag("td");
            i ="123";//子进程完成标志
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

