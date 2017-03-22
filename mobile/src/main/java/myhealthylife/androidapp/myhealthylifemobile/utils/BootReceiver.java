package myhealthylife.androidapp.myhealthylifemobile.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by stefano on 22/03/17.
 */

public class BootReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("BROA","received");
        Toast.makeText(context,"reboot received",Toast.LENGTH_SHORT).show();
    }
}
