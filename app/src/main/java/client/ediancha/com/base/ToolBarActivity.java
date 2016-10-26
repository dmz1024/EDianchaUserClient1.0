package client.ediancha.com.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.LinkedList;
import java.util.List;

import client.ediancha.com.R;
import client.ediancha.com.util.Util;

/**
 * Created by dengmingzhi on 2016/10/18.
 */
public abstract class ToolBarActivity extends BaseActivity  {
    public Toolbar toolbar;
    public TextView tv_toolBar_title;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getRid());
        tv_toolBar_title = ((TextView) toolbar.findViewById(R.id.tv_toolBar_title));
        initView();
        initData();
        onCreateCustomToolBar(toolbar);
        setSupportActionBar(toolbar);
        tv_toolBar_title.setText(getToolBarTitle() != null ? getToolBarTitle() : "");
    }

    public void setTitle(String title) {
        tv_toolBar_title.setText(title);
    }

    protected abstract String getToolBarTitle();


    protected abstract void initView();

    protected abstract void initData();

    protected abstract int getRid();

    @Override
    public void setContentView(int layoutResID) {
//        mToolBarHelper = new ToolBarHelper(this, layoutResID);
//        toolbar = mToolBarHelper.getToolBar();
//        setContentView(mToolBarHelper.getContentView());
//        /*把 toolbar 设置到Activity 中*/
//        setSupportActionBar(toolbar);
//        /*自定义的一些操作*/
//        onCreateCustomToolBar(toolbar);

        ViewGroup rootView = (ViewGroup) findViewById(android.R.id.content);
        RelativeLayout baseLinearLayout = new RelativeLayout(this);
        baseLinearLayout.setBackgroundColor(getResources().getColor(R.color.colorfff));
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        baseLinearLayout.setLayoutParams(params);
        View v = View.inflate(this, layoutResID, null);
        baseLinearLayout.addView(v);
        RelativeLayout.LayoutParams vLayoutParams = (RelativeLayout.LayoutParams) v.getLayoutParams();
        setMargins(vLayoutParams);
        v.setLayoutParams(vLayoutParams);
        rootView.addView(baseLinearLayout);
        toolbar = (Toolbar) View.inflate(this, R.layout.toolbar, null);
        baseLinearLayout.addView(toolbar);
        RelativeLayout.LayoutParams toolBarParams = (RelativeLayout.LayoutParams) toolbar.getLayoutParams();
        toolBarParams.width = Util.getWidth();
        toolbar.setLayoutParams(toolBarParams);
    }

    protected void setMargins(RelativeLayout.LayoutParams vLayoutParams) {
        vLayoutParams.setMargins(0, getTop(), 0, 0);
    }

    protected int getTop() {
        return Util.dp2Px(56f);
    }


    public void onCreateCustomToolBar(Toolbar toolbar) {
        toolbar.setContentInsetsRelative(0, 0);
        toolbar.setTitle("");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            back();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {
            back();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }




    protected void back() {
        finish();
    }


    @Override
    public void onClick(View v) {

    }


    /**
     * 获取状态栏高度
     *
     * @return
     */
    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }
}