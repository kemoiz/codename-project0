package com.kemoiz.project0.handlers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class SaveHandler {

	public static Preferences prefs;

	public static void init() {
		prefs = Gdx.app.getPreferences("project0_prefs");

	}

	public static boolean isGyroscopeEnabled() {

		return prefs.getBoolean("gyroscope", true);
	}

	public static void setGyroscope(boolean g) {
		prefs.putBoolean("gyroscope", g);

	}

	public static int getLevelsUnlocked() {

		return prefs.getInteger("unlocked", 1);
	}

	public static void setLevelsUnlocked(int u) {

		prefs.putInteger("unlocked", u);
	}

	public static boolean setHighScore(float score, int level) {
		if (prefs.getFloat("score_" + level, 9999) < score) {
			return false;
		} else {
			prefs.putFloat("score_" + level, score);
			prefs.flush();
			return true;
		}

	}

	public static float getHighScore(int level) {
		return prefs.getFloat("score_" + level, 0);
	}

	public static void flush() {
		prefs.flush();
	}

	public static void delete() {
		prefs.clear();

	}

}
