package myhealthylife.androidapp.myhealthylifemobile;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import myhealthylife.androidapp.myhealthylifemobile.services.StepsService;
import myhealthylife.androidapp.myhealthylifemobile.utils.ServicesLocator;

public class LoginActivity extends AppCompatActivity {

    private final static String ON_LOGIN_KEY="ON_LOGIN_KEY";

    private Dialog loadingDialog=null;
    /**
     * used during the screen rotation in order to revisualize the loading
     * screen during the login process
     */
    private boolean onLogin=false;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.login_action_bar, menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setTitle(getString(R.string.login_activity_name));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if(savedInstanceState!=null) {
            if (savedInstanceState.containsKey(ON_LOGIN_KEY)) {
                onLogin = savedInstanceState.getBoolean(ON_LOGIN_KEY);
            }
        }

        Log.d("ONCREATE",""+onLogin);

        if(onLogin){
            showLoadingDialog();
        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(ON_LOGIN_KEY,onLogin);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(loadingDialog!=null)
            loadingDialog.show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(loadingDialog!=null)
            loadingDialog.dismiss();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.action_ok:
                handleLogin();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * override the action of the back button in order to quit the aplication
     */
    @Override
    public void onBackPressed() {
        super.onBackPressed();

        finishAffinity();
    }

    public void onClickLogin(View view){
        handleLogin();
    }

    private void handleLogin(){

        final EditText username= (EditText) findViewById(R.id.username);
        final EditText password= (EditText) findViewById(R.id.password);

        Log.d("LOGIN","handle login "+username.getText()+" "+password.getText());

        showLoadingDialog();


        RequestQueue requestQueue= Volley.newRequestQueue(this);

        /*preare the get request*/
        StringRequest stringRequest=new StringRequest(Request.Method.GET,
                ServicesLocator.CENTRIC1_BASE+"/user/data/"+username.getText()
                 ,new Response.Listener() {
            @Override
            public void onResponse(Object response) {
                Log.d("STRING_REQ",response.toString());
                try {
                    JSONObject jsonObject=new JSONObject(response.toString());
                    validateLogin(jsonObject,username.getText().toString(),password.getText().toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                    loginError();
                }
                hideLoadingDialog();
            }

        },new Response.ErrorListener(){

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("STRING_REQ",error.toString());
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> headers=new HashMap<>();

                Log.d("REQUEST","get custom headers");

                headers.put("Accept","application/json");
                headers.put("Content-Type","application/json");

                return headers;
            }
        };

        /*add the request to the request queue, it will be executed as soon as possible*/
        requestQueue.add(stringRequest);
    }

    /**
     * show an error if the credential are wrong
     */
    private void loginError(){
        Toast.makeText(this, "Bad credential",Toast.LENGTH_SHORT).show();
    }

    /**
     * check the login data
     * @param object
     * @param username
     * @param passwordToCheck
     */
    private void validateLogin(JSONObject object,String username,String passwordToCheck){
        if(object.has("password")){
            try {
                if(object.getString("password").equals(passwordToCheck)){
                    Log.d("LOGIN","success");

                    /*save the username in the shared preferencies*/
                    SharedPreferences sharedPreferences=getApplicationContext().getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
                    sharedPreferences.edit().putString(MainActivity.USERNAME_PREF_NAME,username).apply();

                    /*set the alarm manager in order to sending the steps periodically*/
                    StepsService.setAlarmManager(this,username);

                    /*close the activity and return to the main activity*/

                    this.finish();
                }
                else {
                    loginError();
                }
            } catch (JSONException e) {
                e.printStackTrace();
                loginError();
            }
        }
        else {
            loginError();
        }
    }

    private void showLoadingDialog(){

        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        LayoutInflater inflater=this.getLayoutInflater();

        builder.setView(inflater.inflate(R.layout.loading_dialog_layout,null));

        onLogin=true;

        loadingDialog=builder.create();
        loadingDialog.setCanceledOnTouchOutside(false);

        loadingDialog.show();


    }

    private void hideLoadingDialog(){
        if(loadingDialog!=null)
            loadingDialog.dismiss();

        onLogin=false;
    }
}
