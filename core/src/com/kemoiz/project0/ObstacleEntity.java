package com.kemoiz.project0;

import com.badlogic.gdx.physics.box2d.BodyDef;

public class ObstacleEntity {

	public BodyDef bodyDef;
	public int type;

	public ObstacleEntity(BodyDef bodyDef, int type) {
		this.type = type;
		this.bodyDef = bodyDef;

	}

}
