package com.kemoiz.project0.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class MapEntity {

	protected Vector2 position;
	protected Vector2 size;
	protected Sprite sprite;
	
	public void render(SpriteBatch batch) {
		
		sprite.draw(batch);
		
	}
	
	
	public void update(Vector2 playerPosition) {
		

	}
	public void update(double xpos, double ypos) {
		

	}	
	
	public boolean intersects(Circle circ) {
		
		if (Intersector.overlaps(circ, new Rectangle(position.x, position.y, size.x, size.y)))
		return true;
		
		return false;
		
	}
	
}
