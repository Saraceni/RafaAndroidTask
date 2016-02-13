package com.rafa.rafaandroidtask.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.rafa.rafaandroidtask.R;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by rafaelgontijo on 2/13/16.
 */
public class ImageAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<JSONObject> jsons;

    public ImageAdapter(Context c, ArrayList<JSONObject> jsons) {
        mContext = c;
        this.jsons = jsons;
    }

    public int getCount() {
        return jsons.size();
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent)
    {
        ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder();
            // if it's not recycled, initialize some attributes
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            convertView = inflater.inflate(R.layout.grid_adapter, parent, false);
            holder.picture = (ImageView) convertView.findViewById(R.id.picture_grid_adapter);
            holder.description = (TextView) convertView.findViewById(R.id.description_grid_adapter);
            holder.position = position;

            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        try {
            JSONObject json = jsons.get(position);
            String link = json.getString("link");
            Picasso.with(mContext).load(link).into(holder.picture);
            if(json.has("description") && !json.getString("description").equals("null")){
                holder.description.setVisibility(View.VISIBLE);
                holder.description.setText(json.getString("description"));
            } else { holder.description.setVisibility(View.INVISIBLE); }
        } catch (Exception exc) { exc.printStackTrace(); holder.description.setVisibility(View.INVISIBLE); }
        //imageView.setImageResource(mThumbIds[position]);
        return convertView;


    }

    static class ViewHolder {
        ImageView picture;
        TextView description;
        int position;
    }
}
