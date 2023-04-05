package com.example.a1dproject.utils;

import com.google.android.gms.location.Priority;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.location.Location;
import android.util.Log;
import android.util.TypedValue;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;

import com.example.a1dproject.model.CoordinatesApp;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.concurrent.CompletableFuture;

public class MiscUtils {
    public static float dpToPx(Resources resources, float dp) {
        return TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dp,
                resources.getDisplayMetrics()
        );
    }

    public static void displayToast(Context context, String s) {
        Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
    }

    public static CompletableFuture<CoordinatesApp> getCurrentLocation(Context context) {

        FusedLocationProviderClient fusedLocationClient = LocationServices.getFusedLocationProviderClient(context);
        CompletableFuture<CoordinatesApp> coordinates = new CompletableFuture<>();
        Log.d("Weather Component", "Running");
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            fusedLocationClient.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, null).addOnSuccessListener(
                new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        Log.d("Weather Component", "Latitude: " + String.valueOf(location.getLatitude()) + " Longitude: " + String.valueOf(location.getLongitude()));
                        if (location != null) {
                            coordinates.complete(new CoordinatesApp(location.getLatitude(), location.getLongitude()));
                        }else{
                            coordinates.complete(null);
                        }
                    }
                }
            );
        } else {
            Log.d("Weather Component", "No Permission");
            coordinates.complete(null);
        }
        return coordinates;
    }
}
