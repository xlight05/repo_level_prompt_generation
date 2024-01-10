<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html>
  <head>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">

    <title><%= application.getInitParameter("web.appname") %></title>
    
    <!--  Feuille de style nécessaire pour l'utilisation de GWT-EXT  -->
    <link rel="stylesheet" type="text/css" href="<%= application.getInitParameter("web.filepath") %>/js/ext/resources/css/ext-all.css"/>
 
 	<!-- Feuille de style de l'application -->
 	<link rel="stylesheet" type="text/css" href="<%= application.getInitParameter("web.filepath") %>/css/Web.css"/>
  
  	<!-- Feuille de style de l'application -->
 	<link id="theme" rel="stylesheet" type="text/css" href="<%= application.getInitParameter("web.filepath") %>/js/ext/resources/css/xtheme-silverCherry.css"/>

  
  </head>
 
 <body>
 	<!-- Chargement de l'application -->
 	<div id="loading">
 		<div class="loading-indicator">
			<img src="<%= application.getInitParameter("web.filepath") %>/js/ext/resources/images/default/shared/large-loading.gif" width="32" height="32"
			 	style="margin-right:8px;float:left;vertical-align:top;"/><%= application.getInitParameter("web.appname") %><br/>
			<span id="loading-msg">Chargement de l'application...</span>
		</div>
	</div>
	
 	<!--  Lignes nécessaires pour l'utilisation de gwtext avec GoogleMap -->
 	<script type="text/javascript" src="http://maps.google.com/maps?file=api&amp;v=2.x&amp;key=ABQIAAAAU3IKIVDCe5uqRZM-0dcUgRT5ss8ekEqOMG-TpVnxBnyzvKUk3xR1NStYK44sunmR0RYj6k9PvcyHaw"></script>
 	<script type="text/javascript" src="<%= application.getInitParameter("web.filepath") %>/js/ext/mapstraction.js"></script>
	
	<!-- Lignes nécessaires pour l'utilisation de GWT-EXT -->
    <script type="text/javascript" src="<%= application.getInitParameter("web.filepath") %>/js/ext/adapter/ext/ext-base.js"></script>
    <script type="text/javascript" src="<%= application.getInitParameter("web.filepath") %>/js/ext/ext-all.js"></script>
    
    <!-- Gestion de la langue francaise -->
	<script type="text/javascript" src="<%= application.getInitParameter("web.filepath") %>/js/ext/locale/ext-lang-fr.js"></script>	
    
	<!-- Script de l'application -->
	<script type="text/javascript" language="javascript" src="<%= application.getInitParameter("web.filepath") %>/fr.epsi.epsi5.Web.nocache.js"></script>
	
	 <!-- Chargement de l'application finie --> 	
	 <script type="text/javascript">Ext.get('loading').fadeOut({remove: true, duration:.25});</script>
  </body>
</html>
