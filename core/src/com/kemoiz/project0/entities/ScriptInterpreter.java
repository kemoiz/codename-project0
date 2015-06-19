package com.kemoiz.project0.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class ScriptInterpreter {

	private Array<Vector2> array;
	private String charAt;

	public Array<Vector2> getPath(String script) {

		array = new Array<Vector2>();
		int pointer = 0; // -1 means syntax error

		Gdx.app.log("string:", script);

		pointer += 2;
		do {

			if (script.regionMatches(pointer, "up", 0, 2)) {
				pointer += 2;
				Gdx.app.log("le", Character.toString(script.charAt(pointer)));
				String c = Character.toString(script.charAt(pointer));
				array.add(new Vector2(0, Float.valueOf(c)));

			}
			if (script.regionMatches(pointer, "down", 0, 4)) {
				pointer += 4;
				String c = Character.toString(script.charAt(pointer));
				array.add(new Vector2(0, -Float.valueOf(c)));

			}
			pointer++;
			Gdx.app.log(Integer.toString(pointer),
					Character.toString(script.charAt(pointer)));
			charAt = Character.toString(script.charAt(pointer));
		} while (!charAt.contains(")"));

		System.out.println(array.first());

		return array;

	}

}
