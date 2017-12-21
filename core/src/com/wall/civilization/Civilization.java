package com.wall.civilization;

import org.json.simple.JSONObject;

public class Civilization {

	public static enum Country { GERMANY, ENGLAND, SPAIN, RUSSIA, MERICA };
	
	public final String name;
	
	public static String getFilePath(Country country) {
		return country.values().toString() + ".json";
	}
	
	public Civilization(JSONObject json) {
		name = (String) json.get("name");
	}
	
	
	
}
