package polito.iot.iotmobileapplication.utils;



import java.net.CookieManager;
import java.net.HttpCookie;
import java.net.URI;
import java.util.Map;

/**
 * Created by user on 07/09/2018.
 */

public class MyCookieManager extends CookieManager {

    private URI uri;

    public MyCookieManager(URI uri) {

        super();
        this.uri = uri;
        getCookieStore().add(uri,new HttpCookie("app-id",Constants.APP_ID));

    }


    public void addMyCookie(String key, String value){


        getCookieStore().add(uri, new HttpCookie(key,value));


    }




}
