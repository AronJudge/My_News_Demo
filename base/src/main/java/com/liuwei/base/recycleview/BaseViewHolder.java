package com.liuwei.base.recycleview;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.liuwei.base.customview.IBaseCustomView;
import com.liuwei.base.customview.BaseCustomViewModel;

public class BaseViewHolder extends RecyclerView.ViewHolder {
    private IBaseCustomView itemView;
    public BaseViewHolder(@NonNull IBaseCustomView itemView) {
        super((View)itemView);
        this.itemView = itemView;
    }

    public void binding(BaseCustomViewModel viewModel) {
        this.itemView.setData(viewModel);
    }
}
