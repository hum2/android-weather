package jp.asciimw.androidbook.chapter2.weather.util;

/**
 * Created by hum2 on 14/11/13.
 */
public class Temperature
{
    /**
     * return Degrees
     *
     * @param kelvin
     * @return string
     */
    public static String kelvinToDegrees(Float kelvin)
    {
        return Integer.toString(Math.round(kelvin - 273.15f)) + '℃';
    }

    /**
     * return Degrees
     *
     * @param kelvin
     * @return string
     */
    public static String kelvinToDegrees(String kelvinStr)
    {
        Float kelvin = Float.valueOf(kelvinStr);
        return kelvinToDegrees(kelvin);
    }
}
