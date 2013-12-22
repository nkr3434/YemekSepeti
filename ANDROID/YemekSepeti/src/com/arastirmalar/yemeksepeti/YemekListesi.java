package com.arastirmalar.yemeksepeti;

import android.os.Build;
import android.os.Bundle;
import android.annotation.TargetApi;
import android.app.Activity;
import android.view.Menu;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class YemekListesi extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_yemek_listesi);
		getActionBar().setTitle("Yemek Listesi");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.yemek_listesi, menu);
		return true;
	}

}
