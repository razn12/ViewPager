package com.example.viewpager;

import java.util.concurrent.TimeUnit;


import android.app.Activity;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class HomeMediaplayer extends Activity {
	Cursor c;
	Handler myhandler;
	SeekBar seekbar;
	TextView t1,t2,t3,t4;
	MediaPlayer mplayer;
	String filepath;
	MediaMetadataRetriever retriever;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.activity_home_mediaplayer);
			myhandler=new Handler();
			android.app.ActionBar bar = getActionBar();
			bar.setIcon(R.drawable.player);
			bar.setTitle("Song Playing");
			bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#33CCCC")));
			 
			getActionBar().setDisplayHomeAsUpEnabled(true);
		//	 Toast.makeText(getApplicationContext(), ApplicationConstraint.new_path, 2000).show();
			 filepath=ApplicationConstraint.new_path;
				t1=(TextView)findViewById(R.id.timer1);
				t2=(TextView)findViewById(R.id.timer2);
				t3=(TextView)findViewById(R.id.title);
				t4=(TextView)findViewById(R.id.author);
				seekbar = (SeekBar) findViewById(R.id.homeseekbar);
	
				initMediaPlayer(filepath);
				mplayer.start();
				seekbar.setMax(mplayer.getDuration());

				seekbar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

					@Override
					public void onStopTrackingTouch(SeekBar seekBar) {
						// TODO Auto-generated method stub
					}

					@Override
					public void onStartTrackingTouch(SeekBar seekBar) {
						// TODO Auto-generated method stub
					}

					@Override
					public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
						// TODO Auto-generated method stub
						if(fromUser){
							if(mplayer != null)
							mplayer.seekTo(progress);
						}
					}
				});

				ImageView img=(ImageView)findViewById(R.id.imageView);
			retriever=new MediaMetadataRetriever();
			  Bitmap art = getAlbumart(filepath);
			if(art!=null){
			img.setImageBitmap(art);
			}

			else{
				img.setImageResource(R.drawable.music);
						}


			String duration = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
			  long dur = Long.parseLong(duration);
			  String formattedTime = String.format("%d : %d", TimeUnit.MILLISECONDS.toMinutes(dur), TimeUnit.MILLISECONDS.toSeconds(dur) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(dur)));
			  t2.setText(formattedTime);
			  String songtitle = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE);
			  if(songtitle!=null){
				  t3.setText("Playing: "+songtitle);

			  }else{

				  t3.setText("");

			  }
			  String author = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST);
			  if(author!=null){
				  t4.setText("By: "+author);

				  }else{
					  t4.setText("");

				  }

			updateSeekBar();

	}

	
	public void test(View v){
		
		switch(v.getId()){
		
		case R.id.imageView3 :    //make sure media player is initialized and is currently playing a song
						     if(mplayer!=null){
							 if(mplayer.isPlaying())
								 mplayer.pause();
						     }
							 break;
		case R.id.imageView2  :    // start playing only if mediaplayer is not null
						     if(mplayer!=null)mplayer.start();
							 break;
		case R.id.imageView4  :    //make sure media player is initialized and is currently playing a song
			               
							 if(mplayer!=null){
							 	if(mplayer.isPlaying()){
							 		mplayer.stop();
							 		initMediaPlayer(ApplicationConstraint.new_path);
							 		
							 	}
							 }
							 break;
		case  R.id.imageView5 : if(mplayer !=null)
			mplayer.seekTo(mplayer.getCurrentPosition()+mplayer.getDuration()/10);
						   	 break;
		case R.id.imageView1 :  if(mplayer!=null)
			mplayer.seekTo(mplayer.getCurrentPosition()-mplayer.getDuration()/10);
							 break;
		}		
		
	}


Runnable run=new Runnable() {
	@Override
	public void run() {
		updateSeekBar();
	}
};

private void updateSeekBar(){
	seekbar.setProgress(mplayer.getCurrentPosition());
	int dur = seekbar.getProgress();
	
	String currentDuration = String.format("%d : %d", TimeUnit.MILLISECONDS.toMinutes(dur), TimeUnit.MILLISECONDS.toSeconds(dur) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(dur)));
	
	t1.setText(currentDuration);
	
	
	myhandler.postDelayed(run, 100);	
}

private void initMediaPlayer(String filename){
	
    mplayer = new MediaPlayer();
    ApplicationConstraint.mplayer=mplayer;
	
	try {
		mplayer.setDataSource(filename);
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	try {
		mplayer.prepare();
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}
private Bitmap getAlbumart(String filepath){
	
	retriever.setDataSource(filepath);
	//getEmbeddedPictures() returns null when album art is not available in the media
	byte[] art = retriever.getEmbeddedPicture();
	if(art == null)
	 return null;
	Bitmap albumart = BitmapFactory.decodeByteArray(art, 0, art.length);
	//I don't want big image so downscaling it to 100 X 100
	return Bitmap.createScaledBitmap(albumart, 600, 600, false);
}


}
