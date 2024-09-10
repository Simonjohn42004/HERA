package com.example.hera12.loginactivities.surveyactivities.surveyquestions;

import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.hera12.R;
import com.example.hera12.loginactivities.database.MapSurveyDataBase;


public class Question4 extends Fragment {

    Button yesBtn,noBtn;
    View myFragment;
   
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        myFragment =  inflater.inflate(R.layout.true_or_false_survey_question, container, false);

        yesBtn = myFragment.findViewById(R.id.trueAnswerButton);
        noBtn = myFragment.findViewById(R.id.falseAnswerButton);



        yesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                MapSurveyDataBase.patientData.put("Pimples", "1");
                yesBtn.setBackgroundTintList(ContextCompat.getColorStateList(myFragment.getContext(), R.color.Green));
                noBtn.setBackgroundTintList(ContextCompat.getColorStateList(myFragment.getContext(), R.color.Red));

            }
        });

        noBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MapSurveyDataBase.patientData.put("Pimples", "0");
                noBtn.setBackgroundTintList(ContextCompat.getColorStateList(myFragment.getContext(), R.color.Green));
                yesBtn.setBackgroundTintList(ContextCompat.getColorStateList(myFragment.getContext(), R.color.Red));
            }
        });

        if(MapSurveyDataBase.patientData.get("Pimples") != null){
            if(1.0f == Float.parseFloat(String.valueOf(MapSurveyDataBase.patientData.get("Pimples")))){
                yesBtn.setBackgroundTintList(ContextCompat.getColorStateList(myFragment.getContext(), R.color.Green));
                noBtn.setBackgroundTintList(ContextCompat.getColorStateList(myFragment.getContext(), R.color.Red));
            }
            else {
                noBtn.setBackgroundTintList(ContextCompat.getColorStateList(myFragment.getContext(), R.color.Green));
                yesBtn.setBackgroundTintList(ContextCompat.getColorStateList(myFragment.getContext(), R.color.Red));
            }
        }




        return myFragment;
    }
}