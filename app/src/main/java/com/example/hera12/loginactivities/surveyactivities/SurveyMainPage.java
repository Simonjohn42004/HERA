package com.example.hera12.loginactivities.surveyactivities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.hera12.R;
import com.example.hera12.loginactivities.database.MapSurveyDataBase;
import com.example.hera12.loginactivities.database.PatientDatabase;
import com.example.hera12.loginactivities.end2endencryption.AES;
import com.example.hera12.loginactivities.homepageactivities.MainHomePage;
import com.example.hera12.loginactivities.surveyactivities.surveyquestions.Question1;
import com.example.hera12.loginactivities.surveyactivities.surveyquestions.Question10;
import com.example.hera12.loginactivities.surveyactivities.surveyquestions.Question11;
import com.example.hera12.loginactivities.surveyactivities.surveyquestions.Question12;
import com.example.hera12.loginactivities.surveyactivities.surveyquestions.Question13;
import com.example.hera12.loginactivities.surveyactivities.surveyquestions.Question14;
import com.example.hera12.loginactivities.surveyactivities.surveyquestions.Question15;
import com.example.hera12.loginactivities.surveyactivities.surveyquestions.Question16;
import com.example.hera12.loginactivities.surveyactivities.surveyquestions.Question17;
import com.example.hera12.loginactivities.surveyactivities.surveyquestions.Question18;
import com.example.hera12.loginactivities.surveyactivities.surveyquestions.Question2;
import com.example.hera12.loginactivities.surveyactivities.surveyquestions.Question3;
import com.example.hera12.loginactivities.surveyactivities.surveyquestions.Question4;
import com.example.hera12.loginactivities.surveyactivities.surveyquestions.Question5;
import com.example.hera12.loginactivities.surveyactivities.surveyquestions.Question6;
import com.example.hera12.loginactivities.surveyactivities.surveyquestions.Question7;
import com.example.hera12.loginactivities.surveyactivities.surveyquestions.Question8;
import com.example.hera12.loginactivities.surveyactivities.surveyquestions.Question9;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RequiresApi(api = Build.VERSION_CODES.O)
public class SurveyMainPage extends AppCompatActivity {

    Button nextBtn,prevBtn;
    TextView questionNumber;
    TextView question;
    List<Fragment> fragmentList = new ArrayList<>();
    int index = 0;
    List<String> questionsList = new ArrayList<>();

    @RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressLint({"MissingInflatedId", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_survey_main_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        fragmentList.add(new Question1());
        fragmentList.add(new Question2());
        fragmentList.add(new Question3());
        fragmentList.add(new Question4());
        fragmentList.add(new Question5());
        fragmentList.add(new Question6());
        fragmentList.add(new Question7());
        fragmentList.add(new Question8());
        fragmentList.add(new Question9());
        fragmentList.add(new Question10());
        fragmentList.add(new Question11());
        fragmentList.add(new Question12());
        fragmentList.add(new Question13());
        fragmentList.add(new Question14());
        fragmentList.add(new Question15());
        fragmentList.add(new Question16());
        fragmentList.add(new Question17());
        fragmentList.add(new Question18());




        questionsList.add("Are you currently Pregnant?"); //Question 1
        questionsList.add("Do you have hair growth?"); //Question 2
        questionsList.add("Has your skin become dark?"); //Question 3
        questionsList.add("Do you have pimples?"); //Question 4
        questionsList.add("FSH level"); //Question 5
        questionsList.add("LH level"); //Question 6
        questionsList.add("FSH and LH ratio"); //Question 7
        questionsList.add("AMH level"); //Question 8
        questionsList.add("TSH level"); //Question 9
        questionsList.add("1 BETA level"); //Question 10
        questionsList.add("2 BETA level"); //Question 11
        questionsList.add("Waist to Hip ratio"); //Question 12
        questionsList.add("Number of Abortions"); //Question 13
        questionsList.add("Waist length"); //Question 14
        questionsList.add("Number of left Follicle"); //Question 15
        questionsList.add("Number of right Follicle"); //Question 16
        questionsList.add("Average left Follicle size"); //Question 17
        questionsList.add("Average right Follicle size"); //Question 18






        nextBtn = findViewById(R.id.nextQuestionButton);
        prevBtn = findViewById(R.id.previousQuestionButton);
        questionNumber = findViewById(R.id.surveyQuestionNumberCount);
        question = findViewById(R.id.surveyQuestion);

        nextBtn.setOnClickListener(view -> {

            if (index < fragmentList.size() - 1) {
                index++;
                questionNumber.setText("Question " + (index + 1) + " of 18");
                question.setText(questionsList.get(index));
                setNewSurveyFragmentQuestion(fragmentList.get(index));
                if (index == fragmentList.size() - 1) {
                    nextBtn.setText("Finish");
                }
            } else {
                boolean allParametersFilled = true;
                for (Map.Entry<String, String> entry : MapSurveyDataBase.patientData.entrySet()) {
                    if (entry.getValue() == null || entry.getValue().isEmpty()) {
                        allParametersFilled = false;
                        break;
                    }
                }

                if (MapSurveyDataBase.patientData.size() == 18 && allParametersFilled) {
                    // Set "Has taken survey" as "1" (as a String)
                    MapSurveyDataBase.patientData.put("Has taken survey", "1");

                    // Convert Map to PatientDatabase object
                    PatientDatabase patientDatabase = getPatientDatabase();

                    // Store patient data directly in Firestore
                    storePatientData(patientDatabase);

                    // All parameters are filled, proceed to MainHomePage activity
                    nextPage();

                } else {
                    // At least one parameter is null, show an error message or prevent navigation
                    Toast.makeText(SurveyMainPage.this, "Please fill in all required fields.", Toast.LENGTH_SHORT).show();
                }
            }
        });



        prevBtn.setOnClickListener(view -> {
            if(index > 0) {
                index--;
                questionNumber.setText("Question "+ (index +1) + " of 18");
                question.setText(questionsList.get(index));
                setNewSurveyFragmentQuestion(fragmentList.get(index));
                nextBtn.setText("Next");
            }
        });








        setNewSurveyFragmentQuestion(new Question1());
        question.setText(questionsList.get(index));
        questionNumber.setText("Question "+ (index +1) + " of 18");
        index = 0;
    }

    private void nextPage() {
        Intent intent = new Intent(SurveyMainPage.this, MainHomePage.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }


    private static @NonNull PatientDatabase getPatientDatabase() {

        Map<String, String> patientData = MapSurveyDataBase.patientData;
        Log.d("MapData", "map data: " + patientData);

        // Adjust key names based on the actual keys in your map
        String isPregnant = patientData.getOrDefault("Is Pregnant", "0.0");
        String numberOfAbortions = patientData.getOrDefault("Number of Abortions", "0.0");
        String fsh = patientData.getOrDefault("FSH level", "0.0");
        String lh = patientData.getOrDefault("LH level", "0.0");
        String fshLhRatio = patientData.getOrDefault("FSH and LH ratio", "0.0");
        String amh = patientData.getOrDefault("AMH level", "0.0");
        String tsh = patientData.getOrDefault("TSH level", "0.0");
        String beta1 = patientData.getOrDefault("1 BETA level", "0.0");
        String beta2 = patientData.getOrDefault("2 BETA level", "0.0");
        String waistLength = patientData.getOrDefault("Waist length", "0.0");
        String waistHipRatio = patientData.getOrDefault("Waist to Hip ratio", "0.0");
        String hairGrowth = patientData.getOrDefault("Hair Growth", "0.0");
        String darkSkin = patientData.getOrDefault("Skin Darkening", "0.0");
        String pimples = patientData.getOrDefault("Pimples", "0.0");
        String leftFollicleCount = patientData.getOrDefault("Number of left Follicle", "0.0");
        String rightFollicleCount = patientData.getOrDefault("Number of right Follicle", "0.0");
        String leftFollicleSize = patientData.getOrDefault("Average left Follicle size", "0.0");
        String rightFollicleSize = patientData.getOrDefault("Average right Follicle size", "0.0");

        Log.d("PatientData", "waisthipratio: " + waistHipRatio);

        return new PatientDatabase(
                isPregnant,              // PREGNANT
                numberOfAbortions,       // NO OF ABORTIONS
                fsh,                     // FSH
                lh,                      // LH
                fshLhRatio,              // FSH/LH Ratio
                amh,                     // AMH
                tsh,                     // TSH
                beta1,                   // 1 BETA
                beta2,                   // 2 BETA
                waistLength,             // WAIST
                waistHipRatio,           // WAIST:HIP RATIO
                hairGrowth,              // HAIR GROWTH
                darkSkin,                // SKIN DARKENING
                pimples,                 // PIMPLES
                leftFollicleCount,       // FOLLICLE(L)
                rightFollicleCount,      // FOLLICLE(R)
                leftFollicleSize,        // AVG.F SIZE(L)
                rightFollicleSize,       // AVG.F SIZE(R)
                "1.0"                    // Some constant or dynamic value
        );
    }
//
//
//    private void storePatientData(PatientDatabase patientDatabase) {
//        String userUid = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
//        FirebaseFirestore db = FirebaseFirestore.getInstance();
//        DocumentReference userReference = db.collection("Registered Users").document(userUid);
//
//
//
//        // Store the data in Firestore
//        userReference.set(patientDatabase).addOnSuccessListener(aVoid -> {
//            // Handle success
//            Toast.makeText(SurveyMainPage.this, "Data successfully stored!", Toast.LENGTH_SHORT).show();
//        }).addOnFailureListener(e -> {
//            // Handle failure
//            Toast.makeText(SurveyMainPage.this, "Error storing data: " + e.getMessage(), Toast.LENGTH_LONG).show();
//        });
//    }



    // Function to store encrypted patient data in Firestore

    private void storePatientData(PatientDatabase patientDatabase) {
        String userUid = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference userReference = db.collection("Registered Users").document(userUid);

        // Initialize AES encryption class
        AES aes = new AES();
        aes.init(); // Initialize key

        try {
            // Serialize PatientDatabase to JSON string
            Gson gson = new Gson();
            String patientDataJson = gson.toJson(patientDatabase);

            // Encrypt the JSON string
            String encryptedPatientData = aes.encrypt(patientDataJson);

            // Export the encryption key and IV (you can store this separately or with the user data)
            aes.exportKey();
            String encryptionKey = aes.getKeyString();
            String ivString = aes.getIVString();

            // Create a map to store encrypted data and the encryption parameters
            Map<String, String> encryptedDataMap = new HashMap<>();
            encryptedDataMap.put("encryptedData", encryptedPatientData);
            encryptedDataMap.put("encryptionKey", encryptionKey);
            encryptedDataMap.put("iv", ivString);

            // Store the encrypted data in Firestore
            userReference.set(encryptedDataMap).addOnSuccessListener(aVoid -> {
                // Handle success
                Toast.makeText(SurveyMainPage.this, "Data successfully stored!", Toast.LENGTH_SHORT).show();
            }).addOnFailureListener(e -> {
                // Handle failure
                Toast.makeText(SurveyMainPage.this, "Error storing data: " + e.getMessage(), Toast.LENGTH_LONG).show();
            });

        } catch (Exception e) {
            Log.e("EncryptionError", "Error encrypting patient data", e);
            Toast.makeText(SurveyMainPage.this, "Error encrypting data", Toast.LENGTH_LONG).show();
        }
    }







    public void setNewSurveyFragmentQuestion(Fragment fragment){
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.surveyQuestionFrameContainer, fragment).commit();

    }

}