package com.lakshayswani.virtuastock.data;

import android.net.Uri;
import net.simonvt.schematic.annotation.ContentProvider;
import net.simonvt.schematic.annotation.ContentUri;
import net.simonvt.schematic.annotation.InexactContentUri;
import net.simonvt.schematic.annotation.TableEndpoint;

/**
 * Created by sam_chordas on 10/5/15.
 */
@ContentProvider(authority = QuoteProvider.AUTHORITY, database = QuoteDatabase.class)
public class QuoteProvider {
    /**
     * The constant AUTHORITY.
     */
    public static final String AUTHORITY = "com.lakshayswani.virtuastock.data.QuoteProvider";

    /**
     * The Base content uri.
     */
    static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    /**
     * The interface Path.
     */
    interface Path{
        /**
         * The constant QUOTES.
         */
        String QUOTES = "quotes";
  }

  private static Uri buildUri(String... paths){
    Uri.Builder builder = BASE_CONTENT_URI.buildUpon();
    for (String path:paths){
      builder.appendPath(path);
    }
    return builder.build();
  }

    /**
     * The type Quotes.
     */
    @TableEndpoint(table = QuoteDatabase.QUOTES)
  public static class Quotes{
        /**
         * The constant CONTENT_URI.
         */
        @ContentUri(
        path = Path.QUOTES,
        type = "vnd.android.cursor.dir/quote"
    )
    public static final Uri CONTENT_URI = buildUri(Path.QUOTES);

        /**
         * With symbol uri.
         *
         * @param symbol the symbol
         * @return the uri
         */
        @InexactContentUri(
        name = "QUOTE_ID",
        path = Path.QUOTES + "/*",
        type = "vnd.android.cursor.item/quote",
        whereColumn = QuoteColumns.SYMBOL,
        pathSegment = 1
    )
    public static Uri withSymbol(String symbol){
      return buildUri(Path.QUOTES, symbol);
    }
  }
}
