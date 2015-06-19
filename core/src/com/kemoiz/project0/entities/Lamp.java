package com.kemoiz.project0.entities;

import box2dLight.PointLight;
import box2dLight.RayHandler;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.kemoiz.project0.handlers.AssetHandler;

public class Lamp extends Enemy {

	private float speed = 0.25f;
	private World world;
	private RayCaster rayCaster;

	private Vector2[] rays;

	public Lamp(Vector2 path, RayHandler ray) {
		rayCaster = new RayCaster();
		this.position = new Vector2(path.x, path.y);

		this.light = new PointLight(ray, 150, Color.RED, 100, position.x,
				position.y);

		this.sprite = new Sprite(AssetHandler.lamp);
		this.sprite.setScale(0.25f);
		sprite.setCenter(position.x, position.y);
	}

	@Override
	public void update(World world, Vector2 player) {
		this.world = world;
		// if (isSpotted(player)) {
		//
		// }

		light.setPosition(position);
		sprite.setCenter(position.x, position.y);
	}

	private void followThePlayer(Vector2 player) {
		if (position.x > player.x) {
			position.x -= speed;

		}
		if (position.x < player.x) {
			position.x += speed;

		}
		if (position.y > player.y) {
			position.y -= speed;

		}
		if (position.y < player.y) {
			position.y += speed;

		}

	}

	public boolean isSpotted(Vector2 player) {

		boolean spotted = false;
		rayCaster.switchSpottedValue();
		for (int i = 0; i < raysNumber; i++) {
			rays[i] = new Vector2(50, 0);
			rays[i].setAngle(i);
			rays[i].add(position);
			world.rayCast(rayCaster, position, rays[i]);

			rays[i].set(rayCaster.point);

		}

		if (rayCaster.rayed) {
			return true;
		}

		return false;

	}
}
