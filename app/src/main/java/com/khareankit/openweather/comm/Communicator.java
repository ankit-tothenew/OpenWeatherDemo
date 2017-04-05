package com.khareankit.openweather.comm;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.UnknownHostException;

/**
 * This {@link AsyncTask} is used for communicating with the server and then return result to the caller along with
 * error (if occurred).
 *
 * @author Ankit khare
 */
public class Communicator extends AsyncTask<Void, Void, Void> {
    private static final String AND = "&";
    private static final String EQUAL = "=";
    private static final String NEXT_LINE = "\n";
    private static final int TIME_OUT = 30 * 1000;
    private static final String HEADER_TYPE = "Content-Type";
    private static final String LOG_TAG = Communicator.class.getSimpleName();
    private static final String HEADER_BODY = "application/x-www-form-urlencoded;charset=utf-8";
    // private static final String UTF_8 = "UTF-8";

    /**
     * true if the {@link AsyncTask} is cancelled, false otherwise.
     */
    private boolean isCancelled = false;

    private String url;

    private String method;

    /**
     * Constructor.
     */
    public Communicator() {

    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }


    @Override
    protected Void doInBackground(Void... params) {
        publishProgress(); // Update progress when task start.
        return null;
       // return makeRequest(url);
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    /**
     * Open a {@link HttpURLConnection} and then make a request to it, read the response of server and return it.
     *
     * @param urlString URL with which {@link HttpURLConnection} needs to be opened.
     * @return The response got from server.
     */
    private CommResult makeRequest(String urlString) {
        HttpURLConnection urlConnection = null;
        CommResult result = new CommResult();
        OutputStream outputStream = null;
        InputStream inputStream = null;
        String requestBody = null;
        try {
            URL url = new URL(urlString);
            // Opening a connection for URL made for request.
            urlConnection = (HttpURLConnection) url.openConnection();

            // Set the connection to follow redirects
            HttpURLConnection.setFollowRedirects(true);
            urlConnection.setInstanceFollowRedirects(true);

            //Set this if you want to enable cookie

//			// Handling cookies only for login requests
//			if (mCommType == CommType.LOGIN) {
//				CookieManager cookieManager = new CookieManager();
//				CookieHandler.setDefault(cookieManager);
//			}

            // Set the connection time out after 30 sec.
            urlConnection.setConnectTimeout(TIME_OUT);

            // Set the request method and parameters for request.
            urlConnection.setRequestMethod(method);
            switch (method) {
                // GET and PUT are not in use for now.
                case RequestConstants.GET:
                    urlConnection.setRequestProperty(HEADER_TYPE, HEADER_BODY);
                    break;
                // case PUT:
                // break;
                case RequestConstants.POST:
                    // Set the Input/Output type in connection
                    urlConnection.setDoInput(true);
                    urlConnection.setDoOutput(true);
                    urlConnection.setRequestProperty(HEADER_TYPE, HEADER_BODY);

                    // Attaching request body to the request
                    outputStream = urlConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream));
                    //requestBody = makeRequestStringFromParams();
                  //  bufferedWriter.write(requestBody);
                    bufferedWriter.close();
                    break;
            }

            // Assert the cancellation of Task
            if (isCancelled)
                return null;

            // Get the error stream if sent by server
            InputStream errorStream = urlConnection.getErrorStream();
            if (errorStream != null) {
                String errorString = convertToString(inputStream);
                result.setError(errorString);
                errorStream.close();
            }

            // Assert the cancellation of Task
            if (isCancelled)
                return null;

            // Get the response code sent from server
            int code = urlConnection.getResponseCode();
            LogEvent.logInfo(LOG_TAG, "Response Code " + code);
            result.setCode(code);

            // Redirect if required else set the input as data in response
            if (code >= CommResult.REDIRECT && code < CommResult.BAD_REQUEST) {
                LogEvent.logInfo(LOG_TAG, "Redirecting request to new location");
                return makeRequest(urlConnection.getHeaderField(CommResult.REDIRECT_HEADER));
            } else {
                // Server has sent the response, read it.
                inputStream = urlConnection.getInputStream();
                String dataString = convertToString(inputStream);
                LogEvent.logInfo(LOG_TAG, "Response fetched");
                result.setData(dataString);
            }
        } catch (UnknownHostException unknownHost) {
            // Set the code as Internet not reachable.
            result.setCode(CommResult.HOST_UNREACHABLE);
            LogEvent.printStack(unknownHost);
        } catch (IOException ioException) {
            LogEvent.printStack(ioException);
        } finally {
            // Closing the URL connection
            if (urlConnection != null)
                urlConnection.disconnect();

            // Closing the Output Stream
            if (outputStream != null)
                try {
                    outputStream.flush();
                    outputStream.close();
                } catch (IOException ioException) {
                    LogEvent.printStack(ioException);
                }

            // Closing the Input Stream
            if (inputStream != null)
                try {
                    inputStream.close();
                } catch (IOException ioException) {
                    LogEvent.printStack(ioException);
                }
        }
        return result;
    }

    /**
     * @return Make the string out of the supplied input stream and return it.
     */
    private String convertToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = null;
        InputStreamReader streamReader = null;
        try {
            if (inputStream != null) {
                // Read the stream in a reader and make a string buffer.
                streamReader = new InputStreamReader(inputStream);
                bufferedReader = new BufferedReader(streamReader);
                StringBuilder response = new StringBuilder();
                String line;
                // While AsyncTask is not cancelled, Read input stream and put it in a String Buffer
                while (!isCancelled && (line = bufferedReader.readLine()) != null) {
                    response.append(line);
                    response.append(NEXT_LINE);
                }
                // Remove the extra last line from string.
                int lastNextLine = response.lastIndexOf(NEXT_LINE);
                if (lastNextLine != -1) {
                    response.replace(lastNextLine, response.length(), "");
                }
                // Return the string form of data.
                return response.toString();
            }
        } finally {
            // Close streams so that there is no data leak
            if (bufferedReader != null)
                bufferedReader.close();
            if (streamReader != null)
                streamReader.close();
        }
        return null;
    }

    /**
     * Cancel the current {@link AsyncTask} and invoked the callback method onCommCancelled().
     */
    public void cancelTask() {
        isCancelled = true;
    }

    @Override
    protected void onPostExecute(Void result) {
        super.onPostExecute(result);

    }

    /**
     * @param response Response got from user, it contains Response Code, Main Data and Error (if any).
     * @return Data got in response, or null if error occurred.
     */
    private String readDataFromResult(CommResult response) {
        int responseCode = response.getCode();
        String responseData = response.getData();
        // Print the error if no response data got.
        if (responseData == null) {
            String responseError = response.getError();
        }
        // Return the result only for success.
        else if (responseCode == CommResult.SUCCESS) {
            return responseData;
        }
        return null;
    }

}