/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sunspotworld;

/**
 *
 * @author Jose Luis
 */
public class Events {
    static public final String OKTEMP = "Temperature OK";
    static public final String LHIGHTEMP = "Temperature is a little bit high"; //Little High Temperature
    static public final String HIGHTEMP = "High temperature";
    static public final String FIRE = "FIRE!";
    
    static public final String OKNOISE = "Noise OK";
    static public final String LHIGHNOISE = "Noise is a little bit high"; //Little High Noise
    static public final String HIGHNOISE = "Too noisy";
    static public final String SHOWCASEBROKEN = "A showcase was broken";
    
    //static public final String LOWATTENDANCE = "Level of attendance is low";
    static public final String MEDIUMATTENDANCE = "Level of attendance is medium";
    static public final String HIGHATTENDANCE = "Level of attendance is high";
    static public final String DOORATTENDANCE = "A new visitor came in"; //Cuando cierras una puerta por efecto hall se registra un nuevo visitante
    
    static public final String CLOSINGDOORS = "The doors are closing";
    static public final String ROBBERY = "ROBBERY COMMITTED!";
    static public final String OK = "Everything is OK";
    static public final String MEDICALASSISTANCE = "MEDICAL ASSISTANCE!";
}
