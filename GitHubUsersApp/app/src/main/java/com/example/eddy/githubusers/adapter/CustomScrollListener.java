package com.example.eddy.githubusers.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.eddy.githubusers.fragment.UserList;

public abstract class CustomScrollListener extends RecyclerView.OnScrollListener {

    @SuppressWarnings("FieldCanBeLocal")
    private final int VISIBLE_TRESHOLD = 1;

    private int currentPage = 1;
    private int previousTotalItemCount = 0;
    private boolean loading = true;

    private LinearLayoutManager mLinearLayoutManager;

    public CustomScrollListener(LinearLayoutManager linearLayoutManager){
        mLinearLayoutManager = linearLayoutManager;
    }

    @Override
    public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
        int lastVisibleItemPosition = 0;
        int totalItemCount = mLinearLayoutManager.getItemCount();
        Log.d(CustomScrollListener.class.getSimpleName(), "totalItemCount is: " + totalItemCount);


        lastVisibleItemPosition = mLinearLayoutManager.findLastVisibleItemPosition();
        Log.d(CustomScrollListener.class.getSimpleName(), "last visible item position is: " + lastVisibleItemPosition);


        if (loading && (totalItemCount > previousTotalItemCount)) {
            loading = false;
            previousTotalItemCount = totalItemCount;
        }


//        if(!loading && lastVisibleItemPosition+1 >= (UserList.LIMIT) * currentPage){
//            currentPage++;
//            onLoadMore(currentPage, lastVisibleItemPosition+1, recyclerView);
//            loading = true;
//        }

        //something wrong there, but it seem everything correct and Log's is good
        if (!loading && (lastVisibleItemPosition + VISIBLE_TRESHOLD) > totalItemCount) {
            currentPage++;
            onLoadMore(currentPage, totalItemCount, recyclerView);
            loading = true;
        }
    }

    public abstract void onLoadMore(int page, int totalItemsCount, RecyclerView view);

}
