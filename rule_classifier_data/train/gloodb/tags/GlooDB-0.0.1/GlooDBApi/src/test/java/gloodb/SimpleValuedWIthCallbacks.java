package gloodb;

import java.io.Serializable;

public class SimpleValuedWIthCallbacks extends SimpleValued {
    private static final long serialVersionUID = 8907972554406861861L;

    public SimpleValuedWIthCallbacks(Serializable id) {
        super(id);
    }

    @PreCreate
    @PreUpdate
    @PreRemove
    @PostRestore
    @PostCreate
    @PostUpdate
    @PostRemove
    void callback(Repository repositor) {
        
    }
    
}
