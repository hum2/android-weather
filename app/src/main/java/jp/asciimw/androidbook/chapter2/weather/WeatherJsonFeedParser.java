package jp.asciimw.androidbook.chapter2.weather;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import jp.asciimw.androidbook.chapter2.weather.util.Temperature;

public class WeatherJsonFeedParser
        implements WeatherFeedParserInterface
{
    private String iconUrl;

    public Weather parse(String contents)
            throws WeatherFeedParserException, IOException
    {
        try {
            JSONObject json = new JSONObject(contents);
            Log.d(this.getClass().getName(), "json = " + json.toString());
            return parseWeather(json);
        } catch (JSONException e) {
            throw new WeatherFeedParserException(e.getMessage());
        }
    }

    public void setIconUrl(String iconUrl)
    {
        this.iconUrl = iconUrl;
    }

    private Weather parseWeather(JSONObject json)
            throws JSONException, IOException
    {
        Weather weather = new Weather();
        int type = -1;
        JSONArray main = json.getJSONArray("weather");

        weather.city = getCityName(json);
        weather.weatherIconUrl = getIconUrl(json);
        String[] temp = getTemp(json);
        weather.temp_min = temp[0];
        weather.temp_max = temp[1];

        return weather;
    }

    private String getCityName(JSONObject json)
            throws JSONException
    {
        String cityName = "unknown";
        try {
            cityName = json.getString("name");
        } catch (JSONException e) {
        }

        return cityName;
    }

    private String getIconUrl(JSONObject json)
            throws JSONException
    {
        String iconUrl = "";
        try {
            JSONArray array = json.getJSONArray("weather");
            JSONObject w = array.getJSONObject(0);

            iconUrl = String.format(this.iconUrl, w.getString("icon"));
        } catch (JSONException e) {
        }

        return iconUrl;
    }

    private String[] getTemp(JSONObject json)
            throws JSONException
    {
        String[] temp = new String[2];
        try {
            JSONObject j = json.getJSONObject("main");
            temp[0] = Temperature.kelvinToDegrees(j.getString("temp_min"));
            temp[1] = Temperature.kelvinToDegrees(j.getString("temp_max"));
        } catch (Exception e) {

        }

        return temp;
    }
}
