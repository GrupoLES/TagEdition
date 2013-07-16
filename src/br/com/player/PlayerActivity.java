
package br.com.player;
import java.io.IOException;

import com.example.tagedition.R;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
 
public class PlayerActivity extends Activity {
Button btplay, btpausar;
MediaPlayer Player;
boolean Click;
 
@Override
public void onCreate(Bundle savedInstanceState) {
super.onCreate(savedInstanceState);
setContentView(R.layout.activity_player);
 
btplay = (Button) findViewById(R.id.bttocar);
btpausar = (Button) findViewById(R.id.btpausar);
//Player = MediaPlayer.create(this, R.raw.music);
Player = new MediaPlayer();

try {
	Player.setDataSource("/sdcard/09 Blaze of Glory.mp3");
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

try {
Player.prepare();
} catch (Exception e) {
}
 
btplay.setOnClickListener(new View.OnClickListener() {
 
@Override
public void onClick(View arg0) {
 
Player.start();
 
}
});
 
btpausar.setOnClickListener(new View.OnClickListener() {
 
@Override
public void onClick(View arg0) {
 
Player.pause();
 
		}
	});
 
	}
 
}
