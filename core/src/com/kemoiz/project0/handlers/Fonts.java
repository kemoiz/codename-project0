package com.kemoiz.project0.handlers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;

public class Fonts {

	public BitmapFont white;
	private LabelStyle headingStyle;
	private Label label1, label2, label3, counter;
	public Stage stage;
	public Label label4;
	private int flashingCounter = 0;
	private Label label5;

	public Fonts() {
		stage = new Stage();

		white = new BitmapFont(Gdx.files.internal("data/white.fnt"), false);
		white.setScale(0.3f);

		headingStyle = new LabelStyle(white, Color.WHITE);

		stage.getViewport().setCamera(new OrthographicCamera(800, 480));
		// stage.getViewport().project(new Vector2(-400, -240));
		label1 = new Label("label1", headingStyle);
		label2 = new Label("label2", headingStyle);
		label3 = new Label(" ", headingStyle);
		label4 = new Label("counter", headingStyle);
		label5 = new Label("label5", headingStyle);
		label2.setFontScale(0.2f);
		label3.setFontScale(0.3f);
		label4.setFontScale(0.65f);
		label1.setPosition(200 - 425, 450 - 240);
		label2.setPosition(200 - 400, 40 - 240);
		label3.setPosition(-400, 200 - 240);
		label4.setPosition(-5, -5);
		label5.setPosition(200 - 275, 450 - 240);
		stage.addActor(label2);
		stage.addActor(label1);
		stage.addActor(label3);
		stage.addActor(label4);
		stage.addActor(label5);

	}

	public void render() {

		stage.draw();
	}

	public void setText(String text, int label) {
		switch (label) {
		case 1:
			label1.setText(text);
			break;
		case 2:
			label2.setText(text);
			break;
		case 3:
			label3.setText(text);
			break;
		case 4:
			label4.setText(text);
			break;
		case 5:
			label5.setText(text);
			break;
		}

	}

	public void setBottomLabelPosition(float x) {
		label2.setPosition(x, label2.getY());
	}

	public void setCounter(String text, float size) {

		label4.setText(text);
		label4.setFontScale(size);

	}

	public void dispose() {
		stage.dispose();
		white.dispose();

	}

	public void flash() {

		flashingCounter++;

		if (flashingCounter > 10) {
			label1.setColor(Color.RED);
			label5.setColor(Color.RED);
		} else {
			label1.setColor(Color.WHITE);
			label5.setColor(Color.WHITE);
		}
		if (flashingCounter > 20)
			flashingCounter = 0;
	}

	public void normalize() {
		label1.setColor(Color.WHITE);
		label5.setColor(Color.WHITE);

	}
}
