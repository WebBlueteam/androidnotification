package com.example.alert;


import java.util.Calendar;

import globle_variable.globle_variable;
import android.R.bool;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.ImageView;

public class View_image extends Activity{
	private ViewPager pager_img = null;
	private int num_img;
	private globle_variable gl;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		ActionBar actionBar = getActionBar();
		//
		gl=new globle_variable();
		//
		actionBar.hide();
		setContentView(R.layout.show_image_detail);
		//get result from detail
		Bundle extrast=getIntent().getExtras();
		String pos=extrast.getString("position");
		// ser so trang
		num_img=gl.list_mage.size();
		pager_img = (ViewPager) findViewById(R.id.pager_image);
		pager_img.setAdapter(new SampleAdapter_img());
		pager_img.setOffscreenPageLimit(9);
		//set trang hien thi trung vs ben detail click
		pager_img.setCurrentItem(Integer.parseInt(pos));
		//bat su kien quay lai trang detail
		pager_img.setOnTouchListener(new OnTouchListener() {
			int x=0;
			int y=0;
			 private long startClickTime;
			 private boolean longclick=false;
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if(event.getAction()==MotionEvent.ACTION_UP){
					longclick=false;
				}
				else if(event.getActionMasked()==MotionEvent.ACTION_DOWN){
					x=(int)event.getX();
					y=(int)event.getY();
					Log.w("nhan nut", "x="+x+"y="+y);
					if(longclick==false){
						longclick=true;
						startClickTime = Calendar.getInstance().getTimeInMillis();
					}
				}
				else if(event.getActionMasked()==MotionEvent.ACTION_MOVE){
					
					int x1=(int)event.getX();
					int y1=(int)event.getY();
					Log.w("tinh toan:","y1-y:="+y1+"-"+y+"="+(y1-y));
					Log.w("loglick=",longclick+"");
					if(longclick==true){
						
						if(y1>y&&y1-y>150){
							long clickDuration = Calendar.getInstance().getTimeInMillis() - startClickTime;
							Log.w("time",clickDuration+"");
							if(clickDuration>1000){
								finish();
								overridePendingTransition(android.R.anim.fade_in,R.anim.top_out);
								longclick=false;
							}
						}
						
						
					}
				
				}
				return false;
			}
		});
	}
	private  class SampleAdapter_img extends PagerAdapter {
		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			View page = getLayoutInflater().inflate(R.layout.view_page, container, false);
			page.setTag("myview" + position);
			ImageView img=(ImageView)page.findViewById(R.id.view_page_image);
			img.setImageResource(gl.list_mage.get(position));
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
	      return(num_img);
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
