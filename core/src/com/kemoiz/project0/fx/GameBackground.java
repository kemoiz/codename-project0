package com.kemoiz.project0.fx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

public class GameBackground {
	private ShaderProgram shaderProgram;
	private OrthographicCamera camera;
	private SpriteBatch batch;
	public static float time = 0;

	private Pixmap stars;
	private Texture stars_tex, tp;
	private boolean forward = true;
	private double timer;

	public GameBackground() {
		batch = new SpriteBatch();
		camera = new OrthographicCamera(800, 480);
		camera.position.set(400, 240, 0);
		camera.update();
		shaderProgram = new ShaderProgram(
				Gdx.files.internal("shaders/background.vsh"),
				Gdx.files.internal("shaders/background.fsh"));
		System.out.println(shaderProgram.isCompiled() ? "shader compiled"
				: shaderProgram.getLog());
		batch.setProjectionMatrix(camera.combined);
		batch.setShader(shaderProgram);
		stars = new Pixmap(800, 480, Format.RGBA4444);
		stars_tex = new Texture(stars);
	}

	public void render(double transparency) {

		if (time == 0 && !forward)
			forward = true;

		timer = (Math.sin(time / 500) + 1) * 1000;

		shaderProgram.begin();
		shaderProgram.setUniformf("time", (float) timer);
		shaderProgram.end();
		if (Gdx.input.isKeyPressed(Keys.R)) {
			shaderProgram = new ShaderProgram(
					Gdx.files.internal("shaders/background.vsh"),
					Gdx.files.internal("shaders/background.fsh"));
			System.out.println(shaderProgram.isCompiled() ? "shader compiled"
					: shaderProgram.getLog());
			batch.setShader(shaderProgram);
		}
		if (forward)
			if (Gdx.input.isTouched()) {
				time += 0.3f;
			} else {
				time += 1f;
			}
		else if (Gdx.input.isTouched()) {
			time -= 0.3f;
		} else {
			time -= 1f;
		}

		if (time == 1)
			refresh();
		else {
			batch.begin();
			batch.disableBlending();
			batch.draw(stars_tex, 0, 0, 800, 480);

			batch.end();

		}

	}

	private void refresh() {

		stars_tex.draw(stars, 0, 0);
		batch.begin();

		batch.draw(stars_tex, 0, 0, 800, 480);

		batch.end();

	}

	public void dispose() {
		stars.dispose();
		stars_tex.dispose();

	}
}
