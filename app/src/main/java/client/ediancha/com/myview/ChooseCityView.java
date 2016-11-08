package client.ediancha.com.myview;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import client.ediancha.com.R;
import client.ediancha.com.base.PopBaseView;
import client.ediancha.com.entity.Citys;
import client.ediancha.com.util.Util;

/**
 * Created by dengmingzhi on 2016/10/25.
 */

public class ChooseCityView extends PopBaseView {
    private List<Citys.Data> list;
    private int selectedSheng;
    private int selectedShi;
    private int selectedqu;
    private String shengText;
    private String shiText;
    private String quText;

    public ChooseCityView(Context ctx, List<Citys.Data> list) {
        super(ctx);
        this.list = list;
    }

    @Override
    protected View getView() {
        View view = View.inflate(ctx, R.layout.pop_city_choose, null);
        final PickerView pv_1 = (PickerView) view.findViewById(R.id.pv_1);
        final PickerView pv_2 = (PickerView) view.findViewById(R.id.pv_2);
        final PickerView pv_3 = (PickerView) view.findViewById(R.id.pv_3);
        pv_1.setIsMiddle(true);
        pv_2.setIsMiddle(true);
        pv_3.setIsMiddle(true);
        pv_1.setSize(Util.dp2Px(15), Util.dp2Px(10));
        pv_2.setSize(Util.dp2Px(15), Util.dp2Px(10));
        pv_3.setSize(Util.dp2Px(15), Util.dp2Px(10));

        List<String> shengs = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            shengs.add(list.get(i).name);
        }
        pv_1.setData(shengs);
        List<String> shis = new ArrayList<>();
        List<Citys.City> citys = list.get(pv_1.getmCurrentSelected()).city;
        for (int i = 0; i < citys.size(); i++) {
            shis.add(citys.get(i).name);
        }

        pv_2.setData(shis);
        final List<Citys.County> countys = citys.get(0).county;
        List<String> qus = new ArrayList<>();
        for (int i = 0; i < countys.size(); i++) {
            qus.add(countys.get(i).name);
        }
        pv_3.setData(qus);


        selectedSheng = pv_1.getmCurrentSelected();
        selectedShi = pv_2.getmCurrentSelected();
        selectedqu = pv_3.getmCurrentSelected();

        pv_1.setOnSelectListener(new PickerView.onSelectListener() {
            @Override
            public void onSelect(String text) {
                int count = list.size();
                exit:
                for (int i = 0; i < count; i++) {
                    String name = list.get(i).name;
                    if (TextUtils.equals(name, text)) {
                        selectedSheng = i;
                        break exit;
                    }
                }

                List<String> shis = new ArrayList<>();
                List<Citys.City> citys = list.get(selectedSheng).city;
                count = citys.size();
                for (int i = 0; i < count; i++) {
                    shis.add(citys.get(i).name);
                }
                pv_2.setData(shis);

                List<Citys.County> countys = citys.get(0).county;
                List<String> qus = new ArrayList<>();
                count = countys.size();
                for (int i = 0; i < count; i++) {
                    qus.add(countys.get(i).name);
                }

                pv_3.setData(qus);
            }

        });

        pv_2.setOnSelectListener(new PickerView.onSelectListener() {
            @Override
            public void onSelect(String text) {
                List<Citys.City> city = list.get(selectedSheng).city;
                int count = city.size();
                exit:
                for (int i = 0; i < count; i++) {
                    String name = city.get(i).name;
                    if (TextUtils.equals(name, text)) {
                        selectedShi = i;
                        break exit;
                    }
                }

                List<String> shis = new ArrayList<>();
                List<Citys.County> county = city.get(selectedShi).county;
                count = county.size();
                for (int i = 0; i < count; i++) {
                    shis.add(county.get(i).name);
                }
                pv_3.setData(shis);

            }

        });

        pv_3.setOnSelectListener(new PickerView.onSelectListener() {
            @Override
            public void onSelect(String text) {
                quText = text;
                List<Citys.County> county = list.get(selectedSheng).city.get(selectedShi).county;
                int count = county.size();
                exit:
                for (int i = 0; i < count; i++) {
                    String name = county.get(i).name;
                    if (TextUtils.equals(name, text)) {
                        selectedqu = i;
                        break exit;
                    }
                }
            }

        });


        TextView tv_cancle = (TextView) view.findViewById(R.id.tv_cancle);
        TextView tv_ok = (TextView) view.findViewById(R.id.tv_ok);
        tv_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        tv_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                Log.d("xuanze", pv_1.getSelectedText() + "--" + pv_2.getSelectedText() + "--" + pv_3.getSelectedText());
                Citys.Data sheng = list.get(selectedSheng);
                Citys.City city = sheng.city.get(selectedShi);
                Citys.County county = city.county.get(selectedqu);
                select(sheng.name, sheng.id, city.name, city.id, county.name, county.id);
            }
        });


        return view;
    }

    @Override
    protected int getGravity() {
        return Gravity.BOTTOM;
    }

    @Override
    protected int height() {
        return Util.dp2Px(250);
    }

    protected void select(String... select) {

    }
}
