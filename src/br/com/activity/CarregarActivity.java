package br.com.activity;

import java.io.File;
import java.io.FilenameFilter;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import br.com.logica.FileAdapter;
import br.com.logica.ListMusic;

import com.example.tagedition.R;

public class CarregarActivity extends Activity {
	
	private File sdcard;
	
	private List<File> listArray(File[] files){
		List<File> retorno = new ArrayList<File>();
		
		for (int i = 0; i < files.length; i++) {
			retorno.add(files[i]);
		}
		
		return retorno;
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_carregar);
		
		sdcard = Environment.getExternalStorageDirectory();
		
		final File[] files = sdcard.listFiles(new FilenameFilter() {
			
			@Override
			public boolean accept(File dir, String filename) {
				if (filename.toLowerCase().endsWith(".mp3")){
					return true;
				}
				return false;
			}
		});
		
		final ListView listView = (ListView) findViewById(R.id.files);
		List<File> list = listArray(files);
		FileAdapter adapter = new FileAdapter(getApplicationContext(), list);
		//ArrayAdapter<String> listAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);
		listView.setAdapter(adapter);
		
		listView.setOnItemClickListener(new OnItemClickListener() {
	        public void onItemClick(AdapterView<?> a, View v, int position, long id) {
	        	ListMusic.getInstance().addMusica(files[position]);
	        	MainActivity.fileAdapter.notifyDataSetChanged();
	            }
	        });
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.carregar, menu);
		return true;
	}

}
