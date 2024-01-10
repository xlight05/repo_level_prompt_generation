package org.zls.client;

import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.ImageBundle;
import com.google.gwt.user.client.ui.TreeImages;

public abstract class Tab extends Composite {
	/**
	 * An image provider to make available images to Sinks.
	 */
	public interface Images extends ImageBundle, TreeImages {
		AbstractImagePrototype gwtLogo();
	}

	/**
	 * Encapsulated information about a sink. Each sink is expected to have a
	 * static <code>init()</code> method that will be called by the kitchen sink
	 * on startup.
	 */
	public abstract static class TabInfo {
		private Tab instance;
		private String name, description;

		public TabInfo(String name, String desc) {
			this.name = name;
			description = desc;
		}

		public abstract Tab createInstance();

		public String getColor() {
			return "#2a8ebf";
		}

		public String getDescription() {
			return description;
		}

		public final Tab getInstance() {
			if (instance != null) {
				return instance;
			}
			return (instance = createInstance());
		}

		public String getName() {
			return name;
		}
	}

	/**
	 * Called just before this sink is hidden.
	 */
	public void onHide() {
	}

	/**
	 * Called just after this sink is shown.
	 */
	public void onShow() {
	}
}
