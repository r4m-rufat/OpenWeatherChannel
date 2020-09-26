package com.kivitool.openweatherchannel.API;

import com.kivitool.openweatherchannel.Models.Current.CurrentResult;
import com.kivitool.openweatherchannel.Models.DaysForWind.JsonMember5DaysResult;
import com.kivitool.openweatherchannel.Models.Weekly.WeeklyResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RestApiInterface {

    @GET("weather?")
    Call<CurrentResult> getCurrentInforamtions(
            @Query("q") String country_name,
            @Query("units") String units,
            @Query("appid") String api_key
    );

    @GET("onecall?")
    Call<WeeklyResult> getWeeklyInformations(
            @Query("lat") String lat,
            @Query("lon") String lon,
            @Query("%20exclude") String daily,
            @Query("units") String metric,
            @Query("appid") String api_key
    );

    @GET("forecast?")
    Call<JsonMember5DaysResult> get5DaysInformations(
            @Query("q") String country_name,
            @Query("appid") String key
    );

    @GET("weather?")
    Call<CurrentResult> getCurrentInforamtionsWithLatLon(
            @Query("lat") String lat,
            @Query("lon") String lon,
            @Query("units") String units,
            @Query("appid") String api_key
    );

}
