package br.com.activity;

import java.io.File;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import br.com.logica.FileAdapter;
import br.com.logica.ListMusic;
import br.com.player.PlayerActivity;

import com.example.tagedition.R;

public class MainActivity extends Activity {
	
	public static FileAdapter fileAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		final ListView listView = (ListView) findViewById(R.id.listView1);
		List<File> list = ListMusic.getInstance().getListMusic();
		fileAdapter = new FileAdapter(getApplicationContext(), list);
		
		listView.setAdapter(fileAdapter);
		create();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	private void create(){
		Button botaoEditarTag = (Button) findViewById(R.id.botaoEditar);
		Button botaoCarregar = (Button) findViewById(R.id.botaoCarregar);
		Button botaoTocar = (Button) findViewById(R.id.botaoTocar);
		
		botaoEditarTag.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(MainActivity.this,SetValueTag.class);
				startActivity(i);
					
			}
		});
		
		botaoCarregar.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(MainActivity.this,CarregarActivity.class);
				startActivity(i);
			}
		});
		
		botaoTocar.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent i = new Intent(MainActivity.this,PlayerActivity.class);
				startActivity(i);
				
			}
			
		});
	}

}
