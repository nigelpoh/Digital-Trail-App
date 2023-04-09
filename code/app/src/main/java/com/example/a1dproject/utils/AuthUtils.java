package com.example.a1dproject.utils;

import static androidx.core.content.ContextCompat.startActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.a1dproject.R;
import com.example.a1dproject.controller.AuthActivity;
import com.example.a1dproject.controller.HomeActivity;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicReference;

public class AuthUtils {
    private static AuthUtils instance;
    private static FirebaseAuth auth;

    private AuthUtils() {}

    public static AuthUtils getInstance() {
        if (instance == null) {
            instance = new AuthUtils();
            auth = FirebaseAuth.getInstance();
            Log.d("Authentication", "Got Instance!");
        }
        return instance;
    }

    public FirebaseUser getUser(){
        return auth.getCurrentUser();
    }

    public GoogleSignInClient getGoogleSignInClient(Context context) {
        // Initialize sign in options with the client-id from resources
        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(context.getResources().getString(R.string.web_client_id))
                .requestEmail()
                .build();

        // Initialize sign in client
        return GoogleSignIn.getClient(context, googleSignInOptions);
    }

    public CompletableFuture<Boolean> googleAuthentication(Intent data, Context context){
        Task<GoogleSignInAccount> signInAccountTask = GoogleSignIn.getSignedInAccountFromIntent(data);
        CompletableFuture<Boolean> signInStatus = new CompletableFuture<>();
        if (signInAccountTask.isSuccessful()) {
            try {
                GoogleSignInAccount googleSignInAccount = signInAccountTask.getResult(ApiException.class);
                if (googleSignInAccount != null) {
                    AuthCredential authCredential = GoogleAuthProvider.getCredential(googleSignInAccount.getIdToken(), null);
                    MiscUtils.displayToast(context, "Signing you in...");
                    auth.signInWithCredential(authCredential).addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            signInStatus.complete(true);
                        }else{
                            signInStatus.complete(false);
                        }
                    });
                }else{
                    signInStatus.complete(false);
                }
            } catch (ApiException e) {
                signInStatus.complete(false);
                e.printStackTrace();
            }
        }else{
            signInStatus.complete(false);
        }
        return signInStatus;
    }

    public CompletableFuture<String> getIDToken(){
        CompletableFuture<String> tokenStore = new CompletableFuture<>();

        FirebaseUser user = this.getUser();
        if(user == null){
            return null;
        }
        user.getIdToken(true).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                tokenStore.complete(task.getResult().getToken());
            } else {
                tokenStore.complete(null);
                Log.d("Authentication", "Error getting token: " + task.getException().getMessage());
            }
        });
        return tokenStore;
    }

    public void signOut(){
        auth.signOut();
    }
}
