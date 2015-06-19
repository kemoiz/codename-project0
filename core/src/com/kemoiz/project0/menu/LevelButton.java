package com.kemoiz.project0.menu;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

public class LevelButton {

	private Texture texture;
	private int number;
	private Sprite sprite;
	private double counter;
	private int x, y;

	private int pos;

	public LevelButton(int n, int x, int y, int position, Texture texture) {
		this.number = n;
		this.x = x;
		this.y = y;
		this.pos = position;

		sprite = new Sprite(texture);
		// sprite.setTexture(texture);
		sprite.setBounds(1500 + x + (int) (position / 3) * 600, 330 + y, 64, 64);

	}

	public Sprite getSprite() {
		return sprite;
	}

	public boolean isClicked(double cameraX) {

		if (ClickedHandler.isOverhere(new Vector2((float) (100 + x - cameraX
				+ 1800 + (int) (pos / 3) * 600), sprite.getY() + 20),
				new Vector2(sprite.getWidth(), sprite.getHeight()))) {

			sprite.setColor((float) Math.sin(RainbowBars.frameCounter * 5),
					(float) -Math.sin(RainbowBars.frameCounter * 5), 1, 1);
			counter = counter + 0.1f;
			// sprite.setScale((float) (Math.sin(counter) / 2 + 2));
		} else {
			sprite.setScale(1);
			sprite.setColor(1, 1, 1, 1);
		}

		// TODO Auto-generated method stub

		return ClickedHandler.isClicked(new Vector2((float) (100 + x - cameraX
				+ 1800 + (int) (pos / 3) * 600), sprite.getY() + 20),
				new Vector2(sprite.getWidth(), sprite.getHeight()), true);
	}

	public int id() {
		// TODO Auto-generated method stub
		return number;
	}

}
