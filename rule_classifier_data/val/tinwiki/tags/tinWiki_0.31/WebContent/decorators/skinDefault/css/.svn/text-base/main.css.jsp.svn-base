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

#topnav li a {	
	color: #555555;
	text-decoration: none;
	border-bottom: none;
	border-right: 1px solid #DDDDDD;
	/*border-top: 1px solid #CCCCCC;*/
	font: 100%/200% Verdana, Geneva, Arial, Helvetica, sans-serif;

	/* Repeat in both ul and li because li uses display: inline. */
	/* Still playing here. This must not go beyond whatever the font area is or you will get overlapps when resizing the font through the browser. */
	padding-top: 3px; /* Larger value will push down the background images in buttons. */
	padding-bottom: 4px; /* To stop gaps in resizing in firefox 1.5.0.8 makes sure to add 1 additional pixel.*/
	
	/* Change this one area for menu tab padding. */
	padding-left: 15px;
	padding-right: 14px;

	background: #fff url(<%=request.getContextPath()%>/decorators/skinDefault/images/whtgrad.png);
}

#topnav li.active a { 

	color: #EEEEEE;
	/* When drawing the bg keep in mind that in ie6 the border top will cover up 1 pixel of the bg. */
	background-color: black;
	background: Black url(<%=request.getContextPath()%>/decorators/skinDefault/images/blkgrad.png);
	border-top: 1px solid #CCCCCC;
	font: bold;
}
