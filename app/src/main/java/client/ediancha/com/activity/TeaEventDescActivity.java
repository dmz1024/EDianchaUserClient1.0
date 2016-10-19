package client.ediancha.com.activity;

import android.content.ClipboardManager;
import android.content.Context;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import client.ediancha.com.R;
import client.ediancha.com.base.ToolBarActivity;
import client.ediancha.com.entity.ShareIcon;
import client.ediancha.com.fragment.TeaEventDescFragment;
import client.ediancha.com.myview.ChooseShareView;
import client.ediancha.com.myview.ChooseStringView;

public class TeaEventDescActivity extends ToolBarActivity {

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        getSupportFragmentManager().beginTransaction().replace(R.id.fg_base, new TeaEventDescFragment()).commit();
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
}
