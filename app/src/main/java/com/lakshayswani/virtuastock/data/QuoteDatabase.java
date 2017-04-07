package com.lakshayswani.virtuastock.data;

import net.simonvt.schematic.annotation.Database;
import net.simonvt.schematic.annotation.Table;

/**
 * Created by sam_chordas on 10/5/15.
 */
@Database(version = QuoteDatabase.VERSION)
public class QuoteDatabase {
    private QuoteDatabase() {
    }

    /**
     * The constant VERSION.
     */
    public static final int VERSION = 7;

    /**
     * The constant QUOTES.
     */
    @Table(QuoteColumns.class)
    public static final String QUOTES = "quotes";
}
