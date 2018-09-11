package polito.iot.iotmobileapplication.core;

import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.CookieHandler;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import polito.iot.iotmobileapplication.R;
import polito.iot.iotmobileapplication.utils.Constants;
import polito.iot.iotmobileapplication.utils.MyCookieManager;
import polito.iot.iotmobileapplication.utils.NonSwipeableViewPager;
import polito.iot.iotmobileapplication.utils.ServiceEntry;

/**
 * Created by user on 09/09/2018.
 */

public class ServiceActivity extends AppCompatActivity {

    //private static final int NUM_PAGES = 5;

    /**
     * The pager widget, which handles animation and allows swiping horizontally to access previous
     * and next wizard steps.
     */
    private NonSwipeableViewPager mPager;

    /**
     * The pager adapter, which provides the pages to the view pager widget.
     */
    private PagerAdapter mPagerAdapter;

    private MyCookieManager myCookieManager;

    private FloatingActionButton next,back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myservice);
        mPager = (NonSwipeableViewPager) findViewById(R.id.service_pager);
        next = (FloatingActionButton) findViewById(R.id.next);
        back = (FloatingActionButton) findViewById(R.id.previous);

        back.setVisibility(View.INVISIBLE);
        mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(final int position) {

                if (position == 0) {
                    back.setVisibility(View.INVISIBLE);

                }
                else
                    back.setVisibility(View.VISIBLE);

                if (position == mPagerAdapter.getCount()-1) {


                    next.setImageResource(R.drawable.baseline_done_white_24);
                    next.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {


                            finish();

                        }
                    });
                } else {

                    next.setImageResource(R.drawable.baseline_arrow_forward_white_24);
                    next.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mPager.setCurrentItem(position+1);
                        }
                    });
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mPager.setCurrentItem(mPager.getCurrentItem()+1);

            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mPager.setCurrentItem(mPager.getCurrentItem()-1);
            }
        });
        try {
            myCookieManager = new MyCookieManager(new URI(Constants.SERVER_ADDRESS));
            myCookieManager.addMyCookie("token",getApplicationContext().getSharedPreferences(Constants.PREFERENCE_FILE,MODE_PRIVATE).getString("token",""));
            CookieHandler.setDefault(myCookieManager);

        }catch(Exception e){}



        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.SERVER_ADDRESS+ "/getexercisesbyscheduleid",
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        System.out.println("RESPONSE " + response);

                        try {
                            JSONArray arr = new JSONArray(response);
                            mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager(),arr);
                            mPager.setAdapter(mPagerAdapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }





                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                error.printStackTrace();
            }
        } ){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id_schedule", getIntent().getStringExtra("id_schedule"));

                return params;

            }
        };;
        Volley.newRequestQueue(getApplicationContext()).add(stringRequest);


        // Instantiate a ViewPager and a PagerAdapter.

    }

    @Override
    public void onBackPressed() {
        if (mPager.getCurrentItem() == 0) {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            super.onBackPressed();
        } else {
            // Otherwise, select the previous step.
            mPager.setCurrentItem(mPager.getCurrentItem() - 1);
        }
    }

    /**
     * A simple pager adapter that represents 5 ScreenSlidePageFragment objects, in
     * sequence.
     */
    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {

        private int[] colors;
        private JSONArray arr;

        public ScreenSlidePagerAdapter(FragmentManager fm,JSONArray arr) {
            super(fm);
            this.arr = arr;
            colors = getApplicationContext().getResources().getIntArray(R.array.ex_colors);

        }

        @Override
        public Fragment getItem(int position) {




            ServiceEntry entry = new ServiceEntry();

            Bundle bundle = new Bundle();
            int color_index = 0;
            if (position>=colors.length)
                color_index = position-colors.length;
            else
                color_index = position;

            bundle.putInt("color",colors[color_index]);
            try {
                bundle.putString("exercise", ((JSONObject) this.arr.get(position)).toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            entry.setArguments(bundle);
            return entry;
        }

        @Override
        public int getCount() {
            return this.arr.length();
        }
    }
}
