<?xml version="1.0" encoding="ISO-8859-1" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%-- 

Since this is a decoration file, relying on references relative to this file will not work. 
The path the xhtml rendered page will use is the actual content page being viewed in the wikiData folder.
Decoratoration files should use absolute paths.

--%>

<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator"%>
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
		<title><decorator:title /></title>

		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
		
		<%-- There are references to images in the CSS file to images hence the need for using jsp include. --%>
		<style type="text/css">
		<!--
			<jsp:include page="./css/main.css.jsp" flush="true" />
			<jsp:include page="./css/style.css.jsp" flush="true" />
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

	<body class="body" onload="addEditLinks('<%=request.getContextPath()%>','<%=pageURI%>'); addAddLinks('<%=request.getContextPath()%>','<%=pageURI%>'); change('active');<decorator:getProperty property="body.onload" />" >		
		<div id="wrapper">
		
			<div id="topBanner">
				<img src="<%=request.getContextPath()%>/decorators/skinJin/images/JinTheme.png" width="300" height="60" alt="" />
			</div>
			
			<div id="topnavWrapper">
				<div id="topnav">
					<ul><%-- Note spaces between the list items will cause issues with CSS driven dsiplay. --%>
						<li id="topNav_Home"><a href="<%=request.getContextPath()%>/wikiData/jin/Home/Overview.jsp">Home</a></li><li id="topNav_Theme_Library"><a href="<%=request.getContextPath()%>/wikiData/jin/Theme_Library/Theme_Library.jsp">Theme Library</a></li><li id="topNav_Jin"><a href="<%=request.getContextPath()%>/wikiData/jin/Jin/jin.jsp">Jin</a></li><li id="topNav_Jin2"><a href="<%=request.getContextPath()%>/wikiData/jin/Jin2/jin2.jsp">Jin2</a></li><li id="topNav_Something_Else"><a href="<%=request.getContextPath()%>/wikiData/jin/Something_Else/SomethingElse.jsp">Something Else</a></li><li id="topNav_Jinie"><a href="<%=request.getContextPath()%>/wikiData/jin/Jinie/jinie.jsp">Jinie</a></li><li id="topNav_VeryVeryLongName"><a href="<%=request.getContextPath()%>/wikiData/jin/Very_Very_Looong_Name/VeryVeryLongName.jsp" class="last">Very Very Looong Name</a></li>
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
				<a href="<%=request.getContextPath()%>/wikiData/default/Home/Last_Edited">Last Edited <i>Feb 19, 2007</i></a> | 
				<a href="<%=request.getContextPath()%>/wikiData/default/Home/Copyright.jsp">Copyright</a>
			</div>
			
		</div>
	</body>
		
</html:html>