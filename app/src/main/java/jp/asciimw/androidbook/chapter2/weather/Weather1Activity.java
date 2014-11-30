package jp.asciimw.androidbook.chapter2.weather;

import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import roboguice.activity.RoboListActivity;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;

@ContentView(R.layout.main)
public class Weather1Activity
        extends RoboListActivity
{
    private List<Weather> mWeatherList;
    private WeatherArrayAdapter mAdapter;

    @InjectView(R.id.executeBtn)
    private Button executeBtn;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        mWeatherList = new ArrayList<Weather>();
        mAdapter = new WeatherArrayAdapter(getApplicationContext(), mWeatherList);
        getListView().setAdapter(mAdapter);
        executeBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                getAllWeatherData();
                v.setEnabled(false);
            }
        });
    }

    private void getAllWeatherData()
    {
        final String[] prefectures = getResources().getStringArray(R.array.prefectures);
        for (String prefecture : prefectures) {
            String url = getResources().getString(R.string.weather_api_url, prefecture);
            try {
                new WeatherAsyncTask(this, mWeatherList, mAdapter, url).execute();
            }  catch(Exception e) {
                Log.d(Weather1Activity.class.getSimpleName(), e.getMessage());
            }
        }
    }
}