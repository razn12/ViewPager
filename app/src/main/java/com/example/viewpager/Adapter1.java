package com.example.viewpager;

import java.io.File;
import java.util.zip.Inflater;

import android.app.AlertDialog;
import android.app.Application;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class Adapter1 extends BaseAdapter {
Context activity;
ListView list;
Frag1 mactivity;
private Cursor c;
String[] files;

	public Adapter1(Context frag1,ListView lview,Frag1 m) {
		activity=frag1;
		mactivity=m;
		list= lview;
		c=ApplicationConstraint.c;
		files = getAllMusicFiles();
	}



	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return c.getCount();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getItemViewType(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View v, ViewGroup parent) {
		LayoutInflater inflater=LayoutInflater.from(activity);
		v=inflater.inflate(R.layout.index1,null);
		TextView songname=(TextView)v.findViewById(R.id.textView1);
		TextView artistname=(TextView)v.findViewById(R.id.textView2);
		
		c.moveToPosition(position);
		
		MediaMetadataRetriever retriever=new MediaMetadataRetriever();
		retriever.setDataSource(files[position]);
		String title = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE);

		if(title!=null){
			  songname.setText(title);}
		else{
			  String displayname=c.getString(c.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME));
			  ApplicationConstraint.displayname=displayname;
			  songname.setText(displayname);
		}
		
		artistname.setText("<" + retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST) + ">");
		
	/*	byte[] art = mmr.getEmbeddedPicture();
		if(art == null)
			song_img.setImageResource(R.drawable.music);
		else{
			Bitmap albumart = BitmapFactory.decodeByteArray(art, 0, art.length);
			song_img.setImageBitmap(Bitmap.createScaledBitmap(albumart, 50, 50, false));
		}*/
		list.setOnItemLongClickListener(new OnItemLongClickListener() {
			
			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				final String new_path=files[arg2];
				System.out.println(new_path);
				
				AlertDialog.Builder builder1 = new AlertDialog.Builder(activity);
				builder1.setMessage("Add to Favorite List?");
				
				builder1.setCancelable(true);

				builder1.setPositiveButton(
				    "Yes",
				    new DialogInterface.OnClickListener() {
				        public void onClick(DialogInterface dialog, int id) {
				        	System.out.println("*****************");
				        	ContentValues values=new ContentValues();
				        	values.put("fav_song",new_path);
				        	long i =ApplicationConstraint.dBase.insert("list", null, values);
				        	
				        	ApplicationConstraint.refresh.refreshAdapter();
				        	
				        	Toast.makeText(activity, "Added Successfully...", Toast.LENGTH_SHORT).show();
				        	dialog.cancel();
				        }
				    });

				builder1.setNegativeButton(
				    "No",
				    new DialogInterface.OnClickListener() {
				        public void onClick(DialogInterface dialog, int id) {
				        	System.out.println("---------------");
				        	dialog.cancel();
				        }
				    });

				AlertDialog alert11 = builder1.create();
				alert11.show();
				
				return true;
			}
		});		
		
		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
									long arg3) {


				String new_path=files[arg2];
				ApplicationConstraint.new_path=new_path;
				if(ApplicationConstraint.mplayer!=null)
					ApplicationConstraint.mplayer.stop();



				Intent i = new Intent();
				i.setComponent(new ComponentName(activity, HomeMediaplayer.class));
				mactivity.startActivityForResult(i, 2);
			}
		});
		
		return v;
	}

	String[] getAllMusicFiles(){
		
		
		c.moveToPosition(0);	
	
		String files[] = new String[c.getCount()];
		for(int i=0; i<c.getCount(); i++){
			c.moveToPosition(i);
			int index = c.getColumnIndex(MediaStore.Audio.Media.DATA);
			files[i] = c.getString(index);	
		}
		
		return files;
			
	
	}	

	
}
