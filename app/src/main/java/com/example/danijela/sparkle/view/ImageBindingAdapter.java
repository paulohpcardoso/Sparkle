package com.example.danijela.sparkle.view;

import android.databinding.BindingAdapter;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.widget.ImageView;

import com.example.danijela.sparkle.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

/**
 * Created by danijela on 12/22/16.
 */

public class ImageBindingAdapter {

    //TODO: check if I should define this only once in project
    @BindingAdapter("app:rounded_src")
    public static void loadRoundedImage(final ImageView view, String imageUrl) {
        Picasso.with(view.getContext())
                .load(imageUrl).resize(200, 200)
                .placeholder(R.drawable.loading_image)
                .into(view, new Callback() {
                    @Override
                    public void onSuccess() {
                        Bitmap imageBitmap = ((BitmapDrawable) view.getDrawable()).getBitmap();
                        RoundedBitmapDrawable imageDrawable = RoundedBitmapDrawableFactory.create(view.getResources(), imageBitmap);
                        imageDrawable.setCircular(true);
                        imageDrawable.setCornerRadius(Math.max(imageBitmap.getWidth(), imageBitmap.getHeight()) / 2.0f);
                        view.setImageDrawable(imageDrawable);
                    }

                    @Override
                    public void onError() {

                    }
                });
    }

    @BindingAdapter("android:src")
    public static void loadImage(final ImageView view, String imageUrl) {
        Picasso.with(view.getContext())
                .load(imageUrl)
                .placeholder(R.drawable.loading_image)
                .error(R.drawable.error_image)
                .into(view);
    }
}
