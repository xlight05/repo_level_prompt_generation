<HTML>
<HEAD>
<TITLE>::: XMLHttpRequest :::</TITLE>
<script src="jquery-1.7.2.min.js" type="text/javascript"></script>

</HEAD>

<BODY bgcolor="#FFFFFF" text="#000000">
	<DIV id="input">
		<input name="name" id="zaperoso">
	</DIV>
	<DIV id="Sugestao"></DIV>
</BODY>
</HTML>
<script language="javascript">
	$("#zaperoso")
			.bind(
					"change",
					function() {
						$
								.ajax({
									type : "GET",
									url : "http://localhost:8080/test/spring/"
											+ $(zaperoso).attr("value"),
									dataType : "xml",
									success : function(xml) {
										$(xml).find('usuario').each(
												function() {
													var uName = $(this).find(
															'name').text();
													$("#Sugestao")
															.append(uName);
												});
									},
									error : function() {
										alert("Ocorreu um erro inesperado durante o processamento.");
									}
								});
					});
</script>