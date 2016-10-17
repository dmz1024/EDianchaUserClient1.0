package client.ediancha.com.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import client.ediancha.com.R;
import client.ediancha.com.fragment.MyCenterFragment;
import client.ediancha.com.fragment.TeaEventDescFragment;

public class TeaEventDescActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_center);
        getSupportFragmentManager().beginTransaction().add(R.id.fg_content, new TeaEventDescFragment()).commit();
    }
}
