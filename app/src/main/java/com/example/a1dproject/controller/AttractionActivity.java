package com.example.a1dproject.controller;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;

import android.Manifest;
import com.example.a1dproject.R;
import com.example.a1dproject.utils.AuthUtils;
import com.example.a1dproject.utils.MiscUtils;
import com.example.a1dproject.view.AttractionInfo;
import com.example.a1dproject.view.RecommendedCards;
import com.example.a1dproject.view.WeatherCard;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.gms.location.Priority;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;

import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.example.a1dproject.databinding.ActivityAttractionBinding;

public class AttractionActivity extends AppCompatActivity {
    private ActivityAttractionBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        binding = ActivityAttractionBinding.inflate(getLayoutInflater());
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        );
        setContentView(binding.getRoot());

        Intent intent = getIntent();
        String vid = intent.getStringExtra("vid");

        AttractionInfo attraction = new AttractionInfo(this);
        attraction.init(vid);

        NestedScrollView bottomSheet = findViewById(R.id.bottom_sheet);
        BottomSheetBehavior<NestedScrollView> bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);

        // Set the initial height of the bottom sheet
        int windowHeight = getResources().getDisplayMetrics().heightPixels;
        int desiredHeight = windowHeight / 2;
        bottomSheetBehavior.setPeekHeight(desiredHeight);

        // Set the expanded height of the bottom sheet
        bottomSheetBehavior.setExpandedOffset(0);

        binding.goBackAttractionsFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AttractionActivity.this, HomeActivity.class));
            }
        });
    }
}