package br.com.logica;

import java.util.LinkedList;
import java.util.List;

import org.farng.mp3.MP3File;

public class ManageTag {
		List<MP3File> MP3Files;
		private static ManageTag instancia = null;

		
		private ManageTag() {
			MP3Files = new LinkedList<MP3File>();
		}

		public static ManageTag getInstance() {
			 if(instancia == null) {
				 instancia = new ManageTag();
			 }
			 return instancia;
		}
		public List<MP3File> getMP3Selecionadas(){
			return null;
		}
		public void addMP3(MP3File music){
			MP3Files.add(music);
		}
		public void editTag(String autor, String album, String genero){
			MP3File music = null;
			for (int i = 0; i < MP3Files.size(); i++) {
				music = MP3Files.get(i);
				if(autor != ""){
					music.getID3v2Tag().setAlbumTitle(album);
				}
				if(album != ""){
					music.getID3v2Tag().setAuthorComposer(autor);
				}
				if(genero != ""){
					music.getID3v2Tag().setSongGenre(genero);
				}
			}
		}
		public boolean gravarTags(){
			
			return true;
		}
}
