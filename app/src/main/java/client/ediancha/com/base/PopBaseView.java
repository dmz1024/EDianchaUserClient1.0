package client.ediancha.com.base;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.PopupWindow;

import com.github.florent37.viewanimator.ViewAnimator;

import client.ediancha.com.R;
import client.ediancha.com.util.Util;


/**
 * Created by dengmingzhi on 2016/10/22.
 */

public abstract class PopBaseView implements PopupWindow.OnDismissListener {
    protected Context ctx;
    protected PopupWindow popupWindow;

    public PopBaseView(Context ctx) {
        this.ctx = ctx;
        ViewAnimator.animate(((Activity) ctx).findViewById(android.R.id.content)).alpha(1f, 0.3f).duration(500).start();
    }

    private void creatPop() {
        popupWindow = new PopupWindow((getView()), Util.getWidth() - width(), height(), true);

        popupWindow.setBackgroundDrawable(new ColorDrawable());
        popupWindow.setOnDismissListener(this);
        popupWindow.setAnimationStyle(getAnimation());
    }

    protected int height() {
        return FrameLayout.LayoutParams.WRAP_CONTENT;
    }

    public void showAtLocation() {
        creatPop();
        popupWindow.showAtLocation(((Activity) ctx).findViewById(android.R.id.content), getGravity(), x(), y());
    }

    public void showAsDropDown(View v) {
        creatPop();
        popupWindow.showAsDropDown(v, x(), y());
    }

    protected int x() {
        return 0;
    }

    protected int y() {
        return 0;
    }


    /**
     * View显示位置
     *
     * @return
     */
    protected int getGravity() {
        return Gravity.CENTER;
    }

    /**
     * pop显示动画
     *
     * @return
     */
    protected int getAnimation() {
        return R.style.popwin_anim_up_and_down;
    }


    protected int width() {
        return 0;
    }

    protected abstract View getView();

    @Override
    public void onDismiss() {
        ViewAnimator.animate(((Activity) ctx).findViewById(android.R.id.content)).alpha(1f, 1f).duration(600).start();
    }

    public void dismiss() {
        popupWindow.dismiss();
    }

}
