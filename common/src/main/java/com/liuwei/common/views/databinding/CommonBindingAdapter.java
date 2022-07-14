package com.liuwei.common.views.databinding;

import android.text.TextUtils;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class CommonBindingAdapter {

    // 全局 不用new
    @BindingAdapter("loadImageUrl")
    public static void loadImageUrl(ImageView imageView, String picUrl) {
        if(!TextUtils.isEmpty(picUrl)) {
            Glide.with(imageView.getContext())
                    .load(picUrl)
                    .transition(withCrossFade())
                    .into(imageView);
        }
    }

}
