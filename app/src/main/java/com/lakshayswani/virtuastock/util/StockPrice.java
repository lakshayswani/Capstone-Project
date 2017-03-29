package com.lakshayswani.virtuastock.util;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by lakshay.swani on 3/29/2017.
 */

public class StockPrice {

    private OkHttpClient client = new OkHttpClient();

    public String getStockDetails(String stockName)
    {
        StringBuilder urlStringBuilder = new StringBuilder();
        String getResponse = null;
        String urlString = null;
        try{
            // Base URL for the Yahoo query
            urlStringBuilder.append("https://query.yahooapis.com/v1/public/yql?q=");
            urlStringBuilder.append(URLEncoder.encode("select * from yahoo.finance.quotes where symbol "
                    + "in (", "UTF-8"));
            urlStringBuilder.append(URLEncoder.encode("\""+stockName+"\")", "UTF-8"));
            urlStringBuilder.append("&format=json&diagnostics=true&env=store%3A%2F%2Fdatatables."
                    + "org%2Falltableswithkeys&callback=");

            urlString = urlStringBuilder.toString();

            getResponse = fetchData(urlString);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return getResponse;
    }

    public String fetchData(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = client.newCall(request).execute();
        return response.body().string();
    }

}
