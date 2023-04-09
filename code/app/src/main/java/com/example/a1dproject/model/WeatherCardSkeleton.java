package com.example.a1dproject.model;

import android.widget.ImageView;
import android.widget.TextView;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;
import android.widget.TextView;

public class WeatherCardSkeleton {
    private TextView mLocationText;
    private TextView mTemperatureText;
    private TextView mWeatherText;
    private ImageView mWeatherImage;
    private TextView mWindSpeedText;
    private TextView mHumidityText;
    private ImageView mForecastTomorrowWeatherImage;
    private TextView mForecastTomorrowTemperature;
    private ImageView mForecastDayAfterWeatherImage;
    private TextView mForecastDayAfterTemperature;

    public void setMLocationText(TextView locationText) {
        mLocationText = locationText;
    }

    public void setMTemperatureText(TextView temperatureText) {
        mTemperatureText = temperatureText;
    }

    public void setMWeatherText(TextView weatherText) {
        mWeatherText = weatherText;
    }

    public void setMWeatherImage(ImageView weatherImage) {
        mWeatherImage = weatherImage;
    }

    public void setMWindSpeedText(TextView windSpeedText) {
        mWindSpeedText = windSpeedText;
    }

    public void setMHumidityText(TextView humidityText) {
        mHumidityText = humidityText;
    }

    public void setMForecastTomorrowWeatherImage(ImageView forecastTomorrowWeatherImage) {
        mForecastTomorrowWeatherImage = forecastTomorrowWeatherImage;
    }

    public void setMForecastTomorrowTemperature(TextView forecastTomorrowTemperature) {
        mForecastTomorrowTemperature = forecastTomorrowTemperature;
    }

    public void setMForecastDayAfterWeatherImage(ImageView forecastDayAfterWeatherImage) {
        mForecastDayAfterWeatherImage = forecastDayAfterWeatherImage;
    }

    public void setMForecastDayAfterTemperature(TextView forecastDayAfterTemperature) {
        mForecastDayAfterTemperature = forecastDayAfterTemperature;
    }

    public void setLocationText(CharSequence locationText) {
        if (mLocationText != null) {
            mLocationText.setText(locationText);
        }
    }

    public void setTemperatureText(CharSequence temperatureText) {
        if (mTemperatureText != null) {
            mTemperatureText.setText(temperatureText);
        }
    }

    public void setWeatherText(CharSequence weatherText) {
        if (mWeatherText != null) {
            mWeatherText.setText(weatherText);
        }
    }

    public void setWeatherImage(Drawable drawable) {
        if (mWeatherImage != null) {
            mWeatherImage.setImageDrawable(drawable);
        }
    }

    public void setWindSpeedText(CharSequence windSpeedText) {
        if (mWindSpeedText != null) {
            mWindSpeedText.setText(windSpeedText);
        }
    }

    public void setHumidityText(CharSequence humidityText) {
        if (mHumidityText != null) {
            mHumidityText.setText(humidityText);
        }
    }

    public void setForecastTomorrowWeatherImage(Drawable drawable) {
        if (mForecastTomorrowWeatherImage != null) {
            mForecastTomorrowWeatherImage.setImageDrawable(drawable);
        }
    }

    public void setForecastTomorrowTemperature(CharSequence temperatureText) {
        if (mForecastTomorrowTemperature != null) {
            mForecastTomorrowTemperature.setText(temperatureText);
        }
    }

    public void setForecastDayAfterWeatherImage(Drawable drawable) {
        if (mForecastDayAfterWeatherImage != null) {
            mForecastDayAfterWeatherImage.setImageDrawable(drawable);
        }
    }

    public void setForecastDayAfterTemperature(CharSequence temperatureText) {
        if (mForecastDayAfterTemperature != null) {
            mForecastDayAfterTemperature.setText(temperatureText);
        }
    }
}