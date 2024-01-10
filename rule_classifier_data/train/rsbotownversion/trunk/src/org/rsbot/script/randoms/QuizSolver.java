package org.rsbot.script.randoms;

import org.rsbot.script.Random;
import org.rsbot.script.ScriptManifest;
import org.rsbot.script.wrappers.RSInterface;
import org.rsbot.script.wrappers.RSNPC;

@ScriptManifest(authors = {"PwnZ"}, name = "Quiz Solver", version = 1.0)
public class QuizSolver extends Random {

	public class QuizQuestion {
		int ID_One;
		int ID_Two;
		int ID_Three;
		int answer;

		public QuizQuestion(final int One, final int Two, final int Three) {
			ID_One = One;
			ID_Two = Two;
			ID_Three = Three;
		}

		public boolean activateCondition() {
			if (ID_to_Slot(ID_One) != -1) {
				if (ID_to_Slot(ID_Two) != -1) {
					if (ID_to_Slot(ID_Three) != -1)
						return true;
				}
			}

			return false;
		}

		public boolean arrayContains(final int[] arr, final int i) {
			boolean returnt = false;
			for (final int num : arr) {
				if (num == i) {
					returnt = true;
				}
			}

			return returnt;
		}

		public boolean clickAnswer() {
			answer = -1;
			int count = 0;

			for (int j = 0; j < items.length; j++) {
				if (arrayContains(items[j], ID_One)) {
					log.info("Slot 1: " + names[j]);
					count++;
				}
				if (arrayContains(items[j], ID_Two)) {
					log.info("Slot 2: " + names[j]);
					count++;
				}
				if (arrayContains(items[j], ID_Three)) {
					log.info("Slot 3: " + names

							[j]);
					count++;
				}

				if (count >= 2) {
					log.info("Type Found: " + names[j]);
					answer = j;
					break;
				}
			}

			if (answer != -1) {
				int slot = -1;
				if ((slot = findNotInAnswerArray()) != -1)
					return atSlot(slot);
				else {
					log.info("findNotInAnswerArray() fail.");
					return false;
				}
			} else {
				log.info("answer fail.");
				return false;
			}

		}

		public int findNotInAnswerArray() {
			if (!arrayContains(items[answer], ID_One))
				return 1;
			else if (!arrayContains(items[answer], ID_Two))
				return 2;
			else if (!arrayContains(items[answer], ID_Three))
				return 3;
			else
				return -1;
		}
	}

	public RSInterface quizInterface = getInterface(191);
	public int[] Fish = {1715, 1414, 1753, 1383};
	public int[] Jewelry = {640, 1062, 793, 856};
	public int[] Weapons = {1510, 1218, 1331, 1996};
	public int[] Farming = {908, 1280};
	public int[][] items = {Fish, Jewelry, Weapons, Farming};

	public String[] names = {"Fish", "Jewelry", "Weapons", "Farming"};

	@Override
	public boolean activateCondition() {
		final RSNPC quizMaster = getNearestNPCByName("Quiz Master");
		return quizMaster != null;
	}

	public void atRandom() {
		atSlot(random(1, 3));
	}

	public boolean atSlot(final int slot) {
		switch (slot) {
			case 1:
				return atInterface(quizInterface.getChild(3));
			case 2:
				return atInterface(quizInterface.getChild(4));
			case 3:
				return atInterface(quizInterface.getChild(5));
			default:
				return false;
		}
	}

	public int ID_to_Slot(final int id) {
		if (Slot_to_ID(1) == id)
			return 1;
		else if (Slot_to_ID(2) == id)
			return 2;
		else if (Slot_to_ID(3) == id)
			return 3;
		else
			return -1;
	}

	@Override
	public int loop() {
		final RSNPC quizMaster = getNearestNPCByName("Quiz Master");
		if (quizMaster == null)
			return -1;

		if (Slot_to_ID(1) != -1) {
			log.info("Question detected.");
			final QuizQuestion question = new QuizQuestion(Slot_to_ID(1), Slot_to_ID(2), Slot_to_ID(3));
			if (question.clickAnswer())
				return random(2200, 3000);
			else {
				log.info("Trying Random Answer");
				atRandom();
				return random(1200, 2200);
			}
		} else {
			if (clickContinue())
				return random(800, 1200);
		}
		return random(1200, 2000);
	}

	public int Slot_to_ID(final int slot) {
		switch (slot) {
			case 1:
				return quizInterface.getChild(6).getModelZoom();
			case 2:
				return quizInterface.getChild(7).getModelZoom();
			case 3:
				return quizInterface.getChild(8).getModelZoom();
			default:
				return -1;
		}
	}
}
