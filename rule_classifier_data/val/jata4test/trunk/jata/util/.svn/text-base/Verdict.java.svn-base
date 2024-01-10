package jata.util;

public class Verdict {

	public Verdict(){
		value = VerdictEnum.none;
	}
	protected VerdictEnum value;

	public VerdictEnum getValue() {
		return value;
	}

	public void setValue(VerdictEnum value) {
		if (value == VerdictEnum.error) {
			this.value = VerdictEnum.error;
		} else if ((this.value == VerdictEnum.fail)
				|| (this.value == VerdictEnum.error)) {
		} else if (this.value == VerdictEnum.none) {
			this.value = value;
		} else if (this.value == VerdictEnum.inconc) {
			this.value = (value == VerdictEnum.fail) ? value
					: VerdictEnum.inconc;
		} else {
			this.value = (value == VerdictEnum.none) ? this.value : value;
		}
	}
	public void Pass() { setValue ( VerdictEnum.pass); }
    public void Fail() { setValue ( VerdictEnum.fail); }
    public void None() { setValue ( VerdictEnum.none); }
    public void Inconc() { setValue ( VerdictEnum.inconc); }
    public void Error() { setValue ( VerdictEnum.error); }
    public boolean isPass() { return value == VerdictEnum.pass; }
    public boolean isFail() { return value == VerdictEnum.fail; }
    public boolean isNone() { return value == VerdictEnum.none; }
    public boolean isInconc() { return value == VerdictEnum.inconc; }
    public boolean isError() { return value == VerdictEnum.error; }
	
    public String toString(){
    	if (value == VerdictEnum.pass) return "pass";
    	if (value == VerdictEnum.fail) return "fail";
    	if (value == VerdictEnum.none) return "none";
    	if (value == VerdictEnum.inconc) return "inconc";
    	return "error";
    }
}
