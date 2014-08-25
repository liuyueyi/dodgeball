package com.july.dodgeball;

import com.badlogic.gdx.Game;

public class MainGame extends Game {
	MainActivity main;

	public MainGame(MainActivity main) {
		this.main = main;
	}

	@Override
	public void create() {
		Asset.getInstance().loadTexture();
		Asset.getInstance().loadMusic();
		if (Constants.isMusic) {
			Asset.getInstance().bgMusic.play();
		}
		Constants.startTime++;
		setScreen(new MenuScreen(this));
	}

	@Override
	public void dispose() {
		Asset.getInstance().writeScore();
		Asset.getInstance().dispose();
	}
}
