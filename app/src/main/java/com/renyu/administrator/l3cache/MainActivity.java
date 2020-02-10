package com.renyu.administrator.l3cache;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

import com.renyu.administrator.Loader.RxImageLoader;

public class MainActivity extends AppCompatActivity {
    ImageView mImageView;
    String url = "http://img2.baa.bitautotech.com/usergroup/editor_pic/2017/3/22/694494c2f3544226ae911bf86b4e2bcc.png";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mImageView = findViewById(R.id.image);
        RxImageLoader.with(this).load(url).into(mImageView);

    }
}
