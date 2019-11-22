package com.appcent.flickr.Activity;

import android.content.Context;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.appcent.flickr.Adapter.PaginationListener;
import com.appcent.flickr.Adapter.PostRecyclerAdapter;
import com.appcent.flickr.Entity.FlickrData;
import com.appcent.flickr.Entity.Photo;
import com.appcent.flickr.R;
import com.appcent.flickr.RestApi.RestApiClient;
import com.appcent.flickr.RestApi.RestInterface;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.appcent.flickr.Adapter.PaginationListener.PAGE_START;

public class MainActivity extends AppCompatActivity
        implements SwipeRefreshLayout.OnRefreshListener {
    private static final String TAG = "MainActivity";
    RecyclerView mRecyclerView;
    SwipeRefreshLayout swipeRefresh;
    RestInterface restInterface;
    private PostRecyclerAdapter adapter;
    private int currentPage = PAGE_START;
    private boolean isLoading = false;
    Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        swipeRefresh = findViewById(R.id.swipeRefresh);
        mRecyclerView= findViewById(R.id.recyclerview);
        context = this;

        swipeRefresh.setOnRefreshListener(this);
        swipeRefresh.setColorSchemeResources(android.R.color.holo_blue_bright, android.R.color.holo_green_light, android.R.color.holo_orange_light, android.R.color.holo_red_light);
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);

        adapter = new PostRecyclerAdapter(new ArrayList<>(),this,this);
        mRecyclerView.setAdapter(adapter);
        doApiCall();

        /**
         * add scroll listener while user reach in bottom load more will call
         */
        mRecyclerView.addOnScrollListener(new PaginationListener(layoutManager) {
            @Override
            protected void loadMoreItems() {
                isLoading = true;
                currentPage++;
                doApiCall();
            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }
        });
    }

    private void doApiCall() {

        final ArrayList<Photo> items = new ArrayList<>();
        restInterface= RestApiClient.getClient().create(RestInterface.class);

        Call<FlickrData> call=restInterface.getRepo();

        call.enqueue(new Callback<FlickrData>() {
            @Override
            public void onResponse(Call<FlickrData> call, Response<FlickrData> response) {
                FlickrData getRepo = response.body();
                items.addAll(getRepo.getPhotos().getPhoto());

                if (currentPage != PAGE_START) adapter.removeLoading();
                adapter.addItems(items);

                swipeRefresh.setRefreshing(false);

                adapter.addLoading();
                isLoading = false;
            }
            @Override
            public void onFailure(Call<FlickrData> call, Throwable t) {
            }
        });
    }

    @Override
    public void onRefresh() {
        currentPage = PAGE_START;
        adapter.clear();
        doApiCall();
    }
}