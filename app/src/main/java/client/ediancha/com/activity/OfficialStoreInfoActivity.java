package client.ediancha.com.activity;

import android.content.ClipboardManager;
import android.content.Context;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import client.ediancha.com.R;
import client.ediancha.com.base.ToolBarActivity;
import client.ediancha.com.entity.ShareIcon;
import client.ediancha.com.fragment.StoreInfoFragment;
import client.ediancha.com.myview.ChooseShareView;
import client.ediancha.com.processor.ShareUtil;
import client.ediancha.com.util.MyToast;

/**
 * Created by dengmingzhi on 2016/11/1.
 */

public class OfficialStoreInfoActivity extends ToolBarActivity {
    private StoreInfoFragment storeInfoFragment;

    @Override
    protected String getToolBarTitle() {
        return getIntent().getStringExtra("title");
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        getSupportFragmentManager().beginTransaction().replace(R.id.fg_base, storeInfoFragment = StoreInfoFragment.getInstance(getIntent().getStringExtra("id"))).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_store_info, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (isCanClick()) {
            switch (item.getItemId()) {
                case R.id.item_share:
                    share();
                    break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    protected boolean isCanClick() {
        return storeInfoFragment.getIsOk();
    }

    private void share() {
        final List<ShareIcon> lists = new ArrayList<>();
        lists.add(new ShareIcon(R.mipmap.icons_share_wechat, "微信好友"));
        lists.add(new ShareIcon(R.mipmap.icons_share_wechat_friend, "朋友圈"));
        lists.add(new ShareIcon(R.mipmap.icons_share_qq, "QQ"));
        lists.add(new ShareIcon(R.mipmap.icons_share_c, "复制链接"));

        new ChooseShareView(this, lists) {
            @Override
            protected void itemClick(int position) {
                switch (position) {
                    case 0:
                    case 1:
                        ShareUtil.ShareInfo wechatInfo = getShareInfo();
                        wechatInfo.app_name = "E点茶";
                        wechatInfo.type = position;
                        ShareUtil.getInstance().Wechat(new Handler(), OfficialStoreInfoActivity.this, wechatInfo);
                        break;
                    case 2:
                        ShareUtil.ShareInfo qqInfo = getShareInfo();
                        qqInfo.app_name = "E点茶";
                        ShareUtil.getInstance().QQFriend(OfficialStoreInfoActivity.this, qqInfo);
                        break;
                    case 3:
                        ctrlC(getShareInfo().url);
                        break;
                }
            }
        }.showAtLocation();
    }

    private ShareUtil.ShareInfo getShareInfo() {
        return storeInfoFragment.getShareInfo();
    }

    /**
     * 复制
     */
    private void ctrlC(String string) {
        ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        cm.setText(string);
        MyToast.showToast("链接已复制至粘贴板");
    }

    @Override
    protected int getRid() {
        return R.layout.base_framelayout;
    }
}
