/**
 * Contains custom css elements global to the site.
 *
 * @author      Tin Pham
 * @version     1.0
 * @css level   (2 and W3C Compliant)
 * @browsers    ie 6, firefox 1.5.0.8
 *
 * @see
 *    - "Sliding Doors of CSS by Douglas Bowman" - http://alistapart.com/articles/slidingdoors/ , http://alistapart.com/articles/slidingdoors2
 *
 */

/*
Work within the parameters given if you want to leverage all my testing 
across multiple browsers.

1. To control the tab heights:
   Do not use padding in li and ul if you want the tabs to wrap down 
   nicely when you resize the font size through the browser.
	Instead, use... 
	
*/
 
#wrapper {

	/* Firefox 1.5.0.8 bug shifts menus off if using percentage for top. */
	margin: 25px 3% 3% 4%;
	
	border-top: 1px solid #000200;
	border-left: 1px solid #000200;
	border-right: thin ridge #000200;
	border-bottom: thin ridge #000200;
	background: White;

	/* Prevents overflow of topnav in Firefox and Opera. For IE see topnav definition. */
	min-width: 780px;

}

#topBanner {
	background: Black url(<%=request.getContextPath()%>/decorators/images/newTheme/Bannerback.png);
	padding: 0px 0px 0px 0px;
	margin: 0px 0px 0px 0px;
}

#topnavWrapper {
	margin: 0px 0px 0px 0px;
	padding: 0px 0px 0px 0px;
}
 
#topnav {
	/* Set this to prevent wrapping of nav menu in ie6. */
	/* @see  .minWidth and make sure to set to same size */
	width: 785px;

}

#topnav ul {
	/* Need to over-ride the default, ul tags usually indent. */
	/* Repeat in both ul and li because li uses display: inline. */
	margin: 0px 0px 0px 0px;
	padding: 0px 0px 0px 0px;
	/*display: inline;*/
	list-style: none;
}

#topnav li {
	/* Need to over-ride the default, ul tags usually indent. */
	/* Repeat in both ul and li because li uses display: inline. */
	margin: 0px 0px 0px 0px;
	padding: 0px 0px 0px 0px;
	display: inline;
	list-style: none;
}

#topnav li a {	
	color: #B0B0B0;
	text-decoration: none;
	border-bottom: 1px solid #000000;
	border-right: 0px solid #DDDDDD;
	border-top: 1px solid #000000;
	font: 100%/200% Verdana, Geneva, Arial, Helvetica, sans-serif;

	/* Repeat in both ul and li because li uses display: inline. */
	/* Still playing here. This must not go beyond whatever the font area is or you will get overlapps when resizing the font through the browser. */
	padding-top: 3px; /* Larger value will push down the background images in buttons. */
	padding-bottom: 4px; /* To stop gaps in resizing in firefox 1.5.0.8 makes sure to add 1 additional pixel.*/
	
	/* Change this one area for menu tab padding. */
	padding-left: 15px;
	padding-right: 14px;

	background: #fff url(<%=request.getContextPath()%>/decorators/images/newTheme/whtgrad.png);
}

#topnav li.active a { 
	color: #EEEEEE;
	/* When drawing the bg keep in mind that in ie6 the border top will cover up 1 pixel of the bg. */
	background: Black url(<%=request.getContextPath()%>/decorators/images/newTheme/blkgrad.png);
	border-top: 1px solid Black;
	border-right: 1px solid #000000;
	border-left: 1px solid #000000;
	font: bold;
}

#topnav li a:hover {
	color: #EEEEEE;
	background: #606060 none;
	border-top: 1px solid #000000;
}

#topnav li.last{
	border-top: 1px solid #000000;
	border-right: 1px solid #000000;
	border-bottom: 1px solid #000000;
	padding-top: 3px;
	padding-bottom: 4px;
}

#crumbs {
	background: #D9DCFF url(<%=request.getContextPath()%>/decorators/images/newTheme/crm_whtbk_1.png);
	color: #1F1F1F;
	border-top: 2px solid #FFFFFF;
	border-bottom: 1px solid #EEEEDD;
}

#crumbsText {
	font: Verdana, Geneva, Arial, Helvetica, sans-serif;
	padding: 1px 5px 2px 15px;
	font-size: 85%;
}

#crumbsBar {
	margin: 0px 0px 0px 0px;
	padding: 0px 0px 0px 0px;
}

/* Prevents elements from shrinking to small. */
/* @see  #topnav */
.minWidth {
	margin: 0px 0px 0px 0px;
	padding: 0px 0px 0px 0px;
	width: 785px;
}

#contents {
	/* Set this to prevent wrapping. Currently using same width as #topnav */
	/* @see  .minWidth */
	/*width: 730px;*/
	
	padding: 0px 30px 0px 35px;
	text-align: left;
	background: White;
	min-height: 460px;
	/* CSS 2.1 allows use of _ in front for vendor specific setting in this case IE @see http://www.philledgerwood.com/?p=14*/
	_height: 460px;
}

a.edit {
	text-decoration: none;
	float: right;
	margin: 5px 0px 0px 0px;
	color: Blue;
	font-size: 80%;
}
a.add {
	text-decoration: none;
	float: right;
	margin: 5px 0px 0px 2px;
	color: Blue;
	font-size: 80%;
}

a:hover.edit {
	text-decoration: underline;
}

a:hover.add {
	text-decoration: underline;
}

.highLighter {
	background: #DFF0FF url(<%=request.getContextPath()%>/decorators/images/newTheme/blubk.png);;
}

.highLighterAdd {
	background: #DDDDDD url(<%=request.getContextPath()%>/decorators/images/newTheme/blkbk.png);
}

.clearHighLighter {
	background: inherit;
}

.highLightEditArea {
	/* not doing anything at the moment consider grey text */
}

.highLightEditAreaAdd {
	/*border-top: 2px solid red;*/
}

.unHighLightEditArea {
	color: inherit;
}

#footer {
	border-top: 1px solid #DDDDDD;
	border-bottom: 1px solid #DDDDDD;
	margin: 4% 5% 20px 5%;
	padding: 10px 0px 10px 0px;
	text-align: center;
	font-size: smaller;
	font-family: 'Lucida Grande', Geneva, Verdana, Arial, sans-serif;
}