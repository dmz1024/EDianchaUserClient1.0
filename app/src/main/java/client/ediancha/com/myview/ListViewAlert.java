package client.ediancha.com.myview;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;

/**
 * Created by dengmingzhi on 16/6/1.
 */
public class ListViewAlert {
    private AlertDialog.Builder builder;

    public ListViewAlert(Context ctx, String title) {
        builder = new AlertDialog.Builder(ctx);
        if (!TextUtils.isEmpty(title)) {
            builder.setTitle(title);
        }
    }

    /**
     * 单选
     */
    public void SingleSelection(String[] strings) {
        //    设置一个下拉的列表选择项
        builder.setItems(strings, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                select(which);
            }
        });
        builder.show();

    }

    public void select(int position) {

    }


}
