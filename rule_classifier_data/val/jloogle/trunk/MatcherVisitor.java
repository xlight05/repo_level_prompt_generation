import java.lang.reflect.*;

/**
 * Responsible for matching methods against method patterns.
 */
final class MatcherVisitor implements Visitor {

  private final Method method;
  private boolean result = false;

  MatcherVisitor(Method method) {
    this.method = method;
  }

  // --------------------------------------------------
  // Interface
  // --------------------------------------------------

  public boolean matches(ASTStart node) {
    result = false;
    try {
      node.accept(this);
    } catch (DoneException done) {}
    return result;
  }

  // --------------------------------------------------
  // Visitor interface
  // --------------------------------------------------

  public void visit(ASTAnd node) {
    Node l = node.jjtGetChild(0);
    l.accept(this);
    if (!result) return;
    boolean b = result;
    result = false;
    Node r = node.jjtGetChild(1);
    r.accept(this);
    result = result && b;
  }

  public void visit(ASTArg node) {
    SimpleStringNode ssn = node.getName();
    for (Class t : method.getParameterTypes()) {
      if (ssn.matches(Util.toString(t))) {
        result = true;
      }
    }
  }

  public void visit(ASTId node) {
    // TODO
  }

  public void visit(ASTName node) {
    if (node.getName().matches(method.getName())) {
      result = true;
    }
  }

  public void visit(ASTOr node) {
    Node l = node.jjtGetChild(0);
    l.accept(this);
    if (result) return;
    Node r = node.jjtGetChild(1);
    r.accept(this);
  }

  public void visit(ASTRegExp node) {
    // TODO
  }

  public void visit(ASTReturns node) {
    if (node.getName().matches(Util.toString(method.getReturnType()))) {
      result = true;
    }
  }

  public void visit(ASTStart node) {
    visitAll(node);
  }

  public void visit(ASTThrows node) {
    // TODO
  }

  public void visit(ASTImports node) {
    // TODO
  }

  public void visit(ASTNeg node) {
    node.jjtGetChild(0).accept(this);
    result = !result;
  }

  public void visit(ASTTrue node) {
    result = true;
  }

  public void visit(ASTFalse node) {
    result = false;
  }


  // --------------------------------------------------
  // Private
  // --------------------------------------------------

  private void visitAll(Node node) {
    for (int i=0, N=node.jjtGetNumChildren(); i<N; i++) {
      node.jjtGetChild(i).accept(this);
    }
  }

  private static class DoneException extends RuntimeException {}

  private void done(boolean result) {
    this.result = result;
    throw new DoneException();
  }

  private void done() {
    done(true);
  }
}
