package polito.iot.iotmobileapplication;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.CookieHandler;
import java.net.URI;
import java.util.ArrayList;

import polito.iot.iotmobileapplication.core.ChatFragment;
import polito.iot.iotmobileapplication.core.NotificationFragment;
import polito.iot.iotmobileapplication.core.ProfileFragment;
import polito.iot.iotmobileapplication.core.TrainingFragment;
import polito.iot.iotmobileapplication.utils.Constants;
import polito.iot.iotmobileapplication.utils.Exercise;
import polito.iot.iotmobileapplication.utils.Message;
import polito.iot.iotmobileapplication.utils.MyCookieManager;
import polito.iot.iotmobileapplication.utils.Schedule;
import polito.iot.iotmobileapplication.utils.ScheduleAdapter;
import polito.iot.iotmobileapplication.utils.User;
import polito.iot.iotmobileapplication.utils.ViewPagerAdapter;

/**
 * Created by user on 19/05/2018.
 */

public class HomeActivity extends AppCompatActivity implements TrainingFragment.TrainingInterface, ProfileFragment.ProfileInterface, NotificationFragment.NotificationInterface {



    private BottomNavigationView bottomNavigationView;
    private ViewPager viewPager;
    private MenuItem prevMenuItem;
    private MyCookieManager myCookieManager;


    @Override
    public void findUserProfile(Intent intent) {

        StringRequest stringRequest = new StringRequest(Request.Method.GET, Constants.SERVER_ADDRESS+ "/getuserbyid",
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        System.out.println("RESPONSE " + response);
                        Fragment f = ((ViewPagerAdapter)viewPager.getAdapter()).getItem(viewPager.getCurrentItem());
                        if(f instanceof ProfileFragment){

                            try {

                                JSONObject obj = new JSONObject(response);

                                User user = new User(obj.optString("name"),
                                        obj.optString("surname"),
                                        obj.optString("email"),
                                        obj.optString("phone"),
                                        "",
                                        obj.optString("address"),
                                        obj.optString("birth_date"),
                                        obj.optString("end_date"));

                                ((ProfileFragment) f).setUserDetails(user);

                                } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                error.printStackTrace();
            }
        } );
        Volley.newRequestQueue(getApplicationContext()).add(stringRequest);
    }

    @Override
    public void findSchedules(Intent intent) {

        StringRequest stringRequest = new StringRequest(Request.Method.GET, Constants.SERVER_ADDRESS+ "/getschedulesandexerciselistbyuserid",
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        System.out.println("RESPONSE " + response);
                        Fragment f = ((ViewPagerAdapter)viewPager.getAdapter()).getItem(viewPager.getCurrentItem());
                        if(f instanceof TrainingFragment){

                            try {
                                JSONArray jsonArray = new JSONArray(response);
                                ArrayList<Schedule> list = new ArrayList<>();
                                ArrayList<Exercise> e = new ArrayList<>();
                                for (int i = 0;i<jsonArray.length();i++){

                                    JSONObject obj = (JSONObject)jsonArray.get(i);
                                    JSONObject schedule_obj = obj.getJSONObject("schedule");
                                    JSONArray exercise_arr = obj.getJSONArray("lists");

                                    for (int j = 0;j<exercise_arr.length();j++){

                                        JSONObject ex_obj = (JSONObject)exercise_arr.get(j);
                                        Exercise exercise = new Exercise(ex_obj.optString("name"),ex_obj.optString("muscolar_zone"),ex_obj.optString("description"),ex_obj.optString("details"),ex_obj.optInt("repetitions"),Float.parseFloat(String.valueOf(ex_obj.optDouble("weight"))),ex_obj.optString("url"));
                                        e.add(exercise);

                                    }


                                    Schedule schedule = new Schedule(
                                            schedule_obj.getString("id_schedule"),
                                            schedule_obj.getString("name"),
                                            schedule_obj.getString("objective"),
                                            schedule_obj.getString("details"),
                                            schedule_obj.getString("start_date"),
                                            schedule_obj.getString("end_date"),
                                            schedule_obj.getInt("days"),
                                            e);//)ArrayList<Exercise>)obj.opt("exercise_list"));
                                    list.add(schedule);

                                }
                                ((TrainingFragment) f).addScheduleList(list);



                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                error.printStackTrace();
            }
        } );
        Volley.newRequestQueue(getApplicationContext()).add(stringRequest);



    }


    @Override
    public void findMessages(Intent intent) {

        StringRequest stringRequest = new StringRequest(Request.Method.GET, Constants.SERVER_ADDRESS+ "/getmessages",
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        System.out.println("RESPONSE " + response);
                        Fragment f = ((ViewPagerAdapter)viewPager.getAdapter()).getItem(viewPager.getCurrentItem());
                        if(f instanceof NotificationFragment){

                            try {
                                JSONArray jsonArray = new JSONArray(response);
                                ArrayList<Message> list = new ArrayList<>();

                                for (int i = 0;i<jsonArray.length();i++){

                                    JSONObject obj = (JSONObject)jsonArray.get(i);

                                    Message message = new Message(
                                            obj.getString("title"),
                                            obj.getString("body"),
                                            obj.getString("send_date"));
                                    list.add(message);

                                }
                                ((NotificationFragment) f).addMessages(list);



                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                error.printStackTrace();
            }
        } );
        Volley.newRequestQueue(getApplicationContext()).add(stringRequest);
    }

    private void displayLastMessage(){



            Handler h = new Handler();
            h.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Fragment f = ((ViewPagerAdapter)viewPager.getAdapter()).getItem(viewPager.getCurrentItem());
                    if (f instanceof NotificationFragment) {

                        ((NotificationFragment) f).showLastMessage();
                    }

                }
            },1000);






    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        FirebaseMessaging.getInstance().subscribeToTopic("all");



        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        viewPager = (ViewPager) findViewById(R.id.viewpager);

        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.action_training:
                                viewPager.setCurrentItem(0);
                                break;
/*                            case R.id.action_chat:
                                viewPager.setCurrentItem(1);
                                break;*/
                            case R.id.action_notifications:
                                viewPager.setCurrentItem(1);
                                break;
                            case R.id.action_profile:
                                viewPager.setCurrentItem(2);
                                break;
                        }
                        return false;
                    }
                });




        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (prevMenuItem != null) {
                    prevMenuItem.setChecked(false);
                }
                else
                {
                    bottomNavigationView.getMenu().getItem(0).setChecked(false);
                }

                bottomNavigationView.getMenu().getItem(position).setChecked(true);
                prevMenuItem = bottomNavigationView.getMenu().getItem(position);


                if (position == 1){

                    findMessages(new Intent());

                } else if (position == 0){

                    findSchedules(new Intent());

                } else if (position == 2){

                    findUserProfile(new Intent());

                }


            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }

        });


        setupViewPager(viewPager);
        if (getIntent().getAction()!=null && getIntent().getAction().equals("NOTACTIVITY")) {
            viewPager.setCurrentItem(1);
            displayLastMessage();

        }

        try {
            myCookieManager = new MyCookieManager(new URI(Constants.SERVER_ADDRESS));
            myCookieManager.addMyCookie("token",getApplicationContext().getSharedPreferences(Constants.PREFERENCE_FILE,MODE_PRIVATE).getString("token",""));
            CookieHandler.setDefault(myCookieManager);

        }catch(Exception e){}

        findSchedules(new Intent());


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onBackPressed() {

        moveTaskToBack(false);
    }

    private void setupViewPager(ViewPager viewPager)
    {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());


        adapter.addFragment(new TrainingFragment());
        //adapter.addFragment(new ChatFragment());
        adapter.addFragment(new NotificationFragment());
        adapter.addFragment(new ProfileFragment());
        viewPager.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_exit:

                AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this,R.style.Theme_AppCompat_Light_Dialog_Alert);
                builder.setMessage("Are you sure you want to exit?");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        endSession();

                    }
                });

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.setCancelable(true);
                builder.create().show();


                return true;


            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    private void endSession(){

        getApplicationContext().getSharedPreferences(Constants.PREFERENCE_FILE,MODE_PRIVATE).edit().putString("token","").apply();
        Intent i = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(i);
        finish();
        FirebaseMessaging.getInstance().unsubscribeFromTopic("all");


        StringRequest stringRequest = new StringRequest(Request.Method.GET, Constants.SERVER_ADDRESS+ "/logout",
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        System.out.println("RESPONSE " + response);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                error.printStackTrace();
            }
        } );
        Volley.newRequestQueue(getApplicationContext()).add(stringRequest);



    }
}
