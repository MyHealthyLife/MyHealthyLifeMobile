package myhealthylife.androidapp.myhealthylifemobile;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.webkit.WebView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import javax.sql.DataSource;

import myhealthylife.androidapp.myhealthylifemobile.services.StepsService;
import myhealthylife.androidapp.myhealthylifemobile.web.WebInterface;

public class MainActivity extends AppCompatActivity{

    public final static String USERNAME_PREF_NAME="USERNAME_PREF_NAME";
    public final static String CRON_JOB_ACTIVE="CRON_JOB_ACTIVE";

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
    }

    private void sendSteps(){

        if(username==null)
            return;

        Intent intent=StepsService.getStepsIntend(this,username);
        startService(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        SharedPreferences sharedPreferences=getApplicationContext().getSharedPreferences(getString(R.string.preference_file_key),Context.MODE_PRIVATE);

        switch (item.getItemId()){
            case R.id.logout:

                /*unset the cronjob for sending the steps*/
                StepsService.removeAlarmManager(this,username);

                /*remove login informations*/
                sharedPreferences.edit().remove(USERNAME_PREF_NAME).apply();

                /*reload the activity*/
                finish();
                startActivity(getIntent());
                break;
            case R.id.send_steps:
                sendSteps();
                break;
        }

        return super.onOptionsItemSelected(item);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        webView = (WebView) findViewById(R.id.webViewMyHealthyLife);
        webView.setWebViewClient(new CustomWebViewClient());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.addJavascriptInterface(new WebInterface(this),"Android");

        if (savedInstanceState==null) {
            //webView.loadUrl("http://www.google.com");
            webView.loadUrl("file:///android_asset/test.html");
        }
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
}
