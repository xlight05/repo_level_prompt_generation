﻿<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>Home</title>
	</head>
	<body>
		<div id="section0">
			<h1>About tinWiki</h1>
			<p>
				Somewhere in January 2006 I got sick for two days and decided it was the perfect time to brush up on my knowledge of XML and learn
				<a href="http://en.wikipedia.org/wiki/JAXP" shape="rect" target="_blank">JAXP</a>
				(Java API for XML Processing).
			</p>
			<p>At the same time I was thinking using a wiki to organize growing team. The idea of a wiki really made sense, but almost all wiki's reviewed by myself and Mike were overly complex (example, need to learn a new mark-up language), hard to setup and not portable.</p>
			<p>
				Hence, the birth of tinWiki two days later. A nice thing about being sick was that I kept to the goal of following the
				<em>keep it simple</em>
				rule. All content from an end user perspective is stored in html. Actually, it is really xhtml which when I am parsing becomes xml. However, most people relate better when I say html. As far as I know, a wiki with this architecture is the first of its kind.
			</p>
			<p>Shoot foward a few weeks later where Garth contributes by building the front-end dhtml decoration and version 0.1 of tinWiki is deployed.</p>
			<p>Main contributers,</p>
			<ul>
				<li>Garth Dalhstrom</li>
				<li>Jinwoo Choi</li>
				<li>Michael Myers</li>
				<li>Tin Pham</li>
			</ul>
			<p>
				<a href="http://validator.w3.org/check?uri=referer" shape="rect">
					<img alt="Valid XHTML 1.0 Transitional" height="31" src="http://www.w3.org/Icons/valid-xhtml10" width="88" />
				</a>
			</p>
		</div>
		<div id="section1">
			<h2>Revision History</h2>
			<p>** Next Goals</p>
			<ol start="1">
				<li>Authentication and Authorization System (keep it simple, in property files and for advanced plug into directory system).</li>
				<li>Allow inserting of new sections.</li>
				<li>Allow uploading of image files.</li>
				<li>File checkout and locking mechanism.</li>
				<li>
					Search Engine,
					<a href="http://lucene.apache.org/" shape="rect" target="_blank">Lucene</a>
					and
					<a href="http://www.egothor.org/" shape="rect" target="_blank">Egothor</a>
					are the candidate add-on to use.
				</li>
				<li>Drop DownMenus.</li>
				<li>Administrative control of the main menu system.</li>
				<li>Printer Friendly.</li>
				<li>Allow message sending from one user to another.</li>
				<li>Footer date should reflect file date.</li>
				<li>Integrate in Log4j</li>
			</ol>
			<p>*** Version 0.3 (Major Changes)</p>
			<ul>
				<li>Moved to UTF-8 Files (sorry your data files will break)</li>
				<li>Even wikiData pages can now be xhtml compliant</li>
				<li>Moved everything to one standard, xhtml 1.1 transitional </li>
				<li>Upgrade SiteMesh which fixed some w3c bugs</li>
				<li>Fixed my own bug in implementing SiteMesh to allow decoration of html files</li>
				<li>Some minor presentation adjustments</li>
				<li>Updated default decorator to match</li>
				<li>[to do] add force refresh for updated html files</li>
				<li>[to do] update framework.txt document</li>
				<li>[to do] update tinmce (or maybe do in next version after tagging in subversion)</li>
				<li>...</li>
			</ul>
			<p>*** Version 0.18</p>
			<ul>
				<li>Formatting of XML. Finally, now when xml is saved it is nicely formatted and indented. Also the character encoding is now also properly set. The root of why I had so much trouble doing this? WebSphere 6.0 includes JDOM 1.0 beta 7 which collided with JDOM 1.0 I was using. Even though I was coding properly, my code kept on picking up the beta 7 library which did not have the new formatting features. Google searches show people resolving this by changing the class loader from parent_first to parent_last. I do not like this at all. In fact, I found it messes up Struts and who knows what else. So a simpler solution. Since I have the source for jdom, import the source and rename from org.jdom to orgx.jdom. Easy and no playing with the class loader.</li>
				<li>Went through and commented things better, did some refactoring of the code. Still more refactoring to do.</li>
			</ul>
			<p>*** Version 0.17</p>
			<ul>
				<li>Jinwoo Choi had the time to further investigate and allow use of special characters by declaring xml entities. Good job JinWoo! Code has been incorporated into the main build.</li>
				<li>Fixed menu so it handles browser font resizing nicely on the 2 major browsers. The menu items properly go on to new lines without overlapping.</li>
			</ul>
			<p>*** Version 0.15</p>
			<ul>
				<li> Allow use of non-breaking space. Not the best approach but it will do for now.</li>
			</ul>
			<p>*** Version 0.14</p>
			<ul>
				<li>Edit flow: return to edited page and clear cache if required.</li>
				<li>Editor: allow colour and highlight, provide indent and unindent buttons</li>
			</ul>
			<p>*** Version 0.12</p>
			<ul>
				<li>Turned off untested features and made available for use by the team.</li>
				<li>Made header and footer cosmetic changes as suggested by Franck Sevin and Abhay Singh.</li>
				<li>Edit flow: return to edited page and clear cache if required.</li>
			</ul>
			<p>*** Version 0.11 (Bulk of Wiki code built in two days)</p>
			<ul>
				<li>Decorator pattern applied via SiteMesh.</li>
				<li>MVC patternapplied via Struts.</li>
				<li>File saving with date and time stamp.</li>
				<li>Using JDOM to parse XHTML.</li>
				<li>WYSIWYG editor working.</li>
				<li>Edit functions applied after page rendition and separate from content.</li>
			</ul>
		</div>
	</body>
</html>