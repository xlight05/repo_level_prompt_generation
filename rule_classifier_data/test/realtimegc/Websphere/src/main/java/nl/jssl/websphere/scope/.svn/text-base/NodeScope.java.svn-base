package nl.jssl.websphere.scope;

public class NodeScope extends SubScope {
    
    private ServerScope serverScope;

    public NodeScope(String name) {
        super(name);
    }
    
    public boolean isNodeWide() {
        return (serverScope == null);
    }

    @Override
    public String toString() {
        return "Node="+name+", "+serverScope.toString();
    }
    
    public void setServerScope(ServerScope serverScope) {
        this.serverScope = serverScope;
        serverScope.parentScope=this;
    }

    @Override
    public String toExpression() {
        return "/Node:"+name+serverScope.toExpression();
    }
    
    
}
