package br.com.dyad.infrastructure.unit;

import java.lang.reflect.Method;

import javax.servlet.http.HttpSession;

import br.com.dyad.client.AppException;
import br.com.dyad.infrastructure.service.DyadService;
import br.com.dyad.infrastructure.widget.Action;
import br.com.dyad.infrastructure.widget.Interaction;
import br.com.dyad.infrastructure.widget.Window;
import br.com.dyad.infrastructure.widget.field.Field;
import br.com.dyad.infrastructure.widget.field.FieldBoolean;
import br.com.dyad.infrastructure.widget.field.FieldMemo;
import br.com.dyad.infrastructure.widget.field.FieldString;
import br.com.dyad.infrastructure.widget.grid.VariableGrid;

public class ExecuteTestsWindow extends Window {

	public TestCaseRunner runner = new TestCaseRunner();
	public String stackErrors = new String();
	
	public Action selectAll = new Action(this, "Select All"){
		@Override
		public void onClick() throws Exception {
			VariableGrid grid = (VariableGrid)ExecuteTestsWindow.this.getGrids().get(0);
			Boolean bTrue = new Boolean(true);
			for (int i = 0; i < grid.getFields().size(); i++) {
				Field field = grid.getFields().get(i);			
				if (field instanceof FieldBoolean){
					field.setValue(bTrue);
				}
			}	
		}
	};

	public Action unselectAll = new Action(this, "Unselect All"){
		@Override
		public void onClick() throws Exception {
			VariableGrid grid = (VariableGrid)ExecuteTestsWindow.this.getGrids().get(0);
			Boolean bFalse = new Boolean(false);
			for (int i = 0; i < grid.getFields().size(); i++) {
				Field field = grid.getFields().get(i);			
				if (field instanceof FieldBoolean){
					field.setValue(bFalse);
				}
			}	
		}
	};
	
	public Action executeTests = new Action(this, "Execute Tests"){
		@Override
		public void onClick() throws Exception {			
			Thread thread = new Thread(){
				@Override
				public void run() {
					try {
						//TODO conversar com o Helton sobre a restauração dos valores dos fields
						VariableGrid grid = (VariableGrid)ExecuteTestsWindow.this.getGrids().get(0);
						Object testObject = null;
						int size = grid.getFields().size();
						
						ExecuteTestsWindow.this.stackErrors = "";
						
						for (int i = 0; i < grid.getFields().size(); i++) {
						//for (int i = 0; i < 1; i++) {
							Field field = grid.getFields().get(i);
							showProgress("Pesquisando teste " + field.getLabel(), size, i + 1);
							
							if (field instanceof FieldBoolean){					
								TestDefinition testDef = (TestDefinition)field.getData("test");
								Boolean value = (Boolean)field.getValue();
								
								if (value != null && value){
									showProgress("Executando " + testDef.getClazz().getName() + "." + field.getLabel(), size, i + 1);
									if (testObject == null || !testObject.getClass().equals(testDef.getClazz())) {
										testObject = testDef.getClazz().newInstance();							
									}							
									
									Method m = (Method)field.getData("method");
									TestResult result = runner.executeTest(testObject, m);
									
									String resultName= field.getName() + "_RESULT";
									FieldString resultField = (FieldString)grid.getFieldByName(resultName);
									
 									if ( ! result.getResult()){														
										resultField.setValue(translate("Failed"));
										ExecuteTestsWindow.this.stackErrors += "-----" + "\r\n" + 
																				"Class: " + result.getClazz() + "\r\n" + 
																				"Method: " + result.getMethod() + "\r\n" + 
																				"Stack: " + DyadService.stackTraceToString( result.getException() ) + "\r\n\r\n";
									} else {
										resultField.setValue(translate("OK"));
									}
								}
							}
						}
						int x1 =0;
					} catch (Exception e) {
						throw new RuntimeException(e);
					}
				}
			};
			
			thread.start();			
		}
	};
	
	public Action showStacks = new Action(this, "Show Error Stacks"){
		@Override
		public void onClick() throws Exception {
			if ( ExecuteTestsWindow.this.stackErrors == null || ExecuteTestsWindow.this.stackErrors.equalsIgnoreCase("") ){				
				throw new AppException( "Stack is empty or errors were note found. Execute tests again." );
			}
			ExecuteTestsWindow.this.setNextInteraction( "showStack" );
		}
	};	
	


	public VariableGrid gridStack;
		
	public VariableGrid gridTests;
	
	public Interaction it = new Interaction(this, "it"){

		@Override
		public void defineInteraction() throws Exception {
			enableAndShowActions(executeTests);
			enableAndShowActions(selectAll);
			enableAndShowActions(unselectAll);
			enableAndShowActions(showStacks);
					
			this.add( gridTests );
		}
		
	};	

	public Interaction showStack = new Interaction(this, "showStack"){

		@Override
		public void defineInteraction() throws Exception {
			this.add( gridStack );
			gridStack.getFieldByName("stackField").setValue( ExecuteTestsWindow.this.stackErrors );
		}
		
	};	

	public ExecuteTestsWindow(HttpSession httpSession) {
		super(httpSession);
	}

	@Override
	public void defineWindow() throws Exception {		
		gridStack = new VariableGrid(this, "stack"){
			
			@Override
			public void defineGrid() throws Exception {
				
				this.setTitle(translate("Stacks"));					
				FieldMemo stackField = new FieldMemo(this, "stackField");
				stackField.setLabel("Stacks");
				stackField.setSaveLastValueAsDefault(false);
				stackField.setColumn(0);
				stackField.setHeight(400);
				stackField.setWidth(500);
			}
		};


		gridTests = new VariableGrid(this, "tests"){
			
			@Override
			public void defineGrid() throws Exception {
				
				this.setTitle(translate("Test cases"));
				
				runner.listClasses();
				int col = -1;

				for (TestDefinition testDef : runner.getClasses().values()) {
					
					for (Method m : testDef.getMethods().keySet()) {								
						String fieldName = testDef.getClazz().getName() + "_" + m.getName();
						
						FieldBoolean boolField = new FieldBoolean(this, fieldName);
						boolField.setLabel(m.getName());
						boolField.setData("test", testDef);
						boolField.setData("method", m);
						boolField.setGroup("Class: " + testDef.getClazz().getName());
						boolField.setSaveLastValueAsDefault(false);
						col++;
						//boolField.setColumn(0);
						boolField.setOrder(col * 100 );													
						
						FieldString resultField = new FieldString(this, fieldName + "_RESULT");
						resultField.setLabel("Result");
						resultField.setGroup("Class: " + testDef.getClazz().getName());
						resultField.setSaveLastValueAsDefault(false);
						col++;
						//resultField.setColumn(1);
						resultField.setOrder(col * 100);
						resultField.setReadOnly(true);						
					}
				}			
			}
		};	

	}
}
