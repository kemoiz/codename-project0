package com.kemoiz.project0.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.kemoiz.project0.MotherClass;
import com.kemoiz.project0.handlers.AssetHandler;

public class AccelerometerPointer {

	public Vector2 position = new Vector2(650, 50);
	public float iX, iY;
	private Texture pointer, axes;

	public AccelerometerPointer() {
		pointer = AssetHandler.acc_pointer;
		axes = AssetHandler.acc_meter;

	}

	public void render(SpriteBatch batch) {

		batch.draw(axes, position.x, position.y, 128, 128);
		batch.draw(pointer, position.x + (30 * 2), 50 + (30 * 2)
				+ MotherClass.accX * 9);
		batch.draw(pointer, position.x + (30 * 2) + MotherClass.accY * 9,
				50 + (30 * 2));
	}
}
