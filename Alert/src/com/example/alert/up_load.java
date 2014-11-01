package com.example.alert;
import java.io.File;
import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.Settings;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.showgallery.Custom_gallery;
import com.example.showgallery.gallary_adapters;
import com.example.showgallery.gallery;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.utils.StorageUtils;

public class up_load extends Activity implements LocationListener{

	GridView gridGallery;
	Handler handler;
	gallary_adapters adapter;

	ImageView imgSinglePick;
	Button btnGalleryPickMul;
	Button btnshowcamera;
	ImageLoader imageLoader2;
	//
	protected LocationManager locationManager;
	protected LocationListener locationListener;
	TextView txtLat;
	String lat;
	String provider;
	//
	TextView textlocation;
	//
	protected double latitude,longitude; 
	//
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.upload_entry);
		initImageLoader();
		init();
		//
		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);

		
		//
		Button location=(Button)findViewById(R.id.show_mylocation);
		//textlocation=(TextView)findViewById(R.id.locationview);
		location.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				 if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
					 textlocation.setText("Latitude:" + latitude + ", Longitude:" + longitude);
				    }else{
				    	showSettingsAlert("GPS");
				    }
			}
		});
		//
		
	}
	public void showSettingsAlert(String provider) {
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(
				up_load.this);

		alertDialog.setTitle(provider + " SETTINGS");

		alertDialog
				.setMessage(provider + " is not enabled! Want to go to settings menu?");

		alertDialog.setPositiveButton("Settings",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						Intent intent = new Intent(
								Settings.ACTION_LOCATION_SOURCE_SETTINGS);
						up_load.this.startActivity(intent);
					}
				});

		alertDialog.setNegativeButton("Cancel",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
					}
				});

		alertDialog.show();
	}
	private void initImageLoader() {
		try {
			String CACHE_DIR = Environment.getExternalStorageDirectory()
					.getAbsolutePath() + "/.temp_tmp";
			new File(CACHE_DIR).mkdirs();

			File cacheDir = StorageUtils.getOwnCacheDirectory(getBaseContext(),
					CACHE_DIR);

			DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
					.showImageOnLoading(R.drawable.no_media)
					.showImageForEmptyUri(R.drawable.no_media)
					.showImageOnFail(R.drawable.ic_error)
					.resetViewBeforeLoading(true).cacheInMemory(true)
					.cacheOnDisc(true).considerExifParams(true)
					.imageScaleType(ImageScaleType.EXACTLY)
					.bitmapConfig(Bitmap.Config.RGB_565).build();
			ImageLoaderConfiguration.Builder builder = new ImageLoaderConfiguration.Builder(
					getBaseContext())
					.defaultDisplayImageOptions(defaultOptions)
					.discCache(new UnlimitedDiscCache(cacheDir))
					.memoryCache(new WeakMemoryCache());

			ImageLoaderConfiguration config = builder.build();
			imageLoader2 = ImageLoader.getInstance();
			imageLoader2.init(config);

		} catch (Exception e) {

		}
	}
	private void init() {

		handler = new Handler();
		gridGallery = (GridView) findViewById(R.id.selectedgalery);
		gridGallery.setFastScrollEnabled(true);
		adapter = new gallary_adapters(getApplicationContext(), imageLoader2);
		gridGallery.setAdapter(adapter);
//		

		btnGalleryPickMul = (Button) findViewById(R.id.show_gallery);
		btnshowcamera=(Button)findViewById(R.id.show_camera);
		btnshowcamera.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
				startActivityForResult(intent,0);
			}
		});
		
		
		btnGalleryPickMul.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent i = new Intent(up_load.this,gallery.class);
				startActivityForResult(i, 200);
			}
		});

	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

	 if (requestCode == 200 && resultCode == Activity.RESULT_OK) {
			String[] all_path = data.getStringArrayExtra("all_path");

			ArrayList<Custom_gallery> dataT = new ArrayList<Custom_gallery>();

			for (String string : all_path) {
				Custom_gallery item = new Custom_gallery();
				item.sdcardPath = string;
				item.isSeleted=true;
				item.ischoose=true;
				dataT.add(item);
			}
			adapter.addAll(dataT);
		}
	}
	@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub
		latitude=location.getLatitude();
		longitude=location.getLongitude();
		
	}
	@Override
	public void onProviderDisabled(String arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
	}
}
