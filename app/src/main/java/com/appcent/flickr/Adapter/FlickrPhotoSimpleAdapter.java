package com.appcent.flickr.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import com.appcent.flickr.Class.Photo;
import com.appcent.flickr.R;

import java.util.ArrayList;

public class FlickrPhotoSimpleAdapter extends RecyclerView.Adapter<FlickrPhotoSimpleAdapter.MyViewHolder> {

    ArrayList<Photo> mDataList;
    LayoutInflater inflater;

    public FlickrPhotoSimpleAdapter(Context context, ArrayList<Photo> data) {

        //inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater = LayoutInflater.from(context);
        this.mDataList = data;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View v = inflater.inflate(R.layout.list_item, parent, false);
        MyViewHolder holder = new MyViewHolder(v);
        return holder;

    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Photo tiklanilanManzara = mDataList.get(position);
        holder.setData(tiklanilanManzara, position);

    }

    public void deleteItem(int position) {
        mDataList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, mDataList.size());

        //Aşağıdaki method yukarıdaki iki satır kodun yaptığı işi yapar,
        //Ama çok kaynak tüketilir. ve de animasyonları göremeyiz.
        //notifyDataSetChanged();
    }

    public void addItem(int position, Photo kopyalanacakManzara) {
        mDataList.add(position, kopyalanacakManzara);
        notifyItemInserted(position);
        notifyItemRangeChanged(position, mDataList.size());

        //Aşağıdaki method yukarıdaki iki satır kodun yaptığı işi yapar,
        //Ama çok kaynak tüketilir. ve de animasyonları göremeyiz.
        //notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView mManzaraBaslik, mManzaraAciklama;
        ImageView mManzaraResmi, mSilResmi, mKopyalaResmi;
        int tiklanilanOgeninPositionDegeri = 0;
        Photo kopyalanacakManzara;


        public MyViewHolder(View itemView) {
            super(itemView);

            mManzaraBaslik = (TextView) itemView.findViewById(R.id.tvManzaraBaslik);
            mManzaraAciklama = (TextView) itemView.findViewById(R.id.tvManzaraTanim);
            mManzaraResmi = (ImageView) itemView.findViewById(R.id.imgManzara);
            mSilResmi = (ImageView) itemView.findViewById(R.id.imgSil);
            mKopyalaResmi = (ImageView) itemView.findViewById(R.id.imgKopyala);

            mSilResmi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("EMRE", "Silmeden önce position:" + tiklanilanOgeninPositionDegeri + " ElemanSayısı " + mDataList.size());
                    deleteItem(tiklanilanOgeninPositionDegeri);
                    Log.d("EMRE", "Silmeden sonra position:" + tiklanilanOgeninPositionDegeri + " ElemanSayısı " + mDataList.size());
                }
            });

            mKopyalaResmi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("EMRE", "Eklenmeden önce position: " + tiklanilanOgeninPositionDegeri + " ElemanSayısı " + mDataList.size());
                    addItem(tiklanilanOgeninPositionDegeri, kopyalanacakManzara);
                    Log.d("EMRE", "Eklenmeden sonra position: " + tiklanilanOgeninPositionDegeri + " ElemanSayısı " + mDataList.size());
                }
            });
        }

        public void setData(Photo tiklanilanManzara, int position) {
            this.mManzaraBaslik.setText(tiklanilanManzara.getBaslik());
            this.mManzaraAciklama.setText(tiklanilanManzara.getTanim());
            this.mManzaraResmi.setImageResource(tiklanilanManzara.getImageID());
            this.tiklanilanOgeninPositionDegeri = position;
            this.kopyalanacakManzara = tiklanilanManzara;
        }
    }
}
