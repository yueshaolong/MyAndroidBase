package com.ysl.myandroidbase.myview.bottomnavigation;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.ysl.myandroidbase.R;

public class BottomNavigationActivity extends AppCompatActivity {

    protected Fragment currentFragment;
    private HomeFragment homeFragment;
    private DashboardFragment dashboardFragment;
    private NotificationFragment notificationFragment;
    private PersonalFragment personalFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottomnavigation);

        BottomNavigationView bottom_navigation = findViewById(R.id.bottom_navigation);
        bottom_navigation.setOnNavigationItemSelectedListener(navigationItemSelectedListener);
        initData();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.navigation_home:
                    clickHomeItem();
                    break;
                case R.id.navigation_dashboard:
                    clickDashboardItem();
                    break;
                case R.id.navigation_notifications:
                    clickNotificationItem();
                    break;
                case R.id.navigation_person:
                    clickPersonalItem();
                    break;
            }
            return true;
        }
    };

    private void initData() {
        if (homeFragment == null) {
            homeFragment = HomeFragment.newInstance("","");
        }

        if (!homeFragment.isAdded()) {
            getSupportFragmentManager().beginTransaction().add(R.id.fl_content, homeFragment).commitAllowingStateLoss();
        } else {
            getSupportFragmentManager().beginTransaction().show(homeFragment).commitAllowingStateLoss();
        }

        currentFragment = homeFragment;
    }

    protected void addOrShowFragment(FragmentTransaction transaction, Fragment fragment) {
        if (currentFragment == fragment){
            return;
        }

        if (!fragment.isAdded()) { // 如果当前fragment未被添加，则添加到Fragment管理器中
            transaction.hide(currentFragment).add(R.id.fl_content, fragment).commitAllowingStateLoss();
        } else {
            transaction.hide(currentFragment).show(fragment).commitAllowingStateLoss();
        }

        currentFragment = fragment;
    }
    private void clickHomeItem() {
        if (homeFragment == null) {
            homeFragment = HomeFragment.newInstance("","");
        }
        addOrShowFragment(getSupportFragmentManager().beginTransaction(), homeFragment);
    }
    private void clickDashboardItem() {
        if (dashboardFragment == null) {
            dashboardFragment = DashboardFragment.newInstance("","");
        }
        addOrShowFragment(getSupportFragmentManager().beginTransaction(), dashboardFragment);
    }
    private void clickNotificationItem() {
        if (notificationFragment == null) {
            notificationFragment = NotificationFragment.newInstance("","");
        }
        addOrShowFragment(getSupportFragmentManager().beginTransaction(), notificationFragment);
    }
    private void clickPersonalItem() {
        if (personalFragment == null) {
            personalFragment = PersonalFragment.newInstance("","");
        }
        addOrShowFragment(getSupportFragmentManager().beginTransaction(), personalFragment);
    }
}
