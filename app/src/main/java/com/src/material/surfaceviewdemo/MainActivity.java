package com.src.material.surfaceviewdemo;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageView;

import java.io.File;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    //声明视频的路径地址
    private static final String VIDEO_PATH = Environment.getExternalStoragePublicDirectory(
            Environment.DIRECTORY_DOWNLOADS).getAbsolutePath()
            + File.separator + "aaabbbccc.mp4";

    //声明媒体播放器
    private MediaPlayer mediaPlayer;

    //声明媒体播放器的屏幕
    private SurfaceView surfaceView;
    private ImageView imageView;
    private SurfaceHolder surfaceHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = ((ImageView) findViewById(R.id.image_main));
        imageView.setImageBitmap(BitmapThumbnailHelper.createVideoThumbnail(VIDEO_PATH));

        //初始化SurfaceView
        surfaceView = ((SurfaceView) findViewById(R.id.surface_main));
        //初始化SurfaceHolder
        surfaceHolder = surfaceView.getHolder();
        surfaceHolder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {

            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {

            }
        });

        //初始化播放器
        mediaPlayer = new MediaPlayer();

        /**
         * 设置缓冲监听器
         * 设置媒体播放器的准备监听，当调用MediaPlayer.prepare方法时，MediaPlayer会进行准备工作
         * 当准备工作完成后，会回调此监听器的方法，在此方法中需要启动播放
         */
        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                //说明已经缓冲完毕，进行播放操作
                mediaPlayer.start();
            }
        });
    }

    /**
     * 播放视频
     * @param view
     */
    public void play(View view) {
        try {
            imageView.setVisibility(View.INVISIBLE);
            surfaceView.setVisibility(View.VISIBLE);

            //设置播放器的屏幕
            mediaPlayer.setDisplay(surfaceHolder);

            //设置播放器需要播放的视频源
            mediaPlayer.setDataSource(this, Uri.parse(VIDEO_PATH));

            //启动播放器缓冲操作
            mediaPlayer.prepareAsync();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 暂停视频
     * @param view
     */
    public void pause(View view) {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        }
    }

    /**
     * 继续播放视频
     * @param view
     */
    public void keep(View view) {
        if (!mediaPlayer.isPlaying()) {
//            mediaPlayer.start();
        }
    }

    /**
     * 停止视频播放
     * @param view
     */
    public void stop(View view) {
        mediaPlayer.stop();
        Log.e("TAG", "stop: ");
        imageView.setVisibility(View.VISIBLE);
        surfaceView.setVisibility(View.INVISIBLE);
        mediaPlayer.reset();
    }

    public void m() {
        
    }
}
