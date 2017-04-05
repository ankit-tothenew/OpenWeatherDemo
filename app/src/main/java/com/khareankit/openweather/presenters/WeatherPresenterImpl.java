package com.khareankit.openweather.presenters;

import com.google.gson.Gson;
import com.khareankit.openweather.models.current.WeatherModal;
import com.khareankit.openweather.networkcalls.GetWeatherApiService;
import com.khareankit.openweather.networkcalls.RestClient;
import com.khareankit.openweather.networkcalls.WeatherApi;

import retrofit2.Retrofit;

/**
 * @author Ankit khare
 */

public class WeatherPresenterImpl implements WeatherPresenter<WeatherView>, WeatherPresenter.GetWeather {

    /**
     * This is used to load jni code.
     */
    static {
        System.loadLibrary("hide_key");
    }

    /**
     * This is used to return the secret key from c code.
     * @return app_id
     */
    public native String stringFromJNI();

    private WeatherView mWeatherView;
    private Retrofit mRetrofit;

    @Override
    public void attachView(WeatherView view) {
        mRetrofit = RestClient.getClient();
        mWeatherView = view;
    }

    @Override
    public void detachView() {

    }

    @Override
    public void makeApiCall(String city) {
        GetWeatherApiService service = new GetWeatherApiService(mRetrofit.create(WeatherApi.class));
        service.getWeatherData(this, city, stringFromJNI());
    }

    @Override
    public void onSuccess(String iResponse) {
        mWeatherView.onApiResponse(new Gson().fromJson(iResponse, WeatherModal.class));
    }

    @Override
    public void onFailure(String iMessage) {
        mWeatherView.showMessage(iMessage);
    }

}
