package com.bizmiz.gulbozor.ui.home.core.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bizmiz.gulbozor.R;
import com.bizmiz.gulbozor.ui.home.core.model.GroupItem;

import java.util.ArrayList;

public class GroupAdapter extends RecyclerView.Adapter<GroupViewHolder> {
    private ArrayList<GroupItem> groupItems = new ArrayList<>();

    public void setGroupItems(ArrayList<GroupItem> groupItems) {
        this.groupItems.addAll(groupItems);
        notifyItemRangeInserted(this.groupItems.size() - groupItems.size(), groupItems.size());
    }

    @NonNull
    @Override
    public GroupViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new GroupViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_group, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull GroupViewHolder holder, int position) {
        GroupItem item = groupItems.get(position);

        holder.recImage.setImageResource(item.getRecImage());
        holder.txtCategories.setText(item.getTxtCategories());
        holder.txtAllCat.setText(item.getTxtAll());
        holder.txtYoutubeTitle.setText(item.getTxtYouTubeTitle());
        holder.imgYoutube.setImageResource(item.getImgYouTube());
        holder.imgAllYoutube.setImageResource(item.getOthersYouTube());
        holder.txtAllPosts.setText(item.getTxtAll());
        holder.childAdapter.setFlowersList(item.getFlowerList());
    }

    @Override
    public int getItemCount() {
        return groupItems.size();
    }
}
