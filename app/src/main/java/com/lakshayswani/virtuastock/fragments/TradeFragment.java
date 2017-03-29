package com.lakshayswani.virtuastock.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import com.lakshayswani.virtuastock.R;
import com.lakshayswani.virtuastock.util.Stocks;
import com.rey.material.widget.Slider;
import com.rey.material.widget.Switch;
import com.rey.material.widget.TextView;

import org.w3c.dom.Text;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TradeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TradeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TradeFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private String[] stocks = null;

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
        TradeFragment fragment = new TradeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
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

        searchStock                 = (AutoCompleteTextView) view.findViewById(R.id.searchStock);
        stock_trade_price           = (Slider) view.findViewById(R.id.stock_trade_price);
        stock_trade_quantity        = (Slider) view.findViewById(R.id.stock_trade_quantity);
        stock_trade_price_number    = (EditText) view.findViewById(R.id.stock_trade_price_number);
        stock_trade_quantity_number = (EditText) view.findViewById(R.id.stock_trade_quantity_number);
        stock_trade                 = (Switch) view.findViewById(R.id.stock_trade);
        submitTrade                 = (Button) view.findViewById(R.id.submitTrade);
        stock_text_price            = (TextView) view.findViewById(R.id.stock_text_price);
        stock_text_quantity         = (TextView) view.findViewById(R.id.stock_text_quantity);
        stock_text_buy              = (TextView) view.findViewById(R.id.stock_text_buy);

        hideVisibility();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (getActivity(),android.R.layout.select_dialog_item, stocks);
        searchStock.setThreshold(2);
        searchStock.setAdapter(adapter);
        searchStock.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showVisibility();
            }
        });
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    private void hideVisibility()
    {
        stock_trade_price.setVisibility(View.GONE);
        stock_trade_quantity.setVisibility(View.GONE);
        stock_trade_price_number.setVisibility(View.GONE);
        stock_trade_quantity_number.setVisibility(View.GONE);
        stock_trade.setVisibility(View.GONE);
        submitTrade.setVisibility(View.GONE);
        stock_text_price.setVisibility(View.GONE);
        stock_text_quantity.setVisibility(View.GONE);
        stock_text_buy.setVisibility(View.GONE);
    }

    private void showVisibility()
    {
        stock_trade_price.setVisibility(View.VISIBLE);
        stock_trade_quantity.setVisibility(View.VISIBLE);
        stock_trade_price_number.setVisibility(View.VISIBLE);
        stock_trade_quantity_number.setVisibility(View.VISIBLE);
        stock_trade.setVisibility(View.VISIBLE);
        submitTrade.setVisibility(View.VISIBLE);
        stock_text_price.setVisibility(View.VISIBLE);
        stock_text_quantity.setVisibility(View.VISIBLE);
        stock_text_buy.setVisibility(View.VISIBLE);
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
}
