package jp.asciimw.androidbook.chapter2.weather;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.inject.Inject;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import roboguice.activity.RoboListActivity;
import roboguice.inject.ContentView;

@ContentView(R.layout.main)
public class Weather3Activity
        extends RoboListActivity
{
    private List<Weather> mWeatherList;
    private WeatherArrayAdapter mAdapter;
    private WeatherLooperThread mWeatherLooperThread;

    public void click(View view)
    {
        getAllWeatherData();
        view.setEnabled(false);
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        mWeatherList = new ArrayList<Weather>();
        mAdapter = new WeatherArrayAdapter(getApplicationContext(), mWeatherList);
        getListView().setAdapter(mAdapter);
        mWeatherLooperThread = new WeatherLooperThread();
        mWeatherLooperThread.start();
    }

    private void getAllWeatherData()
    {
        final String[] prefectures = getResources().getStringArray(R.array.prefectures);
        for (String prefecture : prefectures) {
            mWeatherLooperThread.getWeatherData(getResources().getString(R.string.weather_api_url, prefecture));
        }
    }

    private class WeatherArrayAdapter
            extends ArrayAdapter<Weather>
    {
        private LayoutInflater mInflater;

        public WeatherArrayAdapter(Context context, List<Weather> weatherList)
        {
            super(context, 0, weatherList);
            mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            ViewHolder holder;

            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.row, null, false);
                holder = new ViewHolder();
                holder.icon = (ImageView) convertView.findViewById(R.id.icon);
                holder.city = (TextView) convertView.findViewById(R.id.city_name);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            Weather weather = getItem(position);
            holder.city.setText(weather.city);
            String iconUrl = weather.weatherIconUrl;
            holder.icon.setTag(iconUrl);
            Bitmap b = ImageMap.getImage(iconUrl);
            if (b != null) {
                holder.icon.setImageBitmap(b);
            } else {
                holder.icon.setImageDrawable(null);
                new SetImageTask(iconUrl, holder.icon).execute((Void) null);
            }
            return convertView;
        }

        private class ViewHolder
        {
            TextView city;
            ImageView icon;
        }
    }

    private class WeatherLooperThread
            extends Thread
    {
        private DefaultHttpClient mClient;
        @Inject
        private WeatherFeedParserInterface mParser;
        private Handler mUIHandler;
        private Handler mOtherHandler;

        private WeatherLooperThread()
        {
            mUIHandler = new Handler();
            mClient = new DefaultHttpClient();
        }

        void getWeatherData(final String url)
        {
            mOtherHandler.post(new Runnable()
            {
                @Override
                public void run()
                {
                    final Weather result = executeImpl(url);
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
            });
        }

        @Override
        public void run()
        {
            Looper.prepare();
            mOtherHandler = new Handler();
            Looper.loop();
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