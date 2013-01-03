package com.boh.flatmate;

import com.boh.flatmate.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager.OnBackStackChangedListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class FlatFragment extends Fragment {

	private ViewGroup c;
	FlatListFragment flatList;

	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v1 = inflater.inflate(R.layout.flat_mate_page, container, false);
		c = container;
		return v1;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		android.support.v4.app.FragmentTransaction ft = getFragmentManager().beginTransaction();
		flatList = new FlatListFragment();
		ft.add(R.id.list, flatList,"flat_fragment");
		//ft.addToBackStack(null);
		ft.commit();
		final Button button = (Button) c.findViewById(R.id.map_button);
		button.setText("View Map");
		button.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				if(getFragmentManager().getBackStackEntryCount() == 0){
					android.support.v4.app.FragmentTransaction ft = getFragmentManager().beginTransaction();
					Fragment map = new MapFragment();
					ft.replace(R.id.list, map, "Map_fragment");
					ft.addToBackStack(null);
					ft.commit();
					button.setText("Flat List");
				}else{
					getFragmentManager().popBackStack();
					button.setText("View Map");
				}
			}
		});
		getFragmentManager().addOnBackStackChangedListener(new OnBackStackChangedListener(){
			public void onBackStackChanged(){
				if(getFragmentManager().getBackStackEntryCount() == 0){
					button.setText("View Map");
				}
			}
		});
	}
}
