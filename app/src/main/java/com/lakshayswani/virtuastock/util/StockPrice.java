package com.lakshayswani.virtuastock.util;

import android.util.Log;
import org.json.JSONObject;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by lakshay.swani on 3/29/2017.
 */

public class StockPrice {

    public JSONObject getStockDetailsFromGoogle(String s) {
        String response = null;
        JSONObject JSON = null;
        String url = "http://finance.google.com/finance/info?client=ig&q=" + s;
        try {
            response = getResponse(url);
            response = response.substring(9, response.length()-2);
            JSON = new JSONObject(response);
        }catch (Exception e)
        {
            Log.e("Stock Details","Invalid Stock Name");
        }
            return JSON;
    }

    public String getResponse(String url) {
        String result = null;
        int c;
        try {
            URL hp = new URL(url);
            URLConnection hpCon = hp.openConnection();
            InputStream input = hpCon.getInputStream();
            while (((c = input.read()) != -1)) {
                result = result + (char) c;
            }
            input.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

}
