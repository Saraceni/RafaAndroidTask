package com.rafa.rafaandroidtask.data;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by rafaelgontijo on 3/14/16.
 */
public class ImgurObject {

    public static final String LINK = "link";
    public static final String DESCRIPTION = "description";
    public static final String IS_ALBUM = "is_album";
    public static final String UPS = "ups";
    public static final String DOWNS = "downs";
    public static final String SCORE = "score";
    public static final String TITLE = "title";

    private String link;
    private String description;
    private boolean isAlbum;
    private String title;
    private int ups;
    private int downs;
    private int score;

    public ImgurObject(JSONObject data) throws JSONException {

        this.link = data.has(LINK) ? data.getString(LINK) : "";
        this.description = data.has(DESCRIPTION) ? data.getString(DESCRIPTION) : "";
        this.isAlbum = data.has(IS_ALBUM) ? data.getBoolean(IS_ALBUM) : false;
        this.ups = data.has(UPS) ? data.getInt(UPS) : 0;
        this.downs = data.has(DOWNS) ? data.getInt(DOWNS) : 0;
        this.score = data.has(SCORE) ? data.getInt(SCORE) : 0;
        this.title = data.has(TITLE) ? data.getString(TITLE) : "";
    }

    public int getScore() {
        return this.score;
    }

    public int getDowns() {
        return this.downs;
    }

    public int getUps() {
        return this.ups;
    }

    public String getTitle() {
        return this.title;
    }

    public boolean getIsAlbum() {
        return this.isAlbum;
    }

    public String getDescription() {
        return description;
    }

    public String getLink() {
        return this.link;
    }

    public JSONObject getJSONData() {

        JSONObject json = new JSONObject();

        try {
            json.put(LINK, this.link);
            json.put(DESCRIPTION, this.description);
            json.put(IS_ALBUM, this.isAlbum);
            json.put(UPS, this.ups);
            json.put(DOWNS, this.downs);
            json.put(SCORE, this.score);
            json.put(UPS, ups);
            json.put(TITLE, title);
            return json;
        }
        catch(Exception exc) {
            exc.printStackTrace();
            return null;
        }

    }

}
