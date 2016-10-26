package client.ediancha.com.myview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import client.ediancha.com.R;

/**
 * Created by dengmingzhi on 2016/10/24.
 */

public class MyRatingBar extends RelativeLayout {
    private RelativeLayout rl_rating_bottom;
    private RelativeLayout rl_rating_top;
    private int show_star = R.mipmap.icon_star_fill;
    private int null_star = R.mipmap.icon_star_null;
    private int starCount = 5;
    private float starCurrent = 1;
    private float step = 1;
    private float space = 5;
    private boolean clickable;
    private int starImageSize;
    private Bitmap bitmap_fill, bitmap_null;
    private float currentFill;
    private float currentCore;

    public MyRatingBar(Context context) {
        super(context);

    }

    public MyRatingBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(attrs);
    }

    public MyRatingBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(attrs);
    }

    private void initView(AttributeSet attrs) {
        View.inflate(getContext(), R.layout.view_ratingbar, this);
        rl_rating_bottom = (RelativeLayout) findViewById(R.id.rl_rating_bottom);
        rl_rating_top = (RelativeLayout) findViewById(R.id.rl_rating_top);

        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.MyRatingBar);

        show_star = a.getResourceId(R.styleable.MyRatingBar_MyRatingBar_starEmpty, show_star);
        null_star = a.getResourceId(R.styleable.MyRatingBar_MyRatingBar_starFill, null_star);
        starCount = a.getInteger(R.styleable.MyRatingBar_MyRatingBar_starCount, starCount);
        starCurrent = a.getFloat(R.styleable.MyRatingBar_MyRatingBar_starCurrent, starCurrent);
        step = a.getFloat(R.styleable.MyRatingBar_MyRatingBar_step, step);
        space = a.getFloat(R.styleable.MyRatingBar_MyRatingBar_space, space);
        clickable = a.getBoolean(R.styleable.MyRatingBar_MyRatingBar_clickable, false);
        starImageSize = a.getDimensionPixelSize(R.styleable.MyRatingBar_MyRatingBar_starImageSize, 50);
        currentCore = 5.0f;
        bitmap_fill = BitmapFactory.decodeResource(getResources(), show_star);
        bitmap_null = BitmapFactory.decodeResource(getResources(), null_star);
        addStarBottom();
        addStarTop();


//        rl_rating_top.setPadding(0, 0, getWidth(), 0);

        setOnTouchListener(new OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (!clickable || onFillStarListener == null) {
                    return false;
                }

                int paddingX = (int) (rl_rating_bottom.getWidth() - event.getX());
                if (paddingX < 0) {
                    paddingX = 0;
                } else if (paddingX > rl_rating_bottom.getWidth()) {
                    paddingX = rl_rating_bottom.getWidth();
                }

                RelativeLayout.LayoutParams params;
                switch (event.getAction()) {
                    case MotionEvent.ACTION_MOVE:
                    case MotionEvent.ACTION_DOWN:
                        params = (LayoutParams) rl_rating_top.getLayoutParams();
                        rl_rating_top.setPadding(0, 0, -paddingX, 0);
                        rl_rating_top.setLayoutParams(params);
                        currentFill = event.getX();
                        if (currentFill > rl_rating_bottom.getWidth()) {
                            currentFill = rl_rating_bottom.getWidth();
                            onFillStarListener.currentFillFloat(5.0f);
                            onFillStarListener.currentFillInt(5);
                            currentCore = 5.0f;
                            return true;
                        }
                        if (currentFill < 0) {
                            onFillStarListener.currentFillFloat(0.0f);
                            onFillStarListener.currentFillInt(0);
                            currentCore = 0.0f;
                            return true;
                        }

                        float a = currentFill / starImageSize;
                        int b = (int) a;
                        currentFill = currentFill - b * space;
                        a = currentFill / starImageSize;
                        currentCore = a;
                        if (onFillStarListener != null) {
                            onFillStarListener.currentFillFloat(a);
                            onFillStarListener.currentFillInt((int) (a + 0.5f));
                        }

                        return true;
                    case MotionEvent.ACTION_UP:

                        break;
                }

                return true;
            }
        });

    }

    /**
     * 给未选中状态添加View
     */
    private void addStarBottom() {
        for (int i = 0; i < starCount; i++) {
            ImageView v = new ImageView(getContext());
            v.setImageBitmap(bitmap_null);
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(starImageSize, starImageSize);
            params.leftMargin = (int) ((space + starImageSize) * i);
            v.setLayoutParams(params);
            rl_rating_bottom.addView(v);
        }
    }


    /**
     * 给选中状态添加View
     */
    private void addStarTop() {
        for (int i = 0; i < starCount; i++) {
            ImageView v = new ImageView(getContext());
            v.setImageBitmap(bitmap_fill);
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(starImageSize, starImageSize);
            params.leftMargin = (int) ((space + starImageSize) * i);
            params.height = starImageSize;
            params.width = starImageSize;
            v.setLayoutParams(params);
            rl_rating_top.addView(v);
        }
    }


    public void setOnFillStarListener(OnFillStarListener onFillStarListener) {
        this.onFillStarListener = onFillStarListener;
    }

    private OnFillStarListener onFillStarListener;

    public interface OnFillStarListener {
        void currentFillFloat(float fill);

        void currentFillInt(int fill);
    }


    public float getCurrentCore() {
        return currentCore;
    }

}
