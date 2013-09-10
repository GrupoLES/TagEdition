package br.com.activity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
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
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import br.com.logica.ManageTag;

import com.example.tagedition.R;

public class CapaInternetActivity extends Activity {
	
	private String search = "";
	private JsonRequest jsonRequest;
	private List<String> fotos;
	private int indice;
	private ImageView image1;
	private ImageView image2;
	private ImageView image3;
	private ImageView image4;
	private File file1;
	private File file2;
	private File file3;
	private File file4;
	private ManageTag manageTag = ManageTag.getInstance();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_capa_internet);
		search = getIntent().getExtras().getString("album");
		
		fotos = new ArrayList<String>();
		indice = 0;
		image1 = (ImageView) findViewById(R.id.imageView1);
		image2 = (ImageView) findViewById(R.id.imageView2);
		image3 = (ImageView) findViewById(R.id.imageView3);
		image4 = (ImageView) findViewById(R.id.imageView4);
		
		try {
			search = java.net.URLEncoder.encode(search, "UTF-8");
			if (!(search.equals(""))){
				jsonRequest = (JsonRequest) new JsonRequest().execute(search);
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		image1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (file1 != null){
					manageTag.setImagenTag(file1);
					Intent i = new Intent(CapaInternetActivity.this,SetValueTag.class);
    				startActivity(i);
					finish();
				}
			}
		});
		
		image2.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (file2 != null){
					manageTag.setImagenTag(file2);
					Intent i = new Intent(CapaInternetActivity.this,SetValueTag.class);
    				startActivity(i);
					finish();
				}		
			}
		});

		image3.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (file3 != null){
					manageTag.setImagenTag(file3);
					Intent i = new Intent(CapaInternetActivity.this,SetValueTag.class);
    				startActivity(i);
					finish();
				}	
			}
		});

		image4.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (file4 != null){
					manageTag.setImagenTag(file4);
					Intent i = new Intent(CapaInternetActivity.this,SetValueTag.class);
    				startActivity(i);
					finish();
				}
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.capa_internet, menu);
		return true;
	}
	
	private void copiaStream(File f, InputStream stream){
		if (f != null){
			 OutputStream out;
			try {
				out = new FileOutputStream(f);
				 byte buf[]=new byte[1024];
				 int len;
				 while((len=stream.read(buf))>0){
					 out.write(buf,0,len);
				 }
				 out.close();
				 stream.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void geraFile(InputStream stream, int indiceDownload){
		File f = null;
		switch (indiceDownload) {
		case 0:
			f=new File(getCacheDir().getAbsolutePath()+"/tempFile1.jpg");
			copiaStream(f, stream);
			file1 = f;
			break;
		case 1:
			f=new File(getCacheDir().getAbsolutePath()+"/tempFile2.jpg");
			copiaStream(f, stream);
			file2 = f;
			break;
		case 2:
			f=new File(getCacheDir().getAbsolutePath()+"/tempFile3.jpg");
			copiaStream(f, stream);
			file3 = f;
			break;
		case 3:
			f=new File(getCacheDir().getAbsolutePath()+"/tempFile4.jpg");
			copiaStream(f, stream);
			file4 = f;
			break;
		default:
			break;
		}
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
	                System.out.println("requisiçao");
	                InputStream a = response.getEntity().getContent();
	               
	                resultString = convertStreamToString(a);
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
	                InputStream result = response.getEntity().getContent();
	                geraFile(result, indice);
	    			this.finalize();
	    		} catch (MalformedURLException e) {
	    			e.printStackTrace();
	    			if (indice < 4){
	    				indice++;
	    				new ImageRequest().execute();
	    			}
	    		} catch (IOException e) {
	    			e.printStackTrace();
	    			if (indice < 4){
	    				indice++;
	    				new ImageRequest().execute();
	    			}
	    		} catch (URISyntaxException e) {
	    			e.printStackTrace();
	    			if (indice < 4){
	    				indice++;
	    				new ImageRequest().execute();
	    			}
	    		} catch (Throwable e) {
					e.printStackTrace();
					if (indice < 4){
	    				indice++;
	    				new ImageRequest().execute();
	    			}
				}
				return "";
	        }        

	        @Override
	        protected void onPostExecute(String result) {
	        	if ((indice < fotos.size()) && (indice < 4)){	
		        	ImageView myImage = null;
		        	switch (indice) {
						case 0:
							if (file1 != null){
								myBitmap = BitmapFactory.decodeFile(file1.getAbsolutePath());
								myImage = image1;
							}
							break;
						case 1:
							if (file2 != null){
								myBitmap = BitmapFactory.decodeFile(file2.getAbsolutePath());
								myImage = image2;
							}
							break;
						case 2:
							if (file3 != null){
								myBitmap = BitmapFactory.decodeFile(file3.getAbsolutePath());
								myImage = image3;
							}
							break;
						case 3:
							if (file4 != null){
								myBitmap = BitmapFactory.decodeFile(file4.getAbsolutePath());
								myImage = image4;
							}
							break;
						default:
							break;
					}
		        	if((myImage != null) && (myBitmap != null)){
		        		myImage.setImageBitmap(Bitmap.createScaledBitmap(myBitmap, 130, 160, false));
			        	indice++;
			        	new ImageRequest().execute();
		        	}else{
		        		if (indice < 4){
				        	indice++;
				        	new ImageRequest().execute();
		        		}
		        	}
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
