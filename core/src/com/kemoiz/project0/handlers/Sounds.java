package com.kemoiz.project0.handlers;

public class Sounds {

	public static void play(String sound) {
		switch (sound) {
		case "lose":
			AssetHandler.lose.play(0.2f);
			break;

		}

	}

}
