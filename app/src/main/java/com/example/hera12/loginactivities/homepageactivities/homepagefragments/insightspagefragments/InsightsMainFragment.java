package com.example.hera12.loginactivities.homepageactivities.homepagefragments.insightspagefragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hera12.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.List;

public class InsightsMainFragment extends Fragment {

    private LineChart lineChart;
    private List<Entry> dataEntries;
    View myFragment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        myFragment =  inflater.inflate(R.layout.fragment_insights_main, container, false);

        // Initialize the LineChart
        lineChart = myFragment.findViewById(R.id.chartContainer);

        // Sample data (similar to Swift code)
        dataEntries = new ArrayList<>();
        dataEntries.add(new Entry(1f, 20f));
        dataEntries.add(new Entry(2f, 45f));
        dataEntries.add(new Entry(3f, 30f));
        dataEntries.add(new Entry(4f, 60f));
        dataEntries.add(new Entry(5f, 50f));

        // Load the data into the chart
        loadLineChart();
        return myFragment;
    }

    private void loadLineChart() {
        // Create a LineDataSet with the data entries
        LineDataSet lineDataSet = new LineDataSet(dataEntries, "Health Data");
        lineDataSet.setColor(getResources().getColor(android.R.color.holo_blue_dark));
        lineDataSet.setLineWidth(2f);
        lineDataSet.setCircleRadius(5f);
        lineDataSet.setCircleColor(getResources().getColor(android.R.color.holo_blue_light));
        lineDataSet.setDrawValues(false);

        // Configure the chart
        LineData lineData = new LineData(lineDataSet);
        lineChart.setData(lineData);
        lineChart.getDescription().setEnabled(false);

        // Customize X and Y axes
        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        YAxis leftAxis = lineChart.getAxisLeft();
        leftAxis.setDrawGridLines(true);

        lineChart.getAxisRight().setEnabled(false);
        lineChart.invalidate(); // Refresh the chart


    }
}