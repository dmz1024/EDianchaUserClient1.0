package client.ediancha.com.myview;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.github.florent37.viewanimator.ViewAnimator;

import java.util.List;

import client.ediancha.com.R;
import client.ediancha.com.base.PopBaseView;

/**
 * Created by dengmingzhi on 2016/10/18.
 */

public class ChooseStringView extends PopBaseView {
    private List<String> list;

    public ChooseStringView(Context ctx, List<String> list) {
        super(ctx);
        this.list = list;
    }


    @Override
    protected int getGravity() {
        return Gravity.BOTTOM;
    }

    @Override
    protected View getView() {
        ListView lv_content = new ListView(ctx);
        lv_content.setBackgroundColor(ctx.getResources().getColor(R.color.colorfff));
        lv_content.setDivider(new ColorDrawable(ctx.getResources().getColor(R.color.colore1e1e1)));
        lv_content.setDividerHeight(1);
        lv_content.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return list.size() + 1;
            }

            @Override
            public Object getItem(int position) {
                return null;
            }

            @Override
            public long getItemId(int position) {
                return 0;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                TextView tv_content = (TextView) View.inflate(ctx, R.layout.item_textview, null);
                if (position + 1 == getCount()) {
                    tv_content.setText("取消");
                    tv_content.setTextColor(ctx.getResources().getColor(R.color.color333));
                } else {
                    tv_content.setTextColor(ctx.getResources().getColor(R.color.color666));
                    tv_content.setText(list.get(position));
                }
                return tv_content;
            }
        });

        lv_content.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                dismiss();
                if (position != list.size()) {

                    itemClick(position);
                }


            }
        });

        return lv_content;
    }

    protected void itemClick(int position) {

    }
}
