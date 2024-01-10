<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<%-- 

Since this is a decoration file, relying on references relative to this file will not work. 
The path the xhtml rendered page will use is the actual content page being viewed in the wikiData folder.
Decoratoration files should use absolute paths.

--%>

<%@ taglib uri="/WEB-INF/tlds/sitemesh-decorator.tld" prefix="decorator"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/custom-urlBasedBreadCrumb.tld" prefix="examples"%>

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" >

	<head>
		<title><decorator:title /></title>
	
		<!-- to correct the unsightly Flash of Unstyled Content. http://www.bluerobot.com/web/css/fouc.asp -->
		<script type="text/javascript"></script>
	
		<%-- There are references to images in the CSS file to images hence the need for using jsp include. --%>
		<style type="text/css">
		<!--
			<jsp:include page="./css/main.css.jsp" flush="true" />
		-->
		</style>

		<%-- Start extracts contents of decorated page's head. --%>
		<decorator:head />
		<%-- End extracts contents of decorated page's head. --%>
		
	</head>
	
	<!--
		This xhtml document is marked up to provide the designer with the maximum possible flexibility.
		There are more classes and extraneous tags than needed, and in a real world situation, it's more
		likely that it would be much leaner.
		
		However, I think we can all agree that even given that, we're still better off than if this had been
		built with tables.
	-->
	
	<body id="css-zen-garden">
	
	<div id="container">
		<div id="intro">
			<div id="pageHeader">
				<h1><span>Lemon Bistro</span></h1>
				<h2><span>The Beauty of <acronym title="Cascading Style Sheets">CSS</acronym> Design</span></h2>
			</div>
	
			<div id="quickSummary">
				<p class="p1"><span>"If you can sit at the table by the kitchen and watch the chef work you are in for a treat." <a href="http://www.restaurantica.com/restaurants/10991/">Maggie LeFevre</a></span></p>
				<p class="p2"><span><i>Rotate with different reviews from news clippings.</i></span></p>
			</div>
	
			<div id="preamble">
				<h3><span>The Road to Enlightenment</span></h3>
				<decorator:body />
			</div>
		</div>
	
		<div id="supportingText">
		
			<div id="benefits">
				<h3><span>Benefits</span></h3>
				<p class="p1"><span>...</span></p>
			</div>
		
			<div id="footer">
				<a href="<%=request.getContextPath()%>/wikiData/lemon/About_Lemon_Bistro.jsp">About Lemon Bistro</a> &nbsp; 
				<a href="<%=request.getContextPath()%>/wikiData/lemon/Menu.jsp">Menu</a> &nbsp; 
				<a href="<%=request.getContextPath()%>/wikiData/lemon/Photo_Gallery.jsp">Photo Gallery</a> &nbsp;
				<a href="<%=request.getContextPath()%>/wikiData/lemon/Hours_and_Location.jsp">Hours and Location</a> &nbsp;
				<a href="<%=request.getContextPath()%>/wikiData/lemon/Contact_Us.jsp">Contact Us</a>
			</div>
	
		</div>
	
		
		<div id="linkList">
			<div id="linkList2">
				<div id="lselect">
					<h3 class="select"><span>Today's Special</span></h3>
					<ul>
						<li><a href="#" accesskey="a">Squash Soup</a></li>
						<li><a href="#" title="AccessKey: b" accesskey="b">Cornish Hen</a></li>
						<li><a href="#" title="AccessKey: c" accesskey="c">Swordfish</a></li>
						<li><a href="#" title="AccessKey: d" accesskey="d">Creme Brulue</a></li>
					</ul>
				</div>
	
				<div id="larchives">
					<h3 class="archives"><span>Archives:</span></h3>
					<ul>
						<li><a href="#">Partner Sponsor 1 &raquo;</a></li>
						<li><a href="#">Partner Sponsor 2 &raquo;</a></li>
					</ul>
				</div>
				
			</div>
		</div>
	
	
	</div>
	
	<!-- These extra divs/spans may be used as catch-alls to add extra imagery. -->
	<div id="extraDiv1"><span></span></div><div id="extraDiv2"><span></span></div><div id="extraDiv3"><span></span></div>
	<div id="extraDiv4"><span></span></div><div id="extraDiv5"><span></span></div><div id="extraDiv6"><span></span></div>
	
	</body>
</html>