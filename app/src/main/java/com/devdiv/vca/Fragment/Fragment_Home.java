package com.devdiv.vca.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.fragment.app.Fragment;

import com.devdiv.vca.LottieDialog;
import com.devdiv.vca.R;

public class Fragment_Home extends Fragment {

    private WebView web_View;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment__home, container, false);

        web_View = view.findViewById(R.id.web_View);
        web_View.getSettings().setLoadsImagesAutomatically(true);
        web_View.getSettings().setJavaScriptEnabled(true);
        web_View.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        web_View.loadUrl("https://www.espn.in/cricket/");
        LottieDialog lottieDialog = new LottieDialog(getContext());
        lottieDialog.show();

        web_View.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {

                getActivity().setProgress(progress * 100);

                if(progress == 100){
                    lottieDialog.dismiss();
                }
//                    activity.setTitle(R.string.app_name);
            }
        });
//        AppWebViewClients webViewClients = new AppWebViewClients(getContext(), lottieDialog);

        return view;
    }

    public class AppWebViewClients extends WebViewClient {
        Context context;
        LottieDialog lottieDialog;

        public AppWebViewClients(Context context, LottieDialog lottieDialog) {
            this.context = context;
            this.lottieDialog = lottieDialog;
            lottieDialog.show();
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            // TODO Auto-generated method stub
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            // TODO Auto-generated method stub
            super.onPageFinished(view, url);
            lottieDialog.dismiss();
        }


    }

}