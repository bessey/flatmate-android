package com.boh.flatmate;

import com.boh.flatmate.R;
import com.boh.flatmate.FlatMate.FlatDataExchanger;
import com.boh.flatmate.connection.User;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class UFlatMateRowAdapter extends ArrayAdapter<User> {

	private Context context;
	private User[] FlatData;

	public UFlatMateRowAdapter(Context c, int textViewResourceId, User[] data) {
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
			v = vi.inflate(R.layout.uflat_row, null);
		}
		
		String name = flatMate.getFirst_name() +" "+ flatMate.getLast_name();
		if (name != null) {
			TextView tt = (TextView) v.findViewById(R.id.name);
			if (tt != null) {
				tt.setText(name);
			}
		}
		
		Button approve = (Button) v.findViewById(R.id.approveButton);
		Button ignore = (Button) v.findViewById(R.id.ignoreButton);
		final int flatmateId = flatMate.getId();
		
		approve.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				approveFlatmatePressed(flatmateId);
			}
		});
		ignore.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				ignoreFlatmatePressed(flatmateId);
			}
		});

		return v;
	}
	
	public void approveFlatmatePressed(int id){
		FlatMate.ConnectionExchanger.connection.approveMember(id);
	}
	
	public void ignoreFlatmatePressed(int id){
		FlatMate.ConnectionExchanger.connection.ignoreMember(id);
	}
	
}