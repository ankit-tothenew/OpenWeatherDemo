package com.khareankit.openweather.networkcalls;

import com.khareankit.openweather.presenters.WeatherPresenter;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * This {@link retrofit2.Retrofit} is to make a {@link retrofit2.http.GET} request for fetching data for a particular city
 * and then notifying to the UI.
 * @author Ankit khare
 */

public class GetWeatherApiService {

    WeatherApi mWeatherApi;

    public GetWeatherApiService(WeatherApi iWeatherApi) {
        mWeatherApi = iWeatherApi;
    }


    public void getWeatherData(final WeatherPresenter.GetWeather notifyUI, String cityName, String appId) {
        Call<ResponseBody> responseBodyCall = mWeatherApi.getWeather(cityName, appId);
        responseBodyCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    notifyUI.onSuccess(response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                notifyUI.onFailure(null);
            }
        });
    }


//    void downloadImage(){
//        OkHttpClient client = new OkHttpClient();
//
//        Request request = new Request.Builder()
//                .url("")
//                .build();
//
//        client.newCall(request).enqueue(new Callback() {
//            @Override
//            public void onFailure(Request request, IOException e) {
//                System.out.println("request failed: " + e.getMessage());
//            }
//
//            @Override
//            public void onResponse(Response response) throws IOException {
//                response.body().byteStream(); // Read the data from the stream
//            }
//        });
//    }
}
