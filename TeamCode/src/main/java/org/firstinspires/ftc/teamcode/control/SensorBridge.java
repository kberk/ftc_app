//package org.firstinspires.ftc.teamcode.control;
//
//import android.content.Context;
//import android.hardware.Sensor;
//import android.hardware.SensorEvent;
//import android.hardware.SensorEventListener;
//import android.hardware.SensorManager;
//
//public class GyroManager implements SensorEventListener {
//
//    private SensorManager sensorManager;
//    private boolean gyroAvailable;
//
//    private Context context;
//    public GyroManager(Context context) {
//        this.context = context;
//    }
//
//    public void init() {
//        // get sensorManager and initialise sensor listeners
//        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
//        if (sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE) == null) {
//            gyroAvailable = false;
//        }
//        else {
//            gyroAvailable = true;
//            initListener();
//
//            // wait for one second until gyroscope and magnetometer/accelerometer
//            // data is initialised then scedule the complementary filter task
////            fuseTimer.scheduleAtFixedRate(new calculateFusedOrientationTask(),
////                    1000, TIME_CONSTANT);
//        }
//
//
//    }
//
//    // This function registers sensor listeners for the accelerometer, magnetometer and gyroscope.
//    private void initListener(){
//        sensorManager.registerListener(this,
//                sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE),
//                SensorManager.SENSOR_DELAY_FASTEST);
//    }
//
//    public void onGyroscopeSensorChanged(float[] gyroscope, long timestamp)
//    {
//        // This timestep's delta rotation to be multiplied by the current
//        // rotation after computing it from the gyro sample data.
//        if (previousTimestamp != 0)
//        {
//            final float dT = (timestamp - timestampOldCalibrated) * NS2S;
//
//            // Axis of the rotation sample, not normalized yet.
//            float axisX = gyroscope[0];
//            float axisY = gyroscope[1];
//            float axisZ = gyroscope[2];
//
//            // Calculate the angular speed of the sample
//            float omegaMagnitude = (float) Math.sqrt(axisX * axisX + axisY
//                    * axisY + axisZ * axisZ);
//
//            // Normalize the rotation vector if it's big enough to get the axis
//            if (omegaMagnitude > EPSILON)
//            {
//                axisX /= omegaMagnitude;
//                axisY /= omegaMagnitude;
//                axisZ /= omegaMagnitude;
//            }
//
//            // Integrate around this axis with the angular speed by the timestep
//            // in order to get a delta rotation from this sample over the
//            // timestep. We will convert this axis-angle representation of the
//            // delta rotation into a quaternion before turning it into the
//            // rotation matrix.
//            float thetaOverTwo = omegaMagnitude * dT / 2.0f;
//
//            float sinThetaOverTwo = (float) Math.sin(thetaOverTwo);
//            float cosThetaOverTwo = (float) Math.cos(thetaOverTwo);
//
//            deltaRotationVectorCalibrated[0] = sinThetaOverTwo * axisX;
//            deltaRotationVectorCalibrated[1] = sinThetaOverTwo * axisY;
//            deltaRotationVectorCalibrated[2] = sinThetaOverTwo * axisZ;
//            deltaRotationVectorCalibrated[3] = cosThetaOverTwo;
//
//            SensorManager.getRotationMatrixFromVector(
//                    deltaRotationMatrixCalibrated,
//                    deltaRotationVectorCalibrated);
//
//            currentRotationMatrixCalibrated = matrixMultiplication(
//                    currentRotationMatrixCalibrated,
//                    deltaRotationMatrixCalibrated);
//
//            SensorManager.getOrientation(currentRotationMatrixCalibrated,
//                    gyroscopeOrientationCalibrated);
//        }
//
//        timestampOldCalibrated = timestamp;
//    }
//
//    @Override
//    public void onAccuracyChanged(Sensor sensor, int i) {
//
//    }
//}
