package br.com.activity;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import br.com.logica.AdapterCheckList;
import br.com.logica.FileAdapter;
import br.com.logica.ListMusic;

import com.beaglebuddy.mp3.MP3;
import com.example.tagedition.R;

public class RemoveActivity extends Activity {
	
	private Button botaoRemoverSelecionados;
	private Button botaoRemoverTudo;
	private AdapterCheckList adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_remove);
		
		final ListView listView = (ListView) findViewById(R.id.listRemove);
		botaoRemoverSelecionados = (Button) findViewById(R.id.button1);
		botaoRemoverTudo = (Button) findViewById(R.id.button2);
		
		adapter = new AdapterCheckList(this, ListMusic.getInstance().getListMusic());
		listView.setAdapter(adapter);
		
		botaoRemoverSelecionados.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				List<File> aux = adapter.getSelecionados();
				
				for (File file : aux) {
					ListMusic.getInstance().getListMusic().remove(file);
				}
				MainActivity.fileAdapter.notifyDataSetChanged();
				finish();
			}
		});
		
		botaoRemoverTudo.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				List<File> list = ListMusic.getInstance().getListMusic();
				list.removeAll(list);
				MainActivity.fileAdapter.notifyDataSetChanged();
				finish();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.remove, menu);
		return true;
	}
}
