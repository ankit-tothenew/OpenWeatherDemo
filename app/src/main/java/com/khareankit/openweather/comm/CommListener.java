package com.khareankit.openweather.comm;

/**
 * This interface contains the callback methods to be invoked on various events happening while communicating with
 * server.
 * @author Ankit Khare
 */
public interface CommListener {
	/**
	 * Called when the background task to communicate with server starts.
	 */
	public void onCommStarted();

	/**
	 * Called when the Internet is lost while communicating with server.
	 */
	public void onNetworkLost();

	/**
	 * Called when the background task to communicate with server is cancelled.
	 */
	public void onCommCancelled();

	/**
	 * Called when the background task to communicated with server gets completed.
	 * @param response Response data got from server.
	 */
	public void onCommCompleted(String response);
}