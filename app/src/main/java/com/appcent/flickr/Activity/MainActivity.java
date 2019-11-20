package com.appcent.flickr.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.appcent.flickr.Adapter.FlickrAdapter;
import com.appcent.flickr.Entity.FlickrData;
import com.appcent.flickr.R;
import com.appcent.flickr.RestApi.RestApiClient;
import com.appcent.flickr.RestApi.RestInterface;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    FlickrAdapter flickrAdapter;

    RecyclerView recyclerView;
    RestInterface restInterface;
    FlickrData repoList = new FlickrData();
    Context context;
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fab = findViewById(R.id.fab);
        recyclerView= findViewById(R.id.recyclerview);
        context = this;

        getList();

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getList();
            }
        });

    }

    private void getList(){
        restInterface= RestApiClient.getClient().create(RestInterface.class);
        Call<FlickrData> call=restInterface.getRepo();

        call.enqueue(new Callback<FlickrData>() {
            @Override
            public void onResponse(Call<FlickrData> call, Response<FlickrData> response) {
                FlickrData getRepo;
                getRepo = response.body();

                flickrAdapter = new FlickrAdapter(context, getRepo);
                recyclerView.setAdapter(flickrAdapter);
            }
            @Override
            public void onFailure(Call<FlickrData> call, Throwable t) {
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.layout_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id=item.getItemId();

        switch (id){
            case R.id.linearViewHorizontal:
                LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
                linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                recyclerView.setLayoutManager(linearLayoutManager);
                break;

            case R.id.linearViewVertical:
                LinearLayoutManager linearLayoutManagerVertical=new LinearLayoutManager(this);
                linearLayoutManagerVertical.setOrientation(LinearLayoutManager.VERTICAL);
                recyclerView.setLayoutManager(linearLayoutManagerVertical);
                break;

            case R.id.gridView:
                GridLayoutManager mGridLayoutManager = new GridLayoutManager(this, 3); // (Context context, int spanCount)
                recyclerView.setLayoutManager(mGridLayoutManager);
                break;
            case R.id.staggeredViewHorizontal:
                StaggeredGridLayoutManager mStaggeredHorizontalLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.HORIZONTAL); // (int spanCount, int orientation)
                recyclerView.setLayoutManager(mStaggeredHorizontalLayoutManager);
                break;
            case R.id.staggeredViewVertical:
                StaggeredGridLayoutManager mStaggeredVerticalLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL); // (int spanCount, int orientation)
                recyclerView.setLayoutManager(mStaggeredVerticalLayoutManager);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}