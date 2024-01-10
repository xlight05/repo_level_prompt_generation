package de.exobros.exogdx.sbg;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public abstract class GameState2D implements GameState {

	private Stage stage;

	protected SpriteBatch spriteBatch;
	protected OrthographicCamera camera;
	protected Table gui;
	protected StateBasedGame stateBasedGame;
	
	@Override
	public void onCreate(StateBasedGame sbg) {
		this.stateBasedGame = sbg;

		spriteBatch = new SpriteBatch();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		stage = new Stage();

		gui = new Table();
		gui.setBounds(0, 0, stage.getWidth(), stage.getHeight());
		gui.setLayoutEnabled(false);

		stage.addActor(gui);
		this.init();
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

		camera.update();
		spriteBatch.setProjectionMatrix(camera.combined);

		this.update(delta);

		spriteBatch.begin();
		this.draw(delta, spriteBatch);
		spriteBatch.end();
		
		stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		

	}

	@Override
	public void show() {
		Gdx.input.setInputProcessor(stage);
	}

	@Override
	public void hide() {
		Gdx.input.setInputProcessor(new Stage());
	}

	@Override
	public void pause() {
		

	}

	@Override
	public void resume() {
		

	}

	@Override
	public void dispose() {
		

	}

	public abstract void init();

	public abstract void update(float delta);

	public abstract void draw(float delta, SpriteBatch sb);

	public void onEnter() {

	}

	public void onLeave() {

	}

}
