package com.lakshayswani.virtuastock.touch_helper;

/**
 * Created by sam_chordas on 10/6/15.
 * credit to Paul Burke (ipaulpro)
 * Interface for enabling swiping to delete
 */
public interface ItemTouchHelperViewHolder {
    /**
     * On item selected.
     */
    void onItemSelected();

    /**
     * On item clear.
     */
    void onItemClear();
}
