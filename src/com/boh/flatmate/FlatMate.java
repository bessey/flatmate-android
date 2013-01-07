package com.boh.flatmate;

import com.boh.flatmate.R;
import com.boh.flatmate.connection.Flat;
import com.boh.flatmate.connection.ServerConnection;
import com.google.android.maps.MapView;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

public class FlatMate extends FragmentActivity implements ActionBar.TabListener {

	AppSectionsPagerAdapter mAppSectionsPagerAdapter;

	ViewPager mViewPager;
	View mMapViewContainer;
	MapView mMapView;

	public void onCreate(Bundle savedInstanceState) {
		setTheme(R.style.flatMateTheme);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.flat_mate);

		contextExchanger.context = this;
		
		ConnectionExchanger.connection = new ServerConnection();

		FlatDataExchanger.flatData = new Flat();
		
		FlatDataExchanger.flatData = ConnectionExchanger.connection.getMyFlat();
		FlatDataExchanger.flatData.setCurrentUser(ConnectionExchanger.connection.getMe());
		int flatId = FlatDataExchanger.flatData.getId();
		
		mapExchanger.mMapView = new MapView(this, getString(R.string.maps_api_key));
		
		Intent service = new Intent(FlatMate.this, UpdateService.class);
		startService(service);

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
				.setIcon(R.drawable.tasks_tab)
				.setTabListener(this));
		
	}
	
	@Override
	public void onStart(){
		super.onStart();
		//new loadData().execute();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.options, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		switch (item.getItemId()) {
		case R.id.menu_logout:
			return true;
		case R.id.menu_settings:
			return true;
		default:
			return true;
		}
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
				Fragment ShoppingList =  new ShoppingFragment();
				return ShoppingList;   
			case 2:
				return new TasksFragment();
			default:
				Fragment FlatListDefault =  new FlatListFragment();
				return FlatListDefault;
			}
		}

		@Override
		public int getCount() {
			return 3;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			return "";
		}
	}
	
	public static class ConnectionExchanger{
		public static ServerConnection connection;
	}

	public static class FlatDataExchanger{
		public static Flat flatData;
	}

	public static class contextExchanger{
		public static Context context;
	}

	public static class mapExchanger {
		public static MapView mMapView;
	}
}
