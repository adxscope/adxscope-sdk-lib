package com.adxscope.sdk.adxmobile;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.Log;
import android.webkit.ConsoleMessage;
import android.webkit.URLUtil;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by Adxscope on 16/06/17.
 */

public class mBanner extends WebView {
    private static final String TAG = "webview-helper";




    public mBanner(Context context) {
        super(context);
        init();
    }

    public mBanner(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public mBanner(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public mBanner(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
    public mBanner(Context context, AttributeSet attrs, int defStyleAttr, boolean privateBrowsing) {
        super(context, attrs, defStyleAttr, privateBrowsing);
        init();
    }

    private void init() {
    }


    public void loadAds(String zoneid) {
        String url = "https://adxscope.com/mads.html?zoneid=" + zoneid + "&color=white";
        if (URLUtil.isValidUrl(url)) {
            super.loadUrl(url);
            super.setWebViewClient(new MyWebViewClient());
            super.clearCache(true);
            super.setBackgroundColor(Color.parseColor("#919191"));
            WebSettings webSettings = super.getSettings();
            webSettings.setJavaScriptEnabled(true);
            if (Build.VERSION.SDK_INT >= 21) {
                webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
            }

            super.setWebChromeClient(new WebChromeClient()
            {
                @Override
                public boolean onConsoleMessage(ConsoleMessage cm) {
                    //Log.d("TAG", cm.message() + " at " + cm.sourceId() + ":" + cm.lineNumber());
                    return true;
                }

            });

        } else {
            Log.e(TAG, "Loaded invalid url in webview");
        }
    }

    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {

            if (url != null) {
                view.getContext().startActivity(
                        new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                return true;
            } else {
                return false;
            }
        }



    }



}

