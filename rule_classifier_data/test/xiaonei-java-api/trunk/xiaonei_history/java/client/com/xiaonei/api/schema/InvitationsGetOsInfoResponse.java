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
 *         &lt;element name="os_invitation_info">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;all>
 *                   &lt;element name="inviter_uid" type="{http://api.xiaonei.com/1.0/}uid"/>
 *                   &lt;element name="invite_time" type="{http://api.xiaonei.com/1.0/}date"/>
 *                   &lt;element name="invitee_uid" type="{http://api.xiaonei.com/1.0/}uid" minOccurs="0"/>
 *                   &lt;element name="register_time" type="{http://api.xiaonei.com/1.0/}date" minOccurs="0"/>
 *                   &lt;element name="selected" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *                   &lt;element name="install_time" type="{http://api.xiaonei.com/1.0/}date" minOccurs="0"/>
 *                 &lt;/all>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
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
    "osInvitationInfo"
})
@XmlRootElement(name = "invitations_getOsInfo_response")
public class InvitationsGetOsInfoResponse {

    @XmlElement(name = "os_invitation_info")
    protected List<InvitationsGetOsInfoResponse.OsInvitationInfo> osInvitationInfo;

    /**
     * Gets the value of the osInvitationInfo property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the osInvitationInfo property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getOsInvitationInfo().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link InvitationsGetOsInfoResponse.OsInvitationInfo }
     * 
     * 
     */
    public List<InvitationsGetOsInfoResponse.OsInvitationInfo> getOsInvitationInfo() {
        if (osInvitationInfo == null) {
            osInvitationInfo = new ArrayList<InvitationsGetOsInfoResponse.OsInvitationInfo>();
        }
        return this.osInvitationInfo;
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
     *         &lt;element name="inviter_uid" type="{http://api.xiaonei.com/1.0/}uid"/>
     *         &lt;element name="invite_time" type="{http://api.xiaonei.com/1.0/}date"/>
     *         &lt;element name="invitee_uid" type="{http://api.xiaonei.com/1.0/}uid" minOccurs="0"/>
     *         &lt;element name="register_time" type="{http://api.xiaonei.com/1.0/}date" minOccurs="0"/>
     *         &lt;element name="selected" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
     *         &lt;element name="install_time" type="{http://api.xiaonei.com/1.0/}date" minOccurs="0"/>
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
    public static class OsInvitationInfo {

        @XmlElement(name = "inviter_uid")
        protected int inviterUid;
        @XmlElement(name = "invite_time", required = true)
        protected String inviteTime;
        @XmlElement(name = "invitee_uid")
        protected Integer inviteeUid;
        @XmlElement(name = "register_time")
        protected String registerTime;
        @XmlElement(defaultValue = "0")
        protected Integer selected;
        @XmlElement(name = "install_time")
        protected String installTime;

        /**
         * Gets the value of the inviterUid property.
         * 
         */
        public int getInviterUid() {
            return inviterUid;
        }

        /**
         * Sets the value of the inviterUid property.
         * 
         */
        public void setInviterUid(int value) {
            this.inviterUid = value;
        }

        /**
         * Gets the value of the inviteTime property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getInviteTime() {
            return inviteTime;
        }

        /**
         * Sets the value of the inviteTime property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setInviteTime(String value) {
            this.inviteTime = value;
        }

        /**
         * Gets the value of the inviteeUid property.
         * 
         * @return
         *     possible object is
         *     {@link Integer }
         *     
         */
        public Integer getInviteeUid() {
            return inviteeUid;
        }

        /**
         * Sets the value of the inviteeUid property.
         * 
         * @param value
         *     allowed object is
         *     {@link Integer }
         *     
         */
        public void setInviteeUid(Integer value) {
            this.inviteeUid = value;
        }

        /**
         * Gets the value of the registerTime property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getRegisterTime() {
            return registerTime;
        }

        /**
         * Sets the value of the registerTime property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setRegisterTime(String value) {
            this.registerTime = value;
        }

        /**
         * Gets the value of the selected property.
         * 
         * @return
         *     possible object is
         *     {@link Integer }
         *     
         */
        public Integer getSelected() {
            return selected;
        }

        /**
         * Sets the value of the selected property.
         * 
         * @param value
         *     allowed object is
         *     {@link Integer }
         *     
         */
        public void setSelected(Integer value) {
            this.selected = value;
        }

        /**
         * Gets the value of the installTime property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getInstallTime() {
            return installTime;
        }

        /**
         * Sets the value of the installTime property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setInstallTime(String value) {
            this.installTime = value;
        }

    }

}
