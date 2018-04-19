package izabet.sync.com.i_zabet;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.provider.Settings.Secure;
import android.view.MenuItem;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;
import android.R.layout ;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.Switch;


public class HomePage extends AppCompatActivity {

    SharedPreferences prefs = null;
    private String android_id = Secure.getString(this.getContentResolver(), Secure.ANDROID_ID);
    private String url ="http://162.243.33.136/izabet/index.php?tab=requestAccount&device_id="+android_id+"&type=2";
    private String url2 = "";
    private String salt;

    private int id ;
    private Switch switch1 ;
    private boolean switchOnOff ;
    public static final String SHARED_PREFS="sharedPrefs";
    public static final String TEXT = "text";
    public static final String SWITCH1= "switch1";
    RequestQueue rq = Volley.newRequestQueue(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        prefs = getSharedPreferences("com.sync.izabet", MODE_PRIVATE);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();







        sendHttpRequest();

        saveData(TEXT,salt );


    }





    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home_page, menu);
        return true;
    }
//    @Override
//    protected void onResume() {
//        super.onResume();
//        if (prefs.getBoolean("firstrun", true)) {
//
//
//
//
//            prefs.edit().putBoolean("firstrun", false).commit();
//        }
//
//        }

    public void sendHttpRequest(){
        JsonObjectRequest jor = new JsonObjectRequest( com.android.volley.Request.Method.GET, url, null, new com.android.volley.Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    salt = response.getString("salt");
                    id = response.getInt("id");


                }

                catch (JSONException e)
                {
                    e.printStackTrace();
                }

            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


            }
        }) ;


        rq.add(jor);
    }


    public void secondSendHttpRequest(){
        JsonObjectRequest jor = new JsonObjectRequest( com.android.volley.Request.Method.GET, url2, null, new com.android.volley.Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    salt = response.getString("salt");
                    id = response.getInt("id");


                }

                catch (JSONException e)
                {
                    e.printStackTrace();
                }

            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


            }
        }) ;


        rq.add(jor);
    }


    public void saveData(String constantName, String value)
    {

        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(constantName, value);
        editor.putBoolean(SWITCH1, switch1.isChecked());

    }

    public void loadData()
    {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        salt = sharedPreferences.getString(TEXT,"" );
        switchOnOff = sharedPreferences.getBoolean(SWITCH1, false);

    }


    }

