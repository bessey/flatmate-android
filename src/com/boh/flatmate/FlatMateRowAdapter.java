package com.boh.flatmate;

import com.boh.flatmate.R;
import com.boh.flatmate.FlatMate.FlatDataExchanger;
import com.boh.flatmate.connection.User;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class FlatMateRowAdapter extends ArrayAdapter<User> {

	private Context context;
	private User[] FlatData;

	public FlatMateRowAdapter(Context c, int textViewResourceId, User[] data) {
		super(c, textViewResourceId, data);
		FlatData = data;
		context = c;
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
		User flatMate = FlatData[position];
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
		
		String name = flatMate.getFirst_name() +" "+ flatMate.getLast_name();
		if (name != null) {
			TextView tt = (TextView) v.findViewById(R.id.name);
			if (tt != null) {
				tt.setText(name);
			}
		}
		if(flatMate.getId() == 1){
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
		
		double distance = flatMate.distanceFromHome();
		
		TextView disFHText = (TextView) v.findViewById(R.id.distanceText);
		disFHText.setText(distance+"mi from flat");
		
		return v;
	}
}