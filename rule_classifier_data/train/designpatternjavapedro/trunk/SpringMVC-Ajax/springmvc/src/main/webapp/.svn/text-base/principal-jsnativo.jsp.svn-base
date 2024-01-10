<HTML>
<HEAD>
<TITLE>::: XMLHttpRequest :::</TITLE>

</HEAD>

<BODY bgcolor="#FFFFFF" text="#000000">
	<DIV id="carregando"></DIV>
	<DIV id="urlContent">
		<input name="name" onkeyup="alteraURL(this);"> <a href="#"
			onclick="alteraURL('http://localhost:8080/test/welcome/');">Exemplo</a>
	</DIV>
</BODY>
</HTML>
<script language="javascript">
	var xmlhttp = getXmlHttpRequest();

	function getXmlHttpRequest() {
		if (window.XMLHttpRequest) {
			return new XMLHttpRequest();
		} else if (window.ActiveXObject) {
			return new ActiveXObject("Microsoft.XMLHTTP");
		}
	}

	function alteraURL(form) {
		var url = 'http://localhost:8080/test/spring/' + form.value;
		document.getElementById("carregando").innerHTML = "<img src='carregando.gif'>";
		xmlhttp.open("GET", url, true);
		xmlhttp.onreadystatechange = function() {
			if (xmlhttp.readyState == 4) {
				form.value = xmlhttp.responseXML
						.getElementsByTagName('usuario')[0]
						.getElementsByTagName('name')[0].firstChild.nodeValue;
				document.getElementById("carregando").innerHTML = "";
			}
		}
		xmlhttp.send(null);
	}
</script>