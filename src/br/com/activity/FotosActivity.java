package br.com.activity;

import com.example.tagedition.R;
import com.example.tagedition.R.layout;
import com.example.tagedition.R.menu;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class FotosActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fotos);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.fotos, menu);
		return true;
	}

}
