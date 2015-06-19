package com.kemoiz.project0.handlers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.math.Vector2;
import com.kemoiz.project0.Map;

public class TileAnimator {

	private int counter;
	private Texture tiles;
	private TextureRegion lava1, lava2;
	private Cell lavaCell1, lavaCell2;
	private int animationSpeed = 20;

	public TileAnimator(Map map) {

		lava1 = new TextureRegion(AssetHandler.tiles, 192, 0, 16, 16);

		lava2 = new TextureRegion(AssetHandler.tiles, 208, 0, 16, 16);
		lavaCell1 = new Cell();
		lavaCell2 = new Cell();
		lavaCell1.setTile(new StaticTiledMapTile(lava1));
		lavaCell2.setTile(new StaticTiledMapTile(lava2));

	}

	public Map tick(Map map, Vector2 pP) {

		counter += 1;

		if (counter < animationSpeed) {

			return map;
		}
		counter = 0;
		int width = (int) pP.x;
		int height = (int) pP.y;
		for (int x = width + 10; x >= width - 10; x--) {
			for (int y = height + 10; y >= height - 10; y--) {
				if (map.getLayer(0).getCell(x, y) != null
						&& map.getLayer(0).getCell(x, y).getTile()
								.getProperties().containsKey("lava")) {
					boolean d = false;
					if (map.getLayer(0).getCell(x, y).getTile().getProperties()
							.get("lava").equals("1")) {

						map.getLayer(0).setCell(x, y, lavaCell1);

						map.getLayer(0).getCell(x, y).getTile().getProperties()
								.put("lava", "2");
						d = true;
					}

					if (!d
							&& map.getLayer(0).getCell(x, y).getTile()
									.getProperties().get("lava").equals("2")) {

						map.getLayer(0).setCell(x, y, lavaCell2);
						map.getLayer(0).getCell(x, y).getTile().getProperties()
								.put("lava", "1");

					}

				}

			}

		}
		return map;
	}
}
