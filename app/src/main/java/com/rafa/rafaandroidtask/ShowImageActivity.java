package com.rafa.rafaandroidtask;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.rafa.rafaandroidtask.data.ImgurObject;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

/**
 * Created by rafaelgontijo on 2/13/16.
 */
public class ShowImageActivity extends Activity {

    public static final String JSON_EXTRA = "JSON_EXTRA";

    private WebView webView;
    private TextView ups;
    private TextView downs;
    private TextView score;
    private TextView title;
    private TextView description;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_show_image);

        webView = (WebView) findViewById(R.id.web_view);
        ups = (TextView) findViewById(R.id.ups_show_image_activity);
        downs = (TextView) findViewById(R.id.downs_show_image_activity);
        score = (TextView) findViewById(R.id.score_show_image_activity);
        title = (TextView) findViewById(R.id.title_show_image_activity);
        description = (TextView) findViewById(R.id.description_show_image_activity);

        Bundle extras =  getIntent().getExtras();
        if(extras != null && extras.containsKey(JSON_EXTRA))
        {
            try
            {
                JSONObject json = new JSONObject(extras.getString(JSON_EXTRA));
                ImgurObject imgur = new ImgurObject(json);

                ups.setText("Ups: " + String.valueOf(imgur.getUps()));
                downs.setText("Downs: " + String.valueOf(imgur.getDowns()));
                score.setText("Score: " + String.valueOf(imgur.getScore()));

                if(imgur.getIsAlbum()) {
                    title.setVisibility(View.GONE);
                    description.setVisibility(View.GONE);
                }
                else {
                    title.setText(imgur.getTitle());

                    if(!imgur.getDescription().equals("null")) {
                        description.setText(imgur.getDescription());
                    }
                    else {
                        description.setVisibility(View.GONE);
                    }
                }

                if(imgur.getLink() != null && imgur.getLink().length() > 0) {
                    webView.setWebViewClient(new WebViewClient());
                    webView.getSettings().setUserAgentString("user-agent-saraceni");
                    webView.getSettings().setBuiltInZoomControls(true);
                    webView.getSettings().setDisplayZoomControls(false);
                    webView.getSettings().setUseWideViewPort(true);
                    webView.getSettings().setLoadWithOverviewMode(true);
                    webView.getSettings().setJavaScriptEnabled(true);
                    webView.loadUrl(imgur.getLink());
                }

            } catch (Exception exc) { exc.printStackTrace(); }
        }

    }
}
