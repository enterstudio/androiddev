package com.tihonchik.lenonhonor360.models;

public enum LoadType {
	LOAD_NOTHING("loadNothing"), 
	LOAD_WEB("loadWeb"), 
	LOAD_DB("loadDb"), 
	LOAD_NEW("loadNew");

	private String name;

	private LoadType(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
