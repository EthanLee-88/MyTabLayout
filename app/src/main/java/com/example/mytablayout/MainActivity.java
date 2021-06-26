package com.example.mytablayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.example.mytablayout.ui.ColorTrackTextView;
import com.example.mytablayout.ui.MyTabLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

import static androidx.fragment.app.FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT;

public class MainActivity extends AppCompatActivity {
    private static final String[] tab = new String[]{"首页" , "我的" , "通知"};
     private MyTabLayout mMyTabLayout;
    private ViewPager mainPager;
    private BottomNavigationView navView;
    private List<Fragment> fragments = new ArrayList<>();
    private List<ColorTrackTextView> colorText = new ArrayList<>();
    private String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mMyTabLayout = findViewById(R.id.my_tab);
        mainPager = findViewById(R.id.main_pager);
        navView = findViewById(R.id.nav_view);

        initRes();
        initPager();
    }
    private void initRes() {
        fragments.add(new HomeFragment());
        fragments.add(new DashboardFragment());
        fragments.add(new NotificationsFragment());
        mMyTabLayout.initRes(tab);
        colorText.addAll(mMyTabLayout.getTabChildren());
        Log.d(TAG , "colorText.get(0)=" + colorText.get(0).getText().toString());
    }


    private void initPager() {
        mainPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager(), BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
            @NonNull
            @Override
            public Fragment getItem(int position) {
                Log.d(TAG, "getItem=" + position);
                return fragments.get(position);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }
        });
        mainPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (position > colorText.size() - 1) return;
                Log.d(TAG , "positionOffset = " + positionOffset  + "---position=" + position);
                if (position + 1 < colorText.size()){
                    colorText.get(position).changeRightColor(1 - positionOffset);
                    colorText.get(position + 1).changeLeftColor(positionOffset);
                }
            }

            @Override
            public void onPageSelected(int position) {
                Log.d(TAG, "onPageSelected=" + position);
                switch (position) {
                    case 0:
                        navView.setSelectedItemId(R.id.navigation_home);
                        break;
                    case 1:
                        navView.setSelectedItemId(R.id.navigation_dashboard);
                        break;
                    case 2:
                        navView.setSelectedItemId(R.id.navigation_notifications);
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        navView.setOnNavigationItemSelectedListener((@NonNull MenuItem item) -> {
            Log.d(TAG, "item.getItemId()=" + item.getItemId() + "--R.id.navigation_home = " + R.id.navigation_home);
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mainPager.setCurrentItem(0, true);
                    break;
                case R.id.navigation_dashboard:
                    mainPager.setCurrentItem(1, true);
                    break;
                case R.id.navigation_notifications:
                    mainPager.setCurrentItem(2, true);
                    break;
                default:
                    break;
            }
            return true;
        });
    }
}