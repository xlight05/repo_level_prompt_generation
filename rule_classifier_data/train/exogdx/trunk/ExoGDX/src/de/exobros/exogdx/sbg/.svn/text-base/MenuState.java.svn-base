package de.exobros.exogdx.sbg;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;

public abstract class MenuState extends GameState2D {

	private MenuSetup setup;
	private Table menu;

	protected ArrayList<MenuEntry> entryList = new ArrayList<MenuEntry>();

	@Override
	public void init() {
		this.setup = this.getMenuSetup();
		if (setup == null) {
			try {
				throw new Exception("Menu \"" + this.getClass().getName() + "\" not set up!");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		this.initEntryList();
		this.updateButtons();
	}

	private void updateButtons() {
		if (menu != null) {
			gui.removeActor(menu);
		}
		menu = new Table();
		menu.setLayoutEnabled(false);
		menu.setBounds((Gdx.graphics.getWidth() * (1 - setup.width) / 2), Gdx.graphics.getHeight() * (1 - setup.height) / 2, Gdx.graphics.getWidth() * setup.width,
				Gdx.graphics.getHeight() * setup.height);
		gui.add(menu);

		final MenuEntry[] ea = entryList.toArray(new MenuEntry[0]);
		float bheight = menu.getHeight() / ea.length;
		float bwidth = menu.getWidth();
		for (int a = 0; a < ea.length; a++) {
			final int b = a;
			TextButtonStyle tbs = new TextButtonStyle();
			TextureRegion tex = ea[a].buttonTexture;
			tbs.up = new TextureRegionDrawable(tex.split(tex.getRegionWidth(), tex.getRegionHeight() / 2)[0][0]);
			tbs.down = new TextureRegionDrawable(tex.split(tex.getRegionWidth(), tex.getRegionHeight() / 2)[1][0]);
			tbs.font = ea[a].font;

			TextButton tb = new TextButton(ea[a].name, tbs);
			tb.setPosition(0, bheight * (ea.length - 1 - a));
			tb.setSize(bwidth, bheight - setup.padding);
			tb.addListener(new ChangeListener() {

				@Override
				public void changed(ChangeEvent event, Actor actor) {
					ea[b].action.run();
				}
			});

			menu.add(tb);
		}
	}

	@Override
	public void update(float delta) {
		
	}

	@Override
	public void draw(float delta, SpriteBatch sb) {
		sb.draw(setup.background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
	}

	public abstract MenuSetup getMenuSetup();

	public abstract void initEntryList();

}
