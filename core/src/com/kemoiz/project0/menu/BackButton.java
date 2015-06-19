package com.kemoiz.project0.menu;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.kemoiz.project0.handlers.AssetHandler;

public class BackButton {

	private Texture texture;
	private Vector2 position, size;

	public BackButton(Vector2 position) {
		size = new Vector2(64, 64);
		this.position = position;

		texture = AssetHandler.button_back;

	}

	public Texture getTexture() {
		return texture;
	}

	public Vector2 getPosition() {
		return position;
	}

	public boolean isClicked() {

		return ClickedHandler.isClicked(position, size, true);
	}

	public void inverseState() {
		ClickedHandler.clickedLastFrame = !ClickedHandler.clickedLastFrame;

	}

}
