package com.july.dodgeball;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.Align;

public class GameScreen implements Screen, InputProcessor {
	MainGame game;

	private SpriteBatch batch;
	private Stage stage;
	private TextureRegion bg;
	private Bear bear;
	private BallFactory ballFactory;
	private Label scoreLabel;
	private Label bestLabel;

	private Result result;

	private World world;
	protected OrthographicCamera camera;
	protected Box2DDebugRenderer renderer; // 测试用绘制器
	
	public GameScreen(MainGame game) {
		this.game = game;
		initWorld();

		batch = new SpriteBatch();
		stage = new Stage(Constants.screenWidth, Constants.screenHeight, false);
		bg = Asset.getInstance().bg[(int) (Math.random() * 3)];

		bear = new Bear(); // 创建小熊
		bear.setPosition(Constants.screenWidth / 2, 5);
		stage.addActor(bear);

		ballFactory = new BallFactory(world);
		stage.addActor(ballFactory.group);

		scoreLabel = new Label("score: 0.000", Asset.getInstance().fontStyle);
		scoreLabel.setBounds(0, Constants.screenHeight * 0.75f,
				Constants.screenWidth, 40);
		scoreLabel.setAlignment(Align.center);
		stage.addActor(scoreLabel);

		bestLabel = new Label("best: "
				+ Constants.df.format(Constants.bestScore[Constants.ballNum - 3]),
				Asset.getInstance().numfontStyle);
		bestLabel.setBounds(0, Constants.screenHeight - 40,
				Constants.screenWidth, 40);
		bestLabel.setAlignment(Align.left);
		stage.addActor(bestLabel);

		result = new Result(this);

		Gdx.input.setInputProcessor(this);
		Constants.status = Constants.isPause; // 开始时，进入就绪状态
		Constants.score = 0;
	}

	public void initWorld() {
		camera = new OrthographicCamera();
		// 将像素转换为米，false表示不颠倒相机
		camera.setToOrtho(false, Constants.screenWidth / Constants.PPM,
				Constants.screenHeight / Constants.PPM);
		renderer = new Box2DDebugRenderer();

		// 创建一个世界, ture表示没有事件发生时休眠
		world = new World(new Vector2(0, -9.81f), true);

		BodyDef groundBodyDef = new BodyDef();
		groundBodyDef.type = BodyDef.BodyType.StaticBody;
		groundBodyDef.position.set(
				Constants.screenWidth / Constants.DOUBLE_PPM,
				Constants.screenHeight / Constants.DOUBLE_PPM); // 是坐标从起点到圆心的距离
		// 生成
		Body groundBody = world.createBody(groundBodyDef);
		EdgeShape edge = new EdgeShape();
		FixtureDef boxShapeDef = new FixtureDef();
		boxShapeDef.shape = edge;
		edge.set(new Vector2(-Constants.edgeX, -Constants.edgeY), new Vector2(
				Constants.edgeX, -Constants.edgeY));
		groundBody.createFixture(boxShapeDef);

		edge.set(new Vector2(-Constants.edgeX, -Constants.edgeY), new Vector2(
				-Constants.edgeX, Constants.edgeY));
		groundBody.createFixture(boxShapeDef);

		edge.set(new Vector2(Constants.edgeX, Constants.edgeY), new Vector2(
				Constants.edgeX, -Constants.edgeY));
		groundBody.createFixture(boxShapeDef);

		edge.set(new Vector2(Constants.edgeX, Constants.edgeY), new Vector2(
				-Constants.edgeX, Constants.edgeY));
		groundBody.createFixture(boxShapeDef);
	}

	public void draw() {
		batch.begin();
		batch.draw(bg, 0, 0, Constants.screenWidth, Constants.screenHeight);
		batch.end();

		if (Constants.status == Constants.isRun) {
			Constants.score += Gdx.graphics.getDeltaTime();
			scoreLabel
					.setText("score: " + Constants.df.format(Constants.score));

			if (Constants.score > Constants.bestScore[Constants.ballNum - 3]) {
				Constants.bestScore[Constants.ballNum - 3] = Constants.score;
				bestLabel.setText("best: "
						+ Constants.df.format(Constants.bestScore[Constants.ballNum - 3]));
			}

			if (ballFactory.judge(bear.getRectangle())) {
				Constants.status = Constants.isOver; // 撞倒了小球，结束
				Asset.getInstance().writeScore(); // 更新最高成绩
				result.show();
			}
		} else if (Constants.status == Constants.isPause) {
			batch.begin();
			batch.draw(Asset.getInstance().start,
					(Constants.screenWidth - Asset.getInstance().start
							.getRegionWidth()) / 2,
					Constants.screenHeight * 0.4f);
			batch.end();
		}

		stage.act();
		stage.draw();

		result.draw(batch);
	}

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		Gdx.gl.glClearColor(.4f, .4f, .4f, .4f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		draw();

		world.step(Gdx.app.getGraphics().getDeltaTime(), 6, 2);
		camera.update();
		renderer.render(world, camera.combined);
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
		ballFactory.removeAll();
		batch.dispose();
		stage.dispose();
		world.dispose();
		result.dispose();
	}

	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		if (Constants.isSound) {
			Asset.getInstance().clickMusic.play();
		}

		if (Constants.status == Constants.isPause) {
			Constants.status = Constants.isRun;
			ballFactory.generateBall();
		} else if (Constants.status == Constants.isRun) {
			bear.setMoveAction(screenX, Constants.screenHeight - screenY,
					(int) (Math.random() * 2));
		}
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}
}
