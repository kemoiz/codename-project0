package com.kemoiz.project0.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.utils.TimeUtils;

public class MenuLabel extends Label {

	private boolean clicked;
	protected ShapeRenderer shapeRenderer;
	private float greenVal;
	private float redVal;
	private Matrix4 proj;
	public boolean visible = true;

	public MenuLabel(CharSequence text, LabelStyle style, Matrix4 combined) {

		super(text, style);
		shapeRenderer = new ShapeRenderer();
		this.setAlignment(Align.center);
		this.setAlignment(Align.center, Align.center);
		proj = combined;

	}

	@Override
	public void draw(Batch batch, float parentAlpha) {

		if (visible)
			super.draw(batch, parentAlpha);

	}

	public boolean isClicked() {

		if (ClickedHandler.isOverhere(new Vector2(getX(), getY()), new Vector2(
				getWidth(), getHeight()))) {

			greenVal = (float) Math.sin(TimeUtils.millis() / 90);
			redVal = -greenVal;
			if (!Gdx.input.isTouched() && ClickedHandler.clickedLastFrame)
				return true;
		} else {
			greenVal = 0;
			redVal = 0;
		}

		return ClickedHandler.isClicked(new Vector2(getX(), getY()),
				new Vector2(getWidth(), getHeight()), true);
	}

	public void drawRect(float x) {
		shapeRenderer.begin(ShapeType.Filled);
		shapeRenderer.setProjectionMatrix(proj);
		shapeRenderer.setColor(redVal, greenVal, 1, 0.1f);

		shapeRenderer.rect((getX() - x), getY(), getWidth(), getHeight());
		shapeRenderer.end();

	}

}
