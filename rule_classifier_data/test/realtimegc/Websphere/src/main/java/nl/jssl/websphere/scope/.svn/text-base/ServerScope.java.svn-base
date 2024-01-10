package nl.jssl.websphere.scope;

public class ServerScope extends Scope {
    NodeScope parentScope;
    
    public ServerScope(String name) {
        super(name);
    }
    
    @Override
    public String toString() {
        return String.format("Server=%s",name);
    }

    @Override
    public String toExpression() {
        return String.format("/Server:%s/",name);
    }

}
