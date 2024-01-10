package org.rsbot.script.wrappers;

public class RSNPC extends RSCharacter {
	private final org.rsbot.accessors.RSNPC npc;

	public RSNPC(final org.rsbot.accessors.RSNPC npc) {
		super(npc);
		c = npc;
		this.npc = npc;
	}

	public String[] getActions() {
		final org.rsbot.accessors.RSNPCDef def = getDefInternal();
		if (def != null)
			return def.getActions();
		return new String[0];
	}

	org.rsbot.accessors.RSNPCDef getDefInternal() {
		if (npc == null)
			return null;
		else
			return npc.getRSNPCDef();
	}

	public int getID() {
		final org.rsbot.accessors.RSNPCDef def = getDefInternal();
		if (def != null)
			return def.getType();
		return -1;
	}

	@Override
	public String getName() {
		final org.rsbot.accessors.RSNPCDef def = getDefInternal();
		if (def != null)
			return def.getName();
		return "";
	}

	@Override
	public int getLevel() {
		final org.rsbot.accessors.RSNPCDef def = getDefInternal();
		if (def != null)
			return def.getLevel();
		return -1;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		for (final String act : getActions()) {
			sb.append(act);
			sb.append(",");
		}
		if (sb.length() > 0) {
			sb.setLength(sb.length() - 1);
		}
		return "NPC[" + getName() + "],actions=[" + sb.toString() + "]" + super.toString();
	}
}
