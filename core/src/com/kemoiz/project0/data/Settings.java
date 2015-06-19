package com.kemoiz.project0.data;

public class Settings {

	public static int[] rays = { 150, 500, 800 };
	public static int dragValue = 7;

	public static int LOW_QUALITY = 0, MED_QUALITY = 1, HI_QUALITY = 2;

	public static int getRays(int quality) {
		switch (quality) {

		case 0:
			return rays[0];
		case 1:
			return rays[1];
		case 2:
			return rays[2];

		}

		return 200;

	}
}
