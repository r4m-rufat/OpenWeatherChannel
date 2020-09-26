package com.kivitool.openweatherchannel.Models.Weekly;

import java.util.List;

public class WeeklyResult{
	private Current current;
	private String timezone;
	private int timezoneOffset;
	private List<DailyItem> daily;
	private double lon;
	private List<HourlyItem> hourly;
	private List<MinutelyItem> minutely;
	private double lat;

	public Current getCurrent(){
		return current;
	}

	public String getTimezone(){
		return timezone;
	}

	public int getTimezoneOffset(){
		return timezoneOffset;
	}

	public List<DailyItem> getDaily(){
		return daily;
	}

	public double getLon(){
		return lon;
	}

	public List<HourlyItem> getHourly(){
		return hourly;
	}

	public List<MinutelyItem> getMinutely(){
		return minutely;
	}

	public double getLat(){
		return lat;
	}
}