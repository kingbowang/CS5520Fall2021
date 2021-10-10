package edu.neu.madcourse.numad21fa_pengbowang;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ActivityClicky extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clicky);
    }

    public void onBtnClick(View view) {
        TextView button10 = findViewById(R.id.textView2);
        button10.setText(R.string.PressedA);
    }

    public void onBtnClick1(View view) {
        TextView button2 = findViewById(R.id.textView2);
        button2.setText(R.string.PressedB);
    }

    public void onBtnClick2(View view) {
        TextView button3 = findViewById(R.id.textView2);
        button3.setText(R.string.PressedC);
    }

    public void onBtnClick3(View view) {
        TextView button7 = findViewById(R.id.textView2);
        button7.setText(R.string.PressedD);
    }

    public void onBtnClick4(View view) {
        TextView button8 = findViewById(R.id.textView2);
        button8.setText(R.string.PressedE);
    }

    public void onBtnClick5(View view) {
        TextView button9 = findViewById(R.id.textView2);
        button9.setText(R.string.PressedF);
    }

    public void onBtnClick6(View view) {
        TextView textView4 = findViewById(R.id.textView2);
        textView4.setText(R.string.NoPressed);
    }
}
