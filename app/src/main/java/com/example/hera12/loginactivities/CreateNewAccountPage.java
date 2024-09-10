package com.example.hera12.loginactivities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CreateNewAccountPage extends AppCompatActivity {

    Button createNewAccoundButton;
    EditText userName, userEmail, userPassword, userConfirmPassword;
    ImageView visibilityButton;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_create_new_account_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        createNewAccoundButton = findViewById(R.id.createNewAccountButton);
        userName = findViewById(R.id.newAccountUsernameText);
        userEmail = findViewById(R.id.newAccoundMailText);
        userPassword = findViewById(R.id.newAccountPasswordText);
        userConfirmPassword = findViewById(R.id.confirmNewAccountPasswordText);
        visibilityButton = findViewById(R.id.passwordVisibilityImageView);

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


        // click listener for confirming new account creation
        createNewAccoundButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = userName.getText().toString();
                String Email = userEmail.getText().toString();
                String password = userPassword.getText().toString();
                String confirmPassword = userConfirmPassword.getText().toString();

                // checking if all the fields are entered and are correct
                if(TextUtils.isEmpty(name)){
                    Toast.makeText(CreateNewAccountPage.this, "Please enter username", Toast.LENGTH_SHORT).show();
                    userName.setError("username not entered");
                    userName.requestFocus();
                } else if(TextUtils.isEmpty(Email)){
                    Toast.makeText(CreateNewAccountPage.this, "Please enter email", Toast.LENGTH_SHORT).show();
                    userEmail.setError("Email not entered");
                    userEmail.requestFocus();
                }else if(!Patterns.EMAIL_ADDRESS.matcher(Email).matches()){
                    Toast.makeText(CreateNewAccountPage.this, "Please enter a valid Email", Toast.LENGTH_SHORT).show();
                    userEmail.setError("Invalid email");
                    userEmail.requestFocus();
                }else if(TextUtils.isEmpty(password)){
                    Toast.makeText(CreateNewAccountPage.this, "Please enter password", Toast.LENGTH_SHORT).show();
                    userPassword.setError("Password not entered");
                    userPassword.requestFocus();
                }else if(TextUtils.isEmpty(confirmPassword)){
                    Toast.makeText(CreateNewAccountPage.this, "Please enter confirm password", Toast.LENGTH_SHORT).show();
                    userConfirmPassword.setError("Password not entered");
                    userConfirmPassword.requestFocus();
                }else if(!isValidPassword(password)){
                    Toast.makeText(CreateNewAccountPage.this, "Please re-enter password", Toast.LENGTH_SHORT).show();
                    userPassword.setError("Invalid Password");
                    userPassword.requestFocus();

                }else if(!TextUtils.equals(password, confirmPassword)){
                    Toast.makeText(CreateNewAccountPage.this, "Please re-enter confirm password", Toast.LENGTH_SHORT).show();
                    userConfirmPassword.setError("Passwords do not match");
                    userConfirmPassword.requestFocus();
                }
                else{

                    registerUser(name, Email, password);
                    Intent i = new Intent(CreateNewAccountPage.this, MainActivity.class);
                    startActivity(i);
                    finish();

                }


            }
        });
    }

    private void registerUser(String name, String email, String password) {

        FirebaseAuth userAuth = FirebaseAuth.getInstance();
        userAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    FirebaseUser user = userAuth.getCurrentUser();

                    if(user != null){

                        user.sendEmailVerification();


                        UserProfileChangeRequest userProfileChangeRequest = new UserProfileChangeRequest.Builder()
                                .setDisplayName(name)
                                .build();

                        user.updateProfile(userProfileChangeRequest);

                        EmailCredentials userCredentials = new EmailCredentials(name, email, password);

                        // adding datas into the cloud firestore
                        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
                        CollectionReference reference = firestore.collection("Registered Users");
                        DocumentReference userRef = firestore.collection("Registered Users").document(user.getUid());
                        userRef.set(userCredentials);


                    }

                }else{
                    try {
                        throw task.getException() ;
                    } catch (Exception e){
                        Toast.makeText(CreateNewAccountPage.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }
    public boolean isValidPassword(String password){
        String regex = "^(?=.*[0-9])"
                + "(?=.*[a-z])(?=.*[A-Z])"
                + "(?=.*[@#$%^&+=])"
                + "(?=\\S+$).{8,20}$";

        Pattern p = Pattern.compile(regex);

        if (password == null) {
            return false;
        }
        Matcher m = p.matcher(password);

        return m.matches();
    }
}

