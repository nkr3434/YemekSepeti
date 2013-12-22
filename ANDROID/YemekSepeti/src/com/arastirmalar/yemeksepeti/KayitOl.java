package com.arastirmalar.yemeksepeti;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class KayitOl extends Activity {
	
	Button buttonKayitOl;
	EditText editTextKullaniciAdi, editTextSifre;
	
    private ProgressDialog pDialog;
    JSONParser jsonParser = new JSONParser();
    
    private static final String REGISTER_URL = "http://serkanayaz.com/webservice/register.php";
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_kayit_ol);
		getActionBar().setTitle("Kayýt Ol");
		componentDefine();
		
		buttonKayitOl.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new CreateUser().execute();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.kayit_ol, menu);
		return true;
	}
	
	public void componentDefine() {
    	buttonKayitOl=(Button) findViewById(R.id.buttonKayitOlGonder);
    	
    	editTextKullaniciAdi=(EditText) findViewById(R.id.editTextKayitOlKullaniciAdi);
    	editTextSifre=(EditText) findViewById(R.id.editTextKayitOlSifre);
    }

	
	
	
class CreateUser extends AsyncTask<String, String, String> {

		
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(KayitOl.this);
            pDialog.setMessage("Kullanýcý Oluþturuluyor...");
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
 
                Log.d("request!", "starting");
                
                //Posting user data to script 
                JSONObject json = jsonParser.makeHttpRequest(REGISTER_URL, "POST", params);
 
                // full json response
                Log.d("Registering attempt", json.toString());
 
                // json success element
                success = json.getInt(TAG_SUCCESS);
                if (success == 1) {
                	Log.d("Kullanýcý Oluþturuldu!", json.toString());              	
                	finish();
                	return json.getString(TAG_MESSAGE);
                }else{
                	Log.d("Kullanýcý Kaydý Baþarýsýz!", json.getString(TAG_MESSAGE));
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
            if (file_url != null){
            	Toast.makeText(KayitOl.this, file_url, Toast.LENGTH_LONG).show();
            }
 
        }
		
	}
}
