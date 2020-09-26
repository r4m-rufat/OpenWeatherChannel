package com.kivitool.openweatherchannel.Models.Weekly;

import java.util.List;

public class DailyItem{
	private double rain;
	private int sunrise;
	private Temp temp;
	private double uvi;
	private int pressure;
	private int clouds;
	private FeelsLike feelsLike;
	private int dt;
	private double pop;
	private int windDeg;
	private double dewPoint;
	private int sunset;
	private List<WeatherItem> weather;
	private int humidity;
	private double windSpeed;

	public double getRain(){
		return rain;
	}

	public int getSunrise(){
		return sunrise;
	}

	public Temp getTemp(){
		return temp;
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

	public FeelsLike getFeelsLike(){
		return feelsLike;
	}

	public int getDt(){
		return dt;
	}

	public double getPop(){
		return pop;
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