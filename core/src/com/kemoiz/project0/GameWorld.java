package com.kemoiz.project0;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.kemoiz.project0.entities.Coin;
import com.kemoiz.project0.entities.Lamp;
import com.kemoiz.project0.entities.MovingWall;
import com.kemoiz.project0.entities.ScriptInterpreter;
import com.kemoiz.project0.entities.ScriptObjectContainer;
import com.kemoiz.project0.handlers.SaveHandler;
import com.kemoiz.project0.handlers.Sounds;
import com.kemoiz.project0.handlers.TileAnimator;

public class GameWorld {

	private World world;
	public Player player;
	private Box2DDebugRenderer renderer;
	private OrthographicCamera camera;
	private Array<Body> obstacles = new Array<Body>();
	public Map map;
	private float time;
	private TileAnimator animator;
	private MapProperties checkedTile;
	public int blocksAmount, deliveredBlocksAmount;

	public Array<Lamp> lamps = new Array<Lamp>();
	public Array<Coin> coins = new Array<Coin>();
	public Array<MovingWall> movingWalls = new Array<MovingWall>();
	public float slowmoAmount = 0;

	public GameWorld(Vector2 playerStart) {
		world = new World(new Vector2(0, 0), false);
		player = new Player(world, playerStart,
				SaveHandler.isGyroscopeEnabled());
		renderer = new Box2DDebugRenderer();
		camera = new OrthographicCamera(800, 480);
		World.setVelocityThreshold(25f);
		animator = new TileAnimator(map);

	}

	public void loadLamps(Array<Vector2> a) {
		for (Vector2 v : a) {
			lamps.add(new Lamp(v, GameScreen.rayHandler));
		}
	}

	public float getTime() {
		return time;
	}

	public Map tick(float delta) {

		int temp_delivered = 0;

		for (MovingWall wall : movingWalls) {

			wall.update();
			if (isBlockDelivered(wall.getX(), wall.getY()))
				wall.delivered = true;
			if (wall.delivered)
				temp_delivered++;

		}
		deliveredBlocksAmount = temp_delivered;

		if (Gdx.input.isTouched()) {
			if (slowmoAmount < 0.5f)
				slowmoAmount += 0.05f;

			time += delta * (1.0f - slowmoAmount);

			delta = delta * (1.0f - slowmoAmount);
		} else {
			if (slowmoAmount > 0)
				slowmoAmount -= 0.05f;
			time += delta * (1.0f - slowmoAmount);

			delta = delta * (1.0f - slowmoAmount);
		}
		double roundedTime = (Math.round(time * 100.0)) / 100.0;
		time = (float) roundedTime;
		if (map.stopwatch && map.bronzeTime - roundedTime < 0) {

			death();
		}
		player.update();

		world.step(delta, 1, 1);

		checkPlayersCell();

		optimise();
		return animator.tick(map, player.getTilePosition());
	}

	private boolean isBlockDelivered(double dx, double dy) {
		int xx = (int) (dx / 16);
		int yy = (int) (dy / 16);

		if (map.getLayer(0).getCell(xx, yy) != null) {
			System.out.println(xx + " " + yy);
			checkedTile = map.getLayer(0).getCell(xx, yy).getTile()
					.getProperties();
			if (checkedTile.containsKey("finish")) {

				return true;
			}

		}

		return false;
	}

	private void checkPlayersCell() {
		int cx = (int) (player.getX() / 16);
		int cy = (int) (player.getY() / 16);

		if (map.getLayer(0).getCell(cx, cy) != null) {
			checkedTile = map.getLayer(0).getCell(cx, cy).getTile()
					.getProperties();
			if (checkedTile.containsKey("speedup")) {
				player.setPowerUp(true);
			} else {
				player.setPowerUp(false);
			}
			if (checkedTile.containsKey("water")) {
				death(); // death
			}
			if (checkedTile.containsKey("lava")) {
				death(); // death
			}
			if (checkedTile.containsKey("finish")
					&& deliveredBlocksAmount == blocksAmount) {
				nextLevel();

			}

		} else {
			death();
		}

	}

	private void nextLevel() {
		SaveHandler.setHighScore(time, MotherClass.level);
		if (MotherClass.unlockedLevels == MotherClass.level - 1) {
			MotherClass.unlockedLevels++;
			SaveHandler.setLevelsUnlocked(MotherClass.unlockedLevels);

		}

		MotherClass.gameState = 3; // nextlevel
	}

	private void death() {
		Gdx.input.vibrate(400);
		Sounds.play("lose");
		MotherClass.gameState = 1;

	}

	private void optimise() {
		int xx = 0;
		for (Body body : obstacles) {
			float x = player.getX() - body.getPosition().x;
			float y = player.getY() - body.getPosition().y;
			if (Vector2.len(x, y) > 200) {
				body.setActive(false);
			} else {
				body.setActive(true);
				xx++;
			}
		}
		// System.out.println(xx);
	}

	public void setObstacles(Array<ObstacleEntity> mapObstacles) {
		PolygonShape shape = new PolygonShape();

		for (ObstacleEntity oE : mapObstacles) {
			shape.set(Obstacle.getShape(oE.type));

			obstacles.add(world.createBody(oE.bodyDef));
			obstacles.peek().createFixture(shape, 1f);

		}
	}

	private void debug(Map map) {
		int tilex = (int) ((player.getX() - 8) / 16);
		int tiley = (int) ((player.getY() - 8) / 16);
		System.out.println("x:" + player.getX() + "y: " + player.getY());
		System.out.println("tile x:" + tilex + " tile y: " + tiley);
		System.out.println("tile " + map.getLayer(0).getCell(tilex, tiley));
	}

	public void debugRender() {
		camera.combined.set(GameScreen.camera.combined);
		renderer.render(world, camera.combined);

	}

	public World getWorld() {
		// TODO Auto-generated method stub
		return world;
	}

	public void setInitialCoordinates(float aY, float aX) {
		player.setInitialCoordinates(aY, aX);

	}

	public void setMap(Map map2) {
		map = map2;

	}

	public int listen() {
		if (Gdx.input.justTouched()) {
			if (Gdx.input.getX() < MotherClass.SCREEN_WIDTH / 2) {
				return 0; // restart the level
			}
			if (Gdx.input.getX() > MotherClass.SCREEN_WIDTH / 2) {
				return 1; // change to the next level
			}
		}
		return -1;
	}

	public double getMovementX() {
		return player.getaX();
	}

	public double getMovementY() {
		return player.getaY();
	}

	public void dispose() {
		renderer.dispose();
		world.dispose();

	}

	public float getPlayerTotalVelocity() {

		return Math.max(Math.abs(player.getVelocity().x),
				Math.abs(player.getVelocity().y));
	}

	public void loadCoins(Array<Vector2> c) {
		for (Vector2 v : c) {
			coins.add(new Coin(v));
		}
	}

	public void loadScripts(Array<ScriptObjectContainer> a) {
		ScriptInterpreter interpreter = new ScriptInterpreter();

		for (ScriptObjectContainer cc : a) {

			movingWalls.add(new MovingWall(cc.position, cc.size, world,
					GameScreen.rayHandler));

			blocksAmount++;
		}
		for (MovingWall wall : movingWalls) {

			wall.update();

		}

	}

}
