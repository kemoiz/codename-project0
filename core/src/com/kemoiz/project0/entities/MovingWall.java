package com.kemoiz.project0.entities;

import box2dLight.PointLight;
import box2dLight.RayHandler;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.kemoiz.project0.handlers.PixmapGen;

public class MovingWall extends PhysicEntity {

	private String worldID = "0";
	private MapScript script;
	private FixtureDef fixtureDef;
	private Vector2 initialPosition, size;
	private Vector2 change = new Vector2(0, 0);
	private int step = 0;
	private Texture texture;

	public boolean delivered;
	public PointLight light;

	public MovingWall(Vector2 position, Vector2 size, World world,
			RayHandler ray) {

		this.sprite = new Sprite(new Texture(new PixmapGen().pixmap(
				(int) size.x, (int) size.y)));
		this.script = script;
		this.size = size;

		this.light = new PointLight(ray, 100, Color.GREEN, 15, position.x,
				position.y);
		light.setXray(true);
		initialPosition = position;
		BodyDef bodyDef = new BodyDef();

		bodyDef.type = BodyType.DynamicBody;

		bodyDef.position.set(initialPosition.x + 8, initialPosition.y + 16);

		PolygonShape shape = new PolygonShape();
		fixtureDef = new FixtureDef();
		shape.setAsBox(size.x / 2, size.y / 2);
		fixtureDef.shape = shape;
		fixtureDef.density = 0.95f;
		fixtureDef.friction = 0.1f;
		fixtureDef.restitution = 0.97f;

		body = world.createBody(bodyDef);
		sprite.setOrigin(sprite.getWidth() / 2, sprite.getHeight() / 2);
		sprite.setPosition(body.getPosition().x - (size.x / 2),
				body.getPosition().y - (size.x / 2));
		sprite.setRotation(body.getAngle() * MathUtils.radiansToDegrees);
		sprite.setSize(size.x, size.y);
		body.setUserData(new EntityDataContainer(sprite, worldID));

		body.createFixture(fixtureDef);
	}

	@Override
	public void render(SpriteBatch batch) {
		sprite.setPosition(body.getPosition().x - (size.x / 2),
				body.getPosition().y - (size.y / 2));
		sprite.setRotation(body.getAngle() * MathUtils.radiansToDegrees);
		super.render(batch);

	}

	public void update() {

		body.setLinearVelocity(body.getLinearVelocity().x * 0.995f,
				body.getLinearVelocity().y * 0.995f);
		body.setAngularVelocity(body.getAngularVelocity() * 0.95f);

		light.setPosition(body.getPosition());
	}
}
