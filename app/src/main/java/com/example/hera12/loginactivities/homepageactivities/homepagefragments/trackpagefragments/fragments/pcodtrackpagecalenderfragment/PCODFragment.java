package com.example.hera12.loginactivities.homepageactivities.homepagefragments.trackpagefragments.fragments.pcodtrackpagecalenderfragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hera12.R;
import com.example.hera12.loginactivities.database.PatientDatabase;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collections;

import ai.onnxruntime.OnnxTensor;
import ai.onnxruntime.OrtEnvironment;
import ai.onnxruntime.OrtSession;

public class PCODFragment extends Fragment {

    PCODFragmentAdapter pcodFragmentAdapter;
    View view;
    ViewPager2 calenderTimeLineViewPager2;
    TabLayout calenderTimeLineTabLayout;
    Button foodActivityBtn, exerciseActivityBtn;
    TextView pcosResult;
    PatientDatabase patient;
    public String mainResult = "hello";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_p_c_o_d, container, false);
        foodActivityBtn = view.findViewById(R.id.foodActivityButton);
        pcodFragmentAdapter = new PCODFragmentAdapter(getChildFragmentManager(), getLifecycle());
        pcodFragmentAdapter.setCalenderTimeLineFragmentList(new PCODCalenderFragment(), "Calender");
        pcodFragmentAdapter.setCalenderTimeLineFragmentList(new PCODTimeLineFragment(), "TimeLine");
        pcosResult = view.findViewById(R.id.pcosModelresult);
        calenderTimeLineViewPager2 = view.findViewById(R.id.trackPagePCODPageViewPager2);
        calenderTimeLineViewPager2.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
        calenderTimeLineViewPager2.setAdapter(pcodFragmentAdapter);
        exerciseActivityBtn = view.findViewById(R.id.exerciseActivityButton);

        calenderTimeLineTabLayout = view.findViewById(R.id.trackPagePCODPageCalenderTimelineTabLayout);

        new TabLayoutMediator(calenderTimeLineTabLayout, calenderTimeLineViewPager2, (tab, position) -> {
            tab.setText(pcodFragmentAdapter.getCalenderTimeLineFragmentName(position));
        }).attach();

        foodActivityBtn.setOnClickListener(view -> startActivity(new Intent(view.getContext(), DietPlanActivity.class)));

        exerciseActivityBtn.setOnClickListener(view -> startActivity(new Intent(view.getContext(), ExcerciseActivity.class)));

        // Retrieve data directly from Firestore without a FirestoreUtil class
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            DocumentReference docRef = db.collection("Registered Users").document(user.getUid());

            docRef.get().addOnSuccessListener(documentSnapshot -> {
                if (documentSnapshot.exists()) {
                    patient = documentSnapshot.toObject(PatientDatabase.class);

                    try {
                        // Define the asset file name and destination path in internal storage
                        String assetFileName = "model.onnx";
                        String destinationPath = requireActivity().getApplicationContext().getFilesDir() + "/model.onnx";

                        // Copy the ONNX model file from assets to internal storage
                        copyFileFromAssets(view.getContext(), assetFileName, destinationPath);

                        // Load the ONNX environment and session
                        OrtEnvironment env = OrtEnvironment.getEnvironment();
                        OrtSession.SessionOptions options = new OrtSession.SessionOptions();
                        OrtSession session = env.createSession(destinationPath, options);

                        // Define input data from PatientDatabase object excluding 'hasTakenSurvey'
                        float[][] inputData = new float[][]{
                                {
                                        parseFloatStrict(patient.getIsPregnant()),
                                        parseFloatStrict(patient.getNumberOfAbortions()),
                                        parseFloatStrict(patient.getFSH()),
                                        parseFloatStrict(patient.getLH()),
                                        parseFloatStrict(patient.getFSH_LH()),
                                        parseFloatStrict(patient.getAMH()),
                                        parseFloatStrict(patient.getTSH()),
                                        parseFloatStrict(patient.getBeta1()),
                                        parseFloatStrict(patient.getBeta2()),
                                        parseFloatStrict(patient.getWaist()),
                                        parseFloatStrict(patient.getWaist_hip_ratio()),
                                        parseFloatStrict(patient.getHairGrowth()),
                                        parseFloatStrict(patient.getSkinDarkening()),
                                        parseFloatStrict(patient.getPimples()),
                                        parseFloatStrict(patient.getLeftFollicle()),
                                        parseFloatStrict(patient.getRightFollicle()),
                                        parseFloatStrict(patient.getAverageLeftFollicleSize()),
                                        parseFloatStrict(patient.getAverageRightFollicleSize())
                                }
                        };

                        // Create the input tensor and run the model
                        OnnxTensor inputTensor = OnnxTensor.createTensor(env, inputData);
                        OrtSession.Result result = session.run(Collections.singletonMap("float_input", inputTensor));

                        // Process the result of the model prediction
                        long[] outputData = (long[]) result.get(0).getValue();

                        if (outputData.length > 0) {
                            if (outputData[0] == 1) {
                                mainResult = "The patient has PCOD";
                            } else {
                                mainResult = "The patient does not have PCOD";
                            }
                        } else {
                            mainResult = "Unable to determine PCOD status";
                        }

                        // Set the result in the TextView
                        pcosResult.setText(mainResult);
                        Toast.makeText(view.getContext(), "The model ran successfully", Toast.LENGTH_LONG).show();

                    } catch (Exception e) {
                        mainResult = e.getMessage();
                        pcosResult.setText(mainResult);
                        e.printStackTrace();
                    }

                } else {
                    pcosResult.setText("Patient data not found.");
                    Log.e("PCODFragment", "Patient data not found.");
                }
            }).addOnFailureListener(e -> {
                Log.e("PCODFragment", "Error retrieving patient data", e);
                pcosResult.setText("Failed to retrieve patient data.");
            });
        }

        return view;
    }

    // Helper method to safely parse floats
    private static float parseFloatStrict(Object value) throws NumberFormatException {
        if (value == null) {
            throw new NumberFormatException("Value is null");
        }
        return Float.parseFloat(value.toString());
    }

    // Helper method to copy the ONNX model file from assets
    public static void copyFileFromAssets(Context context, String assetFileName, String destinationPath) throws IOException {
        InputStream inputStream = context.getAssets().open(assetFileName);
        OutputStream outputStream = new FileOutputStream(destinationPath);

        byte[] buffer = new byte[1024];
        int length;
        while ((length = inputStream.read(buffer)) > 0) {
            outputStream.write(buffer, 0, length);
        }

        outputStream.flush();
        outputStream.close();
        inputStream.close();
    }
}




// Nutritionix API
// Application ID
//1c4b0f1e
//
//API KEY
//6b236d26e577f8dfc25eb3866ea4d369
