package br.com.logica;


import java.util.List;

import android.media.MediaPlayer;

public class PlayerController {
	
	public static MediaPlayer Player;
	public static int musicIndex = 0;
	public static List<String> listaMusicas;

	public static MediaPlayer getPlayer() {
		return Player;
	}

	public static void setPlayer(MediaPlayer player) {
		Player = player;
	}
	
	

}
