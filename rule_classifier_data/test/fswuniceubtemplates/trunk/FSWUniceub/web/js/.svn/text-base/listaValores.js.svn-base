/* 
 * @author Tiago Deusdara
 * Implementacao de LV - Lista de Valores
 */

/*
 * Este plugin so funcionara com links <a> e somente se apenas 1(um) estiver selecionado
 * 
 * O proposito deste plugin é interagir com Java e gerar um lista de valores.
 * Este plugin assume que os campos de destino(target) estão dentro de uma div ou span 
 * incluindo o link que ira gera a lv sendo que um sera um input do tipo hidden para 
 * guardar o codigo da opcao selecionada e outro input do tipo text que ira quardar 
 * o texto da opcao, é recomendado que este segundo seja desativado não permitindo 
 * modificação.
 * 
 * O nome|id|classe do campo filtro é sempre "filtroLV"
 * 
 * Ao declarar um lv deve-se informar seus atributos. Caso não seja informado
 * serao utilizado valores padroes:
 * 
 * idDialog:"lvDialog", //Deve ser modificado sempre que ouver mais de uma 
 *                        lv por formulario
 * url: link selecionado,
 * 
 * idTargetSpan: a tag pai do link selecinado // se o link não estiver contido 
 *                                               dentro da div ou span separando
 *                                               os inputs necessarios a lv podera
 *                                               bagunçar o formulario
 *                                               
 * data: nada // todos os paramentors extras selecionados com o jQuery
 * 
 * validate: nada //Campos selecinados com jQuery que o form validade ira verificar
 *                  o dialogo so será aberto se passar no validate
 * 
 * Exemplo:
 * $("a#lvTipo").lv();
 * $("a#lvTipo").lv({
 *           idDialog: "lvTipoDialog",
 *           url: "Controller?map=Carro.lvTipo",
 *           idTargetSpan: "tipoSpan",
 *           data: $("#codModelo, #numPassageiros"),
 *           validate: $("#nmeModelo, #numPassageiros")
 *       });    
 *       
 *       
 *  Return é o objeto dialogo gerado se funcinar, ou o proprio conteudo 
 *  selecionado se falhar  
 * 
 */
(function($){
    $.fn.lv = function(opicoes) {
        if($(this).filter("a").size() != 1){
            return this;
        }
        $(this).unbind();
        $(this).die();

        var defaults = {
            idDialog: $(this).attr('id')+"Dialog",
            url: $(this).attr("href"),
            idTargetSpan: $(this).parent().attr("id"),
            data: $(""),
            validate: undefined,
            titulo: 'Lista de Valores',
            altura: "auto",
            largura: 500
                
        };                 
        var options =  $.extend(defaults, opicoes);
        
        var lvUrl = options.url+"&puro=true";
        
        $("#"+options.idDialog).remove();
        
        var lvDialog = $('<div id="'+options.idDialog+'"></div>').dialog({ 
            title: options.titulo,
            modal: true,
            autoOpen: false,
            width:options.largura,
            height:options.altura,
            position: 'top',
            show: 'fade',
            hide: 'fade',
            open: function (){
                $(this).load(lvUrl,options.data.serialize());
                $("a.filtrarLV").live('click', function(e){
                    e.preventDefault();
                    e.stopPropagation();
                    $(this).parent().load(lvUrl+"&"+$(this).siblings(".filtroLV").serialize(),options.data.serialize());            
                }); 
            },
            close: function(){
                $("a.filtrarLV").die('click');
            },
            buttons: {
                "Escolher": function() {
                    $("#"+options.idTargetSpan+" input[type='hidden']").val($("#"+options.idDialog+" input[type='radio']:checked").val());
                    $("#"+options.idTargetSpan+" input[type='text']").val($("#"+options.idDialog+" input[type='radio']:checked + label").html());                     
                    $(this).dialog('close');
                },
                "Cancelar": function() {
                    $(this).dialog('close');                        
                }
            }                
        });
        $(this).after('<a class="limpaLV">Limpar</a>');
        
        $(this).bind("click",function(e) {
            e.preventDefault();
            e.stopPropagation();
            if(options.validate === undefined || options.validate.valid()){
                lvDialog.dialog('open');            
            }
        }); 
        
        return lvDialog;
    };   
})(jQuery);  