package com.azarnush.webeskan;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import dmax.dialog.SpotsDialog;

public class WebLogFragment extends Fragment {
    SpotsDialog dialog;
    WebView myWebView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_web_log, container, false);
        HomeActivity.toolbar.setTitle("وبلاگ");

        myWebView = root.findViewById(R.id.wv_weblog);
        dialog = new SpotsDialog(getContext(), R.style.Custom);
        dialog.setCancelable(true);
        myWebView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);

        myWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {

               dialog.show();

                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                dialog.dismiss();

                super.onPageFinished(view, url);
            }
        });

        myWebView.loadUrl("https://webeskan.com/Blog");

        myWebView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    if (myWebView.canGoBack()) {
                        myWebView.goBack();
                        return true;
                    }
                }
                return false;
            }
        });
        return root;
    }


    @Override
    public void onResume() {
        super.onResume();
        HomeActivity.toolbar.setTitle("وبلاگ");
    }
}
