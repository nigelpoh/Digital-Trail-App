package com.example.a1dproject.model;

public class WeatherAPIResponse {
    private Now current_conditions;
    private Forecast tomorrow_forecast;
    private Forecast two_days_forecast;
    private Forecast three_day_forecast;
    private Forecast four_day_forecast;
    private String location;
    private Boolean status = false;

    public Now getCurrentConditions() {
        return current_conditions;
    }

    public void setCurrentConditions(Now current_conditions) {
        this.current_conditions = current_conditions;
    }

    public Forecast getTomorrowForecast() {
        return tomorrow_forecast;
    }

    public void setTomorrowForecast(Forecast tomorrow_forecast) {
        this.tomorrow_forecast = tomorrow_forecast;
    }

    public Forecast getTwoDaysForecast() {
        return two_days_forecast;
    }

    public void setTwoDaysForecast(Forecast two_days_forecast) {
        this.two_days_forecast = two_days_forecast;
    }

    public Forecast getThreeDayForecast() {
        return three_day_forecast;
    }

    public void setThreeDayForecast(Forecast three_day_forecast) {
        this.three_day_forecast = three_day_forecast;
    }

    public Forecast getFourDayForecast() {
        return four_day_forecast;
    }

    public void setFourDayForecast(Forecast four_day_forecast) {
        this.four_day_forecast = four_day_forecast;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}


