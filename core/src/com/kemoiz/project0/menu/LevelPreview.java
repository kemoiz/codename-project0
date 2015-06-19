package com.kemoiz.project0.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.kemoiz.project0.MotherClass;

public class LevelPreview {

	private int selectedLevel = 0;
	public boolean transition = false;
	private Array<Texture> previews = new Array<Texture>();
	private int maxLevel = MotherClass.maxLevel;
	private float transVal = 0;
	private Sprite trans1, trans2;
	private boolean next;
	public int holdCounter = 0;

	public int hold(boolean isTouched) {

		if (isTouched)
			holdCounter++;
		else
			holdCounter = 0;
		return holdCounter;
	}

	public LevelPreview() {

		loadPreviews();
	}

	private void loadPreviews() {
		for (int i = 0; i <= maxLevel; i++) {
			if (Gdx.files.internal("gfx/preview_" + i + ".png").exists()) {
				previews.add(new Texture(Gdx.files.internal("gfx/preview_" + i
						+ ".png")));
			} else {
				previews.add(new Texture("gfx/preview_na.png"));
			}
		}
	}

	public Texture getCurrentPreview() {

		return previews.get(selectedLevel);
	}

	public void render(SpriteBatch batch) {
		if (!transition)
			batch.draw(getCurrentPreview(), 1475, 50);
		else {

			trans1.draw(batch, Math.abs(transVal - 1f));
			trans2.draw(batch, transVal);
		}

	}

	public void update() {
		if (transition) {
			transVal += 1 / 30f;

		}

		if (transVal >= 1 && transition) {
			transition = false;
			if (next)
				selectedLevel++;
			else
				selectedLevel--;
			transVal = 0;
		}
	}

	public void changePreview(boolean next) {
		this.next = next;
		if (next) {

			if (selectedLevel == maxLevel) {
				return;
			}

			transition = true;
			trans1 = new Sprite(previews.get(selectedLevel));
			trans2 = new Sprite(previews.get(selectedLevel + 1));
			trans1.setPosition(1475, 50);
			trans2.setPosition(1475, 50);
			return;
		}

		if (!next) {
			if (selectedLevel == 0)
				return;

			transition = true;
			trans1 = new Sprite(previews.get(selectedLevel));
			trans2 = new Sprite(previews.get(selectedLevel - 1));
			trans1.setPosition(1475, 50);
			trans2.setPosition(1475, 50);
		}
	}

	public int getCurrentLevel() {
		// TODO Auto-generated method stub
		return selectedLevel;
	}

}
