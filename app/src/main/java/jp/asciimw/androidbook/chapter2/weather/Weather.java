package jp.asciimw.androidbook.chapter2.weather;

import android.os.Parcel;
import android.os.Parcelable;

public class Weather
        implements Parcelable
{
    public static final Parcelable.Creator<Weather> CREATOR
            = new Parcelable.Creator<Weather>()
    {
        public Weather createFromParcel(Parcel parcel)
        {
            return new Weather(parcel);
        }

        public Weather[] newArray(int size)
        {
            return new Weather[size];
        }
    };
    public String city;
    public String weatherIconUrl;
    public String temp_min;
    public String temp_max;

    public Weather()
    {
    }

    public Weather(Parcel in)
    {
        readFromParcel(in);
    }

    public int describeContents()
    {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int flags)
    {
        parcel.writeString(city);
        parcel.writeString(weatherIconUrl);
    }

    public void readFromParcel(Parcel parcel)
    {
        city = parcel.readString();
        weatherIconUrl = parcel.readString();
    }

    @Override
    public String toString()
    {
        return "Weather [city=" + city + ", weatherIconUrl=" + weatherIconUrl + "]";
    }

}
