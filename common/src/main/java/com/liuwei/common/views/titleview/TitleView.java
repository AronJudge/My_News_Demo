package com.liuwei.common.views.titleview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import androidx.databinding.DataBindingUtil;

import com.liuwei.base.customview.BaseCustomView;
import com.liuwei.base.customview.BaseCustomViewModel;
import com.liuwei.base.customview.IBaseCustomView;
import com.liuwei.common.R;
import com.liuwei.common.databinding.TitleViewBinding;
import com.liuwei.webview.WebviewActivity;

/**
 * View 只处理用户的即时交互；
 * ViewModel 只处理业务逻辑；
 * Model 只处理数据存储与获取。
 *
 *
 * 自定义view， View Holder —— View  满足单一职责原则
 * 所有View的相关操作都在View里面， 开闭原则
 * 1. 继承于view
 * 2. 继承于view group 需要自己布局
 * 3. 继承于现有布局 线性布局
 *
 * 为什么不继承相对布局？
 * 线性布局好调整
 *
 *  自定义view：构造 测量 画
 */
public class TitleView extends BaseCustomView<TitleViewBinding, TitleViewModel>  {
    public TitleView(Context context) {
        super(context);
    }
        /*
        自动生成
        viewBinding 只能省略find view byId  viewBinding{enable true}  不需要修改xml
        dataBinding 除了ViewBinding的功能还能绑定data， 需要修改xml
        <layout 标签开头>
        <data> 数据标签
        <import> 引入一些类
        <variable> 定义变量 和那些数据进行绑定
        <viewModel.cells>
    // 不同地方创建view， 参数不一样， 来源于代码只需要这一个就够了
    public TitleView(Context context) {
        super(context);
    }

/*     // 父类已经做了
        public void init() {
        // 简单工厂
        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        titleViewBinding = DataBindingUtil.inflate(inflater, R.layout.title_view,this,false);
        titleViewBinding.getRoot().setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                WebviewActivity.startCommonWeb(getContext(),"News", viewModel.jumpUrl+"");
            }
        });
        addView(titleViewBinding.getRoot());
    }*/

    @Override
    public int getLayoutID() {
        return R.layout.title_view;
    }

    @Override
    public void onRootViewClicked(View view) {
        WebviewActivity.startCommonWeb(getContext(),"News", data.jumpUrl+"");
    }

    @Override
    protected void setDataToView(TitleViewModel titleViewModel) {
        viewbinding.setViewModel(titleViewModel);
    }
}
