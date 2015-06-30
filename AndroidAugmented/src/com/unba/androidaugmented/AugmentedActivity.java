package com.unba.androidaugmented;

import android.app.Activity;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.Size;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.widget.TextView;

public class AugmentedActivity extends Activity {
	
	SurfaceView cameview;
	SurfaceHolder holderprev;
	Camera cam;
	boolean inprev;
	
	final static String TAG = "PAAR";
	SensorManager sensorManager;
	int orientationSensor;
	float headingAngle;
	float pitchAngle;
	
	float rollAngle;
	int accelerometerSensor;
	float xAxis;
	float yAxis;
	float zAxis;
	LocationManager locationManager;
	double latitude;
	double longitude;
	double altitude;
	
	TextView xAxisValue;
	TextView yAxisValue;
	TextView zAxisValue;
	TextView headingValue;
	TextView pitchValue;
	TextView rollValue;
	TextView latitudeValue;
	TextView longitudeValue;
	TextView altitudeValue;
	

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODOs Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		xAxisValue = (TextView)findViewById(R.id.xAxisValue);
		yAxisValue = (TextView)findViewById(R.id.yAxisValue);
		zAxisValue = (TextView)findViewById(R.id.zAxisValue);
		headingValue = (TextView)findViewById(R.id.headingValue);
		pitchValue = (TextView)findViewById(R.id.pitchValue);
		rollValue = (TextView)findViewById(R.id.rollValue);
		latitudeValue = (TextView)findViewById(R.id.latitudeValue);
		longitudeValue = (TextView)findViewById(R.id.longitudeValue);
		altitudeValue = (TextView)findViewById(R.id.altitudeValue);
		
		locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
		2000, 2, locationListener);
		sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
		orientationSensor = Sensor.TYPE_ORIENTATION;
		accelerometerSensor = Sensor.TYPE_ACCELEROMETER;
		sensorManager.registerListener(sensorEventListener, sensorManager
		.getDefaultSensor(orientationSensor), SensorManager.SENSOR_DELAY_NORMAL);
		sensorManager.registerListener(sensorEventListener, sensorManager
		.getDefaultSensor(accelerometerSensor), SensorManager.SENSOR_DELAY_NORMAL);
		
		inprev = false;
		cameview = (SurfaceView)findViewById(R.id.cameraPreview);
		holderprev = cameview.getHolder();
		holderprev.addCallback(surfaceCallback);
		holderprev.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
	}
	
	LocationListener locationListener = new LocationListener() {
		public void onProviderDisabled(String arg0) {
		// TODOs Auto-generated method stub
		}
		public void onProviderEnabled(String arg0) {
		// TODOs Auto-generated method stub
		}
		public void onStatusChanged(String arg0, int arg1, Bundle arg2)
		{
		// TODOs Auto-generated method stub
		}
		@Override
		public void onLocationChanged(Location location) {
			// TODOs Auto-generated method stub
			latitude = location.getLatitude();
			longitude = location.getLongitude();
			altitude = location.getAltitude();
			
			Log.d(TAG, "Latitude: " + String.valueOf(latitude));
			Log.d(TAG, "Longitude: " + String.valueOf(longitude));
			Log.d(TAG, "Altitude: " + String.valueOf(altitude));
			
			latitudeValue.setText(String.valueOf(latitude));
			longitudeValue.setText(String.valueOf(longitude));
			altitudeValue.setText(String.valueOf(altitude));
			
		}
		};
	
		final SensorEventListener sensorEventListener = new SensorEventListener() {
			@SuppressWarnings("deprecation")
			public void onSensorChanged(SensorEvent sensorEvent) {
			if (sensorEvent.sensor.getType() == Sensor.TYPE_ORIENTATION)
			{
			headingAngle = sensorEvent.values[0];
			pitchAngle = sensorEvent.values[1];
			rollAngle = sensorEvent.values[2];
			Log.d(TAG, "Heading: " + String.valueOf(headingAngle));
			Log.d(TAG, "Pitch: " + String.valueOf(pitchAngle));
			Log.d(TAG, "Roll: " + String.valueOf(rollAngle));
			
			headingValue.setText(String.valueOf(headingAngle));
			pitchValue.setText(String.valueOf(pitchAngle));
			rollValue.setText(String.valueOf(rollAngle));
			
			}
			else if (sensorEvent.sensor.getType() ==
			Sensor.TYPE_ACCELEROMETER)
			{
			xAxis = sensorEvent.values[0];
			yAxis = sensorEvent.values[1];
			zAxis = sensorEvent.values[2];
			Log.d(TAG, "X Axis: " + String.valueOf(xAxis));
			Log.d(TAG, "Y Axis: " + String.valueOf(yAxis));
			Log.d(TAG, "Z Axis: " + String.valueOf(zAxis));
			
			xAxisValue.setText(String.valueOf(xAxis));
			yAxisValue.setText(String.valueOf(yAxis));
			zAxisValue.setText(String.valueOf(zAxis));
			
			}
			}
			public void onAccuracyChanged (Sensor senor, int accuracy) {
				//Not used
				}
		};
	
	@Override
	protected void onPause() {
		// TODOs Auto-generated method stub
		if (inprev) {
			cam.stopPreview();
			}
		locationManager.removeUpdates(locationListener);
		sensorManager.unregisterListener(sensorEventListener);
		cam.release();
		cam=null;
		inprev=false;
		super.onPause();
	}


	@Override
	protected void onResume() {
		// TODOs Auto-generated method stub
		super.onResume();
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000,
				2, locationListener);
				sensorManager.registerListener(sensorEventListener, sensorManager
				.getDefaultSensor(orientationSensor), SensorManager.SENSOR_DELAY_NORMAL);
				sensorManager.registerListener(sensorEventListener, sensorManager
				.getDefaultSensor(accelerometerSensor), SensorManager.SENSOR_DELAY_NORMAL);
		cam=Camera.open();
	}


	Callback surfaceCallback = new Callback() {
		
		@Override
		public void surfaceDestroyed(SurfaceHolder holder) {
			// TODOs Auto-generated method stub
			
		}
		
		@Override
		public void surfaceCreated(SurfaceHolder holder) {
			// TODOs Auto-generated method stub
			try {
					cam.setPreviewDisplay(holderprev);
				}
				catch (Throwable t) {
				Log.e("ProAndroidAR2Activity", "Exception insetPreviewDisplay()", t);
				}
		}
		
		@Override
		public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
			// TODOs Auto-generated method stub
			Camera.Parameters parameters=cam.getParameters();
			Camera.Size size=getBestPreviewSize(width, height, parameters);
			if (size!=null) {
			parameters.setPreviewSize(size.width, size.height);
			cam.setParameters(parameters);
			cam.startPreview();
			inprev=true;
			}
			
		}

		private Size getBestPreviewSize(int width, int height,
				Parameters parameters) {
			// TODOs Auto-generated method stub
			Camera.Size result=null;
			for (Camera.Size size : parameters.getSupportedPreviewSizes()) {
				if (size.width<=width && size.height<=height) {
					if (result==null) {
						result=size;
						}
						else {
						int resultArea=result.width*result.height;
						int newArea=size.width*size.height;
						if (newArea>resultArea) {
						result=size;
						}
				}
				}
			}
			return (result);
		}
	};
	
	

}
