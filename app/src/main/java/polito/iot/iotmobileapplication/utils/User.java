package polito.iot.iotmobileapplication.utils;


/**
 * Created by user on 15/04/2018.
 */
public class User {

    public String name;
    public String surname;
    public String email;
    public String phone;
    public String password;
    public String address;
    public String birthday;
    public String end_date;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String name, String surname, String email, String phone, String password, String address, String birthday, String end_date) {
        this.name = name;
        this.email = email;
        this.surname = surname;
        this.phone = phone;
        this.address = address;
        this.password = password;
        this.birthday = birthday;
        this.end_date = end_date;
    }





}