package com.kemoiz.project0.menu;

import com.badlogic.gdx.Files.FileType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFont.BitmapFontData;
import com.badlogic.gdx.graphics.g2d.BitmapFont.Glyph;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.kemoiz.project0.handlers.Fonts;

public class LevelButtonGenerator {

	private static final int TILE_WIDTH = 64;
	private static final int TILE_HEIGHT = 64;
	private Fonts fonts = new Fonts();
	private TextureRegion[] digits = new TextureRegion[3];
	private TextureRegion region = fonts.white.getRegion(0);
	private Texture texture;
	private Pixmap tile;

	public LevelButtonGenerator() {

	}

	public Texture getTexture(int number) {

		// load the background into a pixmap
		tile = new Pixmap(Gdx.files.getFileHandle(
				"gfx/levelbutton_template.png", FileType.Internal));

		// load the font

		BitmapFont font = fonts.white;

		// get the glypth info
		BitmapFontData data = font.getData();
		Pixmap fontPixmap = new Pixmap(Gdx.files.internal(data.imagePaths[0]));
		Glyph glyph = data.getGlyph(String.valueOf(number).charAt(0));

		// draw the character onto our base pixmap
		tile.drawPixmap(fontPixmap, (TILE_WIDTH - glyph.width) / 2,
				(TILE_HEIGHT - glyph.height) / 2, glyph.srcX, glyph.srcY,
				glyph.width, glyph.height);

		// save this as a new texture
		Texture tex = new Texture(tile);
		tile.dispose();
		fontPixmap.dispose();

		return tex;

	}

}
