package client.ediancha.com.myview;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import client.ediancha.com.R;
import client.ediancha.com.base.PopBaseView;
import client.ediancha.com.interfaces.SingleTextWatcher;

/**
 * Created by dengmingzhi on 2016/11/2.
 */

public class PopEdit extends PopBaseView {
    private boolean canEm;
    private String content;

    public PopEdit(Context ctx) {
        super(ctx);
    }

    public PopEdit(Context ctx, boolean canEm, String content) {
        super(ctx);
        this.canEm = canEm;
        this.content = content;
    }



    @Override
    protected View getView() {
        View view = View.inflate(ctx, R.layout.pop_edit, null);
        final EditText et_content = (EditText) view.findViewById(R.id.et_content);
        final Button bt_ok = (Button) view.findViewById(R.id.bt_ok);
        bt_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                content(et_content.getText().toString());
            }
        });

        et_content.addTextChangedListener(new SingleTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (TextUtils.equals(content, et_content.getText().toString())) {
                    bt_ok.setEnabled(false);
                    bt_ok.setAlpha(0.5f);
                } else {

                    if (!TextUtils.isEmpty(et_content.getText().toString())) {
                        bt_ok.setEnabled(true);
                        bt_ok.setAlpha(1.0f);
                    } else {
                        if (!canEm) {
                            bt_ok.setEnabled(false);
                            bt_ok.setAlpha(0.5f);
                        } else {
                            bt_ok.setEnabled(true);
                            bt_ok.setAlpha(1.0f);
                        }
                    }
                }


            }
        });


        et_content.setText(content);

        return view;
    }


    protected void content(String content) {

    }


}
