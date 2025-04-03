package com.example.hera12.loginactivities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.hera12.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPasswordPage extends AppCompatActivity {
    FirebaseAuth userAuth;
    Button submitPasswordResetCode;
    EditText emailId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_forgot_password_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        submitPasswordResetCode = findViewById(R.id.submitCode);
        emailId = findViewById(R.id.forgotPasswordMailId);


        // click listener for submitting code for setting new password
        submitPasswordResetCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Email = emailId.getText().toString();
                if(TextUtils.isEmpty(Email) || !Patterns.EMAIL_ADDRESS.matcher(Email).matches()){
                    Toast.makeText(ForgotPasswordPage.this, "Please Enter Email", Toast.LENGTH_LONG).show();
                    emailId.setError("Enter Valid Email");
                }else {
                    resetPassword(Email);
                    Intent i = new Intent(ForgotPasswordPage.this, MainActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP| Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(i);
                    finish();
                }

            }
        });
    }

    private void resetPassword(String email) {
        FirebaseAuth userAuth = FirebaseAuth.getInstance();

        userAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(ForgotPasswordPage.this, "Please Check Email to reset password", Toast.LENGTH_LONG).show();
                    Intent i = new Intent(Intent.ACTION_MAIN);
                    i.addCategory(Intent.CATEGORY_APP_EMAIL);
                    startActivity(i);
                } else{
                    try {
                        throw task.getException();
                    } catch(Exception e){
                        Toast.makeText(ForgotPasswordPage.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

}