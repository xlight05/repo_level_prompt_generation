/*
 * @author Kyle Kemp
 */
package battle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import org.jibble.pircbot.Colors;

import shared.Constants;
import shared.Observer;
import shared.Subject;
import bot.Bot;

import commands.Command;

import entities.Mob;

public class Battle implements Subject, Constants {

	private class MonitorThread extends Thread {

		Bot bot;
		Battle battle;
		Mob m;
		boolean canRun = true;

		public MonitorThread(Battle battle, Bot b) {
			super("Waiting for " + battle.getCurTurn());
			this.bot = b;
			this.battle = battle;
			this.m = battle.getCurTurn();
		}

		@Override
		public void run() {
			for (int x = 0; x < 240; x++) {
				if (!m.equals(battle.getCurTurn()) || !canRun) {
					return;
				}
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			for (Command c : Bot.getCommands()) {
				if (c.getAliases().contains("defend")) {
					c.execute(m, Bot.channel, bot.reverseMobFind(m), "");
					battle.changeTurn();
					break;
				}
			}
		}
	}

	public Bot bot;
	String channel;
	private Mob curTurn;
	private MonitorThread timer;
	private int consolationExp = 0;

	ArrayList<Observer> observers = new ArrayList<Observer>();

	ArrayList<Mob> queue = new ArrayList<Mob>();

	private ArrayList<Team> teams = new ArrayList<Team>();

	public Battle(Bot b, String channel) {
		this.bot = b;
		this.channel = channel;
	}

	@Override
	public void addObserver(Observer o) {
		if (!observers.contains(o)) {
			observers.add(o);
		}
	}

	public void addTeam(Team t) {
		if (t.getMembers().isEmpty()) {
			return;
		}
		teams.add(t);
		addObserver(t);
	}

	private void changeQueue() {
		for (Team t : teams) {
			for (Mob e : t.getMembers()) {
				if (queue.contains(e) && e.getCharacterClass().getHp().atMin()) {
					queue.remove(e);
				}
			}
		}
		
		Collections.sort(queue, new Comparator<Mob>() {
			@Override
			public int compare(Mob f1, Mob f2) {
				return f2.getCharacterClass().getSpeed().getCurrent()
						- f1.getCharacterClass().getSpeed().getCurrent();
			}
		});

		if (checkVictory()) {
			endFight();
		}
	}

	public void changeTurn() {
		changeQueue();
		if (queue.isEmpty()) {
			return;
		}
		if (getCurTurn() != null) {
			getCurTurn().sendState(TURN_END, getCurTurn());
			if (queue.indexOf(getCurTurn()) < queue.size() - 1) {
				setCurTurn(queue.get(queue.indexOf(getCurTurn()) + 1));
			} else {
				sendState(ROUND_END);
				
				changeQueue();

				bot.sendMessage(Bot.channel, Colors.BOLD+"Round over! Turn order: " + queue);
				setCurTurn(queue.get(0));
				sendState(ROUND_START);
			}
		} else {
			setCurTurn(queue.get(0));
			sendState(ROUND_START);
		}
		getCurTurn().sendState(TURN_START, getCurTurn());
	}

	private boolean checkVictory() {
		Team victor = queue.get(0).getTeam();
		for (Mob m : queue) {
			if (!m.getTeam().equals(victor)) {
				return false;
			}
		}
		return true;
	}

	@Override
	public void clearObservers() {
		for (Observer o : observers) {
			removeObserver(o);
		}

	}

	private void endFight() {
		bot.sendMessage(channel, Colors.BOLD+"Victory for " + queue.get(0).getTeam() + "!");
		for (Team t : teams) {
			bot.getTeams().remove(t.getName());
		}
		sendState(FIGHT_END);
		bot.save();
		queue.clear();
		timer.canRun = false;
		setCurTurn(null);
	}

	@Override
	protected void finalize() throws Throwable {
		System.out.println("Garbage collected a battle, broski");
		super.finalize();
	}

	public int getConsolationExp() {
		return consolationExp;
	}

	/**
	 * @return the curTurn
	 */
	public Mob getCurTurn() {
		return curTurn;
	}

	public ArrayList<Mob> getQueue() {
		return queue;
	}

	/**
	 * @return the teams
	 */
	public ArrayList<Team> getTeams() {
		return teams;
	}

	public void initialize() {
		initQueue();
		bot.sendMessage(channel, Colors.BOLD + "Turn order: " + queue.toString());
		setCurTurn(queue.get(0));
		sendState(FIGHT_START);
		sendState(ROUND_START);
	}

	public void initQueue() {
		// queue.clear();
		for (Team t : teams) {
			for (Mob e : t.getMembers()) {
				consolationExp += e.getCharacterClass().getLevel().getTotal() * 50;
				e.getCharacterClass().heal();
				if (!queue.contains(e)
						&& !e.getCharacterClass().getHp().atMin()) {
					queue.add(e);
				}
			}
		}
		consolationExp /= 10;
		Collections.sort(queue, new Comparator<Mob>() {
			@Override
			public int compare(Mob f1, Mob f2) {
				return f2.getCharacterClass().getSpeed().getCurrent()
						- f1.getCharacterClass().getSpeed().getCurrent();
			}
		});

	}

	@Override
	public void notifyObservers(int state) {
		for (Observer o : observers) {
			o.update(this, state);
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

	public void setConsolationExp(int consolationExp) {
		this.consolationExp = consolationExp;
	}

	/**
	 * @param curTurn
	 *            the curTurn to set
	 */
	public void setCurTurn(Mob curTurn) {
		this.curTurn = curTurn;
		if (timer != null) {
			timer.canRun = false;
		}
		if (curTurn != null) {
			timer = new MonitorThread(this, bot);
			timer.start();
		}
		bot.sendMessage(channel, Colors.BOLD + "Current turn: " + this.curTurn.toBattleString());
	}

}
