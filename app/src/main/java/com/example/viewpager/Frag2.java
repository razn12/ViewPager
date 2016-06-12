package com.example.viewpager;

import com.actionbarsherlock.app.SherlockFragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class Frag2 extends SherlockFragment{
	View v;
	ListView list;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		v=inflater.inflate(R.layout.frag2, container,false);
		Frag2 refresh=new Frag2();
		ApplicationConstraint.refresh=refresh;
		list = (ListView)v.findViewById(R.id.listView1);
		list.setAdapter(new Adapter2(v.getContext(),list,this));
		return v;
		
		
	}
	public void refreshAdapter(){
   	
        list.setAdapter(new Adapter2(v.getContext(),list,this));

   }
}
