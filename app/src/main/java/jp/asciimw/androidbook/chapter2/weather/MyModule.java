package jp.asciimw.androidbook.chapter2.weather;

import android.util.Log;

import com.google.inject.AbstractModule;

/**
 * Created by hum2 on 14/11/28.
 */
public class MyModule
        extends AbstractModule
{
    @Override
    protected void configure()
    {
        Log.d(MyModule.class.getSimpleName(), "load configure");
        // bind weather parser
        bind(WeatherFeedParserInterface.class).toProvider(WeatherFeedParserProvider.class);
    }
}
