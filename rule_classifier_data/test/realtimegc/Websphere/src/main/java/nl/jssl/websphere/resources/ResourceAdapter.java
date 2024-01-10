package nl.jssl.websphere.resources;

import nl.jssl.websphere.scope.Scope;
import nl.jssl.websphere.scope.ScopeContainer;

public class ResourceAdapter implements ScopeContainer{
    private Scope scope;

    public Scope getScope() {
        return scope;
    }
}
