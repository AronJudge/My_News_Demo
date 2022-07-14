package com.liuwei.news.newslist;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.liuwei.base.customview.BaseCustomViewModel;

import com.liuwei.base.recycleview.BaseViewHolder;
import com.liuwei.common.views.picturetitleview.PictureTitleView;
import com.liuwei.common.views.picturetitleview.PictureTitleViewModel;
import com.liuwei.common.views.titleview.TitleView;

import java.util.List;

public class NewsListRecyclerViewAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private final int VIEW_TYPE_PICTURE_TITLE = 1;
    private final int VIEW_TYPE_TITLE = 2;
    private List<BaseCustomViewModel> mItems;

/*    private Context mContext;
    NewsListRecyclerViewAdapter(Context context) {
        mContext = context;
    }*/

    void setData(List<BaseCustomViewModel> items) {
        mItems = items;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mItems != null) {
            return mItems.size();
        }
        return 0;
    }

    @Override
    public int getItemViewType(int position) {
        if (mItems != null && mItems.get(position) instanceof PictureTitleViewModel) {
            return VIEW_TYPE_PICTURE_TITLE;
        }
        return VIEW_TYPE_TITLE;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_PICTURE_TITLE) {
            return new BaseViewHolder(new PictureTitleView(parent.getContext()));
        } else if (viewType == VIEW_TYPE_TITLE) {
            return new BaseViewHolder(new TitleView(parent.getContext()));
        }

        return null;
    }

/*    private class PictureTitleViewHolder extends RecyclerView.ViewHolder {
        public TextView titleTextView;
        public AppCompatImageView picutureImageView;

        public PictureTitleViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.item_title);
            picutureImageView = itemView.findViewById(R.id.item_image);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    WebviewActivity.startCommonWeb(mContext, "News", v.getTag()+"");
                }
            });
        }
    }*/


/*    private class TitleViewHolder extends RecyclerView.ViewHolder {
        public TextView titleTextView;

        public TitleViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.item_title);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    WebviewActivity.startCommonWeb(mContext, "News", v.getTag()+"");
                }
            });
        }
    }*/
    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        holder.binding(mItems.get(position));
    }
}
