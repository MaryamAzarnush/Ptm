package com.azarnush.webeskan;

import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import dmax.dialog.SpotsDialog;

public class Manager_loginFragment extends Fragment {

    SpotsDialog dialog;
    WebView myWebView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_manager_login, container, false);
        HomeActivity.toolbar.setTitle("ورود مدیران");


        myWebView = root.findViewById(R.id.wv_manager);
        myWebView.setVerticalScrollBarEnabled(true);
        myWebView.getSettings().setJavaScriptEnabled(true);

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

        myWebView.loadUrl("https://manager.webeskan.com/Account");
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
        HomeActivity.toolbar.setTitle("ورود مدیران");
    }
}
