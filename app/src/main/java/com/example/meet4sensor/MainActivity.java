package com.example.meet4sensor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager mSensorManager;

    private Sensor mSensorLight;
    private Sensor mSensorProximity;

    private TextView mTextSensorLight;
    private TextView mTextSensorProximity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
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

        mTextSensorLight = findViewById(R.id.label_light);
        mTextSensorProximity = findViewById(R.id.label_gravity);

        mSensorProximity = mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        mSensorLight = mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);

        String sensorError = "No sensor";
        if (mSensorLight == null) {
            mTextSensorLight.setText(sensorError);
        }

        if (mSensorProximity == null) {
            mTextSensorProximity.setText(sensorError);
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
                mTextSensorLight.setText(String.format("Light sensor : %1$.2f", currentValue));
                break;
            case Sensor.TYPE_PROXIMITY:
                mTextSensorProximity.setText(String.format("Proximity sensor : %1$.2f", currentValue));
                break;
            default:
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}