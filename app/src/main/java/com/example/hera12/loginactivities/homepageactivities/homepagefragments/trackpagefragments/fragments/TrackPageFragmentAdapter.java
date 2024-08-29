package com.example.hera12.loginactivities.homepageactivities.homepagefragments.trackpagefragments.fragments;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;
import java.util.List;
// creating adapter for inflating tab within track page
public class TrackPageFragmentAdapter extends FragmentStateAdapter {

    List<Fragment> trackMainPageFragmentList = new ArrayList<>();
    List<String> trackMainPageTabNameList = new ArrayList<>();

    public TrackPageFragmentAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return trackMainPageFragmentList.get(position);
    }

    @Override
    public int getItemCount() {
        return trackMainPageFragmentList.size();
    }
    public String getFragmentName(int position){
        return trackMainPageTabNameList.get(position);
    }


    public void setTrackMainPageFragmentList(Fragment fragment, String fragmentName){
        trackMainPageFragmentList.add(fragment);
        trackMainPageTabNameList.add(fragmentName);
    }
}
