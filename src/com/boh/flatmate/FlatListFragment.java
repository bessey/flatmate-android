package com.boh.flatmate;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.boh.flatmate.R;
import com.boh.flatmate.FlatMate.FlatDataExchanger;

public class FlatListFragment extends ListFragment {

	private ListView mListView;
	private FlatMateRowAdapter mAdapter;

	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		mAdapter = new FlatMateRowAdapter(getActivity(), android.R.id.list, FlatDataExchanger.flatData.getUsers());
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v1 = inflater.inflate(R.layout.flat_list, container, false);
		mListView = (ListView) v1.findViewById(android.R.id.list);
		
		TextView flatName = (TextView) v1.findViewById(R.id.flatName);
		flatName.setText(FlatDataExchanger.flatData.getNickname());
		
		TextView atFlat = (TextView) v1.findViewById(R.id.atFlatText);
		atFlat.setText(FlatDataExchanger.flatData.getNoAtFlat()+" flat mates at home");
		
		ImageButton messageButton = (ImageButton)v1.findViewById(R.id.messageButton);
		messageButton.setOnClickListener(FlatDataExchanger.flatData.messageListener);
		
		return v1;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		mListView.setAdapter(mAdapter);
	}
}