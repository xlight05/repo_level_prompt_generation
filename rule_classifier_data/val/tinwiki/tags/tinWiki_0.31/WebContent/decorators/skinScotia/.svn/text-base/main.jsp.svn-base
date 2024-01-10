<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%-- 

Since this is a decoration file, relying on references relative to this file will not work. 
The path the xhtml rendered page will use is the actual content page being viewed in the wikiData folder.
Decoratoration files should use absolute paths.

--%>

<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<%@ taglib uri="/WEB-INF/tlds/custom-urlBasedBreadCrumb.tld" prefix="examples"%>

<%
String pageURI = null;
String contextPath = request.getContextPath();

pageURI = request.getRequestURI();
pageURI = pageURI.substring(contextPath.length(), pageURI.length());
%>

<html:html xhtml="true">
	<head>
		<title><decorator:title /></title>

		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		
		<%-- There are references to images in the CSS file to images hence the need for using jsp include. --%>
		<style type="text/css">
		<!--
			<jsp:include page="../skinDefault/css/main.css.jsp" flush="true" />
			<jsp:include page="../skinDefault/css/style.css.jsp" flush="true" />
		-->
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

	<body class="body" onload="addEditLinks('<%=request.getContextPath()%>','<%=pageURI%>'); addAddLinks('<%=request.getContextPath()%>','<%=pageURI%>'); change('active','<%=pageURI%>');<decorator:getProperty property="body.onload" />" >		
		<div id="wrapper">
		
			<div id="topBanner">
				<img src="<%=request.getContextPath()%>/decorators/skinScotia/images/ScotiabankLogo.gif" width="300" height="54" alt="" />
			</div>
			
			<div id="topnavWrapper">
				<div id="topnav">
					<ul><%-- Note spaces between the list items will cause issues with CSS driven dsiplay. --%>
						<li id="topNav_Home"><a href="<%=request.getContextPath()%>/web/wiki/Home/Overview.jsp">Home</a></li><li id="topNav_Architecture"><a href="<%=request.getContextPath()%>/web/wiki/Architecture/Overview.jsp">Architecture</a></li><li id="topNav_Common_Modules"><a href="<%=request.getContextPath()%>/web/wiki/Common_Modules/Overview.jsp">Common Modules</a></li><li id="topNav_Development"><a href="<%=request.getContextPath()%>/web/wiki/Development/Overview.jsp">Development</a></li><li id="topNav_Documents"><a href="<%=request.getContextPath()%>/web/wiki/Documents/Overview.jsp">Docs</a></li><li id="topNav_Library_Chart"><a href="<%=request.getContextPath()%>/web/wiki/Library_Chart/Overview.jsp">Library Chart</a></li><li id="topNav_Wlogs"><a href="<%=request.getContextPath()%>/web/wiki/WLogs/Work_Logs.jsp">WLogs</a></li><li id="topNav_Servers"><a href="<%=request.getContextPath()%>/web/wiki/Servers/Overview.jsp">Servers</a></li>
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