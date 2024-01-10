package nl.jssl.websphere.server;

import java.util.List;

import nl.jssl.websphere.CustomProperty;

public class JVM {
    private String classpath;
    private MemorySettings memorySettings;
    
    private String debugArguments;
    private String genericJvmArguments;
    private boolean disableJit=false;
    private List<CustomProperty> customProperties;
}
