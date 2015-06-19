package com.kemoiz.project0.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.kemoiz.project0.handlers.AssetHandler;

public class Coin extends MapEntity {

	public Coin(Vector2 position) {
		
		this.position = position;
		this.size = new Vector2(16, 16);
		this.sprite = new Sprite(AssetHandler.coin);
		sprite.setCenter(position.x, position.y);
		
	}
	
	@Override 
	public void update(Vector2 playerPosition) {
		
	}
	
	
	
	
	
	
}
