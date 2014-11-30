package jp.asciimw.androidbook.chapter2.weather;

import android.app.ListActivity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class Weather4Activity
        extends ListActivity
{
    private List<Weather> mWeatherList;
    private WeatherArrayAdapter mAdapter;
    private IWeatherService mWeatherService;
    private ServiceConnection mConnection;
    private IWeatherListener mWeatherListener = new IWeatherListener.Stub()
    {
        @Override
        public void onWeatherReceived(final Weather weather)
                throws RemoteException
        {
            if (weather != null) {
//                mWeatherList.add(weather);
//                mAdapter.notifyDataSetChanged();
                runOnUiThread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        mWeatherList.add(weather);
                        mAdapter.notifyDataSetChanged();
                    }
                });
            }
        }
    };

    public void click(View view)
    {
        try {
            mWeatherService.execute();
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
        view.setEnabled(false);
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        mWeatherList = new ArrayList<Weather>();
        mAdapter = new WeatherArrayAdapter(getApplicationContext(), mWeatherList);
        getListView().setAdapter(mAdapter);
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        mConnection = new ServiceConnection()
        {
            public void onServiceConnected(ComponentName className, IBinder service)
            {
                mWeatherService = IWeatherService.Stub.asInterface(service);
                try {
                    mWeatherService.addWeatherListener(mWeatherListener);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }

            public void onServiceDisconnected(ComponentName className)
            {
                mWeatherService = null;
            }
        };
        bindService(new Intent(IWeatherService.class.getName()), mConnection, BIND_AUTO_CREATE);
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        if (mWeatherService != null) {
            try {
                mWeatherService.removeWeatherListener(mWeatherListener);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        unbindService(mConnection);
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
}