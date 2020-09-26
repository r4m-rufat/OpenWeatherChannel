package com.kivitool.openweatherchannel.Models.DaysForWind;

import java.util.List;

public class JsonMember5DaysResult {
	private City city;
	private int cnt;
	private String cod;
	private int message;
	private List<ListItem> list;

	public City getCity(){
		return city;
	}

	public int getCnt(){
		return cnt;
	}

	public String getCod(){
		return cod;
	}

	public int getMessage(){
		return message;
	}

	public List<ListItem> getList(){
		return list;
	}
}