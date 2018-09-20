package polito.iot.iotmobileapplication;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.HttpCookie;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import polito.iot.iotmobileapplication.firebase.MyFirebaseInstanceIdService;
import polito.iot.iotmobileapplication.utils.Constants;
import polito.iot.iotmobileapplication.utils.Exercise;
import polito.iot.iotmobileapplication.utils.MyCookieManager;
import polito.iot.iotmobileapplication.utils.User;

public class MainActivity extends AppCompatActivity {

    private Button login_btn, signup_button;
    private TextInputLayout email_layout, password_layout;
    private TextInputEditText email_edit, password_edit;
    private MyCookieManager myCookieManager;
    private EditText server_ip;
    private String url;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //startService(new Intent(getApplicationContext(),MyFirebaseInstanceIdService.class));
        System.out.println("FIREBASE " + FirebaseInstanceId.getInstance().getToken());

        server_ip = (EditText) findViewById(R.id.server_ip);




        email_edit = (TextInputEditText) findViewById(R.id.email_edit);
        password_edit = (TextInputEditText) findViewById(R.id.password_edit);

        login_btn = (Button) findViewById(R.id.login_btn);
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                url = "http://"+server_ip.getText().toString();
                try {

                    myCookieManager = new MyCookieManager(new URI(url));

                    CookieHandler.setDefault(myCookieManager);

                }catch(Exception e){}

                StringRequest stringRequest = new StringRequest(Request.Method.POST, url+ "/mobile-app/logging",
                        new Response.Listener<String>() {

                            @Override
                            public void onResponse(String response) {
                                // Display the first 500 characters of the response string.
                                System.out.println("RESPONSE " + response);

                                try {
                                    JSONObject jsonObject = new JSONObject(response);

                                    if(jsonObject.optBoolean("status")){
                                        getApplicationContext().getSharedPreferences(Constants.PREFERENCE_FILE,MODE_PRIVATE).edit().putString("server_ip",url).apply();                                        Toast.makeText(getApplicationContext(), "Logged in successfully", Toast.LENGTH_SHORT).show();
                                        //getApplicationContext().getSharedPreferences(Constants.PREFERENCE_FILE,MODE_PRIVATE).edit().putString("token",jsonObject.optString("token")).apply();                                        Toast.makeText(getApplicationContext(), "Logged in successfully", Toast.LENGTH_SHORT).show();

                                        Intent i = new Intent(getApplicationContext(),HomeActivity.class);
                                        startActivity(i);
                                        finish();
                                    } else {
                                        Toast.makeText(getApplicationContext(), "There was an error during login", Toast.LENGTH_LONG).show();
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Log.w("Error",error);
                        Toast.makeText(getApplicationContext(), "Error during login. Verify your username and/or password", Toast.LENGTH_SHORT).show();
                    }
                } ) {

                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        params.put("email", email_edit.getText().toString());
                        params.put("password", password_edit.getText().toString());

                        return params;

                    }



                    @Override
                    protected Response<String> parseNetworkResponse(NetworkResponse response) {
                        // since we don't know which of the two underlying network vehicles
                        // will Volley use, we have to handle and store session cookies manually
                        Log.i("response",response.headers.toString());
                        Map<String, String> responseHeaders = response.headers;
                        String rawCookies = responseHeaders.get("Set-Cookie");
                        //Toast.makeText(getApplicationContext(), "Logged in successfully", Toast.LENGTH_SHORT).show();
                        if (rawCookies.contains("token"))
                            getApplicationContext().getSharedPreferences(Constants.PREFERENCE_FILE,MODE_PRIVATE).edit().putString("token",rawCookies.split("=")[1]).apply();

                        Log.i("cookies",rawCookies);
                        return super.parseNetworkResponse(response);
                    }
                };
                Volley.newRequestQueue(getApplicationContext()).add(stringRequest);
            }
        });

        signup_button = (Button) findViewById(R.id.signup_btn);
        signup_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                url = "http://"+server_ip.getText().toString();

                Intent i = new Intent(getApplicationContext(),RegistrationActivity.class);
                i.putExtra("server_ip",url);
                startActivity(i);

            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();

        //Check whether the user is already logged
        try {
            url = "http://"+server_ip.getText().toString();

            myCookieManager = new MyCookieManager(new URI(url));
            myCookieManager.addMyCookie("token",getApplicationContext().getSharedPreferences(Constants.PREFERENCE_FILE,MODE_PRIVATE).getString("token",""));
            CookieHandler.setDefault(myCookieManager);

        }catch(Exception e){}

        StringRequest stringRequest = new StringRequest(Request.Method.POST,getApplicationContext().getSharedPreferences(Constants.PREFERENCE_FILE,MODE_PRIVATE).getString("server_ip","")+ "/mobile-app/isLogged",
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        System.out.println("RESPONSE " + response);

                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            if(jsonObject.optBoolean("status")==true){

                                Toast.makeText(getApplicationContext(), "Logged in successfully", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(getApplicationContext(),HomeActivity.class);
                                startActivity(i);
                                finish();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
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
}
