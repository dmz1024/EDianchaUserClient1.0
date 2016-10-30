package client.ediancha.com.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;

import client.ediancha.com.base.SingleNetWorkBaseFragment;
import client.ediancha.com.entity.BaseEntity;
import client.ediancha.com.interfaces.OnDataHaveListener;
import client.ediancha.com.interfaces.ScrollViewListener;
import client.ediancha.com.interfaces.ShareInfoInterface;
import client.ediancha.com.myview.ScrollChangedScrollView;
import client.ediancha.com.processor.ShareUtil;


/**
 * Created by dengmingzhi on 2016/10/22.
 */

public abstract class TeaDescBaseFragment<D extends BaseEntity> extends SingleNetWorkBaseFragment<D> implements ShareInfoInterface {
    protected ScrollViewListener scrollViewListener;
    protected String id;
    protected ScrollChangedScrollView scrollView;
    protected ShareUtil.ShareInfo shareInfo=new ShareUtil.ShareInfo();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        id = getArguments().getString("id");
    }

    @Override
    protected ShowCurrentViewENUM getDefaultView() {
        return ShowCurrentViewENUM.VIEW_IS_LOADING;
    }

    @Override
    protected String getUrl() {
        return "app.php";
    }

    @Override
    protected void writeData(D t) {
        if(onDataHaveListener!=null){
            onDataHaveListener.dataHave();
        }
    }

    @Override
    protected boolean getCanRefresh() {
        return false;
    }


    @Override
    public boolean isCanRefresh() {
        return false;
    }


    public void setScrollViewListener(ScrollViewListener scrollViewListener) {
        this.scrollViewListener = scrollViewListener;
    }

    private OnDataHaveListener onDataHaveListener;
    public void setOnDataHaveListener(OnDataHaveListener onDataHaveListener) {
        this.onDataHaveListener = onDataHaveListener;
    }

    public boolean getResult() {
        return result;
    }


    @Override
    public ShareUtil.ShareInfo getShareInfo() {
        return shareInfo;
    }

}
