package myhealthylife.androidapp.myhealthylifemobile.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import myhealthylife.androidapp.myhealthylifemobile.MainActivity;
import myhealthylife.androidapp.myhealthylifemobile.R;
import myhealthylife.androidapp.myhealthylifemobile.services.StepsService;

/**
 * Created by stefano on 22/03/17.
 * This class will be called after the system startup and register the
 * alarm manager in order to send the steps periodically
 */

public class BootReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("BROA","received");
        Toast.makeText(context,"reboot received",Toast.LENGTH_SHORT).show();

        SharedPreferences sharedPreferences=context.getSharedPreferences(context.getString(R.string.preference_file_key),
                Context.MODE_PRIVATE);

        String username=null;

        if(sharedPreferences.contains(MainActivity.USERNAME_PREF_NAME))
        {
            Log.d("BROA","Registering alarm manager");
            Toast.makeText(context,"Registering alarm manager",Toast.LENGTH_SHORT).show();
            username=sharedPreferences.getString(MainActivity.USERNAME_PREF_NAME,"");

            /*resetting last sended steps in order to get the right steps values*/
            StepsService.resetSendStepsField(context);
            /*register the alarm manager in order to send the steps periodically*/
            StepsService.setAlarmManager(context,username);
        }
        else{
            Log.d("BROA","Not logged");
            Toast.makeText(context,"Not logged",Toast.LENGTH_SHORT).show();
        }
    }
}
