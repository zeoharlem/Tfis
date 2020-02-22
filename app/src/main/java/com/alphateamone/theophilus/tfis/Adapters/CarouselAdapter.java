package com.alphateamone.theophilus.tfis.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.alphateamone.theophilus.tfis.Model.ImgCarousel;
import com.alphateamone.theophilus.tfis.R;

import java.util.List;

public class CarouselAdapter extends PagerAdapter {

    private List<ImgCarousel> imgCarousels;
    private LayoutInflater inflater;
    private Context context;

    public CarouselAdapter(List<ImgCarousel> imgCarousels, Context context) {
        this.imgCarousels   = imgCarousels;
        this.context        = context;
    }

    @Override
    public int getCount() {
        return imgCarousels.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view.equals(o);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        inflater    = LayoutInflater.from(context);
        View view   = inflater.inflate(R.layout.item_image_carousel, container, false);
        //return super.instantiateItem(container, position);
        ImageView mImageView;
        mImageView  = view.findViewById(R.id.imgCarousel);
        mImageView.setImageResource(imgCarousels.get(position).getImage());
        container.addView(view, 0);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        //super.destroyItem(container, position, object);
        container.removeView((View) object);
    }
}
