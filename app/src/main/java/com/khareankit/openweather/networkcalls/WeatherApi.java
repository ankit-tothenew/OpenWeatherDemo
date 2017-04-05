package com.khareankit.openweather.networkcalls;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * This {@link retrofit2.Retrofit} for creating an interface used to call the api.
 * @author Ankit khare
 */
public interface WeatherApi {

    @GET("2.5/weather?")
    Call<ResponseBody> getWeather(@Query("q") String cityname, @Query("appid") String appid);
}
