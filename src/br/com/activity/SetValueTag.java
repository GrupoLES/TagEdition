package br.com.activity;

import java.io.File;
import java.io.IOException;

import org.cmc.music.common.ID3WriteException;
import org.cmc.music.metadata.IMusicMetadata;
import org.cmc.music.metadata.MusicMetadata;
import org.cmc.music.metadata.MusicMetadataSet;
import org.cmc.music.myid3.MyID3;
import org.farng.mp3.MP3File;
import org.farng.mp3.TagConstant;

import br.com.logica.ListMusic;
import br.com.logica.ManageTag;

import com.example.tagedition.R;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
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
			boolean alteracao = false;
			String autor = editTextAutor.getText().toString();
			String album = editTextAlbum.getText().toString();
			String genero = editTextGenero.getText().toString();
			
			
			@Override
			public void onClick(View v) {
				MP3File mp3 = null;
				File file = null;
				for (int i = 0; i < listMusic.getListMusic().size(); i++) {
					file = listMusic.getListMusic().get(i);
					if(file.toString().contains(".mp3")){
						try {
							
							mp3 = new MP3File(file);
							
							if(!(editTextAlbum.getText().toString().trim().equals(""))){
								mp3.getID3v2Tag().setAlbumTitle(editTextAlbum.getText().toString());
								alteracao = true;
							}
							if(!(editTextAutor.getText().toString().trim().equals(""))){
								mp3.getID3v2Tag().setAuthorComposer(editTextAutor.getText().toString());
								alteracao = true;
							}
							if(!(editTextGenero.getText().toString().trim().equals(""))){
								mp3.getID3v2Tag().setSongGenre(editTextGenero.getText().toString());
								alteracao = true;
							}
							
						} catch (Exception e) {
							System.out.println("Erro ao carregar: "+file.toString());
						}
						try {
							mp3.save(file, TagConstant.MP3_FILE_SAVE_OVERWRITE);
							file = new File(file.toString().replace(".mp3", ".original.mp3"));
							file.deleteOnExit();
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
					alert.setTitle("Confirma‹o");
					alert.setMessage("Autor:"+autor+"\nAlbum: "+album+"\nGenero: "+genero+ "\nforam alteradas com sucesso!");
					alert.setNeutralButton("Ok", new  DialogInterface.OnClickListener() {
						public  void  onClick(DialogInterface dialog, int  whichButton) { 
							finish();
						}
					});
					alert.show();
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
