package br.com.logica;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import com.beaglebuddy.mp3.MP3;


public class ManageTag {
		List<MP3> MP3Files;
		File imagenTag = null;
		
		private String autor = null;
		private String album = null;
		private String genero = null;
		
		private static ManageTag instancia = null;

		
		private ManageTag() {
			MP3Files = new LinkedList<MP3>();
			
		}
		public void setImagenTag( File fileImagen){
			imagenTag = fileImagen;
		}
		public File getImagenTag(){
			return imagenTag;
		}

		public static ManageTag getInstance() {
			 if(instancia == null) {
				 instancia = new ManageTag();
			 }
			 return instancia;
		}
		public List<MP3> getMP3Selecionadas(){
			return null;
		}
		public void addMP3(MP3 music){
			MP3Files.add(music);
		}
		public void editTag(String autor, String album, String genero){
			MP3 music = null;
			for (int i = 0; i < MP3Files.size(); i++) {
				music = MP3Files.get(i);
				if(autor != ""){
					music.setAlbum(album);
				}
				if(album != ""){
					music.setMusicBy(autor);
				}
				if(genero != ""){
					music.setMusicType(genero);
				}
			}
		}
		public boolean gravarTags(){
			
			return true;
		}
		public String getAutor() {
			return autor;
		}
		public void setAutor(String autor) {
			this.autor = autor;
		}
		public String getAlbum() {
			return album;
		}
		public void setAlbum(String album) {
			this.album = album;
		}
		public String getGenero() {
			return genero;
		}
		public void setGenero(String genero) {
			this.genero = genero;
		}
}
