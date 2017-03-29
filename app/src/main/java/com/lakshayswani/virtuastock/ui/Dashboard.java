package com.lakshayswani.virtuastock.ui;

import com.lakshayswani.virtuastock.fragments.*;

import android.net.Uri;
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
import com.lakshayswani.virtuastock.fragments.dummy.DummyContent;

public class Dashboard extends AppCompatActivity implements PortfolioFragment.OnListFragmentInteractionListener, StocksFragment.OnListFragmentInteractionListener, AccountFragment.OnFragmentInteractionListener, TradeFragment.OnFragmentInteractionListener {

    @Override
    public void onListFragmentInteraction(DummyContent.DummyItem item) {

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    private FragmentManager fragmentManager;

    private FragmentTransaction fragmentTransaction;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    fragmentTransaction = fragmentManager.beginTransaction();
                    StocksFragment stocksFragment = StocksFragment.newInstance(1);
                    Toast.makeText(Dashboard.this, "Stocks Fragment", Toast.LENGTH_SHORT).show();
//                    fragmentTransaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                    fragmentTransaction.replace(R.id.dashboardContent, stocksFragment);
                    fragmentTransaction.commit();
                    return true;
                case R.id.navigation_portfolio:
                    fragmentTransaction = fragmentManager.beginTransaction();
                    PortfolioFragment portfolioFragment = PortfolioFragment.newInstance(1);
                    Toast.makeText(Dashboard.this, "Portfolio Fragment", Toast.LENGTH_SHORT).show();
//                    fragmentTransaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                    fragmentTransaction.replace(R.id.dashboardContent, portfolioFragment);
                    fragmentTransaction.commit();
                    return true;
                case R.id.navigation_trade:
                    fragmentTransaction = fragmentManager.beginTransaction();
                    TradeFragment tradeFragment = TradeFragment.newInstance(null, null);
                    Toast.makeText(Dashboard.this, "Trade Fragment", Toast.LENGTH_SHORT).show();
//                    fragmentTransaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                    fragmentTransaction.replace(R.id.dashboardContent, tradeFragment);
                    fragmentTransaction.commit();
                    return true;
                case R.id.navigation_account:
                    fragmentTransaction = fragmentManager.beginTransaction();
                    AccountFragment accountFragment = AccountFragment.newInstance(null,null);
                    Toast.makeText(Dashboard.this, "Account Fragment", Toast.LENGTH_SHORT).show();
//                    fragmentTransaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                    fragmentTransaction.replace(R.id.dashboardContent, accountFragment);
                    fragmentTransaction.commit();
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
        StocksFragment stocksFragment = StocksFragment.newInstance(1);
        fragmentTransaction.add(R.id.dashboardContent, stocksFragment);
        fragmentTransaction.commit();
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

}
