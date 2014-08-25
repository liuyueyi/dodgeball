package com.july.dodgeball;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class Asset {
	private static Asset instance;

	public BitmapFont font;
	public LabelStyle fontStyle;
	public BitmapFont numfont;
	public LabelStyle numfontStyle;

	public TextureAtlas atlas;
	public TextureRegion bg[];

	public TextureRegion balls[];
	public TextureRegion bearStand[];
	public TextureRegion bearRunLeft[];
	public TextureRegion bearRunRight[];
	public TextureRegion bearGunLeft[];
	public TextureRegion bearGunRight[];
	public TextureRegion bearDie[];

	public TextureRegion title;
	public TextureRegion gameover;
	public TextureRegion start; // 提示开始的图片
	public TextureRegion startButton;
	public TextureRegion continueButton;
	public TextureRegion returnButton;
	public TextureRegion exit;
	public TextureRegion more;
	public TextureRegion music[];
	public TextureRegion sound[];

	// public TextureRegion levelButton[];
	TextureRegionDrawable levels[];

	public FileHandle file;

	public Music bgMusic;
	public Music clickMusic;
	public Music failMusic;

	private Asset() {

	}

	public static Asset getInstance() {
		if (instance == null) {
			instance = new Asset();
		}
		return instance;
	}

	public void loadTexture() {
		Texture ftr = new Texture(Gdx.files.internal("font/font.png"));
		ftr.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		font = new BitmapFont(Gdx.files.internal("font/font.fnt"),
				new TextureRegion(ftr), false);
		font.setScale(Constants.hrate, Constants.wrate);
		fontStyle = new LabelStyle(font, Color.WHITE);

		Texture ftr2 = new Texture(Gdx.files.internal("font/num.png"));
		ftr2.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		numfont = new BitmapFont(Gdx.files.internal("font/num.fnt"),
				new TextureRegion(ftr2), false);
		numfont.setScale(Constants.hrate, Constants.wrate);
		numfontStyle = new LabelStyle(numfont, Color.WHITE);

		atlas = new TextureAtlas(Gdx.files.internal("gfx/ball.pack"));
		bg = new TextureRegion[3];
		bg[0] = atlas.findRegion("bg1");
		bg[1] = atlas.findRegion("bg2");
		bg[2] = atlas.findRegion("bg3");

		balls = new TextureRegion[5];
		for (int i = 0; i < 5; i++) {
			balls[i] = atlas.findRegion("ball", i);
		}

		bearStand = new TextureRegion[4];
		bearStand[0] = atlas.findRegion("1");
		bearStand[1] = atlas.findRegion("2");
		bearStand[2] = atlas.findRegion("3");
		bearStand[3] = atlas.findRegion("2");
		bearRunLeft = new TextureRegion[8];
		for (int i = 0; i < 8; i++) {
			bearRunLeft[i] = atlas.findRegion("run_left", i);
		}
		bearRunRight = new TextureRegion[8];
		for (int i = 0; i < 8; i++) {
			bearRunRight[i] = atlas.findRegion("run_right", i);
		}
		bearGunLeft = new TextureRegion[5];
		for (int i = 0; i < 5; i++) {
			bearGunLeft[i] = atlas.findRegion("gun_left", i);
		}
		bearGunRight = new TextureRegion[5];
		for (int i = 0; i < 5; i++) {
			bearGunRight[i] = atlas.findRegion("gun_right", i);
		}
		bearDie = new TextureRegion[8];
		for (int i = 0; i < 8; i++) {
			bearDie[i] = atlas.findRegion("die", i);
		}

		title = atlas.findRegion("title");
		gameover = atlas.findRegion("game_over");
		start = atlas.findRegion("start_info");

		startButton = atlas.findRegion("start");
		continueButton = atlas.findRegion("continue");
		returnButton = atlas.findRegion("return");
		music = new TextureRegion[2];
		music[0] = atlas.findRegion("music", 0);
		music[1] = atlas.findRegion("music", 1);
		sound = new TextureRegion[2];
		sound[0] = atlas.findRegion("sound", 0);
		sound[1] = atlas.findRegion("sound", 1);
		exit = atlas.findRegion("close");
		more = atlas.findRegion("more");

		levels = new TextureRegionDrawable[6];
		levels[0] = new TextureRegionDrawable(atlas.findRegion("3ball", 0));
		levels[1] = new TextureRegionDrawable(atlas.findRegion("3ball", 1));
		levels[2] = new TextureRegionDrawable(atlas.findRegion("4ball", 0));
		levels[3] = new TextureRegionDrawable(atlas.findRegion("4ball", 1));
		levels[4] = new TextureRegionDrawable(atlas.findRegion("5ball", 0));
		levels[5] = new TextureRegionDrawable(atlas.findRegion("5ball", 1));
		//
		// levelButton = new TextureRegion[6];
		// levelButton[0] = atlas.findRegion("3ball", 0);
		// levelButton[1] = atlas.findRegion("3ball", 1);
		// levelButton[2] = atlas.findRegion("4ball", 0);
		// levelButton[3] = atlas.findRegion("4ball", 1);
		// levelButton[4] = atlas.findRegion("5ball", 0);
		// levelButton[5] = atlas.findRegion("5ball", 1);

		file = Gdx.files.local("data/.score");
		if (file.exists()) {
			readScore();
		} else {
			writeScore();
		}
	}

	public void readScore() {
		try {
			String score[] = file.readString().split("\\$");
			Constants.bestScore[0] = Float.parseFloat(score[0]);
			Constants.bestScore[1] = Float.parseFloat(score[1]);
			Constants.bestScore[2] = Float.parseFloat(score[2]);
			Constants.ballNum = Integer.parseInt(score[3]);
			Constants.isMusic = Boolean.parseBoolean(score[4]);
			Constants.isSound = Boolean.parseBoolean(score[5]);
			Constants.startTime = Integer.parseInt(score[6]);
		} catch (Exception e) {
			writeScore();
		}
	}

	public void writeScore() {
		file.writeString(Constants.bestScore[0] + "$" + Constants.bestScore[1]
				+ "$" + Constants.bestScore[2] + "$" + Constants.ballNum + "$"
				+ Constants.isMusic + "$" + Constants.isSound + "$"
				+ Constants.startTime, false); // 覆盖写
	}

	public void loadMusic() {
		bgMusic = Gdx.audio.newMusic(Gdx.files.internal("mfx/bg.mp3"));
		bgMusic.setLooping(true);
		clickMusic = Gdx.audio.newMusic(Gdx.files.internal("mfx/click.mp3"));
		failMusic = Gdx.audio.newMusic(Gdx.files.internal("mfx/fail.ogg"));
	}

	public void dispose() {
		bgMusic.dispose();
		clickMusic.dispose();
		failMusic.dispose();

		atlas.dispose();
		font.dispose();
		numfont.dispose();
	}
}
