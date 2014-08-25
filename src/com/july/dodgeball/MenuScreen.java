package com.july.dodgeball;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class MenuScreen implements Screen {
	MainGame game;

	private SpriteBatch batch;
	private Stage stage;
	private TextureRegion bg;
	private TextureRegion title;

	private Animation ballAnimation;
	private TextureRegion ball;
	private Animation bearRun[];
	private boolean runLeft = false;
	private float bearX, bearY, ballX;
	private TextureRegion bear;
	private float time = 0;

	private Image startButton;
	private Image music;
	private Image sound;
	private Image exit;
	private Image more;
	private Image levels[];

	public MenuScreen(final MainGame game) {
		this.game = game;
		final MenuScreen tem = this;

		batch = new SpriteBatch();
		stage = new Stage(Constants.screenWidth, Constants.screenHeight, false);
		bg = Asset.getInstance().bg[0];
		title = Asset.getInstance().title;
		

		startButton = new Image(Asset.getInstance().startButton);
		startButton.setPosition(Constants.startButtonX, Constants.startButtonY
				+ Constants.screenHeight);
		startButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (Constants.isSound) {
					Asset.getInstance().clickMusic.play();
				}
				tem.dispose();
				game.setScreen(new GameScreen(game));
				game.main.showAdStatic(Constants.CLOSE_BANNER);
			}
		});
		stage.addActor(startButton);
		Action a0 = Actions.moveTo(Constants.startButtonX,
				Constants.startButtonY + Constants.screenHeight,
				Gdx.graphics.getDeltaTime() * 10);
		Action a1 = Actions.moveTo(Constants.startButtonX,
				Constants.startButtonY - 150, Gdx.graphics.getDeltaTime() * 10);
		Action a2 = Actions.moveTo(Constants.startButtonX,
				Constants.startButtonY + 100, Gdx.graphics.getDeltaTime() * 10);
		Action a3 = Actions.moveTo(Constants.startButtonX,
				Constants.startButtonY, Gdx.graphics.getDeltaTime() * 10);
		startButton.addAction(Actions.sequence(a0, a1, a2, a3));

		final TextureRegionDrawable trd[] = {
				new TextureRegionDrawable(Asset.getInstance().music[0]),
				new TextureRegionDrawable(Asset.getInstance().music[1]),
				new TextureRegionDrawable(Asset.getInstance().sound[0]),
				new TextureRegionDrawable(Asset.getInstance().sound[1]) };
		if (Constants.isMusic)
			music = new Image(trd[0]);
		else
			music = new Image(trd[1]);
		music.setBounds(Constants.musicX, Constants.musicY, Constants.tagWidth,
				Constants.tagHeight);
		music.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (Constants.isSound) {
					Asset.getInstance().clickMusic.play();
				}

				if (Constants.isMusic) { // close music
					Constants.isMusic = false;
					Asset.getInstance().bgMusic.stop();
					music.setDrawable(trd[1]);
				} else {
					Asset.getInstance().bgMusic.play();
					Constants.isMusic = true;
					music.setDrawable(trd[0]);
				}
			}
		});
		stage.addActor(music);

		if (Constants.isSound)
			sound = new Image(trd[2]);
		else
			sound = new Image(trd[3]);
		sound.setBounds(Constants.musicX, Constants.musicY
				+ Constants.tagHeight + 10, Constants.tagWidth,
				Constants.tagHeight);
		sound.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (Constants.isSound) { // close sound
					Constants.isSound = false;
					sound.setDrawable(trd[3]);
				} else {
					Asset.getInstance().clickMusic.play();
					Constants.isSound = true;
					sound.setDrawable(trd[2]);
				}
			}
		});
		stage.addActor(sound);

		exit = new Image(Asset.getInstance().exit);
		exit.setBounds(Constants.closeX, Constants.closeY, Constants.tagWidth,
				Constants.tagHeight);
		exit.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				//if (Constants.startTime >= 0) {
					game.main.showAdStatic(Constants.CHAPIN);
				//}
				game.main.showAdStatic(Constants.EXIT);
			}
		});
		stage.addActor(exit);
		
		
		more = new Image(Asset.getInstance().more);
		more.setBounds(Constants.moreX, Constants.moreY, Constants.tagWidth,
				Constants.tagHeight);
		more.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (Constants.isSound) {
					Asset.getInstance().clickMusic.play();
				}
				game.main.showAdStatic(Constants.TUIGUANG);
			}
		});
		stage.addActor(more);

		levels = new Image[3];
		levels[0] = new Image(Asset.getInstance().levels[0]);
		levels[1] = new Image(Asset.getInstance().levels[2]);
		levels[2] = new Image(Asset.getInstance().levels[4]);
		levels[Constants.ballNum - 3]
				.setDrawable(Asset.getInstance().levels[Constants.ballNum * 2 - 5]);
		for (int i = 0; i < 3; i++) {
			levels[i].setBounds(10, 10 + (Constants.tagHeight + 10) * i,
					Constants.tagWidth * 2, Constants.tagHeight);
			stage.addActor(levels[i]);
		}
		levels[0].addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (Constants.isSound) {
					Asset.getInstance().clickMusic.play();
				}
				Constants.ballNum = 3;
				levels[0].setDrawable(Asset.getInstance().levels[1]);
				levels[1].setDrawable(Asset.getInstance().levels[2]);
				levels[2].setDrawable(Asset.getInstance().levels[4]);
			}
		});
		levels[1].addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (Constants.isSound) {
					Asset.getInstance().clickMusic.play();
				}
				Constants.ballNum = 4;
				levels[0].setDrawable(Asset.getInstance().levels[0]);
				levels[1].setDrawable(Asset.getInstance().levels[3]);
				levels[2].setDrawable(Asset.getInstance().levels[4]);
			}
		});
		levels[2].addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (Constants.isSound) {
					Asset.getInstance().clickMusic.play();
				}
				Constants.ballNum = 5;
				levels[0].setDrawable(Asset.getInstance().levels[0]);
				levels[1].setDrawable(Asset.getInstance().levels[2]);
				levels[2].setDrawable(Asset.getInstance().levels[5]);
			}
		});

		Gdx.input.setInputProcessor(stage);

		initAnimation(); // ³õÊ¼»¯¶¯»­
	}

	public void initAnimation() {
		bearRun = new Animation[2];
		bearRun[0] = new Animation(0.1f, Asset.getInstance().bearRunLeft);
		bearRun[1] = new Animation(0.1f, Asset.getInstance().bearRunRight);

		ballAnimation = new Animation(0.1f, Asset.getInstance().balls);
		time = 0;
		bearX = 0;
		bearY = 0;
	}

	public void drawAnimation() {
		time += Gdx.graphics.getDeltaTime();
		ball = ballAnimation.getKeyFrame(time, true);

		if (runLeft) {
			bear = bearRun[0].getKeyFrame(time, true);
			bearX -= 10;
			ballX = bearX + Constants.ballWidth * 1.5f;
			if (bearX < -Constants.ballWidth * 3) {
				runLeft = false;
			}

		} else {
			bear = bearRun[1].getKeyFrame(time, true);
			bearX += 10;
			ballX = bearX - Constants.ballWidth * 1.5f;
			if (bearX > Constants.screenWidth + Constants.ballWidth * 2) {
				runLeft = true;
			}
		}

		batch.begin();
		batch.draw(ball, ballX, bearY);
		batch.draw(bear, bearX, bearY);
		batch.end();
	}

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		Gdx.gl.glClearColor(.4f, .4f, .4f, .4f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		batch.begin();
		batch.draw(bg, 0, 0, Constants.screenWidth, Constants.screenHeight);
		batch.draw(title, Constants.titleX, Constants.titleY,
				Constants.titleWidth, Constants.titleHeight);
		batch.end();

		stage.act();
		stage.draw();

		drawAnimation();
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void show() {
		// TODO Auto-generated method stub

	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		batch.dispose();
		stage.dispose();
	}

}
