<?xml version="1.0" encoding="ISO-8859-1" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ taglib uri="/WEB-INF/tlds/sitemesh-decorator.tld" prefix="decorator"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/custom-urlBasedBreadCrumb.tld" prefix="examples"%>

<%
String pageURI = null;
String deliminator = "/";
String deliminatorExtension = ".";
String contextPath = request.getContextPath();

pageURI = request.getRequestURI();
pageURI = pageURI.substring(contextPath.length(), pageURI.length());
%>

<html:html xhtml="true">
	<head>
		<title><decorator:title /></title>

		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />

		<link rel="stylesheet" type="text/css"  href="<html:rewrite page='/decorators/scotia/main.css'/>" />
		<link rel="stylesheet" type="text/css"  href="<html:rewrite page='/decorators/scotia/style.css'/>" />

		<%-- Start extracts contents of decorated page's head. --%>
		<decorator:head />
		<%-- End extracts contents of decorated page's head. --%>

		<script type="text/javascript" language="javascript" src="<%=request.getContextPath()%>/decorators/javascript/pageDecorator.js">
		</script>

		<script type="text/javascript">
		/* <![CDATA[ */

			function replaceChars(entry) {
				var out = " "; // replace this
				var add = "_"; // with this
				var temp = "" + entry; // temporary holder
				var pos = null;
				while (temp.indexOf(out)>-1) {
					pos = temp.indexOf(out);
					temp = "" + (temp.substring(0, pos) + add + temp.substring((pos + out.length), temp.length));
				}
				return temp;
			}

			// highlight the corect tab
			// this is such a cheat, make this better
			function change(newClass) {
				replaceChars(document.title);
				identity=document.getElementById("topNav_" + replaceChars(document.title));
				if (identity != null) {
					identity.className=newClass;	
				}
			}
		/* ]]> */
		</script>

	</head>

	<body class="body" onload="addEditLinks('<%=request.getContextPath()%>','<%=pageURI%>'); change('active');<decorator:getProperty property="body.onload" />" >		
		<div id="wrapper">
		
			<div id="topBanner">
				<img src="<%=request.getContextPath()%>/decorators/images/ScotiabankLogo.gif" width="300" height="54" alt="" />
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
				<a href="<%=request.getContextPath()%>/web/wiki/Home/Sitemap.jsp">Sitemap</a> | 
				<a href="<%=request.getContextPath()%>/web/wiki/Home/About_tinWiki.jsp">About tinWiki</a> | 
				<a href="<%=request.getContextPath()%>/web/wiki/Home/Last_Edited.jsp">Last Edited <i>Apr 1, 2005</i></a> | 
				<a href="<%=request.getContextPath()%>/web/wiki/Home/Copyright.jsp">Copyright</a>
			</div>
			
		</div>
	</body>
		
</html:html>