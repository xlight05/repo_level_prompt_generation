<%@ page contentType="image/svg+xml"%>
<%@ taglib uri="svglib" prefix="s1"%>
<jsp:useBean id="JSONRPCBridge" scope="session"
     class="org.jabsorb.JSONRPCBridge" />

<svg xmlns="http://www.w3.org/2000/svg" 
  xmlns:xlink="http://www.w3.org/1999/xlink" 
  width='1000px' height='50px'>
	
	<script type="text/ecmascript"><![CDATA[
		<jsp:include page="../script/zoom_controller.js"></jsp:include>
	]]></script>
	<s1:ZoomSquare/>
	
</svg>
