package com.kemoiz.project0.entities;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.RayCastCallback;
import com.badlogic.gdx.utils.FloatArray;

public class RayCaster implements RayCastCallback {

	public static Vector2 point = new Vector2(0, 0),
			normal = new Vector2(0, 0);
	private FloatArray fx = new FloatArray();
	private float fraction;
	public int cts = 0;

	public boolean rayed;

	public RayCaster() {

	}

	@Override
	public float reportRayFixture(Fixture fixture, Vector2 point,
			Vector2 normal, float fraction) {
		if (this.point == point) {

			fx.sort();
			EntityDataContainer e = (EntityDataContainer) fixture.getBody()
					.getUserData();
			if (e != null && fraction < fx.peek()) {
				rayed = true;
				// System.out.println(fraction);

			} else {

				fx.truncate(20);
			}
			fx.add(fraction);
		} else {

			fx.clear();
		}
		this.fraction = fraction;
		this.normal = normal;
		this.point = point;
		if ((EntityDataContainer) fixture.getBody().getUserData() != null) {
			EntityDataContainer e = (EntityDataContainer) fixture.getBody()
					.getUserData();

		}

		return -1;
	}

	public void switchSpottedValue() {

		cts = 0;
		rayed = false;
	}

}
