package br.com.dyad.infrastructure.navigation.tests;

import javax.servlet.http.HttpSession;

import br.com.dyad.commons.data.DataList;
import br.com.dyad.infrastructure.annotations.MetaField;
import br.com.dyad.infrastructure.entity.BaseEntity;
import br.com.dyad.infrastructure.entity.User;
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

public class ExemploDeCriacaoDeWindow extends Window {	

	public ExemploDeCriacaoDeWindow(HttpSession httpSession) {
		super(httpSession);
	}

	public String actionName1 = "Ir para a Interacao 2";
	public String actionName2 = "Ir para a Interacao 3";
	public String actionName3 = "Alterar registros...";
	public String actionName4 = "Deletar registros...";	

	public DataList dataListFiltrado = DataListFactory.newDataList(getDatabase());
	public DataList dataListFiltrado2 = DataListFactory.newDataList(getDatabase());
	
	//public DataGrid gridDeDadosDeTeste;
	
	public DataGrid gridDeDadosDeTeste2;
	
	//public DataGrid gridUsuarios = new CrudGrid("gridDeUsuariosDeExemplo", "br.com.dyad.infrastructure.entity.User" );
	
	Action action1 = new Action(ExemploDeCriacaoDeWindow.this, actionName1 ){
		public void onClick(){
			ExemploDeCriacaoDeWindow process = (ExemploDeCriacaoDeWindow) this.getParent();			
			//System.out.println(this.getText());
		}		
	};	
	
	Action action2 = new Action( ExemploDeCriacaoDeWindow.this, actionName2 ){
		public void onClick() throws Exception{
			ExemploDeCriacaoDeWindow process = (ExemploDeCriacaoDeWindow) this.getParent();
			System.out.println(this.getText());
			process.setNextInteraction("nothing3");
			//process.buscar();
		}
	};	
	
	Action action3 = new Action( ExemploDeCriacaoDeWindow.this, actionName3 ){
		public void onClick(){
			ExemploDeCriacaoDeWindow process = (ExemploDeCriacaoDeWindow) this.getParent();
			System.out.println(this.getText());
			process.alterar();
		}
	};
	
	Action action4 = new Action(ExemploDeCriacaoDeWindow.this, actionName4 ){
		public void onClick(){
			ExemploDeCriacaoDeWindow process = (ExemploDeCriacaoDeWindow) this.getParent();
			System.out.println(this.getText());
			process.deletar();
		}
	};	

	GridDeVariaveis gridDeVariaveis = null;
	
	@Override
	public void defineWindow() throws Exception {
		gridDeVariaveis = new GridDeVariaveis("gridDeVariaveis");
		/*gridDeDadosDeTeste = new DataGrid( "gridDeDadosDeTeste" ){			
			@Override
			public void defineGrid() throws Exception {
				this.defineField(
						"login", 
						MetaField.label, "LoginX"
					);	

				super.defineGrid();
			}
		};*/

		gridDeDadosDeTeste2 = new DataGrid( this, "gridDeDadosDeTeste2" ){
			@Override
			public void defineGrid() throws Exception {
				this.defineField(
						"id", 
						MetaField.visible, true
					);	

				super.defineGrid();
			}
		};		

		dataListFiltrado.executeQuery("from User where classId = '" + BaseEntity.getClassIdentifier(User.class) + "'" );
		dataListFiltrado.setCommitOnSave(true);
		
		dataListFiltrado2.executeQuery( "from Teste" );
		dataListFiltrado2.setCommitOnSave(true);
		
		//TODO width nao esta sendo setado quando vem do construtor do field
		//TODO implementar grids details
		
		add( new Interaction( this, "exibeVariaveis" ){
			@Override
			public void defineInteraction() throws Exception {
				ExemploDeCriacaoDeWindow process = (ExemploDeCriacaoDeWindow) this.getWindow();
				process.enableAndShowActions( actionName1 );				
								
				//gridDeDadosDeTeste.setBeanName("br.com.dyad.infrastructure.entity.User");
				//gridDeDadosDeTeste.setDataList(dataListFiltrado);
				//gridDeDadosDeTeste.setTitle("Users");
				

				//gridDeDadosDeTeste2.setBeanName("br.com.dyad.infrastructure.entity.Teste");
				//gridDeDadosDeTeste2.setDataList(dataListFiltrado2);
				//gridDeDadosDeTeste2.setTitle("References");
				
				//gridDeDadosDeTeste2.setCanInsert(false);
				//gridDeDadosDeTeste2.setCanDelete(false);
				
				this.add(gridDeVariaveis);
				//this.add(gridDeDadosDeTeste);
				//this.add(gridDeDadosDeTeste2);
				
			}
		});					

		add( new Interaction( this, "nothing2" ){
			@Override
			public void defineInteraction() {
				ExemploDeCriacaoDeWindow process = (ExemploDeCriacaoDeWindow) this.getWindow();
				process.enableAndShowActions( actionName2 );												
				System.out.println("Rodou o defineInteraction da interaction: " + this.getName());				
			}
		});	
	
		add( new Interaction( this, "nothing3" ){
			@Override
			public void defineInteraction() {
				ExemploDeCriacaoDeWindow process = (ExemploDeCriacaoDeWindow) this.getWindow();				
				System.out.println("Rodou o defineInteraction da interaction: " + this.getName());
			}
		});
	}

	private void inserir(){
	}
	
	private void buscar(){
	}

	private void alterar(){
	}
	
	private void deletar(){
	}
	
	class GridDeVariaveis extends VariableGrid{

		public GridDeVariaveis(String name) throws Exception{
			super(ExemploDeCriacaoDeWindow.this, name);
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
}