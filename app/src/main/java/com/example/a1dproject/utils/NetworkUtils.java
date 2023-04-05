package com.example.a1dproject.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.example.a1dproject.model.AttractionsAPIResponse;
import com.example.a1dproject.model.RecommendationsAPIResponse;
import com.example.a1dproject.model.WeatherAPIResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;

public class NetworkUtils {

    private static CompletableFuture<String> apiCall(String url_str) {
        final AuthUtils auth = AuthUtils.getInstance();
        CompletableFuture<String> response = new CompletableFuture<>();
        auth.getIDToken().thenAcceptAsync(idToken -> {
            if (idToken != null) {
                try {
                    URL url = new URL(url_str);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setRequestProperty("Authorization", "Bearer " + idToken);
                    int status_code = connection.getResponseCode();
                    if (status_code == HttpURLConnection.HTTP_OK) {
                        InputStream inputStream = connection.getInputStream();
                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

                        String line;
                        StringBuffer responseBuffer = new StringBuffer();
                        while ((line = bufferedReader.readLine()) != null) {
                            responseBuffer.append(line);
                        }
                        bufferedReader.close();
                        response.complete(responseBuffer.toString());
                    } else {
                        response.complete(null);
                    }
                } catch (IOException e) {
                    response.complete(null);
                }
            } else {
                response.complete(null);
            }
        }, Executors.newSingleThreadExecutor());
        return response;
    }


    public static CompletableFuture<WeatherAPIResponse> getWeatherData(Context context) {

        CompletableFuture<WeatherAPIResponse> weather_obj = new CompletableFuture<>();

        MiscUtils.getCurrentLocation(context).thenAccept(coordinatesApp -> {
            apiCall("https://digitaltrails.nigelpoh.me/api/weather?lat=" + String.valueOf(coordinatesApp.getLat()) + "&lng=" + String.valueOf(coordinatesApp.getLng())).thenAccept(res -> {
                if(res == null){
                    weather_obj.complete(null);
                }else{
                    ObjectMapper objectMapper = new ObjectMapper();
                    try {
                        WeatherAPIResponse response = objectMapper.readValue(res, WeatherAPIResponse.class);
                        weather_obj.complete(response);
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                        weather_obj.complete(null);
                    }
                }
            });
        });
        return weather_obj;
    }

    public static CompletableFuture<RecommendationsAPIResponse> getRecommendationsData(Context context) {

        CompletableFuture<RecommendationsAPIResponse> recommendations_obj = new CompletableFuture<>();

        MiscUtils.getCurrentLocation(context).thenAccept(coordinatesApp -> {
            apiCall("https://digitaltrails.nigelpoh.me/api/recommendation?lat=" + String.valueOf(coordinatesApp.getLat()) + "&lng=" + String.valueOf(coordinatesApp.getLng())).thenAccept(res -> {
                if (res == null) {
                    recommendations_obj.complete(null);
                } else {
                    ObjectMapper objectMapper = new ObjectMapper();
                    try {
                        RecommendationsAPIResponse response = objectMapper.readValue(res, RecommendationsAPIResponse.class);
                        recommendations_obj.complete(response);
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                        recommendations_obj.complete(null);
                    }
                }
            });
        });
        return recommendations_obj;
    }

    public static CompletableFuture<AttractionsAPIResponse> getAttractionInfo(Context context, String vid) {

        CompletableFuture<AttractionsAPIResponse> attraction_obj = new CompletableFuture<>();

        MiscUtils.getCurrentLocation(context).thenAccept(coordinatesApp -> {
            apiCall("https://digitaltrails.nigelpoh.me/api/info?vid=" + vid).thenAccept(res -> {
                if (res == null) {
                    attraction_obj.complete(null);
                } else {
                    ObjectMapper objectMapper = new ObjectMapper();
                    try {
                        AttractionsAPIResponse response = objectMapper.readValue(res, AttractionsAPIResponse.class);
                        attraction_obj.complete(response);
                    } catch (JsonProcessingException e) {
                        Log.e("ERRRRR", e.getMessage());
                        e.printStackTrace();
                        attraction_obj.complete(null);
                    }
                }
            });
        });
        return attraction_obj;
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager != null ? connectivityManager.getActiveNetworkInfo() : null;
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
