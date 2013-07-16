package br.com.activity;

import br.com.logica.ManageTag;

import com.example.tagedition.R;
import com.example.tagedition.R.layout;
import com.example.tagedition.R.menu;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SetValueTag extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_set_value_tag);
		create();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.set_value_tag, menu);
		return true;
	}

	Button botaoLimpar;
	Button botaoCancelar;
	Button botaoSalvar;

	EditText editTextAutor;
	EditText editTextGenero;
	EditText editTextAlbum;

	ManageTag singletonTag = ManageTag.getInstance();

	private void create() {
		botaoLimpar = (Button) findViewById(R.id.botaoLimpar);
		botaoCancelar = (Button) findViewById(R.id.botaoCancelar);
		botaoSalvar = (Button) findViewById(R.id.botaoSalvar);

		editTextAutor = (EditText) findViewById(R.id.editTextAutor);
		editTextGenero = (EditText) findViewById(R.id.editTextGenero);
		editTextAlbum = (EditText) findViewById(R.id.editTextAlbum);

		botaoLimpar.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				editTextAlbum.setText("");
				editTextAutor.setText("");
				editTextGenero.setText("");

			}
		});

		botaoSalvar.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				singletonTag.editTag(editTextAutor.getText().toString(),
						editTextAlbum.getText().toString(), editTextGenero
								.getText().toString());

			}
		});
		
		botaoCancelar.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});
	}

}
