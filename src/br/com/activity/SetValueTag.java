package br.com.activity;

import java.io.File;
import java.io.IOException;

import org.cmc.music.common.ID3WriteException;
import org.cmc.music.metadata.IMusicMetadata;
import org.cmc.music.metadata.MusicMetadata;
import org.cmc.music.metadata.MusicMetadataSet;
import org.cmc.music.myid3.MyID3;

import br.com.logica.ListMusic;
import br.com.logica.ManageTag;

import com.example.tagedition.R;

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
	
	ListMusic listMusic = ListMusic.getInstance();
	
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
				for (int i = 0; i < listMusic.getListMusic().size(); i++) {
					File file = listMusic.getListMusic().get(i);
					MusicMetadataSet music = null;
					
					try {
						music = new MyID3().read(file);
						IMusicMetadata musicMetaData = music.getSimplified();
						
						MusicMetadata musicaMeta = new MusicMetadata("");
						if(!("".equals(editTextAlbum.getText().toString()))){
							musicaMeta.setAlbum(editTextAlbum.getText().toString());
						}else{
							musicaMeta.setAlbum(musicMetaData.getAlbum());
						}
						if(!("".equals(editTextAutor.getText().toString()))){
							musicaMeta.setArtist(editTextAutor.getText().toString());
						}else{
							musicaMeta.setArtist(musicMetaData.getArtist());
						}
						if(!("".equals(editTextGenero.getText().toString()))){
							musicaMeta.setArtist(editTextGenero.getText().toString());
						}else{
							musicaMeta.setGenre(musicMetaData.getGenre());
						}
						
						try {
							new MyID3().write(file, file, music, musicaMeta);
						} catch (ID3WriteException e) {
							System.out.println("Erro ao escrever a musica: "+file);
						}
						
					} catch (IOException e) {
						System.out.println("Erro ao ler:"+file);
					}
					
				}

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
