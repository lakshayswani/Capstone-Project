package com.lakshayswani.virtuastock.ui;

import com.lakshayswani.virtuastock.fragments.*;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import com.lakshayswani.virtuastock.R;

public class dashboard extends AppCompatActivity {

    private FragmentManager fragmentManager;

    private FragmentTransaction fragmentTransaction;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    fragmentTransaction = fragmentManager.beginTransaction();
                    StocksFragment stocksFragment = new StocksFragment();
                    fragmentTransaction.add(R.id.content, stocksFragment);
                    fragmentTransaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                    return true;
                case R.id.navigation_portfolio:
                    fragmentTransaction = fragmentManager.beginTransaction();
                    PortfolioFragment portfolioFragment = new PortfolioFragment();
                    fragmentTransaction.add(R.id.content, portfolioFragment);
                    fragmentTransaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                    return true;
                case R.id.navigation_trade:
                    fragmentTransaction = fragmentManager.beginTransaction();
                    TradeFragment tradeFragment = new TradeFragment();
                    fragmentTransaction.add(R.id.content, tradeFragment);
                    fragmentTransaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                    return true;
                case R.id.navigation_account:
                    fragmentTransaction = fragmentManager.beginTransaction();
                    AccountFragment accountFragment = new AccountFragment();
                    fragmentTransaction.add(R.id.content, accountFragment);
                    fragmentTransaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

}
