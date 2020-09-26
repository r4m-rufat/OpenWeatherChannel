package com.kivitool.openweatherchannel.Models.DaysForWind;

import java.util.List;

public class ListItem{
	private int dt;
	private double pop;
	private int visibility;
	private String dtTxt;
	private List<WeatherItem> weather;
	private Main main;
	private Clouds clouds;
	private Sys sys;
	private Wind wind;
	private Rain rain;

	public int getDt(){
		return dt;
	}

	public double getPop(){
		return pop;
	}

	public int getVisibility(){
		return visibility;
	}

	public String getDtTxt(){
		return dtTxt;
	}

	public List<WeatherItem> getWeather(){
		return weather;
	}

	public Main getMain(){
		return main;
	}

	public Clouds getClouds(){
		return clouds;
	}

	public Sys getSys(){
		return sys;
	}

	public Wind getWind(){
		return wind;
	}

	public Rain getRain(){
		return rain;
	}
}