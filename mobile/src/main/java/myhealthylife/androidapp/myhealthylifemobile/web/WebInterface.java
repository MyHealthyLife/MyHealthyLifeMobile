package myhealthylife.androidapp.myhealthylifemobile.web;

import android.content.Context;
import android.content.SharedPreferences;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

import myhealthylife.androidapp.myhealthylifemobile.MainActivity;
import myhealthylife.androidapp.myhealthylifemobile.R;

/**
 * Created by stefano on 22/03/17.
 */

public class WebInterface {
    Context mContext;

    public WebInterface(Context c){
        mContext=c;
    }

    @JavascriptInterface
    public String getUsername(){
        SharedPreferences sharedPreferences=mContext.getApplicationContext().getSharedPreferences(mContext.getString(R.string.preference_file_key),
                Context.MODE_PRIVATE);

        if(sharedPreferences.contains(MainActivity.USERNAME_PREF_NAME))
        {
            return sharedPreferences.getString(MainActivity.USERNAME_PREF_NAME,"");

        }

        return "error";
    }
}
