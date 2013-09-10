
package br.com.player;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import br.com.activity.CarregarActivity;
import br.com.activity.FacebookConnActivity;
import br.com.logica.ListMusic;
import br.com.logica.PlayerController;

import com.beaglebuddy.mp3.MP3;
import com.example.tagedition.R;
 
public class PlayerActivity extends Activity {
	
	private Button btPlay, btPausar, btStop, btPrevious, btNext, btnSource, facebtn;
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
	byte[] picture;
	private MP3 mp3;
	
	
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
		setAlbumImage();
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
	
	private void setAlbumImage(){
		mp3 = null;
		try {
			mp3 = new MP3(musicas.get(PlayerController.musicIndex));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		System.out.println("bites: " + mp3.getPictures().size());
		
		if(mp3.getPictures().size() == 0){
			Drawable imageDrawable = getResources().getDrawable(R.drawable.default1);
			bg.setImageDrawable(imageDrawable);
		}else{
			picture = mp3.getPictures().get(0).getImage();
			Bitmap bmpImage = BitmapFactory.decodeByteArray(picture, 0, picture.length); 
			bg.setImageBitmap(bmpImage);
		}
		

		
		
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
		PlayerController.listaMusicas = musicas;
		facebtn = (Button) findViewById(R.id.face);
		
		
		if(PlayerController.Player != null && musicas.size() > 0){
			
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
			
		//Drawable image = getResources().getDrawable(R.drawable.default1);
		
		
		
		facebtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				AlertDialog.Builder alert = new AlertDialog.Builder(PlayerActivity.this);
				alert.setTitle("Aviso");
				alert.setMessage("É preciso ter uma sessão do Facebook em Execução!");
				alert.setNeutralButton("Ok", new  DialogInterface.OnClickListener() { 
					@Override
					public  void  onClick(DialogInterface dialog, int  whichButton) {
						
						if(musicas.size() > 0) {
							
							String musicaTitulo = getFileNameFromPath(musicas.get(PlayerController.musicIndex));
							musicaTitulo = musicaTitulo.split("\\.")[0];
							Intent i = new Intent(PlayerActivity.this,FacebookConnActivity.class);
							Bundle params = new Bundle();
													
							if(mp3.getPictures().size() != 0){
								params.putByteArray("imagem", picture);				
							}
							
							
							params.putString("musica", musicaTitulo);
							i.putExtras(params);
							startActivity(i);
							
						}
						

					}
				});
				alert.show();
				
			}
			
		});
		
		btPlay.setOnClickListener(new View.OnClickListener() {
		 
		@Override
		public void onClick(View arg0) {
			
			musicas = convertPath();
			if(PlayerController.Player != null){
				if(paused == false && PlayerController.Player.isPlaying() ){
					pause();
					btPlay.setBackgroundResource(R.drawable.play);
					return;
				}else{
					btPlay.setBackgroundResource(R.drawable.pause);
					paused = false;
				}
			}
			
			
			
			
			if(musicas.size() > 0){
				
				if(stopped){
					PlayerController.Player = returnPlayer(PlayerController.musicIndex);
					stopped = false;
					paused = false;
					
				}
					
				
				if(! PlayerController.Player.isPlaying()){
					try {
						PlayerController.Player.prepare();
					} catch (Exception e) {
					}	
					
					PlayerController.Player.start();
					nameField.setText(getFileNameFromPath(musicas.get(PlayerController.musicIndex)));
					buildListener();	
					
					
				}
				
				
				
			}
			
			 
			}


		});
		
		
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
				finish();
				Intent i = new Intent(PlayerActivity.this,CarregarActivity.class);
				startActivity(i);
				
				
			}
			
		});
			
	
	}
	public void pause(){
		if(paused == false && stopped == false){
			PlayerController.Player.pause();
			paused = true;
		}
	}
	

 
}
