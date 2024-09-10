package com.example.hera12.loginactivities.surveyactivities.surveyquestions;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.hera12.R;
import com.example.hera12.loginactivities.database.MapSurveyDataBase;

public class Question17 extends Fragment {



    EditText answerText;
    Button submitBtn;
    View myFragment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        myFragment = inflater.inflate(R.layout.edit_text_survey_question_int_values, container, false);


        answerText = myFragment.findViewById(R.id.surveyAnswerEditText);
        submitBtn = myFragment.findViewById(R.id.submitAnswerButton);

        if(MapSurveyDataBase.patientData.get("Average left Follicle size") != null){
            answerText.setText(String.valueOf(MapSurveyDataBase.patientData.get("Average left Follicle size")));
        }

        submitBtn.setOnClickListener(view -> {
            String answer = answerText.getText().toString();

            if(answer.isEmpty()){
                Toast.makeText(getContext(), "Please enter your answer", Toast.LENGTH_SHORT).show();
            }
            else {
                MapSurveyDataBase.patientData.put("Average left Follicle size", answer);
                answerText.setTextColor(getResources().getColor(R.color.Green, null));
                Toast.makeText(getContext(), "Your answer has been saved", Toast.LENGTH_SHORT).show();
            }
        });

        if(MapSurveyDataBase.patientData.get("Average left Follicle size") != null){
            answerText.setText(String.valueOf(MapSurveyDataBase.patientData.get("Average left Follicle size")));
            answerText.setTextColor(getResources().getColor(R.color.Green, null));
        }

        return myFragment;
    }
}