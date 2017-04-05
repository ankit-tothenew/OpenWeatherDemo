package com.khareankit.openweather.view;

import android.content.DialogInterface;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.khareankit.openweather.R;
import com.khareankit.openweather.location.UpdateUserLocation;
import com.khareankit.openweather.models.current.WeatherModal;
import com.khareankit.openweather.presenters.WeatherPresenterImpl;
import com.khareankit.openweather.presenters.WeatherView;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * This {@link AppCompatActivity} is to display Weather data for a particular city from OpenWeather
 *
 * @author Ankit khare
 */
public class WeatherActivity extends AppCompatActivity implements WeatherView, UpdateUserLocation {

    TextView mCityField;
    TextView mUpdatedField;
    TextView mDetailsField;
    TextView mCurrentTemperatureField;
    ProgressBar mProgressBar;

    private WeatherModal mWeatherModal;
    private WeatherPresenterImpl mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weather_layout);
        mPresenter = new WeatherPresenterImpl();
        mPresenter.attachView(this);
        initViews();
    }

    private void initViews() {
        mCityField = (TextView) findViewById(R.id.city_field);
        mUpdatedField = (TextView) findViewById(R.id.updated_field);
        mDetailsField = (TextView) findViewById(R.id.details_field);
        mCurrentTemperatureField = (TextView) findViewById(R.id.current_temperature_field);
        mProgressBar = (ProgressBar) findViewById(R.id.progressbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.weather, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.change_city) {
            showInputDialog();
        }
        return false;

    }


    private void showInputDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Enter city");
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);
        builder.setPositiveButton("Go", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mProgressBar.setVisibility(View.VISIBLE);
                mPresenter.makeApiCall(input.getText().toString());
            }
        });
        builder.show();
    }

    private void updateUI() {
        if (mWeatherModal != null) {
            mCityField.setText(mWeatherModal.getName().toUpperCase(Locale.US) +
                    ", " +
                    mWeatherModal.getSys().getCountry());

            mDetailsField.setText(
                    mWeatherModal.getWeather().get(0).getDescription().toUpperCase(Locale.US) +
                            "\n" + "Humidity: " + mWeatherModal.getMain().getHumidity() + "%" +
                            "\n" + "Pressure: " + mWeatherModal.getMain().getPressure() + " hPa");


            mCurrentTemperatureField.setText(convert(mWeatherModal.getMain().getTemp()) + " ℃");

            DateFormat df = DateFormat.getDateTimeInstance();
            String updatedOn = df.format(new Date(mWeatherModal.getDt() * 1000));
            mUpdatedField.setText("Last update: " + updatedOn);

        }
    }

    private String convert(float val) {
        float vala = ((val - 32) * 5) / 9;
        return String.valueOf(vala);
    }

    @Override
    public void onLocationChanged(Location iLocation) {

    }

    @Override
    public void onError(String iMessaage) {
        Toast.makeText(this, iMessaage, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onApiResponse(WeatherModal iWeatherModal) {
        mProgressBar.setVisibility(View.GONE);
        mWeatherModal = iWeatherModal;
        updateUI();
    }

    @Override
    public void showMessage(String iMessage) {

    }
}
