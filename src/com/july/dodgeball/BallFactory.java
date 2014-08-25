package com.july.dodgeball;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.Array;

public class BallFactory {
	World world;
	Group group;
	Array<Ball> arrays;
	boolean flag = false;
	float time = 0;
	int size = 0;

	public BallFactory(World world) {
		size = 0;
		flag = false;
		time = 0;
		this.world = world;
		group = new Group();
		arrays = new Array<Ball>();
	}

	public void autoGenerate() {
		time += Gdx.graphics.getDeltaTime();
		if (time > 3 && size < Constants.ballNum) { // 没三秒生成一个小球，最多有ballNum个小球
			time = 0;
			generateBall();
		}
	}

	public void generateBall() {
		float x = Constants.ballWidth / 2 + 5;
		float y = Constants.screenHeight - x;
		++size;
		Ball temp = new Ball(world, x, y);
		temp.sbody.setLinearVelocity(3, -(int)(Math.random() * 2));
		arrays.add(temp);
		group.addActor(temp);
		System.out.println("generate ball + " + size + " array " + arrays.size);
	}

	public boolean judge(Rectangle bearRectangle) {
		autoGenerate();

		if (!flag) {
			for (Ball ball : arrays) {
				if (ball.hited(bearRectangle)) {
					System.out.println("hit bear!");
					flag = true;
					return true;
				}
			}
		}
		return flag;
	}

	public void removeAll() {
		size = 0;
		arrays.clear();
		group.remove();
	}
}
