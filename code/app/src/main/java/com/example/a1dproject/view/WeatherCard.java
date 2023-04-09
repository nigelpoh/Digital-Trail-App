package com.example.a1dproject.view;

import android.content.Context;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import androidx.core.content.ContextCompat;

import com.example.a1dproject.R;
import com.example.a1dproject.model.WeatherAPIResponse;
import com.example.a1dproject.model.WeatherCardSkeleton;
import com.example.a1dproject.utils.MiscUtils;
import com.example.a1dproject.utils.NetworkUtils;
import com.example.a1dproject.utils.UIUtils;

public class WeatherCard extends LinearLayout {
    private ProgressBar progressBar;
    private LinearLayout mainContent;
    private WeatherCardSkeleton weatherCard;
    private WeatherCardAsyncTask asyncTask;

    public WeatherCard(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.weather_card, this, true);

        mainContent = findViewById(R.id.main_content);
        progressBar = findViewById(R.id.progress_bar);
        weatherCard = new WeatherCardSkeleton();

        // Set up the WeatherCardSkeleton views
        weatherCard.setMLocationText(findViewById(R.id.location));
        weatherCard.setMTemperatureText(findViewById(R.id.location_temperature));
        weatherCard.setMWeatherText(findViewById(R.id.location_weather));
        weatherCard.setMWeatherImage(findViewById(R.id.location_weather_img));
        weatherCard.setMWindSpeedText(findViewById(R.id.location_windspeed));
        weatherCard.setMHumidityText(findViewById(R.id.location_humidity));
        weatherCard.setMForecastTomorrowWeatherImage(findViewById(R.id.forecast_tomorrow_weather_img));
        weatherCard.setMForecastTomorrowTemperature(findViewById(R.id.forecast_tomorrow_temperature));
        weatherCard.setMForecastDayAfterWeatherImage(findViewById(R.id.forecast_dayafter_weather));
        weatherCard.setMForecastDayAfterTemperature(findViewById(R.id.forecast_dayafter_temperature));

        if(NetworkUtils.isNetworkAvailable(context)) {
            asyncTask = new WeatherCardAsyncTask(context);
            asyncTask.execute();
        }else{
            progressBar.setVisibility(View.VISIBLE);
            mainContent.setVisibility(View.GONE);
        }
    }

    private class WeatherCardAsyncTask extends AsyncTask<Void, Void, WeatherAPIResponse> {
        private Context context;

        public WeatherCardAsyncTask(Context context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
            mainContent.setVisibility(View.GONE);

        }

        @Override
        protected WeatherAPIResponse doInBackground(Void... voids) {
            return NetworkUtils.getWeatherData(context).join();
        }
        @Override
        protected void onPostExecute(WeatherAPIResponse weatherData) {
            super.onPostExecute(weatherData);
            progressBar.setVisibility(View.GONE);
            mainContent.setVisibility(View.VISIBLE);
            weatherCard.setLocationText(weatherData.getLocation());
            weatherCard.setTemperatureText(weatherData.getCurrentConditions().getTemperature() + "ºC");
            weatherCard.setWeatherText(weatherData.getCurrentConditions().getWeather());
            weatherCard.setWindSpeedText(weatherData.getCurrentConditions().getWindSpeed() + " km/h");
            weatherCard.setHumidityText(weatherData.getCurrentConditions().getHumidity() + "%");
            weatherCard.setWeatherImage(ContextCompat.getDrawable(context, UIUtils.getWeatherImage(weatherData.getCurrentConditions().getWeather(), true)));
            weatherCard.setForecastTomorrowTemperature(weatherData.getTomorrowForecast().getTemperature() + "ºC");
            weatherCard.setForecastTomorrowWeatherImage(ContextCompat.getDrawable(context, UIUtils.getWeatherImage(weatherData.getTomorrowForecast().getWeather(), false)));
            weatherCard.setForecastDayAfterTemperature(weatherData.getTwoDaysForecast().getTemperature() + "ºC");
            weatherCard.setForecastDayAfterWeatherImage(ContextCompat.getDrawable(context, UIUtils.getWeatherImage(weatherData.getTwoDaysForecast().getWeather(), false)));
        }
    }
}
