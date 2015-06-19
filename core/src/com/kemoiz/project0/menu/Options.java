package com.kemoiz.project0.menu;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.kemoiz.project0.handlers.AssetHandler;

public class Options {

	private Texture gyroscope_selected, gyroscope_disabled;
	private Texture joystick_selected, joystick_disabled;
	public boolean isGyroscopeSelected = true;
	private Vector2 size = new Vector2(192, 192);

	public Options(boolean b) {

		isGyroscopeSelected = b;
		gyroscope_selected = AssetHandler.gyroscope_selected;
		gyroscope_selected
				.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		gyroscope_disabled = AssetHandler.gyroscope_disabled;
		joystick_selected = AssetHandler.joystick_selected;
		joystick_selected.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		joystick_disabled = AssetHandler.joystick_disabled;

	}

	public void render(SpriteBatch batch) {

		if (ClickedHandler.isOverhere(new Vector2(180, 128), size)) {
			isGyroscopeSelected = true;
		}
		if (ClickedHandler.isOverhere(new Vector2(180 + 228, 128), size)) {
			isGyroscopeSelected = false;
		}

		if (isGyroscopeSelected) {

			batch.draw(gyroscope_selected, 180, 128, 192, 192);

			batch.draw(joystick_disabled, 180 + 228, 128, 192, 192);
		} else {
			batch.draw(gyroscope_disabled, 180, 128, 192, 192);

			batch.draw(joystick_selected, 180 + 228, 128, 192, 192);

		}

	}

}
