package com.bizmiz.gulbozor.ui.home.core.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.bizmiz.gulbozor.R;
import com.bizmiz.gulbozor.ui.home.FlowersAdapter;

public class GroupViewHolder extends RecyclerView.ViewHolder {
    private final StaggeredGridLayoutManager layoutManager;
    public ImageView recImage;
    public TextView txtCategories;
    public TextView txtAllCat;
    public TextView txtYoutubeTitle;
    public ImageView imgYoutube;
    public ImageView imgAllYoutube;
    public TextView txtAllPosts;
    public RecyclerView childList;
    public FlowersAdapter childAdapter;

    public GroupViewHolder(@NonNull View itemView) {
        super(itemView);
        recImage = itemView.findViewById(R.id.rec_view);
        txtCategories = itemView.findViewById(R.id.txt_category);
        txtAllCat = itemView.findViewById(R.id.txt_all);
        txtYoutubeTitle = itemView.findViewById(R.id.youtube_title);
        txtAllPosts = itemView.findViewById(R.id.txt_all_post);
        imgAllYoutube = itemView.findViewById(R.id.youtube_others);
        imgYoutube = itemView.findViewById(R.id.youtube_image);
        childList = itemView.findViewById(R.id.child_list);

        childAdapter = new FlowersAdapter();
        layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        childList.setLayoutManager(layoutManager);
        childList.setAdapter(childAdapter);
    }
}
