package polito.iot.iotmobileapplication.utils;

/**
 * Created by user on 11/09/2018.
 */

public class Message {

    private String title,body,send_date;

    public Message(String title, String body, String send_date) {
        this.title = title;
        this.body = body;
        this.send_date = send_date;
    }

    public Message() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getSend_date() {
        return send_date;
    }

    public void setSend_date(String send_date) {
        this.send_date = send_date;
    }
}
