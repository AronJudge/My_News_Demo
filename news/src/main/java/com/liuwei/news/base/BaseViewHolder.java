package com.liuwei.news.base;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class BaseViewHolder extends RecyclerView.ViewHolder {
    private  BaseCustomView itemView;
    public BaseViewHolder(@NonNull BaseCustomView itemView) {
        super((View)itemView);
        this.itemView = itemView;
    }

    public void binding(BaseCustomViewModel viewModel) {
        this.itemView.setData(viewModel);
    }
}
