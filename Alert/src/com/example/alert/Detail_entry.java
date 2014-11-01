package com.example.alert;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

public class Detail_entry extends Activity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.detail_page);
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
		    int value = extras.getInt("ENTRY_ID");
		    Toast.makeText(this,value+"", Toast.LENGTH_LONG).show();
		}
	}
}
