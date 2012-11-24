package com.Ballin_Octo_Hipster.flatmate;

import com.Ballin_Octo_Hipster.flatmate.data.Flat_data;
import com.google.android.maps.MapView;

import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.ListFragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

public class FlatMate extends FragmentActivity implements ActionBar.TabListener {

	AppSectionsPagerAdapter mAppSectionsPagerAdapter;

	ViewPager mViewPager;
	View mMapViewContainer;
	MapView mMapView;

	public void onCreate(Bundle savedInstanceState) {
		setTheme(R.style.flatMateTheme);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.flat_mate);
		
		FlatDataExchanger.flatData = new Flat_data();
		contextExchanger.context = getBaseContext();
		mapExchanger.mMapView = new MapView(this, getString(R.string.maps_api_key));

		mAppSectionsPagerAdapter = new AppSectionsPagerAdapter(getSupportFragmentManager());

		final ActionBar actionBar = getActionBar();

		//actionBar.setHomeButtonEnabled(false);

		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mAppSectionsPagerAdapter);
		mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
			@Override
			public void onPageSelected(int position) {
				actionBar.setSelectedNavigationItem(position);
			}
		});

		actionBar.addTab(
				actionBar.newTab()
				.setIcon(R.drawable.flatmates_tab)
				.setTabListener(this));
		actionBar.addTab(
				actionBar.newTab()
				.setIcon(R.drawable.shopping_tab)
				.setTabListener(this));
		actionBar.addTab(
				actionBar.newTab()
				.setText("Tasks")
				.setTabListener(this));
		actionBar.addTab(
				actionBar.newTab()
				.setText("???")
				.setTabListener(this));
		
	}

	@Override
	public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
	}

	@Override
	public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction){
		mViewPager.setCurrentItem(tab.getPosition());
		
	}

	@Override
	public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
	}

	public static class AppSectionsPagerAdapter extends FragmentPagerAdapter {

		public AppSectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int i) {
			switch (i) {
			case 0:
				Fragment FlatList =  new FlatFragment();
				return FlatList;
			case 1:
				Fragment ShoppingList =  new ShoppingListFragment();
				return ShoppingList;   
			case 2:
				return new TasksFragment();
			case 3:
				return new TasksFragment();
			default:
				Fragment FlatListDefault =  new FlatListFragment();
				return FlatListDefault;
			}
		}

		@Override
		public int getCount() {
			return 4;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			return "";
		}
	}
	
	public static class FlatDataExchanger{
		public static Flat_data flatData;
	}
	
	public static class contextExchanger{
		public static Context context;
	}
	
	public static class mapExchanger {
        public static MapView mMapView;
    }
	
	public static class FlatFragment extends Fragment {
		
		private ViewGroup c;
		
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
			FlatListFragment flatList = new FlatListFragment();
			ft.add(R.id.list, flatList);
			//ft.addToBackStack(null);
			ft.commit();
			Button button = (Button) c.findViewById(R.id.map_button);
			button.setOnClickListener(new View.OnClickListener() {
			    public void onClick(View v) {
			    	android.support.v4.app.FragmentTransaction ft = getFragmentManager().beginTransaction();
			    	Fragment map = new MapFragment();
					ft.replace(R.id.list, map);
					ft.addToBackStack(null);
					ft.commit();
			    }
			});
		}
	}
	
	public static class FlatListFragment extends ListFragment {

		private ListView mListView;
		private FlatMateRowAdapter mAdapter;
		
		@Override
		public void onCreate(Bundle savedInstanceState) 
		{
		    super.onCreate(savedInstanceState);
		    mAdapter = new FlatMateRowAdapter(getActivity(), android.R.id.list, FlatDataExchanger.flatData.getFlatMates());
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			View v1 = inflater.inflate(R.layout.flat_list, container, false);
		    mListView = (ListView) v1.findViewById(android.R.id.list);
		    return v1;
		}

		@Override
		public void onActivityCreated(Bundle savedInstanceState) {
			super.onActivityCreated(savedInstanceState);
		    mListView.setAdapter(mAdapter);
		}
	}
	
	public static class ShoppingListFragment extends ListFragment {

	}
	
	public static class TasksFragment extends Fragment {

	}
}
