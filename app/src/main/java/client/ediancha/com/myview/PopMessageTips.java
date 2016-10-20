package client.ediancha.com.myview;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.github.florent37.viewanimator.ViewAnimator;

import client.ediancha.com.R;
import client.ediancha.com.util.Util;


/**
 * Created by dengmingzhi on 16/9/27.
 */

public class PopMessageTips implements PopupWindow.OnDismissListener {
    private String title;
    private String content;
    private String right;
    private String left;
    private PopupWindow popupWindow;
    private Context ctx;

    public PopMessageTips(String title, String content, String right, String left) {
        this.content = content;
        this.right = right;
        this.left = left;
        this.title = title;
    }

    public PopMessageTips(String content, String right, String left) {
        this("提示", content, right, left);
    }


    public void showView(Context ctx) {
        show();
        this.ctx = ctx;
        View view = View.inflate(Util.getApplication(), R.layout.pop_message, null);
        TextView tv_title = (TextView) view.findViewById(R.id.tv_title);
        TextView tv_content = (TextView) view.findViewById(R.id.tv_content);
        TextView tv_left = (TextView) view.findViewById(R.id.tv_left);
        TextView tv_right = (TextView) view.findViewById(R.id.tv_right);
        tv_title.setText(title);
        tv_content.setText(content);
        if (TextUtils.isEmpty(left)) {
            tv_left.setVisibility(View.INVISIBLE);
        } else {
            tv_left.setText(left);
            tv_left.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    left();
                }
            });
        }
        if (TextUtils.isEmpty(right)) {
            tv_right.setVisibility(View.INVISIBLE);
        } else {
            tv_right.setText(right);
            tv_right.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    right();
                }
            });
        }
        popupWindow = new PopupWindow(view, Util.getWidth()-70, FrameLayout.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setBackgroundDrawable(new ColorDrawable());
        popupWindow.setAnimationStyle(R.style.pop_message);
        popupWindow.setOnDismissListener(this);
        popupWindow.showAtLocation(((Activity) ctx).findViewById(android.R.id.content), Gravity.CENTER, 0, 0);

        ViewAnimator.animate(((Activity) ctx).findViewById(android.R.id.content)).alpha(1f, 0.3f).duration(500).start();
    }

    protected void left() {
        popupWindow.dismiss();
    }

    protected void right() {
        popupWindow.dismiss();
    }

    protected void show() {

    }

    public void dismiss(){
        popupWindow.dismiss();
    }


    @Override
    public void onDismiss() {
        ViewAnimator.animate(((Activity) ctx).findViewById(android.R.id.content)).alpha(1f, 1f).duration(600).start();
    }
}
