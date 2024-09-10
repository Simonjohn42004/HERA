package com.example.hera12.loginactivities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.hera12.R;
import com.example.hera12.loginactivities.apputils.EmailVerificationAlertBox;
import com.example.hera12.loginactivities.apputils.emailAndPasswordValidity;
import com.example.hera12.loginactivities.homepageactivities.MainHomePage;
import com.example.hera12.loginactivities.surveyactivities.SurveyStartPage;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

@RequiresApi(api = Build.VERSION_CODES.O)

public class MainActivity extends AppCompatActivity {

    Button loginBtn;
    TextView forgotPasswordBtn;
    TextView createNewAccountBtn;
    FirebaseAuth userAuth;
    ImageView visibilityButton;
    EditText userEmail, userPassword;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main_login_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        // assigning each view to it UI
        loginBtn = findViewById(R.id.loginButton);
        forgotPasswordBtn = findViewById(R.id.forgotPasswordText);
        createNewAccountBtn = findViewById(R.id.createNewAccountText);
        userEmail = findViewById(R.id.emailText);
        userPassword = findViewById(R.id.loginPasswordText);
        visibilityButton = findViewById(R.id.viewPasswordImageView);

        visibilityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(userPassword.getTransformationMethod().equals(HideReturnsTransformationMethod.getInstance())){
                    userPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    visibilityButton.setImageResource(R.drawable.baseline_visibility_off_24);
                }
                else{
                    userPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    visibilityButton.setImageResource(R.drawable.baseline_visibility_24);
                }
            }
        });



        // click listener for login button
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               String email = userEmail.getText().toString();
               String password = userPassword.getText().toString();
               emailAndPasswordValidity emailAndPasswordValidity = new emailAndPasswordValidity();
               if(!emailAndPasswordValidity.isValidEmail(email)){
                   Toast.makeText(MainActivity.this, "Please Enter email", Toast.LENGTH_SHORT).show();
                   userEmail.setError("Enter a Valid Email");
                   userEmail.requestFocus();
               }
               else if(!emailAndPasswordValidity.isValidPassword(password)){
                   Toast.makeText(MainActivity.this, "Please enter Password", Toast.LENGTH_SHORT).show();
                   userPassword.setError("Enter a Valid Password");
                   userPassword.requestFocus();
               }

               else{
                    loginUser(email, password);

                }
            }
        });


        // click listener for forgot password text
        forgotPasswordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Moving to forgot password page", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(MainActivity.this, ForgotPasswordPage.class);
                startActivity(i);
            }
        });

        // click listener for creating new account page
        createNewAccountBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, CreateNewAccountPage.class);
                startActivity(i);
            }
        });
    }


    private void loginUser(String email, String password) {
        FirebaseAuth userAuth = FirebaseAuth.getInstance();

        userAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    FirebaseUser user = userAuth.getCurrentUser();
                    if (user != null) {
                        EmailVerificationAlertBox verificationAlertBox = new EmailVerificationAlertBox(MainActivity.this, userAuth);

                        // Check if email is verified
                        if (!user.isEmailVerified()) {
                            verificationAlertBox.EmailVerification(userAuth);
                        } else {
                            // Check if the user has completed the survey
                            FirebaseFirestore firestore = FirebaseFirestore.getInstance();
                            firestore.collection("Registered Users").document(user.getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                @Override
                                public void onSuccess(DocumentSnapshot documentSnapshot) {
                                    try {
                                        // Retrieve the 'hasTakenSurvey' field
                                        String hasTakenSurveyString = (String) documentSnapshot.get("hasTakenSurvey");

                                        if (hasTakenSurveyString != null) {
                                            float hasTakenSurvey = Float.parseFloat(hasTakenSurveyString);
                                            if (hasTakenSurvey == 1.0f) {
                                                // Survey completed, navigate to MainHomePage
                                                Intent i = new Intent(MainActivity.this, MainHomePage.class);
                                                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                                startActivity(i);
                                                finish();
                                            } else {
                                                // Survey not completed, navigate to SurveyStartPage
                                                Toast.makeText(MainActivity.this, "Welcome to the PCOD Tracker and Stabiliser App", Toast.LENGTH_SHORT).show();
                                                Intent i = new Intent(MainActivity.this, SurveyStartPage.class);
                                                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                                startActivity(i);
                                                finish();
                                            }
                                        } else {
                                            // 'hasTakenSurvey' field not found, redirect to SurveyStartPage
                                            Toast.makeText(MainActivity.this, "Please complete the survey", Toast.LENGTH_SHORT).show();
                                            Intent i = new Intent(MainActivity.this, SurveyStartPage.class);
                                            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                            startActivity(i);
                                            finish();
                                        }
                                    } catch (Exception e) {
                                        // Handle parsing errors
                                        Log.e("Survey Error", "Error parsing survey data", e);
                                        Toast.makeText(MainActivity.this, "Error loading survey data", Toast.LENGTH_SHORT).show();
                                        Intent i = new Intent(MainActivity.this, SurveyStartPage.class);
                                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(i);
                                        finish();
                                    }
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    // Handle Firestore errors
                                    Log.e("Firestore Error", "Error fetching document", e);
                                    Toast.makeText(MainActivity.this, "Error loading user data", Toast.LENGTH_SHORT).show();
                                    Intent i = new Intent(MainActivity.this, MainActivity.class);
                                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(i);
                                    finish();
                                }
                            });
                        }
                    } else {
                        // Handle the case where the user is null
                        Toast.makeText(MainActivity.this, "Error: User not found", Toast.LENGTH_SHORT).show();
                        Log.e("Auth Error", "User is null");
                    }
                } else {
                    // If login fails, provide appropriate error messages
                    String errorMessage;
                    if (task.getException() != null) {
                        errorMessage = task.getException().getMessage();
                    } else {
                        errorMessage = "Login failed, please try again.";
                    }
                    Toast.makeText(MainActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                    Log.e("Login Error", "Login failed: " + errorMessage);
                }
            }
        });
    }


}