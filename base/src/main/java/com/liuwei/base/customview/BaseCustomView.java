package com.liuwei.base.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

// 基类 需要同时加入四个构造方法
public abstract class BaseCustomView<VIEWBINDING extends ViewDataBinding, DATA extends BaseCustomViewModel>
        extends LinearLayout implements IBaseCustomView<DATA>{

    protected VIEWBINDING viewbinding;
    protected DATA data;

    public BaseCustomView(Context context) {
        super(context);
        init();
    }

    public BaseCustomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public BaseCustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public BaseCustomView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    private void init(){
        LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        viewbinding = DataBindingUtil.inflate(layoutInflater, getLayoutID(), this, false);
        viewbinding.getRoot().setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                onRootViewClicked(view);
            }
        });
        addView(viewbinding.getRoot());
    }

    // 模板方法
    @Override
    public void setData(DATA data) {
        this.data = data;
        setDataToView(data);
        viewbinding.executePendingBindings();
    }

    // 工厂方法 建造型
    public abstract int getLayoutID();

    public abstract void onRootViewClicked(View view);

    protected  abstract void setDataToView(DATA data);
}
