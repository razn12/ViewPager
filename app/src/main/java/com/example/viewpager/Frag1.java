package com.example.viewpager;

import com.actionbarsherlock.app.SherlockFragment;


import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
public class Frag1 extends SherlockFragment{

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v=inflater.inflate(R.layout.frag1, container,false);
		
		ListView list = (ListView)v.findViewById(R.id.listView1);
		ApplicationConstraint.list=list;
		list.setAdapter(new Adapter1(v.getContext(),list,this));
		
		
		return v;
		
	}

}
