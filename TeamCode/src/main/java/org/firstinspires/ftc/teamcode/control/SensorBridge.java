package org.firstinspires.ftc.teamcode.control;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class SensorBridge implements SensorEventListener {

    // Credits to batteriesinblac (https://www.chiefdelphi.com/forums/showthread.php?p=1616537)

    private SensorManager mSensorManager;
    private boolean bInitialized;

    private Context context;
    public SensorBridge(Context context) {

    }

    public void init() {
        // get sensorManager and initialise sensor listeners
        mSensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        if (mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) == null ||
                mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE) == null ||
                mSensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION) == null ||
                mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD) == null) {
            bInitialized = false;
        }
        else {
            bInitialized = true;

            initListeners();

            // wait for one second until gyroscope and magnetometer/accelerometer
            // data is initialised then scedule the complementary filter task
//            fuseTimer.scheduleAtFixedRate(new calculateFusedOrientationTask(),
//                    1000, TIME_CONSTANT);
        }


    }

    // This function registers sensor listeners for the accelerometer, magnetometer and gyroscope.
    private void initListeners(){
        mSensorManager.registerListener(this,
                mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_FASTEST);

        mSensorManager.registerListener(this,
                mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE),
                SensorManager.SENSOR_DELAY_FASTEST);

        mSensorManager.registerListener(this,
                mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD),
                SensorManager.SENSOR_DELAY_FASTEST);

        mSensorManager.registerListener(this,
                mSensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION),
                SensorManager.SENSOR_DELAY_FASTEST);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
