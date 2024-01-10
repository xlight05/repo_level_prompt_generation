//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.1-b02-fcs 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2009.04.16 at 10:43:04 ���� CST 
//


package com.xiaonei.api.schema;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementRefs;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence maxOccurs="unbounded" minOccurs="0">
 *         &lt;element name="notifications_per_day" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="requests_per_day" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "notificationsPerDayAndRequestsPerDay"
})
@XmlRootElement(name = "admin_getAllocation_response")
public class AdminGetAllocationResponse {

    @XmlElementRefs({
        @XmlElementRef(name = "requests_per_day", namespace = "http://api.xiaonei.com/1.0/", type = JAXBElement.class),
        @XmlElementRef(name = "notifications_per_day", namespace = "http://api.xiaonei.com/1.0/", type = JAXBElement.class)
    })
    protected List<JAXBElement<Integer>> notificationsPerDayAndRequestsPerDay;

    /**
     * Gets the value of the notificationsPerDayAndRequestsPerDay property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the notificationsPerDayAndRequestsPerDay property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getNotificationsPerDayAndRequestsPerDay().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link JAXBElement }{@code <}{@link Integer }{@code >}
     * {@link JAXBElement }{@code <}{@link Integer }{@code >}
     * 
     * 
     */
    public List<JAXBElement<Integer>> getNotificationsPerDayAndRequestsPerDay() {
        if (notificationsPerDayAndRequestsPerDay == null) {
            notificationsPerDayAndRequestsPerDay = new ArrayList<JAXBElement<Integer>>();
        }
        return this.notificationsPerDayAndRequestsPerDay;
    }

}
