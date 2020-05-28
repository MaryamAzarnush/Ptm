package com.azarnush.webeskan;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import dmax.dialog.SpotsDialog;

public class WebLogFragment extends Fragment {
    SpotsDialog dialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_web_log, container, false);
        HomeActivity.toolbar.setTitle("وبلاگ");

        WebView myWebView = root.findViewById(R.id.wv_weblog);
        dialog = new SpotsDialog(getContext(), R.style.Custom);
        myWebView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);

        myWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                dialog.setCancelable(true);
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
        return root;
    }


    @Override
    public void onResume() {
        super.onResume();
        HomeActivity.toolbar.setTitle("وبلاگ");
    }
}
