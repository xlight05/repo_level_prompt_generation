package nl.jssl.websphere.util;

import nl.jssl.websphere.scope.Scope;

public class Util {

    public static String slash2Dot(String text) {
        return text.replaceAll("\\/", ".");
    }

    public static String nodeFromAdminConfig(Scope scope) {
        return String.format("node = AdminConfig.getid(\"%s\")\nif len(node) == 0:\n"
            + "   print \"createBindings: could not find a Node object called \"+" + scope.toString() + "\n"
            + "   return\n", scope.toExpression());
    }

}
