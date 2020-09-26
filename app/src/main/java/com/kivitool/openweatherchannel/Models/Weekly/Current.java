package com.kivitool.openweatherchannel.Models.Weekly;

import java.util.List;

public class Current{
	private int sunrise;
	private double temp;
	private int visibility;
	private double uvi;
	private int pressure;
	private int clouds;
	private double feelsLike;
	private int dt;
	private int windDeg;
	private double dewPoint;
	private int sunset;
	private List<WeatherItem> weather;
	private int humidity;
	private double windSpeed;

	public int getSunrise(){
		return sunrise;
	}

	public double getTemp(){
		return temp;
	}

	public int getVisibility(){
		return visibility;
	}

	public double getUvi(){
		return uvi;
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

	public int getDt(){
		return dt;
	}

	public int getWindDeg(){
		return windDeg;
	}

	public double getDewPoint(){
		return dewPoint;
	}

	public int getSunset(){
		return sunset;
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
}