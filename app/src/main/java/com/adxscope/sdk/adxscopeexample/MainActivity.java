package com.adxscope.sdk.adxscopeexample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.adxscope.sdk.adxmobile.mBanner;

public class MainActivity extends AppCompatActivity {

    private mBanner AdxBanner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AdxBanner = (mBanner) findViewById(R.id.webView);
        AdxBanner.loadAds("286");

    }

}
