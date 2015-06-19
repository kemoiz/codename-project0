package com.kemoiz.project0;

import com.badlogic.gdx.math.Vector2;

public class Obstacle {

	public static int REG = 0;
	public static int DL = 1;
	public static int UL = 2;
	public static int UR = 3;
	public static int DR = 4;

	public static Vector2[] getDL() {
		Vector2[] vec = new Vector2[3];
		vec[0] = new Vector2(-8, -8);
		vec[1] = new Vector2(-8, 8);
		vec[2] = new Vector2(8, -8);
		return vec;

	}

	public static Vector2[] getUL() {
		Vector2[] vec = new Vector2[3];
		vec[0] = new Vector2(-8, -8);
		vec[1] = new Vector2(-8, 8);
		vec[2] = new Vector2(8, 8);
		return vec;

	}

	public static Vector2[] getUR() {
		Vector2[] vec = new Vector2[3];
		vec[0] = new Vector2(8, 8);
		vec[1] = new Vector2(-8, 8);
		vec[2] = new Vector2(8, -8);
		return vec;

	}

	public static Vector2[] getDR() {
		Vector2[] vec = new Vector2[3];
		vec[0] = new Vector2(-8, -8);
		vec[1] = new Vector2(8, -8);
		vec[2] = new Vector2(8, 8);
		return vec;

	}

	public static Vector2[] getREG() {
		Vector2[] vec = new Vector2[4];
		vec[0] = new Vector2(-8, -8);
		vec[1] = new Vector2(8, -8);
		vec[2] = new Vector2(8, 8);
		vec[3] = new Vector2(-8, 8);
		return vec;

	}

	public static Vector2[] getShape(int type) {
		switch (type) {
		case 0:
			return getREG();

		case 1:
			return getDL();
		case 2:
			return getUL();
		case 3:
			return getUR();
		case 4:
			return getDR();

		}

		return getREG();
	}

}
