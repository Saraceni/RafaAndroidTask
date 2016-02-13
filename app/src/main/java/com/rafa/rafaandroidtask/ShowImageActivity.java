package com.rafa.rafaandroidtask;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONObject;

/**
 * Created by rafaelgontijo on 2/13/16.
 */
public class ShowImageActivity extends Activity {

    public static final String JSON_EXTRA = "JSON_EXTRA";

    private ImageView pic;
    private TextView ups;
    private TextView downs;
    private TextView score;
    private TextView title;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_show_image);


        pic = (ImageView) findViewById(R.id.pic_show_image_activity);
        ups = (TextView) findViewById(R.id.ups_show_image_activity);
        downs = (TextView) findViewById(R.id.downs_show_image_activity);
        score = (TextView) findViewById(R.id.score_show_image_activity);
        title = (TextView) findViewById(R.id.title_show_image_activity);

        Bundle extras =  getIntent().getExtras();
        if(extras != null && extras.containsKey(JSON_EXTRA))
        {
            try
            {
                JSONObject json = new JSONObject(extras.getString(JSON_EXTRA));

                if(json.has("ups") && json.getInt("ups") > 0) {
                    ups.setText("Ups: " + String.valueOf(json.getInt("ups")));
                } else { ups.setText(""); }

                if(json.has("downs") && json.getInt("downs") > 0) {
                    downs.setText("Downs: " + String.valueOf(json.getInt("downs")));
                } else { downs.setText(""); }

                if(json.has("score") && json.getInt("score") > 0) {
                    score.setText("Score: " + String.valueOf(json.getInt("score")));
                } else { score.setText(""); }

                if(json.has("link") && !json.getString("link").equals("null")) {
                    Picasso.with(this).load(json.getString("link")).into(pic);
                }

                if(json.has("title") && !json.getString("title").equals("null")) {
                    title.setText(json.getString("title"));
                }


            } catch (Exception exc) { exc.printStackTrace(); }
        }

    }
}
