package client.ediancha.com.activity;

import android.support.v7.widget.RecyclerView;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import client.ediancha.com.R;
import client.ediancha.com.base.ToolBarActivity;

public class TestActivity extends ToolBarActivity {
//    private MyRatingBar aa;
//    private TextView tv_1;
//    private TextView tv_2;

    private RecyclerView rv_content;
    private View view;

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView();
////        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
////        setSupportActionBar(toolbar);
////        aa = (MyRatingBar) findViewById(R.id.aa);
////        tv_1 = (TextView) findViewById(R.id.tv_1);
////        tv_2 = (TextView) findViewById(R.id.tv_2);
////
////        aa.setOnFillStarListener(new MyRatingBar.OnFillStarListener() {
////            @Override
////            public void currentFillFloat(float fill) {
////                tv_1.setText(fill + "");
////            }
////
////            @Override
////            public void currentFillInt(int fill) {
////                tv_2.setText(fill + "");
////            }
////        });
////
////
////        PickerView pickerView = (PickerView) findViewById(R.id.pick_view);
////
////        List<String> data = new ArrayList<String>();
////        for (int i = 0; i < 20; i++) {
////            data.add("" + i);
////        }
////        pickerView.setData(data);
////
////        pickerView.setOnSelectListener(new PickerView.onSelectListener() {
////            @Override
////            public void onSelect(String text) {
////                MyToast.showToast(text);
////            }
////        });
////        rv_content = (RecyclerView) findViewById(R.id.rv_content);
////        view = findViewById(R.id.view);
//
//
//    }

    @Override
    protected String getToolBarTitle() {
        return "测试";
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
//        List<String> data = new ArrayList<>();
//        Citys citys = new Gson().fromJson(Util.getJsonDataFromAssets(this, "city.json"), Citys.class);
//        for (int i = 0; i < citys.data.size(); i++) {
//            data.add(citys.data.get(i).name);
//
//            Log.d("城市", data.get(i));
//        }
//
//        new ChooseCityView(TestActivity.this, data).showAtLocation();
    }

    @Override
    protected int getRid() {
        return R.layout.choose_view;
    }


    class MyAdapter extends RecyclerView.Adapter<Viewholder> {
        private List<String> list;

        public MyAdapter(List<String> list) {
            this.list = list;
        }

        @Override
        public Viewholder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new Viewholder(View.inflate(TestActivity.this, R.layout.item_choose_content, null));
        }

        @Override
        public void onBindViewHolder(Viewholder holder, int position) {
            holder.tv_content.setText(list.get(position));
        }

        @Override
        public int getItemCount() {
            return list.size();
        }
    }

    class Viewholder extends RecyclerView.ViewHolder {
        public TextView tv_content;

        public Viewholder(View itemView) {
            super(itemView);
            tv_content = (TextView) itemView;
        }
    }

}
