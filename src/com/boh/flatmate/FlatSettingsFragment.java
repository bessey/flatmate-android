package com.boh.flatmate;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class FlatSettingsFragment extends Fragment {

	private ViewGroup c;
	private View v1;
	FlatListFragment flatList;

	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		v1 = inflater.inflate(R.layout.flat_settings, container, false);
		c = container;

        EditText flatNickname = (EditText) v1.findViewById(R.id.flatNickname);
        flatNickname.setText(FlatMate.FlatDataExchanger.flatData.getNickname());
        
        EditText flatPostcode = (EditText) v1.findViewById(R.id.flatPostcode);
        flatPostcode.setText(FlatMate.FlatDataExchanger.flatData.getPostcode());
        
        Button saveButton = (Button) v1.findViewById(R.id.saveButton);
		saveButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				saveButtonPressed();
			}
		});
		
		return v1;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}
	
	public void saveButtonPressed(){
		EditText flatNicknameBox = (EditText) v1.findViewById(R.id.flatNickname);
		EditText flatPostcodeBox = (EditText) v1.findViewById(R.id.flatPostcode);
		FlatMate.FlatDataExchanger.flatData.setNickname(flatNicknameBox.getText().toString());
		FlatMate.FlatDataExchanger.flatData.setPostcode(flatPostcodeBox.getText().toString());
		
		FlatMate.ConnectionExchanger.connection.updateMyFlat(FlatMate.FlatDataExchanger.flatData);
	}
}
