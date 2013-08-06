package br.com.activity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ImageView;

import com.example.tagedition.R;

public class CapaInternetActivity extends Activity {
	
	private String search = "";
	private JsonRequest jsonRequest;
	private List<String> fotos;
	private int indice;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_capa_internet);
		search = getIntent().getExtras().getString("album");
		try {
			search = java.net.URLEncoder.encode(search, "UTF-8");
			fotos = new ArrayList<String>();
			indice = 0;
			if (!(search.equals(""))){
				jsonRequest = (JsonRequest) new JsonRequest().execute(search);
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.capa_internet, menu);
		return true;
	}
	
	 private class JsonRequest extends AsyncTask<String, Void, String> {
		 	
		 	private String resultString;
		 	
		 	private void updateUrls(){
		 		try {
		 			String jsonStringAux = new JSONObject(resultString).getString("responseData");
		 			String jsonStringAux2 = new JSONObject(jsonStringAux).getString("results");
					JSONArray json = new JSONArray(jsonStringAux2);
					for (int j = 0; j < json.length(); j++) {
						fotos.add(json.getJSONObject(j).getString("url"));
					}
				} catch (JSONException e) {
					System.out.println(e.getMessage());
				}
		 	}
		 	
		 	private String convertStreamToString(InputStream is) {
		 		String line;
		 		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		 		StringBuilder builder = new StringBuilder();
		 		try {
					while((line = reader.readLine()) != null) {
						 builder.append(line);
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		 		return builder.toString();
			}
		 	
	        @Override
	        protected String doInBackground(String... params) {
	        	HttpResponse response;
	        	try {
	    			HttpClient client = new DefaultHttpClient();
	                URI website = new URI("https://ajax.googleapis.com/ajax/services/search/images?v=1.0&q="+params[0]);
	                HttpGet request = new HttpGet();
	                request.setURI(website);
	                response = client.execute(request);
	                resultString = convertStreamToString(response.getEntity().getContent());
	                this.finalize();
	    		} catch (MalformedURLException e) {
	    			e.printStackTrace();
	    		} catch (IOException e) {
	    			e.printStackTrace();
	    		} catch (URISyntaxException e) {
	    			e.printStackTrace();
	    		} catch (Throwable e) {
					e.printStackTrace();
				}
	        	return "";
	        }        

	        @Override
	        protected void onPostExecute(String result) {
	        	updateUrls();
	        	new ImageRequest().execute();
	        }

	        @Override
	        protected void onPreExecute() {
	        }

	        @Override
	        protected void onProgressUpdate(Void... values) {
	        }
	    }
	 
	 private class ImageRequest extends AsyncTask<String, Void, String> {
		 
		 	private Bitmap myBitmap;
		 
	        @Override
	        protected String doInBackground(String... params) {
	        	try {
	        		
	    			HttpClient client = new DefaultHttpClient();
	                URI website = new URI(fotos.get(indice));
	                HttpGet request = new HttpGet();
	                request.setURI(website);
	                HttpResponse response = client.execute(request);          
	                myBitmap = BitmapFactory.decodeStream(response.getEntity().getContent());
	    			this.finalize();
	    		} catch (MalformedURLException e) {
	    			e.printStackTrace();
	    		} catch (IOException e) {
	    			e.printStackTrace();
	    		} catch (URISyntaxException e) {
	    			e.printStackTrace();
	    		} catch (Throwable e) {
					e.printStackTrace();
				}
				return "";
	        }        

	        @Override
	        protected void onPostExecute(String result) {
	        	if ((indice < fotos.size()) && (indice < 4) && (myBitmap != null)){	
		        	ImageView myImage;
		        	switch (indice) {
						case 0:
							myImage = (ImageView) findViewById(R.id.imageView1);
							break;
						case 1:
							myImage = (ImageView) findViewById(R.id.imageView2);
							break;
						case 2:
							myImage = (ImageView) findViewById(R.id.imageView3);
							break;
						case 3:
							myImage = (ImageView) findViewById(R.id.imageView4);
							break;
						default:
							myImage = (ImageView) findViewById(R.id.imageView1);
							break;
					}
		        	myImage.setImageBitmap(Bitmap.createScaledBitmap(myBitmap, 130, 160, false));
		        	indice++;
		        	new ImageRequest().execute();
	        	}
	        }

	        @Override
	        protected void onPreExecute() {
	        }

	        @Override
	        protected void onProgressUpdate(Void... values) {
	        }
	    }

}
