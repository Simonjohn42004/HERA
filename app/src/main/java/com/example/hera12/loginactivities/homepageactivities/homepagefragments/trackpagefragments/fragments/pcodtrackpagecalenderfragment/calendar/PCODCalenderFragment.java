package com.example.hera12.loginactivities.homepageactivities.homepagefragments.trackpagefragments.fragments.pcodtrackpagecalenderfragment.calendar;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hera12.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class PCODCalenderFragment extends Fragment implements WeekAdapter.OnDateClickListener{


    private RecyclerView recyclerViewWeek;
    private WeekAdapter weekAdapter;
    private TextView tvMonthYear;
    private Calendar currentCalendar;
    View myFragment;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        myFragment = inflater.inflate(R.layout.fragment_p_c_o_d_calender, container, false);

        recyclerViewWeek = myFragment.findViewById(R.id.recyclerViewWeek);
        tvMonthYear = myFragment.findViewById(R.id.tvMonthYear);
        ImageButton btnPreviousWeek = myFragment.findViewById(R.id.btnPreviousWeek);
        ImageButton btnNextWeek = myFragment.findViewById(R.id.btnNextWeek);

        currentCalendar = Calendar.getInstance();

        // Set up RecyclerView
        recyclerViewWeek.setLayoutManager(new GridLayoutManager(getContext(), 7));  // 7 columns for 7 days

        updateWeekView(); // Initial update

        // Previous week button click listener
        btnPreviousWeek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentCalendar.add(Calendar.WEEK_OF_YEAR, -1);  // Move to previous week
                animateWeekChange(false);  // Animate to the left (previous week)
                updateWeekView();  // Update the displayed week after navigation
            }
        });

        // Next week button click listener
        btnNextWeek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentCalendar.add(Calendar.WEEK_OF_YEAR, 1);  // Move to next week
                animateWeekChange(true);  // Animate to the right (next week)
                updateWeekView();  // Update the displayed week after navigation
            }
        });

        return myFragment;
    }


    // Update the RecyclerView with the new week and the month/year display
    private void updateWeekView() {
        // Get the new week days
        List<Date> weekDays = DateUtils.getWeekDaysFromDate(currentCalendar.getTime());

        // If the adapter is null, initialize it; otherwise, update it with new data
        if (weekAdapter == null) {
            weekAdapter = new WeekAdapter(weekDays, this);  // Pass the listener to the adapter
            recyclerViewWeek.setAdapter(weekAdapter);
        } else {
            weekAdapter.updateData(weekDays); // Update the adapter with the new week data
        }

        // Update the month/year text with the new month and year
        String monthYear = DateUtils.getMonthYear(weekDays.get(0));  // Pass the first day of the week
        tvMonthYear.setText(monthYear);
    }

    // Method to animate week changes: forward (next week) or backward (previous week)
    private void animateWeekChange(boolean isNextWeek) {
        Animation animation = AnimationUtils.loadAnimation(getContext(), isNextWeek ? R.anim.slide_in_right : R.anim.slide_in_left);
        recyclerViewWeek.startAnimation(animation);
    }

    @Override
    public void onDateClick(Date date) {
        // Handle date click here, if needed
        // For example, show a Toast or update other UI elements
        Toast.makeText(getContext(), "Selected date: " + new SimpleDateFormat("yyyy-MM-dd").format(date), Toast.LENGTH_SHORT).show();
    }
}