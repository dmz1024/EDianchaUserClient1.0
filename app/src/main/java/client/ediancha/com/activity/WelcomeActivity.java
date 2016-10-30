package client.ediancha.com.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;

import client.ediancha.com.R;
import client.ediancha.com.util.Util;

/**
 * Created by dengmingzhi on 2016/10/30.
 */

public class WelcomeActivity extends AppCompatActivity {
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_my_center);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
                intent.putExtra("isFromMessage", getIntent().getBooleanExtra("isFromMessage", false));
                startActivity(intent);
                finish();
            }
        }, 1500);
    }
}
