package client.ediancha.com.activity;

import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ScrollView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import client.ediancha.com.R;
import client.ediancha.com.api.MyRetrofitUtil;
import client.ediancha.com.base.ToolBarActivity;
import client.ediancha.com.constant.UserInfo;
import client.ediancha.com.entity.BaseEntity;
import client.ediancha.com.entity.ShareIcon;
import client.ediancha.com.fragment.TeaEventDescFragment;
import client.ediancha.com.interfaces.ScrollViewListener;
import client.ediancha.com.myview.ChooseShareView;
import client.ediancha.com.myview.ChooseStringView;
import client.ediancha.com.myview.PopMessageTips;
import client.ediancha.com.util.MyToast;
import client.ediancha.com.util.Util;

public class TeaEventDescActivity extends ToolBarActivity implements ScrollViewListener {
    private TeaEventDescFragment teaEventDescFragment;
    private String collectionType = "收藏";

    @Override
    protected String getToolBarTitle() {
        return null;
    }

    @Override
    protected void initView() {
        teaEventDescFragment = TeaEventDescFragment.getInstance(getIntent().getStringExtra("id"));
        teaEventDescFragment.setScrollViewListener(this);
    }

    @Override
    protected void initData() {
        isCollection();
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
        if (teaEventDescFragment.result) {
            switch (item.getItemId()) {
                case R.id.item_more:
                    more();
                    break;
                case R.id.item_share:
                    share();
                    break;
            }
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
        mores.add(collectionType);
        new ChooseStringView(this, mores) {
            @Override
            protected void itemClick(int position) {
                switch (position) {
                    case 0:
                        collection();
                        break;
                }
            }
        }.creatPop();
    }

    /**
     * 收藏
     */
    private void collection() {
        Map<String, String> map = new HashMap<>();
        map.put("c", "shoucang");
        map.put("uid", UserInfo.uid);
        map.put("token", UserInfo.token);
        map.put("dataid", getIntent().getStringExtra("id"));
        map.put("type", "3");
        if (TextUtils.equals("取消收藏", collectionType)) {
            map.put("a", "cancel");
        } else {
            map.put("a", "add");
        }

        MyRetrofitUtil.getInstance().post("app.php", map, BaseEntity.class, new MyRetrofitUtil.OnRequestListener<BaseEntity>() {
            @Override
            public void noNetwork() {

            }

            @Override
            public void serverErr() {

            }

            @Override
            public void haveData(BaseEntity baseEntity) {

                if (baseEntity.result == 0) {
                    MyToast.showToast(collectionType + "成功");
                    collectionType = TextUtils.equals("取消收藏", collectionType) ? "收藏" : "取消收藏";
                } else {
                    if (TextUtils.equals("token", baseEntity.msg)) {
                        new PopMessageTips("账号信息", "账号信息已过期,请重新登录!", "去登录", "再看看") {
                            @Override
                            protected void right() {
                                super.right();
                                Util.skip(TeaEventDescActivity.this, LoginActivity.class);
                            }
                        }.showView(TeaEventDescActivity.this);
                    } else {
                        MyToast.showToast(baseEntity.msg);
                    }

                }

            }

            @Override
            public void onCompleted() {

            }

            @Override
            public void resultNo0(String s) {

            }

            @Override
            public void start() {

            }
        }, "正在" + collectionType + "...", TeaEventDescActivity.this);


    }


    /**
     * 是否收藏
     */
    private void isCollection() {
        if (TextUtils.isEmpty(UserInfo.uid)) {
            return;
        }

        Map<String, String> map = new HashMap<>();
        map.put("c", "shoucang");
        map.put("a", "find");
        map.put("uid", UserInfo.uid);
        map.put("token", UserInfo.token);
        map.put("dataid", getIntent().getStringExtra("id"));
        map.put("type", "3");

        MyRetrofitUtil.getInstance().post("app.php", map, BaseEntity.class, new MyRetrofitUtil.OnRequestListener<BaseEntity>() {
            @Override
            public void noNetwork() {

            }

            @Override
            public void serverErr() {

            }

            @Override
            public void haveData(BaseEntity baseEntity) {
                if (baseEntity.result == 0) {
                    collectionType = "收藏";
                } else {
                    collectionType = "取消收藏";
                }
            }

            @Override
            public void onCompleted() {

            }

            @Override
            public void resultNo0(String s) {

            }

            @Override
            public void start() {

            }
        });

    }


    private int currentColor = 0;
    private boolean isColor;

    @Override
    public void onScrollChanged(ScrollView scrollView, int x, int y, int oldx, int oldy) {
        String color;
        if (y > oldy) {
            currentColor += 1;
        } else if (y < oldy) {
            if (y < Util.getHeight() / 3) {
                currentColor -= 1;
            }
        }

        if (currentColor > 100) {
            currentColor = 100;
        } else if (currentColor < 0) {
            currentColor = 0;
        }


        if (currentColor < 10) {
            color = "#0" + currentColor + "333333";
        } else if (currentColor == 100) {
            color = "#333333";
        } else {
            color = "#" + currentColor + "333333";
        }

        if (currentColor >= 50) {
            setTitle(getIntent().getStringExtra("title"));
        } else {
            setTitle(getIntent().getStringExtra(""));
        }

        if (y <= 10) {
            setTitle(getIntent().getStringExtra(""));
            color = "#00333333";
        } else if (y >= Util.getHeight() / 2) {
            color = "#333333";
            setTitle(getIntent().getStringExtra("title"));
        }

        toolbar.setBackgroundColor(Color.parseColor(color));


    }
}
