package com.example.a1dproject;

import static android.content.ContentValues.TAG;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.google.firebase.auth.GoogleAuthProvider;

public class AuthActivity extends AppCompatActivity {

    AppCompatButton btSignIn;
    GoogleSignInClient googleSignInClient;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        
        firebaseAuth = FirebaseAuth.getInstance();
        // Assign variable
        btSignIn = findViewById(R.id.continueWithGoogle);

        // Initialize sign in options the client-id is copied form google-services.json file
        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("907302022409-7m5j57048cgmiqnpu3ls0o2rua7lcj90.apps.googleusercontent.com")
                .requestEmail()
                .build();

        // Initialize sign in client
        googleSignInClient = GoogleSignIn.getClient(AuthActivity.this, googleSignInOptions);

        ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        // Check condition
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            Intent data = result.getData();
                            if(data != null){
                                Task<GoogleSignInAccount> signInAccountTask = GoogleSignIn.getSignedInAccountFromIntent(data);
                                // check condition
                                if (signInAccountTask.isSuccessful()) {
                                    // When google sign in successful initialize string
                                    String s = "Google sign in successful";
                                    // Display Toast
                                    displayToast(s);
                                    // Initialize sign in account
                                    try {
                                        // Initialize sign in account
                                        GoogleSignInAccount googleSignInAccount = signInAccountTask.getResult(ApiException.class);
                                        // Check condition
                                        if (googleSignInAccount != null) {
                                            // When sign in account is not equal to null initialize auth credential
                                            AuthCredential authCredential = GoogleAuthProvider.getCredential(googleSignInAccount.getIdToken(), null);
                                            // Check credential
                                            firebaseAuth.signInWithCredential(authCredential).addOnCompleteListener(AuthActivity.this, new OnCompleteListener<AuthResult>() {
                                                @Override
                                                public void onComplete(@NonNull Task<AuthResult> task) {
                                                    // Check condition
                                                    if (task.isSuccessful()) {
                                                        // When task is successful redirect to profile activity display Toast
                                                        startActivity( new Intent(AuthActivity.this, HomeActivity.class) );
                                                        displayToast("Firebase authentication successful");
                                                    } else {
                                                        // When task is unsuccessful display Toast
                                                        displayToast("Authentication Failed :" + task.getException().getMessage());
                                                    }
                                                }
                                            });
                                        }
                                    } catch (ApiException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }
                    }
                }
        );

        btSignIn.setOnClickListener((View.OnClickListener) view -> {
            displayToast("You clicked depression");
            // Initialize sign in intent
            Intent intent = googleSignInClient.getSignInIntent();

            // Start activity for result
            someActivityResultLauncher.launch(intent);
        });
    }

    private void displayToast(String s) {
        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
    }
}
