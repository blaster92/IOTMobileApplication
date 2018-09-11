package polito.iot.iotmobileapplication.core;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import polito.iot.iotmobileapplication.R;
import polito.iot.iotmobileapplication.utils.User;

/**
 * Created by user on 06/08/2018.
 */

public class ProfileFragment extends Fragment {

    private TextInputEditText name,surname,phone,email,birthday,address;
    private TextView end_date,status;

    private SimpleDateFormat format;

    public interface ProfileInterface{

        void findUserProfile(Intent intent);


    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.fragment_profile,null);

        name = (TextInputEditText) view.findViewById(R.id.name_edit);
        surname = (TextInputEditText) view.findViewById(R.id.surname_edit);
        phone = (TextInputEditText) view.findViewById(R.id.phone_edit);
        email = (TextInputEditText) view.findViewById(R.id.email_edit);
        address = (TextInputEditText) view.findViewById(R.id.address_edit);
        birthday = (TextInputEditText) view.findViewById(R.id.date_of_birth_edit);
        end_date = (TextView) view.findViewById(R.id.end_date);
        status = (TextView) view.findViewById(R.id.state);

        format = new SimpleDateFormat("yyyy-MM-dd");


        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public void setUserDetails(User user){

        name.setText(user.name);
        surname.setText(user.surname);
        birthday.setText(user.birthday);
        address.setText(user.address);
        phone.setText(user.phone);
        email.setText(user.email);

        try {
            Date d = format.parse(user.end_date);
            Calendar c = Calendar.getInstance();
            c.setTime(d);
            end_date.setText(c.get(Calendar.DAY_OF_MONTH)+"/"+c.get(Calendar.MONTH)+"/"+c.get(Calendar.YEAR));

            if (d.before(new Date()))
                status.setText("EXPIRED");
            else
                status.setText("VALID");

        } catch (ParseException e) {
            e.printStackTrace();
        }






    }
}
