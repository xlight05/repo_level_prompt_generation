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
		<title>www.tin.homeip.net - <decorator:title /></title>

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
			@import "<%=request.getContextPath()%>/decorators/skinDefault/css/main.css";
		</style>
		<style type="text/css" title="currentStyle" media="screen">
			@import "<%=request.getContextPath()%>/decorators/skinDefault/css/style.css";
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

	<body class="body" onload="addEditLinks('<%=request.getContextPath()%>','<%=pageURI%>'); change('active','<%=pageURI%>');<decorator:getProperty property="body.onload" />">		
		<div id="wrapper">
		
			<div id="topBanner">
				<a href="http://www.tin.homeip.net"><img src="<%=request.getContextPath()%>/decorators/skinDefault/images/logoTin.gif" width="333" height="55" alt="www.tin.homeip.net" /></a>
			</div>
			
			<div id="topnavWrapper">
				<div id="topnav">
					<ul><%-- Note spaces between the list items will cause issues with CSS driven display. --%>
						<li id="topNav_Home"><a href="<%=request.getContextPath()%>/wikiData/default/Home/Index.html">Home</a></li
						><li id="topNav_Linux"><a href="<%=request.getContextPath()%>/wikiData/default/Linux/Index.html">Linux</a></li
						><li id="topNav_Toolbox"><a href="<%=request.getContextPath()%>/wikiData/default/Toolbox/Index.html">Toolbox</a></li
						><li id="topNav_Downloads"><a href="<%=request.getContextPath()%>/wikiData/default/Downloads/Index.html">Downloads</a></li
						><li id="topNav_Comics"><a href="<%=request.getContextPath()%>/wikiData/default/Comics/Index.html">Comics</a></li
						><li id="topNav_Links"><a href="<%=request.getContextPath()%>/wikiData/default/Links/Index.html">Links</a></li>
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