package br.com.activity;

import com.example.tagedition.R;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
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
	}

}
