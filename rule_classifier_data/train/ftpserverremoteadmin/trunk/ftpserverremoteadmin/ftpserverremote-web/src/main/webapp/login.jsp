<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@ taglib uri="http://myfaces.apache.org/tobago/component" prefix="tc" %>
<%@ taglib uri="http://myfaces.apache.org/tobago/extension" prefix="tx" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<f:view locale="en">
    <f:loadBundle basename="locale" var="messages" />
    <html>
        <head>
            <title>Web Interface for Apache FtpServer</title>
        </head>
        <body>
            <table width="500" border="0" align="center" cellpadding="2" cellspacing="2">
                <tr>
                    <td>
                        <div align="center">
                            <tc:image value="resources/logo_login.jpg" />
                        </div>
                    </td>
                </tr>
                <tr>
                    <td>
                        <div align="center"></div>
                        <tc:page id="loginPage" >
                            
                            <tc:panel width="300" height="100px">
                                <f:facet name="layout">
                                    <tc:gridLayout columns="*;*" rows="22px;22px;22px;*" cellspacing="3px"/>
                                </f:facet>
                                
                                <tc:label value="#{messages.login}" />
                                <tc:in id="adminLogin" value="#{AdminBean.login}">
                                </tc:in>
                                
                                <tc:label value="#{messages.password}" />
                                <tc:in id="adminPassword" password="true" value="#{AdminBean.password}">
                                </tc:in>
                                
                                <tc:out value="" />
                                <tc:button label="#{messages.log_in}" id="loginButton" action="#{AdminBean.login}"
                                           width="60px"/>
                            </tc:panel>
                            
                            <tc:attribute name="renderedPartially" value="loginErrorPopup"/>
                            <tc:popup width="250px" height="100px" id="loginErrorPopup" rendered="#{AdminBean.showLoginErrorPopup}">
                                <tc:box label="#{AdminBean.errorHeader}">
                                    <tc:panel>
                                        <f:facet name="layout">
                                            <tc:gridLayout columns="*" rows="*;22px" />
                                        </f:facet>
                                        <tc:out value="#{AdminBean.errorMessage}" />
                                        <tc:button action="#{AdminBean.closeLoginErrorPopup}" label="#{messages.ok}" >
                                            <tc:attribute name="popupClose" value="afterSubmit"/>
                                        </tc:button>
                                    </tc:panel>    
                                </tc:box>
                            </tc:popup>
                            
                            </tc:page>
                    </td>
                </tr>
            </table>
            
    </body>
</html>
</f:view>


