package nl.jssl.websphere.resources.jdbc;

import nl.jssl.websphere.scope.Scope;
import nl.jssl.websphere.scope.ScopeContainer;

public class DataSource implements ScopeContainer {
    private Scope scope;
    private JDBCProvider jdbcProvider;

    
    public Scope getScope() {
        return scope;
    }

}
