package com.kemoiz.project0.entities;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.FloatArray;
import com.badlogic.gdx.utils.IntArray;

public class MapScript {

	private Array<Vector2> path;
	private FloatArray objectSpeed = new FloatArray();

	// 0 - forward, 1 - backward, 2 - sleep (then, objectSpeed is an amount of
	// frames without movement)
	// TODO: rotating objects

	private IntArray moveType;

	public MapScript(Array<Vector2> path) {

		this.path = path;
		this.objectSpeed = objectSpeed;
		objectSpeed.add(2);

	}

	public Vector2 getCurrentPos(int index) {

		return path.get(index);
	}

	public float getCurrentSpeed(int index) {

		return objectSpeed.get(index);
	}

	public int getCurrentMoveType(int index) {

		return moveType.get(index);

	}

	public int getArrayLength() {
		return path.size;
	}

}
