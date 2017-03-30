package com.lakshayswani.virtuastock.ui;

import com.lakshayswani.virtuastock.fragments.*;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
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
            fragmentTransaction = fragmentManager.beginTransaction();
            Fragment currentFragment = fragmentManager.findFragmentById(R.id.dashboardContent);
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    if (!(currentFragment instanceof StocksFragment)) {
                        StocksFragment stocksFragment = StocksFragment.newInstance(1);
                        addAnimation(fragmentTransaction, currentFragment, stocksFragment);
                        fragmentTransaction.replace(R.id.dashboardContent, stocksFragment);
                        fragmentTransaction.commit();
                    }
                    return true;
                case R.id.navigation_portfolio:
                    if (!(currentFragment instanceof PortfolioFragment)) {
                        PortfolioFragment portfolioFragment = PortfolioFragment.newInstance(1);
                        addAnimation(fragmentTransaction, currentFragment, portfolioFragment);
                        fragmentTransaction.replace(R.id.dashboardContent, portfolioFragment);
                        fragmentTransaction.commit();
                    }
                    return true;
                case R.id.navigation_trade:
                    if (!(currentFragment instanceof TradeFragment)) {
                        TradeFragment tradeFragment = TradeFragment.newInstance(null, null);
                        addAnimation(fragmentTransaction, currentFragment, tradeFragment);
                        fragmentTransaction.replace(R.id.dashboardContent, tradeFragment);
                        fragmentTransaction.commit();
                    }
                    return true;
                case R.id.navigation_account:
                    if (!(currentFragment instanceof AccountFragment)) {
                        AccountFragment accountFragment = AccountFragment.newInstance(null, null);
                        addAnimation(fragmentTransaction, currentFragment, accountFragment);
                        fragmentTransaction.replace(R.id.dashboardContent, accountFragment);
                        fragmentTransaction.commit();
                    }
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

    private void addAnimation(FragmentTransaction fragmentTransaction, Fragment f1, Fragment f2) {
        int start = 0;
        int end = 0;
        if (f1 instanceof StocksFragment) {
            start = 1;
        } else if (f1 instanceof PortfolioFragment) {
            start = 2;
        } else if (f1 instanceof TradeFragment) {
            start = 3;
        } else if (f1 instanceof AccountFragment) {
            start = 4;
        }
        if (f2 instanceof StocksFragment) {
            end = 1;
        } else if (f2 instanceof PortfolioFragment) {
            end = 2;
        } else if (f2 instanceof TradeFragment) {
            end = 3;
        } else if (f2 instanceof AccountFragment) {
            end = 4;
        }
        if(start<end)
        {
            fragmentTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
        }
        else if(start>end)
        {
            fragmentTransaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right);
        }
        else
        {
            fragmentTransaction.setCustomAnimations(android.R.anim.bounce_interpolator, android.R.anim.bounce_interpolator);
        }
    }

}
