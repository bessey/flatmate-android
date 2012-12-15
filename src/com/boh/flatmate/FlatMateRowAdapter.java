package com.boh.flatmate;

import java.util.ArrayList;

import com.Ballin_Octo_Hipster.flatmate.R;
import com.boh.flatmate.data.FlatMate_data;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class FlatMateRowAdapter extends ArrayAdapter<FlatMate_data> {

	private Context context;
	private ArrayList<FlatMate_data> FlatData;

	public FlatMateRowAdapter(Context c, int textViewResourceId, ArrayList<FlatMate_data> data) {
		super(c, textViewResourceId, data);
		FlatData = data;
		context = c;
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
		FlatMate_data flatMate = FlatData.get(position);
		if (v == null) {
			LayoutInflater vi = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = vi.inflate(R.layout.flat_row, null);
		}
		if(position == 0){
			v.setBackgroundResource(R.drawable.box1);
		}else if(position == 1){
			v.setBackgroundResource(R.drawable.box2);
		}else{
			v.setBackgroundResource(R.drawable.box3);
		}
		
		String name = flatMate.getName();
		if (name != null) {
			TextView tt = (TextView) v.findViewById(R.id.name);
			if (tt != null) {
				tt.setText(name);
			}
		}
		if(flatMate.isCurrentUser() == 1){
			ImageButton phoneButton = (ImageButton)v.findViewById(R.id.callButton);
			phoneButton.setVisibility(View.INVISIBLE);
			ImageButton messageButton = (ImageButton)v.findViewById(R.id.messageButton);
			messageButton.setVisibility(View.INVISIBLE);
		}else{
			ImageButton phoneButton = (ImageButton)v.findViewById(R.id.callButton);
			phoneButton.setOnClickListener(flatMate.phoneListener);
			ImageButton messageButton = (ImageButton)v.findViewById(R.id.messageButton);
			messageButton.setOnClickListener(flatMate.messageListener);
		}
		
		if(flatMate.isHome() == 0){
			ImageView image = (ImageView) v.findViewById(R.id.icon);
			image.setImageResource(R.drawable.home_x);
		}
		
		return v;
	}
}