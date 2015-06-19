package com.kemoiz.project0.handlers;

import java.util.Random;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;

public class PixmapGen {

	public Pixmap pixmap(int width, int height) {
		Pixmap pixmap = new Pixmap(width, height, Format.RGBA4444);

		Random r = new Random();
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				pixmap.setColor(r.nextFloat(), r.nextFloat(), r.nextFloat(), 1);
				pixmap.drawPixel(x, y);
			}

		}

		return pixmap;

	}

}
