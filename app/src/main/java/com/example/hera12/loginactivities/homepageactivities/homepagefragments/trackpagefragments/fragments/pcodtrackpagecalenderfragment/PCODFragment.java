package com.example.hera12.loginactivities.homepageactivities.homepagefragments.trackpagefragments.fragments.pcodtrackpagecalenderfragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hera12.R;
import com.example.hera12.loginactivities.homepageactivities.homepagefragments.trackpagefragments.fragments.PeriodsFragment;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class PCODFragment extends Fragment {

    PCODFragmentAdapter pcodFragmentAdapter;
    View view;
    ViewPager2 calenderTimeLineViewPager2;
    TabLayout calenderTimeLineTabLayout;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_p_c_o_d, container, false);

        pcodFragmentAdapter = new PCODFragmentAdapter(getChildFragmentManager(), getLifecycle());
        pcodFragmentAdapter.setCalenderTimeLineFragmentList(new PCODCalenderFragment(), "Calender");
        pcodFragmentAdapter.setCalenderTimeLineFragmentList(new PCODTimeLineFragment(), "TimeLine");

        calenderTimeLineViewPager2 = view.findViewById(R.id.trackPagePCODPageViewPager2);
        calenderTimeLineViewPager2.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
        calenderTimeLineViewPager2.setAdapter(pcodFragmentAdapter);

        calenderTimeLineTabLayout = view.findViewById(R.id.trackPagePCODPageCalenderTimelineTabLayout);

        new TabLayoutMediator(calenderTimeLineTabLayout, calenderTimeLineViewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                tab.setText(pcodFragmentAdapter.getCalenderTimeLineFragmentName(position));
            }
        }).attach();


        return view;
    }
}