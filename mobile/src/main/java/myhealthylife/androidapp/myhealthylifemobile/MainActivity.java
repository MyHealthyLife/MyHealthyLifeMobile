package myhealthylife.androidapp.myhealthylifemobile;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.PersistableBundle;
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

import myhealthylife.androidapp.myhealthylifemobile.services.StepsService;

public class MainActivity extends AppCompatActivity {

    public final static String USERNAME_PREF_NAME="USERNAME_PREF_NAME";
    public final static String CRON_JOB_ACTIVE="CRON_JOB_ACTIVE";

    private WebView webView;

    private String username=null;

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

    private PendingIntent getCronjobIntent(){

        return null;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.logout:
                /*remove login informations*/
                SharedPreferences sharedPreferences=getApplicationContext().getSharedPreferences(getString(R.string.preference_file_key),Context.MODE_PRIVATE);
                sharedPreferences.edit().remove(USERNAME_PREF_NAME).apply();

                /*reload the activity*/
                finish();
                startActivity(getIntent());
                break;
            case R.id.send_steps:
                Intent intent=new Intent(this, StepsService.class);
                intent.putExtra(StepsService.USERNAME,username);
                startService(intent);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        webView = (WebView) findViewById(R.id.webViewMyHealthyLife);
        webView.setWebViewClient(new WebViewClient());
        webView.getSettings().setJavaScriptEnabled(true);

        if (savedInstanceState==null) {
            //webView.loadUrl("http://www.google.com");
            webView.loadUrl("http://192.168.1.68:8080/MyHealthyLifeWeb/index.jsp");
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
