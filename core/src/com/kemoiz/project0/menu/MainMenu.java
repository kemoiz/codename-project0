package com.kemoiz.project0.menu;

import org.json.JSONArray;
import org.json.JSONObject;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.utils.Array;
import com.kemoiz.project0.MotherClass;
import com.kemoiz.project0.data.Strings;
import com.kemoiz.project0.handlers.SaveHandler;

public class MainMenu implements Screen {

	public static double vX = 800 / MotherClass.SCR_WIDTH;
	public static double vY = 480 / MotherClass.SCR_HEIGHT;

	private OrthographicCamera camera;
	private MotherClass game;
	private SpriteBatch batch;
	private RainbowBars rainbowBars;
	private Stage stage;
	private BitmapFont white;
	private LabelStyle headingStyle;
	private MenuLabel playLabel;
	private MenuLabel optionsLabel;
	// private Array<LevelButton> levelButtons;
	private Array<Episode> episodes;
	// main menu - 0, forward - 1, level select - 2, backward - 3, options - 4
	private LevelPreview levelPreview;
	private int state = 0;
	private float swypeVelocity;
	private LevelButtonGenerator buttonGen;

	private Options options;

	private BackButton backButton;

	private int starter, tapDelay;
	private boolean pointerReady;

	public MainMenu(MotherClass mClass) {
		game = mClass;
		buttonGen = new LevelButtonGenerator();
		episodesInit();

		batch = new SpriteBatch();
		rainbowBars = new RainbowBars();
		camera = new OrthographicCamera(800, 480);

		camera.position.set(400, 240, 0);
		camera.update();
		batch.setProjectionMatrix(camera.combined);

		fontInit();
		levelPreview = new LevelPreview();
		backButton = new BackButton(new Vector2(700, 30));
		options = new Options(SaveHandler.isGyroscopeEnabled());

	}

	private void episodesInit() {
		episodes = new Array<>(16);

		JSONObject obj = new JSONObject(Gdx.files.internal("levels/data.json")
				.readString());

		JSONArray arr = obj.getJSONArray("episodes");
		for (int i = 0; i < arr.length(); i++) {

			episodes.add(new Episode(arr.getJSONObject(i).getString("title"),
					(int) arr.getJSONObject(i).getInt("length"), (int) arr
							.getJSONObject(i).getInt("position"), buttonGen));

		}

	}

	private void fontInit() {

		stage = new Stage();
		white = new BitmapFont(Gdx.files.internal("data/white.fnt"), false);
		white.setScale(0.4f);
		headingStyle = new LabelStyle(white, Color.WHITE);
		playLabel = new MenuLabel(Strings.start_button, headingStyle,
				camera.combined);
		optionsLabel = new MenuLabel(Strings.options_button, headingStyle,
				camera.combined);
		playLabel.setPosition(280, 300);
		optionsLabel.setPosition(120, 200);

	}

	@Override
	public void render(float delta) {
		if (tapDelay != 0)
			tapDelay--;

		if (game.keyHandler.isBackPressed() || backButton.isClicked()) {

			switch (state) {
			case 0:
				if (Gdx.input.isTouched() && tapDelay == 0)
					Gdx.app.exit();
			case 2:
				starter = -16;
				state = 3;
				backButton.inverseState();

			}

		}

		if (state == 1 && camera.position.x < 1800) {
			if (starter < 32) {
				starter++;
			}
			camera.translate(starter, 0);

		}

		if (state == 3) {

			if (starter > -32) {
				starter--;
			}
			camera.translate(starter, 0);

		}
		if (state == 3 && camera.position.x < 400) {

			camera.position.x = 400;
			state = 0;

		}
		if (camera.position.x >= 1800) {
			state = 2;
		}

		if (state == 5) {

			playLabel.visible = false;
			optionsLabel.visible = false;
			if (game.keyHandler.isBackPressed() || backButton.isClicked()) {
				tapDelay = 30;
				state = 0;
				SaveHandler.setGyroscope(options.isGyroscopeSelected);
				SaveHandler.flush();
			}

		} else {
			playLabel.visible = true;
			optionsLabel.visible = true;

		}

		batch.setProjectionMatrix(camera.combined);
		camera.update();
		levelPreview.update();
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		if (Gdx.input.isKeyPressed(Keys.RIGHT)) {
			levelPreview.changePreview(true);
		}
		if (camera.position.x <= 1800) {
			rainbowBars.render(8, 0.60f + (camera.position.x / 5000));
		} else {
			rainbowBars.render(8, 0.93f);
		}
		if (state == 5) {
			rainbowBars.render(8, 0.93f);
		}

		if (state == 0) {
			playLabel.drawRect((camera.position.x - 400));
			optionsLabel.drawRect((camera.position.x - 400));

			if (playLabel.isClicked()) {
				state = 1;

			}
			if (optionsLabel.isClicked()) {
				state = 5;

			}
		}
		if (state == 2) {
			if (Gdx.input.getDeltaX() > 2 && Gdx.input.isTouched()) {
				// levelPreview.changePreview(false);
				System.out.println("right");
				swypeVelocity -= Gdx.input.getDeltaX() * 0.2f;
			}
			if (Gdx.input.getDeltaX() < -2 && Gdx.input.isTouched()) {
				// levelPreview.changePreview(true);
				System.out.println("left");
				swypeVelocity -= Gdx.input.getDeltaX() * 0.2f;
			}

		}

		camera.translate(swypeVelocity, 0);
		if (state == 2 && camera.position.x < 1800) {
			camera.position.x = 1800;
			swypeVelocity = 0;
		}
		swypeVelocity /= 1.2;

		batch.begin();

		int actualLevelCounter = 0;
		for (Episode episode : episodes) {
			int levelSelected = episode.render(batch, camera.position.x, state,
					MotherClass.unlockedLevels, actualLevelCounter);

			if (levelSelected != -1 && pointerReady) {

				game.switchScreen(levelSelected + actualLevelCounter);
			}
			actualLevelCounter += episode.getLength() + 1;
		}
		playLabel.draw(batch, 1);
		optionsLabel.draw(batch, 1);
		camera.update();
		batch.draw(backButton.getTexture(), backButton.getPosition().x
				+ camera.position.x - 400, backButton.getPosition().y);
		if (state == 5) {
			options.render(batch);
		}
		batch.end();
		stage.act();
		stage.draw();
		ClickedHandler.update();
		if (Gdx.input.getDeltaX() != 0) {
			pointerReady = false;
		}
		if (Gdx.input.getDeltaX() == 0 && !Gdx.input.isTouched()) {
			pointerReady = true;
		}

	}

	@Override
	public void resize(int width, int height) {

	}

	@Override
	public void show() {

	}

	@Override
	public void hide() {

	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void dispose() {
		SaveHandler.flush();
		rainbowBars.dispose();
		playLabel.shapeRenderer.dispose();
		optionsLabel.shapeRenderer.dispose();
	}

}
