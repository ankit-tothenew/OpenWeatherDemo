package com.khareankit.openweather.presenters;

import com.khareankit.openweather.models.current.WeatherModal;

/**
 * Interfcae to notify UI when we receive an update from presenter.
 * * @author Ankit khare
 */

public interface WeatherView {

    void onApiResponse(WeatherModal iWeatherModal);

    void showMessage(String iMessage);
}
