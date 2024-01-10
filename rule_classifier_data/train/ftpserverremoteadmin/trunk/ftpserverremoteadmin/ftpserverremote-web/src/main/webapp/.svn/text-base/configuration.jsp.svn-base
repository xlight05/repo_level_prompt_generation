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
            <table align="center">
                <tr><td>
                        <tc:page id="configurationPage" >
                            <tc:panel width="750px" >
                                <f:facet name="layout">
                                    <tc:gridLayout columns="*" rows="90px;*;22px" />
                                </f:facet>  
                                
                                <tc:image value="resources/logo_configuration.jpg" width="563"/>
                                
                                <tc:panel>
                                    <f:facet name="layout">
                                        <tc:gridLayout columns="*" rows="22px;*" />
                                    </f:facet>
                                    
                                    <tc:panel>
                                        <f:facet name="layout">
                                            <tc:gridLayout columns="60px;*;60px;7px" rows="22px" />
                                        </f:facet>
                                        <tc:label value="#{messages.servers}"/>
                                        <tc:out value="#{ConfigurationBean.printableSelectedServers}" />
                                        <tc:button label="#{messages.log_out}" action="#{AdminBean.logout}"/>
                                        <tc:out />
                                    </tc:panel>
                                    
                                    <tc:tabGroup id="tabs" rendered="true" width="740px" selectedIndex="0" switchType="reloadPage">
                                        
                                        <tc:tab label="#{messages.configuration}" >  
                                            
                                            <jsp:include page="/tabs/configurationTab.jspf" />
                                            
                                        </tc:tab>
                                        
                                        <tc:tab label="#{messages.users}" >          
                                            <jsp:include page="/tabs/usersTab.jspf" />
                                        </tc:tab>
                                        
                                        <tc:tab label="#{messages.ip_restrictor}" >          
                                            <jsp:include page="/tabs/ipRestrictorTab.jspf" />
                                        </tc:tab>
                                        
                                        <tc:tab label="#{messages.messages}" >          
                                            
                                            <jsp:include page="/tabs/messagesTab.jspf" />
                                            
                                        </tc:tab>
                                        
                                       <tc:tab label="#{messages.connections}" >          
                                            <jsp:include page="/tabs/connectionsTab.jspf" />
                                        </tc:tab>
                                        
                                        <tc:tab label="#{messages.spy}" >          
                                            <jsp:include page="/tabs/spyTab.jspf" />
                                        </tc:tab>
                                        
                                        <tc:tab label="#{messages.file}" > 
                                            
                                            <jsp:include page="/tabs/fileTab.jspf" />
                                            
                                        </tc:tab>
                                        
                                        <tc:tab label="#{messages.directory}" >          
                                            <jsp:include page="/tabs/directoryTab.jspf" />
                                        </tc:tab>
                                        
                                        <tc:tab label="#{messages.log}" >      
                                            <jsp:include page="/tabs/logTab.jspf" />
                                        </tc:tab>
                                        
                                        <tc:tab label="#{messages.statistics}" >          
                                            <jsp:include page="/tabs/statisticTab.jspf" />
                                        </tc:tab>        
                                        
                                    </tc:tabGroup>
                                    
                                </tc:panel> 
                                
                                <tc:panel width="750px" >
                                    <f:facet name="layout">
                                        <tc:gridLayout columns="*;570px;*" />
                                    </f:facet> 
                                    <tc:out />
                                    <tc:label value="AGH © IOSR 2007/2008 © Agnieszka Janowska, Monika Nawrot, Tomasz Jadczyk, Tomasz Sadura" />
                                    <tc:out />
                                    </tc:panel>
                        </tc:panel>
                        </tc:page>
                    </td>
                    </tr>               
                </table>
    </body>
</html>
</f:view>



