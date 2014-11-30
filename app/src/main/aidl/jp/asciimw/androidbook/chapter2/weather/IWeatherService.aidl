package jp.asciimw.androidbook.chapter2.weather;

import jp.asciimw.androidbook.chapter2.weather.IWeatherListener;

interface IWeatherService {
	void addWeatherListener(IWeatherListener listener);
	void removeWeatherListener(IWeatherListener listener);
	void execute();
}