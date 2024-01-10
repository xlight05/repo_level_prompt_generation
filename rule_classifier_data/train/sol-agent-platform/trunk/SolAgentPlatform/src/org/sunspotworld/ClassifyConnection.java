/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sunspotworld;

import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.TreeMap;

/**
 *
 * @author Jose Luis
 */
public abstract class ClassifyConnection {
    public static TreeMap<String,String> classifyContent(String cont){
        TreeMap<String,String> res = new TreeMap<String,String>();

        StringTokenizer st = new StringTokenizer(cont,"##");
        res.put("agent",st.nextToken());
        res.put("mas",st.nextToken());
        res.put("cat",st.nextToken());

        return res;
    }
}
