package com.rafa.rafaandroidtask.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.rafa.rafaandroidtask.R;
import com.rafa.rafaandroidtask.data.ImgurObject;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by rafaelgontijo on 3/24/16.
 */
public class ListAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<ImgurObject> imgurObjects;

    public ListAdapter(Context c, ArrayList<ImgurObject> imgurObjects) {

        mContext = c;
        this.imgurObjects = imgurObjects;

    }

    public int getCount() {
        return imgurObjects.size();
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
            convertView = inflater.inflate(R.layout.list_adapter, parent, false);
            holder.picture = (ImageView) convertView.findViewById(R.id.image_list_adapter);
            holder.description = (TextView) convertView.findViewById(R.id.description_list_adapter);
            holder.position = position;

            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        try {
            ImgurObject imgur = imgurObjects.get(position);
            String link = imgur.getLink();
            if(!imgur.getIsAlbum()) {
                Picasso.with(mContext).load(link).fit().into(holder.picture);
            }
            else {
                holder.picture.setImageResource(R.mipmap.imgur_logo);
            }

            String description = imgur.getDescription();
            if(description != null && !description.equals("null")){
                holder.description.setVisibility(View.VISIBLE);
                holder.description.setText(description);
            } else { holder.description.setVisibility(View.INVISIBLE); }

        } catch (Exception exc) { exc.printStackTrace(); holder.description.setVisibility(View.INVISIBLE); }
        return convertView;


    }

    static class ViewHolder {
        ImageView picture;
        TextView description;
        int position;
    }
}
