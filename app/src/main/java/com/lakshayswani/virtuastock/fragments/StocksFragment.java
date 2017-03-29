package com.lakshayswani.virtuastock.fragments;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.gcm.GcmNetworkManager;
import com.google.android.gms.gcm.PeriodicTask;
import com.google.android.gms.gcm.Task;
import com.lakshayswani.virtuastock.R;
import com.lakshayswani.virtuastock.adapters.MystocksRecyclerViewAdapter;
import com.lakshayswani.virtuastock.data.QuoteColumns;
import com.lakshayswani.virtuastock.data.QuoteProvider;
import com.lakshayswani.virtuastock.fragments.dummy.DummyContent;
import com.lakshayswani.virtuastock.fragments.dummy.DummyContent.DummyItem;
import com.lakshayswani.virtuastock.rest.QuoteCursorAdapter;
import com.lakshayswani.virtuastock.rest.RecyclerViewItemClickListener;
import com.lakshayswani.virtuastock.service.StockIntentService;
import com.lakshayswani.virtuastock.service.StockTaskService;
import com.lakshayswani.virtuastock.ui.Dashboard;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class StocksFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 2;
    private OnListFragmentInteractionListener mListener;

    private Intent mServiceIntent;

    private boolean isConnected;

    private static final int CURSOR_LOADER_ID = 0;

    private QuoteCursorAdapter mCursorAdapter;

    private Cursor mCursor;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public StocksFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static StocksFragment newInstance(int columnCount) {
        StocksFragment fragment = new StocksFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_stocks_list2, container, false);

        ConnectivityManager cm =
                (ConnectivityManager) getActivity().getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        mServiceIntent = new Intent(getActivity(), StockIntentService.class);
        if (savedInstanceState == null) {
            mServiceIntent.putExtra(getResources().getString(R.string.string_tag), getResources().getString(R.string.string_init));
            if (isConnected) {
                getActivity().startService(mServiceIntent);
            } else {
                Toast.makeText(getActivity().getApplicationContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();
            }
        }
        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;

            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }

            getLoaderManager().initLoader(CURSOR_LOADER_ID, null, this);

            mCursorAdapter = new QuoteCursorAdapter(getActivity(), null);
            recyclerView.addOnItemTouchListener(new RecyclerViewItemClickListener(getActivity(),
                    new RecyclerViewItemClickListener.OnItemClickListener() {
                        @Override
                        public void onItemClick(View v, int position) {
                            //TODO:
                            // do something on item click
//                            Intent chartIntent = new Intent(mContext, MyStockDetailActivity.class);
//                            mCursor.moveToPosition(position);
//                            chartIntent.putExtra(getResources().getString(R.string.string_symbol), mCursor.getString(mCursor.getColumnIndex(getResources().getString(R.string.string_symbol))));
//                            mContext.startActivity(chartIntent);
                        }
                    }));
            recyclerView.setAdapter(mCursorAdapter);
//            recyclerView.setAdapter(new MystocksRecyclerViewAdapter(DummyContent.ITEMS, mListener));
        }

        if (isConnected) {
            long period = 3600L;
            long flex = 10L;
            String periodicTag = getResources().getString(R.string.periodic_tag);

            PeriodicTask periodicTask = new PeriodicTask.Builder()
                    .setService(StockTaskService.class)
                    .setPeriod(period)
                    .setFlex(flex)
                    .setTag(periodicTag)
                    .setRequiredNetwork(Task.NETWORK_STATE_CONNECTED)
                    .setRequiresCharging(false)
                    .build();
            GcmNetworkManager.getInstance(getActivity()).schedule(periodicTask);
        }

        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
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
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(DummyItem item);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(getActivity(), QuoteProvider.Quotes.CONTENT_URI,
                new String[]{QuoteColumns._ID, QuoteColumns.SYMBOL, QuoteColumns.BIDPRICE,
                        QuoteColumns.PERCENT_CHANGE, QuoteColumns.CHANGE, QuoteColumns.ISUP},
                QuoteColumns.ISCURRENT + getResources().getString(R.string.loaderIsCurrent),
                new String[]{getResources().getString(R.string.loaderString)},
                null);
    }

    @Override
    public void onLoadFinished(android.support.v4.content.Loader<Cursor> loader, Cursor data) {
        mCursorAdapter.swapCursor(data);
        mCursor = data;
    }

    @Override
    public void onLoaderReset(android.support.v4.content.Loader<Cursor> loader) {
        mCursorAdapter.swapCursor(null);
    }
}
