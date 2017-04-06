package com.lakshayswani.virtuastock.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.lakshayswani.virtuastock.R;
import com.lakshayswani.virtuastock.ui.Dashboard;
import com.lakshayswani.virtuastock.util.StockPrice;
import com.lakshayswani.virtuastock.util.Stocks;
import com.rey.material.widget.Slider;
import com.rey.material.widget.Switch;

import org.json.JSONObject;
import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import worldline.com.foldablelayout.FoldableLayout;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TradeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TradeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TradeFragment extends Fragment {

    static TradeFragment fragment;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private String[] stocks = null;

    private StockPrice stockPrice;

    private AutoCompleteTextView searchStock;

    private Slider stock_trade_price;

    private Slider stock_trade_quantity;

    private EditText stock_trade_price_number;

    private EditText stock_trade_quantity_number;

    private Switch stock_trade;

    private Button submitTrade;

    private TextView stock_text_price;

    private TextView stock_text_quantity;

    private TextView stock_text_buy;

    private TextView stock_text_sell;

    private FoldableLayout foldableLayout;

    private TextView stock_name_cover;

    private TextView stock_price_cover;

    private WebView stock_detail;

    public TradeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TradeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TradeFragment newInstance(String param1, String param2) {
        if (fragment == null) {
            fragment = new TradeFragment();
            Bundle args = new Bundle();
            args.putString(ARG_PARAM1, param1);
            args.putString(ARG_PARAM2, param2);
            fragment.setArguments(args);
        }
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_trade, container, false);

        Stocks s = new Stocks();

        stocks = s.getStocks();
        stockPrice = new StockPrice();

        searchStock = (AutoCompleteTextView) view.findViewById(R.id.searchStock);
        stock_trade_price = (Slider) view.findViewById(R.id.stock_trade_price);
        stock_trade_quantity = (Slider) view.findViewById(R.id.stock_trade_quantity);
        stock_trade_price_number = (EditText) view.findViewById(R.id.stock_trade_price_number);
        stock_trade_quantity_number = (EditText) view.findViewById(R.id.stock_trade_quantity_number);
        stock_trade = (Switch) view.findViewById(R.id.stock_trade);
        submitTrade = (Button) view.findViewById(R.id.submitTrade);
        stock_text_price = (TextView) view.findViewById(R.id.stock_text_price);
        stock_text_quantity = (TextView) view.findViewById(R.id.stock_text_quantity);
        stock_text_buy = (TextView) view.findViewById(R.id.stock_text_buy);
        stock_text_sell = (TextView) view.findViewById(R.id.stock_text_sell);

        searchStock.setTypeface(Dashboard.robotoLight);
        stock_trade_price_number.setTypeface(Dashboard.robotoLight);
        stock_trade_quantity_number.setTypeface(Dashboard.robotoLight);
        submitTrade.setTypeface(Dashboard.robotoLight);
        stock_text_price.setTypeface(Dashboard.robotoLight);
        stock_text_quantity.setTypeface(Dashboard.robotoLight);
        stock_text_buy.setTypeface(Dashboard.robotoLight);
        stock_text_sell.setTypeface(Dashboard.robotoLight);

        foldableLayout = (FoldableLayout) view.findViewById(R.id.foldable_stock);
        foldableLayout.setupViews(R.layout.foldable_cover, R.layout.foldable_detail, R.dimen.foldable_card_height, getActivity().getApplicationContext());

        stock_name_cover = (TextView) foldableLayout.getCoverView().findViewById(R.id.stock_name_cover);
        stock_price_cover = (TextView) foldableLayout.getCoverView().findViewById(R.id.stock_price_cover);
        stock_detail = (WebView) foldableLayout.getDetailView().findViewById(R.id.stock_detail);
        stock_detail.setWebViewClient(new WebViewClient());

        stock_name_cover.setTypeface(Dashboard.robotoLight);
        stock_price_cover.setTypeface(Dashboard.robotoLight);

        setupFoldableLayout();

        if (savedInstanceState != null) {
            if (!savedInstanceState.getString("stockName").equalsIgnoreCase("")) {
                stock_name_cover.setText(savedInstanceState.getString("stockName"));
                stock_price_cover.setText(savedInstanceState.getString("stockPrice"));
                stock_detail.loadUrl("https://in.finance.yahoo.com/q?s=" + savedInstanceState.getString("stockName") + "&ql=1");
                stock_trade_price.setValueRange(savedInstanceState.getInt("bidPrice"), savedInstanceState.getInt("bidPrice") + 100, true);
                stock_trade_quantity.setValueRange(0, savedInstanceState.getInt("bidQuantity"), true);
            } else {
                hideVisibility();
            }
        } else {
            hideVisibility();
        }

        submitTrade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                com.lakshayswani.virtuastock.model.Stocks stock = new com.lakshayswani.virtuastock.model.Stocks();
                stock.setStockName(stock_name_cover.getText().toString());
                stock.setBidPrice("" + stock_trade_price.getValue());
                stock.setQuantity("" + stock_trade_quantity.getValue());
                stock.setType(stock_trade.isChecked() ? "sell" : "buy");
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                String currentDateandTime = sdf.format(new Date());
                stock.setDate(currentDateandTime);

                if (Integer.parseInt(stock.getQuantity()) > 0) {
                    if (stock.getType().equalsIgnoreCase("sell")) {
                        if (Dashboard.user.checkSell(stock.getStockName(), Integer.parseInt(stock.getQuantity()))) {
                            proceedTransaction(stock);
                        } else {
                            Toast.makeText(getActivity(), "OOPS! You don't seem to have these many stocks to sell.", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        proceedTransaction(stock);
                    }
                }
            }
        });

        stock_trade_quantity.setOnPositionChangeListener(new Slider.OnPositionChangeListener() {
            @Override
            public void onPositionChanged(Slider view, boolean fromUser, float oldPos, float newPos, int oldValue, int newValue) {
                final String newV = "" + newValue;
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        stock_trade_quantity_number.setText(newV);
                    }
                });
            }
        });
        stock_trade_price.setOnPositionChangeListener(new Slider.OnPositionChangeListener() {
            @Override
            public void onPositionChanged(Slider view, boolean fromUser, float oldPos, float newPos, int oldValue, int newValue) {
                final String newV = "" + newValue;
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        stock_trade_price_number.setText(newV);
                    }
                });
            }
        });
        stock_trade_price_number.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        stock_trade_price.setValue(Float.parseFloat(stock_trade_price_number.getText().toString()), true);
                    }
                });
            }
        });
        stock_trade_quantity_number.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        stock_trade_quantity.setValue(Float.parseFloat(stock_trade_quantity_number.getText().toString()), true);
                    }
                });
            }
        });

        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (getActivity(), android.R.layout.select_dialog_item, stocks);
        searchStock.setThreshold(2);
        searchStock.setAdapter(adapter);
        searchStock.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        final JSONObject price = stockPrice.getStockDetailsFromGoogle(searchStock.getText().toString());
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                String name = null;
                                String bidPrice = null;
                                try {
                                    bidPrice = price.getString("l_cur");
                                    name = price.getString("t");
                                } catch (Exception e) {
                                    Log.e("TradeFragment", e.getMessage());
                                }
                                if (name != null && bidPrice != null) {
                                    stock_name_cover.setText(name);
                                    stock_price_cover.setText(bidPrice);
                                    int price = Math.round(Float.parseFloat(bidPrice));
                                    int quantity = 10000 / price;
                                    showVisibility();
                                    stock_detail.loadUrl("https://in.finance.yahoo.com/q?s=" + stock_name_cover.getText() + "&ql=1");
                                    stock_trade_price.setValueRange((price) - 50, (price) + 50, true);
                                    stock_trade_quantity.setValueRange(0, quantity, true);
                                }
                            }
                        });
                    }
                }).start();
            }
        });
        return view;
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    private void setupFoldableLayout() {
        foldableLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (foldableLayout.isFolded()) {
                    foldableLayout.unfoldWithAnimation();
                } else {
                    foldableLayout.foldWithAnimation();
                }
            }
        });

        foldableLayout.setFoldListener(new FoldableLayout.FoldListener() {
            @Override
            public void onUnFoldStart() {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    foldableLayout.setElevation(5);
                }
            }

            @Override
            public void onUnFoldEnd() {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    foldableLayout.setElevation(0);
                }
            }

            @Override
            public void onFoldStart() {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    foldableLayout.setElevation(5);
                }
            }

            @Override
            public void onFoldEnd() {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    foldableLayout.setElevation(0);
                }
            }
        });
    }

    private void hideVisibility() {
        stock_trade_price.setVisibility(View.GONE);
        stock_trade_quantity.setVisibility(View.GONE);
        stock_trade_price_number.setVisibility(View.GONE);
        stock_trade_quantity_number.setVisibility(View.GONE);
        stock_trade.setVisibility(View.GONE);
        submitTrade.setVisibility(View.GONE);
        stock_text_price.setVisibility(View.GONE);
        stock_text_quantity.setVisibility(View.GONE);
        stock_text_buy.setVisibility(View.GONE);
        stock_text_sell.setVisibility(View.GONE);
        foldableLayout.setVisibility(View.GONE);
    }

    private void showVisibility() {
        stock_trade_price.setVisibility(View.VISIBLE);
        stock_trade_quantity.setVisibility(View.VISIBLE);
        stock_trade_price_number.setVisibility(View.VISIBLE);
        stock_trade_quantity_number.setVisibility(View.VISIBLE);
        stock_trade.setVisibility(View.VISIBLE);
        submitTrade.setVisibility(View.VISIBLE);
        stock_text_price.setVisibility(View.VISIBLE);
        stock_text_quantity.setVisibility(View.VISIBLE);
        stock_text_buy.setVisibility(View.VISIBLE);
        stock_text_sell.setVisibility(View.VISIBLE);
        foldableLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    private void proceedTransaction(com.lakshayswani.virtuastock.model.Stocks stock) {
        if (Dashboard.user.getStocks() == null) {
            ArrayList<com.lakshayswani.virtuastock.model.Stocks> stocks = new ArrayList<>();
            stocks.add(stock);
            Dashboard.user.setStocks(stocks);
        } else {
            Dashboard.user.getStocks().add(stock);
        }
        Dashboard.user.balanceUpdate(stock.getType(), Integer.parseInt(stock.getBidPrice()), Integer.parseInt(stock.getQuantity()));
        Dashboard.updateUser();
        hideVisibility();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        try {
            outState.putString("stockName", stock_name_cover.getText().toString());
            outState.putString("stockPrice", stock_price_cover.getText().toString());
            outState.putInt("bidPrice", stock_trade_price.getMinValue());
            outState.putInt("bidQuantity", stock_trade_quantity.getMaxValue());
        } catch (Exception e) {
            Log.d("Null Stock", e.getMessage());
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
    }
}
