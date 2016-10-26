package client.ediancha.com.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import client.ediancha.com.R;
import client.ediancha.com.activity.AddressManagerActivity;
import client.ediancha.com.base.BaseViewHolder;
import client.ediancha.com.base.DescBaseActivity;
import client.ediancha.com.base.SingleBaseAdapter;
import client.ediancha.com.constant.Constant;
import client.ediancha.com.entity.Address;
import client.ediancha.com.entity.Like;
import client.ediancha.com.interfaces.OnResultListener;
import client.ediancha.com.myview.TextImage;
import client.ediancha.com.processor.AddressUtil;
import client.ediancha.com.processor.CollectionUtil;
import client.ediancha.com.util.MyToast;

/**
 * Created by dengmingzhi on 16/10/12.
 */

public class AddressAdapter extends SingleBaseAdapter<Address.Data> {
    private String type;
    private int address_default;

    public AddressAdapter(Context ctx, List<Address.Data> list) {
        super(ctx, list);
    }

    public AddressAdapter(List<Address.Data> list) {
        super(list);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new AddressViewHolder(View.inflate(ctx, R.layout.item_address, null));
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        AddressViewHolder mHolder = (AddressViewHolder) holder;
        Address.Data data = list.get(position);
        mHolder.tv_name.setText(data.name);
        mHolder.tv_tel.setText(data.tel);
        mHolder.tv_address.setText(data.address);
        if (data.is_default == 1) {
            address_default = position;
            mHolder.tv_type.setDrawable(R.mipmap.icon_a_selected);
            mHolder.tv_type.setText("默认地址");
            mHolder.tv_type.setTextColor(ctx.getResources().getColor(R.color.color51b338));
        } else {
            mHolder.tv_type.setDrawable(R.mipmap.icon_a_select);
            mHolder.tv_type.setText("设为默认");
            mHolder.tv_type.setTextColor(ctx.getResources().getColor(R.color.color333));
        }
    }

    public class AddressViewHolder extends BaseViewHolder {
        public TextView tv_name;
        public TextView tv_tel;
        public TextView tv_address;
        public TextImage tv_type;
        public TextImage tv_delete;
        public TextImage tv_edit;

        public AddressViewHolder(View itemView) {
            super(itemView);
            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            tv_tel = (TextView) itemView.findViewById(R.id.tv_tel);
            tv_address = (TextView) itemView.findViewById(R.id.tv_address);
            tv_type = (TextImage) itemView.findViewById(R.id.tv_type);
            tv_delete = (TextImage) itemView.findViewById(R.id.tv_delete);
            tv_edit = (TextImage) itemView.findViewById(R.id.tv_edit);
            tv_delete.setOnClickListener(this);
            tv_edit.setOnClickListener(this);
            tv_type.setOnClickListener(this);
        }

        @Override
        protected void itemOnclick(int id, final int layoutPosition) {
            switch (id) {
                case R.id.tv_delete:
                    AddressUtil.getInstance().setContext(ctx).setOnAddressManagerListener(new AddressUtil.OnAddressManagerListener() {
                        @Override
                        public void ok() {
                            remove(layoutPosition);
                            onRefresh();
                        }

                        @Override
                        public void faile(String msg) {
                            MyToast.showToast(msg);
                        }
                    }).deleteOrDefault("delete", list.get(layoutPosition).address_id, "删除地址");
                    break;
                case R.id.tv_edit:
                    onClick(layoutPosition);
                    break;
                case R.id.tv_type:
                    if (list.get(layoutPosition).is_default != 1) {
                        AddressUtil.getInstance().setContext(ctx).setOnAddressManagerListener(new AddressUtil.OnAddressManagerListener() {
                            @Override
                            public void ok() {
                                list.get(layoutPosition).is_default = 1;
                                list.get(address_default).is_default = 0;
                                notifyDataSetChanged();
                            }

                            @Override
                            public void faile(String msg) {
                                MyToast.showToast(msg);
                            }
                        }).deleteOrDefault("set_default", list.get(layoutPosition).address_id, "设置默认地址");
                    }
                    break;
            }
        }

        @Override
        protected void onClick(int layoutPosition) {
            Intent intent = new Intent(ctx, AddressManagerActivity.class);
            intent.putExtra("data", list.get(layoutPosition));
            ((Activity) ctx).startActivityForResult(intent, Constant.ADDRESS_ADD);
        }
    }


    protected void onRefresh() {

    }

}
