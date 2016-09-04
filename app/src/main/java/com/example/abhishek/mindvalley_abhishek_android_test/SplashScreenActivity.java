package com.example.abhishek.mindvalley_abhishek_android_test;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.abhishek.remoteresourceloader.OnDownloadCompletionListener;
import com.example.abhishek.remoteresourceloader.ResourceManager;

public class SplashScreenActivity extends AppCompatActivity {
    TextView textView_appName;
    ImageView imageView_Circle;
    Animation anim_rotate, anim_move;
    Context mContext;
    Animation.AnimationListener listener;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        mContext = getApplicationContext();
        listener = new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (animation == anim_move) {
                    Intent intent = new Intent(SplashScreenActivity.this, BoardActivity.class);
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        };

        textView_appName = (TextView) findViewById(R.id.fullscreen_content);
        imageView_Circle = (ImageView) findViewById(R.id.circle_rotate);

        anim_move = AnimationUtils.loadAnimation(mContext, R.anim.slide_anim);
        anim_rotate = AnimationUtils.loadAnimation(mContext, R.anim.rotate_anim);

        anim_rotate.setAnimationListener(listener);
        anim_move.setAnimationListener(listener);

        imageView_Circle.startAnimation(anim_rotate);
        fetchResource();
    }

    private void fetchResource() {
        ResourceManager resourceManager = ResourceManager.getInstance(getApplicationContext());
        resourceManager.getResourceLoader().fetchResource("http://pastebin.com/raw/wgkJgazE", new OnDownloadCompletionListener() {
            @Override
            public void resourceFetchedFromRemote(String url, byte[] bytes) {
                textView_appName.startAnimation(anim_move);
            }

            @Override
            public void resourceFetchedFromMemory(String url, byte[] bytes) {
                textView_appName.startAnimation(anim_move);
            }

            @Override
            public void resourceDownloadCancelled(String url) {

            }

        }, null);
    }

}