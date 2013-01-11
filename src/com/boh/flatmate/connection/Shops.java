package com.boh.flatmate.connection;


public class Shops {
	private Results[] results;

	public Results[] getResults() {
		return results;
	}

	public void setResults(Results[] results) {
		this.results = results;
	}
	
	public ShopLocation getLocation(int i) {
		if (results[i].getGeometry().getLocation() != null) {
			return results[i].getGeometry().getLocation();
		} else return null;
	}
	
	public int isOpen(int i) {
		if (results[i].getOpening_Hours() == null) {
			return -1;
		} else if (results[i].getOpening_Hours().isOpen_now()) {
			return 1;
		} else return 0;
	}
	
	public String getName(int i) {
		if (results[i].getName() != null) {
			return results[i].getName().getName();
		} else {
			return "";
		}
	}
}
