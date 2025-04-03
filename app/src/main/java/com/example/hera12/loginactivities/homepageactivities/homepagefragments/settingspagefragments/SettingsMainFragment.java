package com.example.hera12.loginactivities.homepageactivities.homepagefragments.settingspagefragments;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.hera12.R;
import com.example.hera12.loginactivities.MainActivity;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class SettingsMainFragment extends Fragment {
    View myFragment;
    BarChart barChart;
    Button signOutButton;
    private ImageView profileImageView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        myFragment = inflater.inflate(R.layout.fragment_settings_main, container, false);

        signOutButton = myFragment.findViewById(R.id.signOutButton);

        profileImageView = myFragment.findViewById(R.id.profileImageView);

        // Set a click listener for the profile image
        profileImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showProfilePhotoDialog();
            }
        });



        // setting data's on the barchart
        barChart = myFragment.findViewById(R.id.progressChart);
        barChart.setDrawBarShadow(false);
        barChart.getDescription().setEnabled(false);
        barChart.setPinchZoom(false);
        barChart.setDrawGridBackground(true);
        userDietChart();


        signOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth userAuth = FirebaseAuth.getInstance();
                userAuth.signOut();
                Toast.makeText(getContext(), "Successfully Signed out", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(getActivity(), MainActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
                requireActivity().finish();

            }
        });




        return myFragment;
    }

    private void userDietChart() {
        // empty labels so that the names are spread evenly
        String[] labels = {"", "Day X", "Day y", "Day Z", "Day A", "Day B", ""};
        XAxis xAxis = barChart.getXAxis();
        xAxis.setCenterAxisLabels(true);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(true);
        xAxis.setGranularity(1f); // only intervals of 1 day
        xAxis.setTextColor(Color.BLACK);
        xAxis.setTextSize(12);
        xAxis.setAxisLineColor(Color.WHITE);
        xAxis.setAxisMinimum(1f);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));

        YAxis leftAxis = barChart.getAxisLeft();
        leftAxis.setTextColor(Color.BLACK);
        leftAxis.setTextSize(12);
        leftAxis.setAxisLineColor(Color.WHITE);
        leftAxis.setDrawGridLines(true);
        leftAxis.setGranularity(2);
        leftAxis.setLabelCount(8, true);
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);

        barChart.getAxisRight().setEnabled(false);
        barChart.getLegend().setEnabled(false);

        float[] valOne = {0, 60, 60, 60, 60};
        float[] valTwo = {60, 50, 40, 30, 20};
        float[] valThree = {50, 60, 20, 10, 30};

        ArrayList<BarEntry> barOne = new ArrayList<>();
        ArrayList<BarEntry> barTwo = new ArrayList<>();
        ArrayList<BarEntry> barThree = new ArrayList<>();
        for (int i = 0; i < valOne.length; i++) {
            barOne.add(new BarEntry(i, valOne[i]));
            barTwo.add(new BarEntry(i, valTwo[i]));
            barThree.add(new BarEntry(i, valThree[i]));
        }

        BarDataSet set1 = new BarDataSet(barOne, "barOne");
        set1.setColor(Color.BLUE);
        BarDataSet set2 = new BarDataSet(barTwo, "barTwo");
        set2.setColor(Color.RED);
        BarDataSet set3 = new BarDataSet(barThree, "barTwo");
        set3.setColor(Color.YELLOW);

        set1.setHighlightEnabled(false);
        set2.setHighlightEnabled(false);
        set3.setHighlightEnabled(false);
        set1.setDrawValues(false);
        set2.setDrawValues(false);
        set3.setDrawValues(false);

        ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
        dataSets.add(set1);
        dataSets.add(set2);
        dataSets.add(set3);
        BarData data = new BarData(dataSets);
        float groupSpace = 0.4f;
        float barSpace = 0f;
        float barWidth = 0.3f;
        // (barSpace + barWidth) * 2 + groupSpace = 1
        data.setBarWidth(barWidth);
        // so that the entire chart is shown when scrolled from right to left
        xAxis.setAxisMaximum(labels.length - 1.1f);
        barChart.setData(data);
        barChart.setScaleEnabled(false);
        barChart.setVisibleXRangeMaximum(6f);
        barChart.groupBars(1f, groupSpace, barSpace);
        barChart.invalidate();
    }

    private void showProfilePhotoDialog() {
        // Create the dialog
        Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.dialog_profile_photo);

        // Set the profile image in the dialog (you can load an actual image if necessary)
        ImageView dialogProfileImageView = dialog.findViewById(R.id.dialogProfileImageView);
        dialogProfileImageView.setImageDrawable(profileImageView.getDrawable());

        // Set Edit Profile button click listener
        Button btnEditProfile = dialog.findViewById(R.id.btnEditProfile);
        btnEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle edit profile action
                dialog.dismiss();
            }
        });

        // Set Delete Profile Photo button click listener
        Button btnDeleteProfilePhoto = dialog.findViewById(R.id.btnDeleteProfilePhoto);
        btnDeleteProfilePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle delete profile photo action
                dialog.dismiss();
            }
        });

        // Show the dialog
        dialog.show();
    }
}