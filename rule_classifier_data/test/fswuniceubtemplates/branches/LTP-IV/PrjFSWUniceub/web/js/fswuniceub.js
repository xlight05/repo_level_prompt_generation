/*!
 * Implementação da UI para o FSWUniCEUB
 * http://fsw.uniceub.br
 *
 * Esse javascript vai servir para definir eventos globais
 *
 * Dependencias: 
 *  - jQuery 1.5+
 *  - jQuery UI 1.8+
 *
 * @Author 2011 - Celula AT
 *
 */
$(function(){
        $("a.ajaxConteudo").live("click", function(){
            $.ajax({
              url: $(this).attr("href"),
              success: function(data){
                $('#conteudo').html(data);
                carregaEventos();
              }
            });
            return false;
	});
 });

 function carregaEventos() {
     $( ".campoData" ).datepicker({
            showOn: "button",
            buttonImage: "imagens/icones/calendario.png",
            buttonImageOnly: true
    });
 }