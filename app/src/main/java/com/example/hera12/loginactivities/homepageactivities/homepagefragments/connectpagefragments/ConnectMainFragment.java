package com.example.hera12.loginactivities.homepageactivities.homepagefragments.connectpagefragments;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import com.example.hera12.R;
import com.example.hera12.loginactivities.database.PatientDatabase;
import com.example.hera12.loginactivities.end2endencryption.AES;
import com.example.hera12.loginactivities.homepageactivities.homepagefragments.connectpagefragments.report.ReportDownloader;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;

import java.util.Arrays;
import java.util.Collections;

import ai.onnxruntime.OnnxTensor;
import ai.onnxruntime.OrtEnvironment;
import ai.onnxruntime.OrtSession;

@RequiresApi(api = Build.VERSION_CODES.Q)
public class ConnectMainFragment extends Fragment {

    private GridView healthGrid;
    private String[] metrics = {"Heart Rate", "Steps", "Calories Burned", "Hydration", "Sleep", "Body Temperature"};
    private int[] imageIcons = {R.drawable.heart_icon,  R.drawable.steps_icon, R.drawable.calories_icon, R.drawable.hydration_icon, R.drawable.sleep_icon, R.drawable.body_temperature_icon};
    private int[] metricValues = {75, 10000, 2000, 2700, 8, 37};
    private View myFragment;
    private Button reportButton;
    private PatientDatabase patient;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        myFragment = inflater.inflate(R.layout.fragment_connect_main, container, false);

        healthGrid = myFragment.findViewById(R.id.healthGrid);
        reportButton = myFragment.findViewById(R.id.downloadReportButton);


        GridAdapter adapter = new GridAdapter(getContext(), metrics, imageIcons, metricValues);
        healthGrid.setAdapter(adapter);
        retrievePatientData();

        reportButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                ReportDownloader reportDownloader = new ReportDownloader(myFragment.getContext(), patient); // pass patient data
                reportDownloader.downloadReport();
            }
        });





        return myFragment;
    }


    public void retrievePatientData(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            DocumentReference docRef = db.collection("Registered Users").document(user.getUid());

            docRef.get().addOnSuccessListener(documentSnapshot -> {
                if (documentSnapshot.exists()) {
                    // Retrieve the encrypted data, key, and IV from Firestore
                    String encryptedData = documentSnapshot.getString("encryptedData");
                    String encryptionKey = documentSnapshot.getString("encryptionKey");
                    String iv = documentSnapshot.getString("iv");


                    try {

                        // Initialize the AES class and load the key and IV
                        AES aes = new AES();
                        aes.setKeyString(encryptionKey);
                        aes.setIVString(iv);
                        aes.initKeyFromString(); // Load the key and IV from the strings

                        // Decrypt the data
                        String decryptedData = aes.decrypt(encryptedData);
                        // Convert the decrypted JSON string back into a PatientDatabase object
                        Gson gson = new Gson();
                        patient = gson.fromJson(decryptedData, PatientDatabase.class);

                        Log.d("PatientDatabase", patient.toString());

                    } catch (Exception e) {
                        Log.e("PCODFragment", "Error retrieving patient data", e);
                    }

                } else {
                    Log.e("PCODFragment", "Patient data not found.");
                }
            }).addOnFailureListener(e -> {
                Log.e("PCODFragment", "Error retrieving patient data", e);
            });
        }
    }


}
