//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.1-b02-fcs 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2009.04.16 at 10:43:04 ���� CST 
//


package com.xiaonei.api.schema;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for user complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="user">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element name="uid" type="{http://api.xiaonei.com/1.0/}uid"/>
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="sex" type="{http://api.xiaonei.com/1.0/}sex" minOccurs="0"/>
 *         &lt;element name="star" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="zidou" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="birthday" type="{http://api.xiaonei.com/1.0/}date" minOccurs="0"/>
 *         &lt;element name="tinyurl" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="headurl" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="mainurl" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="hometown_location" type="{http://api.xiaonei.com/1.0/}hometown_location" minOccurs="0"/>
 *         &lt;element name="work_history" type="{http://api.xiaonei.com/1.0/}work_history" minOccurs="0"/>
 *         &lt;element name="university_history" type="{http://api.xiaonei.com/1.0/}university_history" minOccurs="0"/>
 *         &lt;element name="hs_history" type="{http://api.xiaonei.com/1.0/}hs_history" minOccurs="0"/>
 *       &lt;/all>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "user", propOrder = {

})
public class User {

    protected int uid;
    protected String name;
    protected Integer sex;
    @XmlElement(defaultValue = "0")
    protected Integer star;
    protected Integer zidou;
    protected String birthday;
    protected String tinyurl;
    protected String headurl;
    protected String mainurl;
    @XmlElement(name = "hometown_location")
    protected HometownLocation hometownLocation;
    @XmlElement(name = "work_history")
    protected WorkHistory workHistory;
    @XmlElement(name = "university_history")
    protected UniversityHistory universityHistory;
    @XmlElement(name = "hs_history")
    protected HsHistory hsHistory;

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
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Gets the value of the sex property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getSex() {
        return sex;
    }

    /**
     * Sets the value of the sex property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setSex(Integer value) {
        this.sex = value;
    }

    /**
     * Gets the value of the star property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getStar() {
        return star;
    }

    /**
     * Sets the value of the star property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setStar(Integer value) {
        this.star = value;
    }

    /**
     * Gets the value of the zidou property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getZidou() {
        return zidou;
    }

    /**
     * Sets the value of the zidou property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setZidou(Integer value) {
        this.zidou = value;
    }

    /**
     * Gets the value of the birthday property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBirthday() {
        return birthday;
    }

    /**
     * Sets the value of the birthday property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBirthday(String value) {
        this.birthday = value;
    }

    /**
     * Gets the value of the tinyurl property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTinyurl() {
        return tinyurl;
    }

    /**
     * Sets the value of the tinyurl property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTinyurl(String value) {
        this.tinyurl = value;
    }

    /**
     * Gets the value of the headurl property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHeadurl() {
        return headurl;
    }

    /**
     * Sets the value of the headurl property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHeadurl(String value) {
        this.headurl = value;
    }

    /**
     * Gets the value of the mainurl property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMainurl() {
        return mainurl;
    }

    /**
     * Sets the value of the mainurl property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMainurl(String value) {
        this.mainurl = value;
    }

    /**
     * Gets the value of the hometownLocation property.
     * 
     * @return
     *     possible object is
     *     {@link HometownLocation }
     *     
     */
    public HometownLocation getHometownLocation() {
        return hometownLocation;
    }

    /**
     * Sets the value of the hometownLocation property.
     * 
     * @param value
     *     allowed object is
     *     {@link HometownLocation }
     *     
     */
    public void setHometownLocation(HometownLocation value) {
        this.hometownLocation = value;
    }

    /**
     * Gets the value of the workHistory property.
     * 
     * @return
     *     possible object is
     *     {@link WorkHistory }
     *     
     */
    public WorkHistory getWorkHistory() {
        return workHistory;
    }

    /**
     * Sets the value of the workHistory property.
     * 
     * @param value
     *     allowed object is
     *     {@link WorkHistory }
     *     
     */
    public void setWorkHistory(WorkHistory value) {
        this.workHistory = value;
    }

    /**
     * Gets the value of the universityHistory property.
     * 
     * @return
     *     possible object is
     *     {@link UniversityHistory }
     *     
     */
    public UniversityHistory getUniversityHistory() {
        return universityHistory;
    }

    /**
     * Sets the value of the universityHistory property.
     * 
     * @param value
     *     allowed object is
     *     {@link UniversityHistory }
     *     
     */
    public void setUniversityHistory(UniversityHistory value) {
        this.universityHistory = value;
    }

    /**
     * Gets the value of the hsHistory property.
     * 
     * @return
     *     possible object is
     *     {@link HsHistory }
     *     
     */
    public HsHistory getHsHistory() {
        return hsHistory;
    }

    /**
     * Sets the value of the hsHistory property.
     * 
     * @param value
     *     allowed object is
     *     {@link HsHistory }
     *     
     */
    public void setHsHistory(HsHistory value) {
        this.hsHistory = value;
    }

}
