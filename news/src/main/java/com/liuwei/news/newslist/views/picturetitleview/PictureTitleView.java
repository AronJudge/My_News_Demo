package com.liuwei.news.newslist.views.picturetitleview;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.databinding.BindingAdapter;
import androidx.databinding.DataBindingUtil;

import com.bumptech.glide.Glide;
import com.liuwei.news.R;
import com.liuwei.news.databinding.PictureTitleViewBinding;
import com.liuwei.news.base.BaseCustomView;
import com.liuwei.webview.WebviewActivity;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class PictureTitleView extends LinearLayout implements BaseCustomView<PictureTitleViewModel> {
    public PictureTitleView(Context context) {
        super(context);
        init();
    }

    PictureTitleViewModel viewModel;
    PictureTitleViewBinding pictureTitleViewBinding;

    public void init() {
        // 简单工厂
        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        pictureTitleViewBinding = DataBindingUtil.inflate(inflater, R.layout.picture_title_view,this,false);
        pictureTitleViewBinding.getRoot().setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                WebviewActivity.startCommonWeb(getContext(),"News", viewModel.jumpUrl+"");
            }
        });
        addView(pictureTitleViewBinding.getRoot());
    }

    @Override
    public void setData(PictureTitleViewModel data) {
        pictureTitleViewBinding.setViewModel(data);
        pictureTitleViewBinding.executePendingBindings();
        this.viewModel = data;
    }

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
