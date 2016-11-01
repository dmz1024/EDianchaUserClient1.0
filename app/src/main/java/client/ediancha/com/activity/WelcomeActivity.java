package client.ediancha.com.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.github.florent37.viewanimator.ViewAnimator;

import client.ediancha.com.R;

/**
 * Created by dengmingzhi on 2016/10/30.
 */

public class WelcomeActivity extends AppCompatActivity {
    private ImageView iv_img;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        iv_img = (ImageView) findViewById(R.id.iv_img);
        skip();
    }

    private void skip() {
        iv_img.postDelayed(new Runnable() {
            @Override
            public void run() {
                ViewAnimator.animate(iv_img).scale(1.0f, 1.5f).duration(1000).start();
            }
        }, 400);

        iv_img.postDelayed(new Runnable() {
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
