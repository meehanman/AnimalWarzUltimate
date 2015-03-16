package com.threeml.awu.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Used to save application information between openings i.e Settings
 * 
 * @author Dean
 * 
 */

public class PreferenceStore {

	// Pref name store
	private static String PrefName = "AnimalWarzSettings";
	SharedPreferences.Editor editor;
	private SharedPreferences store;

	// Constructor
	public PreferenceStore(Context context) {
		this.store = context.getSharedPreferences(PrefName, 0);
		this.editor = store.edit();
	}

	/**
	 * Save Methods
	 * 
	 * @param key
	 * @param bool
	 */
	public void Save(String key, boolean bool) {
		editor.putBoolean(key, bool);
		editor.commit();
	}

	public void Save(String key, int value) {
		editor.putInt(key, value);
		editor.commit();
	}

	public void Save(String key, float value) {
		editor.putFloat(key, value);
		editor.commit();
	}

	public void Save(String key, String value) {
		editor.putString(key, value);
		editor.commit();
	}

	/**
	 * Retrieve Methods
	 * 
	 * @param key
	 * @return value Returns value stored from key
	 */
	public boolean RetrieveBoolean(String key) {
		return store.getBoolean(key, false);
	}

	public int RetrieveInt(String key) {
		return store.getInt(key, -1);
	}

	public float RetrieveFloat(String key) {
		return store.getFloat(key, -1f);
	}

	public String RetrieveString(String key) {
		return store.getString(key, "Not Found");
	}

}