
package br.com.player;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import br.com.logica.ListMusic;

import com.example.tagedition.R;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
 
public class PlayerActivity extends Activity {
	
private Button btPlay, btPausar, btStop, btPrevious, btNext;
private MediaPlayer Player;
private boolean Click;
private int musicIndex = 0;
private boolean stopped = false;
private List<String> musicas;
 
@Override
public void onCreate(Bundle savedInstanceState) {
super.onCreate(savedInstanceState);
setContentView(R.layout.activity_player);
 
btPlay = (Button) findViewById(R.id.botaoPlay);
btPausar = (Button) findViewById(R.id.botaoPause);
btStop = (Button) findViewById(R.id.botaoStop);
btPrevious = (Button) findViewById(R.id.botaoPrevious);
btNext = (Button) findViewById(R.id.botaoNext);

//Player = MediaPlayer.create(this, R.raw.music);
Player = new MediaPlayer();
musicas = ListMusic.getInstance().getListMusic();
//musicas.add("/sdcard/09 Blaze of Glory.mp3");
//musicas.add("/sdcard/01 Under the bridge.mp3");

try {
	
	if(musicas.size() > 0){
		Player.setDataSource(musicas.get(musicIndex));
	}
	
} catch (IllegalArgumentException e1) {
	// TODO Auto-generated catch block
	e1.printStackTrace();
} catch (IllegalStateException e1) {
	// TODO Auto-generated catch block
	e1.printStackTrace();
} catch (IOException e1) {
	// TODO Auto-generated catch block
	e1.printStackTrace();
}


 
btPlay.setOnClickListener(new View.OnClickListener() {
 
@Override
public void onClick(View arg0) {

if(musicas.size() > 0){
	
	if(stopped){
		
		try {
			Player = new MediaPlayer();
			Player.setDataSource(musicas.get(musicIndex));
		} catch (IllegalArgumentException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IllegalStateException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		stopped = false;
		
	}
		
		
	try {
		Player.prepare();
	} catch (Exception e) {
	}	

	Player.start();
	
}

 
}
});


btStop.setOnClickListener(new View.OnClickListener() {

	@Override
	public void onClick(View arg0) {
		stopped = true;
		Player.stop();
		
	}
	
});

btPrevious.setOnClickListener(new View.OnClickListener() {

	@Override
	public void onClick(View arg0) {
		
		if(musicas.size() > 0){
			
			Player.stop();
			if(musicIndex == 0) {
				musicIndex = musicas.size() - 1;
			}else{
				musicIndex--;
			}
			
			
			try {
				Player = new MediaPlayer();
				Player.setDataSource(musicas.get(musicIndex));
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			try {
				Player.prepare();
			}catch (Exception e) {
			}
			
			Player.start();
			
		}
		
		

	}
	
	
});

btNext.setOnClickListener(new View.OnClickListener() {

	@Override
	public void onClick(View arg0) {
		
		if(musicas.size() > 0){
			
			Player.stop();
			if(musicIndex == musicas.size() - 1) {
				musicIndex = 0;
			}else{
				musicIndex++;
			}
			
			
			try {
				Player = new MediaPlayer();
				Player.setDataSource(musicas.get(musicIndex));
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			try {
				Player.prepare();
			}catch (Exception e) {
			}
			
			Player.start();
			
		}
		
		

	}
	
});

 
btPausar.setOnClickListener(new View.OnClickListener() {
 
@Override
public void onClick(View arg0) {
 
Player.pause();
 
		}
	});
 
	}
 
}
