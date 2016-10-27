package client.ediancha.com.util;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import client.ediancha.com.R;

/**
 * Created by dengmingzhi on 16/10/13.
 */

public class GlideUtil {

    public static void GlideErrAndOc(Context ctx, Object url, ImageView iv) {
        Glide.with(ctx).load(url).error(R.mipmap.icon_err).placeholder(R.mipmap.icon_err).into(iv);
    }
}
