package com.example.comment;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.alert.R;

public class Adapter_comment extends BaseAdapter{
	private Activity activity;
	private ArrayList<item_comment> Items;
	private static LayoutInflater inflater=null;
	public Adapter_comment(Activity a,ArrayList<item_comment>d){
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
		convertView = inflater.inflate(R.layout.listview_comment_item, null);
        ImageView imv_image=(ImageView)convertView.findViewById(R.id.avata_comment);
        TextView user_name = (TextView)convertView.findViewById(R.id.username_comment);
        TextView time_cm = (TextView)convertView.findViewById(R.id.time_comment);
        TextView content = (TextView)convertView.findViewById(R.id.content_comment);
 
        item_comment item = Items.get(position);
        imv_image.setImageResource(item.Id_avata);
        user_name.setText(item.User_name);
        time_cm.setText(item.Time_comment);
        content.setText(item.Content_comment);  
        return convertView;
	}

}
