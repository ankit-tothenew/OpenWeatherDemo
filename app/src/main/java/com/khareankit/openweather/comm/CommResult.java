package com.khareankit.openweather.comm;

/**
 * This class contains the data got from server after a request is made.
 * @author Ankit Khare
 */
class CommResult {
	// Response Codes - HTTP
	public static final int SUCCESS = 200;
	public static final int REDIRECT = 300;
	public static final int BAD_REQUEST = 400;
	public static final int UNAUTHORIZED = 401;
	public static final int FORBIDDEN = 403;
	public static final int NOT_FOUND = 404;
	public static final int CONFLICT = 409;
	public static final int GONE = 410;

	public static final int HOST_UNREACHABLE = -1;

	// Extra Headers attached
	public static final String REDIRECT_HEADER = "Location";

	/**
	 * Code got in result from server.
	 */
	private int mResultCode;

	/**
	 * Data got from server, for positive result.
	 */
	private String mDataString;

	/**
	 * Error string supplied by server in case of error.
	 */
	private String mErrorString;

	public int getCode() {
		return mResultCode;
	}

	public void setCode(int code) {
		mResultCode = code;
	}

	public String getData() {
		return mDataString;
	}

	public void setData(String data) {
		mDataString = data;
	}

	public String getError() {
		return mErrorString;
	}

	public void setError(String error) {
		mErrorString = error;
	}
}