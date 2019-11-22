package com.appcent.flickr.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.appcent.flickr.Activity.PhotoDetailActivity;
import com.appcent.flickr.Entity.Photo;
import com.appcent.flickr.R;
import com.squareup.picasso.Picasso;
import java.util.List;

public class PostRecyclerAdapter extends RecyclerView.Adapter<BaseViewHolder> {
    private static final int VIEW_TYPE_LOADING = 0;
    private static final int VIEW_TYPE_NORMAL = 1;
    private boolean isLoaderVisible = false;

    private List<Photo> listPhoto;
    Context context;
    Activity activity;

    public PostRecyclerAdapter(List<Photo> postItems, Context context,Activity activity) {
        this.listPhoto = postItems;
        this.context = context;
        this.activity = activity;
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        switch (viewType) {
            case VIEW_TYPE_NORMAL:
                return new ViewHolder(
                        LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false));
            case VIEW_TYPE_LOADING:
                return new ProgressHolder(
                        LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loading, parent, false));
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        holder.onBind(position);
    }

    @Override
    public int getItemViewType(int position) {
        if (isLoaderVisible) {
            return position == listPhoto.size() - 2 ? VIEW_TYPE_LOADING : VIEW_TYPE_NORMAL;
        } else {
            return VIEW_TYPE_NORMAL;
        }
    }

    @Override
    public int getItemCount() { return listPhoto == null ? 0 : listPhoto.size(); }

    public void addItems(List<Photo> postItems) {
        listPhoto.addAll(postItems);
        notifyDataSetChanged();
    }

    public void addLoading() {
        isLoaderVisible = true;
        listPhoto.add(new Photo());
        notifyItemInserted(listPhoto.size() - 1);
    }

    public void removeLoading() {
        isLoaderVisible = false;
        int position = listPhoto.size() - 1;
        Photo item = getItem(position);
        if (item != null) {
            listPhoto.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void clear() {
        listPhoto.clear();
        notifyDataSetChanged();
    }

    Photo getItem(int position) {
        return listPhoto.get(position);
    }

    public class ViewHolder extends BaseViewHolder {
        ImageView img1;
String photoURL;
        ViewHolder(View itemView) {
            super(itemView);
            img1 = itemView.findViewById(R.id.img1);
        }

        protected void clear() {

        }

        public void onBind(int position) {
            super.onBind(position);
            Photo item = listPhoto.get(position);
            photoURL = "https://farm"+ item.getFarm() + ".staticflickr.com/"+ item.getServer()+"/"+item.getId()+"_"+item.getSecret()+".jpg";
            Picasso.with(context).load(photoURL).into(img1);

            img1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent myIntent = new Intent(activity, PhotoDetailActivity.class);
                    myIntent.putExtra("photoURL", photoURL);
                    activity.startActivity(myIntent);
                }
            });
        }
    }

    public class ProgressHolder extends BaseViewHolder {
        ImageView img1;
        ProgressHolder(View itemView) {
            super(itemView);
            img1 = itemView.findViewById(R.id.img1);
        }

        @Override
        protected void clear() {
        }
    }
}