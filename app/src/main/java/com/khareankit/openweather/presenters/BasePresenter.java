package com.khareankit.openweather.presenters;

/**
 * Base Presenter containing method for attaching the View to Presenter.
 * @author Ankit khare
 */

public interface BasePresenter<V> {

    void attachView(V view);

    void detachView();

}