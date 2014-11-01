package com.example.alert;

import interfaces.entry_interface;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import android.app.Dialog;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnCameraChangeListener;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.GoogleMap.OnMyLocationChangeListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Picasso;

import db.com.Myentry;
import db.com.Positioncs;
import db.com.entry_class;
import db.com.getentry;

public class map_location extends FragmentActivity implements
	OnMyLocationChangeListener, OnCameraChangeListener {
	GoogleMap googleMap;// map google
	Circle myCircle;// hinh tron quanh vi tri cua ban than
	entry_interface api;// api
	LatLng myPosition;// vi tri cua ban than
	List<Myentry> ls;
	List<Myentry> regionlist;
	int stt = 0;
	LatLngBounds bounds;// vung hien tai focus den
	// list maker region
	SupportMapFragment fm;
	
	//
	Map<Marker, Myentry> hasmap_t = new HashMap<Marker, Myentry>();
	Map<Marker, Myentry> hasmap_mylocation_t = new HashMap<Marker, Myentry>();
	//

	Map<String, Marker> ls_rg = new HashMap<String, Marker>();

	//
	Marker oldmarker = null;
	int style = 0;// loai marker mau do
	 RelativeLayout bando;
	//
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		// tat action bar
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.google_main_map);
		fm = (SupportMapFragment) getSupportFragmentManager()
				.findFragmentById(R.id.map);

		ls = new ArrayList<Myentry>();
		regionlist = new ArrayList<Myentry>();
		final ImageView img_mk = (ImageView) findViewById(R.id.img_marker);
		final Button theodoi = (Button) findViewById(R.id.theodoi);
		final ImageButton found = (ImageButton) findViewById(R.id.found_event);
		theodoi.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (stt == 0)// khong theo doi
				{
					theodoi.setText("On");
					stt = 1;
				} else {
					theodoi.setText("Off");
					stt = 0;
				}
			}
		});
		// xem nguy hiem o 1 vung khac
		found.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				clearmaker_region();
				reset_old_marker();
				style = 0;
				oldmarker = null;
				if (bounds != null) {
					List<Positioncs> po = new ArrayList<Positioncs>();
					Positioncs north = new Positioncs();
					north.lat = (float) bounds.northeast.latitude;
					north.lon = (float) bounds.northeast.longitude;

					Positioncs south = new Positioncs();
					south.lat = (float) bounds.southwest.latitude;
					south.lon = (float) bounds.southwest.longitude;

					po.add(south);
					po.add(north);
					danger_maker_region(po);
				}
				// TODO Auto-generated method stub

			}
		});
		int status = GooglePlayServicesUtil
				.isGooglePlayServicesAvailable(getBaseContext());

		// neu google service chua san sang
		if (status != ConnectionResult.SUCCESS) { // Google Play Services are
													// not available

			int requestCode = 10;
			Dialog dialog = GooglePlayServicesUtil.getErrorDialog(status, this,
					requestCode);
			dialog.show();

		} else {
			
			googleMap = fm.getMap();
			// Enabling MyLocation Layer of Google Map

			// getlist_entry();
			googleMap.setMyLocationEnabled(true);
			googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
			LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
			// Creating a criteria object to retrieve provider
			Criteria criteria = new Criteria();

			// Getting the name of the best provider
			String provider = locationManager.getBestProvider(criteria, true);

			// Getting Current Location
			Location location = locationManager.getLastKnownLocation(provider);
			if (location != null) {
				double latitude = location.getLatitude();
				double longitude = location.getLongitude();
				myPosition = new LatLng(latitude, longitude);
				set_up_circel(myPosition);

				// add maker for my location
				// set_up_maker_my_location();
				CameraPosition cameraPosition = new CameraPosition.Builder()
						.target(myPosition).zoom(15).build();
				googleMap.animateCamera(CameraUpdateFactory
						.newCameraPosition(cameraPosition));
				//
				danger_maker();
				googleMap.moveCamera(CameraUpdateFactory
						.newCameraPosition(cameraPosition));
				googleMap.setOnMyLocationChangeListener(this);
				googleMap.setOnCameraChangeListener(this);
				final TextView title = (TextView) findViewById(R.id.title_marker);
				final TextView content = (TextView) findViewById(R.id.conten_marker);
				bando=(RelativeLayout)findViewById(R.id.conten_entry);
				googleMap.setOnMapClickListener(new OnMapClickListener() {
					
					@Override
					public void onMapClick(LatLng arg0) {
						// TODO Auto-generated method stub
						MarkerOptions maker = new MarkerOptions()
						.position(arg0);

							Marker mk = googleMap.addMarker(maker);
					}
				});
				googleMap.setOnMarkerClickListener(new OnMarkerClickListener() {

					@Override
					public boolean onMarkerClick(final Marker marker) {
						// TODO Auto-generated method stub

						reset_old_marker();

						Myentry en = (Myentry) hasmap_mylocation_t.get(marker);
						Myentry en2 = (Myentry) hasmap_t.get(marker);
						if (en != null) {
							title.setText(en.E_title);
							content.setText(en.E_content);
							Picasso.with(getBaseContext()).load(en.E_Mainimg)
									.resize(40, 40).centerCrop().into(img_mk);

							style = 1;

						} else if (en2 != null) {
							title.setText(en2.E_title);
							content.setText(en2.E_content);
							Picasso.with(getBaseContext()).load(en2.E_Mainimg)
									.resize(40, 40).centerCrop().into(img_mk);
							style = 2;
						} else {
							title.setText("id: " + marker.getId() + "null");
						}
						oldmarker = marker;
						marker.setIcon(BitmapDescriptorFactory
								.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
						//fm.getView().setVisibility(View.INVISIBLE);
						show();
						return true;
					}
				});

			}
		}
	}

	public void reset_old_marker() {
		if (oldmarker != null) {
			if (style == 1)// neu trang thai =1 nghia la truoc no no thuoc
							// marker location
			{
				oldmarker.setIcon(BitmapDescriptorFactory
						.defaultMarker(BitmapDescriptorFactory.HUE_RED));
			} else if (style == 2) {
				oldmarker.setIcon(BitmapDescriptorFactory
						.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW));
			}
		}
	}

	public void set_up_circel(LatLng latLng) {
		double accuracy = 1000;
		if (myCircle == null) {
			CircleOptions circleOptions = new CircleOptions().center(latLng)
					// set center
					.radius(accuracy)
					// set radius in meters
					.fillColor(Color.parseColor("#500084d3"))
					.strokeColor(Color.BLUE).strokeWidth(3);

			myCircle = googleMap.addCircle(circleOptions);
		} else {
			myCircle.setCenter(latLng);
			myCircle.setRadius(accuracy);
		}
	}

	public entry_interface getApi() {
		if (api == null) {
			api = getentry.getService();
		}
		return api;
	}

	public boolean isMarkerOnArray(List<Myentry> array, Myentry m) {
		for (int c = 0; c < array.size(); c++) {
			Myentry current = array.get(c);
			if (current.E_id == m.E_id)
				return true;
		}
		return false;
	}

	public Marker get_key_hash(Myentry m,Map<Marker,Myentry>k) {
		
		for (Map.Entry<Marker, Myentry> entry : k.entrySet()) {
	        if (m.E_id==entry.getValue().E_id) {
	            return entry.getKey();
	        }
	    }
		
		return null;
	}

	public void danger_maker() {
		getApi().get_position_gps(myPosition.latitude, myPosition.longitude, 1,
				new Callback<entry_class>() {

					@Override
					public void success(entry_class arg0, Response arg1) {
						// TODO thanh cong
						clearmaker_location();
						hasmap_mylocation_t.clear();
						oldmarker = null;
						style = 0;
						if (arg0.reponse.compareTo("false") != 0) {
							ls = arg0.entry;
							for (int i = 0; i < ls.size(); i++) {

								// neu nhu phat hien trong region list co bai
								// thi xoa trong region list va add vao ls
								// xoa trong hasmap
								// add lai trong hasmap location

								if (isMarkerOnArray(regionlist, ls.get(i)))
								{
									
									Marker key=(Marker)get_key_hash(ls.get(i),hasmap_t);
									if(key==null)
									{
										Toast.makeText(map_location.this, "null roi",Toast.LENGTH_LONG).show();
									}
									else{
									key.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
									hasmap_mylocation_t.put(key,(Myentry)hasmap_t.get(key));
									hasmap_t.remove(key);
									
									regionlist.remove(ls.get(i));
									}//key.remove();
									// xoa trong region list

									// regionlist.remove(ls.get(i));

									// xoa marker trong listmarker region

									// lay marker tu ben hasmap_t chuyen sang
									// marker hasmap_location_t

									// xoa trong hasmap

								}
								else
								{
									MarkerOptions maker = new MarkerOptions()
											.position(
													new LatLng(ls.get(i).E_lat, ls
															.get(i).E_lon)).flat(
													true);
	
									Marker mk = googleMap.addMarker(maker);
									// listmaker_location.add(mk);
									hasmap_mylocation_t.put(mk, ls.get(i));
								}

							}
							// hasmap.putAll(hasmap_mylocation);
						}
					}

					@Override
					public void failure(RetrofitError arg0) {
						// TODO fail
						// ls=arg0;
						Toast.makeText(map_location.this,
								"load data not respone", Toast.LENGTH_LONG)
								.show();
					}
				});
	}

	public void danger_maker_region(List<Positioncs> po) {
		getApi().post_position_region(po, new Callback<entry_class>() {

			@Override
			public void success(entry_class arg0, Response arg1) {
				// TODO thanh cong

				if (arg0.reponse.compareTo("false") != 0) {
					regionlist.clear();
					// hasmap.clear();
					hasmap_t.clear();
					for (int i = 0; i < arg0.entry.size(); i++) {
						if (!isMarkerOnArray(ls, arg0.entry.get(i))) {
							regionlist.add(arg0.entry.get(i));
							MarkerOptions maker = new MarkerOptions()
									.position(
											new LatLng(arg0.entry.get(i).E_lat,
													arg0.entry.get(i).E_lon))
									.icon(BitmapDescriptorFactory
											.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW))
									.flat(true);

							Marker mkr = googleMap.addMarker(maker);
							// listmaker_region.add(mkr);
							// hasmap.put(mkr.getId(), arg0.entry.get(i));
							hasmap_t.put(mkr, arg0.entry.get(i));
						}

					}
				}
			}

			@Override
			public void failure(RetrofitError arg0) {
			}
		});
	}

	@Override
	public void onMyLocationChange(Location location) {
		// TODO Auto-generated method stub
		double latitude = location.getLatitude();
		double longitude = location.getLongitude();
		if (myPosition.latitude != latitude
				|| myPosition.longitude != longitude) {
			myPosition = new LatLng(latitude, longitude);
			danger_maker();
			set_up_circel(myPosition);
			CameraPosition cameraPosition = new CameraPosition.Builder()
					.target(myPosition).zoom(15).build();
			if (stt == 1) {
				googleMap.animateCamera(CameraUpdateFactory
						.newCameraPosition(cameraPosition));
			}
		}

	}

	@Override
	public void onCameraChange(CameraPosition arg0) {
		// TODO Auto-generated method stub
		bounds = googleMap.getProjection().getVisibleRegion().latLngBounds;
		// googleMap.clear();

	}

	// xoa maker tren 1 khu vuc
	public void clearmaker_region() {
		if (hasmap_t.size() != 0) {
			for (Marker k : hasmap_t.keySet()) {
				k.remove();

			}
		}
		hasmap_t.clear();
	}

	// xoa marker o vi tri cua minh
	public void clearmaker_location() {
		if (hasmap_mylocation_t.size() != 0) {
			for (Marker k : hasmap_mylocation_t.keySet()) {
				k.remove();

			}
		}
		hasmap_mylocation_t.clear();

	}
	public void show()
	{
		android.view.ViewGroup.LayoutParams params = bando.getLayoutParams();
		params.height=120;
		bando.setLayoutParams(params);
	}
}
