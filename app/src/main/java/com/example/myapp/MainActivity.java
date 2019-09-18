package com.example.myapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class MainActivity extends AppCompatActivity{
    EditText ET1;
    EditText ET2;
    EditText ET3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a1);
        //update
        ET1 = (EditText)findViewById(R.id.editText1);
        ET2 = (EditText)findViewById(R.id.editText2);
        ET3 = (EditText)findViewById(R.id.editText3);
        //下面是第一种方法
        Button Btn1 = (Button)findViewById(R.id.ok);//获取按钮资源
        Btn1.setOnClickListener(new Button.OnClickListener(){//创建监听
            public void onClick(View v) {
                String strTmp = "mfhgit";
                ET1.setText(strTmp);
            }
        });
        //下面是第二种方法
        Button Btn2 = (Button) findViewById(R.id.end);//获取按钮资源
        Btn2.setOnClickListener(listener);//设置监听
    }
    Button.OnClickListener listener = new Button.OnClickListener(){//创建监听对象
        public void onClick(View v){
            String strTmp="mfh123456";
            ET2.setText(strTmp);
        }
    };
    //下面是第三种方法
    public void abc(View view){
        String strTmp="root123";
        ET3.setText(strTmp);
    }

}
