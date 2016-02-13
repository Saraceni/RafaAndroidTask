package com.rafa.rafaandroidtask;

import android.test.InstrumentationTestCase;
import android.util.Log;

import com.rafa.rafaandroidtask.util.RequestHelper;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by rafaelgontijo on 2/13/16.
 */
public class AndroidTest extends InstrumentationTestCase {

    public void testHttpRequest() throws Exception
    {

        final CountDownLatch signal = new CountDownLatch(1);

        new Thread(){
            public void run()
            {
                OkHttpClient client = new OkHttpClient();

                Request request = new Request.Builder()
                        .url("https://api.imgur.com/3/gallery/hot/0?showViral=true")
                        .header("User-Agent", "OkHttp Headers.java")
                        .addHeader("Authorization", "Client-ID 56c5ec8b7f7da3e")
                        .build();

                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        assertTrue(false);
                        e.printStackTrace();
                        signal.countDown();
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

                        Headers responseHeaders = response.headers();
                        for (int i = 0; i < responseHeaders.size(); i++) {
                            Log.i("HTTP_Request", responseHeaders.name(i) + ": " + responseHeaders.value(i));
                        }

                        Log.i("HTTP_Request", response.body().string());

                        signal.countDown();
                    }
                });
            }
        }.start();

        signal.await();


    }

    public void testHttpRequestV2() throws Exception
    {
        final CountDownLatch signal = new CountDownLatch(1);

        Callback callback = new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                assertTrue(false);
                e.printStackTrace();
                signal.countDown();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

                Headers responseHeaders = response.headers();
                for (int i = 0; i < responseHeaders.size(); i++) {
                    Log.i("HTTP_Request", responseHeaders.name(i) + ": " + responseHeaders.value(i));
                }

                //String json = response.body().string();
                try {
                    JSONObject json = new JSONObject(response.body().string());
                    JSONArray data = json.getJSONArray("data");
                    assertNotNull(data);
                    for(int i = 0; i < data.length(); i++)
                    {
                        JSONObject obj = data.getJSONObject(i);
                        assertNotNull(obj);
                        String link = obj.getString("link");
                        assertNotNull(link);
                        Log.i("HTTP_Request", link);


                    }
                    //Log.i("HTTP_Request", data.toString());
                } catch(Exception exc)
                {
                    exc.printStackTrace();
                    assertFalse(true);
                }

                signal.countDown();
            }
        };

        RequestHelper.performRequest(RequestHelper.SECTION_HOT, "0", true, callback);

        signal.await();
    }

}
