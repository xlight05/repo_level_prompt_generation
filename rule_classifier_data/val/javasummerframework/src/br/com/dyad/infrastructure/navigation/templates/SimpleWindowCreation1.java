package br.com.dyad.infrastructure.navigation.templates;

import java.util.Date;

import javax.servlet.http.HttpSession;

import br.com.dyad.infrastructure.entity.BaseEntity;
import br.com.dyad.infrastructure.widget.Action;
import br.com.dyad.infrastructure.widget.DyadEvents;
import br.com.dyad.infrastructure.widget.Interaction;
import br.com.dyad.infrastructure.widget.Window;
import br.com.dyad.infrastructure.widget.WidgetListener;
import br.com.dyad.infrastructure.widget.field.FieldBoolean;
import br.com.dyad.infrastructure.widget.field.FieldCombo;
import br.com.dyad.infrastructure.widget.field.FieldInteger;
import br.com.dyad.infrastructure.widget.field.FieldLookup;
import br.com.dyad.infrastructure.widget.field.FieldMemo;
import br.com.dyad.infrastructure.widget.field.FieldNumber;
import br.com.dyad.infrastructure.widget.field.FieldSimpleDate;
import br.com.dyad.infrastructure.widget.field.FieldString;
import br.com.dyad.infrastructure.widget.grid.VariableGrid;

//Primeiro, crie uma classe que herde do processo que você deseja.
//Este processo pode a classe mais abstrata de processos, no caso o Window,
//ou algum process já implementado. Neste exemplo, herdamos diretamente do process.
public class SimpleWindowCreation1 extends Window {	

	String help = "Este process demonstra como podemos criar simples processos";
	
	public SimpleWindowCreation1(HttpSession httpSession) {
		super(httpSession);
	}
	
	Action action1 = new Action( this, "Mostra Titulo do Window Pai" ){
		public void onClick() throws Exception{
			SimpleWindowCreation1 process = (SimpleWindowCreation1) this.getParent();
			
			if ( process.getParentWindow() != null ){			
				SimpleWindowCreation2 parentProcess = (SimpleWindowCreation2)process.getParentWindow(); 			
				parentProcess.gridDeVariaveis.inteiro2.setValue(123L);
				parentProcess.gridDeVariaveis.vigenciaFinal.setValue( new Date() );
				
				//parentProcess.action1.onClick();				
				process.addWindowToSincronization( parentProcess );			
			} else {
				process.alert( "Este process não possui process pai." );		
			}
		}
	};
	
	//Obrigatoriamente você precisa criar pelo menos uma interação para ser adicionada
	//ao process. 
	Interaction mainInteraction = new Interaction( this, "exibeVariaveis" ){
		
		//Sempre é necessário definir o método defineInteraction.
		@Override
		public void defineInteraction() throws Exception {
			SimpleWindowCreation1.this.action1.setValidateLastInteraction(false);
			this.getWindow().enableAndShowActions( SimpleWindowCreation1.this.action1 );
			//Adicionamos uma grid nesta interação. Isto é suficiente para que a grid passe a aparecer nesta interaction no process.
			this.add(gridDeVariaveis);
		}
	};

	GridDeVariaveis1 gridDeVariaveis = null;
	
	@Override
	public void defineWindow() throws Exception {
		gridDeVariaveis = new GridDeVariaveis1(this, "gridDeVariaveis");
	}
}


//Cria uma grid de variáveis com vários tipos de field.
class GridDeVariaveis1 extends VariableGrid {

	public GridDeVariaveis1(Window window, String name) throws Exception {
		super(window, name);
	}

	FieldInteger inteiro;
	FieldInteger inteiro2;
	FieldBoolean check;
	FieldBoolean check2;	
	FieldNumber totalMaximo;
	FieldNumber totalMaximo2;
	FieldString nome;
	FieldSimpleDate vigenciaFinal;
	FieldSimpleDate vigenciaInicial;
	FieldNumber totalMinimo;
	FieldNumber totalMinimo2;
	FieldSimpleDate vigenciaInicial2;
	FieldSimpleDate vigenciaFinal3;
	FieldMemo informacoes;		
	FieldString senha;		
	FieldCombo combo;		
	FieldLookup usuarios;

	public void defineGrid() throws Exception {
		
		inteiro = new FieldInteger(this);
		inteiro2 = new FieldInteger(this);
		check = new FieldBoolean(this);
		check2 = new FieldBoolean(this);	
		totalMaximo = new FieldNumber(this);
		totalMaximo2 = new FieldNumber(this);
		nome = new FieldString(this);
		vigenciaFinal = new FieldSimpleDate(this);
		vigenciaInicial = new FieldSimpleDate(this);
		totalMinimo = new FieldNumber(this);
		totalMinimo2 = new FieldNumber(this);
		vigenciaInicial2 = new FieldSimpleDate(this);
		vigenciaFinal3 = new FieldSimpleDate(this);
		informacoes = new FieldMemo(this);		
		senha = new FieldString(this);		
		combo = new FieldCombo(this);		
		usuarios = new FieldLookup(this);
		
		this.setTitle("Informacoes para busca");
		this.setHelp(help);

		Integer fieldCounter = 0;

		combo.setName("combo");
		combo.setLabel("Estado?");
		combo.setGroup("Grupo 1");
		combo.setOrder(fieldCounter++ * 100);
		combo.setOptions( BaseEntity.getOptions( 1, "Ce", 2, "Maranhão", 3, "Piaui" ) );

		check.setName("check");
		check.setLabel("Checado?");
		check.setGroup("Grupo 1");
		check.setOrder(fieldCounter++ * 100);

		check2.setName("check2");
		check2.setLabel("Segundo Checado?");
		check.setGroup("Grupo 1");
		check2.setOrder(fieldCounter++ * 100);

		inteiro.setName("primeiroInteiro");
		inteiro.setLabel("Preencha um inteiro");
		inteiro.setRequired(true);
		inteiro.setGroup("Grupo de Inteiros");
		inteiro.setHelp("help do campo inteiro!");
		inteiro.setOrder(fieldCounter++ * 100);	

		inteiro2.setName("primeiroInteiro2");
		inteiro2.setLabel("Segundo inteiro");
		inteiro2.setGroup("Grupo de Inteiros");
		inteiro2.setHelp("help do campo inteiro!");
		inteiro2.setRequired(true);
		inteiro2.setOrder(fieldCounter++ * 100);

		totalMaximo.setName("totalMaximo");
		totalMaximo.setLabel("Valor Máximo");
		totalMaximo.setGroup("Grupo de Números");
		totalMaximo.setOrder(fieldCounter++ * 100);			

		totalMinimo.setName("totalMinimo");
		totalMinimo.setLabel("Valor Minimo");
		totalMinimo.setMin(10.00);
		totalMinimo.setMax(20.00);
		totalMinimo.setGroup("Grupo de Números");
		totalMinimo.setReadOnly(true);
		totalMinimo.setOrder(fieldCounter++ * 100);			

		usuarios.setName("usuarioEscolhido");
		usuarios.setBeanName( "br.com.dyad.infrastructure.entity.User" );
		usuarios.setLabel("Escolha um usuário");
		usuarios.setHelp("help do campo lookup");
		usuarios.setGroup("Lookups");
		usuarios.setRequired(true);
		usuarios.setOrder(fieldCounter++ * 100);

		vigenciaInicial.setName("vigenciaInicial");
		vigenciaInicial.setLabel("Início");
		vigenciaInicial.setGroup("Vigências");
		vigenciaInicial.setHelp("Help do campo de incio");
		vigenciaInicial.setRequired(true);
		vigenciaInicial.setOrder(fieldCounter++ * 100);

		vigenciaFinal.setName("vigenciaFinal");
		vigenciaFinal.setLabel("Fim");
		vigenciaFinal.setGroup("Vigências");
		vigenciaFinal.setHelp("Help do campo de incio");
		vigenciaFinal.setRequired(true);
		vigenciaFinal.setOrder(fieldCounter++ * 100);

		nome.setName("nome");
		nome.setLabel("Informe o nome");
		nome.setRequired(true);
		nome.setOrder(fieldCounter++ * 100);

		totalMaximo2.setName("totalMaximo2");
		totalMaximo2.setLabel("Valor Máximo2");
		totalMaximo2.setWidth(225);
		totalMaximo2.setGroup("Segundo Grupo de Números");
		totalMaximo2.setOrder(fieldCounter++ * 100);			

		totalMinimo2.setName("totalMinimo2");
		totalMinimo2.setLabel("Valor Minimo2");
		totalMinimo2.setMin(10.00);
		totalMinimo2.setMax(20.00);
		totalMinimo2.setGroup("Segundo Grupo de Números");
		totalMinimo2.setDecimalPrecision(4);
		totalMinimo2.setOrder(fieldCounter++ * 100);			

		vigenciaInicial2.setName("vigenciaInicial2");
		vigenciaInicial2.setLabel("Início2");
		vigenciaInicial2.setGroup("Vigências 2");
		vigenciaInicial2.setHelp("Help do campo de incio");
		vigenciaInicial2.setRequired(true);
		vigenciaInicial2.setOrder(fieldCounter++ * 100);

		informacoes.setName("informacoes");
		informacoes.setLabel("Informações adicionais");
		informacoes.setRequired(true);
		informacoes.setOrder(fieldCounter++ * 100);

		vigenciaFinal3.setName("vigenciaFinal3");
		vigenciaFinal3.setLabel("Fim");
		vigenciaFinal3.setGroup("Vigências 2");
		vigenciaFinal3.setHelp("Help do campo de incio");
		vigenciaFinal3.setRequired(true);
		vigenciaFinal3.setOrder(fieldCounter++ * 100);

		senha.setName("pass");
		senha.setLabel("Senha");
		senha.setPassword(true);
		senha.setWidth(60);	
		//senha.removeMethodListener(DyadEvents.onBeforeChange);
		senha.addWidgetListener(DyadEvents.onBeforeChange, new WidgetListener(){
			@Override
			public void handleEvent(Object sender) throws Exception {
				FieldString senha = (FieldString) sender;
				System.out.println("Valor do field " + senha.getValue() );
			}			
		});
	}		
}