package jp.asciimw.androidbook.chapter2.weather;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.inject.Inject;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import roboguice.activity.RoboListActivity;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;

@ContentView(R.layout.main)
public class Weather2Activity
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
        Log.d("HOGE", "getAllWeatherData");
        for (String prefecture : prefectures) {
            new WeatherThread(getResources().getString(R.string.weather_api_url, prefecture)).start();
        }
    }

    private class WeatherThread
            extends Thread
    {
        @Inject
        private DefaultHttpClient mClient;
        @Inject
        private WeatherFeedParserInterface mParser;
        @Inject
        private Handler mUIHandler;
        private String mUrl;

        private WeatherThread(String url)
        {
//            mUIHandler = new Handler();
//            mClient = new DefaultHttpClient();
            mUrl = url;
            Log.d("HOGE", "WeatherThread");
        }

        @Override
        public void run()
        {
            final Weather result = executeImpl(mUrl);
            mUIHandler.post(new Runnable()
            {
                @Override
                public void run()
                {
                    if (result != null) {
                        mWeatherList.add(result);
                        mAdapter.notifyDataSetChanged();
                    }
                }
            });
        }

        private Weather executeImpl(String url)
        {
            Weather entity = null;
            try {
                final HttpGet req = new HttpGet(url);
                final HttpResponse response = executeRequest(req);
                final int statusCode = response.getStatusLine().getStatusCode();
                final StringBuilder buf = new StringBuilder();
                final InputStream in = response.getEntity().getContent();
                final BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                String l = null;
                while ((l = reader.readLine()) != null) {
                    buf.append(l);
                }
                if (statusCode == 200) {
                    entity = mParser.parse(buf.toString());
                }
            } catch (IOException e) {
            } catch (WeatherFeedParserException e) {
            } catch (RuntimeException e) {
            }
            return entity;
        }

        private HttpResponse executeRequest(HttpRequestBase base)
                throws IOException
        {
            try {
                return mClient.execute(base);
            } catch (IOException e) {
                base.abort();
                throw e;
            }
        }
    }

}