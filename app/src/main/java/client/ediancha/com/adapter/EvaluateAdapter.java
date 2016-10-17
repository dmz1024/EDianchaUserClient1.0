package client.ediancha.com.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import client.ediancha.com.R;
import client.ediancha.com.base.BaseViewHolder;
import client.ediancha.com.base.SingleBaseAdapter;
import client.ediancha.com.constant.Constant;
import client.ediancha.com.entity.TeaEventDesc;
import client.ediancha.com.myview.GlideCircleTransform;
import client.ediancha.com.myview.RatingBar;
import client.ediancha.com.myview.TextImage;

/**
 * Created by dengmingzhi on 16/10/12.
 */

public class EvaluateAdapter extends SingleBaseAdapter<TeaEventDesc.Event> {
    private boolean haveImage;

    public EvaluateAdapter(Context ctx, List<TeaEventDesc.Event> list) {
        super(ctx, list);
    }

    public EvaluateAdapter(List<TeaEventDesc.Event> list) {
        super(list);
    }

    public EvaluateAdapter(Context context, List<TeaEventDesc.Event> totalList, boolean haveImage) {
        super(context, totalList);
        this.haveImage = haveImage;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new EvaluateViewHolder(View.inflate(ctx, R.layout.item_evaluate, null));
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        EvaluateViewHolder mHolder = (EvaluateViewHolder) holder;
        TeaEventDesc.Event data = list.get(position);
        Glide.with(ctx).load(Constant.IMAGE).transform(new GlideCircleTransform(ctx)).into(mHolder.iv_head);
        if(haveImage){
            List<String> urls = new ArrayList<>();
            for (int i = 0; i < 4; i++) {
                urls.add(Constant.IMAGE);
            }

            GridLayoutManager manage = new GridLayoutManager(ctx, 4);
            mHolder.rv_image.setLayoutManager(manage);
            mHolder.rv_image.setAdapter(new ImageAdapter(ctx, urls));
        }


    }

    public static class EvaluateViewHolder extends BaseViewHolder {
        public ImageView iv_head;
        public TextView tv_name;
        public TextView tv_time;
        public TextView tv_ratingbar;
        public TextView tv_price;
        public TextView tv_content;
        public RecyclerView rv_image;
        public RatingBar ratingbar;


        public EvaluateViewHolder(View itemView) {
            super(itemView);
            iv_head = (ImageView) itemView.findViewById(R.id.iv_head);
            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            tv_time = (TextView) itemView.findViewById(R.id.tv_time);
            tv_ratingbar = (TextView) itemView.findViewById(R.id.tv_ratingbar);
            tv_price = (TextView) itemView.findViewById(R.id.tv_price);
            tv_content = (TextView) itemView.findViewById(R.id.tv_content);
            rv_image = (RecyclerView) itemView.findViewById(R.id.rv_image);
            ratingbar = (RatingBar) itemView.findViewById(R.id.ratingbar);
        }

    }
}
