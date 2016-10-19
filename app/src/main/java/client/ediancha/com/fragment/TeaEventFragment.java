package client.ediancha.com.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.List;
import java.util.Map;

import client.ediancha.com.adapter.LikeEventAdapter;
import client.ediancha.com.adapter.TeaEventAdapter;
import client.ediancha.com.base.ListNetWorkBaseFragment;
import client.ediancha.com.entity.LikeEvent;
import client.ediancha.com.entity.TeaEvent;
import client.ediancha.com.interfaces.OnTeaEventMapListener;

/**
 * Created by dengmingzhi on 16/10/12.
 */

public class TeaEventFragment extends ListNetWorkBaseFragment<TeaEvent.Data, TeaEvent> {
    private boolean isScrollEnabled = true;
    private OnTeaEventMapListener onTeaEventMapListener;

    @Override
    protected RecyclerView.Adapter getAdapter(List<TeaEvent.Data> totalList) {
        return new TeaEventAdapter(getContext(), totalList);
    }

    @Override
    protected String getUrl() {
        return "app.php";
    }

    @Override
    protected Map<String, String> getMap() {
        if (onTeaEventMapListener != null) {
            return onTeaEventMapListener.getMap(map);
        }
        map.put("c", "chahui");
        map.put("a", "index");
        return map;
    }

    protected int getJian() {
        return 0;
    }


    @Override
    protected Class<TeaEvent> getTClass() {
        return TeaEvent.class;
    }


    @Override
    protected RecyclerView.LayoutManager getLayoutManager() {
        return new LinearLayoutManager(getContext()) {
            @Override
            public boolean canScrollVertically() {
                Log.d("滑动", isScrollEnabled + "");
//                if (d != null) {
//                    if (isScrollEnabled) {
//                        d.isUp();
//                    } else {
//                        d.isDown();
//                    }
//                }
                return true;
            }
        };
    }


    public void setScrollEnable(boolean flag) {
        this.isScrollEnabled = flag;
    }

    @Override
    protected boolean isOnlyInitOne() {
        return onTeaEventMapListener == null;
    }

    public void setOnTeaEventMapListener(OnTeaEventMapListener onTeaEventMapListener) {
        this.onTeaEventMapListener = onTeaEventMapListener;
    }


    public void setCurrentPosition(int position) {
        if (totalList.size() > 0) {
            if (currentType == RequestType.LOAD_NEW || currentType == RequestType.LOAD_fILTER) {
                layoutManager.scrollToPosition(position);
            }
        }
    }

}
