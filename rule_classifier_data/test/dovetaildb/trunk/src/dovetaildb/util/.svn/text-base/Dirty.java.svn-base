package dovetaildb.util;

public class Dirty {
	public interface Able {
		public void setDirtyListener(Listener dirtylListener);
	}
	public interface Listener {
		public void setDirty();
	}
	public static abstract class Abstract implements Able, Listener {
		protected Listener dirtyListener = null;
		public void setDirtyListener(Listener dirtyListener) {
			this.dirtyListener = dirtyListener;
		}
		public void setDirty() {
			if (dirtyListener != null) dirtyListener.setDirty();
		}
	}
}