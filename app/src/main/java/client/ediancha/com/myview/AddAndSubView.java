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

public class AddAndSubView extends RelativeLayout implements View.OnClickListener {
    private ImageView iv_jia;
    private ImageView iv_jian;
    private ImageView iv_jian_move;
    private TextView tv_count;
    private int minCount = 0;
    private int maxCount = 0;
    private String tipsContent;
    private OnAddAndSubListener onAddAndSubListener;
    private boolean isCanChange = true;
    private String tipCanChange;

    public AddAndSubView(Context context) {
        super(context);
        initView();
    }

    public AddAndSubView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public AddAndSubView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    public void setCurrentCount(int initCount) {
        tv_count.setText(initCount + "");
        if (initCount > minCount) {
            iv_jian.setVisibility(VISIBLE);
            tv_count.setVisibility(VISIBLE);
        } else {
            iv_jian.setVisibility(INVISIBLE);
            tv_count.setVisibility(INVISIBLE);
        }
    }

    public void setCanChange(boolean isCanChange) {
        this.isCanChange = isCanChange;
    }

    public void setCanChangeTips(String tipCanChange) {
        this.tipCanChange = tipCanChange;
    }


    public void setCount(int initCount, int minCount, int maxCount) {
        setCount(minCount, maxCount);
        setCurrentCount(initCount);
    }

    public void setCount(int minCount, int maxCount) {
        this.minCount = minCount;
        this.maxCount = maxCount;
    }

    public void setContent(String tipsContent) {
        this.tipsContent = tipsContent;
    }

    private void initView() {
        View.inflate(getContext(), R.layout.add_and_sub_view, this);
        iv_jia = (ImageView) findViewById(R.id.iv_jia);
        iv_jian = (ImageView) findViewById(R.id.iv_jian);
        iv_jian_move = (ImageView) findViewById(R.id.iv_jian_move);
        tv_count = (TextView) findViewById(R.id.tv_count);
        iv_jia.setOnClickListener(this);
        iv_jian.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        if (!isCanChange) {
            if (!TextUtils.isEmpty(tipCanChange)) {
                MyToast.showToast(tipCanChange);
            }
            return;
        }
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
        if (count > minCount) {
            tv_count.setText(count + "");
            if (onAddAndSubListener != null) {
                onAddAndSubListener.sub();
                onAddAndSubListener.content(Integer.parseInt(tv_count.getText().toString()));
            }
        } else if (count == minCount) {
            tv_count.setText(count + "");
            if (onAddAndSubListener != null) {
                onAddAndSubListener.sub();
                onAddAndSubListener.content(Integer.parseInt(tv_count.getText().toString()));
            }
            tv_count.setVisibility(View.INVISIBLE);
            iv_jian.setVisibility(View.INVISIBLE);
//            tv_2.setVisibility(View.VISIBLE);
            tv_count.setAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.scale_alpha_hide));
            animationHide(iv_jian);
        }
    }

    public String getCurrentContent() {
        return tv_count.getText().toString();
    }

    private void jia() {
        int currentCount = Integer.parseInt(tv_count.getText().toString()) + 1;
        if (currentCount <= maxCount) {
            tv_count.setText(currentCount + "");
            tv_count.setVisibility(View.VISIBLE);
            iv_jian_move.setVisibility(View.VISIBLE);
            if (onAddAndSubListener != null) {
                onAddAndSubListener.add();
                onAddAndSubListener.content(Integer.parseInt(tv_count.getText().toString()));
            }
            if (currentCount == minCount + 1) {
                tv_count.setAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.scale_alpha_show));
                animationShow(iv_jian_move);
            }
        } else {
            if (!TextUtils.isEmpty(tipsContent)) {
                MyToast.showToast(tipsContent);
            }
        }


//        translate 位置转移动画效果
//        整型值:
//        fromXDelta 属性为动画起始时 X坐标上的位置
//        toXDelta   属性为动画结束时 X坐标上的位置
//        fromYDelta 属性为动画起始时 Y坐标上的位置
//        toYDelta   属性为动画结束时 Y坐标上的位置
//        注意:
//        没有指定fromXType toXType fromYType toYType 时候，
//        默认是以自己为相对参照物
//        长整型值：
//        duration  属性为动画持续时间
//        说明:   时间以毫秒为单位


    }

    private void animationShow(final View v) {

        AnimationSet set = new AnimationSet(true);


        final RotateAnimation rotateAnimation = new RotateAnimation(359f, 0f, Animation.RELATIVE_TO_SELF,
                0.5f, Animation.RELATIVE_TO_SELF, 0.5f);


        TranslateAnimation translateAnimation = new TranslateAnimation(0, -(iv_jia.getX() - iv_jian.getX()), 0, 0);


        set.addAnimation(rotateAnimation);
        set.addAnimation(translateAnimation);

        set.setDuration(400);

        v.setAnimation(set);

        set.startNow();


        set.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                iv_jian.setVisibility(View.VISIBLE);
                iv_jian_move.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });


    }


    private void animationHide(final View v) {
        AnimationSet set = new AnimationSet(true);


        final RotateAnimation rotateAnimation = new RotateAnimation(0f, 359f, Animation.RELATIVE_TO_SELF,
                0.5f, Animation.RELATIVE_TO_SELF, 0.5f);


        TranslateAnimation translateAnimation = new TranslateAnimation(0, iv_jia.getX() - iv_jian.getX(), 0, 0);


        set.addAnimation(rotateAnimation);
        set.addAnimation(translateAnimation);

        set.setDuration(400);

        v.setAnimation(set);

        set.startNow();

        set.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                v.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });


    }

    public void setOnAddAndSubListener(OnAddAndSubListener onAddAndSubListener) {
        this.onAddAndSubListener = onAddAndSubListener;
    }

    public interface OnAddAndSubListener {
        void add();

        void sub();

        void content(int count);
    }
}
