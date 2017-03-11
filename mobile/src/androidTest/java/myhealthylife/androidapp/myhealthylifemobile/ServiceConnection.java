package myhealthylife.androidapp.myhealthylifemobile;

import android.content.Context;
import android.provider.Settings;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;

import myhealthylife.androidapp.myhealthylifemobile.utils.ServicesLocator;

/**
 * Created by stefano on 09/03/17.
 */

@RunWith(AndroidJUnit4.class)
public class ServiceConnection {
    @Test
    public void testCentric1Connection(){
        Context appContext = InstrumentationRegistry.getTargetContext();
        RequestQueue requestQueue= Volley.newRequestQueue(appContext);

        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, ServicesLocator.CENTRIC1_BASE+"/user/data/pbitta1",null ,new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("JSON",response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("JSON",error.toString());
            }
        });

        requestQueue.add(jsonObjectRequest);

    }

}
