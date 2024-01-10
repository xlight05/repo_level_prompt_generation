<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%-- 

Since this is a decoration file, relying on references relative to this file will not work. 
The path the xhtml rendered page will use is the actual content page being viewed in the wikiData folder.
Decoratoration files should use absolute paths.

--%>

<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/custom-urlBasedBreadCrumb.tld" prefix="examples"%>

<%
String pageURI = null;
String contextPath = request.getContextPath();

pageURI = request.getRequestURI();
pageURI = pageURI.substring(contextPath.length(), pageURI.length());
%>

<html:html xhtml="true">
	<head>
		<title>www.2keys.homeip.net - <decorator:title /></title>

		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />

		<%-- Yahoo YUI Library - Font: renders fonts consistent across browsers. --%>
		<style type="text/css" title="currentStyle" media="screen">
			@import "<%=request.getContextPath()%>/decorators/skinDefault/yui/fonts/reset.css";
		</style>
		<style type="text/css" title="currentStyle" media="screen">
			@import "<%=request.getContextPath()%>/decorators/skinDefault/yui/fonts/fonts.css";
		</style>

		<%-- There are hard coded references to images here. It is for ease of css development in TopStyle. --%>
		<%-- Hard coded references should be copied into .css.jsp files and changed to dynamic. --%>
		<style type="text/css" title="currentStyle" media="screen">
			@import "<%=request.getContextPath()%>/decorators/skin2Keys/css/main.css";
		</style>
		<style type="text/css" title="currentStyle" media="screen">
			@import "<%=request.getContextPath()%>/decorators/skin2Keys/css/style.css";
		</style>
		
		<%-- Overrid the hard coded references to images. --%>
		<style type="text/css">
			<jsp:include page="./css/main.css.jsp" flush="true" />
			<jsp:include page="./css/style.css.jsp" flush="true" />
		</style>

		<%-- Start extracts contents of decorated page's head. --%>
		<decorator:head />
		<%-- End extracts contents of decorated page's head. --%>

		<%-- Adds the controls for edit, insert ect... --%>
		<script type="text/javascript" language="javascript" src="<%=request.getContextPath()%>/decorators/modifyContentControls/javascript/controls.js">
		</script>

		<%-- Highlights the menu based on url. --%>
		<script type="text/javascript" language="javascript" src="<%=request.getContextPath()%>/decorators/skinDefault/javascript/navigationTop.js">
		</script>


	</head>

	<body class="body" onload="addEditLinks('<%=request.getContextPath()%>','<%=pageURI%>'); change('active','<%=pageURI%>');<decorator:getProperty property="body.onload" />" >		
		<div id="wrapper">
		
			<div id="topBanner">
				<a href="http://www.2keys.homeip.net"><img src="<%=request.getContextPath()%>/decorators/skin2Keys/images/logo2Keys.png" width="180" height="55" alt="www.2keys.homeip.net" /></a>
			</div>
			
			<div id="topnavWrapper">
				<div id="topnav">
					<ul><%-- Note spaces between the list items will cause issues with CSS driven display. --%>
						<li id="topNav_Server_List"><a href="<%=request.getContextPath()%>/wikiData/2Keys/Server_List/Server_List.html">Server List</a></li
						><li id="topNav_Environments"><a href="<%=request.getContextPath()%>/wikiData/2Keys/Environments/Environments.html">Environments</a></li
						><li id="topNav_Solaris"><a href="<%=request.getContextPath()%>/wikiData/2Keys/Solaris/Solaris.html">Solaris</a></li
						><li id="topNav_Windows"><a href="<%=request.getContextPath()%>/wikiData/2Keys/Windows/Windows.html">Windows</a></li
						><li id="topNav_Orientation"><a href="<%=request.getContextPath()%>/wikiData/2Keys/Orientation/Orientation.html">Orientation</a></li
						><li id="topNav_Links"><a href="<%=request.getContextPath()%>/wikiData/2Keys/Links/Links.html">Links</a></li
						><li id="topNav_Contacts"><a href="<%=request.getContextPath()%>/wikiData/2Keys/Contacts/Contacts.html">Contacts</a></li
						><li id="topNav_Support"><a href="<%=request.getContextPath()%>/wikiData/2Keys/Support/Support.html">Support</a></li>
					</ul>
				</div>
			</div>
			<div id="crumbs">
				<div class="minWidth"><img src="<%=request.getContextPath()%>/decorators/images/spacer.gif" width="1" height="1" alt="" /></div>
				<div id="crumbsText"><examples:hello /></div>
			</div>
			<div id="crumbsBar">
				<div class="minWidth"><img src="<%=request.getContextPath()%>/decorators/images/spacer.gif" width="1" height="1" alt="" /></div>
				<div>&nbsp;</div>
			</div>
			

			<!-- Extracts contents of decorated page's body. -->
			<div id="contents">
				<!-- Start contents -->				
				<decorator:body />
				<!-- End contents -->
			</div>

			<div id="footer">
				<a href="<%=request.getContextPath()%>/wikiData/default/Home/Sitemap.jsp">Sitemap</a> | 
				<a href="<%=request.getContextPath()%>/wikiData/default/Home/About_tinWiki.jsp">About tinWiki</a> | 
				<a href="<%=request.getContextPath()%>/wikiData/default/Home/Last_Edited.jsp">Last Edited <i>Apr 1, 2005</i></a> | 
				<a href="<%=request.getContextPath()%>/wikiData/default/Home/Copyright.jsp">Copyright</a>
			</div>
			
		</div>
	</body>
		
</html:html>