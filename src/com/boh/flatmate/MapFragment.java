package com.boh.flatmate;

import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.boh.flatmate.R;
import com.boh.flatmate.FlatMate.ConnectionExchanger;
import com.boh.flatmate.FlatMate.FlatDataExchanger;
import com.boh.flatmate.FlatMate.ShopsExchanger;
import com.boh.flatmate.FlatMate.contextExchanger;
import com.boh.flatmate.FlatMate.mapExchanger;
import com.boh.flatmate.connection.Results;
import com.boh.flatmate.connection.ShopItem;
import com.boh.flatmate.connection.User;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapController;
import com.google.android.maps.MyLocationOverlay;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

public class MapFragment extends Fragment {
	protected ViewGroup mapContainer;
	private Context context;
	private MapController mapController;

	@Override
	public void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setRetainInstance(true);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		setUpMap();

		final ViewGroup parent = (ViewGroup) mapExchanger.mMapView.getParent();
		if (parent != null) parent.removeView(mapExchanger.mMapView);

		return mapExchanger.mMapView;
	} 

	private void setUpMap() {
		mapExchanger.mMapView.preLoad();
		//mapExchanger.mMapView.setBuiltInZoomControls(true);
		mapExchanger.mMapView.setClickable(true);
		mapExchanger.mMapView.setSatellite(false);
		mapController = mapExchanger.mMapView.getController();
		double latitude = FlatDataExchanger.flatData.getGeocode_lat();
		double longitude = FlatDataExchanger.flatData.getGeocode_long();
		if(latitude == 0 || longitude == 0){
			mapController.setCenter(new GeoPoint((int)(51.456333 * 1E6),(int)(-2.606084 * 1E6)));
		}else{
			mapController.setCenter(new GeoPoint((int)(latitude * 1E6),(int)(longitude * 1E6)));
		}
		mapController.setZoom(14); // Zoom 1 is world view

		List<Overlay> mapOverlays = mapExchanger.mMapView.getOverlays();
		Drawable drawableHome = this.getResources().getDrawable(R.drawable.flat_marker);
		FlatMateMapOverlay homeOverlay = new FlatMateMapOverlay(drawableHome, context);
		Drawable drawableUser1 = this.getResources().getDrawable(R.drawable.user_marker1);
		FlatMateMapOverlay userOverlay1 = new FlatMateMapOverlay(drawableUser1, context);
		Drawable drawableUser2 = this.getResources().getDrawable(R.drawable.user_marker2);
		FlatMateMapOverlay userOverlay2 = new FlatMateMapOverlay(drawableUser2, context);
		Drawable drawableUser3 = this.getResources().getDrawable(R.drawable.user_marker3);
		FlatMateMapOverlay userOverlay3 = new FlatMateMapOverlay(drawableUser3, context);
		Drawable drawableUser4 = this.getResources().getDrawable(R.drawable.user_marker4);
		FlatMateMapOverlay userOverlay4 = new FlatMateMapOverlay(drawableUser4, context);
		Drawable drawableUser5 = this.getResources().getDrawable(R.drawable.user_marker5);
		FlatMateMapOverlay userOverlay5 = new FlatMateMapOverlay(drawableUser5, context);
		Drawable drawableUser6 = this.getResources().getDrawable(R.drawable.user_marker6);
		FlatMateMapOverlay userOverlay6 = new FlatMateMapOverlay(drawableUser6, context);
		Drawable drawableUser7 = this.getResources().getDrawable(R.drawable.user_marker7);
		FlatMateMapOverlay userOverlay7 = new FlatMateMapOverlay(drawableUser7, context);
		Drawable drawableUser8 = this.getResources().getDrawable(R.drawable.user_marker8);
		FlatMateMapOverlay userOverlay8 = new FlatMateMapOverlay(drawableUser8, context);

		MyLocationOverlay mylocationOverlay = new MyLocationOverlay(contextExchanger.context, mapExchanger.mMapView);
		mylocationOverlay.enableMyLocation();

		double lat = FlatDataExchanger.flatData.getCurrentUser().getGeocode_lat();
		double lng = FlatDataExchanger.flatData.getCurrentUser().getGeocode_long();
		if(lat == 0.0f || lng == 0.0f){
			lat = FlatDataExchanger.flatData.getGeocode_lat();
			lng = FlatDataExchanger.flatData.getGeocode_long();
		}

		new getNearShops().execute(lat,lng);

		float flatLat = FlatDataExchanger.flatData.getGeocode_lat();
		float flatLong = FlatDataExchanger.flatData.getGeocode_long();
		GeoPoint point = new GeoPoint((int)(flatLat*1E6),(int)(flatLong*1E6));
		OverlayItem overlayitem = new OverlayItem(point, "Flat", "Flat is here!");
		homeOverlay.addOverlay(overlayitem);

		for (User u : FlatDataExchanger.flatData.getUsers()) {
			if(u.getGeocode_lat() != 0.0f && u.getGeocode_long() != 0.0f){
				point = new GeoPoint((int)(u.getGeocode_lat()*1E6),(int)(u.getGeocode_long()*1E6));
				overlayitem = new OverlayItem(point, "User", "User x is here!");
				if(u.getColour_Id() == 0) {
					userOverlay1.addOverlay(overlayitem);
				} else if(u.getColour_Id() == 1) {
					userOverlay2.addOverlay(overlayitem);
				} else if (u.getColour_Id() == 2) {
					userOverlay3.addOverlay(overlayitem);
				} else if (u.getColour_Id() == 3) {
					userOverlay4.addOverlay(overlayitem);
				} else if (u.getColour_Id() == 4) {
					userOverlay5.addOverlay(overlayitem);
				} else if (u.getColour_Id() == 5) {
					userOverlay6.addOverlay(overlayitem);
				} else if (u.getColour_Id() == 6) {
					userOverlay7.addOverlay(overlayitem);
				} else if (u.getColour_Id() == 7) {
					userOverlay8.addOverlay(overlayitem);
				} else {
					userOverlay8.addOverlay(overlayitem);
				}
			}
		}

		/*User currU = FlatDataExchanger.flatData.getCurrentUser();
		point = new GeoPoint((int)(currU.getGeocode_lat()*1E6),(int)(currU.getGeocode_long()*1E6));
		overlayitem = new OverlayItem(point, "Flat", "Flat is here!");
		itemizedoverlay.addOverlay(overlayitem);*/

		mapOverlays.add(homeOverlay);
		if(userOverlay1.size() > 0) mapOverlays.add(userOverlay1);
		if(userOverlay2.size() > 0)mapOverlays.add(userOverlay2);
		if(userOverlay3.size() > 0)mapOverlays.add(userOverlay3);
		if(userOverlay4.size() > 0)mapOverlays.add(userOverlay4);
		if(userOverlay5.size() > 0)mapOverlays.add(userOverlay5);
		if(userOverlay6.size() > 0)mapOverlays.add(userOverlay6);
		if(userOverlay7.size() > 0)mapOverlays.add(userOverlay7);
		if(userOverlay8.size() > 0)mapOverlays.add(userOverlay8);

		mapOverlays.add(mylocationOverlay);
	}

	public void addShopsOverlays(){
		List<Overlay> mapOverlays = mapExchanger.mMapView.getOverlays();
		Drawable drawableShopOpen = this.getResources().getDrawable(R.drawable.shop_marker_open);
		FlatMateMapOverlay shopOverlayOpen = new FlatMateMapOverlay(drawableShopOpen, context);
		Drawable drawableShopClosed = this.getResources().getDrawable(R.drawable.shop_marker_closed);
		FlatMateMapOverlay shopOverlayClosed = new FlatMateMapOverlay(drawableShopClosed, context);
		Drawable drawableShop = this.getResources().getDrawable(R.drawable.shop_marker);
		FlatMateMapOverlay shopOverlay = new FlatMateMapOverlay(drawableShop, context);

		OverlayItem overlayitem;
		GeoPoint point;
		com.boh.flatmate.connection.Location loc;
		int i = 0;

		com.boh.flatmate.connection.Location[] locationArray = new com.boh.flatmate.connection.Location[5]; 

		for (Results u : ShopsExchanger.bestShops.getResults()) {
			locationArray[i] = loc = ShopsExchanger.bestShops.getLocation(i);
			point = new GeoPoint((int)(loc.getLat()*1E6),(int)(loc.getLng()*1E6));
			overlayitem = new OverlayItem(point, "User", "User x is here!");
			if(ShopsExchanger.bestShops.isOpen(i) == 1) {
				shopOverlayOpen.addOverlay(overlayitem);
			} else if(ShopsExchanger.bestShops.isOpen(i) == 0) {
				shopOverlayClosed.addOverlay(overlayitem);
			} else {
				shopOverlay.addOverlay(overlayitem);
			}
			if(i >= 4) break;
			i++;
		}

		int j = 0;
		i = 0;

		for (Results u : ShopsExchanger.nearShops.getResults()) {
			boolean skip = false;
			loc = ShopsExchanger.nearShops.getLocation(i);
			point = new GeoPoint((int)(loc.getLat()*1E6),(int)(loc.getLng()*1E6));
			overlayitem = new OverlayItem(point, "User", "User x is here!");
			for (com.boh.flatmate.connection.Location l : locationArray){
				if(l.isEquals(loc)){
					skip = true;
					break;
				}
			}
			if(!skip){
				if(ShopsExchanger.nearShops.isOpen(i) == 1) {
					shopOverlayOpen.addOverlay(overlayitem);
					j++;
				} else if(ShopsExchanger.nearShops.isOpen(i) == 0) {
					shopOverlayClosed.addOverlay(overlayitem);
					j++;
				} else {
					shopOverlay.addOverlay(overlayitem);
					j++;
				}
				if(j >= 4) break;
				i++;
			}
		}

		if(shopOverlayClosed.size() > 0)mapOverlays.add(shopOverlayClosed);
		if(shopOverlay.size() > 0)mapOverlays.add(shopOverlay);
		if(shopOverlayOpen.size() > 0) mapOverlays.add(shopOverlayOpen);
	}

	public Context getContext() {
		return context;
	}

	public void setContext(Context context) {
		this.context = context;
	}

	public class getNearShops extends AsyncTask<Double,Void,Void> {
		protected Void doInBackground(Double... position) {
			ShopsExchanger.nearShops = ConnectionExchanger.connection.getNearbyShops(position[0], position[1]);
			ShopsExchanger.bestShops = ConnectionExchanger.connection.getBestShops(position[0], position[1], 800);
			return null;
		}

		protected void onPostExecute(Void result) {
			addShopsOverlays();
		}
	}
}


