package client.ediancha.com.myview;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import client.ediancha.com.R;
import client.ediancha.com.base.PopBaseView;
import client.ediancha.com.interfaces.SingleTextWatcher;

/**
 * Created by dengmingzhi on 2016/10/18.
 */

public class PopTeaEventApply extends PopBaseView {


    public PopTeaEventApply(Context ctx) {
        super(ctx);
    }

    @Override
    protected int getAnimation() {
        return  R.style.pop_message;
    }

    @Override
    protected int width() {
        return 70;
    }

    @Override
    protected View getView() {
        View view = View.inflate(ctx, R.layout.pop_tea_event_apply, null);
        final EditText et_tel = (EditText) view.findViewById(R.id.et_tel);
        final EditText et_name = (EditText) view.findViewById(R.id.et_name);
        final Button bt_apply = (Button) view.findViewById(R.id.bt_apply);
        et_tel.addTextChangedListener(new SingleTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (et_tel.getText().toString().length() == 11 && et_name.getText().toString().length() > 0) {
                    bt_apply.setAlpha(1f);
                    bt_apply.setEnabled(true);
                } else {
                    bt_apply.setAlpha(0.2f);
                    bt_apply.setEnabled(false);
                }
            }
        });

        et_name.addTextChangedListener(new SingleTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (et_tel.getText().toString().length() == 11 && et_name.getText().toString().length() > 0) {
                    bt_apply.setAlpha(1f);
                    bt_apply.setEnabled(true);
                } else {
                    bt_apply.setAlpha(0.2f);
                    bt_apply.setEnabled(false);
                }
            }
        });


        bt_apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClick(et_tel.getText().toString(), et_name.getText().toString());
            }
        });

        return view;
    }

    protected void itemClick(String tel, String name) {
        dismiss();
    }
}
