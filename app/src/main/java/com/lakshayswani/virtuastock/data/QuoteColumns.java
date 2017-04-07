package com.lakshayswani.virtuastock.data;

import net.simonvt.schematic.annotation.AutoIncrement;
import net.simonvt.schematic.annotation.DataType;
import net.simonvt.schematic.annotation.NotNull;
import net.simonvt.schematic.annotation.PrimaryKey;

/**
 * Created by sam_chordas on 10/5/15.
 */
public class QuoteColumns {
    /**
     * The constant _ID.
     */
    @DataType(DataType.Type.INTEGER) @PrimaryKey @AutoIncrement
  public static final String _ID = "_id";
    /**
     * The constant SYMBOL.
     */
    @DataType(DataType.Type.TEXT) @NotNull
  public static final String SYMBOL = "symbol";
    /**
     * The constant PERCENT_CHANGE.
     */
    @DataType(DataType.Type.TEXT) @NotNull
  public static final String PERCENT_CHANGE = "percent_change";
    /**
     * The constant CHANGE.
     */
    @DataType(DataType.Type.TEXT) @NotNull
  public static final String CHANGE = "change";
    /**
     * The constant BIDPRICE.
     */
    @DataType(DataType.Type.TEXT) @NotNull
  public static final String BIDPRICE = "bid_price";
    /**
     * The constant CREATED.
     */
    @DataType(DataType.Type.TEXT)
  public static final String CREATED = "created";
    /**
     * The constant ISUP.
     */
    @DataType(DataType.Type.INTEGER) @NotNull
  public static final String ISUP = "is_up";
    /**
     * The constant ISCURRENT.
     */
    @DataType(DataType.Type.INTEGER) @NotNull
  public static final String ISCURRENT = "is_current";
}
