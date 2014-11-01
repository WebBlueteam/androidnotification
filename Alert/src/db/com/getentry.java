package db.com;
import interfaces.entry_interface;
import retrofit.*;
public class getentry {
	public static String API_URL = "http://aler.somee.com/";
	public static String API_URL2="http://172.29.70.221:8081/";
    public static entry_interface getService() {
        return new RestAdapter.Builder()
                    .setEndpoint(API_URL)
                    .build()
                    .create(entry_interface.class);
    }
}
