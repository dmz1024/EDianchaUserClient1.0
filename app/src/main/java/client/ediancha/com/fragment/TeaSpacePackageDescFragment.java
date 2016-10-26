package client.ediancha.com.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.Map;

import client.ediancha.com.entity.TeaSpacePackageDesc;

/**
 * Created by dengmingzhi on 2016/10/24.
 */

public class TeaSpacePackageDescFragment extends TeaDescBaseFragment<TeaSpacePackageDesc> {
    public static TeaSpacePackageDescFragment getInstance(String id) {
        Bundle bundle = new Bundle();
        bundle.putString("id", id);
        Log.d("id", id);
        TeaSpacePackageDescFragment teaSpacePackageDescFragment = new TeaSpacePackageDescFragment();
        teaSpacePackageDescFragment.setArguments(bundle);
        return teaSpacePackageDescFragment;
    }

    @Override
    protected void writeData(TeaSpacePackageDesc t) {

    }

    @Override
    protected Map<String, String> getMap() {
        return null;
    }

    @Override
    protected Class<TeaSpacePackageDesc> getTClass() {
        return null;
    }

    @Override
    protected View getHaveDataView() {
        return null;
    }
}
