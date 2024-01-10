/*!
 * Implementação da UI para o projeto
 *
 * Esse javascript vai servir para definir eventos globais
 *
 * Dependencias: 
 *  - jQuery 1.5+
 *  - jQuery UI 1.8+
 *
 *
 */


$(document).ready(function() {
    jQuery(document).ajaxComplete(carregaEventos);
    carregaEventos();
    
    
    $("div.frameworkerror, div.error, div.notice, div.info, div.success").live("dblclick",function(){
        $(this).hide();
    });
    
    $("a.ajaxConteudo").live("click", function(e) {
        e.preventDefault();
        e.stopPropagation();               
        executarAjaxConteudo($(this).attr("href"),"ajax=true");
    });
    
    $('form.ajaxConteudo').live("submit", function(e) {
        e.preventDefault();
        e.stopPropagation();
        if($(this).valid()){
            executarAjaxConteudo("Controller",$(this).serialize()+"&ajax=true");
        }
    });
    
//    $("a.puroConteudo").live("click", function(e) {
//        e.preventDefault();
//        e.stopPropagation();                       
//        executarAjax($(this).attr("href"),"puro=true","#conteudoInterno");
//    });
//    
//    $('form.puroConteudo').live("submit", function(e) {
//        e.preventDefault();
//        e.stopPropagation();
//        if($(this).valid()){
//            executarAjax("Controller",$(this).serialize()+"&puro=true","#conteudoInterno");
//        }
//    });  
    
    $( "a.ajaxSubmit").live("click",function(e) {
        e.preventDefault();
        e.stopPropagation();
        $(this).closest('form').ajaxSubmit({
            type: 'POST',
            target: "#conteudo",
            replaceTarget: false,
            data: {
                ajax: 'true'
            }
        });
    }); 
    
    $(".submitRefresh").live("click",function(e){
        e.preventDefault();
        e.stopPropagation();
        $(this).closest('form').ajaxSubmit({
            success: function(){
                $(location).attr('href',"Controller");
            }            
        });
    });
    
    initMenu();
    
    $("#boxLV").dialog({
        width: 800,
        modal: true,
        height: 530,
        autoOpen: false
    });
    
}); 

function initMenu(){
//    $('#nav ul').slideUp();
    $('#nav > li > a').addClass("menuOpen"); 
    $("#nav > li > a").live("click",function(){ 
    //        if(false == $(this).next().is(':visible')) {
    //            $('#nav ul').slideUp(300);
    //        }
        $(this).toggleClass("menuOpen");    
        $(this).next().slideToggle(300);
    });
}

function executarAjaxConteudo(url,dados){    
    executarAjax(url, dados, "#conteudo");
}
function executarAjax(url,dados,container){    
    $.ajax({
        type: 'POST',
        cache: false,
        url: url,
        data: dados,
        success: function(data){
            $(container).html(data);
        }
    });
}
function resetInputs(selector) {
    $(':input',selector).not(':button, :submit, :reset, :hidden').val('').removeAttr('checked').removeAttr('selected');
}
function isOnTable(self){
    var $self = $(self);
    if($self.closest('tr').length > 0)
        return $self.closest('tr');
    else
        return $self.closest('#conteudoInterno');
}

function carregarButoesCRUD(){
    $("a.novo").addClass("ajaxConteudo");
    $("a.editar, a excluir, a.visualizar").unbind( "click" );
    $("a.editar, a excluir, a.visualizar").bind("click",function(e){
        e.preventDefault();
        e.stopPropagation();  
        var $self = $(this);
        executarAjaxConteudo($self.attr("href"),"ajax=true&"+isOnTable($self).find('input').serialize());        
    });
    //    $("a.visualizar").unbind( "click" );
    //    $("a.visualizar").bind("click",function(e) {
    //        e.preventDefault();
    //        e.stopPropagation();
    //        var $self = $(this);
    //        $('<div id="dialogVisualizar"></div>').dialog({
    //            autoOpen: true,
    //            modal: true,
    //            open: function ()
    //            {
    //                $(this).load($self.attr("href"), "puro=true&idt"+$self.closest('tr').find('input:hidden').val());
    //            },            
    //            title: 'Visualizacao de Item',
    //            buttons: {
    //                Ok: function(){
    //                    $(this).dialog('close');
    //                }
    //            }
    //        });
    //    });
    
    $("a.alterar").unbind( "click" );
    $("a.alterar").bind("click",function(e) {
        e.preventDefault();
        e.stopPropagation();
        var $self = $(this);
        if(isOnTable($self).find('input').valid()){
            $('<div id="dialogAlterar"></div>')
            .html('Tem certeza que deseja alterar este item')
            .dialog({
                autoOpen: true,
                modal: true,            
                title: 'Confirmacao de Alteracao',
                buttons: {
                    'Confirmar': function(){                   
                        executarAjaxConteudo($self.attr('href'),"ajax=true&"+isOnTable($self).find('input').serialize());
                        $(this).dialog('close');
                    },
                    'Cancelar': function() {
                        $(this).dialog('close');
                    }
                }
            });
        }
    });
    
    $("a.excluir").unbind( "click" );
    $("a.excluir").bind("click",function(e) {
        e.preventDefault();
        e.stopPropagation();
        var $self = $(this);
        $('<div id="dialogExcluir"></div>')
        .html('Tem certeza que deseja excluir este item')
        .dialog({
            autoOpen: true,
            modal: true,            
            title: 'Confirmacao de Exclusao',
            buttons: {
                'Confirmar': function(){
                    executarAjaxConteudo($self.attr('href'),"ajax=true&"+isOnTable($self).find('input').serialize());
                    $(this).dialog('close');
                },
                'Cancelar': function() {
                    $(this).dialog('close');
                }
            }
        });
    });
    
    $("a.incluir").unbind( "click" );
    $("a.incluir").bind("click",function(e) {
        e.preventDefault();
        e.stopPropagation();
        var $self = $(this);
        if(isOnTable($self).find('input').valid()){
            $('<div id="dialogIncluir"></div>')
            .html('Tem certeza que deseja incluir este item')
            .dialog({
                autoOpen: true,
                modal: true,            
                title: 'Confirmacao de Inclusao',
                buttons: {
                    'Confirmar': function(){
                        executarAjaxConteudo($self.attr('href'),"ajax=true&"+isOnTable($self).find('input').serialize());
                        $(this).dialog('close');
                    },
                    'Cancelar': function() {
                        $(this).dialog('close');
                    }
                }
            });
        }
    });
}
function carregaEventos() {
          
    buttonIcons();
    carregarButoesCRUD();
    
    $( ".radioset" ).buttonset();
    $(".multiselect").multiselect({
        selectedList: 4 // 0-based index
    });
    
    $(".limpaLV").bind("click",function(){
        $(':input',$(this).parent()).not(':button, :submit, :reset').val('');
    });

    var oTable = $('table.grid').dataTable( {
        "bPaginate": true,
        "bLengthChange": true,
        "bRetrieve":true,
        "bFilter": true,
        "bSort": true,
        "bInfo": true,
        "bAutoWidth": true,
        "sPaginationType": "full_numbers",
        "bJQueryUI": true,
        "oLanguage": {
            "sProcessing":   "Processando...",
            "sLengthMenu":   "Mostrar _MENU_ registros",
            "sZeroRecords":  "N&atilde;o foram encontrados resultados",
            "sInfo":         "Mostrando de _START_ at&eacute; _END_ de _TOTAL_ registros",
            "sInfoEmpty":    "Mostrando de 0 at&eacute; 0 de 0 registros",
            "sInfoFiltered": "(filtrado de _MAX_ registros no total)",
            "sInfoPostFix":  "",
            "sSearch":       "Buscar:",
            "sUrl":          "",
            "oPaginate": {
                "sFirst":    "Primeiro",
                "sPrevious": "Anterior",
                "sNext":     "Seguinte",
                "sLast":     "&Uacute;ltimo"
            }
        }
    });
        
    $( ".campoData" ).datepicker({
        showOn: "button",
        buttonImage: "imagens/icones/calendario.png",
        buttonImageOnly: true
    });
    
    $.mask.definitions['~']='[01]';
    $.mask.definitions['%']='[0123]';
    $.mask.definitions['&']='[012345]';
    /* Codigo de endere�amento postal de um endere�o */
    $(".campoCep").unmask();
    $(".campoCep").mask("99999-999");

    /* Cadastro Nacional da Pessoa Jur�dica */
    $(".campoCnpj").unmask();
    $(".campoCnpj").mask("99.999.999/9999-99");


    /* Cadastro de Pessoa Fisica */
    $(".campoCpf").unmask();
    $(".campoCpf").mask("999.999.999-99");

    /* Discagem direta a distancia */
    $(".campoDdd").unmask();
    $(".campoDdd").mask("99");

    /* Numero de um telefone */
    $(".campoTelefone").unmask();
    $(".campoTelefone").mask("(99) 9999-9999");

    /* Mascara para float */
    $(".campoFloat").unmask();
    $(".campoFloat").numeric(",");

    /* Data no formato dia/mes/ano, ex: 06/03/2008 */
    $(".campoData").unmask();
    $(".campoData").mask("%9/~9/9999");

    /* Mes e ano mes/ano, ex: 03/2008 */
    $(".campoMesAno").unmask();
    $(".campoMesAno").mask("~9/9999");

    /* Ano, ex: 2008 */
    $(".campoAno").unmask();
    $(".campoAno").mask("9999");

    /* Hora, ex: 12:35 */
    $(".campoHoraSimples").unmask();
    $(".campoHoraSimples").mask("99h&9m");

    /* Hora Composta, ex: 12:35:12 */
    $(".campoHoraComposta").unmask();
    $(".campoHoraComposta").mask("99h&9m&9s");

    /* Campo numerico */
    $("input.campoInteiro").numeric();

}