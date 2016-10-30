package client.ediancha.com.myview;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import client.ediancha.com.R;
import client.ediancha.com.util.MyToast;


/**
 * Created by dengmingzhi on 16/9/26.
 */

public class AddAndSub extends RelativeLayout implements View.OnClickListener {
    private ImageView iv_jia;
    private ImageView iv_jian;
    private TextView tv_count;
    private int minCount = 0;
    private int maxCount = 0;
    private OnAddAndSubListener onAddAndSubListener;

    public AddAndSub(Context context) {
        super(context);
        initView();
    }

    public AddAndSub(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public AddAndSub(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    public AddAndSub setCount(int initCount) {
        if (initCount > maxCount) {
            initCount = maxCount;
        } else if (initCount < minCount) {
            initCount = minCount;
        }
        tv_count.setText(initCount + "");
        return this;
    }

    public AddAndSub setCount(int minCount, int maxCount) {
        this.minCount = minCount;
        this.maxCount = maxCount;
        return this;
    }

    private void initView() {
        View.inflate(getContext(), R.layout.add_and_sub, this);
        iv_jia = (ImageView) findViewById(R.id.iv_jia);
        iv_jian = (ImageView) findViewById(R.id.iv_jian);
        tv_count = (TextView) findViewById(R.id.tv_count);
        iv_jia.setOnClickListener(this);
        iv_jian.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_jia:
                jia();
                break;
            case R.id.iv_jian:
                jian();
                break;
        }
    }

    private void jian() {
        int currentCount = Integer.parseInt(tv_count.getText().toString());
        int count = currentCount - 1;
        if (count >= minCount) {
            tv_count.setText(count + "");
            if (onAddAndSubListener != null) {
                onAddAndSubListener.sub();
                onAddAndSubListener.content(count);
            }
        }
    }


    public String getCurrentContent() {
        return tv_count.getText().toString();
    }

    public int getCurrent() {
        return Integer.parseInt(tv_count.getText().toString());
    }

    private void jia() {
        int count = Integer.parseInt(tv_count.getText().toString()) + 1;
        if (count <= maxCount) {
            tv_count.setText(count + "");
            if (onAddAndSubListener != null) {
                onAddAndSubListener.add();
                onAddAndSubListener.content(count);
            }
        }
    }

    public AddAndSub setOnAddAndSubListener(OnAddAndSubListener onAddAndSubListener) {
        this.onAddAndSubListener = onAddAndSubListener;
        return this;

    }

    public interface OnAddAndSubListener {
        void add();

        void sub();

        void content(int count);
    }
}
