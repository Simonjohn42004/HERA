package com.example.hera12.loginactivities.homepageactivities.homepagefragments.trackpagefragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hera12.R;
import com.example.hera12.loginactivities.homepageactivities.homepagefragments.trackpagefragments.fragments.PeriodsFragment;
import com.example.hera12.loginactivities.homepageactivities.homepagefragments.trackpagefragments.fragments.TrackPageFragmentAdapter;
import com.example.hera12.loginactivities.homepageactivities.homepagefragments.trackpagefragments.fragments.pcodtrackpagecalenderfragment.PCODFragment;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;


public class TrackMainFragment extends Fragment {
    ViewPager2 trackMainFragmentViewPager2;
    TrackPageFragmentAdapter trackPageFragmentAdapter;
    View myFragment;
    TabLayout tabLayout;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        myFragment = inflater.inflate(R.layout.fragment_track_main, container, false);

        trackMainFragmentViewPager2 = myFragment.findViewById(R.id.trackPageMainViewPager2);
        trackPageFragmentAdapter = new TrackPageFragmentAdapter(getChildFragmentManager(), getLifecycle());
        tabLayout = myFragment.findViewById(R.id.trackFragmentTabLayout);

        trackPageFragmentAdapter.setTrackMainPageFragmentList(new PCODFragment(), "PCOD");
        trackPageFragmentAdapter.setTrackMainPageFragmentList(new PeriodsFragment(), "Period");

        trackMainFragmentViewPager2.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
        trackMainFragmentViewPager2.setAdapter(trackPageFragmentAdapter);

        new TabLayoutMediator(tabLayout, trackMainFragmentViewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                tab.setText(trackPageFragmentAdapter.getFragmentName(position));
            }
        }).attach();



        return myFragment;
    }
}