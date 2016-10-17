package client.ediancha.com.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import client.ediancha.com.R;
import client.ediancha.com.fragment.EvaluateFragment;
import client.ediancha.com.fragment.TeaDescFragment;
import client.ediancha.com.fragment.TeaPackageFragment;

public class MoreTeaPackageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_center);
        getSupportFragmentManager().beginTransaction().add(R.id.fg_content, new TeaPackageFragment()).commit();
    }

}
