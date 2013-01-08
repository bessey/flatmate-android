package com.boh.flatmate;

import com.boh.flatmate.R;
import com.boh.flatmate.FlatMate.FlatDataExchanger;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager.OnBackStackChangedListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class FlatFragment extends Fragment {

	private ViewGroup c;
	FlatListFragment flatList;

	private int mapViewOpen = 0;
	private int settingsOpen = 0;

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
		ft.setCustomAnimations(R.anim.slide_up_in, R.anim.slide_out_down);
		ft.replace(R.id.list, flatList,"flat_fragment");
		//ft.addToBackStack(null);
		ft.commit();
		final Button button = (Button) c.findViewById(R.id.map_button);
		button.setText("View Map");
		button.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				if(mapViewOpen == 0){
					android.support.v4.app.FragmentTransaction ft = getFragmentManager().beginTransaction();
					Fragment map = new MapFragment();
					ft.replace(R.id.list, map, "Map_fragment");
					ft.addToBackStack(null);
					ft.commit();
					button.setText("View Flat List");
					View topBar = (View) c.findViewById(R.id.topLayout);
					topBar.setVisibility(View.GONE);
					mapViewOpen = 1;
				}else{
					mapViewOpen = 0;
					getFragmentManager().popBackStack();
					button.setText("View Map");
					View topBar = (View) c.findViewById(R.id.topLayout);
					topBar.setVisibility(View.VISIBLE);
				}
			}
		});
		getFragmentManager().addOnBackStackChangedListener(new OnBackStackChangedListener(){
			public void onBackStackChanged(){
				if(mapViewOpen == 1 && getFragmentManager().getBackStackEntryCount() == 0){
					mapViewOpen = 0;
					button.setText("View Map");
					View topBar = (View) c.findViewById(R.id.topLayout);
					topBar.setVisibility(View.VISIBLE);
				}else if(settingsOpen == 1 && getFragmentManager().getBackStackEntryCount() == 0){
					settingsOpen = 0;
					button.setVisibility(View.VISIBLE);
				}
			}
		});

		final ImageButton settings = (ImageButton) c.findViewById(R.id.settingsButton);
		settings.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				if(settingsOpen == 0){
					settingsOpen = 1;
					android.support.v4.app.FragmentTransaction ft = getFragmentManager().beginTransaction();
					Fragment set = new FlatSettingsFragment();
					ft.replace(R.id.list, set, "Set_fragment");
					ft.addToBackStack(null);
					ft.commit();
					button.setVisibility(View.GONE);
				}else{
					settingsOpen = 0;
					getFragmentManager().popBackStack();
					button.setVisibility(View.VISIBLE);
				}
			}
		});

		TextView flatName = (TextView) c.findViewById(R.id.flatName);
		flatName.setText(FlatDataExchanger.flatData.getNickname());

		TextView atFlat = (TextView) c.findViewById(R.id.atFlatText);
		atFlat.setText(FlatDataExchanger.flatData.getNoAtFlat()+" flat mates at home");

		ImageButton messageButton = (ImageButton)c.findViewById(R.id.messageButton);
		messageButton.setOnClickListener(FlatDataExchanger.flatData.messageListener);
	}
}
