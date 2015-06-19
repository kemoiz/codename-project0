package com.kemoiz.project0;

import box2dLight.PointLight;
import box2dLight.RayHandler;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.kemoiz.project0.data.Strings;
import com.kemoiz.project0.entities.AccelerometerPointer;
import com.kemoiz.project0.entities.Coin;
import com.kemoiz.project0.entities.Lamp;
import com.kemoiz.project0.entities.MovingWall;
import com.kemoiz.project0.fx.Bloom;
import com.kemoiz.project0.fx.GameBackground;
import com.kemoiz.project0.handlers.AssetHandler;
import com.kemoiz.project0.handlers.Fonts;
import com.kemoiz.project0.handlers.Joystick;
import com.kemoiz.project0.handlers.SaveHandler;
import com.kemoiz.project0.menu.BackButton;

public class GameScreen implements Screen {

	private GameBackground gameBackground;

	private SpriteBatch spriteBatch, guiBatch;
	private OrthogonalTiledMapRenderer mapRenderer;
	private MotherClass game;
	private Map map;
	public static OrthographicCamera camera, guiCamera;
	private GameWorld gameWorld;
	public static RayHandler rayHandler;
	private PointLight playerLight;
	private Fonts fonts;
	private int level;
	private AccelerometerPointer accPointer;
	private ShaderProgram shaderProgram;
	private float neonShadingSin = MathUtils.random(100f);
	public Joystick joystick;

	private BackButton backButton;
	private int time;

	private Bloom bloom;

	private float endGameCounter;

	public GameScreen(MotherClass game, int level) {
		this.game = game;
		this.level = level;

	}

	@Override
	public void render(float delta) {
		bloom.setBloomIntensity(1.3f);
		time++;

		Profiler.start("glClear");
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Profiler.stop();
		if (delta > 0.025f) {
			delta = 0.025f;
		}
		Profiler.start("controls");
		gameWorld.player.setControls(joystick.getPosition());
		if (MotherClass.gameState != 0 && game.keyHandler.isBackPressed()
				|| MotherClass.gameState != 0 && backButton.isClicked()) {
			game.backToMenu();

		}
		Profiler.stop();

		Profiler.start("shader program");

		shaderProgram.begin();
		shaderProgram.setUniformf("u_neon",
				MathUtils.sin(gameWorld.getTime() + neonShadingSin) * 0.4f,
				MathUtils.sin(gameWorld.getTime() + neonShadingSin + 1) * 0.0f,
				MathUtils.sin(gameWorld.getTime() + neonShadingSin + 2) * 0.4f,
				-0.15f);

		shaderProgram.end();

		Profiler.stop();

		Profiler.start("state dependant render");
		stateDependantRender(delta);

		Profiler.stop();
		spriteBatch.setProjectionMatrix(camera.combined);
		camera.position
				.set(gameWorld.player.getX(), gameWorld.player.getY(), 0);
		camera.update();
		Profiler.start("bloom capture");
		bloom.capture();
		Profiler.stop();
		Profiler.start("background render");
		gameBackground.render(0);
		Profiler.stop();
		Profiler.start("map render");
		renderMap();

		Profiler.stop();
		playerLight.setPosition(gameWorld.player.getX(),
				gameWorld.player.getY());
		Profiler.start("spritebatch");
		spriteBatch.begin();
		for (MovingWall w : gameWorld.movingWalls) {

			w.render(spriteBatch);
		}
		spriteBatch.end();
		Profiler.stop();
		Profiler.start("bloom render");
		bloom.render();
		Profiler.stop();
		Profiler.start("lights render");

		rayHandler.setCombinedMatrix(camera.combined);
		rayHandler.updateAndRender();
		Profiler.stop();
		// no shadows
		Profiler.start("second spritebatch");
		spriteBatch.begin();
		gameWorld.player.render(spriteBatch);
		for (Lamp e : gameWorld.lamps) {
			e.render(spriteBatch);
		}
		for (Coin c : gameWorld.coins) {
			c.render(spriteBatch);
		}

		spriteBatch.end();
		Profiler.stop();
		// gameWorld.debugRender();

		Profiler.start("guibatch");
		guiBatch.begin();
		accPointer.render(guiBatch);
		guiBatch.draw(backButton.getTexture(), backButton.getPosition().x,
				backButton.getPosition().y);
		if (MotherClass.gameState == 3) {
			guiBatch.setColor(1, 1, 1, endGameCounter);
			guiBatch.draw(AssetHandler.level_passed_screen, 0, 0, 800, 480);
		}
		if (MotherClass.gameState == 1) {
			guiBatch.setColor(1, 1, 1, endGameCounter);
			guiBatch.draw(AssetHandler.level_failed_screen, 0, 0, 800, 480);
		}
		if (!gameWorld.player.isUsingGyroscope())
			joystick.render(guiBatch);
		joystick.getPosition();
		guiBatch.end();
		Profiler.stop();
		Profiler.start("render labels");
		renderLabels();
		Profiler.stop();
	}

	private void stateDependantRender(float delta) {

		switch (MotherClass.gameState) {
		case 0:

			gameWorld.setInitialCoordinates(Gdx.input.getAccelerometerY(),
					Gdx.input.getAccelerometerX());
			camera.zoom = 1 + ((-game.counter + 180) * 0.1f);
			if (map.stopwatch) {
				fonts.setBottomLabelPosition(-300);
				fonts.setText(
						"minimum bronze medal:"
								+ String.valueOf(map.bronzeTime) + "|silver:"
								+ String.valueOf(map.silverTime) + "|gold:"
								+ String.valueOf(map.goldTime), 2);
			} else {

				fonts.setText("don't rush, no time limit :)", 2);
			}
			fonts.setCounter(String.valueOf(3 - ((int) game.counter / 60)),
					16 / ((game.counter % 60) + 0.0001f));
			break;
		case 1:
			if (endGameCounter < 1)
				endGameCounter += 0.05f;
			// fonts.setText(Strings.level_fail, 3);

			setRedAmbientLight();
			cameraFadeOut();
			if (gameWorld.listen() != -1) {
				game.switchScreen(MotherClass.level);
			}
			break;
		case 3:
			if (endGameCounter < 1)
				endGameCounter += 0.05f;
			fonts.setText(
					Strings.hiscore
							+ SaveHandler.getHighScore(MotherClass.level), 2);
			// fonts.setText(Strings.level_end, 3);
			setGreenAmbientLight();
			cameraFadeOut();
			switch (gameWorld.listen()) {
			case 0:
				game.switchScreen(MotherClass.level);
				break;
			case 1:
				game.switchScreen(MotherClass.level + 1);
			}

			break;
		case 2:
			fonts.setBottomLabelPosition(-200);
			fonts.setCounter("", 1);

			if ((gameWorld.getPlayerTotalVelocity() / 480) + 1 < camera.zoom - 0.05f) {

				camera.zoom -= 0.04f;
			} else {
				camera.zoom = 1 + (gameWorld.getPlayerTotalVelocity() / 480);
			}
			this.map = gameWorld.tick(delta);
			fonts.setText("fps " + Gdx.graphics.getFramesPerSecond()
					+ " state " + MotherClass.gameState + "blocks delivered:"
					+ gameWorld.deliveredBlocksAmount + "/"
					+ gameWorld.blocksAmount, 2);
			break;

		}

	}

	private void cameraFadeOut() {
		camera.zoom += 0.0003f;
		camera.rotate(0.01f);

	}

	private void renderLabels() {

		if (map.bronzeTime - gameWorld.getTime() < 10
				&& map.bronzeTime - gameWorld.getTime() > 0) {
			fonts.flash();
		} else {
			fonts.normalize();
		}

		double roundedTimeLeft = (Math.round((map.bronzeTime - gameWorld
				.getTime()) * 100.0)) / 100.0;

		if (map.stopwatch) {
			fonts.setText(String.valueOf(gameWorld.getTime()), 1);
			fonts.setText("time left:" + String.valueOf(roundedTimeLeft), 5);
		} else {
			fonts.setText(String.valueOf(gameWorld.getTime()), 1);
			fonts.setText("", 5);

		}
		fonts.render();

	}

	private void renderMap() {
		mapRenderer.setView(camera);
		mapRenderer.render();

	}

	@Override
	public void resize(int width, int height) {

	}

	@Override
	public void show() {
		gameBackground = new GameBackground();
		bloom = new Bloom();

		Gdx.gl.glTexParameteri(GL20.GL_TEXTURE_2D, GL20.GL_TEXTURE_MIN_FILTER,
				GL20.GL_TEXTURE_MIN_FILTER);
		ShaderProgram.pedantic = false;

		shaderProgram = new ShaderProgram(
				Gdx.files.internal("shaders/program.vsh"),
				Gdx.files.internal("shaders/program.fsh"));
		System.out.println(shaderProgram.isCompiled() ? "shader compiled"
				: shaderProgram.getLog());

		spriteBatch = new SpriteBatch();
		spriteBatch.setShader(shaderProgram);

		guiBatch = new SpriteBatch();
		backButton = new BackButton(new Vector2(700, 370));
		camera = new OrthographicCamera(200, 120);
		guiCamera = new OrthographicCamera(800, 480);

		guiCamera.translate(400, 240);

		guiCamera.update();
		guiBatch.setProjectionMatrix(guiCamera.combined);
		// camera.setToOrtho(false);
		map = new Map(level);

		mapRenderer = new OrthogonalTiledMapRenderer(map.getMap(), spriteBatch);

		gameWorld = new GameWorld(map.getStart());
		gameWorld.setMap(map);
		gameWorld.setObstacles(map.mapObstacles);
		initLights();
		gameWorld.loadLamps(map.getEnemies());
		gameWorld.loadCoins(map.getCoins());

		gameWorld.loadScripts(map.getScripts());
		fonts = new Fonts();
		accPointer = new AccelerometerPointer();
		joystick = new Joystick();
	}

	private void initLights() {
		rayHandler = new RayHandler(gameWorld.getWorld());
		// rayHandler.setAmbientLight(0.1f, 0.2f, 0.3f, 0.10f);

		playerLight = new PointLight(rayHandler, 256, Color.RED, 100, 400, 400);
		playerLight.setDistance(100);
		playerLight.setSoft(false);

		playerLight.setColor(0.4f, 0.4f, 1f, 0.97f);

	}

	public void reloadLevel(int level) {

		this.level = level;
		endGameCounter = 0;
		camera = new OrthographicCamera(200, 120);
		guiCamera = new OrthographicCamera(800, 480);

		guiCamera.translate(400, 240);

		guiCamera.update();
		guiBatch.setProjectionMatrix(guiCamera.combined);
		gameWorld.dispose();
		map.getMap().dispose();
		rayHandler.dispose();

		map = new Map(level);
		mapRenderer.setMap(map.getMap());
		gameWorld = new GameWorld(map.getStart());
		gameWorld.setMap(map);
		gameWorld.setObstacles(map.mapObstacles);
		initLights();
		gameWorld.loadLamps(map.getEnemies());
		gameWorld.loadCoins(map.getCoins());
		gameWorld.loadScripts(map.getScripts());
		fonts.setText("", 3);
	}

	@Override
	public void hide() {

	}

	@Override
	public void pause() {
		MotherClass.lastState = MotherClass.gameState;
		MotherClass.gameState = 4;

	}

	@Override
	public void resume() {
		if (MotherClass.lastState != 0) {
			MotherClass.gameState = MotherClass.lastState;
		} else {
			MotherClass.gameState = 2;
		}

	}

	@Override
	public void dispose() {
		gameWorld.dispose();
		map.getMap().dispose();
		fonts.dispose();
		spriteBatch.dispose();
		gameBackground.dispose();
		shaderProgram.dispose();
	}

	private void setGreenAmbientLight() {
		rayHandler.setAmbientLight(0, 0.9f, 0, 0.11f);
		playerLight.setDistance(50f);

	}

	private void setRedAmbientLight() {
		rayHandler.setAmbientLight(0.9f, 0, 0, 0.11f);
		playerLight.setDistance(50f);

	}
}
