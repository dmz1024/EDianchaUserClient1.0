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
import client.ediancha.com.entity.TeaPackage;

/**
 * Created by dengmingzhi on 16/10/12.
 */

public class TeaPackageAdapter extends SingleBaseAdapter<TeaPackage.Data> {

    public TeaPackageAdapter(Context ctx, List<TeaPackage.Data> list) {
        super(ctx, list);
    }

    public TeaPackageAdapter(List<TeaPackage.Data> list) {
        super(list);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TeaPackageViewHolder(View.inflate(ctx, R.layout.item_package, null));
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        TeaPackageViewHolder mHolder = (TeaPackageViewHolder) holder;
        TeaPackage.Data data = list.get(position);
        Glide.with(ctx).load(data.images.large).into(mHolder.iv_img);
        mHolder.tv_name.setText(data.title);

    }

    public static class TeaPackageViewHolder extends BaseViewHolder {
        public ImageView iv_img;
        public TextView tv_name;
        public TextView tv_content;
        public TextView tv_price;
        public TextView tv_old_price;



        public TeaPackageViewHolder(View itemView) {
            super(itemView);
            iv_img = (ImageView) itemView.findViewById(R.id.iv_img);
            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            tv_content = (TextView) itemView.findViewById(R.id.tv_content);
            tv_price = (TextView) itemView.findViewById(R.id.tv_price);
            tv_old_price = (TextView) itemView.findViewById(R.id.tv_old_price);

        }

    }
}
