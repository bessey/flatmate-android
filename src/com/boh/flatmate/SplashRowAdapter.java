package com.boh.flatmate;

import com.boh.flatmate.R;
import com.boh.flatmate.FlatMate.FlatDataExchanger;
import com.boh.flatmate.connection.Flat;
import com.boh.flatmate.connection.User;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashRowAdapter extends ArrayAdapter<Flat> {

	private Context context;
	private Flat[] FlatData;

	public SplashRowAdapter(Context c, int textViewResourceId, Flat[] data) {
		super(c, textViewResourceId, data);
		FlatData = data;
		context = c;
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
		Flat flat = FlatData[position];
		if (v == null) {
			LayoutInflater vi = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = vi.inflate(R.layout.flat_row, null);
		}
		
		String name = flat.getNickname();
		if (name != null) {
			TextView tt = (TextView) v.findViewById(R.id.name);
			if (tt != null) {
				tt.setText(name);
			}
		}
		
		return v;
	}
}