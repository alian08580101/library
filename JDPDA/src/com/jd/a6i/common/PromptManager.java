package com.jd.a6i.common;

import java.util.HashMap;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Vibrator;

public class PromptManager {
	private static PromptManager instance;
	private static SoundPool soundPool;
	private static HashMap<Integer, Integer> soundPoolMap;
	private static AudioManager audioManager;
	private Context context;

	private PromptManager(Context context){
		initSounds(context);
	}
	
	/**
	 * Requests the instance of the Sound Manager and creates it if it does not exist.
	 * @return Returns the single instance of the SoundVibatorManager
	 */
	public static synchronized PromptManager getInstance(Context context) {
		if (instance == null){
			instance = new PromptManager(context);
		}
		return instance;
	}
	
	/**
	 * Initialises the storage for the sounds
	 * @param theContext  The Application context
	 */
	public void initSounds(Context context) {
		this.context = context;
		soundPool = new SoundPool(4, AudioManager.STREAM_MUSIC, 0);
		soundPoolMap = new HashMap<Integer, Integer>();
		audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
	}

	/**
	 * Add a new Sound to the SoundPool
	 * 
	 * @param index
	 *            - The Sound Index for Retrieval
	 * @param soundID
	 *            - The Android ID for the Sound asset.
	 */
	public void addSound(int index, int soundID) {
		soundPoolMap.put(index, soundPool.load(context,soundID, 1));
	}

	/**
	 * Loads the various sound assets Currently hardcoded but could easily be
	 * changed to be flexible.
	 */
	public void loadSounds(int[] resourceIdArray) {
		for(int i=0;i<resourceIdArray.length;i++){
			soundPoolMap.put(i+1, soundPool.load(context, resourceIdArray[i], 1));
		}
		/*soundPoolMap.put(1, soundPool.load(context, R.raw.beep, 1));
		soundPoolMap.put(2, soundPool.load(context, R.raw.buzz, 1));*/
	}

	/**
	 * Plays a Sound
	 * 
	 * @param index
	 *            - The Index of the Sound to be played
	 * @param speed
	 *            - The Speed to play not, not currently used but included for
	 *            compatibility
	 */
	public void playSound(int index, float speed) {
		float streamVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
		//streamVolume = streamVolume / audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
		soundPool.play(soundPoolMap.get(index), streamVolume, streamVolume, 1, 0, speed);
		
		/*
		float streamMaxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
		soundPool.play(soundPoolMap.get(index), streamMaxVolume, streamMaxVolume, 1, 0, speed);*/
		
	}

	/**
	 * Stop a Sound
	 * 
	 * @param index
	 *            - index of the sound to be stopped
	 */
	public void stopSound(int index) {
		soundPool.stop(soundPoolMap.get(index));
	}

	public void clean() {
		soundPool.release();
		soundPool = null;
		soundPoolMap.clear();
		//audioManager.unloadSoundEffects();
		instance = null;

	}

	public void playVibrator(Context context, long timelong) {
		Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
		if(vibrator.hasVibrator()){
			vibrator.vibrate(timelong);
		}
	}
}
