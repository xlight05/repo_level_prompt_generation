package nl.jssl.websphere.resources.mail;

import nl.jssl.websphere.scope.Scope;
import nl.jssl.websphere.scope.ScopeContainer;

public class Mailprovider implements ScopeContainer {
    private Scope scope;

    public Scope getScope() {
        return scope;
    }

}
