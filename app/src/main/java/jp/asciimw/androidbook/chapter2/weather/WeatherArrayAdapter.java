package jp.asciimw.androidbook.chapter2.weather;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by hum2 on 14/11/29.
 */
public class WeatherArrayAdapter
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
            holder.temp = (TextView) convertView.findViewById(R.id.temp);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Weather weather = getItem(position);
        holder.city.setText(weather.city);
        String iconUrl = weather.weatherIconUrl;
        holder.icon.setTag(iconUrl);
        String temp = weather.temp_min + "/" + weather.temp_max;
        holder.temp.setText(temp);
        Picasso.with(getContext()).load(iconUrl).into(holder.icon);
//            Bitmap b = ImageMap.getImage(iconUrl);
//            if (b != null) {
//                holder.icon.setImageBitmap(b);
//            } else {
//                holder.icon.setImageDrawable(null);
//                new SetImageTask(iconUrl, holder.icon).execute((Void) null);
//            }
        return convertView;
    }

    private class ViewHolder
    {
        TextView city;
        ImageView icon;
        TextView temp;
    }
}