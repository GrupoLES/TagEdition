package br.com.example;

import java.io.*;


import org.farng.mp3.MP3File;
import org.farng.mp3.TagConstant;

public class ManipulacaoMP3File {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		//Diretorio onde est‹o as musicas.
		String DIRETORIO = "/Users/thiagoalmeida/Desktop/";
		File file = new File(DIRETORIO);
		//Pega todos os arquivos no diretorio
		String[] arquivos = file.list();

		for (int i = 0; i < arquivos.length; i++) {
			//Filtrar s— os MP3
			if (arquivos[i].contains(".mp3")) {
				//Pega uma musica por vez
				File musica = new File(DIRETORIO + "/" + arquivos[i]);
				try {
					//A biblioteca faz um parser de MP3 para o MP3File
					MP3File m = new MP3File(musica);

					System.out.println("-----Musica "+m.getID3v2Tag().getAuthorComposer()+"-----");
					
					//Forma de alterar as Tags
					m.getID3v2Tag().setAlbumTitle("_l_");
					m.getID3v2Tag().setAuthorComposer("Thiago");
					//Metodo para escrever o arquivo as altera›es //TODO:O metodo est‡ duplicando a musica 
					m.save(musica, TagConstant.MP3_FILE_SAVE_OVERWRITE);
				} catch (Exception e) {
					System.out.println("Erro ao ler a musica: " + arquivos[i]);
				}

			}

		}
	}

}
