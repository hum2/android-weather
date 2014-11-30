package jp.asciimw.androidbook.chapter2.weather;

import java.io.IOException;

/**
 * Created by hum2 on 14/11/29.
 */
public interface WeatherFeedParserInterface
{
    public Weather parse(String contents)
            throws WeatherFeedParserException, IOException;

}
