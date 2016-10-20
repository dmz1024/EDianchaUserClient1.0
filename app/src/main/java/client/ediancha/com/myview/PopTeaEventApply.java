package client.ediancha.com.myview;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.github.florent37.viewanimator.ViewAnimator;

import java.util.List;

import client.ediancha.com.R;
import client.ediancha.com.util.Util;

/**
 * Created by dengmingzhi on 2016/10/18.
 */

public class PopTeaEventApply implements PopupWindow.OnDismissListener {
    private Context ctx;
    private PopupWindow popupWindow;

    public PopTeaEventApply(Context ctx) {
        this.ctx = ctx;
    }

    public void creatPop() {
        popupWindow = new PopupWindow((getView()), Util.getWidth() - 70, FrameLayout.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setBackgroundDrawable(new ColorDrawable());
        popupWindow.setOnDismissListener(this);
        popupWindow.setAnimationStyle(getAnimation());
        popupWindow.showAtLocation(((Activity) ctx).findViewById(android.R.id.content), Gravity.CENTER, 0, 0);
    }

    protected int getAnimation() {
        return R.style.pop_message;
    }

    protected View getView() {
        View view = View.inflate(ctx, R.layout.pop_tea_event_apply, null);
        final EditText et_tel = (EditText) view.findViewById(R.id.et_tel);
        final EditText et_name = (EditText) view.findViewById(R.id.et_name);
        final Button bt_apply = (Button) view.findViewById(R.id.bt_apply);
        et_tel.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(et_tel.getText().toString().length()==11 && et_name.getText().toString().length()>0){
                    bt_apply.setAlpha(1f);
                    bt_apply.setEnabled(true);
                }else {
                    bt_apply.setAlpha(0.2f);
                    bt_apply.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        et_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(et_tel.getText().toString().length()==11 && et_name.getText().toString().length()>0){
                    bt_apply.setAlpha(1f);
                    bt_apply.setEnabled(true);
                }else {
                    bt_apply.setAlpha(0.2f);
                    bt_apply.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });



        bt_apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClick(et_tel.getText().toString(), et_name.getText().toString());
            }
        });
        ViewAnimator.animate(((Activity) ctx).findViewById(android.R.id.content)).alpha(1f, 0.3f).duration(500).start();

        return view;
    }

    protected void itemClick(String tel, String name) {
        popupWindow.dismiss();
    }

    public void dismiss() {
        popupWindow.dismiss();
    }


    @Override
    public void onDismiss() {
        ViewAnimator.animate(((Activity) ctx).findViewById(android.R.id.content)).alpha(1f, 1f).duration(600).start();
    }
}
