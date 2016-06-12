package com.example.viewpager;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaMetadataRetriever;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.view.ViewPager;

public class MainActivity extends SherlockFragmentActivity {

	private ViewPager mViewPager;
	private TabsAdapter mTabsAdapter;
	SQLiteDatabase dBase;



	private Cursor c;
	private MediaMetadataRetriever retriever;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);


		try {
			dBase = openOrCreateDatabase("song_db", Context.MODE_PRIVATE, null);
			dBase.execSQL("create table if not exists list(fav_song varchar(100))");
			ApplicationConstraint.dBase = dBase;
		} catch (Exception e) {
			// TODO: handle exception
		}


		mViewPager = new ViewPager(this);
		mViewPager.setId(R.id.pager);
		setContentView(R.layout.activity_main);
		mViewPager = (ViewPager) findViewById(R.id.pager);

		final ActionBar bar = getSupportActionBar();
		bar.setIcon(R.drawable.player);
		bar.setTitle("My Player");
		bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#33CCCC")));
		bar.setStackedBackgroundDrawable(new ColorDrawable(Color.parseColor("#33CCCC")));
		bar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		mTabsAdapter = new TabsAdapter(this, mViewPager);
		mTabsAdapter.addTab(bar.newTab().setText("Song List"), Frag1.class, null);
		mTabsAdapter.addTab(bar.newTab().setText("Favourite List"), Frag2.class, null);

		c = getAllSongs();
		ApplicationConstraint.c=c;



	}


	private Cursor getAllSongs() {
		String[] projection = {MediaStore.Audio.Media._ID, MediaStore.Audio.Media.DISPLAY_NAME, MediaStore.Audio.Media.ARTIST, MediaStore.Audio.Media.DATA};
		Cursor c = getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, projection, null, null, MediaStore.Audio.Media.DEFAULT_SORT_ORDER);
		return c;
	}

	public void onBackPressed() {


		AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
		builder1.setMessage("Exit Player?");

		builder1.setCancelable(true);

		builder1.setPositiveButton(
				"Yes",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						System.out.println("*****************");
						if (ApplicationConstraint.mplayer!=null)
							ApplicationConstraint.mplayer.stop();
						finish();
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

	}

}