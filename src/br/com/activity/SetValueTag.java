package br.com.activity;

import java.io.File;
import br.com.logica.ListMusic;
import br.com.logica.ManageTag;
import com.beaglebuddy.mp3.MP3;
import com.beaglebuddy.mp3.enums.PictureType;
import com.example.tagedition.R;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

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
	
	ListMusic listMusic = ListMusic.getInstance();
	ImageView imageView;
	ImageButton botaoLimpar;
	ImageButton botaoCancelar;
	ImageButton botaoSalvar;
	ImageButton botaoCapa;
	ImageButton botaoCapaInternet;

	EditText editTextAutor;
	EditText editTextGenero;
	EditText editTextAlbum;

	ManageTag singletonTag = ManageTag.getInstance();

	private void create() {
		imageView = (ImageView) findViewById(R.id.ImageCapaMusica);
		botaoLimpar = (ImageButton) findViewById(R.id.botaoLimpar);
		botaoCancelar = (ImageButton) findViewById(R.id.botaoCancelar);
		botaoSalvar = (ImageButton) findViewById(R.id.botaoSalvar);
		botaoCapa = (ImageButton) findViewById(R.id.botaoCapa);
		botaoCapaInternet = (ImageButton) findViewById(R.id.botaoInternet);
		
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
			boolean alteracao = false;
			String autor = editTextAutor.getText().toString();
			String album = editTextAlbum.getText().toString();
			String genero = editTextGenero.getText().toString();
			
			
			@Override
			public void onClick(View v) {
				MP3 mp3 = null;
				File file = null;
				for (int i = 0; i < listMusic.getListMusic().size(); i++) {
					file = listMusic.getListMusic().get(i);
					if(file.toString().contains(".mp3")){
						try {
							
							mp3 = new MP3(file);
							
							if(!(editTextAlbum.getText().toString().trim().equals(""))){
								mp3.setAlbum(editTextAlbum.getText().toString());
								alteracao = true;
							}
							if(!(editTextAutor.getText().toString().trim().equals(""))){
								mp3.setMusicBy(editTextAutor.getText().toString());
								alteracao = true;
							}
							if(!(editTextGenero.getText().toString().trim().equals(""))){
								mp3.setMusicType(editTextGenero.getText().toString());
								alteracao = true;
							}
							if(singletonTag.getImagenTag() != null){
								
								mp3.setPicture(PictureType.FRONT_COVER, singletonTag.getImagenTag());
								
								
								
							}
							
						} catch (Exception e) {
							System.out.println("Erro ao carregar: "+file.toString());
						}
						try {
							mp3.save();
						} catch (Exception e) {
							System.out.println("Erro ao escrever a musica: "+file);
							alteracao = false;
						}
					}
				if(alteracao){
					
					autor = editTextAutor.getText().toString();
					album = editTextAlbum.getText().toString();
					genero = editTextGenero.getText().toString();
					
					AlertDialog.Builder alert = new AlertDialog.Builder(SetValueTag.this);
					alert.setTitle("Confirmação");
					alert.setMessage("Autor:"+autor+"\nAlbum: "+album+"\nGenero: "+genero+ "\nforam alteradas com sucesso!");
					alert.setNeutralButton("Ok", new  DialogInterface.OnClickListener() {
						@Override
						public  void  onClick(DialogInterface dialog, int  whichButton) { 
							finish();
						}
					});
					alert.show();
				}
				}
				singletonTag.setImagenTag(null);
			}
		});		
		botaoCancelar.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});
		
		botaoCapa.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(SetValueTag.this,FotosActivity.class);
				startActivity(i);
			}
		});
		
		botaoCapaInternet.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(SetValueTag.this,CapaInternetActivity.class).putExtra("album", editTextAlbum.getText().toString());
				startActivity(i);
			}
		});
	}

}
