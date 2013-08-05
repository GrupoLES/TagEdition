
package br.com.player;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.TextView;
import br.com.activity.CarregarActivity;
import br.com.activity.MainActivity;
import br.com.logica.ListMusic;
import br.com.logica.PlayerController;

import com.example.tagedition.R;
 
public class PlayerActivity extends Activity {
	
	private Button btPlay, btPausar, btStop, btPrevious, btNext, btnSource;
//	private MediaPlayer Player;
	private boolean Click;
//	private int musicIndex = 0;
	private boolean stopped = false;
	private boolean paused = false;
	private TextView nameField;
	private List<String> musicas;
	private ImageView bg;
	private ProgressBar progressBar;
	private int progressBarStatus = 0;
	private Handler progressBarHandler = new Handler();
	
	private List<String> convertPath(){
		List<String> retorno = new ArrayList<String>();
		List<File> files = ListMusic.getInstance().getListMusic();
		
		for (int i = 0; i < files.size(); i++) {
			retorno.add(files.get(i).getPath());
		}
		return retorno;
	}
	
	private MediaPlayer returnPlayer(int index){
		
		MediaPlayer player = null;
		try {
			player = new MediaPlayer();
			player.setDataSource(musicas.get(index));
		} catch (IllegalArgumentException e1) {
			e1.printStackTrace();
			System.exit(1);
		} catch (IllegalStateException e1) {
			e1.printStackTrace();
			System.exit(1);
		} catch (IOException e1) {
			e1.printStackTrace();
			System.exit(1);
		}
		return player;
		
	}
	
	private void buildListener() {
		
//		startProgressBar();
		PlayerController.Player.setOnCompletionListener(new OnCompletionListener(){
			
			@Override
			public void onCompletion(MediaPlayer mp) {
				
				if(musicas.size() >  PlayerController.musicIndex + 1){
					PlayerController.musicIndex++;
					PlayerController.Player.release();
					PlayerController.Player = returnPlayer(PlayerController.musicIndex);
					try {
						PlayerController.Player.prepare();
					} catch (IllegalStateException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					PlayerController.Player.start();
					nameField.setText(getFileNameFromPath(musicas.get(PlayerController.musicIndex)));
					buildListener();
				}else{
					btPlay.setBackgroundResource(R.drawable.play);
				}
				
			}
				
			
			
		});
		
	}
	
	private String getFileNameFromPath(String path){
		String[] array = path.split("/");
		return array[array.length - 1];
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_player);
		
		btPlay = (Button) findViewById(R.id.btnPlay);
		//btPausar = (Button) findViewById(R.id.botaoPause);
		//btStop = (Button) findViewById(R.id.botaoStop);
		btPrevious = (Button) findViewById(R.id.btnPrevious);
		btNext = (Button) findViewById(R.id.btnNext);
		nameField = (TextView) findViewById(R.id.textNomeMusica);
		btnSource = (Button) findViewById(R.id.btnSource);
		bg = (ImageView) findViewById(R.id.bg);
		musicas = convertPath();
		System.out.println("MUSICAA: " + musicas.size() );

		
		
		
		if(PlayerController.Player != null){
			nameField.setText(getFileNameFromPath(musicas.get(PlayerController.musicIndex)));
			
			
			if(PlayerController.Player.isPlaying()){
				btPlay.setBackgroundResource(R.drawable.pause);
			}else{
				btPlay.setBackgroundResource(R.drawable.play);
			}
			
			buildListener();
			
			
			try {
				if(! PlayerController.Player.isPlaying()){
					PlayerController.Player = new MediaPlayer();
					
				}
			} catch (Exception e) {
				PlayerController.Player = new MediaPlayer();
			}
			
			
			
			
		}else{
			PlayerController.Player = new MediaPlayer();
		}
		
		
		if(musicas.size() > 0 && !PlayerController.Player.isPlaying()){
			PlayerController.Player = returnPlayer(PlayerController.musicIndex);
			
		}
			
		Drawable image = getResources().getDrawable(R.drawable.default1);
		bg.setImageDrawable(image);
		

		 
		btPlay.setOnClickListener(new View.OnClickListener() {
		 
		@Override
		public void onClick(View arg0) {
			
			musicas = convertPath();
			if(PlayerController.Player != null){
				if(paused == false && PlayerController.Player.isPlaying() ){
					System.out.println("1");
					pause();
					btPlay.setBackgroundResource(R.drawable.play);
					return;
				}else{
					System.out.println("2");
					btPlay.setBackgroundResource(R.drawable.pause);
					paused = false;
				}
			}
			
			
			
			
			if(musicas.size() > 0){
				
				if(stopped){
					System.out.println("3");
					PlayerController.Player = returnPlayer(PlayerController.musicIndex);
					stopped = false;
					paused = false;
					
				}
					
				
				if(! PlayerController.Player.isPlaying()){
					try {
						System.out.println("4");
						PlayerController.Player.prepare();
					} catch (Exception e) {
					}	
					
					PlayerController.Player.start();
					nameField.setText(getFileNameFromPath(musicas.get(PlayerController.musicIndex)));
					
	//				if(paused == false){
	//					buildThread();
	//				}
					
					System.out.println("aquiiii");
					buildListener();	
					
					
				}
				
				
				
			}
			
			 
			}


		});
		
		
//		btStop.setOnClickListener(new View.OnClickListener() {
//		
//			@Override
//			public void onClick(View arg0) {
//				
//				
//				if(!stopped){
//					PlayerController.Player.stop();
//					PlayerController.Player.release();
//					stopped = true;
//				}
//				
//			}
//			
//		});
		
		btPrevious.setOnClickListener(new View.OnClickListener() {
		
			@Override
			public void onClick(View arg0) {
				musicas = convertPath();
				if(musicas.size() > 0){
					btPlay.setBackgroundResource(R.drawable.pause);
					if(stopped == false || paused ==  true){
						PlayerController.Player.stop();
						PlayerController.Player.release();
					}
					
					
					if(PlayerController.musicIndex == 0) {
						PlayerController.musicIndex = musicas.size() - 1;
					}else{
						PlayerController.musicIndex--;
					}
					
					PlayerController.Player = returnPlayer(PlayerController.musicIndex);
					stopped = false;
					paused = false;
					try {
						PlayerController.Player.prepare();
					}catch (Exception e) {
					}
					
					PlayerController.Player.start();
					nameField.setText(getFileNameFromPath(musicas.get(PlayerController.musicIndex)));
					buildListener();
				}
		
			}
			
		});
		
		btNext.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				musicas = convertPath();
				
				if(musicas.size() > 0){
					btPlay.setBackgroundResource(R.drawable.pause);
					if(stopped == false || paused == true){
						PlayerController.Player.stop();
						PlayerController.Player.release();
					}
					if(PlayerController.musicIndex == musicas.size() - 1) {
						PlayerController.musicIndex = 0;
					}else{
						PlayerController.musicIndex++;
					}
					
					PlayerController.Player = returnPlayer(PlayerController.musicIndex);
					stopped = false;
					paused = false;
					try {
						PlayerController.Player.prepare();
					}catch (Exception e) {
					}
					
					PlayerController.Player.start();
					nameField.setText(getFileNameFromPath(musicas.get(PlayerController.musicIndex)));
					buildListener();
					
				}
				
				
		
			}
			
		});
		
		btnSource.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				pause();
				stopped = true;
				Intent i = new Intent(PlayerActivity.this,CarregarActivity.class);
				startActivity(i);
				
				
			}
			
		});
		
		 
//		btPausar.setOnClickListener(new View.OnClickListener() {
//		 
//		@Override
//		public void onClick(View arg0) {
//			
//			
//			if(paused == false && stopped == false){
//				PlayerController.Player.pause();
//				paused = true;
//			}
//			
//		 
//		}
//	});
	 
			
	
	}
	public void pause(){
		if(paused == false && stopped == false){
			PlayerController.Player.pause();
			paused = true;
		}
	}
	
//	public void startProgressBar(){
//		int size = PlayerController.Player.getDuration() / 1000;
//		System.out.println("tamanho musica: " + size);
//		progressBar.setProgress(0);
//		progressBar.setMax(size);
//		progressBar.showContextMenu();
//		progressBarStatus = 0;
//		
//		new Thread(new Runnable() {
//			  public void run() {
//				while (progressBarStatus < PlayerController.Player.getDuration() / 1000) {
//
//				  // process some tasks
//				  progressBarStatus++;
//
//				  // your computer is too fast, sleep 1 second
//				  try {
//					Thread.sleep(1000);
//				  } catch (InterruptedException e) {
//					e.printStackTrace();
//				  }
//
//				  // Update the progress bar
//				  progressBarHandler.post(new Runnable() {
//					public void run() {
//					  progressBar.setProgress(progressBarStatus);
//					}
//				  });
//				}
//
//				// ok, file is downloaded,
//				if (progressBarStatus >= 100) {
//
//					// sleep 2 seconds, so that you can see the 100%
//					try {
//						Thread.sleep(2000);
//					} catch (InterruptedException e) {
//						e.printStackTrace();
//					}
//
//					// close the progress bar dialog
//					
//				}
//			  }
//		       }).start();
//		
//		
//	}

 
}
