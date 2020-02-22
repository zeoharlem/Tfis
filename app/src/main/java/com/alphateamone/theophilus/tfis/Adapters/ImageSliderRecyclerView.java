package com.alphateamone.theophilus.tfis.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.alphateamone.theophilus.tfis.Model.ImgCarousel;
import com.alphateamone.theophilus.tfis.R;

import java.util.ArrayList;

public class ImageSliderRecyclerView extends RecyclerView.Adapter<ImageSliderRecyclerView.ViewHolder> {

    private Context context;
    private ArrayList<ImgCarousel> imgCarouselArrayList;

    public ImageSliderRecyclerView(Context context, ArrayList<ImgCarousel> imgCarouselArrayList) {
        this.context                = context;
        this.imgCarouselArrayList   = imgCarouselArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_image_carousel, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.myImageView.setImageResource(imgCarouselArrayList.get(i).getImage());
    }

    @Override
    public int getItemCount() {
        return imgCarouselArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView myImageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            myImageView = itemView.findViewById(R.id.imgCarousel);
        }
    }
}
