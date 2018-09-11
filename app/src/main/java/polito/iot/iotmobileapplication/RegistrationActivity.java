package polito.iot.iotmobileapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
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

import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.HttpCookie;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import polito.iot.iotmobileapplication.utils.Constants;
import polito.iot.iotmobileapplication.utils.User;

/**
 * Created by user on 15/04/2018.
 */

public class RegistrationActivity extends AppCompatActivity {

    private Button confirm;
    private TextInputEditText name, surname, phone, email, address, password, birth, confirm_email, confirm_password;
    private TextInputLayout email_layout, password_layout, confirm_email_layout, confirm_password_layout;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        name = (TextInputEditText) findViewById(R.id.name_edit);
        surname = (TextInputEditText) findViewById(R.id.surname_edit);
        email = (TextInputEditText) findViewById(R.id.email_edit);
        phone = (TextInputEditText) findViewById(R.id.phone_edit);
        confirm_email = (TextInputEditText) findViewById(R.id.confirm_email_edit);
        confirm_password = (TextInputEditText) findViewById(R.id.confirm_password_edit);
        address =(TextInputEditText) findViewById(R.id.address_edit);
        password = (TextInputEditText) findViewById(R.id.password_edit);
        birth = (TextInputEditText) findViewById(R.id.date_of_birth_edit);

        email_layout = (TextInputLayout) findViewById(R.id.email);
        password_layout = (TextInputLayout) findViewById(R.id.password);
        confirm_email_layout = (TextInputLayout) findViewById(R.id.confirm_email);
        confirm_password_layout = (TextInputLayout) findViewById(R.id.confirm_password);

        confirm = (Button) findViewById(R.id.confirm);



        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                confirm_email_layout.setErrorEnabled(false);
                confirm_password_layout.setErrorEnabled(false);
                email_layout.setErrorEnabled(false);
                password_layout.setErrorEnabled(false);

                if (password.getText().toString().equals("")){

                    password.requestFocus();
                    password_layout.setError("This field must be not empty");

                } else if (confirm_password.getText().toString().equals("")){

                    confirm_password.requestFocus();
                    confirm_password_layout.setError("This field must be not empty");

                } else if (email.getText().toString().equals("")) {

                    email.requestFocus();
                    email_layout.setError("This field must be not empty");

                } else if (confirm_email.getText().toString().equals("")) {

                    confirm_email.requestFocus();
                    confirm_email_layout.setError("This field must be not empty");

                }else if (!confirm_email.getText().toString().equals(email.getText().toString())){

                    confirm_email.requestFocus();
                    confirm_email_layout.setError("Email addresses don't match");


                } else if (!confirm_password.getText().toString().equals(password.getText().toString())) {

                    confirm_password.requestFocus();
                    confirm_password_layout.setError("Passwords don't match");

                } else {

                    try {
                        CookieManager manager = new CookieManager();
                        manager.getCookieStore().add(new URI(Constants.SERVER_ADDRESS), new HttpCookie("app-id", "val"));
                        CookieHandler.setDefault(manager);

                    }catch(Exception e){}

                    StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://192.168.1.78:9000/signup",
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    // Display the first 500 characters of the response string.
                                    System.out.println("RESPONSE " + response);

                                    try {
                                        JSONObject jsonObject = new JSONObject(response);
                                        if (jsonObject.optBoolean("status") == true){

                                            Toast.makeText(getApplicationContext(), "Signed up successfully", Toast.LENGTH_SHORT).show();
                                            getApplicationContext().getSharedPreferences(Constants.PREFERENCE_FILE,MODE_PRIVATE).edit().putString("token",jsonObject.optString("token")).apply();
                                            Intent i = new Intent(getApplicationContext(),HomeActivity.class);
                                            startActivity(i);


                                            finish();

                                        } else if (jsonObject.optInt("error_code") == 2){

                                            Toast.makeText(getApplicationContext(), "This email is already taken", Toast.LENGTH_LONG).show();

                                        } else {

                                            Toast.makeText(getApplicationContext(), "There was an error during registration", Toast.LENGTH_LONG).show();

                                        }

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }


                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.w("volley", error);
                            Toast.makeText(getApplicationContext(), "There was an error during registration", Toast.LENGTH_LONG).show();
                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> params = new HashMap<>();
                            params.put("name", name.getText().toString());
                            params.put("surname", surname.getText().toString());
                            params.put("email", email.getText().toString());
                            params.put("address", address.getText().toString());
                            params.put("birthdate", birth.getText().toString());
                            params.put("phone", phone.getText().toString());
                            params.put("password", password.getText().toString());
                            params.put("token", "");

                            return params;

                        }
                    };

                    Volley.newRequestQueue(getApplicationContext()).add(stringRequest);



                }

            }
        });


    }


}
