package com.kemoiz.project0;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;

public class Profiler {

	static long time_start = 0;
	static String this_process;

	public static void start(String process) {
		this_process = process;
		time_start = System.nanoTime();

	}

	public static double stop() {

		if (Gdx.input.isKeyPressed(Keys.L))
			Gdx.app.log(
					"profiler ",
					this_process + " "
							+ String.valueOf(System.nanoTime() - time_start)
							+ " ns");

		return System.nanoTime() - time_start;

	}

}
