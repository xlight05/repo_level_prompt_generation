package nl.jssl.websphere.scope;

public class CellScope extends Scope{

    /**
     * a cell contains either node or cluster children
     */
    private SubScope subScope;

    public CellScope(String name){
        super(name);
    }
    
    @Override
    public String toString() {
        return "Cell=" + name + ", " + subScope.toString();
    }

    @Override
    public String toExpression() {
        return subScope.toExpression();
    }
    
    public void setClusterScope(ClusterScope clusterScope) {
        subScope = clusterScope;
        clusterScope.parentScope=this;
    }

    public void setNodeScope(NodeScope nodeScope) {
        subScope = nodeScope;
    }
    
    public boolean isCellWide() {
        return (subScope == null);
    }

}
