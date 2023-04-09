package com.example.a1dproject.utils;

import com.example.a1dproject.R;

import java.time.LocalTime;

public class UIUtils {

    private static int getWeatherImageNight(String weather){
        if(weather.toLowerCase().replaceAll(" ","").contains("fair")){
            return R.drawable.night;
        }else if(weather.toLowerCase().replaceAll(" ","").contains("partlycloudy")){
            return R.drawable.cloudynight;
        }else{
            return getWeatherImageDay(weather);
        }
    }

    private static int getWeatherImageDay(String weather){
        if(weather.toLowerCase().replaceAll(" ","").contains("fair") || weather.toLowerCase().replaceAll(" ","").contains("windy")){
            return R.drawable.sunny;
        }else if(weather.toLowerCase().replaceAll(" ","").contains("partlycloudy")){
            return R.drawable.cloudysunny;
        }else if(weather.toLowerCase().replaceAll(" ","").contains("cloudy") || weather.toLowerCase().replaceAll(" ","").contains("hazy") || weather.toLowerCase().replaceAll(" ","").contains("mist")){
            return R.drawable.cloudy;
        }else if(weather.toLowerCase().replaceAll(" ","").contains("rain") || weather.toLowerCase().replaceAll(" ","").contains("passing") || weather.toLowerCase().replaceAll(" ","").contains("light") || weather.toLowerCase().replaceAll(" ","").contains("heavyshowers") || weather.toLowerCase().replaceAll(" ","").equals("showers")){
            return R.drawable.raining;
        }else {
            return R.drawable.thunderstorm;
        }
    }

    public static int getWeatherImage(String weather, boolean nowCast){
        LocalTime time_now = LocalTime.now();
        if (nowCast && (time_now.isAfter(LocalTime.of(20, 0)) || time_now.isBefore(LocalTime.of(6, 0))) ) {
            return getWeatherImageNight(weather);
        } else {
            return getWeatherImageDay(weather);
        }
    }
}
