package com.example.a1dproject.controller;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;

import android.Manifest;
import com.example.a1dproject.R;
import com.example.a1dproject.utils.AuthUtils;
import com.example.a1dproject.utils.MiscUtils;
import com.example.a1dproject.view.RecommendedCards;
import com.example.a1dproject.view.WeatherCard;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.gms.location.Priority;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import com.example.a1dproject.databinding.ActivityHomeBinding;

public class HomeActivity extends AppCompatActivity {
    private ActivityHomeBinding binding;
    AuthUtils auth;
    private boolean isMenuOpen = false;

    private void showMenu(){
        binding.suggestAnAttractionFab.setVisibility(View.VISIBLE);
        binding.exhibitScannerFab.setVisibility(View.VISIBLE);
        binding.signOutFab.setVisibility(View.VISIBLE);
        float px = MiscUtils.dpToPx(this.getResources(), 56);
        isMenuOpen = true;
        binding.suggestAnAttractionFab.animate().translationY(-(px + 20));
        binding.exhibitScannerFab.animate().translationY(-(px * 2 + 40));
        binding.signOutFab.animate().translationY(-(px * 3 + 60));
    }

    private void closeMenu(){
        isMenuOpen = false;
        binding.suggestAnAttractionFab.animate().translationY(0);
        binding.exhibitScannerFab.animate().translationY(0);
        binding.signOutFab.animate().translationY(0).withEndAction(
            new Runnable() {
                @Override
                public void run() {
                    binding.suggestAnAttractionFab.setVisibility(View.INVISIBLE);
                    binding.exhibitScannerFab.setVisibility(View.INVISIBLE);
                    binding.signOutFab.setVisibility(View.INVISIBLE);
                }
            }
        );
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        );
        setContentView(binding.getRoot());

        auth = AuthUtils.getInstance();
        String name = auth.getUser().getDisplayName();
        binding.home.textViewHomeHello.setText("Good Morning \n" + name + " \uD83D\uDC4B");

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, getResources().getInteger(R.integer.LOCATION_PERMISSION_REQUEST_CODE));
        }else{
            WeatherCard weather = new WeatherCard(this);
            RecommendedCards recommendedCards = new RecommendedCards(this);
            binding.home.weatherInfo.addView(weather);
            binding.home.recommendationsAll.addView(recommendedCards);
        }

        binding.suggestAnAttractionFab.setVisibility(View.INVISIBLE);
        binding.exhibitScannerFab.setVisibility(View.INVISIBLE);
        binding.signOutFab.setVisibility(View.INVISIBLE);

        binding.exhibitScannerFabBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity( new Intent(HomeActivity.this, ExhibitScannerActivity.class) );
            }
        });
        binding.signOutFabBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                auth.signOut();
                startActivity( new Intent(HomeActivity.this, MainActivity.class) );
            }
        });
        binding.seeMoreFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isMenuOpen){
                    showMenu();
                }else{
                    closeMenu();
                }
            }
        });
    }
    @Override
    public void onBackPressed() {
        if(!isMenuOpen){
            super.onBackPressed();
        }else{
            closeMenu();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == getResources().getInteger(R.integer.LOCATION_PERMISSION_REQUEST_CODE)) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                WeatherCard weather = new WeatherCard(this);
                binding.home.weatherInfo.addView(weather);
            }
        }
    }


}