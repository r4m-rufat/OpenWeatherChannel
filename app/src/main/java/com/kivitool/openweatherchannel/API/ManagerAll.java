package com.kivitool.openweatherchannel.API;

import com.kivitool.openweatherchannel.Models.Current.CurrentResult;
import com.kivitool.openweatherchannel.Models.DaysForWind.JsonMember5DaysResult;
import com.kivitool.openweatherchannel.Models.Weekly.Current;
import com.kivitool.openweatherchannel.Models.Weekly.WeeklyResult;

import javax.sql.StatementEvent;

import retrofit2.Call;

public class ManagerAll extends BaseManager{

    public synchronized static ManagerAll getInstance(){
        return new ManagerAll();
    }

    public Call<CurrentResult> getWeatherCurrentInfo(String country_name, String units, String api_key){
        Call<CurrentResult> currentResultCall = getApiInterface().getCurrentInforamtions(country_name, units, api_key);
        return currentResultCall;
    }

    public Call<WeeklyResult> getWeatherWeeklyInformations(String lat, String lon, String daily, String units, String api_key){
        Call<WeeklyResult> weeklyResultCall = getApiInterface().getWeeklyInformations(lat, lon, daily, units,  api_key);
        return weeklyResultCall;
    }

    public Call<JsonMember5DaysResult> getWeather5DaysInformations(String country_name, String api_key){
        Call<JsonMember5DaysResult> jsonMember5DaysResultCall = getApiInterface().get5DaysInformations(country_name, api_key);
        return jsonMember5DaysResultCall;
    }

    public Call<CurrentResult> getWeatherInformationsWithLatLon(String lat, String lon, String units, String api_key){
        Call<CurrentResult> currentResultCall = getApiInterface().getCurrentInforamtionsWithLatLon(lat, lon, units, api_key);
        return currentResultCall;
    }

}
