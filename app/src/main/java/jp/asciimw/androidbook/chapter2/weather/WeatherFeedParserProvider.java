package jp.asciimw.androidbook.chapter2.weather;

import com.google.inject.Provider;

import roboguice.inject.InjectResource;

/**
 * Created by hum2 on 14/11/30.
 */
public class WeatherFeedParserProvider
        implements Provider<WeatherFeedParserInterface>
{
    @InjectResource(R.string.weather_icno_url)
    private String iconUrl;

    @Override
    public WeatherFeedParserInterface get()
    {
        WeatherJsonFeedParser parser = new WeatherJsonFeedParser();
        parser.setIconUrl(iconUrl);

        return parser;
    }
}
