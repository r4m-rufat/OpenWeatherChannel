package com.kivitool.openweatherchannel.Models.DaysForWind;

public class City{
	private String country;
	private Coord coord;
	private int sunrise;
	private int timezone;
	private int sunset;
	private String name;
	private int id;
	private int population;

	public String getCountry(){
		return country;
	}

	public Coord getCoord(){
		return coord;
	}

	public int getSunrise(){
		return sunrise;
	}

	public int getTimezone(){
		return timezone;
	}

	public int getSunset(){
		return sunset;
	}

	public String getName(){
		return name;
	}

	public int getId(){
		return id;
	}

	public int getPopulation(){
		return population;
	}
}
