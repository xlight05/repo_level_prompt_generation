/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sunspotworld;

/**
 *
 * @author Jose Luis
 */
public class Status {
    static public final String OK = "INFORMATION"; //Nivel OK
    static public final String CAUTION = "CAUTION"; //Nivel de problemas 1
    static public final String URGENCY = "URGENCY"; //Nivel de problemas 2
    static public final String EMERGENCY = "EMERGENCY"; //Nivel de problemas 3
    
    public static void addStatus(AuroraMessage msg){
        String event = msg.getContent();
        if (event.equals(Events.FIRE) || event.equals(Events.MEDICALASSISTANCE) || event.equals(Events.ROBBERY)){
            msg.setLanguage(EMERGENCY);
        }else if (event.equals(Events.HIGHTEMP) || event.equals(Events.HIGHNOISE) || event.equals(Events.HIGHATTENDANCE)
                || event.equals(Events.SHOWCASEBROKEN)){
            msg.setLanguage(URGENCY);
        }else if (event.equals(Events.LHIGHTEMP) || event.equals(Events.LHIGHNOISE) || event.equals(Events.MEDIUMATTENDANCE)
                || event.equals(Events.CLOSINGDOORS)){
            msg.setLanguage(CAUTION);
        }else if (event.equals(Events.DOORATTENDANCE) || event.equals(Events.OKNOISE) || event.equals(Events.OKTEMP)
                || event.equals(Events.OK)){
            msg.setLanguage(OK);
        }
    }
}
