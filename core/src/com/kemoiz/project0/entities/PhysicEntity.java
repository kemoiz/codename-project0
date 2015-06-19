package com.kemoiz.project0.entities;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;

public class PhysicEntity extends MapEntity {

	protected Body body;
	protected BodyDef bodyDef;

	public float getX() {
		return body.getPosition().x;
	}

	public float getY() {
		return body.getPosition().y;
	}

}
