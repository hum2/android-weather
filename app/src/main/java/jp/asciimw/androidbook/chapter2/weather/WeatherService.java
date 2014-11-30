package jp.asciimw.androidbook.chapter2.weather;

import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteCallbackList;
import android.os.RemoteException;

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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import roboguice.service.RoboService;

public class WeatherService
        extends RoboService
{
    private final IWeatherService.Stub stub = new IWeatherService.Stub()
    {
        public void addWeatherListener(IWeatherListener listener)
                throws RemoteException
        {
            mListeners.register(listener);
        }

        public void removeWeatherListener(IWeatherListener listener)
                throws RemoteException
        {
            mListeners.unregister(listener);
        }

        public void execute()
                throws RemoteException
        {
            final String[] prefectures = getResources().getStringArray(R.array.prefectures);
            for (String prefecture : prefectures) {
                mExecutor.execute(new GetWeatherTask(getResources().getString(R.string.weather_api_url, prefecture)));
            }
        }
    };
    private RemoteCallbackList<IWeatherListener> mListeners = new RemoteCallbackList<IWeatherListener>();
    private ExecutorService mExecutor = Executors.newFixedThreadPool(3);

    @Override
    public void onCreate()
    {
        super.onCreate();
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
    }

    private void fire(Weather weather)
    {
        try {
            final int numListeners = mListeners.beginBroadcast();
            for (int i = 0; i < numListeners; i++) {
                mListeners.getBroadcastItem(i).onWeatherReceived(weather);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                mListeners.finishBroadcast();
            } catch (Exception ex) {
            }
        }
    }

    public IBinder onBind(Intent intent)
    {
        return stub;
    }

    @Override
    public boolean onUnbind(Intent intent)
    {
        return super.onUnbind(intent);
    }

    private class GetWeatherTask
            implements Runnable
    {
        private DefaultHttpClient mClient;
        @Inject
        private WeatherFeedParserInterface mParser;
        private String mUrl;

        GetWeatherTask(String url)
        {
            mClient = new DefaultHttpClient();
            mUrl = url;
        }

        @Override
        public void run()
        {
            Weather weather = executeImpl(mUrl);
            fire(weather);
        }

        Weather executeImpl(String url)
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
                e.printStackTrace();
            } catch (WeatherFeedParserException e) {
                e.printStackTrace();
            } catch (RuntimeException e) {
                e.printStackTrace();
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
