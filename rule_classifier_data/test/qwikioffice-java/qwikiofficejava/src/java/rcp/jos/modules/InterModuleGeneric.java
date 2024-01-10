/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package rcp.jos.modules;

import rcp.json.core.JSONObject;

/**
 *
 * @author rcrespo
 */
public interface InterModuleGeneric {

    public JSONObject runAction(JSONObject _params,String _action) throws Exception;
    
}
