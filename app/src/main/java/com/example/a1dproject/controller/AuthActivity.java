package com.example.a1dproject.controller;

import static androidx.core.content.ContextCompat.startActivity;

import com.example.a1dproject.databinding.ActivityAuthBinding;
import com.example.a1dproject.databinding.ActivityHomeBinding;
import com.example.a1dproject.utils.MiscUtils;

import com.example.a1dproject.utils.AuthUtils;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.a1dproject.R;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;

public class AuthActivity extends AppCompatActivity {

    ActivityAuthBinding binding;
    AppCompatButton btSignIn;
    AuthUtils auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        binding = ActivityAuthBinding.inflate(getLayoutInflater());
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        );
        setContentView(binding.getRoot());

        auth = AuthUtils.getInstance();
        // Assign variable
        btSignIn = findViewById(R.id.continueWithGoogle);

        // Initialize sign in client
        GoogleSignInClient googleSignInClient = auth.getGoogleSignInClient(this);

        ActivityResultLauncher<Intent> googleSignInLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        // Check condition
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            Intent data = result.getData();
                            if(data != null){
                                auth.googleAuthentication(data, getApplicationContext()).thenAccept(successful -> {
                                    if(successful == true){
                                        MiscUtils.displayToast(getApplicationContext(), "Successful login. Welcome!");
                                        startActivity( new Intent(AuthActivity.this, HomeActivity.class) );
                                    }else{
                                        MiscUtils.displayToast(getApplicationContext(), "Login was not successful. Please try again later.");
                                    }
                                });
                            }
                        }
                    }
                }
        );

        btSignIn.setOnClickListener((View.OnClickListener) view -> {
            Intent intent = googleSignInClient.getSignInIntent();
            googleSignInLauncher.launch(intent);
        });
    }
}
