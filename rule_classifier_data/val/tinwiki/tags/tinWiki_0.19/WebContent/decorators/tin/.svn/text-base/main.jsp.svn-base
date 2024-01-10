<?xml version="1.0" encoding="ISO-8859-1" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%-- 

Since this is a decoration file, relying on references relative to this file will not work. 
The path the xhtml rendered page will use is the actual content page. 
For the decorator, use absolute paths.

Tricky thing to watch out for are CSS files hence the need for cssFileName_dynamic.jsp files

--%>

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
		<title>www.tin.homeip.net - <decorator:title /></title>

		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
		
		<%-- There are references to images here hence the need of making this a jsp include. --%>
		<style type="text/css">
		<!--
			<jsp:include page="./css/main.css.jsp" flush="true" />
			<jsp:include page="./css/style.css.jsp" flush="true" />
		-->
		</style>

		<%-- Start extracts contents of decorated page's head. --%>
		<decorator:head />
		<%-- End extracts contents of decorated page's head. --%>

		<script type="text/javascript" language="javascript" src="<%=request.getContextPath()%>/decorators/javascript/pageDecorator.js">
		</script>

		<script type="text/javascript">
		/* <![CDATA[ */

			// nolonger used here,
			// todo , make it nicer and then put into javascript toolbox
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
			// this is based on url... look up existing menu items and then find a match
			function change(newClass, pageURI) {
				var url = window.location
				var identity = null;

				// alert (replaceChars(document.title));
				identity = document.getElementById("topNav_" + extractTopLevelLocation(pageURI));
				
				if (identity != null) {
					identity.className=newClass;	
				}
			}

			// uses regular experssions to get the 3rd word in the pageURI			
			function extractTopLevelLocation(pageURI) {
				
				// /..../ start and end of regular expression
				// [/]\w{1,}[/] = /web/ = /*/
				// (\w{1,}) = one or more alphanumeric characters, brackets asks for storage of the match
				// 
				// var match = /[/]\w{1,}[/]\w{1,}[/](\w{1,})[/]/ .exec('/web/wikiTin/Linux/Index.jsp');
				var match = /[/]\w{1,}[/]\w{1,}[/](\w{1,})[/]/ .exec(pageURI);
				return match[1];
			}
			
		/* ]]> */
		</script>

	</head>

	<body class="body" onload="addEditLinks('<%=request.getContextPath()%>','<%=pageURI%>'); addAddLinks('<%=request.getContextPath()%>','<%=pageURI%>'); change('active','<%=pageURI%>');<decorator:getProperty property="body.onload" />" >		
		<div id="wrapper">
		
			<div id="topBanner">
				<img src="<%=request.getContextPath()%>/decorators/tin/logoTin.gif" width="333" height="55" alt="" />
			</div>
			
			<div id="topnavWrapper">
				<div id="topnav">
					<ul><%-- Note spaces between the list items will cause issues with CSS driven dsiplay. --%>
						<li id="topNav_Home"><a href="<%=request.getContextPath()%>/web/wikiTin/Home/Index.jsp">Home</a></li><li id="topNav_Renovations"><a href="<%=request.getContextPath()%>/web/wikiTin/Renovations/Index.jsp">Renovations</a></li><li id="topNav_Linux"><a href="<%=request.getContextPath()%>/web/wikiTin/Linux/Index.jsp">Linux</a></li><li id="topNav_Toolbox"><a href="<%=request.getContextPath()%>/web/wikiTin/Toolbox/Index.jsp">Toolbox</a></li><li id="topNav_Downloads"><a href="<%=request.getContextPath()%>/web/wikiTin/Downloads/Index.jsp">Downloads</a></li><li id="topNav_Pictures"><a href="<%=request.getContextPath()%>/web/wikiTin/Pictures/Index.jsp">Pictures</a></li><li id="topNav_Comics"><a href="<%=request.getContextPath()%>/web/wikiTin/Comics/Index.jsp">Comics</a></li><li id="topNav_Recipes"><a href="<%=request.getContextPath()%>/web/wikiTin/Recipes/Index.jsp">Recipes</a></li><li id="topNav_Links"><a href="<%=request.getContextPath()%>/web/wikiTin/Links/Index.jsp">Links</a></li>
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
				<a href="<%=request.getContextPath()%>/web/wikiTin/Home/Sitemap.jsp">Sitemap</a> | 
				<a href="<%=request.getContextPath()%>/web/wikiTin/Home/About_tinWiki.jsp">About tinWiki</a> | 
				<a href="<%=request.getContextPath()%>/web/wikiTin/Home/Last_Edited.jsp">Last Edited <i>Apr 1, 2005</i></a> | 
				<a href="<%=request.getContextPath()%>/web/wikiTin/Home/Copyright.jsp">Copyright</a>
			</div>
			
		</div>
	</body>
		
</html:html>