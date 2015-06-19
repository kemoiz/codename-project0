package com.kemoiz.project0.entities;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class ScriptObjectContainer {

	public Array<String> scripts;
	public Vector2 position;
	public Vector2 size;
	public boolean isDynamic;

	public ScriptObjectContainer(Array<String> scripts, Vector2 position,
			Vector2 size, boolean isStatic) {
		this.scripts = scripts;
		this.position = position;
		this.size = size;
		this.isDynamic = isStatic;

	}

}
