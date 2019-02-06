package com.example.eddy.githubusers.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.eddy.githubusers.MainActivity;
import com.example.eddy.githubusers.R;
import com.example.eddy.githubusers.adapter.ReposAdapter;
import com.example.eddy.githubusers.client.ApiManager;
import com.example.eddy.githubusers.model.Repository;
import com.example.eddy.githubusers.model.User;
import com.example.eddy.githubusers.util.NetworkUtil;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserPageFragment extends Fragment {

    private ImageView userImg;
    private TextView userName;
    private TextView followerQuantity;
    private ReposAdapter reposRecyclerAdapter;
    private ImageView LinkView;
    private User user;

    private List<Repository> reposName;

    public UserPageFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_user_page, container, false);

        if (!getArguments().isEmpty()){
            user = getArguments().getParcelable(UserListFragment.USER_ARG);
            Log.d(UserPageFragment.class.getSimpleName(), "send data success getArguments is not empty");

        }else{
            Log.d(UserPageFragment.class.getSimpleName(), "send data failed getArguments is empty");
        }

        loadRepos();

        userImg = rootView.findViewById(R.id.image_view_user_page_fragment);
        userName = rootView.findViewById(R.id.label_user_full_name_user_page_fragment);
        followerQuantity = rootView.findViewById(R.id.followers_count_number_user_page_fragment);
        LinkView = rootView.findViewById(R.id.web_view_user_page_fragment);
        Glide.with(MainActivity.getContextOfApplication())
                .load(user.getImgUrl())
                .into(this.userImg);

        //userName.setText(user.getFullName());
        userName.setText(user.getFullName());
        Log.d(UserPageFragment.class.getSimpleName(), "user repos quan" + user.getFollowersCount() + "text view is:" + followerQuantity.getText() );
        followerQuantity.setText(String.valueOf(user.getFollowersCount()));
        LinkView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(user.getLink()));
                startActivity(intent);
            }
        });


        reposRecyclerAdapter = new ReposAdapter();
        final Handler h = new Handler();
        Toast.makeText(getActivity(),"Data is Loading",Toast.LENGTH_SHORT).show();
        h.postDelayed(new Runnable() {
            @Override
            public void run() {

                if(reposName == null){
                    Log.d(UserPageFragment.class.getSimpleName(), "loading repos name " );
                    h.postDelayed(this, 500);
                }else{
                    reposRecyclerAdapter.addItems(reposName);
                    Log.d(UserPageFragment.class.getSimpleName(), "repos size: " + reposName.size() );
                }

            }
        },1000);


        RecyclerView recyclerView = rootView.findViewById(R.id.user_repos_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(reposRecyclerAdapter);


        return rootView;
    }

    private void loadRepos(){
        if (NetworkUtil.isNetworkAvailable(getContext())){
            Call<List<Repository>> call = ApiManager.getApiClient().getRepos2(user.getUserName());
            //Log.d(UserPageFragment.class.getSimpleName(), "user repos url: " +  user.getRepos());

            call.enqueue(new Callback<List<Repository>>() {
                @Override
                public void onResponse(Call<List<Repository>> call, Response<List<Repository>> response) {
                    reposName = response.body();

                }

                @Override
                public void onFailure(Call<List<Repository>> call, Throwable t) {
                    Log.d(UserPageFragment.class.getSimpleName(), "onFailure callback");
                }
            });
        }
    }

}
