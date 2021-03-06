package br.com.activity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.AdapterView.OnItemClickListener;
import br.com.logica.ImagemAdapter;
import br.com.logica.ManageTag;

import com.example.tagedition.R;

public class FotosActivity extends Activity {
	
	private GridView grid;
	private File sdcard;
	private List<File> list;
	private ImagemAdapter adapter;
	
	ManageTag manageTag = ManageTag.getInstance();
	
	private void updateList(File[] files){	
		if (list == null){
			list = new ArrayList<File>();
		}else{
			list.removeAll(list);
		}
		
		for (int i = 0; i < files.length; i++) {
			if (files[i].isDirectory()) {
				list.add(0, files[i]);
			}else{
				if ((files[i].getName().contains(".jpg")) || (files[i].getName().contains(".jpeg"))){
					list.add(files[i]);
				}
			}	
		}
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fotos);
		
		sdcard = Environment.getExternalStorageDirectory();
		
		final File[] files = sdcard.listFiles();
		
		grid = (GridView) findViewById(R.id.gridView1);
		updateList(files);
		adapter = new ImagemAdapter(this, list);
		grid.setAdapter(adapter);
		
		grid.setOnItemClickListener(new OnItemClickListener() {
	        @Override
			public void onItemClick(AdapterView<?> a, View v, int position, long id) {
	        	if (list.get(position).isDirectory()){
	        		File[] filesAux = list.get(position).listFiles();
	        		File auxParent = list.get(position).getParentFile();
	        		updateList(filesAux);
	        		list.add(0, auxParent);
	        		adapter.notifyDataSetChanged();
	        	}else{
	        			manageTag.setImagenTag(list.get(position));
	        			Intent i = new Intent(FotosActivity.this,SetValueTag.class);
	    				startActivity(i);
	        			finish();
	        			
	        		
	        	}
	        }
	    });
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.fotos, menu);
		return true;
	}

}
