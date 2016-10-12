package client.ediancha.com.myview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.TextView;

import client.ediancha.com.R;


/**
 * Created by dengmingzhi on 16/4/22.
 * 自定义TextView，可以设置4个方向上图片的大小
 */
public class TextImage extends TextView {
    // 需要从xml中读取的各个方向图片的宽和高
    private int height;
    private int width;
    private int mDrawable = -1;
    private int mSeat = 0;

    public TextImage(Context context) {
        this(context, null);
    }

    public TextImage(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TextImage(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.TextImage);
        if (a != null) {
            // 获得参数个数
            int count = a.getIndexCount();
            int index = 0;
            // 遍历参数。先将index从TypedArray中读出来，
            // 得到的这个index对应于attrs.xml中设置的参数名称在R中编译得到的数
            // 这里会得到各个方向的宽和高
            for (int i = 0; i < count; i++) {
                index = a.getIndex(i);
                switch (index) {
                    case R.styleable.TextImage_TextImage_height:
                        height = a.getDimensionPixelSize(index, -1);
                        break;
                    case R.styleable.TextImage_TextImage_width:
                        width = a.getDimensionPixelSize(index, -1);
                        break;
                    case R.styleable.TextImage_TextImage_drawable:
                        mDrawable = a.getResourceId(index, -1);
                        break;
                    case R.styleable.TextImage_TextImage_seat:
                        mSeat = a.getInteger(index, 0);
                        break;

                }
            }

            if (mDrawable != -1 && mSeat != 0) {
                drawable(true);
            }
        }

    }


    public void drawable(boolean visi) {
        if (!visi) {
            setCompoundDrawables(null, null, null, null);
            return;
        }
        Drawable drawable = getResources().getDrawable(mDrawable);
        drawable.setBounds(0, 0, width, height);
        switch (mSeat) {
            case 1:
                setCompoundDrawables(drawable, null, null, null);
                break;
            case 2:
                setCompoundDrawables(null, drawable, null, null);
                break;
            case 3:
                setCompoundDrawables(null, null, drawable, null);
                break;
            case 4:
                setCompoundDrawables(null, null, null, drawable);
                break;
        }
    }


    public void setDrawable(int rid, int seat, int mwidth, int mheight) {
        this.mDrawable = rid;
        this.mSeat = seat;
        this.width = mwidth;
        this.height = mheight;
        drawable(true);
    }

    public void setDrawable(int rid, int mwidth, int mheight) {
        this.mDrawable = rid;
        this.width = mwidth;
        this.height = mheight;
        drawable(true);
    }

    public void setDrawable(int rid, int seat) {
        this.mDrawable = rid;
        this.mSeat = seat;
        drawable(true);
    }

    public void setDrawable(int rid) {
        this.mDrawable = rid;
        drawable(true);
    }

    public int getmDrawable() {
        return mDrawable;
    }


//    public void setDrawable() {
//        // 获取各个方向的图片，按照：左-上-右-下 的顺序存于数组中
//        Drawable[] drawables = getCompoundDrawables();
//        int dir = 0;
//        // 0-left; 1-top; 2-right; 3-bottom;
//        for (Drawable drawable : drawables) {
//            // 设定图片大小
//            setImageSize(drawable, dir++);
//        }
//        // 将图片放回到TextView中
//        setCompoundDrawables(null, , drawables[2],
//                drawables[3]);
//    }

    /**
     * 设定图片的大小
     */
//    private void setImageSize(Drawable d, int dir) {
//        if (d == null) {
//            return;
//        }
//
//        int height = -1;
//        int width = -1;
//        // 根据方向给宽和高赋值
//        switch (dir) {
//            case 0:
//                // left
//                height = leftHeight;
//                width = leftWidth;
//                break;
//            case 1:
//                // top
//                height = topHeight;
//                width = topWidth;
//                break;
//            case 2:
//                // right
//                height = rightHeight;
//                width = rightWidth;
//                break;
//            case 3:
//                // bottom
//                height = bottomHeight;
//                width = bottomWidth;
//                break;
//        }
//        // 如果有某个方向的宽或者高没有设定值，则不去设定图片大小
//        if (width != -1 && height != -1) {
//            d.setBounds(0, 0, width, height);
//        }
//    }
}
