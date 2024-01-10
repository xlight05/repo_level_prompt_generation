package de.exobros.exogdx.sbg;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFont.TextBounds;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

import de.exobros.exogdx.data.Utils;

public class LoadingScreen implements GameState {

	protected StateBasedGame stateBasedGame;
	protected Texture background;
	protected Texture loadingBarBackground;
	protected Texture loadingBar;
	protected Rectangle loadingBarLocation;
	protected float barBorder;
	protected BitmapFont font;
	protected Rectangle loadingTextLocation;
	protected String loadingText;

	protected OrthographicCamera cam;
	protected SpriteBatch sb;

	protected String firstStateID;
	protected long enterTime;
	protected long delay;

	public LoadingScreen(StateBasedGame sbg, String firstStateID) {
		this.stateBasedGame = sbg;
		this.firstStateID = firstStateID;

		this.background = Utils.getColoredTexture(Color.BLACK);
		this.loadingBarBackground = Utils.getColoredTexture(Color.WHITE);
		this.loadingBar = Utils.getColoredTexture(Color.GREEN);
		font = new BitmapFont();

		this.barBorder = 2;
		this.delay = 100;
		loadingText = "Loading...";

		this.loadingBarLocation = new Rectangle(Gdx.graphics.getWidth() / 4, Gdx.graphics.getHeight() / 4, Gdx.graphics.getWidth() / 2,
				Gdx.graphics.getHeight() / 16);

		sb = new SpriteBatch();
		cam = new OrthographicCamera();
		cam.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		TextBounds tb = font.getBounds(loadingText);
		font.scale(loadingBarLocation.height / tb.height);
		tb = font.getBounds(loadingText);
		loadingTextLocation = new Rectangle(Gdx.graphics.getWidth() / 2 - tb.width / 2, loadingBarLocation.y + loadingBarLocation.height
				+ 100, tb.width, tb.height);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

		boolean finished = this.stateBasedGame.getAssetManager().update();

		cam.update();
		sb.setProjectionMatrix(cam.combined);
		sb.begin();
		if (System.currentTimeMillis() >= enterTime + delay) {
			sb.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
			sb.draw(loadingBarBackground, loadingBarLocation.x, loadingBarLocation.y, loadingBarLocation.width, loadingBarLocation.height);
			sb.draw(loadingBar, loadingBarLocation.x + barBorder, loadingBarLocation.y + barBorder,
					(loadingBarLocation.width - 2 * barBorder) * this.stateBasedGame.getAssetManager().getProgress(),
					loadingBarLocation.height - 2 * barBorder);
			font.draw(sb, loadingText, loadingTextLocation.x, loadingTextLocation.y);
		}
		sb.end();

		if (finished) {
			stateBasedGame.initStates();
			stateBasedGame.enterState(firstStateID);
		}
	}

	@Override
	public void resize(int width, int height) {

	}

	@Override
	public void show() {
		this.enterTime = System.currentTimeMillis();
	}

	@Override
	public void hide() {

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

	@Override
	public void onCreate(StateBasedGame sbg) {
		
		
	}

	@Override
	public void onEnter() {
		
		
	}

	@Override
	public void onLeave() {
		
	}

}
