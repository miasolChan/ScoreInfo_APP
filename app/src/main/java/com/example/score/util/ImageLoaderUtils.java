package com.example.score.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class ImageLoaderUtils{

    private ImageView imageView;

    public ImageLoaderUtils(ImageView imageView) {
        this.imageView = imageView;
    }
    public ImageLoaderUtils() {

    }


    public  static void getImageBitmap(final String url, final ImageView imageView, final Context context) {
        new Thread(new Runnable() {
            @Override
            public synchronized void run() {
                final Bitmap bitmap ;
                InputStream is=null;
                try {
                    URL imgUrl = new URL(url);
                    HttpURLConnection conn = (HttpURLConnection) imgUrl
                            .openConnection();
                    conn.connect();
                    is = conn.getInputStream();

                    bitmap = BitmapFactory.decodeStream(is);
                    ((Activity)context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            imageView.setImageBitmap(bitmap);
                            //System.out.println("线程"+url);
                        }
                    });

                } catch (MalformedURLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }finally {
                    if(is!=null){
                        try {
                            is.close();
                        }catch (Exception e){
                            e.printStackTrace();
                        }

                    }

                }
            }
        }).start();

    }
}
