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

public class ChooseImageAdapter extends SingleBaseAdapter<String> {
    private int maxCount;

    public ChooseImageAdapter(Context ctx, List<String> list, int maxCount) {
        super(ctx, list);
        this.maxCount = maxCount;
    }


    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new AddressViewHolder(View.inflate(ctx, R.layout.item_add_image, null));
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        AddressViewHolder mHolder = (AddressViewHolder) holder;
        if (list.size() < maxCount && position == list.size()) {
            Glide.with(ctx).load(R.mipmap.icon_add_image).into(mHolder.iv_img);
            mHolder.iv_delete.setVisibility(View.GONE);
        } else {
            Glide.with(ctx).load(list.get(position)).into(mHolder.iv_img);
            mHolder.iv_delete.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        int count = list.size();
        if (count < maxCount) {
            count = count + 1;
        }
        return count;
    }

    public class AddressViewHolder extends BaseViewHolder {
        public ImageView iv_img;
        public ImageView iv_delete;

        public AddressViewHolder(View itemView) {
            super(itemView);
            iv_img = (ImageView) itemView.findViewById(R.id.iv_img);
            iv_delete = (ImageView) itemView.findViewById(R.id.iv_delete);
            iv_delete.setOnClickListener(this);
        }

        @Override
        protected void onClick(int layoutPosition) {
            chooseImage();
        }

        @Override
        protected void itemOnclick(int id, int layoutPosition) {
            remove(layoutPosition);
        }
    }

    protected void chooseImage() {

    }

}
