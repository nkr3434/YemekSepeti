package com.arastirmalar.yemeksepeti;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class YemekListesi extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_yemek_listesi);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.yemek_listesi, menu);
		return true;
	}

}
