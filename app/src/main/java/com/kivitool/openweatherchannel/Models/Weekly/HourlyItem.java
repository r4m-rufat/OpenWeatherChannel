package com.kivitool.openweatherchannel.Models.Weekly;

import java.util.List;

public class HourlyItem{
	private int dt;
	// change this item
	private double pop;
	private double temp;
	private int windDeg;
	private int visibility;
	private double dewPoint;
	private List<WeatherItem> weather;
	private int humidity;
	private double windSpeed;
	private int pressure;
	private int clouds;
	private double feelsLike;

	public HourlyItem() {
	}

	public int getDt(){
		return dt;
	}

	public double getPop(){
		return pop;
	}

	public double getTemp(){
		return temp;
	}

	public int getWindDeg(){
		return windDeg;
	}

	public int getVisibility(){
		return visibility;
	}

	public double getDewPoint(){
		return dewPoint;
	}

	public List<WeatherItem> getWeather(){
		return weather;
	}

	public int getHumidity(){
		return humidity;
	}

	public double getWindSpeed(){
		return windSpeed;
	}

	public int getPressure(){
		return pressure;
	}

	public int getClouds(){
		return clouds;
	}

	public double getFeelsLike(){
		return feelsLike;
	}
}