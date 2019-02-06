package com.example.eddy.githubusers.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.example.eddy.githubusers.MainActivity;
import com.example.eddy.githubusers.R;
import com.example.eddy.githubusers.adapter.UsersAdapter;
import com.example.eddy.githubusers.client.ApiManager;
import com.example.eddy.githubusers.model.User;
import com.example.eddy.githubusers.model.UserName;
import com.example.eddy.githubusers.util.NetworkUtil;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.eddy.githubusers.MainActivity.APP_TITLE;

public class UserListFragment extends Fragment {

    private UsersAdapter recyclerAdapter;
    private List<User> users;
    private static boolean isFragmentLaunch = true;

    //private int offset = 0;
    public final static int LIMIT = 10;
    private Handler handler;

    public static final String USER_ARG = "user_argument";

    private UsersAdapter.OnItemSelectedListener mOnItemSelectedListener = new UsersAdapter.OnItemSelectedListener() {
        @Override
        public void onItemSelected(User user) {
            //TODO send intent to fragment UserPageFragment, replace fragments, add to backStack, and commit
            Bundle bundle = new Bundle();
            bundle.putParcelable(USER_ARG, user);
            UserPageFragment fragment = new UserPageFragment();
            fragment.setArguments(bundle);
            getFragmentManager().beginTransaction().replace(R.id.container, fragment)
                    .addToBackStack(null)
                    .commit();
            getActivity().setTitle(user.getUserName());
        }
    };

    private List<User> tempUsers;

    public void loadUsers(int offset){

        if(NetworkUtil.isNetworkAvailable(MainActivity.getContextOfApplication())){
            Call<List<User>> call = ApiManager.getApiClient().getUsers(offset, LIMIT);
            Log.d(UserListFragment.class.getSimpleName(), "offset is: " + offset);

            call.enqueue(new Callback<List<User>>() {
                @Override
                public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                    Log.d(UserListFragment.class.getSimpleName(), "onResponse callback");
                    tempUsers = new ArrayList<>();
                    tempUsers = response.body();
                    //users = response.body();

                    if (tempUsers == null){
                        handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            int counter = 0;
                            @Override
                            public void run() {
                                counter++;
                                if(tempUsers == null ){
                                    Log.d(UserListFragment.class.getSimpleName(), "I'm null");
                                    if ( counter > 10){
                                        Toast.makeText(getActivity(),"Api Server is not responding", Toast.LENGTH_SHORT).show();
                                        return;
                                    }

                                    handler.postDelayed(this, 1000);
                                }else{
                                    Log.d(UserListFragment.class.getSimpleName(), "tempList size: " + tempUsers.size());

                                    users.addAll(tempUsers);
                                    Log.d(UserListFragment.class.getSimpleName(), "users size: " + users.size());
                                    loadNameOfUsers();
                                }

                            }
                        }, 1000);

                    }else{
                        Log.d(UserListFragment.class.getSimpleName(), "tempList size: " + tempUsers.size());

                        users.addAll(tempUsers);
                        Log.d(UserListFragment.class.getSimpleName(), "users size: " + users.size());
                        loadNameOfUsers();
                    }
                }

                @Override
                public void onFailure(Call<List<User>> call, Throwable t) {
                    Log.d(UserListFragment.class.getSimpleName(), "onFailure callback");
                }
            });
        }
    }

    private int lastItem = 0;

    private void loadNameOfUsers(){
        for (int i = lastItem; i < users.size(); ++i){
            Call<UserName> mCall = ApiManager.getApiClient().getUserName(users.get(i).getUserName());
            final int index = i;
            mCall.enqueue(new Callback<UserName>() {
                @Override
                public void onResponse(Call<UserName> call, Response<UserName> response) {
                    final UserName userName = response.body();

                        final Handler h = new Handler();
                        h.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                int counter = 0;
                                if(userName == null ){
                                    //Toast.makeText(getActivity(),"Loading Data", Toast.LENGTH_SHORT).show();
                                    counter++;
                                    if(counter > 20) return;
                                    h.postDelayed(this,500);
                                }else{
                                    users.get(index).setFullName(userName.getName());
                                    users.get(index).setFollowersCount(userName.getFollowers());
                                }
                            }
                        },500);

                    Log.d(UserListFragment.class.getSimpleName(), "onResponse callback load names");
                }

                @Override
                public void onFailure(Call<UserName> call, Throwable t) {
                    Log.d(UserListFragment.class.getSimpleName(), "onFailure callback user name response");
                }
            });
        }
        lastItem = users.size();
        handler = new Handler();
        //all started with the fact that GitHub does not give a name when getting all users list, so I
        //using postDelay because recycler adapter add items faster than thread of retrofit has done its work
        //so i didn't find other way to fix this problem
        //if you can say more ELEGANT way, write me in Slack ;D
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
               recyclerAdapter.notifyDataSetChanged();
            }
        },2000);
        recyclerAdapter.setData(users);
        Log.d(UserListFragment.class.getSimpleName(), "all init are done, size of users:" + users.size());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_user_recycler_view, container, false);
        RecyclerView recyclerView = rootView.findViewById(R.id.users_recycler_view);

        initRecyclerView(recyclerView);
        if(isFragmentLaunch){
            loadUsers(0);
            isFragmentLaunch = false;
        }else{
            recyclerAdapter.setData(users);
        }


        return rootView;
    }

    private void initRecyclerView(RecyclerView view){

        recyclerAdapter = new UsersAdapter();
        recyclerAdapter.setOnItemSelectedListener(mOnItemSelectedListener);
        getActivity().setTitle(APP_TITLE);
        //recyclerAdapter.addItems(users);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());

        RecyclerView.OnScrollListener recyclerViewOnScrollListener = new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }
            private int previousTotalItemCount = 0;
            private boolean loading = true;

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int visibleItemCount = linearLayoutManager.getChildCount();
                int totalItemCount = linearLayoutManager.getItemCount();
                int firstVisibleItemPosition = linearLayoutManager.findFirstVisibleItemPosition();
                int lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();

                Log.d(UserListFragment.class.getSimpleName(), "visibleItemCount + firstVisibleItemPosition is: " + (visibleItemCount + firstVisibleItemPosition));
                Log.d(UserListFragment.class.getSimpleName(), "lastVisibleItem is: " + lastVisibleItem);
                Log.d(UserListFragment.class.getSimpleName(), "totalItemCount is: " + totalItemCount);

                if (loading && (totalItemCount > previousTotalItemCount)) {
                    loading = false;
                    previousTotalItemCount = totalItemCount;
                }

                if((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                    && lastVisibleItem+1 >= totalItemCount
                    && firstVisibleItemPosition >=0 && totalItemCount >= LIMIT
                    && !loading){
                    loadUsers(totalItemCount);
                    loading = true;
                }
            }
        };

        view.setLayoutManager(linearLayoutManager);
        view.setHasFixedSize(true);
        view.addOnScrollListener(recyclerViewOnScrollListener);
        view.setAdapter(recyclerAdapter);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        users = new ArrayList<>();
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_search_users, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                //TODO search user using s, download from api, and add in recycler view (if necessary xD)
                Call<User> call = ApiManager.getApiClient().getUser(s);
                Log.d(UserListFragment.class.getSimpleName(),"submit search");
                call.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        User user = response.body();
                        if(user != null){
                            Log.d(UserListFragment.class.getSimpleName(),"NOT NULL !!!!!");

                            users.add(0, user);
                        }
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        Toast.makeText(getContext(), "User not found", Toast.LENGTH_LONG).show();
                    }
                });
                recyclerAdapter.setData(users);
                recyclerAdapter.notifyDataSetChanged();
                recyclerAdapter.getFilter().filter(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {

                recyclerAdapter.getFilter().filter(s);
                return false;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }

    public UserListFragment() {
    }
}
