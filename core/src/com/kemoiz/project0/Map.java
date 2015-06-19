package com.kemoiz.project0;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.kemoiz.project0.entities.ScriptObjectContainer;

public class Map {

	private TiledMap tiledMap;
	private TiledMapTileLayer tileLayer1, tileLayer2;
	public Array<ObstacleEntity> mapObstacles = new Array<ObstacleEntity>();
	private World world;
	private Array<Vector2> a;
	private Array<ScriptObjectContainer> ax;
	public double bronzeTime, silverTime, goldTime;
	public boolean stopwatch;

	public Map(int number) {
		init(number);
		world = new World(new Vector2(0, 0), true);
	}

	private void init(int number) {
		long start = System.currentTimeMillis();
		tiledMap = new TmxMapLoader().load("levels/" + number + ".tmx");
		tileLayer1 = (TiledMapTileLayer) tiledMap.getLayers().get("tile_1"); // background
		tileLayer2 = (TiledMapTileLayer) tiledMap.getLayers().get("tile_2"); // obstacles
																				// etc
		mapObstacles = new Array<ObstacleEntity>(2048);

		int width = tileLayer2.getWidth();
		int height = tileLayer2.getHeight();
		for (int x = width; x >= 0; x--) {
			for (int y = height; y >= 0; y--) {
				if (tileLayer2.getCell(x, y) != null) {
					if (tileLayer2.getCell(x, y).getTile().getProperties()
							.containsKey("obstacle")) {

						switch ((String) tileLayer2.getCell(x, y).getTile()
								.getProperties().get("obstacle")) {
						case "regular":
							mapObstacles.add(new ObstacleEntity(obstacleBody(x,
									y), Obstacle.REG));
							break;
						case "upperleft":
							mapObstacles.add(new ObstacleEntity(obstacleBody(x,
									y), Obstacle.UL));
							break;
						case "upperright":
							mapObstacles.add(new ObstacleEntity(obstacleBody(x,
									y), Obstacle.UR));
							break;
						case "bottomleft":
							mapObstacles.add(new ObstacleEntity(obstacleBody(x,
									y), Obstacle.DL));
							break;
						case "bottomright":
							mapObstacles.add(new ObstacleEntity(obstacleBody(x,
									y), Obstacle.DR));
							break;
						default:
							mapObstacles.add(new ObstacleEntity(obstacleBody(x,
									y), Obstacle.REG));
							break;

						}

					}
				}

			}
		}

		if (tiledMap.getProperties().containsKey("bronze")) {
			stopwatch = true;
			bronzeTime = Double.parseDouble((String) tiledMap.getProperties()
					.get("bronze"));
			silverTime = Double.parseDouble((String) tiledMap.getProperties()
					.get("silver"));
			goldTime = Double.parseDouble((String) tiledMap.getProperties()
					.get("gold"));

		}
		// optimise();
		Gdx.app.log("init",
				"map loading finished in "
						+ (System.currentTimeMillis() - start) + "ms");

	}

	private void optimise() {
		// TODO: block merging for better performance
		int width = tileLayer2.getWidth();
		int height = tileLayer2.getHeight();
		int[][][] mapTable = new int[width][height][5];

		for (ObstacleEntity o : mapObstacles) {
			mapTable[(int) ((o.bodyDef.position.x - 8) / 16)][(int) ((o.bodyDef.position.y - 8) / 16)][0] = Obstacle.REG;
		}

		for (int x = width; x >= 1; x--) {
			for (int y = height; y >= 1; y--) {
				if (mapTable[x][y][0] == mapTable[x][y + 1][0]) {

					mapTable[x][y + 1][0] = -1;

				}

			}
		}
		mapObstacles.clear();
		for (int x = width; x >= 0; x--) {
			for (int y = height; y >= 0; y--) {

				switch (mapTable[x][y][0]) {
				case -1:
					break;
				case 0:
					mapObstacles.add(new ObstacleEntity(obstacleBody(x, y),
							Obstacle.DR));
					break;

				}

			}

		}

	}

	private BodyDef obstacleBody(int x, int y) {
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.StaticBody;

		bodyDef.position.set(x * 16 + 8, y * 16 + 8);

		return bodyDef;
	}

	public Vector2 getStart() {
		float x = (Float) tiledMap.getLayers().get("obj").getObjects()
				.get("start").getProperties().get("x");
		float y = (Float) tiledMap.getLayers().get("obj").getObjects()
				.get("start").getProperties().get("y");
		return new Vector2(x, y);

	}

	public Array<Vector2> getCoins() {
		a = new Array<Vector2>();
		int it = 0;
		do {

			if (tiledMap.getLayers().get("obj").getObjects().get("coin_" + it) != null) {

				float x = (float) tiledMap.getLayers().get("obj").getObjects()
						.get("coin_" + it).getProperties().get("x");
				float y = (float) tiledMap.getLayers().get("obj").getObjects()
						.get("coin_" + it).getProperties().get("y");
				a.add(new Vector2(x, y));

			} else {
				it = 33;
			}
			it++;
		} while (it <= 32);

		return a;

	}

	public Array<Vector2> getEnemies() {
		a = new Array<Vector2>();
		int it = 0;
		do {

			if (tiledMap.getLayers().get("obj").getObjects().get("enemy_" + it) != null) {

				float x = (float) tiledMap.getLayers().get("obj").getObjects()
						.get("enemy_" + it).getProperties().get("x");
				float y = (float) tiledMap.getLayers().get("obj").getObjects()
						.get("enemy_" + it).getProperties().get("y");
				a.add(new Vector2(x, y));

			} else {
				it = 33;
			}
			it++;
		} while (it <= 32);

		return a;

	}

	public Array<ScriptObjectContainer> getScripts() {
		ax = new Array<ScriptObjectContainer>();
		int it = 0;

		do {
			Vector2 position, size;
			Array<String> props = new Array<String>();
			if (tiledMap.getLayers().get("obj").getObjects().get("block_" + it) != null) {
				RectangleMapObject rect = (RectangleMapObject) tiledMap
						.getLayers().get("obj").getObjects().get("block_" + it);
				float x = (float) rect.getRectangle().x;
				float y = (float) rect.getRectangle().y;
				float width = rect.getRectangle().width;
				float height = rect.getRectangle().height;
				String movement = (String) tiledMap.getLayers().get("obj")
						.getObjects().get("block_" + it).getProperties()
						.get("movement");
				String speed = (String) tiledMap.getLayers().get("obj")
						.getObjects().get("block_" + it).getProperties()
						.get("speed");
				boolean isStatic = tiledMap.getLayers().get("obj").getObjects()
						.get("block_" + it).getProperties()
						.containsKey("dynamic");

				position = new Vector2(x, y);
				size = new Vector2(width, height);
				props.add(movement);
				props.add(speed);

				ax.add(new ScriptObjectContainer(props, position, size,
						isStatic));
			} else {
				it = 33;
			}
			it++;
		} while (it <= 32);

		return ax;

	}

	public TiledMapTileLayer getLayer(int layer) {
		if (layer == 0) {
			return tileLayer1;
		}
		return tileLayer2;

	}

	public TiledMap getMap() {
		return tiledMap;
	}

}
