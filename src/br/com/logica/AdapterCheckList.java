package br.com.logica;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import br.com.activity.CarregarActivity;
import br.com.activity.MainActivity;
import br.com.activity.SetValueTag;

import com.beaglebuddy.mp3.MP3;
import com.example.tagedition.R;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

public class AdapterCheckList extends BaseAdapter{
	
	private List<File> files;
	private LayoutInflater mInflater;
	private List<File> selecionados;
	
	public AdapterCheckList(Context context, List<File> files) {
		mInflater = LayoutInflater.from(context);
		this.files = files;
		this.selecionados = new ArrayList<File>();
	}
	
	@Override
	public int getCount() {
		return files.size();
	}

	@Override
	public Object getItem(int arg0) {
		return files.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(final int posicao, View view, ViewGroup arg2) {
		
		if (this.files.get(posicao).isDirectory()){
			view = mInflater.inflate(R.layout.item_list, null); 
			
			ImageView image = (ImageView)view.findViewById(R.id.tipoArquivo);
			image.setImageResource(R.drawable.folder_blue_music);
		}else{
			view = mInflater.inflate(R.layout.item_check_list, null); 
			
			ImageView image = (ImageView)view.findViewById(R.id.tipoArquivo);
			image.setImageResource(R.drawable.music_icon);
			
			final CheckBox check = (CheckBox)view.findViewById(R.id.checkBox);
			
			check.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					if (check.isChecked()){
						check.setButtonDrawable(R.drawable.com_facebook_button_check_on);
						
						selecionados.add(files.get(posicao));
						
						
						try {
							ListMusic.getInstance().setMusicInformation(new MP3(files.get(posicao)));
							if(ListMusic.getInstance().getMusicInformation().getAlbum()!=null && ListMusic.getInstance().getMusicInformation().getAlbum().length()>20){
								String album = String.valueOf(ListMusic.getInstance().getMusicInformation().getAlbum()).length()>12 ? String.valueOf(ListMusic.getInstance().getMusicInformation().getAlbum()).substring(0,20)+"...":String.valueOf(ListMusic.getInstance().getMusicInformation().getAlbum());
								CarregarActivity.textViewAlbum.setText("Album: "+album);
							}else if (ListMusic.getInstance().getMusicInformation().getAlbum()!=null && ListMusic.getInstance().getMusicInformation().getAlbum().length()<=20) {
								CarregarActivity.textViewAlbum.setText("Album: "+ListMusic.getInstance().getMusicInformation().getAlbum());
							}
							if(ListMusic.getInstance().getMusicInformation().getMusicBy() !=null && ListMusic.getInstance().getMusicInformation().getMusicBy().length()> 20){
								String musicBy = String.valueOf(ListMusic.getInstance().getMusicInformation().getMusicBy()).length()>12 ? String.valueOf(ListMusic.getInstance().getMusicInformation().getMusicBy()).substring(0,20)+"...":String.valueOf(ListMusic.getInstance().getMusicInformation().getMusicBy());
								CarregarActivity.textViewAutor.setText("Autor: "+musicBy);
							}else if (ListMusic.getInstance().getMusicInformation().getMusicBy()!=null && ListMusic.getInstance().getMusicInformation().getMusicBy().length()<=20) {
								CarregarActivity.textViewAutor.setText("Autor: "+ListMusic.getInstance().getMusicInformation().getMusicBy());
							}
							if(ListMusic.getInstance().getMusicInformation().getMusicType() != null && ListMusic.getInstance().getMusicInformation().getMusicType().length()>20){
								String genero = String.valueOf(ListMusic.getInstance().getMusicInformation().getMusicType()).length()>12 ? String.valueOf(ListMusic.getInstance().getMusicInformation().getMusicType()).substring(0,20)+"...":String.valueOf(ListMusic.getInstance().getMusicInformation().getMusicType());
								CarregarActivity.textViewGenero.setText("Genero: "+genero);
							}else if (ListMusic.getInstance().getMusicInformation().getMusicType()!=null && ListMusic.getInstance().getMusicInformation().getMusicType().length()<=20) {
								CarregarActivity.textViewGenero.setText("Genero: "+ListMusic.getInstance().getMusicInformation().getMusicType());
							}
						} catch (IOException e) {
							
						}
					}else{				
						check.setButtonDrawable(R.drawable.com_facebook_button_check_off);
						selecionados.remove(files.get(posicao));
					}
				}
			});
		}
		
		
		TextView nome = (TextView)view.findViewById(R.id.nomeArquivo);
		nome.setText(files.get(posicao).getName());
		
		return view;
	}
	
	public List<File> getSelecionados(){
		return selecionados;
	}
}
