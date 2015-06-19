package com.kemoiz.project0;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.kemoiz.project0.entities.EntityDataContainer;
import com.kemoiz.project0.handlers.AssetHandler;

public class Player {

	private Sprite sprite;
	private Body body;
	private BodyDef bodyDef;
	private double power = 4000;

	private float accStrength = 1000;
	private Vector2 initialAccCords;
	private double aY;
	private double aX;
	private boolean powerup;
	public String worldID = "player";
	private Vector2 joystick;
	private boolean gyroscope;

	public Player(World world, Vector2 playerStart, boolean gyroscope) {
		this.gyroscope = gyroscope;
		sprite = new Sprite(AssetHandler.player);
		bodyDef = new BodyDef();
		bodyDef.type = BodyType.DynamicBody;
		bodyDef.position.set(playerStart);
		body = world.createBody(bodyDef);
		body.setBullet(true);
		CircleShape dynamicCircle = new CircleShape();
		dynamicCircle.setRadius(1.1f * 7 / 2);
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = dynamicCircle;
		fixtureDef.density = 0.95f;
		fixtureDef.friction = 0.3f;
		fixtureDef.restitution = 0.6f;
		sprite.setOrigin(sprite.getWidth() / 2, sprite.getHeight() / 2);
		sprite.setPosition(body.getPosition().x - 3.8f,
				body.getPosition().y - 4.0f);
		sprite.setRotation(body.getAngle() * MathUtils.radiansToDegrees);
		sprite.setSize(8, 8);
		body.setUserData(new EntityDataContainer(sprite, worldID));

		body.createFixture(fixtureDef);
		initialAccCords = new Vector2();
	}

	public float getX() {
		return body.getPosition().x;
	}

	public float getY() {
		return body.getPosition().y;
	}

	public void update() {

		control();
		applyTorque();
		if (powerup) {
			// body.setLinearVelocity(body.getLinearVelocity().x * 1.25f,
			// body.getLinearVelocity().y * 1.25f);
			body.applyForceToCenter(body.getLinearVelocity().x * 5555.25f,
					body.getLinearVelocity().y * 5555.25f, true);

		}
		sprite.setOrigin(sprite.getWidth() / 2, sprite.getHeight() / 2);
		sprite.setPosition(body.getPosition().x - 3.8f,
				body.getPosition().y - 4.0f);
		sprite.setRotation(body.getAngle() * MathUtils.radiansToDegrees);
		sprite.setSize(8, 8);

	}

	private void applyTorque() {
		body.setLinearVelocity(body.getLinearVelocity().x * 0.980f,
				body.getLinearVelocity().y * 0.980f);
		body.setAngularVelocity(body.getAngularVelocity() * 0.96f);

	}

	private void limitVelocity() {
		if (body.getLinearVelocity().x > 100) {
			body.setLinearVelocity(100, body.getLinearVelocity().y);
		}
		if (body.getLinearVelocity().x < -100) {
			body.setLinearVelocity(-100, body.getLinearVelocity().y);
		}
		if (body.getLinearVelocity().y > 100) {
			body.setLinearVelocity(body.getLinearVelocity().x, 100);
		}
		if (body.getLinearVelocity().y < -100) {
			body.setLinearVelocity(body.getLinearVelocity().x, -100);
		}

	}

	public void render(SpriteBatch batch) {

		sprite.draw(batch);
	}

	public double getaY() {
		return aY / accStrength;
	}

	public double getaX() {
		return aX / accStrength;
	}

	private void control() {
		// android/ios:

		if (gyroscope) {
			aY = (Gdx.input.getAccelerometerY());

			aY = (Math.min(3, Math.max(aY, -3)) * 3.3f) * accStrength;
			aX = (-Gdx.input.getAccelerometerX() + initialAccCords.x);
			aX = (Math.min(3, Math.max(aX, -3)) * 3.3f) * accStrength;

			MotherClass.accX = (-Gdx.input.getAccelerometerX() + initialAccCords.x);
			MotherClass.accY = (Gdx.input.getAccelerometerY());
			double d = 1f;
			body.applyForceToCenter((float) (aY * d), (float) (aX * d), true);
			// body.setLinearVelocity(aY, aX);
		} else {
			float jX = (float) (joystick.x / 16.66 * accStrength * 3.3f);
			jX = (float) (Math.min(9900.0, Math.max(jX, -9900.0)));
			float jY = (float) (joystick.y / 16.66 * accStrength * 3.3f);
			jY = (float) (Math.min(9900.0, Math.max(jY, -9900.0)));
			body.applyForceToCenter(jX, jY, true);

		}
		// debug();
		// windows debug:
		// double powerZ = power * Gdx.graphics.getDeltaTime() * 60;
		double powerZ = power;
		if (Gdx.input.isKeyPressed(Keys.W)) {
			body.applyForceToCenter(0, (float) powerZ, true);
		}
		if (Gdx.input.isKeyPressed(Keys.S)) {
			body.applyForceToCenter(0, (float) -powerZ, true);
		}
		if (Gdx.input.isKeyPressed(Keys.A)) {
			body.applyForceToCenter((float) -powerZ, 0, false);
		}
		if (Gdx.input.isKeyPressed(Keys.D)) {
			body.applyForceToCenter((float) powerZ, 0, false);
		}

		// joystick:

	}

	private void debug() {
		System.out.println("acc x: " + Gdx.input.getAccelerometerY() * 30
				+ " acc y: " + -Gdx.input.getAccelerometerX() * 30);

	}

	public Sprite getSprite() {
		return sprite;
	}

	public void setControls(Vector2 coords) {
		joystick = coords;

	}

	public void setInitialCoordinates(float aY, float aX) {
		initialAccCords.set((aX), (aY));
	}

	public void setPowerUp(boolean b) {
		powerup = b;

	}

	public Vector2 getTilePosition() {
		int cx = (int) ((getX()) / 16);
		int cy = (int) (((getY()) / 16));
		return new Vector2(cx, cy);
	}

	public Vector2 getVelocity() {

		return body.getLinearVelocity();
	}

	public boolean isUsingGyroscope() {
		// TODO Auto-generated method stub
		return gyroscope;
	}
}
