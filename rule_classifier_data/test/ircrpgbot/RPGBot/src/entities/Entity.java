/*
 * @author Kyle Kemp
 */
package entities;

import java.util.concurrent.CopyOnWriteArrayList;

import shared.Observer;
import shared.Subject;
import spells.Spell;

public abstract class Entity implements Subject {

	private String name;

	private CopyOnWriteArrayList<Observer> observers = new CopyOnWriteArrayList<Observer>();

	@Override
	public void addObserver(Observer o) {
		// only one observer allowed
		clearObservers();
		if (!observers.contains(o)) {
			observers.add(o);
		}
	}

	@Override
	public void clearObservers() {
		for (Observer o : observers) {
			removeObserver(o);
		}
	}

	public String getName() {
		return name;
	}

	/**
	 * @return the observers
	 */
	public CopyOnWriteArrayList<Observer> getObservers() {
		return observers;
	}

	@Override
	public void notifyObservers(int state) {
		notifyObservers(state, (Mob) this, null, null, 0);
	}

	public void notifyObservers(int state, Mob source, Mob target, Spell spell,
			int damage) {
		for (Observer o : observers) {
			if (o != null) {
				o.update(state, source, target, spell, damage);
			}
		}
	}

	@Override
	public void removeObserver(Observer o) {
		observers.remove(o);
	}

	@Override
	public void sendState(int state) {
		notifyObservers(state);
	}

	public void sendState(int state, Mob src) {
		notifyObservers(state, src, null, null, 0);
	}

	public void sendState(int state, Mob src, Mob trg) {
		notifyObservers(state, src, trg, null, 0);
	}

	public void sendState(int state, Mob src, Mob trg, int damage) {
		notifyObservers(state, src, trg, null, damage);
	}

	public void sendState(int state, Mob src, Mob trg, Spell s) {
		notifyObservers(state, src, trg, s, 0);
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return getName();
	}
}
