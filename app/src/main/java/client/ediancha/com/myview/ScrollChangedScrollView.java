package client.ediancha.com.myview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

import client.ediancha.com.interfaces.ScrollViewListener;

/**
 * Created by dengmingzhi on 16/10/17.
 */

public class ScrollChangedScrollView extends ScrollView {
    private ScrollViewListener scrollViewListener = null;

    public ScrollChangedScrollView(Context context) {
        super(context);
    }

    public ScrollChangedScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ScrollChangedScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    public void setScrollViewListener(ScrollViewListener scrollViewListener) {
        this.scrollViewListener = scrollViewListener;
    }

    @Override
    protected void onScrollChanged(int x, int y, int oldx, int oldy) {
        super.onScrollChanged(x, y, oldx, oldy);
        if (scrollViewListener != null) {
            scrollViewListener.onScrollChanged(this, x, y, oldx, oldy);
        }
    }

}
