package client.ediancha.com.myview;

import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import client.ediancha.com.R;
import client.ediancha.com.util.Util;


/**
 * Created by dengmingzhi on 16/9/27.
 */

public class PopMessageTips {
    private String title;
    private String content;
    private String right;
    private String left;
    private PopupWindow popupWindow;

    public PopMessageTips(String title, String content, String right, String left) {
        this.content = content;
        this.right = right;
        this.left = left;
        this.title = title;
    }

    public PopMessageTips(String content, String right, String left) {
        this("提示", content, right, left);
    }


    public void showView(View parent) {
        show();
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
                    popupWindow.dismiss();
                    dismiss();
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
                    popupWindow.dismiss();
                    dismiss();
                    right();
                }
            });
        }
        popupWindow = new PopupWindow(view, FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setBackgroundDrawable(new ColorDrawable());
        popupWindow.setAnimationStyle(R.style.pop_message);
        popupWindow.showAtLocation(parent, Gravity.CENTER, 0, 0);
    }

    protected void left() {

    }

    protected void right() {

    }

    protected void show() {

    }

    protected void dismiss() {

    }


}
