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

/**
 * Created by dengmingzhi on 2016/10/18.
 */

public class ChooseStringView implements PopupWindow.OnDismissListener {
    private Context ctx;
    private List<String> list;
    private PopupWindow popupWindow;

    public ChooseStringView(Context ctx, List<String> list) {
        this.ctx = ctx;
        this.list = list;
    }

    public void creatPop() {
        popupWindow = new PopupWindow((getView()), FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setBackgroundDrawable(new ColorDrawable(ctx.getResources().getColor(R.color.colorfff)));
        popupWindow.setOnDismissListener(this);
        popupWindow.setAnimationStyle(getAnimation());
        popupWindow.showAtLocation(((Activity) ctx).findViewById(android.R.id.content), Gravity.BOTTOM, 0, 0);
    }

    protected int getAnimation() {
        return R.style.popwin_anim_style;
    }

    protected View getView() {
        ListView lv_content = new ListView(ctx);
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
                if (position != list.size()) {
                    itemClick(position);
                }
                popupWindow.dismiss();

            }
        });


        ViewAnimator.animate(((Activity) ctx).findViewById(android.R.id.content)).alpha(1f, 0.3f).duration(500).start();

        return lv_content;
    }

    protected void itemClick(int position) {

    }


    @Override
    public void onDismiss() {
        ViewAnimator.animate(((Activity) ctx).findViewById(android.R.id.content)).alpha(1f, 1f).duration(600).start();
    }
}
