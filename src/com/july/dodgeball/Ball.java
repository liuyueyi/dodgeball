package com.july.dodgeball;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Pools;

public class Ball extends Actor {
	World world;
	TextureRegion ball;
	Body sbody;

	boolean tag = false;

	public Ball(World world, float x, float y) {
		this.world = world;
		ball = Asset.getInstance().balls[(int) (5 * Math.random())];
		setPosition(x / Constants.PPM, y / Constants.PPM);
		setSize(Constants.ballWidth, Constants.ballHeight);

		BodyDef bodyDef = Pools.obtain(BodyDef.class);
		bodyDef.type = BodyDef.BodyType.DynamicBody;
		bodyDef.position.set(x / Constants.PPM, y / Constants.PPM); // ���ó�ʼ����

		CircleShape dynamicCircle = Pools.obtain(CircleShape.class); // ����circle
		dynamicCircle.setRadius(Constants.ballHeight / 2 / Constants.PPM); // ������İ뾶
		FixtureDef fixtureDef = Pools.obtain(FixtureDef.class);
		fixtureDef.shape = dynamicCircle;
		fixtureDef.density = 0f; // �ܶ�
		fixtureDef.friction = 0f; // Ħ��
		fixtureDef.restitution = 1f; // ��ȫ����

		sbody = world.createBody(bodyDef);
		sbody.createFixture(fixtureDef);
		// sbody.setLinearVelocity(3, 0);
		dynamicCircle.dispose();
	}

	/**
	 * �жϸ�С���Ƿ�ײ����
	 * 
	 * @param bearRectangle
	 *            : С�ܵĴ�Сλ��
	 * @return true ��ʾ��ײ���ˣ�false ��ʾû��ײ��
	 */
	public boolean hited(Rectangle bearRectangle) {
		Rectangle rec = Pools.obtain(Rectangle.class);
		rec.x = getX();
		rec.y = getY();
		rec.width = getWidth();
		rec.height = getHeight();

		if (bearRectangle.overlaps(rec)) {
			tag = true;
			return true;
		}
		/*
		 * if (Math.abs(bearRectangle.x - getX()) <= Constants.ballWidth + 10 &&
		 * Math.abs(bearRectangle.y - getY()) <= Constants.ballHeight + 10) {
		 * return true; }
		 */

		return false;
	}

	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		setPosition(sbody.getPosition().x, sbody.getPosition().y);
		batch.draw(ball, getX(), getY(), Constants.ballWidth,
				Constants.ballHeight);
		super.draw(batch, parentAlpha);
	}

	@Override
	public void setPosition(float x, float y) {
		super.setPosition(x * Constants.PPM - Constants.ballWidth / 2, y
				* Constants.PPM - Constants.ballHeight / 2);
	}
}
