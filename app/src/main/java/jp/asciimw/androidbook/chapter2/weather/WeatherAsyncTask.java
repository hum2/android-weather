package jp.asciimw.androidbook.chapter2.weather;

import android.content.Context;
import android.util.Log;

import com.google.inject.Inject;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import roboguice.util.RoboAsyncTask;

/**
 * Created by hum2 on 14/11/29.
 */
public class WeatherAsyncTask
        extends RoboAsyncTask<Weather>
{
    @Inject
    private DefaultHttpClient mClient;
    @Inject
    private WeatherFeedParserInterface mParser;

    private List<Weather> mWeatherList;

    private WeatherArrayAdapter mAdapter;

    private java.lang.String apiUrl;

    public WeatherAsyncTask(Context context, List<Weather> mWeatherList, WeatherArrayAdapter mAdapter, java.lang.String url)
    {
        super(context);
        this.mWeatherList = mWeatherList;
        this.mAdapter = mAdapter;
        this.apiUrl = url;
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

    @Override
    public Weather call()
            throws Exception
    {
        Weather entity = null;
        try {
            final HttpGet req = new HttpGet(this.apiUrl);
            final HttpResponse response = executeRequest(req);
            final int statusCode = response.getStatusLine().getStatusCode();
            final StringBuilder buf = new StringBuilder();
            final InputStream in = response.getEntity().getContent();
            final BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            java.lang.String l = null;
            while ((l = reader.readLine()) != null) {
                buf.append(l);
            }
            if (statusCode == 200) {
                Log.d(WeatherAsyncTask.class.getSimpleName(), buf.toString());
                entity = mParser.parse(buf.toString());
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }

        return entity;
    }

    @Override
    protected void onSuccess(Weather entity)
            throws Exception
    {
        if (entity != null) {
            mWeatherList.add(entity);
            mAdapter.notifyDataSetChanged();
        }
    }
}
