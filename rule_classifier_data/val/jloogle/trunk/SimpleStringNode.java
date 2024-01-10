abstract class SimpleStringNode extends SimpleNode {

  private String name;

  SimpleStringNode(int id){
    super(id);
  }

  public final void setName(String n) {
    name = n;
  }

  public final String getName() {
    return name;
  }
  
  public abstract boolean matches(String s);
}