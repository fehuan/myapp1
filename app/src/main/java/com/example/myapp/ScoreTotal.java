package com.example.myapp;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ScoreTotal extends AppCompatActivity {
    TextView outA;
    TextView outB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_total);

        outA = (TextView)findViewById(R.id.outA);
        outB =(TextView)findViewById(R.id.outB);
    }
    public void sco1(View v){
        showTA(1);
    }

    public void sco2(View v){
        showTA(2);
    }

    public void sco3(View v){
        showTA(3);
    }

    public void sco4(View v){
        showTB(1);
    }

    public void sco5(View v){
        showTB(2);
    }

    public void sco6(View v){
        showTB(3);
    }

    public void btn_reset(View v){
        outA.setText("0");
        outB.setText("0");
    }

    public void showTA (int i){
        String oldScore = (String) outA.getText();
        String newScore = String.valueOf(Integer.parseInt(oldScore)+ i);
        outA.setText(newScore);
    }
    private void showTB(int i){
        String oldScore =(String) outB.getText();
        String newScore =String.valueOf(Integer.parseInt(oldScore)+ i);
        outB.setText(newScore);
    }

}
