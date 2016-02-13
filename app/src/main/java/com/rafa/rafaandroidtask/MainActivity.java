package com.rafa.rafaandroidtask;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.GridView;
import android.widget.ImageButton;

import com.rafa.rafaandroidtask.adapter.ImageAdapter;
import com.rafa.rafaandroidtask.util.RequestHelper;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private GridView gridView;
    private ImageAdapter adapter;
    private ArrayList<JSONObject> jsonArray;

    private ImageButton hotButton;
    private ImageButton userButton;
    private ImageButton topButton;

    private int DARKER_GRAY;
    private int TRANSPARENT;

    private int page = 0;
    private boolean isLoading = true;

    private String section = RequestHelper.SECTION_HOT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        jsonArray = new ArrayList<>();
        adapter = new ImageAdapter(this, jsonArray);

        gridView = (GridView) findViewById(R.id.grid_main);
        gridView.setAdapter(adapter);
        gridView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

                if (scrollState == SCROLL_STATE_IDLE)
                {
                    if(gridView.getLastVisiblePosition() == adapter.getCount()-1 && !isLoading)
                    {
                        isLoading = true;
                        page++;
                        RequestHelper.performRequest(section, String.valueOf(page), true, callback);
                    }
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });

        hotButton = (ImageButton) findViewById(R.id.hot_navigation_main);
        hotButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                isLoading = true;

                hotButton.setBackgroundColor(DARKER_GRAY);
                topButton.setBackgroundColor(TRANSPARENT);
                userButton.setBackgroundColor(TRANSPARENT);

                page = 0;
                section = RequestHelper.SECTION_HOT;
                jsonArray.clear();
                adapter.notifyDataSetChanged();

                RequestHelper.performRequest(section, String.valueOf(page), true, callback);
            }
        });

        topButton = (ImageButton) findViewById(R.id.top_navigation_main);
        topButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                isLoading = true;

                hotButton.setBackgroundColor(TRANSPARENT);
                topButton.setBackgroundColor(DARKER_GRAY);
                userButton.setBackgroundColor(TRANSPARENT);

                page = 0;
                section = RequestHelper.SECTION_TOP;
                jsonArray.clear();
                adapter.notifyDataSetChanged();

                RequestHelper.performRequest(section, String.valueOf(page), true, callback);
            }
        });

        userButton = (ImageButton) findViewById(R.id.user_navigation_main);
        userButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                isLoading = true;

                hotButton.setBackgroundColor(TRANSPARENT);
                topButton.setBackgroundColor(TRANSPARENT);
                userButton.setBackgroundColor(DARKER_GRAY);

                page = 0;
                section = RequestHelper.SECTION_USER;
                jsonArray.clear();
                adapter.notifyDataSetChanged();

                RequestHelper.performRequest(section, String.valueOf(page), true, callback);
            }
        });

        DARKER_GRAY = getResources().getColor(android.R.color.darker_gray);
        TRANSPARENT = getResources().getColor(android.R.color.transparent);

        hotButton.setBackgroundColor(DARKER_GRAY);
        topButton.setBackgroundColor(TRANSPARENT);
        userButton.setBackgroundColor(TRANSPARENT);

        RequestHelper.performRequest(section, String.valueOf(page), true, callback);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    Callback callback = new Callback() {
        @Override
        public void onFailure(Call call, IOException e) {
            e.printStackTrace();
            isLoading = false;
        }

        @Override
        public void onResponse(Call call, Response response) throws IOException {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

            try {

                JSONObject json = new JSONObject(response.body().string());
                JSONArray data = json.getJSONArray("data");
                for(int i = 0; i < data.length(); i++)
                {
                    JSONObject obj = data.getJSONObject(i);
                    String link = obj.getString("link");
                    if(link.endsWith(".gif") || link.endsWith(".png")) {
                        jsonArray.add(obj);
                    }
                }

                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.notifyDataSetChanged();
                    }
                });

            } catch(Exception exc)
            {
                exc.printStackTrace();
            }

            isLoading = false;
        }
    };
}
