package com.example.alert;

import interfaces.entry_interface;

import java.util.ArrayList;
import java.util.List;

import notification.MyConstants;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.google.android.gcm.GCMRegistrar;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu.OnCloseListener;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu.OnOpenListener;

import db.com.entry_info_class;
import db.com.entry_info_class.entryinfo;
import db.com.getentry;

public class main extends Activity {
	ArrayList<Item> Items, Items2;
	Adapter adapter, adapter2;
	ListView listView, listView2, listview3;
	// phan trang
	int start = 1;
	int end = 5;
	//
	boolean loadmore = false;
	//
	private SlidingMenu menu;
	private SlidingMenu menu_right;
	ArrayList<Item_main_listview> Item3;
	static Adapter_main adapter3;
	// api
	entry_interface api;
	List<entryinfo> listentry = new ArrayList<entryinfo>();
	//
	RelativeLayout ls_ct;
	RelativeLayout progres_ct;
	android.view.ViewGroup.LayoutParams params_ls;
	android.view.ViewGroup.LayoutParams params_ct;
	//
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.main);
		/* notification*/
		String registrationId = GCMRegistrar
				.getRegistrationId(getApplicationContext());
		if (registrationId.equals("")) {
			GCMRegistrar.checkDevice(this);
			GCMRegistrar.checkManifest(this);
			GCMRegistrar.register(getApplicationContext(),MyConstants.SENDER_ID);
		}
		
		menu = new SlidingMenu(this);
		menu_right = new SlidingMenu(this);
		//
		menu.setMode(SlidingMenu.LEFT);
		// menu.setShadowDrawable(R.drawable.shadow);
		// menu.setShadowWidthRes(R.dimen.shadow_width);
		menu.setBehindWidth(600);
		menu.setFadeDegree(0.35f);
		// menu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		menu.attachToActivity(this, SlidingMenu.SLIDING_WINDOW);
		menu.setFadeEnabled(false);
		menu.setMenu(R.layout.drawer_left);
		//
		menu_right.setMode(SlidingMenu.RIGHT);
		// menu.setShadowDrawable(R.drawable.shadow);
		// menu_right.setShadowWidthRes(R.dimen.shadow_width);
		menu_right.setBehindWidth(600);
		// menu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		menu_right.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		menu_right.attachToActivity(this, SlidingMenu.SLIDING_WINDOW);
		menu_right.setFadeEnabled(false);
		menu_right.setMenu(R.layout.drawer_rigth);
		ImageButton menu_button = (ImageButton) findViewById(R.id.imvmenu);
		ImageButton location_button = (ImageButton) findViewById(R.id.imvlocation);
		//
		//
		location_button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(v.getContext(), map_location.class);
				startActivity(i);
			}
		});

		menu_button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				menu.toggle();
			}
		});

		menu.setOnOpenListener(new OnOpenListener() {

			@Override
			public void onOpen() {
				// TODO Auto-generated method stub
				menu_right.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
			}
		});
		menu.setOnCloseListener(new OnCloseListener() {

			@Override
			public void onClose() {
				// TODO Auto-generated method stub
				menu_right.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
			}
		});
		menu_right.setOnOpenListener(new OnOpenListener() {

			@Override
			public void onOpen() {
				// TODO Auto-generated method stub
				menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
			}
		});
		menu_right.setOnCloseListener(new OnCloseListener() {

			@Override
			public void onClose() {
				// TODO Auto-generated method stub
				menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
			}
		});

		Item3 = new ArrayList<Item_main_listview>();

		listview3 = (ListView) findViewById(R.id.main_list_new);
		adapter3 = new Adapter_main(this, Item3);
		listview3.setAdapter(adapter3);
		// getlist_entry(start,end);
		ls_ct=(RelativeLayout)findViewById(R.id.content);
		progres_ct=(RelativeLayout)findViewById(R.id.progressbar);
		params_ls= ls_ct.getLayoutParams();
		params_ct= progres_ct.getLayoutParams();
		listview3.setOnScrollListener(new OnScrollListener() {

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				// TODO Auto-generated method stub
				int threshold = 1;
				int count = listview3.getCount();

				if (scrollState == SCROLL_STATE_IDLE) {
					if (listview3.getLastVisiblePosition() >= count - threshold) {

						getlist_entry(start, end);
						
					}
					
				}

			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				}
		});
		
		getlist_entry(start, end);
		
	}

	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
	}

	public entry_interface getApi() {
		if (api == null) {
			api = getentry.getService();
		}
		return api;
	}
	public void show_hide_progress()// 1 load more false=show, load more true hide 
	{
		if(!loadmore)
		{
			params_ls.height=params_ls.height-40;
			params_ct.height=params_ct.height+40;
			ls_ct.setLayoutParams(params_ls);
			progres_ct.setLayoutParams(params_ct);
		}
		else
		{
			params_ls.height=params_ls.height+40;
			params_ct.height=params_ct.height-40;
			ls_ct.setLayoutParams(params_ls);
			progres_ct.setLayoutParams(params_ct);
		}
	}
	public void getlist_entry(int s, int e) {
		loadmore = false;
		show_hide_progress();
		Log.d("chay vo day", "s=" + s + " e=" + e);
		ArrayList<Integer> lid = new ArrayList<Integer>();
		lid.add(s);
		lid.add(e);
		getApi().post_entry_info(lid, new Callback<entry_info_class>() {

			@Override
			public void success(entry_info_class arg0, Response arg1) {
				
				String a="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRIPuP_4cUsMjdHr-v4ssQbeNS3nppx0MOEWIK3sc1rYTcqauyMuA";
				String b="https://encrypted-tbn1.gstatic.com/images?q=tbn:ANd9GcSb0lB5ahsWCaTnXbQefunk82Ztz-obb8w14QipNxXzSGSm27cNRERx65o";
				String c="Cơn lũ vừa qua đã gây thiệt hại nặng nề đối với các tỉnh miền Trung. Theo tổng hợp số liệu tính đến 21h00' ngày 22/10/2010 của Ban chỉ đạo phòng chống lụt bão Trung ương, đã có 77 người chết, 42 người bị thương, 05 người mất tích";
				
				String a3="https://encrypted-tbn3.gstatic.com/images?q=tbn:ANd9GcSSQang5olE8bLmwspeazF0uxCseeseROIJZ_eOBwPWY_7vLKMw";
				String b3="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ59YNyVi26j-y3Sy8yt6AG2S_Pmj_bjCFVD6geT57_4h2PSzKoYA";
				String c3="Những năm 80 của thế kỷ XX, bình quân mỗi năm cả nước xảy ra chừng 4000-5000 vụ TNGT, làm chết 2500-3000 người thì tới giai đoạn 2001-2008";
				
				
				
				
				String a4="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRIPuP_4cUsMjdHr-v4ssQbeNS3nppx0MOEWIK3sc1rYTcqauyMuA";
				String b4="https://encrypted-tbn1.gstatic.com/images?q=tbn:ANd9GcQpgO1r1P946TzrkIgeRGGCq4qywn4l1PqZJJcMe9EqGAzRWhxK";
				String c4="Các trận siêu bão với sức mạnh khủng khiếp luôn gây thiệt hại lớn về người và của ở những nơi mà chúng quét qua.";
				
				Item3.add(new Item_main_listview(
						1,
						a,b,"Phuong",c, "aaa"));
				
				adapter3.notifyDataSetChanged();
				
				Item3.add(new Item_main_listview(
						2,
						a3,b3,"Nam",c3, "aaa"));
				
				adapter3.notifyDataSetChanged();
				Item3.add(new Item_main_listview(
						3,
						a4,b4,"Thang",c4, "aaa"));
				
				
				
				
				

//				if (arg0.reponse.equals("true")) {
//					listentry = arg0.entryinfo;
//					if (listentry.size() >0) {
//						for (int i = 0; i < listentry.size(); i++) 
//						{
//							Item3.add(new Item_main_listview(
//									listentry.get(i).E_id,
//									listentry.get(i).E_avatar,listentry.get(i).E_Mainimg, listentry
//											.get(i).E_username, listentry
//											.get(i).E_content, "aaa"));
//							
//							
//						}
//						adapter3.notifyDataSetChanged();
//						start=end+1;
//						end+=5;
//						loadmore=true;
//						show_hide_progress();
//					}
//					else
//					{
//						loadmore=true;
//						show_hide_progress();
//					}
//
//				}
//				else
//				{
//					loadmore=true;
//					show_hide_progress();
//				}
//				

			}

			@Override
			public void failure(RetrofitError arg0) {
				loadmore=true;
				show_hide_progress();
			}

		});
	
	}
}
