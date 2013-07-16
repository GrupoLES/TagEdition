package br.com.logica;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ListMusic {
	
	private static ListMusic instancia = null;
	private List<File> list;
	
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
}
