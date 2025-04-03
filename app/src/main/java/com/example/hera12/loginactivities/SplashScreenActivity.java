package com.example.hera12.loginactivities;



import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.hera12.R;
import com.example.hera12.loginactivities.database.PatientDatabase;
import com.example.hera12.loginactivities.end2endencryption.AES;
import com.example.hera12.loginactivities.homepageactivities.MainHomePage;
import com.example.hera12.loginactivities.surveyactivities.SurveyStartPage;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;


@SuppressLint("CustomSplashScreen")
@RequiresApi(api = Build.VERSION_CODES.O)
public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_splash_screen);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        new Handler().postDelayed(() -> {
            FirebaseAuth userAuth = FirebaseAuth.getInstance();
            FirebaseUser currentUser = userAuth.getCurrentUser();

            if (currentUser != null) {
                String userUid = currentUser.getUid();
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                DocumentReference userReference = db.collection("Registered Users").document(userUid);

                userReference.get().addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        DocumentSnapshot documentSnapshot = task.getResult();
                        if (documentSnapshot != null && documentSnapshot.exists()) {
                            // Retrieve encrypted data, key, and IV from Firestore
                            String encryptedData = documentSnapshot.getString("encryptedData");
                            String encryptionKey = documentSnapshot.getString("encryptionKey");
                            String iv = documentSnapshot.getString("iv");

                            try {
                                // Initialize the AES class and load the key and IV
                                AES aes = new AES();
                                aes.setKeyString(encryptionKey);
                                aes.setIVString(iv);
                                aes.initKeyFromString();  // Load the key and IV

                                // Decrypt the data
                                String decryptedData = aes.decrypt(encryptedData);
                                // Convert the decrypted JSON string back into a PatientDatabase object
                                Gson gson = new Gson();
                                PatientDatabase patient = gson.fromJson(decryptedData, PatientDatabase.class);

                                //Getting the string to check whether the patient has taken the survey or not
                                String hasTakenSurveyStr = patient.getHasTakenSurvey();

                                // Proceed with logic based on decrypted data
                                if (hasTakenSurveyStr != null) {
                                    try {
                                        float hasTakenSurvey = Float.parseFloat(hasTakenSurveyStr);

                                        Intent intent;
                                        if (hasTakenSurvey == 1.0f) {
                                            // User has completed the survey
                                            intent = new Intent(SplashScreenActivity.this, MainHomePage.class);
                                        } else {
                                            // User has not completed the survey
                                            intent = new Intent(SplashScreenActivity.this, SurveyStartPage.class);
                                        }

                                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                        finish();

                                    } catch (NumberFormatException e) {
                                        Log.e("Parse Error", "Error parsing 'hasTakenSurvey' field", e);
                                        Toast.makeText(SplashScreenActivity.this, "Error loading user data", Toast.LENGTH_SHORT).show();

                                        // Redirect to MainActivity in case of error
                                        Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                        finish();
                                    }

                                } else {
                                    // 'hasTakenSurvey' field not found, redirect to SurveyStartPage
                                    Intent intent = new Intent(SplashScreenActivity.this, SurveyStartPage.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                    finish();
                                }

                            } catch (Exception e) {
                                Log.e("Decryption Error", "Error decrypting data", e);

                                // Redirect to MainActivity in case of error
                                Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                finish();
                            }

                        } else {
                            // Document not found, redirect to SurveyStartPage
                            Intent intent = new Intent(SplashScreenActivity.this, SurveyStartPage.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            finish();
                        }

                    } else {
                        // Firestore task failed, handle the error
                        Log.e("Firestore Error", "Error fetching document", task.getException());

                        // Redirect to MainActivity in case of error
                        Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                    }
                });

            } else {
                // User not logged in, redirect to MainActivity
                Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        }, 2000); // Delay for 3 seconds
    }
}



