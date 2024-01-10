package gloodb.associations;

import gloodb.Identity;
import gloodb.SortingCriteria;

import java.io.Serializable;

public class SortedSerializable implements Serializable {
    private static final long serialVersionUID = 800718573162447315L;
    
    @Identity
    Serializable id;

    String value;
    
    SortedSerializable(Serializable id, String value) {
        this.id = id;
        this.value = value;
    }
    
    @SortingCriteria("test")
    String getValue(int parameter) {
        return value + "." + parameter;
    }
}
