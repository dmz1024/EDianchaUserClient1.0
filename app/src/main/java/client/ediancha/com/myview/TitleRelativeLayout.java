package client.ediancha.com.myview;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

import client.ediancha.com.R;


/**
 * Created by dengmingzhi on 16/6/12.
 */
public class TitleRelativeLayout extends RelativeLayout {
    private TextImage tv_title1;
    private TextImage tv_content1;
    private View view_11;
    private View view_22;
    private int show_view;

    public TitleRelativeLayout(Context context) {
        this(context, null);
    }

    public TitleRelativeLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TitleRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        View.inflate(getContext(), R.layout.view_title_relativelayout, this);
        tv_title1 = (TextImage) findViewById(R.id.tv_title1);
        tv_content1 = (TextImage) findViewById(R.id.tv_content1);
        view_11 = findViewById(R.id.view_11);
        view_22 = findViewById(R.id.view_22);
        /**
         * 获得我们所定义的自定义样式属性
         */
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.TitleRelativeLayout);
        int title_style = typedArray.getResourceId(R.styleable.TitleRelativeLayout_TitleRelativeLayout_title_style, R.style.TextSize14Color333);
        int content_style = typedArray.getResourceId(R.styleable.TitleRelativeLayout_TitleRelativeLayout_content_style, R.style.TextSize13Color999);
        int title_image = typedArray.getResourceId(R.styleable.TitleRelativeLayout_TitleRelativeLayout_title_image, 0);
        int content_image = typedArray.getResourceId(R.styleable.TitleRelativeLayout_TitleRelativeLayout_content_image, R.mipmap.icon_go);
        boolean view_visi = typedArray.getBoolean(R.styleable.TitleRelativeLayout_TitleRelativeLayout_view_visi, true);
        int title_w = typedArray.getDimensionPixelSize(R.styleable.TitleRelativeLayout_TitleRelativeLayout_title_w, 0);
        int title_h = typedArray.getDimensionPixelSize(R.styleable.TitleRelativeLayout_TitleRelativeLayout_title_h, 0);
        int content_w = typedArray.getDimensionPixelSize(R.styleable.TitleRelativeLayout_TitleRelativeLayout_content_w, 0);
        int content_h = typedArray.getDimensionPixelSize(R.styleable.TitleRelativeLayout_TitleRelativeLayout_content_h, 0);

        show_view = typedArray.getInt(R.styleable.TitleRelativeLayout_TitleRelativeLayout_show_view, 1);
        boolean content_visi = typedArray.getBoolean(R.styleable.TitleRelativeLayout_TitleRelativeLayout_content_visi, true);
        String title = typedArray.getString(R.styleable.TitleRelativeLayout_TitleRelativeLayout_title);
        String content = typedArray.getString(R.styleable.TitleRelativeLayout_TitleRelativeLayout_content);
        boolean content_image_visi = typedArray.getBoolean(R.styleable.TitleRelativeLayout_TitleRelativeLayout_content_image_visi, true);
        typedArray.recycle();
        tv_title1.setTextAppearance(getContext(), title_style);
        tv_content1.setTextAppearance(getContext(), content_style);
        if (title_image != 0 && title_h != 0 && title_h != 0) {
            tv_title1.setDrawable(title_image, title_w, title_h);
        } else if (title_image != 0) {
            tv_title1.setDrawable(title_image);
        }

        if (content_image != R.mipmap.icon_go && content_h != 0 && content_h != 0) {
            tv_content1.setDrawable(content_image, content_w, content_h);
        } else if (title_image != 0) {
            tv_content1.setDrawable(content_image);
        }


        if (!content_image_visi) {
            tv_content1.drawable(false);
        }

        tv_title1.setText(title);
        tv_content1.setText(content);
        setViewVisi(view_visi);
        tv_content1.setVisibility(content_visi ? VISIBLE : INVISIBLE);

    }


    public void setTitleImage(int drawable) {
        tv_title1.setDrawable(drawable);
    }

    public void setContentImage(int drawable) {
        tv_content1.setDrawable(drawable);
    }

    public void setTitleImageVisi(boolean visi) {
        tv_title1.drawable(visi);
    }

    public void setTitle(String title) {
        tv_title1.setText(title);
    }

    public void setContent(String content) {
        tv_content1.setText(content);
    }

    public String getContent() {
        return tv_content1.getText().toString();
    }

    public void setViewVisi(boolean visi) {
        if (visi && show_view == 1) {
            view_11.setVisibility(VISIBLE);
        } else if (visi && show_view == 2) {
            view_22.setVisibility(VISIBLE);
        } else {
            view_11.setVisibility(INVISIBLE);
            view_22.setVisibility(INVISIBLE);
        }
    }

    public TextImage getTv_content() {
        return tv_content1;
    }

    public void setContentOnClick(final OnContentListener onClick) {
        tv_content1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onClick.content();
            }
        });
    }


    public interface OnContentListener{
        void content();
    }

}
