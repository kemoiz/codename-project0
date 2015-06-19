package com.kemoiz.project0;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.kemoiz.project0.data.Settings;
import com.kemoiz.project0.handlers.AssetHandler;
import com.kemoiz.project0.handlers.KeyHandler;
import com.kemoiz.project0.handlers.SaveHandler;
import com.kemoiz.project0.menu.MainMenu;

public class MotherClass extends Game {

	/*
	 * project0, made by kemoiz 2014-15
	 */

	public static double SCR_WIDTH;
	public static double SCR_HEIGHT;

	public static float accX = 0, accY = 0;

	public float counter;
	public static int level = 0;
	public static int SCREEN_WIDTH;
	public static int gameState = -1; // -1 - menu, 0 - loading & setting up, 1
										// - dead
	// 2 - playing, 3 - finished
	// 4 - paused
	public static int lastState;
	public static int unlockedLevels;

	public GameScreen gameScreen;
	public MainMenu mainMenu;
	public static int maxLevel = 5;
	public static int quality = Settings.LOW_QUALITY;
	public KeyHandler keyHandler = new KeyHandler();

	@Override
	public void create() {
		Gdx.input.setInputProcessor(new KeyHandler());
		SCR_WIDTH = Gdx.graphics.getWidth();
		SCR_HEIGHT = Gdx.graphics.getHeight();

		SaveHandler.init();

		AssetHandler.init();

		unlockedLevels = SaveHandler.getLevelsUnlocked() - 1;
		Gdx.input.setCatchBackKey(true);

		SCREEN_WIDTH = Gdx.graphics.getWidth();

		mainMenu = new MainMenu(this);
		setScreen(mainMenu);

	}

	public void render() {
		if (counter > -1)
			counter += Gdx.graphics.getDeltaTime() * 60;
		if (counter > 180) {
			gameState = 2;
			counter = -1;
		}
		super.render();
	}

	@Override
	public void dispose() {
		AssetHandler.dispose();
		SaveHandler.flush();

	}

	public void backToMenu() {
		gameState = -1;
		setScreen(mainMenu);

	}

	public void switchScreen(int lvl) {
		if (lvl <= maxLevel) {
			level = lvl;
		}
		if (gameScreen != null & gameState == -1) {
			gameState = 0;
			counter = 0;
			gameScreen.reloadLevel(level);
			setScreen(gameScreen);
		}

		gameState = 0;
		counter = 0;
		if (gameScreen != null) {
			gameScreen.reloadLevel(level);
		} else {

			gameScreen = new GameScreen(this, level);
			setScreen(gameScreen);
		}

	}
}
