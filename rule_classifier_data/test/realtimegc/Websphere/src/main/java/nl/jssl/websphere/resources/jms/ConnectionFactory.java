package nl.jssl.websphere.resources.jms;

import nl.jssl.websphere.scope.Scope;
import nl.jssl.websphere.scope.ScopeContainer;

public class ConnectionFactory implements ScopeContainer{
    private Scope scope;

   
    public Scope getScope() {
        return scope;
    }
}