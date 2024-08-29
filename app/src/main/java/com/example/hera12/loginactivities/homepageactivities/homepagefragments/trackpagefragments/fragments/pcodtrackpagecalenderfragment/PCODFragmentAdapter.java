package com.example.hera12.loginactivities.homepageactivities.homepagefragments.trackpagefragments.fragments.pcodtrackpagecalenderfragment;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;
import java.util.List;

public class PCODFragmentAdapter extends FragmentStateAdapter {

    List<Fragment> calenderTimeLineFragmentList = new ArrayList<>();
    List<String> calenderTimeLineFragmentNameList = new ArrayList<>();

    public PCODFragmentAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return calenderTimeLineFragmentList.get(position);
    }

    @Override
    public int getItemCount() {
        return calenderTimeLineFragmentList.size();
    }

    public String getCalenderTimeLineFragmentName(int position){
        return calenderTimeLineFragmentNameList.get(position);
    }

    public void setCalenderTimeLineFragmentList(Fragment fragment, String fragmentName){
        calenderTimeLineFragmentList.add(fragment);
        calenderTimeLineFragmentNameList.add(fragmentName);
    }
}
