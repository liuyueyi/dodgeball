package com.july.dodgeball;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class Result {
	GameScreen screen;
	Stage stage;
	TextureRegion gameover;
	Label scoreLabel;
	// Image shareButton;
	Image continueButton;
	Image returnButton;

	static int count = 0;

	public Result(final GameScreen screen) {
		this.screen = screen;
		stage = new Stage(Constants.screenWidth, Constants.screenHeight, false);
		gameover = Asset.getInstance().gameover;

		scoreLabel = new Label("" + Constants.score,
				Asset.getInstance().fontStyle);
		scoreLabel.setBounds(0, Constants.continueButtonY
				+ Constants.resultButtonHeight, Constants.screenWidth, 40);
		scoreLabel.setAlignment(Align.center);
		stage.addActor(scoreLabel);

		continueButton = new Image(Asset.getInstance().continueButton);
		continueButton.setBounds(Constants.continueButtonX,
				Constants.continueButtonY, Constants.resultButtonWidth,
				Constants.resultButtonHeight);
		continueButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (Constants.isSound) {
					Asset.getInstance().clickMusic.play();
				}

				Gdx.input.setInputProcessor(screen);
				screen.dispose();
				screen.game.setScreen(new GameScreen(screen.game));
			}
		});
		stage.addActor(continueButton);

		returnButton = new Image(Asset.getInstance().returnButton);
		returnButton.setBounds(Constants.returnButtonX,
				Constants.returnButtonY, Constants.resultButtonWidth,
				Constants.resultButtonHeight);
		returnButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (Constants.isSound) {
					Asset.getInstance().clickMusic.play();
				}

				screen.dispose();
				screen.game.setScreen(new MenuScreen(screen.game));
				screen.game.main.showAdStatic(Constants.SHOW_BANNER);
			}
		});
		stage.addActor(returnButton);
	}

	public void show() {
		if (Constants.isSound) {
			Asset.getInstance().failMusic.play();
		}

		if (++count > 20) {
			count = 0;
			screen.game.main.showAdStatic(Constants.CHAPIN);
		}

		Gdx.input.setInputProcessor(stage);
		scoreLabel.setText("" + Constants.df.format(Constants.score));
	}

	public void draw(SpriteBatch batch) {
		if (Constants.status == Constants.isOver) {
			batch.begin();
			batch.draw(gameover,
					(Constants.screenWidth - Asset.getInstance().gameover
							.getRegionWidth()) / 2, scoreLabel.getHeight()
							+ scoreLabel.getY());
			batch.end();

			stage.act();
			stage.draw();
		}
	}

	public void dispose() {
		stage.dispose();
	}
}
