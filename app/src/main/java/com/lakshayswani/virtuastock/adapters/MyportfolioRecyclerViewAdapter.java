package com.lakshayswani.virtuastock.adapters;

import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lakshayswani.virtuastock.R;
import com.lakshayswani.virtuastock.fragments.PortfolioFragment.OnListFragmentInteractionListener;
import com.lakshayswani.virtuastock.fragments.dummy.DummyContent.DummyItem;
import com.lakshayswani.virtuastock.model.Stocks;
import com.lakshayswani.virtuastock.ui.Dashboard;

import java.util.List;

import worldline.com.foldablelayout.FoldableLayout;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyportfolioRecyclerViewAdapter extends RecyclerView.Adapter<MyportfolioRecyclerViewAdapter.ViewHolder> {

    private final List<Stocks> mValues;
    private final OnListFragmentInteractionListener mListener;

    public MyportfolioRecyclerViewAdapter(List<Stocks> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(new FoldableLayout(parent.getContext()));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);

        holder.cStockName.setText(holder.mItem.getStockName());
        holder.cStockPrice.setText(holder.mItem.getBidPrice());
        holder.dStockName.setText(holder.mItem.getStockName());
        holder.dStockQuantity.setText(holder.mItem.getQuantity());
        holder.dTradePrice.setText(holder.mItem.getBidPrice());
        holder.dTradeProfit.setText(holder.mItem.getBidPrice());
        holder.dDate.setText(holder.mItem.getDate());
        holder.dTradeType.setText(holder.mItem.getType());

        holder.mFoldableLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.mFoldableLayout.isFolded()) {
                    holder.mFoldableLayout.unfoldWithAnimation();
                } else {
                    holder.mFoldableLayout.foldWithAnimation();
                }
            }
        });

        holder.mFoldableLayout.setFoldListener(new FoldableLayout.FoldListener() {
            @Override
            public void onUnFoldStart() {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    holder.mFoldableLayout.setElevation(5);
                }
            }

            @Override
            public void onUnFoldEnd() {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    holder.mFoldableLayout.setElevation(0);
                }
            }

            @Override
            public void onFoldStart() {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    holder.mFoldableLayout.setElevation(5);
                }
            }

            @Override
            public void onFoldEnd() {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    holder.mFoldableLayout.setElevation(0);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        protected FoldableLayout mFoldableLayout;

        public final TextView cStockName;
        public final TextView cStockPrice;
        public final TextView dStockName;
        public final TextView dStockQuantity;
        public final TextView dTradePrice;
        public final TextView dTradeProfit;
        public final TextView dDate;
        public final TextView dTradeType;
        public Stocks mItem;

        public ViewHolder(FoldableLayout foldableLayout) {
            super(foldableLayout);
            foldableLayout.setupViews(R.layout.fragment_portfolio, R.layout.fragment_portfolio_detail, R.dimen.foldable_portfolio_card_height, itemView.getContext());
            cStockName = (TextView) foldableLayout.getCoverView().findViewById(R.id.portfolio_stock_name);
            cStockPrice = (TextView) foldableLayout.getCoverView().findViewById(R.id.portfolio_stock_profit);
            dStockName = (TextView) foldableLayout.getDetailView().findViewById(R.id.portfolio_detail_stock_name);
            dStockQuantity = (TextView) foldableLayout.getDetailView().findViewById(R.id.portfolio_detail_stock_quantity);
            dTradePrice = (TextView) foldableLayout.getDetailView().findViewById(R.id.portfolio_detail_trade_price);
            dTradeProfit = (TextView) foldableLayout.getDetailView().findViewById(R.id.portfolio_detail_trade_profit);
            dDate = (TextView) foldableLayout.getDetailView().findViewById(R.id.portfolio_detail_stock_date);
            dTradeType = (TextView) foldableLayout.getDetailView().findViewById(R.id.portfolio_detail_trade_type);
            cStockName.setTypeface(Dashboard.robotoLight);
            cStockPrice.setTypeface(Dashboard.robotoLight);
            dStockName.setTypeface(Dashboard.robotoLight);
            dStockQuantity.setTypeface(Dashboard.robotoLight);
            dTradePrice.setTypeface(Dashboard.robotoLight);
            dTradeProfit.setTypeface(Dashboard.robotoLight);
            dDate.setTypeface(Dashboard.robotoLight);
            dTradeType.setTypeface(Dashboard.robotoLight);
            mFoldableLayout = foldableLayout;
        }

    }
}
