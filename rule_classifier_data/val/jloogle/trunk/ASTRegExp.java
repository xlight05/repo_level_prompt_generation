import java.util.regex.*;

public class ASTRegExp extends SimpleStringNode {

  ASTRegExp(int id){
    super(id);
  }

  public String toString() {
    return "RegExp: " + getName();
  }

  public void accept(Visitor visitor) {
    visitor.visit(this);
  }

  public boolean matches(String s) {
    return s != null && Pattern.matches(getName(),s);
  }

}