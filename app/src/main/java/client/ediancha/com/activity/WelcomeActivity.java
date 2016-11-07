package client.ediancha.com.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.github.florent37.viewanimator.ViewAnimator;

import java.util.ArrayList;
import java.util.List;

import client.ediancha.com.R;
import client.ediancha.com.myview.PinchImageView;
import client.ediancha.com.util.GlideUtil;
import client.ediancha.com.util.SharedPreferenUtil;

/**
 * Created by dengmingzhi on 2016/10/30.
 */

public class WelcomeActivity extends AppCompatActivity {
    private ImageView iv_img;
    private ViewPager vp_content;
    private boolean isFirst;
    private int[] images = {R.mipmap.guide_1, R.mipmap.guide_2, R.mipmap.guide_3, R.mipmap.guide_4};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        iv_img = (ImageView) findViewById(R.id.iv_img);
        vp_content = (ViewPager) findViewById(R.id.vp_content);
        isFirst = new SharedPreferenUtil(this, "FirstLogin").getData("isFirst");
        if (!isFirst) {
            iv_img.setVisibility(View.GONE);
            showViewPager();
        } else {
            vp_content.setVisibility(View.GONE);
            skip();
        }

    }

    /**
     * 显示引导页
     */
    private void showViewPager() {
        GuideAdapter guidApapter = new GuideAdapter();
        vp_content.setAdapter(guidApapter);
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


    class GuideAdapter extends PagerAdapter {
        private List<ImageView> views = new ArrayList<>();

        public GuideAdapter() {
            creatView();
        }

        private void creatView() {
            for (int i = 0; i < images.length; i++) {

                ImageView view = new ImageView(WelcomeActivity.this);
                view.setScaleType(ImageView.ScaleType.FIT_XY);
                view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                Glide.with(WelcomeActivity.this).load(images[i]).into(view);
                if (i == images.length - 1) {
                    view.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            new SharedPreferenUtil(WelcomeActivity.this, "FirstLogin").setData("isFirst", true);
                            Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
                            intent.putExtra("isFromMessage", getIntent().getBooleanExtra("isFromMessage", false));
                            startActivity(intent);
                            finish();
                        }
                    });
                }
                views.add(view);
            }
        }

        @Override
        public int getCount() {
            return images.length;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(views.get(position), 0);//添加页卡
            return views.get(position);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(views.get(position));
        }
    }
}
