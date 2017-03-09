package myhealthylife.androidapp.myhealthylifemobile;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    public final static String USERNAME_PREF_NAME="USERNAME_PREF_NAME";
    private String username=null;

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
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
