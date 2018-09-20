package polito.iot.iotmobileapplication;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;


import org.json.JSONException;
import org.json.JSONObject;

import java.net.CookieHandler;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import polito.iot.iotmobileapplication.utils.Constants;
import polito.iot.iotmobileapplication.utils.MyCookieManager;

/**
 * Created by user on 15/04/2018.
 */

public class RegistrationActivity extends AppCompatActivity {

    private Button confirm;
    private TextInputEditText name, surname, phone, email, address, password, birth, confirm_email, confirm_password;
    private TextInputLayout email_layout, password_layout, confirm_email_layout, confirm_password_layout;
    private Calendar myCalendar = Calendar.getInstance();
    private DatePickerDialog.OnDateSetListener date;

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


        date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {

                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) { // API 21
            birth.setShowSoftInputOnFocus(false);
        } else { // API 11-20
            birth.setTextIsSelectable(true);
        }



        birth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatePickerDialog datePickerDialog = new DatePickerDialog(RegistrationActivity.this,R.style.CustomDatePickerDialogTheme, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.getDatePicker().setMaxDate(new Date().getTime());
                datePickerDialog.show();
            }
        });

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
                        MyCookieManager myCookieManager = new MyCookieManager(new URI(getIntent().getStringExtra("server_ip")));
                        myCookieManager.addMyCookie("token",getApplicationContext().getSharedPreferences(Constants.PREFERENCE_FILE,MODE_PRIVATE).getString("token",""));
                        CookieHandler.setDefault(myCookieManager);

                    }catch(Exception e){}

                    StringRequest stringRequest = new StringRequest(Request.Method.POST, getIntent().getStringExtra("server_ip")+"/mobile-app/register-user",
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
                                            getApplicationContext().getSharedPreferences(Constants.PREFERENCE_FILE,MODE_PRIVATE).edit().putString("server_ip",getIntent().getStringExtra("server_ip")).apply();

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
                        @Override
                        protected Response<String> parseNetworkResponse(NetworkResponse response) {
                            // since we don't know which of the two underlying network vehicles
                            // will Volley use, we have to handle and store session cookies manually
                            Log.i("response",response.headers.toString());
                            Map<String, String> responseHeaders = response.headers;
                            String rawCookies = responseHeaders.get("Set-Cookie");
                            if (rawCookies.contains("token"))
                                getApplicationContext().getSharedPreferences(Constants.PREFERENCE_FILE,MODE_PRIVATE).edit().putString("token",rawCookies.split("=")[1]).apply();

                            //Toast.makeText(getApplicationContext(), "Logged in successfully", Toast.LENGTH_SHORT).show();

                            Log.i("cookies",rawCookies);
                            return super.parseNetworkResponse(response);
                        }
                    };

                    Volley.newRequestQueue(getApplicationContext()).add(stringRequest);



                }

            }
        });


    }

    private void updateLabel() {
        String myFormat = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.ITALY);

        birth.setText(sdf.format(myCalendar.getTime()));
    }

}
