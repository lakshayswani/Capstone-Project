package com.lakshayswani.virtuastock.util;

/**
 * Created by lenovo on 02-04-2017.
 */

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.util.AttributeSet;
import android.view.MenuItem;

/**
 * The type Bottom navigation view selection.
 */
public class BottomNavigationViewSelection extends BottomNavigationView {

    private ItemSelectedListener listener;
    private ItemIndexSelectedListener listenerByIndex;
    private int currentItem = 0;

    /**
     * Instantiates a new Bottom navigation view selection.
     *
     * @param context the context
     */
    public BottomNavigationViewSelection(Context context) {
        super(context);
        init();
    }

    /**
     * Instantiates a new Bottom navigation view selection.
     *
     * @param context the context
     * @param attrs   the attrs
     */
    public BottomNavigationViewSelection(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    /**
     * Instantiates a new Bottom navigation view selection.
     *
     * @param context      the context
     * @param attrs        the attrs
     * @param defStyleAttr the def style attr
     */
    public BottomNavigationViewSelection(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    /**
     * Sets current item.
     *
     * @param itemIndex the item index
     */
    public void setCurrentItem(int itemIndex) {
        final boolean wasChecked = checkItem(itemIndex);
        if (listener != null) {
            listener.onItemSelected(getMenu().getItem(itemIndex), wasChecked);
        } else if (listenerByIndex != null) {
            listenerByIndex.onItemSelected(itemIndex, wasChecked);
        }
    }

    /**
     * Gets current item.
     *
     * @return the current item
     */
    public int getCurrentItem() {
        return currentItem;
    }

    /**
     * Gets item.
     *
     * @param itemIndex the item index
     * @return the item
     */
    public MenuItem getItem(int itemIndex) {
        return getMenu().getItem(itemIndex);
    }

    /**
     * Sets item selected listener.
     *
     * @param listener the listener
     */
    public void setItemSelectedListener(@Nullable ItemSelectedListener listener) {
        this.listener = listener;
    }

    /**
     * Sets item selected listener.
     *
     * @param listener the listener
     */
    public void setItemSelectedListener(@Nullable ItemIndexSelectedListener listener) {
        this.listenerByIndex = listener;
    }

    private void init() {
        setOnNavigationItemSelectedListener(new OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                final boolean wasChecked = checkItem(item);
                if (listener != null) {
                    return listener.onItemSelected(item, wasChecked);
                }
                return listenerByIndex != null && listenerByIndex.onItemSelected(getItemIndex(item), wasChecked);

            }
        });
    }

    private boolean checkItem(int itemIndex) {
        if (itemIndex < 0) return true;
        if (currentItem == itemIndex) return true;
        for (int i = 0; i < getMenu().size(); i++) {
            getMenu().getItem(i).setChecked(i == itemIndex);
        }

        currentItem = itemIndex;
        return false;
    }

    private boolean checkItem(MenuItem item) {
        item.setChecked(true);
        return checkItem(getItemIndex(item));
    }

    /**
     * @return item's index in Menu or -1 if item was not found
     */

    private int getItemIndex(MenuItem item) {
        for (int i = 0; i < getMenu().size(); i++) {
            if (getMenu().getItem(i).equals(item)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * The interface Item selected listener.
     */
    public interface ItemSelectedListener {
        /**
         * Called when an item in the bottom navigation menu is selected.
         *
         * @param item        The selected item
         * @param wasSelected was this item selected before
         * @return true to display the item as the selected item
         */
        boolean onItemSelected(@NonNull MenuItem item, boolean wasSelected);
    }

    /**
     * The interface Item index selected listener.
     */
    public interface ItemIndexSelectedListener {
        /**
         * Called when an item in the bottom navigation menu is selected.
         *
         * @param itemIndex   The selected item's index
         * @param wasSelected was this item selected before
         * @return true to display the item as the selected item
         */
        boolean onItemSelected(int itemIndex, boolean wasSelected);
    }
}