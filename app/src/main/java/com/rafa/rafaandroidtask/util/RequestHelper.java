package com.rafa.rafaandroidtask.util;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by rafaelgontijo on 2/13/16.
 */
public class RequestHelper {

    public static final String SECTION_HOT = "hot";
    public static final String SECTION_TOP = "top";
    public static final String SECTION_USER = "user";

    private static final String API_BASE_URL = "https://api.imgur.com/3/gallery/";

    public static void performRequest(String section, String sort, String window, String page, boolean viral, final Callback callback)
    {
        String url = API_BASE_URL + section + "/" + sort + "/" + window + "/" + page + "/?";
        if(viral) { url += "showViral=true"; }
        else { url += "showViral=false"; }

        final String completeUrl = url;

        new Thread(){

            public void run()
            {
                OkHttpClient client = new OkHttpClient();

                Request request = new Request.Builder()
                        .url(completeUrl)
                        .header("User-Agent", "OkHttp Headers.java")
                        .addHeader("Authorization", "Client-ID 56c5ec8b7f7da3e")
                        .build();

                client.newCall(request).enqueue(callback);
            }

        }.start();
    }


}
