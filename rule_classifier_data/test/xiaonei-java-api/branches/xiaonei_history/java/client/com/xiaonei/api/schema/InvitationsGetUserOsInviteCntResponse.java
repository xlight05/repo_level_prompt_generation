//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.1-b02-fcs 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2009.04.16 at 10:43:04 ���� CST 
//


package com.xiaonei.api.schema;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
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
 *         &lt;element name="os_invite_cnt" maxOccurs="unbounded" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;all>
 *                   &lt;element name="uid" type="{http://api.xiaonei.com/1.0/}uid"/>
 *                   &lt;element name="os_invite_total" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *                   &lt;element name="star_user_total" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *                 &lt;/all>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *       &lt;attribute name="list" use="required" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "osInviteCnt"
})
@XmlRootElement(name = "invitations_getUserOsInviteCnt_response")
public class InvitationsGetUserOsInviteCntResponse {

    @XmlElement(name = "os_invite_cnt")
    protected List<InvitationsGetUserOsInviteCntResponse.OsInviteCnt> osInviteCnt;
    @XmlAttribute(required = true)
    protected boolean list;

    /**
     * Gets the value of the osInviteCnt property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the osInviteCnt property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getOsInviteCnt().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link InvitationsGetUserOsInviteCntResponse.OsInviteCnt }
     * 
     * 
     */
    public List<InvitationsGetUserOsInviteCntResponse.OsInviteCnt> getOsInviteCnt() {
        if (osInviteCnt == null) {
            osInviteCnt = new ArrayList<InvitationsGetUserOsInviteCntResponse.OsInviteCnt>();
        }
        return this.osInviteCnt;
    }

    /**
     * Gets the value of the list property.
     * 
     */
    public boolean isList() {
        return list;
    }

    /**
     * Sets the value of the list property.
     * 
     */
    public void setList(boolean value) {
        this.list = value;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;all>
     *         &lt;element name="uid" type="{http://api.xiaonei.com/1.0/}uid"/>
     *         &lt;element name="os_invite_total" type="{http://www.w3.org/2001/XMLSchema}int"/>
     *         &lt;element name="star_user_total" type="{http://www.w3.org/2001/XMLSchema}int"/>
     *       &lt;/all>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {

    })
    public static class OsInviteCnt {

        protected int uid;
        @XmlElement(name = "os_invite_total")
        protected int osInviteTotal;
        @XmlElement(name = "star_user_total")
        protected int starUserTotal;

        /**
         * Gets the value of the uid property.
         * 
         */
        public int getUid() {
            return uid;
        }

        /**
         * Sets the value of the uid property.
         * 
         */
        public void setUid(int value) {
            this.uid = value;
        }

        /**
         * Gets the value of the osInviteTotal property.
         * 
         */
        public int getOsInviteTotal() {
            return osInviteTotal;
        }

        /**
         * Sets the value of the osInviteTotal property.
         * 
         */
        public void setOsInviteTotal(int value) {
            this.osInviteTotal = value;
        }

        /**
         * Gets the value of the starUserTotal property.
         * 
         */
        public int getStarUserTotal() {
            return starUserTotal;
        }

        /**
         * Sets the value of the starUserTotal property.
         * 
         */
        public void setStarUserTotal(int value) {
            this.starUserTotal = value;
        }

    }

}
