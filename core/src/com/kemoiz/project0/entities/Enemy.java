package com.kemoiz.project0.entities;

import box2dLight.PointLight;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

public class Enemy {

	protected Vector2 position;
	protected Vector2 velocity;
	protected Vector2[] path; // TODO
	protected int raysNumber = 360;
	protected Rectangle shape;
    protected Sprite sprite;
	
	protected PointLight light;

	public Enemy() {

	}

	public void update(World world, Vector2 vector2) {

	}

	public void update(Vector2 player) {
		// TODO Auto-generated method stub

	}
	
	public void render(SpriteBatch batch) {
		
		sprite.draw(batch);
		
	}

}
