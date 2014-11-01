package com.example.showgallery;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.alert.R;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;

public class gallary_adapters extends BaseAdapter {
	int count=0;
	ImageLoader imageLoader;
	private LayoutInflater infalter;
	private ArrayList<Custom_gallery> data = new ArrayList<Custom_gallery>();

	public gallary_adapters(Context c, ImageLoader i) {
		infalter = (LayoutInflater) c
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.imageLoader = i;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return data.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	public void addAll(ArrayList<Custom_gallery> files) {

		try {
			this.data.clear();
			this.data.addAll(files);

		} catch (Exception e) {
			e.printStackTrace();
		}

		notifyDataSetChanged();
	}
	
	
	public ArrayList<Custom_gallery> getSelected() {
		ArrayList<Custom_gallery> dataT = new ArrayList<Custom_gallery>();

		for (int i = 0; i < data.size(); i++) {
			if (data.get(i).isSeleted) {
				dataT.add(data.get(i));
			}
		}

		return dataT;
	}
	
	public void changeSelection(View v, int position) {

		

		
	}
	@Override
	public View getView(final int position, View convertView, final ViewGroup parent) {
		// TODO Auto-generated method stub
		convertView = infalter.inflate(R.layout.item_gallery, null);
		final ImageView img = (ImageView) convertView
				.findViewById(R.id.image_gallery_loader);
		final FrameLayout frm = (FrameLayout) convertView
				.findViewById(R.id.frm_item);
		
		
		
		//Custom_gallery item = data.get(position);
		if(data.get(position).isSeleted)
		{
			img.setAlpha(1f);
			frm.setBackgroundResource(R.drawable.border_image);
					
		}
		else
		{
			img.setAlpha(0.65f);
			frm.setBackgroundResource(R.drawable.no_boder_image);
		}
		try {

			imageLoader.displayImage("file://" + data.get(position).sdcardPath,
					img, new SimpleImageLoadingListener() {
						@Override
						public void onLoadingStarted(String imageUri, View view) {
							img.setImageResource(R.drawable.no_media);
							super.onLoadingStarted(imageUri, view);
						}
					});

		} catch (Exception e) {
			e.printStackTrace();
		}
		if(data.get(position).ischoose)
		{
			img.setAlpha(1f);
			frm.setBackgroundResource(R.drawable.no_boder_image);
			return convertView;
		}
		convertView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (data.get(position).isSeleted) {
					data.get(position).isSeleted = false;
					img.setAlpha(0.65f);
					frm.setBackgroundResource(R.drawable.no_boder_image);
					count--;
				} else {
					
					if(count==6)
					{
						Toast.makeText(parent.getContext(),"Số lượng Vượt quá 6",Toast.LENGTH_LONG).show();
					}
					else
					{
						data.get(position).isSeleted = true;
						img.setAlpha(1.0f);
						frm.setBackgroundResource(R.drawable.border_image);
						count++;
					}
				}
			}
		});
		return convertView;
	}
	public void clearCache() {
		imageLoader.clearDiscCache();
		imageLoader.clearMemoryCache();
	}

	public void clear() {
		data.clear();
		notifyDataSetChanged();
	}
}
