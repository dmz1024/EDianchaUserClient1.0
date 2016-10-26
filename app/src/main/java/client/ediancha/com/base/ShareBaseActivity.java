package client.ediancha.com.base;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;
import client.ediancha.com.R;
import client.ediancha.com.processor.ShareUtil;
import client.ediancha.com.entity.ShareIcon;
import client.ediancha.com.myview.ChooseShareView;
import client.ediancha.com.myview.ChooseStringView;
import client.ediancha.com.util.MyToast;

/**
 * Created by dengmingzhi on 2016/10/21.
 */

public abstract class ShareBaseActivity extends ToolBarActivity {

    @Override
    protected String getToolBarTitle() {
        return null;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected int getRid() {
        return 0;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (isCanClick()) {
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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_tea_event_desc, menu);
        return true;
    }


    protected boolean isCanClick() {
        return true;
    }

    private void share() {
        final List<ShareIcon> lists = new ArrayList<>();
        lists.add(new ShareIcon(R.mipmap.icons_share_wechat, "微信好友"));
        lists.add(new ShareIcon(R.mipmap.icons_share_wechat_friend, "朋友圈"));
        lists.add(new ShareIcon(R.mipmap.icons_share_qq, "QQ"));
//        lists.add(new ShareIcon(R.mipmap.icons_share_qq_space, "QQ空间"));
        lists.add(new ShareIcon(R.mipmap.icons_share_c, "复制链接"));

        new ChooseShareView(this, lists) {
            @Override
            protected void itemClick(int position) {
                switch (position) {
                    case 0:
                    case 1:
                        ShareUtil.ShareInfo wechatInfo = getShareInfo();
                        wechatInfo.app_name="E点茶";
                        wechatInfo.type = position;
                        ShareUtil.getInstance().Wechat(new Handler(), ShareBaseActivity.this, wechatInfo);
                        break;
                    case 2:
                        ShareUtil.ShareInfo qqInfo = getShareInfo();
                        qqInfo.app_name="E点茶";
                        ShareUtil.getInstance().QQFriend(ShareBaseActivity.this, qqInfo);
                        break;
                    case 3:
                        ctrlC();
                        break;
                }
            }
        }.showAtLocation();
    }


    protected abstract ShareUtil.ShareInfo getShareInfo();

    /**
     * 复制
     */
    private void ctrlC() {
        ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        cm.setText("复制的网址");
        MyToast.showToast("网址已复制至粘贴板");
    }

    private void more() {
        new ChooseStringView(this, getMore()) {
            @Override
            protected void itemClick(int position) {
                handleItem(position);
            }
        }.showAtLocation();
    }

    protected abstract void handleItem(int position);

    protected abstract List<String> getMore();

}
