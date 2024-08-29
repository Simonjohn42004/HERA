package com.example.hera12.loginactivities.homepageactivities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.hera12.R;
import com.example.hera12.loginactivities.homepageactivities.homepagefragments.connectpagefragments.ConnectMainFragment;
import com.example.hera12.loginactivities.homepageactivities.homepagefragments.insightspagefragments.InsightsMainFragment;
import com.example.hera12.loginactivities.homepageactivities.homepagefragments.settingspagefragments.SettingsMainFragment;
import com.example.hera12.loginactivities.homepageactivities.homepagefragments.trackpagefragments.TrackMainFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainHomePage extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main_home_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        bottomNavigationView = findViewById(R.id.homePageBottomViewNavigation);


        //click listener for each item in navigation bar
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemID = item.getItemId();
                if(itemID == R.id.trackItem){
                    setNewHomePageFragment(new TrackMainFragment());
                }
                else if(itemID == R.id.insightsItem){
                    setNewHomePageFragment(new InsightsMainFragment());
                }
                else if(itemID == R.id.connectItem){
                    setNewHomePageFragment(new ConnectMainFragment());
                }
                else if(itemID == R.id.settingsItem){
                    setNewHomePageFragment(new SettingsMainFragment());
                }
                return true;
            }
        });

        // set the initial fragment
        setNewHomePageFragment(new TrackMainFragment());

    }
    // method for replacing each fragment
    public void setNewHomePageFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.homePageFrameContainer, fragment).commit();
    }
}