package client.ediancha.com.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.List;

import client.ediancha.com.R;
import client.ediancha.com.base.BaseViewHolder;
import client.ediancha.com.base.SingleBaseAdapter;

/**
 * Created by dengmingzhi on 16/10/12.
 */

public class ImageAdapter extends SingleBaseAdapter<String> {

    public ImageAdapter(Context ctx, List<String> list) {
        super(ctx, list);
    }

    public ImageAdapter(List<String> list) {
        super(list);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new EvaluateViewHolder(View.inflate(ctx, R.layout.item_image, null));
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        EvaluateViewHolder mHolder = (EvaluateViewHolder) holder;
        Glide.with(ctx).load(list.get(position)).into(mHolder.iv_img);

    }

    public static class EvaluateViewHolder extends BaseViewHolder {
        public ImageView iv_img;

        public EvaluateViewHolder(View itemView) {
            super(itemView);
            iv_img = (ImageView) itemView.findViewById(R.id.iv_img);
        }

    }
}
