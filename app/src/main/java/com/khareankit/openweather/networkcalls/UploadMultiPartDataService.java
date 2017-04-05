package com.khareankit.openweather.networkcalls;

import com.khareankit.openweather.presenters.WeatherPresenter;

import java.io.File;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Ankit Khare on 5/4/17.
 */

public class UploadMultiPartDataService {

    MultiPartApi mMultiPartApi;

    public UploadMultiPartDataService(MultiPartApi iMultiPartApi) {
        mMultiPartApi = iMultiPartApi;
    }


    public void getWeatherData(final WeatherPresenter.GetWeather notifyUI, String filePath) {
        File file = new File(filePath);

        RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("upload", file.getName(), reqFile);
        RequestBody name = RequestBody.create(MediaType.parse("text/plain"), "upload_test");

        Call<ResponseBody> responseBodyCall = mMultiPartApi.postImage(body, name);
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
}
