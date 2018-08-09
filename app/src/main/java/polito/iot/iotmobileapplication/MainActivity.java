package polito.iot.iotmobileapplication;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

import polito.iot.iotmobileapplication.utils.User;

public class MainActivity extends AppCompatActivity {

    private Button login_btn, signup_button;
    private TextInputLayout email_layout, password_layout;
    private TextInputEditText email_edit, password_edit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        email_edit = (TextInputEditText) findViewById(R.id.email_edit);
        password_edit = (TextInputEditText) findViewById(R.id.password_edit);

        login_btn = (Button) findViewById(R.id.login_btn);
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://192.168.1.78/php_IoT/android/login.php",
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                // Display the first 500 characters of the response string.
                                System.out.println("RESPONSE " + response);

                                try {
                                    JSONObject jsonObject = new JSONObject(response);

                                    if(jsonObject.optBoolean("success")==true){

                                        Toast.makeText(getApplicationContext(), "Logged in successfully", Toast.LENGTH_SHORT).show();
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
                    }
                } ) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        params.put("email", email_edit.getText().toString());
                        params.put("password", password_edit.getText().toString());

                        return params;

                    }
                };
                Volley.newRequestQueue(getApplicationContext()).add(stringRequest);
            }
        });

        signup_button = (Button) findViewById(R.id.signup_btn);
        signup_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getApplicationContext(),RegistrationActivity.class);
                startActivity(i);

            }
        });


    }


}