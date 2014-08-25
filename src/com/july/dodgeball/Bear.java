package com.july.dodgeball;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.Pools;

public class Bear extends Actor {
	public static final int RUN_LEFT = 0;
	public static final int RUN_RIGHT = 1;
	public static final int GUN_LEFT = 2;
	public static final int GUN_RIGHT = 3;
	public static final int STAND = 4;
	int status = STAND;

	Animation stand;
	Animation runLeft;
	Animation runRight;
	Animation gunLeft;
	Animation gunRight;
	Animation die;
	TextureRegion draw;
	float time = 0;

	boolean move = false; // 标记是否移动
	float moveTime = 0; // 移动的时间

	public Bear() {
		stand = new Animation(0.4f, Asset.getInstance().bearStand);
		runLeft = new Animation(0.1f, Asset.getInstance().bearRunLeft);
		runRight = new Animation(0.1f, Asset.getInstance().bearRunRight);
		gunLeft = new Animation(0.1f, Asset.getInstance().bearGunLeft);
		gunRight = new Animation(0.1f, Asset.getInstance().bearGunRight);
		die = new Animation(0.4f, Asset.getInstance().bearDie);

	}

	/**
	 * 开始移动
	 * 
	 * @param tox
	 *            目的x
	 * @param toy
	 *            目的y
	 * @param type
	 *            移动类型： 0 跑 1 滚
	 */
	public void setMoveAction(float tox, float toy, int type) {
		if (tox < 0) {
			tox = 0;
		} else if (tox > Constants.screenWidth - getWidth()) {
			tox = Constants.screenWidth - getWidth();
		}
		if (type == 1) {
			if (getX() < tox) {
				status = GUN_RIGHT;
			} else {
				status = GUN_LEFT;
			}
		} else {
			if (getX() < tox) {
				status = RUN_RIGHT;
			} else {
				status = RUN_LEFT;
			}
		}
		move = true; // 开始执行动画
		moveTime = 0;

		Action action = Actions.moveTo(tox, getY(),
				Gdx.graphics.getDeltaTime() * 10);
		addAction(action);
	}

	@Override
	public void act(float delta) {
		super.act(delta);
		if (move) {
			moveTime++;
			if (moveTime > 10) {// 动画结束
				moveTime = 0;
				move = false;
				status = STAND;
			}
		}

		time += Gdx.graphics.getDeltaTime();
		if (Constants.status == Constants.isOver) {
			draw = die.getKeyFrame(time, true);
		} else {
			switch (status) {
			case STAND:
				draw = stand.getKeyFrame(time, true);
				break;
			case RUN_LEFT:
				draw = runLeft.getKeyFrame(time, true);
				break;
			case RUN_RIGHT:
				draw = runRight.getKeyFrame(time, true);
				break;
			case GUN_LEFT:
				draw = gunLeft.getKeyFrame(time, true);
				break;
			case GUN_RIGHT:
				draw = gunRight.getKeyFrame(time, true);
				break;
			}
		}
	}

	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		setSize(draw.getRegionWidth(), draw.getRegionHeight());
		batch.draw(draw, getX(), getY(), getWidth(), getHeight());
		super.draw(batch, parentAlpha);
	}

	public Rectangle getRectangle() {
		Rectangle rec = Pools.obtain(Rectangle.class);
		rec.x = getX();
		rec.y = getY();
		rec.width = getWidth();
		rec.height = getHeight();
		return rec;

	}
}
