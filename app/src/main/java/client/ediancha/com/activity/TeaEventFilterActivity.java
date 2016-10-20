package client.ediancha.com.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import client.ediancha.com.R;
import client.ediancha.com.fragment.MyCenterFragment;
import client.ediancha.com.fragment.TeaEventFilterFragment;
import client.ediancha.com.fragment.TeaOrderFragment;

public class TeaEventFilterActivity extends AppCompatActivity {

    private TextView tv_tea_event;
    private TextView tv_tea_space;
    private TextView tv_tea_culture;
    private TextView tv_left;
    private TextView tv_right;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tea_event_filter);
        initView();
    }

    private void initView() {

        tv_tea_event = (TextView) findViewById(R.id.tv_tea_event);
        tv_tea_space = (TextView) findViewById(R.id.tv_tea_space);
        tv_tea_culture = (TextView) findViewById(R.id.tv_tea_culture);
        tv_left = (TextView) findViewById(R.id.tv_left);
        tv_right = (TextView) findViewById(R.id.tv_right);
        initData();
    }

    private void initData() {
        int position = getIntent().getIntExtra("position", 0);
        switch (position) {
            case 0:
                tv_tea_event.setTextColor(getResources().getColor(R.color.color61c12c));
                tv_tea_event.setBackgroundColor(getResources().getColor(R.color.colorfbfbfb));
                getSupportFragmentManager().beginTransaction().add(R.id.fg_content, new TeaEventFilterFragment()).commit();
                break;
            case 1:
                tv_tea_space.setTextColor(getResources().getColor(R.color.color61c12c));
                tv_tea_space.setBackgroundColor(getResources().getColor(R.color.colorfbfbfb));
                getSupportFragmentManager().beginTransaction().add(R.id.fg_content, TeaOrderFragment.getInstance("")).commit();
                break;
            case 2:
                tv_tea_culture.setTextColor(getResources().getColor(R.color.color61c12c));
                tv_tea_culture.setBackgroundColor(getResources().getColor(R.color.colorfbfbfb));
                getSupportFragmentManager().beginTransaction().add(R.id.fg_content, new MyCenterFragment()).commit();
                break;
        }

    }


}
