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
		double latitude = Float.parseFloat(FlatDataExchanger.flatData.getGeocode_lat());
		double longitude = Float.parseFloat(FlatDataExchanger.flatData.getGeocode_long());
		mapController.setCenter(new GeoPoint((int)(latitude * 1E6),(int)(longitude * 1E6)));
		mapController.setZoom(14); // Zoom 1 is world view
		
		List<Overlay> mapOverlays = mapExchanger.mMapView.getOverlays();
		Drawable drawable = this.getResources().getDrawable(R.drawable.flat_marker);
		FlatMateMapOverlay itemizedoverlay = new FlatMateMapOverlay(drawable, context);
		GeoPoint point = new GeoPoint((int)(51.460291*1E6),(int)(-2.608701*1E6));
		OverlayItem overlayitem = new OverlayItem(point, "Flat", "Flat is here!");
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


