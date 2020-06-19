package com.example.score.cache;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 从网络获取图片
 */
public class NetCache {

    private LocalCache mLocalCache;
    private MemoryCache mMemoryCache;

    public NetCache(LocalCache mLocalCache, MemoryCache mMemoryCache) {
        this.mLocalCache = mLocalCache;
        this.mMemoryCache = mMemoryCache;
    }

    /**
     * 从网络下载图片
     *
     * @param ivPic 显示图片的imageview
     * @param url   下载图片的网络地址
     */
    public void getBitmapFromNet(ImageView ivPic, String url) {
        new BitmapTask().execute(ivPic, url);

    }

    class BitmapTask extends AsyncTask<Object, Void, Bitmap> {

        private ImageView ivPic;
        private String url;

        @Override
        protected Bitmap doInBackground(Object[] params) {
            ivPic = (ImageView) params[0];
            url = (String) params[1];

            return downLoadBitmap(url);
        }
        @Override
        protected void onProgressUpdate(Void[] values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            if (result != null) {
                ivPic.setImageBitmap(result);
                System.out.println("从网络缓存图片OK");

                //从网络获取图片后,保存至本地缓存
                mLocalCache.setBitmapToLocal(url, result);
                //保存至内存中
                mMemoryCache.put(url, result);

            }
        }
    }

    /**
     * 网络下载图片
     * @param url
     * @return
     */
    private Bitmap downLoadBitmap(String url) {
        HttpURLConnection conn = null;
        try {
            conn = (HttpURLConnection) new URL(url).openConnection();
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);
            conn.setRequestMethod("GET");

            int responseCode = conn.getResponseCode();
            if (responseCode == 200) {
                //图片压缩
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize=2;//宽高压缩为原来的1/2
                options.inPreferredConfig = Bitmap.Config.ARGB_4444;
                Bitmap bitmap = BitmapFactory.decodeStream(conn.getInputStream(), null, options);
                return bitmap;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }

        return null;
    }
}
