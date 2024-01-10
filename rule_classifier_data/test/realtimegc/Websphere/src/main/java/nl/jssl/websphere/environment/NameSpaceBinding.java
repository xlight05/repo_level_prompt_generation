package nl.jssl.websphere.environment;

import static nl.jssl.websphere.Output.template;
import nl.jssl.websphere.ValueSet;
import nl.jssl.websphere.scope.Scope;
import nl.jssl.websphere.scope.ScopeContainer;
import nl.jssl.websphere.util.Util;

import com.googlecode.aluminumproject.context.Context;

public class NameSpaceBinding implements ScopeContainer {

    private Scope scope;
    private String jndiName;
    private String stringToBind;

    public NameSpaceBinding(Scope scope) {
        this.scope = scope;
    }

    public Scope getScope() {
        return scope;
    }

    public String create() {
    	return template("nl/jssl/websphere/environment/createNameSpaceBinding.xml").addValues(new ValueSet() {
			public void set(Context context) {
				context.setVariable("name", Util.slash2Dot(jndiName));
		    	context.setVariable("jndiName", jndiName);
		    	context.setVariable("stringToBind", stringToBind);
			}
		}).getText();
    }

    public void setJndiName(String jndiName) {
        this.jndiName = jndiName;
    }

    void setStringToBind(String stringToBind) {
        this.stringToBind = stringToBind;
    }
}
