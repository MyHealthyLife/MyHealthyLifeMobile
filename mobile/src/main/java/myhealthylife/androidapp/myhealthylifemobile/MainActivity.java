package myhealthylife.androidapp.myhealthylifemobile;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    public final static String USERNAME_PREF_NAME="USERNAME_PREF_NAME";
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
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
