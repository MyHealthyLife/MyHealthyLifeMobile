package myhealthylife.androidapp.myhealthylifemobile;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.PersistableBundle;
import android.renderscript.Element;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import javax.sql.DataSource;

import myhealthylife.androidapp.myhealthylifemobile.services.StepsService;

public class MainActivity extends AppCompatActivity implements SensorEventListener{

    public final static String USERNAME_PREF_NAME="USERNAME_PREF_NAME";
    public final static String CRON_JOB_ACTIVE="CRON_JOB_ACTIVE";
    public final static String LAST_SENDED_STEPS="LAST_SENDED_STEPS";

    private SensorManager sensorManager;

    private WebView webView;

    private String username=null;

    private double steps=0;
    private double lastSendSteps=0;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.main_activity_menu,menu);

        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences sharedPreferences=this.getApplicationContext().getSharedPreferences(getString(R.string.preference_file_key),
                Context.MODE_PRIVATE);

        if(sharedPreferences.contains(USERNAME_PREF_NAME))
        {
           username=sharedPreferences.getString(USERNAME_PREF_NAME,"");

            /*register the sensor listener for getting the steps*/
            if(sensorManager!=null) {
                if (sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER) != null) {
                    sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER),
                            sensorManager.SENSOR_DELAY_NORMAL);
                } else {
                    Log.d("STEPS", "step sensor not present");
                    Toast.makeText(this, "step sensor not present", Toast.LENGTH_SHORT).show();
                }
            }
            else{
                Log.d("STEPS", "no sensor manager");
                Toast.makeText(this, "no sensor manager", Toast.LENGTH_SHORT).show();
            }


            if(sharedPreferences.contains(LAST_SENDED_STEPS)){
                lastSendSteps=sharedPreferences.getFloat(LAST_SENDED_STEPS,0);
            }
        }
        else{
            Log.d("MAIN","Open login activity");
            Intent intent=new Intent(getApplicationContext(),LoginActivity.class);
            startActivity(intent);
        }



        if(username!=null){
            /*check if the task wich will automatilly send the steps is active*/
            if(sharedPreferences.contains(CRON_JOB_ACTIVE)){
                boolean active=sharedPreferences.getBoolean(CRON_JOB_ACTIVE,false);

                if(!active){
                    AlarmManager alarmManager= (AlarmManager) getSystemService(Context.ALARM_SERVICE);

                }
            }
        }
        //Log.d("ALARM",""+alarmManager.);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    private PendingIntent getCronjobIntent(){

        return null;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        SharedPreferences sharedPreferences=getApplicationContext().getSharedPreferences(getString(R.string.preference_file_key),Context.MODE_PRIVATE);

        switch (item.getItemId()){
            case R.id.logout:
                /*remove login informations*/
                sharedPreferences.edit().remove(USERNAME_PREF_NAME).apply();

                /*reload the activity*/
                finish();
                startActivity(getIntent());
                break;
            case R.id.send_steps:
                sendSteps(sharedPreferences);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void sendSteps(SharedPreferences sharedPreferences) {
        double sendSteps=0;

        Log.d("STEPS","steps value: "+steps);

        if(steps==0)
            return;

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
            return;
        }


        Intent intent=new Intent(this, StepsService.class);
        intent.putExtra(StepsService.USERNAME,username);
        intent.putExtra(StepsService.STEPS,sendSteps);
        startService(intent);

        lastSendSteps=steps;
        sharedPreferences.edit().putFloat(LAST_SENDED_STEPS, (float) lastSendSteps).apply();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        webView = (WebView) findViewById(R.id.webViewMyHealthyLife);
        webView.setWebViewClient(new CustomWebViewClient());
        webView.getSettings().setJavaScriptEnabled(true);

        if (savedInstanceState==null) {
            //webView.loadUrl("http://www.google.com");
            webView.loadUrl("http://192.168.1.68:8080/MyHealthyLifeWeb/index.jsp");
        }

        sensorManager= (SensorManager) getSystemService(SENSOR_SERVICE);
    }

    @Override
    public void onBackPressed() {

        // If there's a page history, then we go to the previous page
        if (webView.canGoBack()) {
            webView.goBack();
        }
        // If there was no page history, uses the default system behavio
        else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        webView.saveState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        webView.restoreState(savedInstanceState);
    }


    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        steps=sensorEvent.values[0];
        Log.d("STEPS",""+steps);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
