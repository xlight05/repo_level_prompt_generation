package nl.jssl.websphere.scope;


public class ClusterScope extends SubScope {

    public ClusterScope(String name) {
        super(name);
    }

    @Override
    public String toString() {
        return String.format("Cluster=%s",name);
    }

    @Override
    public String toExpression() {
        return String.format("/Cluster:%s/",name);
    }
    
}
