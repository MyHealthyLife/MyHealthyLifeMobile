package myhealthylife.androidapp.myhealthylifemobile.services;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
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

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class StepsService extends IntentService {

    public final static String USERNAME="USERNAME";
    public final static String STEPS="STEPS";

    public StepsService() {
        super("StepsService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d("STEPS","handle intent");
        if (intent != null) {
            if(!intent.hasExtra(USERNAME)){
                Log.d("STEPS","no username");
                return;
            }

            if(!intent.hasExtra(STEPS)){
                Log.d("STEPS","no steps");
                return;
            }

            String username=intent.getStringExtra(USERNAME);

            RequestQueue requestQueue= Volley.newRequestQueue(this);

            JSONObject measure=new JSONObject();

            /*create the measure Json Object*/
            try {
                measure.put("dateRegistered",System.currentTimeMillis());
                measure.put("measureType","steps");
                measure.put("measureValue",intent.getDoubleExtra(STEPS,0));
            } catch (JSONException e) {
                Log.d("STEPS","unable to create the json object");
                return;
            }

            JsonObjectRequest putStepsRequest = getStepsRequest(username, measure);

            requestQueue.add(putStepsRequest);
        }
    }

    @NonNull
    private JsonObjectRequest getStepsRequest(String username, JSONObject measure) {
        return new JsonObjectRequest(Request.Method.POST,
                        ServicesLocator.CENTRIC1_BASE + "/measure/" + username,
                        measure, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("STEPS","update successfull");
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("STEPS","update error "+error.toString());
                    }
                });
    }

}
