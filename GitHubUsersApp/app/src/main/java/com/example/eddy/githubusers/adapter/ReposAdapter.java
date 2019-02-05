package com.example.eddy.githubusers.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import com.example.eddy.githubusers.R;
import com.example.eddy.githubusers.adapter.viewholder.ReposItemViewHolder;
import com.example.eddy.githubusers.model.Repository;

import java.util.ArrayList;
import java.util.List;

public class ReposAdapter extends RecyclerView.Adapter<ReposItemViewHolder> {

    private List<Repository> mData;

    public ReposAdapter() {
        mData = new ArrayList<>();
    }

    public void setmData(List<Repository> mData) {
        this.mData = mData;
    }

    @NonNull
    @Override
    public ReposItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.user_repos_item_recycler_view, viewGroup, false);
        ReposItemViewHolder viewHolder = new ReposItemViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ReposItemViewHolder reposItemViewHolder, int i) {
        Repository repo = mData.get(i);
        reposItemViewHolder.setRepoTitle(repo.getName());

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void addItems(List<Repository> items){
        mData.addAll(items);
        notifyDataSetChanged();
    }


}
