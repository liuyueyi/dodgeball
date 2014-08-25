package com.july.dodgeball;

import java.text.DecimalFormat;

import com.badlogic.gdx.Gdx;

public class Constants {
	public static int startTime = 0;
	
	public static float screenWidth = Gdx.graphics.getWidth();
	public static float screenHeight = Gdx.graphics.getHeight();

	public static float wrate = screenWidth / 800f;
	public static float hrate = screenHeight / 480;

	public static float PPM = 100;
	public static float DOUBLE_PPM = 200;
	public static float edgeX = screenWidth / DOUBLE_PPM;
	public static float edgeY = screenHeight / DOUBLE_PPM;

	public static float bestScore[] = { 0, 0, 0 };
	public static float score = 0;
	public static DecimalFormat df = new DecimalFormat("0.000");

	public static int ballNum = 3;
	public static float ballWidth = 80;
	public static float ballHeight = 80;

	public static final int isRun = 1;
	public static final int isPause = 0;
	public static final int isOver = 2;
	public static int status = isPause;

	// result
	public static float resultButtonWidth = 160;
	public static float resultButtonHeight = 80;
	public static float returnButtonX = screenWidth / 4 - resultButtonWidth / 2;
	public static float returnButtonY = screenHeight * 0.3f;
	public static float continueButtonX = returnButtonX + screenWidth / 2;
	public static float continueButtonY = returnButtonY;

	// menu
	public static float tagWidth = 80;
	public static float tagHeight = 80;
	public static float musicX = screenWidth - tagWidth - 10;
	public static float musicY = 0;
	public static float moreX = musicX;
	public static float moreY = tagHeight * 2 + 20;
	public static float closeX = musicX;
	public static float closeY = screenHeight - tagHeight - 10;

	public static float titleWidth = 320 * wrate;
	public static float titleHeight = 100 * hrate;
	public static float titleX = (screenWidth - titleWidth) / 2;
	public static float titleY = screenHeight * 0.65f;

	public static float startButtonX = (screenWidth - resultButtonWidth) / 2;
	public static float startButtonY = screenHeight * 0.3f;

	// music
	public static boolean isMusic = true;
	public static boolean isSound = true;

	public static final int CHAPIN = 0;
	public static final int TUIGUANG = 1;
	public static final int EXIT = 2;
	public static final int SHARE = 3;
	public static final int CLOSE_BANNER = 4;
	public static final int SHOW_BANNER = 5;
	
}
