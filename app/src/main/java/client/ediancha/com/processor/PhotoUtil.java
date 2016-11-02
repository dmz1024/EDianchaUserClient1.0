package client.ediancha.com.processor;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.bingoogolapple.photopicker.activity.BGAPhotoPickerActivity;

import static android.app.Activity.RESULT_OK;

/**
 * Created by dengmingzhi on 2016/11/2.
 */

public class PhotoUtil {
    private Context ctx;
    private int maxCount = 1;//最多选择照片数量,默认一张
    private ArrayList<String> resultUrl;
    private boolean isneedcamera = true;//是否第一张显示相机
    private static final int REQUEST_CODE_CHOOSE_PHOTO = 1;
    private static final int REQUEST_CODE_PHOTO_PREVIEW = 2;
    private Activity activity;

    public static PhotoUtil getInstance() {
        return new PhotoUtil();
    }

    public PhotoUtil setContext(Context ctx) {
        this.ctx = ctx;
        return this;
    }

    public PhotoUtil setResultUrl(ArrayList<String> resultUrl) {
        this.resultUrl = resultUrl;
        return this;
    }

    public PhotoUtil setActivity(Activity activity) {
        this.activity = activity;
        return this;
    }


    public PhotoUtil setIsneedcamera(boolean isneedcamera) {
        this.isneedcamera = isneedcamera;
        return this;
    }


    public PhotoUtil setMaxCount(int count) {
        this.maxCount = count;
        return this;
    }

    public PhotoUtil showPhoto() {
        File takePhotoDir = new File(Environment.getExternalStorageDirectory(), "ediancha");
        activity.startActivityForResult(BGAPhotoPickerActivity.newIntent(activity, isneedcamera ? takePhotoDir : null, maxCount, resultUrl, false), REQUEST_CODE_CHOOSE_PHOTO);
        return this;
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE_CHOOSE_PHOTO) {
            resultUrl.clear();
            resultUrl.addAll(BGAPhotoPickerActivity.getSelectedImages(data));
        }
    }

    public ArrayList<String> getResultUrl() {
        return resultUrl;
    }

}
