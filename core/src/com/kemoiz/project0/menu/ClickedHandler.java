package com.kemoiz.project0.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.kemoiz.project0.MotherClass;

public class ClickedHandler {

	public static boolean clickedLastFrame = false;

	public static double vX = 800 / MotherClass.SCR_WIDTH,
			vY = 480 / MotherClass.SCR_HEIGHT;

	public static void update() {

		clickedLastFrame = Gdx.input.isTouched();
	}

	public static boolean isClicked(Vector2 position, Vector2 size,
			boolean immediate) {

		Rectangle textRectangle = new Rectangle(position.x, position.y, size.x,
				size.y);
		Rectangle pointerRectangle = new Rectangle(Gdx.input.getX()
				* (float) vX,
				((float) MotherClass.SCR_HEIGHT - Gdx.input.getY())
						* (float) vY, 15, 15);
		if (Intersector.overlaps(textRectangle, pointerRectangle)) {

			if (immediate)
				return true;

			if (!Gdx.input.isTouched() && clickedLastFrame)
				return false;
			return false;

		}

		return false;

	}

	public static boolean isOverhere(Vector2 position, Vector2 size) {

		Rectangle textRectangle = new Rectangle(position.x, position.y, size.x,
				size.y);
		Rectangle pointerRectangle = new Rectangle(Gdx.input.getX()
				* (float) vX,
				((float) MotherClass.SCR_HEIGHT - Gdx.input.getY())
						* (float) vY, 15, 15);

		if (Intersector.overlaps(textRectangle, pointerRectangle)) {

			return true;

		}

		return false;

	}

	public static boolean isTouchedDown() {
		return (clickedLastFrame && !Gdx.input.isTouched());

	}

}
