/* Generated By:JJTree: Do not edit this line. ASTName.java */

public class ASTName extends SimpleNamedNode {
  public ASTName(int id) {
    super(id);
  }

  public ASTName(Parser p, int id) {
    super(p, id);
  }

  public void accept(Visitor visitor) {
    visitor.visit(this);
  }

}
