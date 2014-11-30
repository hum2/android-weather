package jp.asciimw.androidbook.chapter2.weather;

import jp.asciimw.androidbook.chapter2.weather.Weather;

interface IWeatherListener {
    void onWeatherReceived(in Weather weather);
}
