package com.example.myapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class JiSuan extends AppCompatActivity {
    private  static  String TAG ="Main";
    EditText jinput;
    TextView joutput;
    TextView jname;
    String rate;
    String name;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jisuan);

        jname = (TextView) findViewById(R.id.Jname);
        jinput = (EditText) findViewById(R.id.Jinput);
        joutput = (TextView) findViewById(R.id.Joutput);

        //获取bundle中的数据
        Bundle bdl = getIntent().getExtras();
        name = bdl.getString("title");
        rate = bdl.getString("detail");

        jname.setText(name);//显示货币名称
    }

    public void btn_o(View btn) {
        String str = jinput.getText().toString();
        Log.i(TAG, "onClick: get str=" + str);

        float s = 0;
        if(str.length()>0){
            s = Float.parseFloat(str);
        }else{
            //用户没有输入内容
            Toast.makeText(this, "请输入内容", Toast.LENGTH_SHORT).show();
            return;
        }

        Float r = Float.parseFloat(rate);
        joutput.setText("The quantity of money is:" + String.valueOf(100/r*s));
    }
}
