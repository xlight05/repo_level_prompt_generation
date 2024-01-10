package nl.jssl.websphere.environment;

import nl.jssl.websphere.scope.CellScope;
import nl.jssl.websphere.scope.NodeScope;
import nl.jssl.websphere.scope.Scope;
import nl.jssl.websphere.scope.ServerScope;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class NameSpaceBindingTests {
    private Scope scope;

    @Before
    public void createScope() {
        CellScope cellScope = new CellScope("cell1");
        NodeScope nodeScope = new NodeScope("node1");
        ServerScope serverScope = new ServerScope("server1");
        nodeScope.setServerScope(serverScope);
        cellScope.setNodeScope(nodeScope);
        scope = cellScope;
    }
    
    @Test
    public void correctJython() {
        NameSpaceBinding binding = new NameSpaceBinding(scope);
        binding.setJndiName("jndi/name");
        binding.setStringToBind("someString");

        String expected =
            "node = AdminConfig.getid(\"/Node:node1/Server:server1/\")\n"
                + "if len(node) == 0:\n"
                + "   print \"createBindings: could not find a Node object called \"+Cell=cell1, Node=node1, Server=server1\n"
                + "   return\n"
                + "AdminConfig.create('StringNameSpaceBinding', node, [['name', 'jndi.name'],  ['nameInNameSpace', 'jndi/name'], ['stringToBind', \"someString\"]])";
        Assert.assertEquals(expected, binding.create());
    }
}
