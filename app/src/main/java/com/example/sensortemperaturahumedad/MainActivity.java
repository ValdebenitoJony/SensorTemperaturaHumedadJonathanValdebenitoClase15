package com.example.sensortemperaturahumedad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    private TextView temp;
    private TextView hume;
    private SensorManager sensorManager;
    private Sensor tempSensor;
    private Sensor humeSensor;
    private Boolean tempDisponible;
    private Boolean humeDisponible;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        temp = findViewById(R.id.temp);
        hume = findViewById(R.id.hume);
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        tempDisponible = false;
        humeDisponible = false;

        if (sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE) != null){
            tempSensor = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
            tempDisponible = true;

        }else {
            temp.setText("el sensor de temperatura no esta disponoble");
        }
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent){
        if (sensorEvent.sensor.getType()== Sensor.TYPE_AMBIENT_TEMPERATURE){
            temp.setText(sensorEvent.values[0] + " C");
        }else if (sensorEvent.sensor.getType() == Sensor.TYPE_RELATIVE_HUMIDITY){
            hume.setText(sensorEvent.values[0] + " %");

        }
    }
    @Override
    public void onAccuracyChanged(Sensor sensor, int i){

    }
    @Override
    protected void onResume(){
        super.onResume();
        if (tempDisponible){
            sensorManager.registerListener(this, tempSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
        if (humeDisponible){
            sensorManager.registerListener(this, humeSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }
    @Override
    protected void onPause(){
        super.onPause();
        sensorManager.unregisterListener(this);
    }
}