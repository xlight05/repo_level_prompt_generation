import nl.jssl.websphere.scope.CellScope;
import nl.jssl.websphere.scope.NodeScope;
import nl.jssl.websphere.scope.ServerScope;


public class ScopeTest {
	@org.junit.Test
    public void correctScopeString(){
        CellScope cellScope=new CellScope("cell1");
        NodeScope nodeScope = new NodeScope("node1");
        ServerScope serverScope=new ServerScope("server1");
        nodeScope.setServerScope(serverScope);
        cellScope.setNodeScope(nodeScope);
        
        assert "Cell=cell1, Node=node1, Server=server1".equals(cellScope.toString());
    }
    
    
}
