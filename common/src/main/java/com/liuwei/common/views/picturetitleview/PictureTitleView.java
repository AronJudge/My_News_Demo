package com.liuwei.common.views.picturetitleview;

import android.content.Context;
import android.view.View;

import com.liuwei.base.customview.BaseCustomView;
import com.liuwei.common.R;
import com.liuwei.common.databinding.PictureTitleViewBinding;
import com.liuwei.webview.WebviewActivity;



public class PictureTitleView extends BaseCustomView<PictureTitleViewBinding, PictureTitleViewModel> {
    public PictureTitleView(Context context) {
        super(context);
    }

/*    public void init() {
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
    }*/

    @Override
    public int getLayoutID() {
        return R.layout.picture_title_view;
    }

    @Override
    public void onRootViewClicked(View view) {
        WebviewActivity.startCommonWeb(getContext(),"News", data.jumpUrl+"");
    }

    @Override
    protected void setDataToView(PictureTitleViewModel pictureTitleViewModel) {
        viewbinding.setViewModel(pictureTitleViewModel);
    }


}
