package myhealthylife.androidapp.myhealthylifemobile.services;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.Context;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import myhealthylife.androidapp.myhealthylifemobile.R;
import myhealthylife.androidapp.myhealthylifemobile.utils.ServicesLocator;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class StepsService extends IntentService implements SensorEventListener {

    public final static String USERNAME="USERNAME";
    public final static String STEPS="STEPS";
    public final static String LAST_SENDED_STEPS="LAST_SENDED_STEPS";

    private SensorManager sensorManager;

    private double steps=0;
    private double lastSendSteps=0;

    public StepsService() {
        super("StepsService");
    }

    @Override
    public void onCreate() {
        super.onCreate();

        sensorManager= (SensorManager) getSystemService(SENSOR_SERVICE);

        /*register the sensor listener for getting the steps*/
        if(sensorManager!=null) {
            if (sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER) != null) {
                sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER),
                        sensorManager.SENSOR_DELAY_NORMAL);
                Log.d("STEPS", "registered");
            } else {
                Log.d("STEPS", "step sensor not present");
                Toast.makeText(this, "step sensor not present", Toast.LENGTH_SHORT).show();
            }
        }
        else{
            Log.d("STEPS", "no sensor manager");
            Toast.makeText(this, "no sensor manager", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(sensorManager!=null)
            sensorManager.unregisterListener(this);
        Log.d("STEPS", "unregistered");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d("STEPS","handle intent at "+System.currentTimeMillis());

        SharedPreferences sharedPreferences=this.getApplicationContext().getSharedPreferences(getString(R.string.preference_file_key),
                Context.MODE_PRIVATE);

        if (intent != null) {
            if(!intent.hasExtra(USERNAME)){
                Log.d("STEPS","no username");
                return;
            }

            /*if(!intent.hasExtra(STEPS)){
                Log.d("STEPS","no steps");
                return;
            }*/

            if(sharedPreferences.contains(LAST_SENDED_STEPS)){
                lastSendSteps=sharedPreferences.getFloat(LAST_SENDED_STEPS,0);
            }

            String username=intent.getStringExtra(USERNAME);

            RequestQueue requestQueue= Volley.newRequestQueue(this);

            JSONObject measure=new JSONObject();

            Double stepsToSend=getSteps(sharedPreferences);

            if(stepsToSend==null){
                Log.d("STEPS","error");
                showToast("MyHealthyLife: cannot send a 0 steps measure");
                return;
            }

            /*create the measure Json Object*/
            try {
                measure.put("dateRegistered",System.currentTimeMillis());
                measure.put("measureType","steps");
                measure.put("measureValue",stepsToSend);
            } catch (JSONException e) {
                Log.d("STEPS","unable to create the json object");
                return;
            }

            StringRequest putStepsRequest = getStepsRequest(username, measure);

            requestQueue.add(putStepsRequest);
        }
    }

    private void showToast(final String text) {
        Handler mHandler = new Handler(getMainLooper());
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @NonNull
    private StringRequest getStepsRequest(String username, final JSONObject measure) {

        StringRequest request=new StringRequest(Request.Method.POST,
                ServicesLocator.CENTRIC1_BASE + "/measure/" + username, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("STEPS","update successfull");
                Toast.makeText(getApplicationContext(),"MyHealthyLife: steps measure sucessfully updated",Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("STEPS","update error "+error.toString()+" "+error.getMessage());
                Toast.makeText(getApplicationContext(),"MyHealthyLife: error during steps update",Toast.LENGTH_SHORT).show();

            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> headers=new HashMap<>();

                Log.d("REQUEST","get custom headers");

                headers.put("Accept","application/json");

                return headers;
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                Log.d("REQUEST","message: "+measure.toString());
                try {
                    return measure.toString().getBytes("utf-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

                return null;
            }

            @Override
            public String getBodyContentType() {
                return String.format("application/json; charset=%s", "utf-8");
            }


        };



        request.setRetryPolicy(new DefaultRetryPolicy(
                60*1000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        try {
            Log.d("REQUEST","body: "+new String(request.getBody(),"UTF-8"));
            Log.d("REQUEST","body: "+request.getBodyContentType());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (AuthFailureError authFailureError) {
            authFailureError.printStackTrace();
        }

        return request;
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        steps=sensorEvent.values[0];
        Log.d("STEPS",""+steps);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    private Double getSteps(SharedPreferences sharedPreferences) {
        double sendSteps=0;

        Log.d("STEPS","steps value: "+steps);

        if(steps==0)
            return null;

        //TODO assunzione molto forte
                /* step counter will be resetted during the device booting
                * if the actual step value is higher than the last send
                * the device has not been reboted, so we subtract the actual value to the stored value*/
        if(steps>lastSendSteps){
            sendSteps=steps-lastSendSteps;
        }
        else if(steps<lastSendSteps){//the device has been rebooted so the steps value is lower than the stored one
            sendSteps=steps;

        }
        else{
            return null;
        }




        lastSendSteps=steps;
        sharedPreferences.edit().putFloat(LAST_SENDED_STEPS, (float) lastSendSteps).apply();

        return sendSteps;
    }

    public static Intent getStepsIntend(Context context,String username){
        Intent intent=new Intent(context, StepsService.class);
        intent.putExtra(StepsService.USERNAME,username);
        return intent;
    }

    public static PendingIntent getPendingIntent(Context context,String username){
        Intent intent =getStepsIntend(context,username);
        PendingIntent pendingIntent=PendingIntent.getService(context,200,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        return pendingIntent;
    }

    public static void setAlarmManager(Context context, String username){
        Log.d("ALARM MANAGER","setting step service...");
        AlarmManager alarmManager= (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()+1000, 1000*3600*12,
                getPendingIntent(context,username));
    }

    public static void removeAlarmManager(Context context,String username){
        Log.d("ALARM MANAGER","removing step service...");
        AlarmManager alarmManager= (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(getPendingIntent(context,username));
    }

    public static void resetSendStepsField(Context context){
        SharedPreferences sharedPreferences=context.getApplicationContext().getSharedPreferences(context.getString(R.string.preference_file_key),
                Context.MODE_PRIVATE);
        sharedPreferences.edit().putFloat(LAST_SENDED_STEPS, (float) 0).apply();
    }

}
