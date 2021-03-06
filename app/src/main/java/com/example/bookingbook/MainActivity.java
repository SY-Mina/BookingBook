package com.example.bookingbook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView botNav;
    HomeFragment homeFragment = new HomeFragment();
    SearchFragment searchFragment = new SearchFragment();
    RankingFragment rankingFragment = new RankingFragment();
    MyPageFragment myPageFragment = new MyPageFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.mainFragment, homeFragment).commitAllowingStateLoss();
        botNav = findViewById(R.id.botNavMain);

        botNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                switch (item.getItemId()) {
                    case R.id.nav_home:
                        transaction.replace(R.id.mainFragment, homeFragment).commitAllowingStateLoss();
                        break;
                    case R.id.nav_search:
                        transaction.replace(R.id.mainFragment, searchFragment).commitAllowingStateLoss();
                        break;
                    case R.id.nav_ranking:
                        transaction.replace(R.id.mainFragment, rankingFragment).commitAllowingStateLoss();
                        break;
                    case R.id.nav_my:
                        transaction.replace(R.id.mainFragment, myPageFragment).commitAllowingStateLoss();
                        break;
                }

                return true;
            }
        });
    }

    @Override
    protected void onDestroy() {                            // ??????????????? ???????????? ??????, finsh() ?????? ????????? ???????????? ??????
        super.onDestroy();
        SharedPreferences sharedPreferences = getSharedPreferences("login", MODE_PRIVATE);    // test ????????? ???????????? ??????, ?????? test key?????? ????????? ?????? ?????? ?????????.
        String name = sharedPreferences.getString("login","noname");
        if(!name.equals("noname"))
        {
            SharedPreferences pref = getSharedPreferences("login", MODE_PRIVATE);
            SharedPreferences.Editor editor = pref.edit();
            editor.clear();
            editor.commit();
            Toast.makeText(this, "???????????????????????????.", Toast.LENGTH_SHORT).show();
        }
    }
}