package com.appcent.flickr.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;
import com.appcent.flickr.Entity.FlickrData;
import com.appcent.flickr.Entity.Photo;
import com.appcent.flickr.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class FlickrAdapter extends RecyclerView.Adapter<FlickrAdapter.ViewHolder> {

    private List<Photo> listPhoto;
    Context context;

    public FlickrAdapter(Context context, FlickrData flickrData) {
        this.context = context;
        this.listPhoto = flickrData.getPhotos().getPhoto();;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.itemView.setTag(listPhoto.get(position));
        Photo pu = listPhoto.get(position);

        Picasso.with(context).load("https://farm"+ listPhoto.get(position).getFarm() + ".staticflickr.com/"+listPhoto.get(position).getServer()+"/"+listPhoto.get(position).getId()+"_"+listPhoto.get(position).getSecret()+".jpg").into(holder.img1);
    }

    @Override
    public int getItemCount() {
        return listPhoto.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public ImageView img1;

        public ViewHolder(View itemView) {
            super(itemView);

            img1 = itemView.findViewById(R.id.img1);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });

        }
    }

}
