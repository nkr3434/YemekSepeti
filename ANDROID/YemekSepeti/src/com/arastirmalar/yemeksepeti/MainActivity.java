package com.arastirmalar.yemeksepeti;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	Button buttonGiris, buttonKayitOl;
	EditText editTextKullaniciAdi, editTextSifre;
	
	private ProgressDialog pDialog;
	
	private static final String LOGIN_URL = "http://serkanayaz.com/webservice/login.php";
	JSONParser jsonParser = new JSONParser();
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_MESSAGE = "message";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getActionBar().setTitle("Yemek Sepeti");
        componentDefine();
        
        buttonGiris.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				/*MainActivityToYemekListesi MAtYL=new MainActivityToYemekListesi();
		        MAtYL.kullaniciAdi=editTextKullaniciAdi.getText().toString();
		        MAtYL.sifre=editTextSifre.getText().toString();
		        
		        bundle.putSerializable("veri", MAtYL);
		        intent.putExtras(bundle);
		        startActivity(intent);*/
				new AttemptLogin().execute();
			}
		});
        
        buttonKayitOl.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(MainActivity.this, KayitOl.class));
			}
		});
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    public void componentDefine() {
    	buttonGiris = (Button) findViewById(R.id.buttonGiris);
    	buttonKayitOl=(Button) findViewById(R.id.buttonKayitOl);
    	
    	editTextKullaniciAdi=(EditText) findViewById(R.id.editTextKullaniciAdi);
    	editTextSifre=(EditText) findViewById(R.id.editTextSifre);
    }
    
    
    
    
    class AttemptLogin extends AsyncTask<String, String, String> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(MainActivity.this);
			pDialog.setMessage("Kontrol Ediliyor...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}

		@Override
		protected String doInBackground(String... args) {
			// TODO Auto-generated method stub
			// Check for success tag
			int success;
			String username = editTextKullaniciAdi.getText().toString();
			String password = editTextSifre.getText().toString();
			try {
				// Building Parameters
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("username", username));
				params.add(new BasicNameValuePair("password", password));

				Log.d("request!", "baþlatýlýyor");
				// getting product details by making HTTP request
				JSONObject json = jsonParser.makeHttpRequest(LOGIN_URL, "POST", params);

				// check your log for json response
				Log.d("Login attempt", json.toString());

				// json success tag
				success = json.getInt(TAG_SUCCESS);
				if (success == 1) {
					Log.d("Giriþ Baþarýlý!", json.toString());
					// save user data
					SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
					Editor edit = sp.edit();
					edit.putString("username", username);
					edit.commit();
					
					Intent i = new Intent(MainActivity.this, YemekListesi.class);
					finish();
					startActivity(i);
					return json.getString(TAG_MESSAGE);
				} else {
					Log.d("Giriþ Yapýlamadý!", json.getString(TAG_MESSAGE));
					return json.getString(TAG_MESSAGE);
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}

			return null;

		}

		protected void onPostExecute(String file_url) {
			// dismiss the dialog once product deleted
			pDialog.dismiss();
			if (file_url != null) {
				Toast.makeText(MainActivity.this, file_url, Toast.LENGTH_LONG).show();
			}

		}

	}
    
}
