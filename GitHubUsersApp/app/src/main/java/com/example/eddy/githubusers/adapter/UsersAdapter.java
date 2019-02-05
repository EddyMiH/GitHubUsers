package com.example.eddy.githubusers.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import com.example.eddy.githubusers.R;
import com.example.eddy.githubusers.adapter.viewholder.UserItemViewHolder;
import com.example.eddy.githubusers.model.User;
import java.util.ArrayList;
import java.util.List;

public class UsersAdapter extends RecyclerView.Adapter<UserItemViewHolder> implements Filterable {

    private List<User> mData;
    private List<User> searchData;
    private OnItemSelectedListener mOnItemSelectedListener;

    public UsersAdapter(){
        mData = new ArrayList<>();
        searchData = new ArrayList<>();
    }

    @NonNull
    @Override
    public UserItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.user_item_recycler_view, viewGroup, false);
        final UserItemViewHolder viewHolder = new UserItemViewHolder(view);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mOnItemSelectedListener != null){
                    mOnItemSelectedListener.onItemSelected(mData.get(viewHolder.getAdapterPosition()));
                }
            }
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull UserItemViewHolder userItemViewHolder, int i) {
        User user = mData.get(i);
        userItemViewHolder.setImg(user.getImgUrl());
        userItemViewHolder.setmFullName(user.getFullName());
        Log.d(UsersAdapter.class.getSimpleName(), "full name is:" + user.getFullName());
        userItemViewHolder.setmUserName(user.getUserName());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void setData(List<User> us){
        mData.clear();
        mData.addAll(us);
        searchData.clear();
        addToSearchList(us);
        notifyDataSetChanged();
    }

    public void addItems(List<User> users){
        mData.addAll(users);
        addToSearchList(users);
        notifyDataSetChanged();
    }

    public void addToSearchList(List<User> values){
        searchData.addAll(values);
    }

    public void setOnItemSelectedListener(OnItemSelectedListener onItemSelectedListener){
        mOnItemSelectedListener = onItemSelectedListener;
    }

    @Override
    public Filter getFilter() {
        return myFilter;
    }

    private Filter myFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<User> filteredUsers = new ArrayList<>();
            if(constraint == null || constraint.length() == 0){
                filteredUsers.addAll(searchData);
            }else{
                String filterValue = constraint.toString().toLowerCase().trim();
                for(User item : searchData){
                    if (item.getUserName().toLowerCase().contains(filterValue)){
                        filteredUsers.add(item);
                    }
                }
            }
            FilterResults result = new FilterResults();
            result.values = filteredUsers;
            return result;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            mData.clear();
            mData.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

    public interface OnItemSelectedListener{
        void onItemSelected(User user);
    }
}
