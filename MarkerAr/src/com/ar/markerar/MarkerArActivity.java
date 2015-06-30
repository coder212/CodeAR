package com.ar.markerar;

import android.os.Bundle;
import android.util.Log;
import edu.dhbw.andar.ARObject;
import edu.dhbw.andar.ARToolkit;
import edu.dhbw.andar.AndARActivity;
import edu.dhbw.andar.exceptions.AndARException;

/**
 * this source code is under general public license 
 * you allowing to run or modify or redistribute
 * for clear explanation please read the license 
 * this program created by coder212 <yujimaarif.ym@gmail.com>
 *  
 */


public class MarkerArActivity extends AndARActivity {

	private ARObject the_object;
	private ARToolkit artoolkit;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		CustomRenderer renderer = new CustomRenderer();
		setNonARRenderer(renderer);
		try {
			artoolkit = getArtoolkit();

			
			the_object = new CustomObject1
			("belajar", "marker_at11.patt", 90.0, new double[]{0,0});
			artoolkit.registerARObject(the_object);
			
			the_object = new CustomObject2
			("belajar", "marker_peace11.patt", 90.0, new double[]{0,0});
			artoolkit.registerARObject(the_object);
			
			the_object = new CustomObject3
			("belajar", "marker_reas11.patt", 90.0, new double[]{0,0});
			artoolkit.registerARObject(the_object);
			
			the_object = new CustomObject4
			("belajar", "marker_asboon11.patt", 90.0, new double[]{0,0});
			artoolkit.registerARObject(the_object);
			
			the_object = new CustomObject4("test","markers.patt",90.0,new double[]{0,0});
			artoolkit.registerARObject(the_object);
			
		} catch (AndARException ex){
			System.out.println("");
		}		
		startPreview();
	}
	
	@Override
	public void uncaughtException(Thread thread, Throwable x) {
		Log.e("MarkerAr", x.getMessage());
		finish();
	}
}