/* Generated By:JJTree: Do not edit this line. ASTNeg.java */

public class ASTNeg extends SimpleNode {
  public ASTNeg(int id) {
    super(id);
  }

  public ASTNeg(Parser p, int id) {
    super(p, id);
  }

  public void accept(Visitor visitor) {
    visitor.visit(this);
  }

}