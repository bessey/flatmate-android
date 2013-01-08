package com.boh.flatmate;

import java.util.List;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.boh.flatmate.R;
import com.boh.flatmate.FlatMate.FlatDataExchanger;
import com.boh.flatmate.FlatMate.mapExchanger;
import com.boh.flatmate.connection.User;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapController;
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
		Drawable drawable = this.getResources().getDrawable(R.drawable.flat_marker);
		FlatMateMapOverlay itemizedoverlay = new FlatMateMapOverlay(drawable, context);
		float flatLat = FlatDataExchanger.flatData.getGeocode_lat();
		float flatLong = FlatDataExchanger.flatData.getGeocode_long();
		GeoPoint point = new GeoPoint((int)(flatLat*1E6),(int)(flatLong*1E6));
		OverlayItem overlayitem = new OverlayItem(point, "Flat", "Flat is here!");
		itemizedoverlay.addOverlay(overlayitem);
		for (User u : FlatDataExchanger.flatData.getUsers()) {
			point = new GeoPoint((int)(u.getGeocode_lat()*1E6),(int)(u.getGeocode_long()*1E6));
			overlayitem = new OverlayItem(point, "Flat", "Flat is here!");
			itemizedoverlay.addOverlay(overlayitem);
		}
		User currU = FlatDataExchanger.flatData.getCurrentUser();
		point = new GeoPoint((int)(currU.getGeocode_lat()*1E6),(int)(currU.getGeocode_long()*1E6));
		overlayitem = new OverlayItem(point, "Flat", "Flat is here!");
		itemizedoverlay.addOverlay(overlayitem);
		mapOverlays.add(itemizedoverlay);
	}

	public Context getContext() {
		return context;
	}

	public void setContext(Context context) {
		this.context = context;
	}
}


