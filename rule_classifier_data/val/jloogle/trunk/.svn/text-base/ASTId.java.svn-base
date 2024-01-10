public class ASTId extends SimpleStringNode {

  ASTId(int id){
    super(id);
  }

  public String toString() {
    return "Identifier: " + getName();
  }

  public void accept(Visitor visitor) {
    visitor.visit(this);
  }

  public boolean matches(String s) {
    return s != null && s.equals(getName());
  }

}