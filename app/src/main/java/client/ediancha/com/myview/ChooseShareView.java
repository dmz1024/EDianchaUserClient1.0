package client.ediancha.com.myview;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.florent37.viewanimator.ViewAnimator;

import java.util.ArrayList;
import java.util.List;

import client.ediancha.com.R;
import client.ediancha.com.base.BaseViewHolder;
import client.ediancha.com.entity.ShareIcon;
import client.ediancha.com.util.Util;

/**
 * Created by dengmingzhi on 2016/10/18.
 */

public class ChooseShareView implements PopupWindow.OnDismissListener {
    private Context ctx;
    private List<ShareIcon> list;
    private PopupWindow popupWindow;

    public ChooseShareView(Context ctx, List<ShareIcon> list) {
        this.ctx = ctx;
        this.list = list;
    }

    public void creatPop() {
        popupWindow = new PopupWindow((getView()), FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setBackgroundDrawable(new ColorDrawable(ctx.getResources().getColor(R.color.colorfff)));
        popupWindow.setOnDismissListener(this);
        popupWindow.setAnimationStyle(getAnimation());
        popupWindow.showAtLocation(((Activity) ctx).findViewById(android.R.id.content), Gravity.BOTTOM, 0, 0);
    }

    protected int getAnimation() {
        return R.style.popwin_anim_style;
    }

    protected View getView() {
        final View view = View.inflate(ctx, R.layout.pop_share, null);
        final RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.rv_content);
        final RecyclerView recyclerView1 = (RecyclerView) view.findViewById(R.id.rv_content1);
        TextView tv_cancle = (TextView) view.findViewById(R.id.tv_cancle);
        tv_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });

        GridLayoutManager manager = new GridLayoutManager(ctx, 5);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(new MyAdapter(list));


        GridLayoutManager manager1 = new GridLayoutManager(ctx, 5);
        final List<ShareIcon> list1 = new ArrayList<>();
        list1.addAll(list);
        final MyAdapter adapter1 = new MyAdapter(list1);
        recyclerView1.setLayoutManager(manager1);
        recyclerView1.setAdapter(adapter1);

        view.postDelayed(new Runnable() {
            @Override
            public void run() {
                recyclerView.setVisibility(View.VISIBLE);
                recyclerView1.setVisibility(View.VISIBLE);
                ViewAnimator.animate(recyclerView).translationX(Util.getWidth(), 0).interpolator(new DecelerateInterpolator())
                        .andAnimate(recyclerView1).translationX(-Util.getWidth(), 0).interpolator(new DecelerateInterpolator()).duration(350).start();

                view.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        list1.clear();
                        adapter1.notifyDataSetChanged();
                        recyclerView1.setVisibility(View.GONE);
                    }
                }, 310);
            }
        }, 350);


        ViewAnimator.animate(((Activity) ctx).findViewById(android.R.id.content)).alpha(1f, 0.3f).duration(500).start();
        return view;
    }

    protected void itemClick(int position) {

    }


    @Override
    public void onDismiss() {
        ViewAnimator.animate(((Activity) ctx).findViewById(android.R.id.content)).alpha(1f, 1f).duration(600).start();
    }


    class MyViewHolder extends BaseViewHolder {
        public ImageView iv_img;
        public TextView tv_title;

        public MyViewHolder(View itemView) {
            super(itemView);
            iv_img = (ImageView) itemView.findViewById(R.id.iv_img);
            tv_title = (TextView) itemView.findViewById(R.id.tv_title);
        }

        @Override
        protected void onClick(int layoutPosition) {
            popupWindow.dismiss();
            itemClick(layoutPosition);
        }
    }


    class MyAdapter extends RecyclerView.Adapter {
        private List<ShareIcon> list;

        public MyAdapter(List<ShareIcon> list) {
            this.list = list;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new MyViewHolder(View.inflate(ctx, R.layout.item_share, null));
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            MyViewHolder myViewHolder = (MyViewHolder) holder;
            Glide.with(ctx).load(this.list.get(position).icon).into(myViewHolder.iv_img);
            myViewHolder.tv_title.setText(this.list.get(position).title);
        }

        @Override
        public int getItemCount() {
            return this.list.size();
        }
    }
}
