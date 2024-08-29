package com.example.hera12.loginactivities.surveyactivities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.hera12.R;
import com.example.hera12.loginactivities.homepageactivities.MainHomePage;
import com.example.hera12.loginactivities.surveyactivities.surveyquestions.Question1;
import com.example.hera12.loginactivities.surveyactivities.surveyquestions.Question10;
import com.example.hera12.loginactivities.surveyactivities.surveyquestions.Question11;
import com.example.hera12.loginactivities.surveyactivities.surveyquestions.Question12;
import com.example.hera12.loginactivities.surveyactivities.surveyquestions.Question13;
import com.example.hera12.loginactivities.surveyactivities.surveyquestions.Question14;
import com.example.hera12.loginactivities.surveyactivities.surveyquestions.Question2;
import com.example.hera12.loginactivities.surveyactivities.surveyquestions.Question3;
import com.example.hera12.loginactivities.surveyactivities.surveyquestions.Question4;
import com.example.hera12.loginactivities.surveyactivities.surveyquestions.Question5;
import com.example.hera12.loginactivities.surveyactivities.surveyquestions.Question6;
import com.example.hera12.loginactivities.surveyactivities.surveyquestions.Question7;
import com.example.hera12.loginactivities.surveyactivities.surveyquestions.Question8;
import com.example.hera12.loginactivities.surveyactivities.surveyquestions.Question9;

import java.util.ArrayList;
import java.util.List;

public class SurveyMainPage extends AppCompatActivity {
    TextView nextPageBtn, previousPageBtn;
    List<Fragment> SurveyPageFragmentList = new ArrayList<>();
    private static int index = 1;
    @SuppressLint("MissingInflatedId")
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

        nextPageBtn = findViewById(R.id.nextQuestionTextButton);
        previousPageBtn = findViewById(R.id.previousQuestionTextButton);

        // adding all questions to a list
        SurveyPageFragmentList.add(new Question1());
        SurveyPageFragmentList.add(new Question2());
        SurveyPageFragmentList.add(new Question3());
        SurveyPageFragmentList.add(new Question4());
        SurveyPageFragmentList.add(new Question5());
        SurveyPageFragmentList.add(new Question6());
        SurveyPageFragmentList.add(new Question7());
        SurveyPageFragmentList.add(new Question8());
        SurveyPageFragmentList.add(new Question9());
        SurveyPageFragmentList.add(new Question10());
        SurveyPageFragmentList.add(new Question11());
        SurveyPageFragmentList.add(new Question12());
        SurveyPageFragmentList.add(new Question13());
        SurveyPageFragmentList.add(new Question14());


        // click listener for moving to the next question
        nextPageBtn.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                if(index<=13){
                    index++;
                }
                if(index<14) {
                    if(index == 13){
                        nextPageBtn.setText("Finish");
                    }
                    setNewSurveyFragment(index);


                }
                else {
                    Toast.makeText(SurveyMainPage.this, "Moving to Main home page", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(SurveyMainPage.this, MainHomePage.class);
                    startActivity(i);
                }
            }
        });

        //click listener for moving to previous question
        previousPageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(index>=1){
                    index--;
                }
                if(index>=0) {
                    setNewSurveyFragment(index);
                }
            }
        });

        // initial fragment is required
        setNewSurveyFragment(0);
        // resetting index to 0 for each time activity is created
        index = 0;

    }
    public void setNewSurveyFragment(int index){
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.frameLayout, SurveyPageFragmentList.get(index)).commit();
    }
}