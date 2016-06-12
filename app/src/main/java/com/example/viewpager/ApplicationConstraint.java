package com.example.viewpager;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.widget.ListView;
import android.widget.TextView;

public class ApplicationConstraint {

	public static SQLiteDatabase dBase;
	
	public static Frag2 refresh;

	public static MediaMetadataRetriever retriever;

	protected static String new_path;


	public static Cursor c;

	public static MediaPlayer mplayer;

	public static String displayname;

	public static TextView settitle;
	public static TextView setartist;

	public static ListView list;


}
