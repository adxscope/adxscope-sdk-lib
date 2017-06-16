package com.adxscope.sdk.adxmobile;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.webkit.ConsoleMessage;
import android.webkit.URLUtil;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
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
            if(isConnected3G(getContext()) || isConnectedWifi(getContext())) {
                super.loadUrl(url);
                super.setWebViewClient(new MyWebViewClient());
                super.clearCache(true);
                WebSettings webSettings = super.getSettings();
                webSettings.setJavaScriptEnabled(true);
                webSettings.setJavaScriptEnabled(true);
                webSettings.setLoadWithOverviewMode(true);
                webSettings.setUseWideViewPort(true);
                webSettings.setSupportZoom(true);
                webSettings.setBuiltInZoomControls(false);
                webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
                webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
                webSettings.setDomStorageEnabled(true);
                super.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
                super.setScrollbarFadingEnabled(true);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    super.setLayerType(View.LAYER_TYPE_HARDWARE, null);
                }
                
                if (Build.VERSION.SDK_INT >= 21) {
                    webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
                }

                super.setWebChromeClient(new WebChromeClient() {
                    @Override
                    public boolean onConsoleMessage(ConsoleMessage cm) {
                        //Log.d("TAG", cm.message() + " at " + cm.sourceId() + ":" + cm.lineNumber());
                        return true;
                    }
                });
            }else {
                String summary = "<html><body style='background: black;'><p style='color: red;'>Unable to load Ads. Please check if your network connection is working properly or try again later.</p></body></html>";
                super.loadData(summary, "text/html", null);
            }
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

        @SuppressWarnings("deprecation")
        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            String html = "<html><body style='background: black;'><p style='color: red;'>Unable to load Ads. Please check if your network connection is working properly or try again later.</p></body></html>";
            view.loadData(html, "text/html", null);
        }

        @TargetApi(android.os.Build.VERSION_CODES.M)

        @Override
        public void onReceivedError(WebView view, WebResourceRequest req, WebResourceError rerr) {
            // Redirect to deprecated method, so you can use it in all SDK versions
            onReceivedError(view, rerr.getErrorCode(), rerr.getDescription().toString(), req.getUrl().toString());
        }




    }

    public boolean isConnectedWifi(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        return networkInfo.isConnected();
    }

    public boolean isConnected3G(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        return networkInfo.isConnected();
    }




}

