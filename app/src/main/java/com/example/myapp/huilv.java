package com.example.myapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class huilv extends AppCompatActivity {
    private  static  String TAG ="Main";
    float dollarRate = 0.1406f;
    float euroRate = 0.1276f;
    float wonRate = 167.8471f;
    EditText input ;
    TextView output ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_huilv);

       input = (EditText)findViewById(R.id.input);//读取xml中input对象
       output = (TextView)findViewById(R.id.output);
    }

    public void btn_config(View btn) {
        Intent intent = new Intent(this, changehuilv.class);
        //将参数打包成一个bundle
        Bundle bdl = new Bundle();
        bdl.putFloat("key_dollar", dollarRate);
        bdl.putFloat("key_euro", euroRate);
        bdl.putFloat("key_won", wonRate);
        //参数传递
        intent.putExtras(bdl);

        //日志
        Log.i(TAG,"openOne: dollarRate=" + dollarRate);
        Log.i(TAG,"openOne: euroRate=" + euroRate);
        Log.i(TAG,"openOne: wonRate=" + wonRate);

        startActivityForResult(intent, 1);
    }

    public void btn(String b){
        Log.i(TAG, "onClick: ");
        String str = input.getText().toString();
        Log.i(TAG, "onClick: get str=" + str);

        float r = 0;
        if(str.length()>0){
            r = Float.parseFloat(str);
        }else{
            //用户没有输入内容
            Toast.makeText(this, "请输入内容", Toast.LENGTH_SHORT).show();
            return;
        }
        Log.i(TAG, "onClick: r=" + r);
        //判断按的是哪一个按钮
        if(b== "dollar"){
            output.setText("The quantity of money is:" + String.valueOf(r*dollarRate)+ "$");
        }else if(b=="euro"){
            output.setText("The quantity of money is:" + String.valueOf(r*euroRate)+ "€");
        }else{
            output.setText("The quantity of money is:" + String.valueOf(r*wonRate) + "₩");
        }
    }

    public void btn_dollar(View v) {
        btn("dollar");
    }

    public void btn_euro(View v) {
        btn("euro");
    }

    public void btn_won(View v) {
        btn("won");
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && resultCode == 2) {
            Bundle bdl = data.getExtras();
            dollarRate = bdl.getFloat("key_dollar", 0.0f);
            euroRate = bdl.getFloat("key_euro", 0.0f);
            wonRate = bdl.getFloat("key_won", 0.0f);
            //下面日志信息
            Log.i(TAG, "onActivityResult: dollarRate=" + dollarRate);
            Log.i(TAG, "onActivityResult: euroRate=" + euroRate);
            Log.i(TAG, "onActivityResult: wonRate=" + wonRate);
        }
        super.onActivityResult( requestCode, resultCode, data);

    }


    //读取菜单
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);//紫色部分为menu.xml名称
        return true;
    }

    //菜单事件
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.Drate){
            // 事件处理代码
            String dollarRate2 = String.valueOf(dollarRate);
            Toast.makeText(this, "美元汇率为："+ dollarRate2,
                    Toast.LENGTH_LONG).show();
        }

        if(item.getItemId()==R.id.Erate){
            // 事件处理代码
            String euroRate2 = String.valueOf(euroRate);
            Toast.makeText(this, "欧元汇率为："+ euroRate2,
                    Toast.LENGTH_LONG).show();
        }

        if(item.getItemId()==R.id.Wrate){
            // 事件处理代码
            String wonRate2 = String.valueOf(wonRate);
            Toast.makeText(this, "南韩元汇率为："+ wonRate2,
                    Toast.LENGTH_LONG).show();
        }
        return super.onOptionsItemSelected(item);
    }
}
