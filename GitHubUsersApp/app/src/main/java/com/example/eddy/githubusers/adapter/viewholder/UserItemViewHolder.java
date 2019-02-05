package com.example.eddy.githubusers.adapter.viewholder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.example.eddy.githubusers.MainActivity;
import com.example.eddy.githubusers.R;

public class UserItemViewHolder extends RecyclerView.ViewHolder {

    private ImageView img;
    private TextView mFullName;
    private TextView mUserName;

    public UserItemViewHolder(@NonNull View itemView) {
        super(itemView);

        img = itemView.findViewById(R.id.user_image_card_view);
        mFullName = itemView.findViewById(R.id.label_user_full_name_card_view);
        mUserName = itemView.findViewById(R.id.label_user_user_name_card_view);
    }

    public void setImg(String src) {
        Glide.with(MainActivity.getContextOfApplication())
                .load(src)
                .into(this.img);
    }

    public void setmFullName(String mFullName) {
        this.mFullName.setText( mFullName);
    }

    public void setmUserName(String mUserName) {
        this.mUserName.setText(mUserName);
    }
}
