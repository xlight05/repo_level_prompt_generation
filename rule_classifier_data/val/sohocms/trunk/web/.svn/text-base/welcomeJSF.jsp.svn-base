<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>

<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%>
<%@ taglib prefix="a" uri="http://java.sun.com/jmaki" %>

<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <style>
            label{display:block;}
            
        </style>
    </head>
    <body>
        <f:view>
            <h1><h:outputText value="Add Nodetype" /></h1>
            <h:form id="addNodeTypeForm">     
                <fieldset>  
                    <h:outputLabel value="Name:" for="name" ><h:inputText id="name" value="#{NodeType.name}"/></h:outputLabel>
                    <h:outputLabel value="Author:" for ="author" ><h:outputText id="author" value="#{NodeType.author}"> ${requestScope.remoteUser}</h:outputText></h:outputLabel>
                </fieldset>
                <fieldset>
                    <legend>Properties</legend> 
                    <h:form id="addPropertyForm">
                        <h:outputText value="Name:"/><h:inputText id="parname" value="#{NodeType.tempParName}"/>
                        <h:outputText value="Type:" />
                        <h:selectOneMenu id="partype" value="#{NodeType.tempParType}">
                              <f:selectItems value="#{NodeType.types}" />
                        </h:selectOneMenu> 
                            <h:commandButton value="Add Property" action="#{NodeTypeActionListener.processAddProperty}" />                         
                    </h:form>
                        
                    <c:set var="properties" scope="page">${sessionScope.NodeType.properties}</c:set>
                    <c:forEach items="properties" var="property">
                        ${property}           
                    </c:forEach>
                        
                </fieldset> 
                <h:commandButton value="Add Nodetype" action="#{NodeTypeActionListener.processAddNodeType}"/>
            </h:form>
        </f:view>
    </body>
</html>
