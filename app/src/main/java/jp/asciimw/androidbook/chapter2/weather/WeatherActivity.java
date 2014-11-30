package jp.asciimw.androidbook.chapter2.weather;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class WeatherActivity
        extends Activity
{

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.top);
    }

    public void goto1(View view)
    {
        Intent intent = new Intent(this, Weather1Activity.class);
        startActivity(intent);
    }

    public void goto2(View view)
    {
        Intent intent = new Intent(this, Weather2Activity.class);
        startActivity(intent);
    }

    public void goto2_2(View view)
    {
        Intent intent = new Intent(this, Weather2_2Activity.class);
        startActivity(intent);
    }

    public void goto3(View view)
    {
        Intent intent = new Intent(this, Weather3Activity.class);
        startActivity(intent);
    }

    public void goto4(View view)
    {
        Intent intent = new Intent(this, Weather4Activity.class);
        startActivity(intent);
    }
}