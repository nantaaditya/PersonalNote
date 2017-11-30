package com.android.pramuditya.personalnote.Util;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.pramuditya.personalnote.R;
import com.android.pramuditya.personalnote.model.Tag;
import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.iconics.IconicsDrawable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 29/11/2017.
 */

public class TagAdapter extends RecyclerView.Adapter<TagAdapter.ViewHolder> {
    private List<Tag> tags = new ArrayList<>();
    private LayoutInflater inflater;
    private Context context;

    public TagAdapter(Context context, ArrayList<Tag> tags) {
        this.context = context;
        this.tags = tags;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View viewHolder = this.inflater.inflate(R.layout.item_tag, parent, false);
        return new TagAdapter.ViewHolder(viewHolder);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Tag tag = tags.get(position);
        holder.tvTag.setText(tag.getName());
    }

    @Override
    public int getItemCount() {
        return tags.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvTag;

        public ViewHolder(View itemView) {
            super(itemView);
            context = itemView.getContext();
            this.tvTag = (TextView) itemView.findViewById(R.id.tv_tag);
        }
    }
}
