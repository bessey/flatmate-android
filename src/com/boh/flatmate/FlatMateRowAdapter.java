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
	private int displayDebts = 0;

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
		
		if(flatMate.getColour_Id() == 0) {
			v.setBackgroundResource(R.drawable.box1);
		} else if(flatMate.getColour_Id() == 1) {
			v.setBackgroundResource(R.drawable.box2);
		} else if (flatMate.getColour_Id() == 2) {
			v.setBackgroundResource(R.drawable.box3);
		} else if (flatMate.getColour_Id() == 3) {
			v.setBackgroundResource(R.drawable.box4);
		} else if (flatMate.getColour_Id() == 4) {
			v.setBackgroundResource(R.drawable.box5);
		} else if (flatMate.getColour_Id() == 5) {
			v.setBackgroundResource(R.drawable.box6);
		} else if (flatMate.getColour_Id() == 6) {
			v.setBackgroundResource(R.drawable.box7);
		} else if (flatMate.getColour_Id() == 7) {
			v.setBackgroundResource(R.drawable.box8);
		} else {
			v.setBackgroundResource(R.drawable.box8);
		}
		String name = flatMate.getFirst_name() +" "+ flatMate.getLast_name().toUpperCase().charAt(0);
		if (name != null) {
			TextView tt = (TextView) v.findViewById(R.id.name);
			if (tt != null) {
				tt.setText(name);
			}
		}
		if(flatMate.getId() == FlatDataExchanger.flatData.getCurrentUserId() || displayDebts == 1){
			ImageButton phoneButton = (ImageButton)v.findViewById(R.id.callButton);
			phoneButton.setVisibility(View.GONE);
			ImageButton messageButton = (ImageButton)v.findViewById(R.id.messageButton);
			messageButton.setVisibility(View.GONE);
			TextView debtsText = (TextView)v.findViewById(R.id.userDebtsBox);
			debtsText.setVisibility(View.VISIBLE);
			debtsText.setText(FlatDataExchanger.flatData.getDebt(flatMate.getId()));
		}else{
			TextView debtsText = (TextView)v.findViewById(R.id.userDebtsBox);
			debtsText.setVisibility(View.GONE);
			ImageButton phoneButton = (ImageButton)v.findViewById(R.id.callButton);
			phoneButton.setVisibility(View.VISIBLE);
			ImageButton messageButton = (ImageButton)v.findViewById(R.id.messageButton);
			messageButton.setVisibility(View.VISIBLE);
			phoneButton.setOnClickListener(flatMate.phoneListener);
			messageButton.setOnClickListener(flatMate.messageListener);
		}
		
		if(flatMate.isHome() == 0){
			ImageView image = (ImageView) v.findViewById(R.id.icon);
			image.setImageResource(R.drawable.home_x);
		}else{
			ImageView image = (ImageView) v.findViewById(R.id.icon);
			image.setImageResource(R.drawable.home);
		}
		
		double distance = flatMate.distanceFromHome();
		
		TextView disFHText = (TextView) v.findViewById(R.id.distanceText);
		disFHText.setText(round(distance,2)+"mi from flat");
		
		return v;
	}
	
	public void setDisplayDebts(int set){
		displayDebts = set;
		this.notifyDataSetChanged();
	}

	public double round(double value, int places) {
	    if (places < 0) throw new IllegalArgumentException();

	    long factor = (long) Math.pow(10, places);
	    value = value * factor;
	    long tmp = Math.round(value);
	    return (double) tmp / factor;
	}
}