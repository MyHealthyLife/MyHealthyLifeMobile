package myhealthylife.androidapp.myhealthylifemobile;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import myhealthylife.androidapp.myhealthylifemobile.utils.ServicesLocator;

public class LoginActivity extends AppCompatActivity {

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


    public void onClickLogin(View view){
        handleLogin();
    }

    private void handleLogin(){

        final EditText username= (EditText) findViewById(R.id.username);
        final EditText password= (EditText) findViewById(R.id.password);

        Log.d("LOGIN","handle login "+username.getText()+" "+password.getText());


        RequestQueue requestQueue= Volley.newRequestQueue(this);

        /*preare the get request*/
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET,
                ServicesLocator.CENTRIC1_BASE+"/user/data/"+username.getText()
                ,null ,new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                /*handle a 200 response*/
                Log.d("JSON",response.toString());
                validateLogin(response,username.getText().toString(),password.getText().toString());
            }
        }, new Response.ErrorListener() {
            /*handle an error*/
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("JSON",error.toString());
                loginError();
            }
        });

        /*add the request to the request queue, it will be executed as soon as possible*/
        requestQueue.add(jsonObjectRequest);
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
}
