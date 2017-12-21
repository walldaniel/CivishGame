package com.wall.civilization;



import java.util.ArrayList;

import com.wall.civilization.unit.Unit;

public class Player {

	private ArrayList<Unit> units;
//	private Civilization civilization;	TODO: Add what civilization the user is playing and custom units
	
	public Player( ) {
		units = new ArrayList<Unit>();
	}
	
	// Adds a unit to the list of current units
	public void addUnit(Unit unit) {
		units.add(unit);
	}
	
	public ArrayList<Unit> getUnits() {
		return units;
	}
}
