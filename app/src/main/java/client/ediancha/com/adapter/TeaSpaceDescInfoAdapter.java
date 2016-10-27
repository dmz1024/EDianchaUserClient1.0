package client.ediancha.com.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import client.ediancha.com.R;
import client.ediancha.com.base.BaseViewHolder;
import client.ediancha.com.base.SingleBaseAdapter;
import client.ediancha.com.entity.TeaSpaceDesc;
import client.ediancha.com.util.GlideUtil;

/**
 * Created by dengmingzhi on 16/10/12.
 */

public class TeaSpaceDescInfoAdapter extends SingleBaseAdapter<TeaSpaceDesc.Event> {

    public TeaSpaceDescInfoAdapter(Context ctx, List<TeaSpaceDesc.Event> list) {
        super(ctx, list);
    }

    public TeaSpaceDescInfoAdapter(List<TeaSpaceDesc.Event> list) {
        super(list);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TeaSpaceDescInfoViewHolder(View.inflate(ctx, R.layout.item_tea_space_desc_info, null));
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        TeaSpaceDescInfoViewHolder mHolder = (TeaSpaceDescInfoViewHolder) holder;
        TeaSpaceDesc.Event data = list.get(position);
        GlideUtil.GlideErrAndOc(ctx, data.image, mHolder.iv_img);
        mHolder.tv_title.setText(data.title);

    }

    public static class TeaSpaceDescInfoViewHolder extends BaseViewHolder {
        public ImageView iv_img;
        public TextView tv_title;


        public TeaSpaceDescInfoViewHolder(View itemView) {
            super(itemView);
            iv_img = (ImageView) itemView.findViewById(R.id.iv_img);
            tv_title = (TextView) itemView.findViewById(R.id.tv_title);
        }

    }
}
