package com.khareankit.openweather.presenters;

/**
 * @author Ankit khare
 */

public interface WeatherPresenter<V> extends BasePresenter<V> {

    void makeApiCall(String city);


    interface GetWeather {
        void onSuccess(String iObject);

        void onFailure(String iMessage);
    }
}
