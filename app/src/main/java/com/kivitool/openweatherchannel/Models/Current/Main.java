package com.kivitool.openweatherchannel.Models.Current;

public class Main{
	private double temp;
	private double tempMin;
	private int humidity;
	private int pressure;
	private double feelsLike;
	private double tempMax;

	public double getTemp(){
		return temp;
	}

	public double getTempMin(){
		return tempMin;
	}

	public int getHumidity(){
		return humidity;
	}

	public int getPressure(){
		return pressure;
	}

	public double getFeelsLike(){
		return feelsLike;
	}

	public double getTempMax(){
		return tempMax;
	}
}
