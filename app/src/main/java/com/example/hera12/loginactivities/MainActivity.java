package com.example.hera12.loginactivities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.hera12.R;
import com.example.hera12.loginactivities.surveyactivities.SurveyStartPage;

public class MainActivity extends AppCompatActivity {

    Button loginBtn;
    TextView forgotPasswordBtn;
    TextView createNewAccountBtn;

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


        // click listener for login button
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Moving to survey page", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(MainActivity.this, SurveyStartPage.class);
                startActivity(i);
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
                Toast.makeText(MainActivity.this, "Moving to new account creation page", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(MainActivity.this, CreateNewAccountPage.class);
                startActivity(i);
            }
        });
    }
}