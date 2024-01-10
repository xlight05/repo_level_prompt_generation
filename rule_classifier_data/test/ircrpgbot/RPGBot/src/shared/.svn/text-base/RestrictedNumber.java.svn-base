/*
 * @author Kyle Kemp
 */
package shared;

public class RestrictedNumber {

	private int initial, maximum, minimum, current, boosted;

	private String name;

	public RestrictedNumber(int minimum, int current, int maximum) {
		setMinimum(minimum);
		setMaximum(maximum);
		setInitial(current);
		setCurrent(current);
	}

	public RestrictedNumber(String name, int minimum, int current, int maximum) {
		this(minimum, current, maximum);
		setName(name);
	}

	public int asPercent() {
		return (int) ((double) getTotal() / maximum * 100);
	}
	
	public boolean atMax() {
		return maximum == current;
	}

	public boolean atMin() {
		return minimum == current;
	}

	public void decreaseBonus(int number){
		increaseBonus(-number);
	}
	
	public void decrease(int number) {
		increase(-number);
	}

	public int getBoosted() {
		return boosted;
	}

	public int getCurrent() {
		return current;
	}

	public int getInitial() {
		return initial;
	}

	public int getMaximum() {
		return maximum;
	}

	public int getMinimum() {
		return minimum;
	}

	public String getName() {
		return name;
	}

	public int getTotal() {
		return boosted + current;
	}

	public boolean greaterThan(int number) {
		return getTotal() > number;
	}

 	public boolean greaterThanPercent(int perc) {
		return asPercent() > perc;
	}

 	public void increaseBonus(int number){
 		setBoosted(boosted+number);
 	}
 	
	public void increase(int number) {
		setCurrent(current + number);
	}

	public void increaseAndBound(int number) {
		setMaximum(getMaximum() + number);
		setCurrent(getCurrent() + number);
	}

	public boolean lessThan(int number) {
		return !greaterThan(number);
	}

	public boolean lessThanPercent(int perc) {
		return !greaterThanPercent(perc);
	}

	public void setBoosted(int boosted) {
		this.boosted = boosted;
	}

	public void setCurrent(int current) {
		current = Math.min(current, maximum);
		current = Math.max(current, minimum);
		this.current = current;
	}

	public void setInitial(int initial) {
		this.initial = initial;
	}

	public void setMaximum(int maximum) {
		this.maximum = maximum;
	}

	public void setMinimum(int minimum) {
		this.minimum = minimum;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String toHalfString() {
		return getName() + ": " + getCurrent() + (getBoosted()!=0 ? " ("+getBoosted()+")" : "");
	}

	public void toMaximum() {
		setCurrent(getMaximum());
	}

	public void toMinimum() {
		setCurrent(getMinimum());
	}

	@Override
	public String toString() {
		return name + ": " + current + "/" + maximum;
	}
}
