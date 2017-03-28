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
import android.widget.Toast;

import com.lakshayswani.virtuastock.R;

public class Dashboard extends AppCompatActivity {

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
                    Toast.makeText(Dashboard.this, "Stocks Fragment", Toast.LENGTH_SHORT).show();
//                    fragmentTransaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                    fragmentTransaction.replace(R.id.dashboardContent, stocksFragment);
                    return true;
                case R.id.navigation_portfolio:
                    fragmentTransaction = fragmentManager.beginTransaction();
                    PortfolioFragment portfolioFragment = new PortfolioFragment();
                    Toast.makeText(Dashboard.this, "Portfolio Fragment", Toast.LENGTH_SHORT).show();
//                    fragmentTransaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                    fragmentTransaction.replace(R.id.dashboardContent, portfolioFragment);
                    return true;
                case R.id.navigation_trade:
                    fragmentTransaction = fragmentManager.beginTransaction();
                    TradeFragment tradeFragment = new TradeFragment();
                    Toast.makeText(Dashboard.this, "Trade Fragment", Toast.LENGTH_SHORT).show();
//                    fragmentTransaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                    fragmentTransaction.replace(R.id.dashboardContent, tradeFragment);
                    return true;
                case R.id.navigation_account:
                    fragmentTransaction = fragmentManager.beginTransaction();
                    AccountFragment accountFragment = AccountFragment.newInstance(null,null);
                    Toast.makeText(Dashboard.this, "Account Fragment", Toast.LENGTH_SHORT).show();
//                    fragmentTransaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                    fragmentTransaction.replace(R.id.dashboardContent, accountFragment);
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
        StocksFragment stocksFragment = new StocksFragment();
        fragmentTransaction.add(R.id.dashboardContent, stocksFragment);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

}
