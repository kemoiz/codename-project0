package com.kemoiz.project0.handlers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Joystick {

	private Vector2 position;
	private Texture stick, bkg;
	private Vector2 position_bot;
	private boolean pressed;

	public Joystick() {

		stick = AssetHandler.stick;
		position_bot = new Vector2(30, 30);

		position = new Vector2(0, 0);

	}

	public Vector2 getPosition() {
		if (Gdx.input.isTouched()) {
			position.set(Gdx.input.getX() - 238 + 150,
					-Gdx.input.getY() + 240 + 150);
			pressed = true;
		} else {
			position.set(0, 0);
			pressed = false;
		}
		position.limit(60);

		return position;
	}

	public void render(SpriteBatch batch) {
		batch.draw(stick, position_bot.x, position_bot.y, 128, 128);
		if (pressed) {
			batch.draw(stick, position_bot.x + position.x + 30, position_bot.y
					+ 30 + position.y, 63, 63);
		} else {

			batch.draw(stick, position_bot.x + position.x + 50, position_bot.y
					+ 50 + position.y, 32, 32);

		}

	}

}
