package br.com.activity;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import br.com.logica.AdapterCheckList;
import br.com.logica.ListMusic;
import com.beaglebuddy.mp3.MP3;
import com.example.tagedition.R;

public class CarregarActivity extends Activity {
	
	private File sdcard;
	private List<File> list;
	private ListView listView;
	private AdapterCheckList adapter;
	private File[] diretorioAtual;
	public static TextView textViewAlbum = null;
	public static TextView textViewAutor = null;
	public static TextView textViewGenero = null;
	
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
				if (files[i].getName().contains(".mp3")){
					list.add(files[i]);
				}
			}	
		}
	}

	File file = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_carregar);
		
		textViewAutor = (TextView) findViewById(R.id.textAutor);
		textViewAlbum = (TextView) findViewById(R.id.textAlbum);
		textViewGenero = (TextView) findViewById(R.id.textGenero);
		
		//if(ListMusic.getInstance().getMusicInformation() == null){
			textViewAutor.setText("Autor: "+"Campo Vázio");
			textViewAlbum.setText("Album: "+"Campo Vázio");
			textViewGenero.setText("Genero: "+"Campo Vázio");
//		}else{
//			if(ListMusic.getInstance().getMusicInformation().getAlbum()!=null && ListMusic.getInstance().getMusicInformation().getAlbum().length()>20){
//				String album = String.valueOf(ListMusic.getInstance().getMusicInformation().getAlbum()).length()>12 ? String.valueOf(ListMusic.getInstance().getMusicInformation().getAlbum()).substring(0,20)+"...":String.valueOf(ListMusic.getInstance().getMusicInformation().getAlbum());
//				textViewAlbum.setText("Album: "+album);
//			}
//			if(ListMusic.getInstance().getMusicInformation().getMusicBy() !=null && ListMusic.getInstance().getMusicInformation().getMusicBy().length()> 20){
//				String musicBy = String.valueOf(ListMusic.getInstance().getMusicInformation().getMusicBy()).length()>12 ? String.valueOf(ListMusic.getInstance().getMusicInformation().getMusicBy()).substring(0,20)+"...":String.valueOf(ListMusic.getInstance().getMusicInformation().getMusicBy());
//				textViewAutor.setText("Autor: "+musicBy);
//			}
//			if(ListMusic.getInstance().getMusicInformation().getMusicType() != null && ListMusic.getInstance().getMusicInformation().getMusicType().length()>20){
//				String genero = String.valueOf(ListMusic.getInstance().getMusicInformation().getMusicType()).length()>12 ? String.valueOf(ListMusic.getInstance().getMusicInformation().getMusicType()).substring(0,20)+"...":String.valueOf(ListMusic.getInstance().getMusicInformation().getMusicType());
//				textViewGenero.setText("Genero: "+genero);
//			}
//			
//		}
//		
		Button botaoAdd = (Button) findViewById(R.id.addSelecionadas);
		Button botaoAddTodas = (Button) findViewById(R.id.addTodas);
		
		botaoAdd.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				for (File file : adapter.getSelecionados()) {
					if (!(ListMusic.getInstance().getListMusic().contains(file))){
						ListMusic.getInstance().addMusica(file);
					}
				}
				MainActivity.fileAdapter.notifyDataSetChanged();
				finish();
			}
		});
		
		botaoAddTodas.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				for (int i = 0; i < diretorioAtual.length; i++) {
					if(!diretorioAtual[i].isDirectory()){
						if(diretorioAtual[i].getName().contains(".mp3")){
							if(!ListMusic.getInstance().getListMusic().contains(diretorioAtual[i])){
								ListMusic.getInstance().addMusica(diretorioAtual[i]);
							}
						}
						
					}
				}

				MainActivity.fileAdapter.notifyDataSetChanged();
				finish();
				
			}
		});
		
		sdcard = Environment.getExternalStorageDirectory();
		
		final File[] files = sdcard.listFiles();
		diretorioAtual = sdcard.listFiles();
		listView = (ListView) findViewById(R.id.files);
		updateList(files);
		adapter = new AdapterCheckList(getApplicationContext(), list);
		//ArrayAdapter<String> listAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);
		listView.setAdapter(adapter);
		
		listView.setOnItemClickListener(new OnItemClickListener() {
	        @Override
			public void onItemClick(AdapterView<?> a, View v, int position, long id) {
	        	if (list.get(position).isDirectory()){
	        		File[] filesAux = list.get(position).listFiles();
	        		File auxParent = list.get(position).getParentFile();
	        		diretorioAtual = filesAux;
	        		updateList(filesAux);
	        		list.add(0, auxParent);
	        		adapter.notifyDataSetChanged();
	        	}else{
	        		System.out.println("entrou");
	        		try {
	        			System.out.println("entrou");
						MP3 m = new MP3(list.get(position));
						if(m.getAlbum()!=null && m.getAlbum().length()>20){
							String album = String.valueOf(m.getAlbum()).length()>12 ? String.valueOf(m.getAlbum()).substring(0,20)+"...":String.valueOf(m.getAlbum());
							textViewAlbum.setText("Album: "+album);
						}
						if(m.getMusicBy() !=null && m.getMusicBy().length()> 20){
							String musicBy = String.valueOf(m.getMusicBy()).length()>12 ? String.valueOf(m.getMusicBy()).substring(0,20)+"...":String.valueOf(m.getMusicBy());
							textViewAutor.setText("Autor: "+musicBy);
						}
						if(m.getMusicType() != null && m.getMusicType().length()>20){
							String genero = String.valueOf(m.getMusicType()).length()>12 ? String.valueOf(m.getMusicType()).substring(0,20)+"...":String.valueOf(m.getMusicType());
							textViewGenero.setText("Genero: "+genero);
						}
						file = list.get(position);
						
					} catch (IOException e) {
						System.out.println("Não foi possivel adicionar musica!");
					}
	        		
	        	}
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
