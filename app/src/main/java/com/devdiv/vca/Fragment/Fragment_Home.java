package com.devdiv.vca.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.fragment.app.Fragment;

import com.devdiv.vca.R;

public class Fragment_Home extends Fragment {

    private WebView web_View;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment__home, container, false);

        web_View = view.findViewById(R.id.web_View);
////        web_View.setWebViewClient(new MyBrowser());
        web_View.getSettings().setLoadsImagesAutomatically(true);
        web_View.getSettings().setJavaScriptEnabled(true);
        web_View.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        web_View.loadUrl("https://www.espn.in/cricket/");

        return view;
    }

    private class MyBrowser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            view.loadUrl("https://www.espn.in");
            return true;
        }
    }

}