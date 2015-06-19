package com.kemoiz.project0.menu;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.TimeUtils;

public class RainbowBars {
	private OrthographicCamera camera;
	private SpriteBatch batch;
	public static float frameCounter;
	private Pixmap filter;
	private Pixmap rainbow1;
	private Texture rainbow_tex, filter_tex;

	public RainbowBars() {
		batch = new SpriteBatch();
		camera = new OrthographicCamera(800, 480);
		camera.position.set(400, 240, 0);
		camera.update();
		batch.setProjectionMatrix(camera.combined);
		filter = new Pixmap(4, 4, Format.RGBA8888);
		filter.setColor(0, 0.21f, 0.42f, 0.1f);
		filter.drawRectangle(0, 0, 4, 4);
		filter_tex = new Texture(filter);

	}

	public void render(double mult, float transp) {
		frameCounter = frameCounter + 0.001f;

		camera.update();
		batch.setProjectionMatrix(camera.combined);
		camera.rotate(0.1f);
		camera.zoom = (float) (Math.sin(frameCounter) / 5) + 0.8f;
		rainbow1 = new Pixmap(1, 16, Format.RGB565);
		for (int len = 0; len < 16; len++) {

			rainbow1.drawPixel(
					0,
					len,
					(int) ((len * 855322 + ((int) TimeUtils.millis() / (2 * mult)))));
			rainbow1.drawPixel(0, len, Color.alpha(transp));

		}
		rainbow_tex = new Texture(rainbow1);
		batch.begin();
		for (int len = -4; len < 12; len++) {

			batch.draw(rainbow_tex, -200, len * 64, 1200, 64);
		}

		batch.draw(filter_tex, 0, 0, 800, 480);

		batch.end();

		rainbow1.dispose();
		rainbow_tex.dispose();

	}

	public void dispose() {

		filter.dispose();
		batch.dispose();

	}
}
