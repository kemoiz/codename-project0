package com.kemoiz.project0.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.kemoiz.project0.handlers.Fonts;

public class Episode {

	private Array<LevelButton> levelButtons;

	private Fonts fonts;
	private BitmapFont bitmapFont;
	private String title;
	private int position, length;

	public Episode(String title, int length, int position,
			LevelButtonGenerator buttonGen) {
		this.title = title;
		fonts = new Fonts();
		bitmapFont = fonts.white;
		this.position = position;
		this.length = length;
		levelButtons = new Array<LevelButton>(16);
		for (int i = 0; i <= length; i++) {
			levelButtons.add(new LevelButton(i, 96 * i,
					(-position % 3 * 128) % 380, position, buttonGen
							.getTexture(i)));

		}

	}

	private int wrapButton(int y) {
		if (y < -300) {

			y += 500;
			return wrap(y);
		}
		return y;
	}

	public int render(SpriteBatch batch, float cameraX, int state,
			int unlockedLevels, int actualLevelCounter) {
		int counter = -1;

		bitmapFont.setColor(Color.WHITE);
		int x = 1500 + (int) (position / 3) * 600;

		int y = (-position % 3 * 128) % 280 + 420;
		// y = wrap(y);
		bitmapFont.draw(batch, title, x, y);

		for (LevelButton lb : levelButtons) {

			if (counter + actualLevelCounter <= unlockedLevels) {
				lb.getSprite().draw(batch);
				if (lb.isClicked(cameraX) && state == 2
						&& Math.abs(Gdx.input.getDeltaX()) == 0
						&& Gdx.input.justTouched()) {

					return counter + 1;

				}
			} else {
				lb.getSprite().draw(batch, 0.35f);
			}
			counter++;

		}
		return -1;

	}

	private int wrap(int y) {
		if (y < 50) {

			y += 375;
			return wrap(y);
		}
		return y;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}
}
