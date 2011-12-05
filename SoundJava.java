package com.muohio.soccerRobot;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import android.media.SoundPool.OnLoadCompleteListener;
import android.util.Log;
import android.view.View;

public class SoundJava {
	private static SoundPool soundPool;
	private static int soundID;
	static boolean loaded = false;
	//static Context view = null;

	public static void loadSound(int direction, Context context){
		soundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
		soundPool.setOnLoadCompleteListener(new OnLoadCompleteListener() {
			@Override
			public void onLoadComplete(SoundPool soundPool, int sampleId,
					int status) {
				loaded = true;
			}
		});
		
		soundID = soundPool.load(context, R.raw.audiotone, 1);
	}
		public static void playSound(int direction, Context context){
		AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
		float actualVolume = (float) audioManager
				.getStreamVolume(AudioManager.STREAM_MUSIC);
		float maxVolume = (float) audioManager
				.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
		float volume = actualVolume / maxVolume;
		// Is the sound loaded already?
		if (loaded) {
			if(direction==1){
				
			
			soundPool.play(soundID, volume, volume, 1, 0, 1f);
			Log.e("Test", "Played sound");
			}else if( direction==2){
				volume = (float) (volume*.75);
				soundPool.play(soundID, volume, volume, 1, 0, 1f);
			}else if(direction==3){
				volume = (float) (volume*.5);
				soundPool.play(soundID, volume, volume, 1, 0, 1f);
			}else if(direction==4){
				volume = (float) (volume*.25);
				soundPool.play(soundID, volume, volume, 1, 0, 1f);
			}else if(direction==5){
				volume = (float) (volume*0);
				soundPool.play(soundID, volume, volume, 1, 0, 1f);
			}
			
		}
	}
		
		public static void stopSound(){
			//for(int i=0;i<100;i++){
			soundPool.stop(soundID);
			
		}

	}


