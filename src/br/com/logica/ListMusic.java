package br.com.logica;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.beaglebuddy.mp3.MP3;

public class ListMusic {
	
	private static ListMusic instancia = null;
	private List<File> list;
	private MP3 musicInformation = null;
	
	private ListMusic(){
		list = new ArrayList<File>();
	}
	
	public static ListMusic getInstance(){
		if (instancia == null){
			instancia = new ListMusic();
		}
		return instancia;
	}
	
	public void addMusica(File file){
		list.add(file);
	}
	
	public List<File> getListMusic(){
		return list;
	}

	public void setMusicInformation(MP3 mp3) {
		musicInformation = mp3;
	}

	public MP3 getMusicInformation() {
		return musicInformation;
	}
}
