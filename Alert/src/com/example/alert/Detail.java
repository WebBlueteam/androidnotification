package com.example.alert;

import globle_variable.globle_variable;

import java.util.ArrayList;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.example.alert.R.drawable;
import com.example.comment.Adapter_comment;
import com.example.comment.Utility;
import com.example.comment.item_comment;

public class Detail extends Activity{
	private ViewPager pager = null;
	private ArrayList<item_comment> Items;
	private Adapter_comment adapter;
	private ListView listview;
	private int check=0;
	private final int requestcode=10;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		globle_variable.list_mage=new ArrayList<Integer>();
		globle_variable.list_mage.add(drawable.anjp1);
		globle_variable.list_mage.add(drawable.anjp2);
		
		//
		super.onCreate(savedInstanceState);
		ActionBar actionBar = getActionBar();
		actionBar.hide();
		setContentView(R.layout.detail_page2);
		pager = (ViewPager) findViewById(R.id.pager);
		pager.setAdapter(new SampleAdapter());
		pager.setOffscreenPageLimit(9);

		///list comment
		listview=(ListView)findViewById(R.id.listview_comment);
		Items = new ArrayList<item_comment>();
		Items.add(new item_comment(1, "Phuong","5 day ago","Hahaha",R.drawable.avata));
		Items.add(new item_comment(2, "Ga","7 day ago","Xinh nhi:d",R.drawable.ga));
		Items.add(new item_comment(3, "SuDrNguyen","8 day ago","Dep nhi",R.drawable.nam));
		Items.add(new item_comment(4, "Thang","9 day ago","A du",R.drawable.thang));
		Items.add(new item_comment(1, "Phuong","5 day ago","Hahaha",R.drawable.avata));
		Items.add(new item_comment(2, "Ga","7 day ago","Xinh nhi:d",R.drawable.ga));
		Items.add(new item_comment(3, "SuDrNguyen","8 day ago","Dep nhi",R.drawable.nam));
		Items.add(new item_comment(4, "Thang","9 day ago","A du",R.drawable.thang));
		Items.add(new item_comment(1, "Phuong","5 day ago","Hahaha",R.drawable.avata));
		Items.add(new item_comment(2, "Ga","7 day ago","Xinh nhi:d",R.drawable.ga));
		Items.add(new item_comment(3, "SuDrNguyen","8 day ago","Dep nhi",R.drawable.nam));
		Items.add(new item_comment(4, "Thang","9 day ago","A du",R.drawable.thang));
		adapter=new Adapter_comment(this,Items);
		listview.setAdapter(adapter);
		Utility.setListViewHeightBasedOnChildren(listview);
		//bat su kiem click vao ban do de xem to hon
		RelativeLayout bando=(RelativeLayout)findViewById(R.id.mapshow);
		bando.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//
				RelativeLayout bando=(RelativeLayout)findViewById(R.id.mapshow);
				android.view.ViewGroup.LayoutParams params = bando.getLayoutParams();
				CircleImageView avata_user=(CircleImageView)findViewById(R.id.avata_details);
				MarginLayoutParams marginParams = new MarginLayoutParams(avata_user.getLayoutParams());
				//
				int top=marginParams.topMargin;
				if(check==0){
					params.height=params.height+300;
					bando.setLayoutParams(params);
					// set layout avata
					
				    marginParams.setMargins(10,top+350, 0, 0);
				    RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(marginParams);
				    avata_user.setLayoutParams(layoutParams);
				    check =1;
			    }
				else if(check==1){
					params.height=params.height-300;
					bando.setLayoutParams(params);
					// set layout avata
					
				    marginParams.setMargins(10,20, 0, 0);
				    RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(marginParams);
				    avata_user.setLayoutParams(layoutParams);
				    check =0;
			    }
				//
			}
		});
		
	}
	private  class SampleAdapter extends PagerAdapter {

		@Override
		public Object instantiateItem(ViewGroup container,final int position) {
			View page = getLayoutInflater().inflate(R.layout.view_page, container, false);
			 page.setTag("myview" + position);
			 ImageView img=(ImageView)page.findViewById(R.id.view_page_image);
			 img.setImageResource(globle_variable.list_mage.get(position));
			 //
			 img.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// mo activity moi truyen  qua no position
					Intent i=new Intent(v.getContext(),View_image.class);
					i.putExtra("position",Integer.toString(position));
					startActivityForResult(i,requestcode);
				}
			});
			 
			 //
		     container.addView(page);
		      return(page);
		    }

		    @Override
		    public void destroyItem(ViewGroup container, int position,
		                            Object object) {
		      container.removeView((View)object);
		    }

		    @Override
		    public int getCount() {
		      return(2);
		    }

		    @Override
		    public float getPageWidth(int position) {
		      return(1f);
		    }

		    @Override
		    public boolean isViewFromObject(View view, Object object) {
		      return(view == object);
		    }
	}
}
