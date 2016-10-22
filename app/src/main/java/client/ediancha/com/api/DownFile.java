package client.ediancha.com.api;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import client.ediancha.com.util.Util;

/**
 * Created by dengmingzhi on 16/5/6.
 * 下载apk并安装的封装类
 */
public class DownFile {
    private OnDownFileListener onDownFileListener;
    private String httpUrl;
    private String type;

    public DownFile(String httpUrl, String type) {
        this.httpUrl = httpUrl;
        this.type = type;
    }

    /**
     * 下载文件
     *
     * @return
     */
    public void downLoadFile() {
        try {
            File temFile = new File(Util.getPath() + "/" + type);
            if (!temFile.exists()) {
                temFile.mkdirs();
            }
            File file = new File(temFile.getPath() + "/" + Util.stringMD5(httpUrl) + "." + type);
            InputStream is = null;
            FileOutputStream fos = null;
            try {
                URL url = new URL(httpUrl);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                is = conn.getInputStream();
                fos = new FileOutputStream(file);
                byte[] buf = new byte[1024];
                conn.connect();
                long size = conn.getContentLength();
                if (onDownFileListener != null) {
                    onDownFileListener.size(size);
                }
                long currenSize = 0;
                conn.getContentType();
                if (conn.getResponseCode() >= 400) {
                    if (onDownFileListener != null) {
                        onDownFileListener.err("连接错误");
                    }

                } else {
                    //循环读取
                    int numread;
                    while ((numread = is.read(buf)) != -1) {
                        fos.write(buf, 0, numread);
                        currenSize += numread;
                        if (onDownFileListener != null) {
                            onDownFileListener.progress(currenSize);//进度
                        }

                    }
                    onDownFileListener.downOk(file.getAbsolutePath());//下载完成
                }
                conn.disconnect();
            } catch (Exception e) {
                e.printStackTrace();
                if (onDownFileListener != null) {
                    onDownFileListener.err(e.getMessage());
                }
            } finally {
                try {
                    if (fos != null) {
                        fos.close();
                    }
                    if (is != null) {
                        is.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    if (onDownFileListener != null) {
                        onDownFileListener.err(e.getMessage());
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void setOnDownFileListener(OnDownFileListener onDownFileListener) {
        this.onDownFileListener = onDownFileListener;
    }

    public interface OnDownFileListener {
        void err(String e);

        void downOk(String absolutePath);

        void progress(long currenSize);

        void size(long size);
    }

}
