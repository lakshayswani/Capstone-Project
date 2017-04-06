package com.lakshayswani.virtuastock.widget;

import android.content.Intent;
import android.database.Cursor;
import android.os.Binder;
import android.widget.AdapterView;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.lakshayswani.virtuastock.R;
import com.lakshayswani.virtuastock.data.QuoteColumns;
import com.lakshayswani.virtuastock.data.QuoteProvider;

/**
 * Created by lenovo on 20-12-2016.
 */

public class WidgetRemoteViewsFactory extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new RemoteViewsFactory() {
            private Cursor data = null;


            @Override
            public void onCreate() {
            }

            @Override
            public void onDataSetChanged() {
                if (data != null) {
                    data.close();
                }

                final long identityToken = Binder.clearCallingIdentity();

                data = getContentResolver().query(QuoteProvider.Quotes.CONTENT_URI,
                        new String[]{QuoteColumns._ID, QuoteColumns.SYMBOL, QuoteColumns.BIDPRICE,
                                QuoteColumns.PERCENT_CHANGE, QuoteColumns.CHANGE, QuoteColumns.ISUP},
                        QuoteColumns.ISCURRENT + getResources().getString(R.string.loaderIsCurrent),
                        new String[]{getResources().getString(R.string.loaderString)},
                        null);


                Binder.restoreCallingIdentity(identityToken);
            }

            @Override
            public void onDestroy() {
                if (data != null) {
                    data.close();
                    data = null;
                }
            }

            @Override
            public int getCount() {
                return data == null ? 0 : data.getCount();
            }

            @Override
            public RemoteViews getViewAt(int position) {
                if (position == AdapterView.INVALID_POSITION ||
                        data == null || !data.moveToPosition(position)) {
                    return null;
                }

                RemoteViews views = new RemoteViews(getPackageName(), R.layout.new_widget_item_layout);
                String symbol = data.getString(data.getColumnIndex(getResources().getString(R.string.string_symbol)));

                views.setTextViewText(R.id.stock_symbol, symbol);
                views.setTextViewText(R.id.bid_price, data.getString(data.getColumnIndex(getResources().getString(R.string.bid_price))));
                views.setTextViewText(R.id.change, data.getString(data.getColumnIndex(getResources().getString(R.string.percent_change))));


                if (data.getInt(data.getColumnIndex(getResources().getString(R.string.is_up))) == 1) {
                    views.setInt(R.id.change, getResources().getString(R.string.setBackgroundResource), R.drawable.percent_change_pill_green);
                } else {
                    views.setInt(R.id.change, getResources().getString(R.string.setBackgroundResource), R.drawable.percent_change_pill_red);
                }

                return views;
            }

            @Override
            public RemoteViews getLoadingView() {
                return null;
            }

            @Override
            public int getViewTypeCount() {
                return 1;
            }

            @Override
            public long getItemId(int position) {
                if (data.moveToPosition(position))
                    return data.getLong(data.getColumnIndexOrThrow(QuoteColumns._ID));
                return position;
            }

            @Override
            public boolean hasStableIds() {
                return true;
            }
        };
    }
}