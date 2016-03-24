package com.rafa.rafaandroidtask;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.rafa.rafaandroidtask.adapter.ImageAdapter;
import com.rafa.rafaandroidtask.adapter.ListAdapter;
import com.rafa.rafaandroidtask.data.ImgurObject;
import com.rafa.rafaandroidtask.util.Prefs;
import com.rafa.rafaandroidtask.util.RequestHelper;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private static final int SETTINGS_ACTIVITY = 3;

    private GridView gridView;
    private ListView listView;
    private ImageAdapter adapter;
    private ListAdapter listAdapter;
    private ArrayList<ImgurObject> imgurArray;


    private ImageButton hotButton;
    private ImageButton userButton;
    private ImageButton topButton;
    private ProgressBar progress;

    private int DESELECTED_COLOR;
    private int SELECTED_COLOR;

    private int page = 0;
    private boolean isLoading = true;

    private String section = RequestHelper.SECTION_HOT;

    private String prefsWindow;
    private String prefsSort;
    private boolean prefsShowViral;
    private int viewMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Imgur");
        setSupportActionBar(toolbar);

        updatePrefs();

        imgurArray = new ArrayList<>();
        adapter = new ImageAdapter(this, imgurArray);
        listAdapter = new ListAdapter(this, imgurArray);

        listView = (ListView) findViewById(R.id.list_main);
        listView.setAdapter(listAdapter);
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

                if (scrollState == SCROLL_STATE_IDLE) {
                    if (listView.getLastVisiblePosition() == adapter.getCount() - 1 && !isLoading) {
                        isLoading = true;
                        progress.setVisibility(View.VISIBLE);
                        page++;
                        RequestHelper.performRequest(section, prefsSort, prefsWindow, String.valueOf(page), prefsShowViral, callback);
                    }
                }

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ImgurObject imgur = imgurArray.get(position);
                Intent intent = new Intent(MainActivity.this, ShowImageActivity.class);
                intent.putExtra(ShowImageActivity.JSON_EXTRA, imgur.getJSONData().toString());
                startActivity(intent);
            }
        });

        gridView = (GridView) findViewById(R.id.grid_main);
        gridView.setAdapter(adapter);
        gridView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

                if (scrollState == SCROLL_STATE_IDLE) {
                    if (gridView.getLastVisiblePosition() == adapter.getCount() - 1 && !isLoading) {
                        isLoading = true;
                        progress.setVisibility(View.VISIBLE);
                        page++;
                        RequestHelper.performRequest(section, prefsSort, prefsWindow, String.valueOf(page), prefsShowViral, callback);
                    }
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ImgurObject imgur = imgurArray.get(position);
                Intent intent = new Intent(MainActivity.this, ShowImageActivity.class);
                intent.putExtra(ShowImageActivity.JSON_EXTRA, imgur.getJSONData().toString());
                startActivity(intent);
            }
        });

        if(viewMode == Prefs.VIEW_MODE_GRID) {
            listView.setVisibility(View.GONE);
            gridView.setVisibility(View.VISIBLE);
        }
        else {
            gridView.setVisibility(View.GONE);
            listView.setVisibility(View.VISIBLE);
        }

        progress = (ProgressBar) findViewById(R.id.progress_main);

        hotButton = (ImageButton) findViewById(R.id.hot_navigation_main);
        hotButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                isLoading = true;
                progress.setVisibility(View.VISIBLE);

                hotButton.setBackgroundColor(SELECTED_COLOR);
                topButton.setBackgroundColor(DESELECTED_COLOR);
                userButton.setBackgroundColor(DESELECTED_COLOR);

                page = 0;
                section = RequestHelper.SECTION_HOT;
                imgurArray.clear();
                adapter.notifyDataSetChanged();
                listAdapter.notifyDataSetChanged();

                RequestHelper.performRequest(section, prefsSort, prefsWindow, String.valueOf(page), prefsShowViral, callback);
            }
        });

        topButton = (ImageButton) findViewById(R.id.top_navigation_main);
        topButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                isLoading = true;
                progress.setVisibility(View.VISIBLE);

                hotButton.setBackgroundColor(DESELECTED_COLOR);
                topButton.setBackgroundColor(SELECTED_COLOR);
                userButton.setBackgroundColor(DESELECTED_COLOR);

                page = 0;
                section = RequestHelper.SECTION_TOP;
                imgurArray.clear();
                adapter.notifyDataSetChanged();
                listAdapter.notifyDataSetChanged();

                RequestHelper.performRequest(section, prefsSort, prefsWindow, String.valueOf(page), prefsShowViral, callback);
            }
        });

        userButton = (ImageButton) findViewById(R.id.user_navigation_main);
        userButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                isLoading = true;
                progress.setVisibility(View.VISIBLE);

                hotButton.setBackgroundColor(DESELECTED_COLOR);
                topButton.setBackgroundColor(DESELECTED_COLOR);
                userButton.setBackgroundColor(SELECTED_COLOR);

                page = 0;
                section = RequestHelper.SECTION_USER;
                imgurArray.clear();
                adapter.notifyDataSetChanged();
                listAdapter.notifyDataSetChanged();

                RequestHelper.performRequest(section, prefsSort, prefsWindow, String.valueOf(page), prefsShowViral, callback);
            }
        });

        SELECTED_COLOR = getResources().getColor(R.color.colorPrimary);
        DESELECTED_COLOR = getResources().getColor(android.R.color.transparent);

        hotButton.setBackgroundColor(SELECTED_COLOR);
        topButton.setBackgroundColor(DESELECTED_COLOR);
        userButton.setBackgroundColor(DESELECTED_COLOR);

        progress.setVisibility(View.VISIBLE);
        RequestHelper.performRequest(section, prefsSort, prefsWindow, String.valueOf(page), prefsShowViral, callback);

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

        if(id == R.id.menu_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivityForResult(intent, SETTINGS_ACTIVITY);
        }

        return super.onOptionsItemSelected(item);
    }

    private void updatePrefs() {

        prefsShowViral = Prefs.getShowViral(this);
        prefsWindow = Prefs.getWindow(this);
        prefsSort = Prefs.getSort(this);
        viewMode = Prefs.getViewMode(this);
    }

    private void refreshData() {
        updatePrefs();
        page = 0;
        imgurArray.clear();
        adapter.notifyDataSetChanged();
        listAdapter.notifyDataSetChanged();

        if(viewMode == Prefs.VIEW_MODE_GRID) {
            listView.setVisibility(View.GONE);
            gridView.setVisibility(View.VISIBLE);
        }
        else {
            gridView.setVisibility(View.GONE);
            listView.setVisibility(View.VISIBLE);
        }

        progress.setVisibility(View.VISIBLE);
        RequestHelper.performRequest(section, prefsSort, prefsWindow, String.valueOf(page), prefsShowViral, callback);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == SETTINGS_ACTIVITY) {

            if (resultCode == RESULT_OK) {
                refreshData();
            }
        }
    }

    Callback callback = new Callback() {
        @Override
        public void onFailure(Call call, IOException e) {
            e.printStackTrace();
            isLoading = false;
            progress.setVisibility(View.GONE);
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
                    ImgurObject object = new ImgurObject(obj);
                    imgurArray.add(object);
                }

                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.notifyDataSetChanged();
                        listAdapter.notifyDataSetChanged();
                        progress.setVisibility(View.GONE);
                    }
                });

            } catch(Exception exc) {
                exc.printStackTrace();
            }

            isLoading = false;
        }
    };
}
