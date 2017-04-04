package com.example.wufan.wufan.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.example.wufan.wufan.R;
import android.os.Handler;


public class BootActivity extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bootactivity);
        Handler handler = new Handler();
        handler.postDelayed(new splashhandler(), 3500); // 延迟3.5秒，再运行splashhandler的run()
    }

    class splashhandler implements Runnable
    {
        public void run()

        {
            startActivity(new Intent(getApplication(),Loginactivity.class)); // 显示第2屏
            BootActivity.this.finish();   // 结束第1屏
        }
    }
}
