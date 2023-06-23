package com.health.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {
    
    BottomNavigationView bottomNavigation;
    Fragment selectedFragment = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    
        bottomNavigation = findViewById(R.id.bottomNavBar);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.containerFragment, new HomeFragment()).commit();

        bottomNavigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()) {
                    case R.id.homeBotNavBar:
                        selectedFragment = new HomeFragment();
                        break;
                    case R.id.bantuanBotNavBar:
                        selectedFragment = new HelpFragment();
                        break;
                }

                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.containerFragment, selectedFragment).commit();
                return true;
            }
        });
    }
}