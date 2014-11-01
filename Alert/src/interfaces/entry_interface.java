package interfaces;
import java.util.List;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;
import db.com.Positioncs;
import db.com.entry_class;
import db.com.entry_info_class;
public interface entry_interface {
	@GET("/api/entry/")
	void get_position_service(
			Callback<entry_class> callback);
	@GET("/api/location/{lat}/{lon}/{r}")
	void get_position_gps(@Path("lat") double latitude,
			@Path("lon") double latitude2,@Path("r") int R,
			Callback<entry_class> callback);
	
	//
	
	@GET("/api/get_all_entry/{id}/{id1}")
	void get_entry_all(@Path("id") int id,
			@Path("id1") int id1,
			Callback<entry_info_class> callback);
	//
	
	
	@POST("/api/entrylatlon/")
	void post_position_region(@Body List<Positioncs> id, Callback<entry_class> callback);
	//
	@POST("/api/entryinfo/")
	void post_entry_info(@Body List<Integer> id, Callback<entry_info_class> callback);
	
}