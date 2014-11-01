package com.example.alert;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Adapter extends BaseAdapter{
	private Activity activity;
	private ArrayList<Item> Items;
	private static LayoutInflater inflater=null;
	public Adapter(Activity a,ArrayList<Item>d){
		activity =a;
		Items =d;
		inflater =(LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return Items.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		convertView = inflater.inflate(R.layout.draw_list_item, null);
        ImageView imv_image=(ImageView)convertView.findViewById(R.id.imv_image);
        TextView txt_title = (TextView)convertView.findViewById(R.id.txt_title);
 
        Item item = Items.get(position);
 
        imv_image.setImageResource(item.IconFile);
        txt_title.setText(item.Name);
        
        return convertView;
	}

}
