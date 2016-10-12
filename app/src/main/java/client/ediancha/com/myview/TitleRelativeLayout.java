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
    private TextImage tv_title;
    private TextImage tv_content;
    private View view_1;
    private View view_2;
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
        tv_title = (TextImage) findViewById(R.id.tv_title);
        tv_content = (TextImage) findViewById(R.id.tv_content);
        view_1 = findViewById(R.id.view_1);
        view_2 = findViewById(R.id.view_2);
        /**
         * 获得我们所定义的自定义样式属性
         */
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.TitleRelativeLayout);
        int title_style = typedArray.getResourceId(R.styleable.TitleRelativeLayout_TitleRelativeLayout_title_style, R.style.TextSize14Color333);
        int content_style = typedArray.getResourceId(R.styleable.TitleRelativeLayout_TitleRelativeLayout_content_style, R.style.TextSize13Color999);
        int title_image = typedArray.getResourceId(R.styleable.TitleRelativeLayout_TitleRelativeLayout_title_image, 0);
        boolean view_visi = typedArray.getBoolean(R.styleable.TitleRelativeLayout_TitleRelativeLayout_view_visi, true);
        int title_w = typedArray.getDimensionPixelSize(R.styleable.TitleRelativeLayout_TitleRelativeLayout_title_w, 0);
        int title_h = typedArray.getDimensionPixelSize(R.styleable.TitleRelativeLayout_TitleRelativeLayout_title_h, 0);
        show_view = typedArray.getInt(R.styleable.TitleRelativeLayout_TitleRelativeLayout_show_view, 1);
        boolean content_visi = typedArray.getBoolean(R.styleable.TitleRelativeLayout_TitleRelativeLayout_content_visi, true);
        String title = typedArray.getString(R.styleable.TitleRelativeLayout_TitleRelativeLayout_title);
        String content = typedArray.getString(R.styleable.TitleRelativeLayout_TitleRelativeLayout_content);
        boolean content_image_visi=typedArray.getBoolean(R.styleable.TitleRelativeLayout_TitleRelativeLayout_content_image_visi, true);
        typedArray.recycle();
        tv_title.setTextAppearance(getContext(), title_style);
        tv_content.setTextAppearance(getContext(), content_style);
        if (title_image != 0 && title_h != 0 && title_h != 0) {
            tv_title.setDrawable(title_image, title_w, title_h);
        } else if (title_image != 0) {
            tv_title.setDrawable(title_image);
        }

        if(!content_image_visi){
            tv_content.drawable(false);
        }

        tv_title.setText(title);
        tv_content.setText(content);
        setViewVisi(view_visi);
        tv_content.setVisibility(content_visi ? VISIBLE : INVISIBLE);

    }


    public void setTitleImage(int drawable) {
        tv_title.setDrawable(drawable);
    }

    public void setContentImage(int drawable) {
        tv_content.setDrawable(drawable);
    }

    public void setTitleImageVisi(boolean visi) {
        tv_title.drawable(visi);
    }

    public void setTitle(String title) {
        tv_title.setText(title);
    }

    public void setContent(String content) {
        tv_content.setText(content);
    }

    public String getContent() {
        return tv_content.getText().toString();
    }

    public void setViewVisi(boolean visi) {
        if (visi && show_view == 1) {
            view_1.setVisibility(VISIBLE);
        } else if (visi && show_view == 2) {
            view_2.setVisibility(VISIBLE);
        } else {
            view_1.setVisibility(INVISIBLE);
            view_2.setVisibility(INVISIBLE);
        }
    }
}
