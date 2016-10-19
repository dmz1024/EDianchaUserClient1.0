package client.ediancha.com.activity;

import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ScrollView;

import java.util.ArrayList;
import java.util.List;

import client.ediancha.com.R;
import client.ediancha.com.base.ToolBarActivity;
import client.ediancha.com.entity.ShareIcon;
import client.ediancha.com.fragment.TeaEventDescFragment;
import client.ediancha.com.interfaces.ScrollViewListener;
import client.ediancha.com.myview.ChooseShareView;
import client.ediancha.com.myview.ChooseStringView;
import client.ediancha.com.util.MyToast;

public class TeaEventDescActivity extends ToolBarActivity implements ScrollViewListener {
    private TeaEventDescFragment teaEventDescFragment;

    @Override
    protected String getToolBarTitle() {
        return getIntent().getStringExtra("title");
    }

    @Override
    protected void initView() {
        teaEventDescFragment = TeaEventDescFragment.getInstance(getIntent().getStringExtra("id"));
        teaEventDescFragment.setScrollViewListener(this);
    }

    @Override
    protected void initData() {
        getSupportFragmentManager().beginTransaction().replace(R.id.fg_base, teaEventDescFragment).commit();
    }

    @Override
    protected int getRid() {
        return R.layout.base_framelayout;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_tea_event_desc, menu);
        return true;
    }


    @Override
    protected int getTop() {
        return 0;
    }

    @Override
    public void onCreateCustomToolBar(Toolbar toolbar) {
        super.onCreateCustomToolBar(toolbar);

        toolbar.setBackgroundColor(Color.parseColor("#00333333"));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_more:
                more();
                break;
            case R.id.item_share:
                share();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void share() {
        final List<ShareIcon> lists = new ArrayList<>();
        lists.add(new ShareIcon(R.mipmap.icons_share_wechat, "微信好友"));
        lists.add(new ShareIcon(R.mipmap.icons_share_wechat_friend, "朋友圈"));
        lists.add(new ShareIcon(R.mipmap.icons_share_qq, "QQ"));
        lists.add(new ShareIcon(R.mipmap.icons_share_qq_space, "QQ空间"));
        lists.add(new ShareIcon(R.mipmap.icons_share_c, "复制链接"));

        new ChooseShareView(this, lists) {
            @Override
            protected void itemClick(int position) {
                Log.d("分享", lists.get(position).title);
                switch (position) {
                    case 0:
                        break;
                    case 4:
                        ctrlC();
                        break;
                }
            }
        }.creatPop();
    }

    /**
     * 复制
     */
    private void ctrlC() {
        ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        cm.setText("复制的网址");
        MyToast.showToast("网址已复制至粘贴板");
    }

    private void more() {
        List<String> mores = new ArrayList<>();
        mores.add("报错");
        mores.add("举报商家");
        new ChooseStringView(this, mores) {
            @Override
            protected void itemClick(int position) {

            }
        }.creatPop();
    }

    private int currentColor = 100;

    @Override
    public void onScrollChanged(ScrollView scrollView, int x, int y, int oldx, int oldy) {
        Log.d("滚动", y + "-" + oldy);
        if (y > oldy) {
            int color = currentColor - 15;
            if (color < 0) {
                color = 0;
            }
            toolbar.setBackgroundColor(Color.parseColor("#" + (color < 10 ? "0" + color : color) + "333333"));
        } else {
            int color = currentColor + 15;
            if (color > 100) {
                color = 100;
            }

            toolbar.setBackgroundColor(Color.parseColor("#" + (color == 100 ? "00" : color) + "333333"));
        }

//
//        if (y <= 10 || oldy <= 10) {
//            currentColor = 0;
//            toolbar.setBackgroundColor(Color.parseColor("#1333333"));
//        } else if (y >= scrollView.getWidth() / 2 || oldy >= scrollView.getWidth() / 2) {
//            toolbar.setBackgroundColor(Color.parseColor("#99333333"));
//            currentColor = 100;
//        }
//        toolbar.setAlpha((y - oldy) / 10);
    }
}
