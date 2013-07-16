package br.com.logica;

import java.util.ArrayList;
import java.util.List;

public class ListMusic {
	
	private static ListMusic instancia = null;
	private List<String> list;
	
	private ListMusic(){
		list = new ArrayList<String>();
	}
	
	public static ListMusic getInstance(){
		if (instancia == null){
			instancia = new ListMusic();
		}
		return instancia;
	}
	
	public void addMusica(String caminho){
		list.add(caminho);
	}
	
	public List<String> getListMusic(){
		return list;
	}
}
