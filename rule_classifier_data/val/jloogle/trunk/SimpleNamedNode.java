abstract class SimpleNamedNode extends SimpleNode {

  public SimpleNamedNode(int id) {
    super(id);
  }

  public SimpleNamedNode(Parser p, int id) {
    super(p, id);
  }
  
  public final SimpleStringNode getName() {
    return (SimpleStringNode)jjtGetChild(0);
  }

}
