package com.boh.flatmate;

import com.boh.flatmate.R;
import com.boh.flatmate.FlatMate.FlatDataExchanger;
import com.boh.flatmate.connection.User;

import android.content.Context;
import android.util.Log;
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
		//Log.v("USER","Position " + position + " out of " + (FlatData.length-1));
		//Log.v("USER","Name " + flatMate.getFirst_name());
		if (v == null) {
			LayoutInflater vi = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = vi.inflate(R.layout.flat_row, null);
		}

		if(flatMate.getColour_Id() == 0) {
			v.setBackgroundResource(0);
			v.setBackgroundColor(v.getContext().getResources().getColor(R.color.user_1));
		} else if(flatMate.getColour_Id() == 1) {
			v.setBackgroundResource(0);
			v.setBackgroundColor(v.getContext().getResources().getColor(R.color.user_2));
		} else if (flatMate.getColour_Id() == 2) {
			v.setBackgroundResource(0);
			v.setBackgroundColor(v.getContext().getResources().getColor(R.color.user_3));
		} else if (flatMate.getColour_Id() == 3) {
			v.setBackgroundResource(0);
			v.setBackgroundColor(v.getContext().getResources().getColor(R.color.user_4));
		} else if (flatMate.getColour_Id() == 4) {
			v.setBackgroundResource(0);
			v.setBackgroundColor(v.getContext().getResources().getColor(R.color.user_5));
		} else if (flatMate.getColour_Id() == 5) {
			v.setBackgroundResource(0);
			v.setBackgroundColor(v.getContext().getResources().getColor(R.color.user_6));
		} else if (flatMate.getColour_Id() == 6) {
			v.setBackgroundResource(0);
			v.setBackgroundColor(v.getContext().getResources().getColor(R.color.user_7));
		} else if (flatMate.getColour_Id() == 7) {
			v.setBackgroundResource(0);
			v.setBackgroundColor(v.getContext().getResources().getColor(R.color.user_8));
		} else {
			v.setBackgroundResource(R.drawable.box8);
			v.setBackgroundColor(v.getContext().getResources().getColor(R.color.user_8));
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

		if(flatMate.isHome() != 1){
			ImageView image = (ImageView) v.findViewById(R.id.icon);
			image.setImageResource(R.drawable.home_x);
		}else{
			ImageView image = (ImageView) v.findViewById(R.id.icon);
			image.setImageResource(R.drawable.home);
		}

		double distance = flatMate.distanceFromHome();

		TextView disFHText = (TextView) v.findViewById(R.id.distanceText);
		if(flatMate.isHome() != -1){
			if(distance < 500){
				disFHText.setText(round(distance,2)+"m from flat");
			}else{
				disFHText.setText(round((distance/1000),2)+"Km from flat");
			}
		}else{
			disFHText.setText("Unknown Location");
		}
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