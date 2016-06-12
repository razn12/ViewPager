package com.example.viewpager;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.media.MediaMetadataRetriever;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;

public class Adapter2 extends BaseAdapter{
	Context activity;
	ListView list;
	Frag2 mactivity;
	
	Cursor c=ApplicationConstraint.dBase.query("list",new String[]{"fav_song"}, null, null, null, null, null);
	String[] files;
	
	public Adapter2(Context context, ListView lview,Frag2 m) {
		activity=context;
		list=lview;
		mactivity=m;
		ApplicationConstraint.refresh=m;
		files = getAllMusicFiles();
	}

	private String[] getAllMusicFiles() {
		c.moveToPosition(0);	
		
		String files[] = new String[c.getCount()];
		for(int i=0; i<c.getCount(); i++){
			c.moveToPosition(i);
			files[i] = c.getString(0);	
		}
		
		return files;
			
	}

	@Override
	public int getCount() {
		
		return files.length;
	}

	@Override
	public Object getItem(int position) {
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
		v=inflater.inflate(R.layout.index2,null);
		TextView songname=(TextView)v.findViewById(R.id.textView1);
		TextView artistname=(TextView)v.findViewById(R.id.textView2);
		//ImageView song_img=(ImageView)v.findViewById(R.id.imageView1);
		String new_path=files[position];
		
		MediaMetadataRetriever mmr=new MediaMetadataRetriever();
		mmr.setDataSource(new_path);
		String title = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE);

		if(title!=null){
			  songname.setText(title);}
		else{
			  int lastIndexofslash = files[position].lastIndexOf("/");
			  String medianame = files[position].substring(lastIndexofslash+1, files[position].length());
			  songname.setText(medianame);

		}
		
		artistname.setText("<"+mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST)+">");
		/*byte[] art = mmr.getEmbeddedPicture();
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
				builder1.setMessage("Delete from Favorite List?");
				
				builder1.setCancelable(true);

				builder1.setPositiveButton(
				    "Yes",
				    new DialogInterface.OnClickListener() {
				        public void onClick(DialogInterface dialog, int id) {
				        	System.out.println("*****************");
				        	ApplicationConstraint.dBase.delete("list", "fav_song=?",new String[]{new_path});
				        	mactivity.refreshAdapter();
				        	Toast.makeText(activity, "Deleted Successfully...", Toast.LENGTH_SHORT).show();
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
			/*	String new_path=files[arg2];
				//System.out.println(new_path);
				File f=new File(new_path);
				Intent viewIntent = new Intent(Intent.ACTION_VIEW);				
				viewIntent.setDataAndType(Uri.fromFile(f), "audio/*");
				activity.startActivity(Intent.createChooser(viewIntent, null));
				*/

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



}
