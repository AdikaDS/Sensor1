package com.example.meet4sensor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager mSensorManager;

    private Sensor mSensorLight;
    private Sensor mSensorProximity;
    private Sensor mSensorTemperature;
    private Sensor mSensorMagnetic;
    private Sensor mSensorPressure;
    private Sensor mSensorRelativeHumidity;

    private TextView mTextSensorLight;
    private TextView mTextSensorProximity;
    private TextView mTextSensorTemperature;
    private TextView mTextSensorMagnetic;
    private TextView mTextSensorPressure;
    private TextView mTextSensorRelativeHumidity;

    private ScrollView scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        scrollView = findViewById(R.id.scroll_view);
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mTextSensorLight = findViewById(R.id.label_light);
        mTextSensorProximity = findViewById(R.id.label_gravity);
        mTextSensorTemperature = findViewById(R.id.label_temperature);
        mTextSensorMagnetic = findViewById(R.id.label_magnetic);
        mTextSensorPressure = findViewById(R.id.label_pressure);
        mTextSensorRelativeHumidity = findViewById(R.id.label_relative);

        // Untuk menampung sensor apa aja yg ada di device

        List<Sensor> sensorList = mSensorManager.getSensorList(Sensor.TYPE_ALL);

        // Pindah string biar tahu sensornya apa aja
        StringBuilder sensorText = new StringBuilder();

        // Di loop karena sensorlist nya berupa list
        for (Sensor currentSensor : sensorList) {
            sensorText.append(currentSensor.getName()).
                    append(System.getProperty("line.separator"));
        }

        TextView sensorTextView = findViewById(R.id.sensor_list);
        sensorTextView.setText(sensorText);

        mSensorProximity = mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        mSensorLight = mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        mSensorTemperature = mSensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
        mSensorMagnetic = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        mSensorPressure = mSensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE);
        mSensorRelativeHumidity = mSensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY);



        String sensorError = "No sensor";
        if (mSensorLight == null) {
            mTextSensorLight.setText(sensorError);
        }
        if (mSensorProximity == null) {
            mTextSensorProximity.setText(sensorError);
        }
        if (mSensorTemperature == null) {
            mTextSensorTemperature.setText(sensorError);
        }
        if (mSensorMagnetic == null) {
            mTextSensorMagnetic.setText(sensorError);
        }
        if (mSensorPressure == null) {
            mTextSensorPressure.setText(sensorError);
        }
        if (mSensorRelativeHumidity == null) {
            mTextSensorRelativeHumidity.setText(sensorError);
        }
    }

    @Override
    // Register sensor disini
    protected void onStart() {
        super.onStart();
        if (mSensorProximity != null) {
            mSensorManager.registerListener(this, mSensorProximity,
                    SensorManager.SENSOR_DELAY_NORMAL);
        }
        if (mSensorLight != null) {
            mSensorManager.registerListener(this, mSensorLight,
                    SensorManager.SENSOR_DELAY_NORMAL);
        }
        if (mSensorTemperature != null) {
            mSensorManager.registerListener(this, mSensorTemperature,
                    SensorManager.SENSOR_DELAY_NORMAL);
        }
        if (mSensorMagnetic != null) {
            mSensorManager.registerListener(this, mSensorMagnetic,
                    SensorManager.SENSOR_DELAY_NORMAL);
        }
        if (mSensorPressure != null) {
            mSensorManager.registerListener(this, mSensorPressure,
                    SensorManager.SENSOR_DELAY_NORMAL);
        }
        if (mSensorRelativeHumidity != null) {
            mSensorManager.registerListener(this, mSensorRelativeHumidity,
                    SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    // Unregister disini
    protected void onStop() {
        super.onStop();
        mSensorManager.unregisterListener(this);
    }

    @Override
    // Jika ada perubahan data taunya dari sini
    public void onSensorChanged(SensorEvent sensorEvent) {
        // Kita dapetin sensor typenya dulu
        int sensorType = sensorEvent.sensor.getType();
        float currentValue = sensorEvent.values[0];
        switch (sensorType) {
            case Sensor.TYPE_LIGHT:
                mTextSensorLight.setText(String.format("Light sensor : %1$.2f", currentValue) + " lux");
                // Logic sendiri
                if (currentValue >= 10000) {
                    scrollView.setBackgroundColor(getResources().getColor(R.color.black));
                } else {
                    scrollView.setBackgroundColor(getResources().getColor(R.color.teal_700));
                }
                break;
            case Sensor.TYPE_PROXIMITY:
                mTextSensorProximity.setText(String.format("Proximity sensor : %1$.2f", currentValue) + " cm");
                break;
            case Sensor.TYPE_AMBIENT_TEMPERATURE:
                mTextSensorTemperature.setText(String.format("Ambient Temperature sensor : %1$.2f", currentValue) + " °C");
                break;
            case Sensor.TYPE_MAGNETIC_FIELD:
                mTextSensorMagnetic.setText(String.format("Magnetic Field sensor : %1$.2f", currentValue) + " North-East-Up uT");
                break;
            case Sensor.TYPE_PRESSURE:
                mTextSensorPressure.setText(String.format("Pressure sensor : %1$.2f", currentValue) + " hPa");
                break;
            case Sensor.TYPE_RELATIVE_HUMIDITY:
                mTextSensorRelativeHumidity.setText(String.format("Relative Humidity sensor : %1$.2f", currentValue) + " %");
                break;
            default:
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}