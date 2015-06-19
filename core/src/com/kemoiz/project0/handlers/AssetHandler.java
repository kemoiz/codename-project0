package com.kemoiz.project0.handlers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;

public class AssetHandler {

	public static Texture test0, player, tiles, acc_pointer, acc_meter;
	public static Texture lamp, coin, movable_wall, button_back;
	public static Texture stick, stick_bkg, levelbutton_template,
			level_passed_screen;
	public static Texture gyroscope_selected, gyroscope_disabled;
	public static Texture joystick_selected, joystick_disabled;

	public static Sound lose;
	public static Texture level_failed_screen;

	public static void init() {
		long start = System.currentTimeMillis();

		test0 = new Texture("gfx/splash.png");
		player = new Texture("gfx/ball.png");
		player.setFilter(TextureFilter.Nearest, TextureFilter.Linear);
		tiles = new Texture("gfx/tiles.png");
		acc_pointer = new Texture("gfx/acc_pointer.png");
		acc_meter = new Texture("gfx/accelerometer.png");
		lamp = new Texture("gfx/lamp.png");
		coin = new Texture("gfx/coin.png");
		movable_wall = new Texture("gfx/movable_wall.png");
		button_back = new Texture("gfx/button_back.png");
		stick = new Texture("gfx/stick.png");
		joystick_disabled = new Texture("gfx/joystick_disabled.png");
		joystick_selected = new Texture("gfx/joystick_selected.png");
		gyroscope_disabled = new Texture("gfx/gyroscope_disabled.png");
		gyroscope_selected = new Texture("gfx/gyroscope_selected.png");
		levelbutton_template = new Texture("gfx/levelbutton_template.png");
		level_passed_screen = new Texture("gfx/level_passed_screen.png");
		level_failed_screen = new Texture("gfx/level_failed_screen.png");
		lose = Gdx.audio.newSound(Gdx.files.internal("sfx/lose.mp3"));
		Gdx.app.log("init",
				"asset loading finished in "
						+ (System.currentTimeMillis() - start) + "ms");

	}

	public static void dispose() {
		test0.dispose();
		player.dispose();
		tiles.dispose();
		acc_pointer.dispose();
		acc_meter.dispose();
		lamp.dispose();
		coin.dispose();
		movable_wall.dispose();
		button_back.dispose();
		stick.dispose();
		joystick_selected.dispose();
		joystick_disabled.dispose();
		gyroscope_selected.dispose();
		gyroscope_disabled.dispose();
		levelbutton_template.dispose();

		lose.dispose();

	}

}
