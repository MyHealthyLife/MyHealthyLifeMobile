package myhealthylife.androidapp.myhealthylifemobile;

import android.content.Intent;
import android.net.Uri;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by Simone on 11/03/2017.
 */

public class CustomWebViewClient extends WebViewClient {

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {

        // Check if the host being called needs telegram app to execute
        if (Uri.parse(url).getHost().equals("t.me")) {

            // Launch another Activity that handles URLs
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            view.getContext().startActivity(intent);

            return true;
        }

        // Otherwise let the WebView load the page
        return false;
    }

}
