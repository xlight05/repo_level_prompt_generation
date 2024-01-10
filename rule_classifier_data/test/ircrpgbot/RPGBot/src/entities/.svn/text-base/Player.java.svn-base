/*
 * @author Kyle Kemp
 */
package entities;

import org.jibble.pircbot.Colors;

public class Player extends Mob {

	private String createdDate;

	private String password;

	private String createdBy;

	public String getCreatedBy() {
		return createdBy;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public String getPassword() {
		return password;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toBattleString() {
		return getName() + " [" + Colors.RED + getCharacterClass().getHp() +Colors.NORMAL+Colors.BOLD+ "] ["
				+ Colors.BLUE+getCharacterClass().getOther()+Colors.NORMAL+Colors.BOLD+ "]";
	}

	@Override
	public String toString() {
		return getName();
	}
}
