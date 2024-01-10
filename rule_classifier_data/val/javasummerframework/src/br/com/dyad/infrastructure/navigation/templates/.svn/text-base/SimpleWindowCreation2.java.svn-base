package br.com.dyad.infrastructure.navigation.templates;
import javax.servlet.http.HttpSession;

import br.com.dyad.commons.data.DataList;
import br.com.dyad.infrastructure.entity.BaseEntity;
import br.com.dyad.infrastructure.persistence.DataListFactory;
import br.com.dyad.infrastructure.widget.Action;
import br.com.dyad.infrastructure.widget.Interaction;
import br.com.dyad.infrastructure.widget.Window;
import br.com.dyad.infrastructure.widget.field.FieldBoolean;
import br.com.dyad.infrastructure.widget.field.FieldCombo;
import br.com.dyad.infrastructure.widget.field.FieldInteger;
import br.com.dyad.infrastructure.widget.field.FieldLookup;
import br.com.dyad.infrastructure.widget.field.FieldMemo;
import br.com.dyad.infrastructure.widget.field.FieldNumber;
import br.com.dyad.infrastructure.widget.field.FieldSimpleDate;
import br.com.dyad.infrastructure.widget.field.FieldString;
import br.com.dyad.infrastructure.widget.grid.DataGrid;
import br.com.dyad.infrastructure.widget.grid.VariableGrid;

public class SimpleWindowCreation2 extends Window {	

	public SimpleWindowCreation2(HttpSession httpSession) {
		super(httpSession);
	}

	public String actionName1 = "Ir para a Interacao 2";
	public String actionName2 = "Ir para a Interacao 3";
	public String actionName3 = "Cadastro de Usuários";
	public String actionName4 = "Deletar registros...";	
	public String actionName5 = "Teste de consumo de Memória...";
	
	public DataList dataListFiltrado = DataListFactory.newDataList(getDatabase());
	public DataList dataListFiltrado2 = DataListFactory.newDataList(getDatabase());
	
	//public DataGrid gridDeDadosDeTeste;
	
	public DataGrid gridDeDadosDeTeste2;
	
	//public DataGrid gridUsuarios = new CrudGrid("gridDeUsuariosDeExemplo", "br.com.dyad.infrastructure.entity.User" );
	
	Action action1 = new Action(SimpleWindowCreation2.this, actionName1 ){
		public void onClick() throws Exception{
			SimpleWindowCreation2 process = (SimpleWindowCreation2) this.getParent();			
			process.setNextInteraction( process.nothing2 );
		}		
	};	
	
	Action action2 = new Action( SimpleWindowCreation2.this, actionName2 ){
		public void onClick() throws Exception{
			SimpleWindowCreation2 process = (SimpleWindowCreation2) this.getParent();
			System.out.println(this.getText());
			process.setNextInteraction( "nothing3" );
			//process.buscar();
		}
	};	
	
	Action action3 = new Action( SimpleWindowCreation2.this, actionName3 ){
		public void onClick() throws Exception{
			SimpleWindowCreation2.this.setNextInteraction( "exibeVariaveis", SimpleWindowCreation1.class, null, true, true );
		}
	};
	
	Action action4 = new Action(SimpleWindowCreation2.this, actionName4 ){
		public void onClick(){
			SimpleWindowCreation2 process = (SimpleWindowCreation2) this.getParent();
			System.out.println(this.getText());
			process.deletar();
		}
	};	


	Action action5 = new Action(SimpleWindowCreation2.this, actionName5 ){
		public void onClick(){			
			Thread thread = new Thread(){				
				@Override
				public void run() {
					Integer count = new Integer(SimpleWindowCreation2.this.gridDeVariaveis.inteiro.getValue().toString());											
					DataList[] lists = new DataList[count];
					System.out.println( "**************************** -> count=" + count );
					for (int i = 0; i < count; i++){
						SimpleWindowCreation2.this.showProgress( "Criando DataList..." + (i + 1), count-1, i);												
						System.out.println( "**************************** ->i=" + i + "; count=" + count );
						lists[ i ] = DataListFactory.newDataList(getDatabase());
						lists[ i ].executeQuery("from Teste");
					}	
				}
			};
			thread.start();
		}
	};	
	
	GridDeVariaveis gridDeVariaveis = null;
	
	public Interaction exibeVariaveis = new Interaction( this, "exibeVariaveis" ){
		@Override
		public void defineInteraction() throws Exception {
			SimpleWindowCreation2 process = (SimpleWindowCreation2) this.getWindow();

			action1.setValidateLastInteraction(false);
			action3.setValidateLastInteraction(false);
			action5.setValidateLastInteraction(false);

			process.enableAndShowActions( actionName1 );				
			process.enableAndShowActions( actionName3 );	
			process.enableAndShowActions( actionName5 );
	
			this.add(gridDeVariaveis);
		}
	};
	
	public Interaction nothing2 = new Interaction( this, "nothing2" ){
		@Override
		public void defineInteraction() {
			SimpleWindowCreation2 process = (SimpleWindowCreation2) this.getWindow();
			process.enableAndShowActions( actionName2 );												
		}
	};	
	
	public Interaction nothing3 = new Interaction( this, "nothing3" ){
		@Override
		public void defineInteraction() {
			SimpleWindowCreation2 process = (SimpleWindowCreation2) this.getWindow();				
			System.out.println("Rodou o defineInteraction da interaction: " + this.getName());
		}
	};	
	
	@Override
	public void defineWindow() throws Exception {
		gridDeVariaveis = new GridDeVariaveis(this, "gridDeVariaveis");
	}
		
	private void deletar(){
	}
}


class GridDeVariaveis extends VariableGrid{

	public GridDeVariaveis(Window window, String name) throws Exception{
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
		
		this.setTitle("Informações para busca");
		this.setHelp("Informações de help desta grid. Informações de help desta grid. Informações de help desta grid. Informações de help desta grid. ");

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
	}		
}