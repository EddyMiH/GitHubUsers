package com.example.eddy.githubusers.adapter.viewholder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.eddy.githubusers.R;


public class ReposItemViewHolder extends RecyclerView.ViewHolder {

    private TextView repoTitle;

    public ReposItemViewHolder(@NonNull View itemView) {
        super(itemView);

        repoTitle = itemView.findViewById(R.id.repos_label_recycler_view_page_fragment);
    }

    public void setRepoTitle(String title){
        repoTitle.setText(title);
    }

}
