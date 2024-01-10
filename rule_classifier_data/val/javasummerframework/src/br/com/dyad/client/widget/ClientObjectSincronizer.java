package br.com.dyad.client.widget;

import java.util.HashMap;

import com.google.gwt.user.client.Timer;

import br.com.dyad.client.DyadAsyncCallback;
import br.com.dyad.client.DyadInfrastructure;
import br.com.dyad.client.GenericServiceAsync;
import br.com.dyad.client.TimerMessageBox;
import br.com.dyad.client.portal.Application;
import br.com.dyad.client.utilities.SincronizerActionTypes;
import br.com.dyad.client.widget.field.ClientField;
import br.com.dyad.client.widget.grid.ClientGrid;

public class ClientObjectSincronizer {	

	public static final String SINCRONIZER_SERVICE_NAME = "br.com.dyad.infrastructure.service.SynchronizerService";	
	protected ClientProcess clientProcess;
	protected Boolean forceUnmask = false; 
	public ClientObjectSincronizer(){}
	protected Timer timer = new Timer() {
		@Override
		public void run() {
			if (getForceUnmask() != null && !getForceUnmask()) {					
				clientProcess.getWindow().mask("Executing...");
			}				
		}
	};
	
	public Boolean getForceUnmask() {
		return forceUnmask;
	}
	
	public void setForceUnmask(Boolean forceUnmask) {
		this.forceUnmask = forceUnmask;
	}

	private void disableClientProcess( final ClientProcess clientProcess ){
		//Só trava o process depois de 2 segundos
		setForceUnmask(false);		
		timer.schedule(2000);
		//System.out.println( "D:" + clientProcess.getWindow().getFocusWidget() );
		//DyadInfrastructure.messageWindow = TimerMessageBox.wait("Executing", clientProcess.getWindow().getHeading(), "executing...");
		//clientProcess.getProcessToolBar().disable();
	}
	
	public void enableClientProcess( ClientProcess clientProcess ){
		setForceUnmask(true);
		clientProcess.getWindow().unmask();
		
		/*clientProcess.getProcessToolBar().enable();
		if ( DyadInfrastructure.messageWindow != null ){
			DyadInfrastructure.messageWindow.close();
		}
		System.out.println( "E:" + clientProcess.getWindow().getFocusWidget() );*/
	}

	 @SuppressWarnings("unchecked")
	 public void newServerWindow(String serverWindowPath, Long windowId, HashMap props,
			final Boolean showMessageWindow) {
		String actionToExecute = SincronizerActionTypes.NewWindow.toString();

		HashMap params = DyadInfrastructure.getRpcParams();
		params.put("actionToExecute", actionToExecute);
		params.put("windowId", windowId);
		params.put("serverWindowPath", serverWindowPath);
		params.put("props", props);

		if (showMessageWindow) {
			String title = (String)props.get("title");
			title = title == null ? "" : title;
			DyadInfrastructure.messageWindow = TimerMessageBox.wait(DyadInfrastructure.translate("Abrindo") + " " + title, "", DyadInfrastructure.translate("Abrindo") + " " + title + " ...");
			DyadInfrastructure.messageWindow.setModal(true);
		}

		GenericServiceAsync proxy = DyadInfrastructure.getGenericServiceProxy();
		proxy.getServiceValue(ClientObjectSincronizer.SINCRONIZER_SERVICE_NAME,
				params, new DyadAsyncCallback() {
					/*
					 * public void onFailure(Throwable arg0) {
					 * messageWindow.close(); try { arg0.printStackTrace();
					 * ExceptionDialog.show(arg0); } finally {
					 * enableClientProcess( clientProcess ); } }
					 */

					public void onSuccess(Object arg0) {
						HashMap result = (HashMap) arg0;
						createWindow(result, showMessageWindow);
					}
				});
	}
	 
	public void createWindow(HashMap result, Boolean showMessageWindow){
		try {
			String serverWindowId = (String) result.get("serverWindowId");
			HashMap resultMap = (HashMap) result.get("clientSincronizerMap");
			clientProcess.setServerObjectId(serverWindowId);
			clientProcess.synchronizeProcessWithServerInformation(resultMap);
			clientProcess.getWindow().show();
			clientProcess.getWindow().layout();
			DyadInfrastructure.clientProcessCreated.put( serverWindowId, clientProcess );
			
			System.out.println("chegou o serverWindowId=" + serverWindowId);

			setFocusOnFirstWidget();
		} catch (Exception e) {
			e.printStackTrace();
			if (showMessageWindow) {
				ExceptionDialog.show(e);
			}
		} finally {
			if (showMessageWindow) {
				DyadInfrastructure.messageWindow.close();
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	public void executeServerMethod(String serverWindowId, String serverMethod, HashMap props) {
		HashMap params = DyadInfrastructure.getRpcParams();		
		params.put("actionToExecute", SincronizerActionTypes.ExecuteServerMethod.toString());
		params.put("serverWindowId", serverWindowId);
		params.put(SincronizerActionTypes.ExecuteServerMethod.toString(), serverMethod);
		params.put("props", props);
		
		GenericServiceAsync proxy = DyadInfrastructure.getGenericServiceProxy();	
		proxy.getServiceValue( ClientObjectSincronizer.SINCRONIZER_SERVICE_NAME, params, new DyadAsyncCallback() {

			public void onSuccess(Object arg0) {
				try {	
					HashMap result = (HashMap)arg0;
					HashMap resultMap = (HashMap)result.get("clientSincronizerMap");
					clientProcess.synchronizeProcessWithServerInformation(resultMap);
					clientProcess.getWindow().doLayout();
				} catch (Exception e) {
					ExceptionDialog.show(e);
				} finally {
					enableClientProcess( clientProcess );
				}	
			}
		});
	}

	private void setFocusOnFirstWidget(){		
		//TODO corrigir este método
		/*ClientInteraction interaction = clientProcess.getCurrentVisibleInteraction();
		List<ClientGrid> grids = interaction.getClientGrids();
		if (grids.size() > 0){			
			ClientGrid firstGrid = (ClientGrid)grids.get(0);
			if ( firstGrid != null ){
				if ( firstGrid instanceof ClientVariableGrid ){
					setFocusOnFirstFormField(firstGrid );
				} else if ( firstGrid instanceof ClientDataGrid ){					
					ClientDataGrid cdg = (ClientDataGrid) firstGrid;
					if ( cdg.getViewMode().equals(ClientDataGrid.VIEW_MODE_FORMVIEW) ){
						setFocusOnFirstFormField(firstGrid );
					} else if ( cdg.getViewMode().equals(ClientDataGrid.VIEW_MODE_TABLEVIEW) ){
						//-- tableview: foco na primeira linha da primeira grid
						ClientDataGrid grid = ((ClientDataGrid) firstGrid);
						
						grid.tableViewGrid.getSelectionModel().select(0, false);
						grid.tableViewGrid.focus();
						grid.tableViewGrid.getView().focusRow(0);
					}
				}
			}
		}*/
	}
	
	private void setFocusOnFirstFormField( ClientGrid firstGrid ){
		//TODO corrigir este método
		/*FormPanelCustomized formViewPanel = firstGrid.getFormViewPanel();	
		int index = 0;
		if ( index <= formViewPanel.getFields().size() - 1 ){
			Field fld = formViewPanel.getFields().get( index );
			int i = 0;
			while ( ( fld instanceof LabelField || ! fld.isEnabled() || ! fld.isVisible() )  && i < formViewPanel.getFields().size() ){
				try {
					fld = formViewPanel.getFields().get( i );
					i++;
				} catch(Exception e){
					e.printStackTrace();
					break;
				}
			}
			if ( fld != null ){
				//ClientVariableGrid grid = ((ClientVariableGrid) firstGrid);
				//grid.getFormViewPanel().layout();
				fld.focus();
			}	
		}*/
	}
	
	public void setClientProcess(ClientProcess clientProcess) {
		this.clientProcess = clientProcess;
	}

	@SuppressWarnings("unchecked")
	public void destroyServerWindow(String serverWindowId) {
		String actionToExecute = SincronizerActionTypes.DestroyWindow.toString();
		
		HashMap params = DyadInfrastructure.getRpcParams();		
		params.put("actionToExecute", actionToExecute);

		params.put("serverWindowId", serverWindowId);
				
		GenericServiceAsync proxy = DyadInfrastructure.getGenericServiceProxy();	
		proxy.getServiceValue( ClientObjectSincronizer.SINCRONIZER_SERVICE_NAME, params, new DyadAsyncCallback() {
					public void onFailure(Throwable arg0){
						try {
							arg0.printStackTrace();
							ExceptionDialog.show(arg0);
						} finally {
							enableClientProcess( clientProcess );						
						}
					}

					public void onSuccess(Object arg0) {
						try {
							HashMap result = (HashMap)arg0;
							Boolean serverProcessDestroied = (Boolean)result.get("processDestroied");
							String serverWindowId = (String) result.get("serverWindowId");
							DyadInfrastructure.clientProcessCreated.put( serverWindowId, null );

							System.out.println("Ja no Cliente destruiu o serverWindowId? " + serverProcessDestroied);
						} catch ( Exception e ){
							ExceptionDialog.show(e);
						}
					}
				});
	}		

	@SuppressWarnings("unchecked")
	public void dispatchServerActionClick(String serverWindowId, String serverActionId, HashMap fieldValuesToSave ) {
		String actionToExecute = SincronizerActionTypes.ActionClick.toString();
		
		HashMap params = DyadInfrastructure.getRpcParams();		
		params.put("actionToExecute", actionToExecute);

		params.put("serverWindowId", serverWindowId);
		params.put("serverActionId", serverActionId);
		params.put("fieldValuesToSave", fieldValuesToSave);
				
		disableClientProcess( clientProcess );
		
		GenericServiceAsync proxy = DyadInfrastructure.getGenericServiceProxy();	
		proxy.getServiceValue( ClientObjectSincronizer.SINCRONIZER_SERVICE_NAME, params, new DyadAsyncCallback() {
					public void onFailure(Throwable arg0) {							
						try{
							arg0.printStackTrace();						
							ExceptionDialog.show(arg0);
						} finally {	
							enableClientProcess( clientProcess );
						}	
					}

					public void onSuccess(Object arg0) {
						try {	
							HashMap result = (HashMap)arg0;
							HashMap resultMap = (HashMap)result.get("clientSincronizerMap");
							clientProcess.synchronizeProcessWithServerInformation(resultMap);
							clientProcess.getWindow().doLayout();
							System.out.println("Sucesso ao dar o click da action. Ja no client!");
						} catch (Exception e) {
							e.printStackTrace();
							ExceptionDialog.show(e);
						} finally {
							enableClientProcess( clientProcess );
						}	
					}
				});
	}		

	
	@SuppressWarnings("unchecked")
	public void dispatchSetFieldValue(String serverWindowId, String serverGridId, String serverFieldId, Object value){
		String actionToExecute = SincronizerActionTypes.SetFieldValue.toString();
		
		HashMap params = DyadInfrastructure.getRpcParams();		
		params.put("actionToExecute", actionToExecute);

		params.put("serverWindowId", serverWindowId);
		params.put("serverGridId", serverGridId);
		params.put("serverFieldId", serverFieldId);
		params.put("value", value);				
		
		//disableClientProcess( clientProcess );
		
		GenericServiceAsync proxy = DyadInfrastructure.getGenericServiceProxy();	
		proxy.getServiceValue( ClientObjectSincronizer.SINCRONIZER_SERVICE_NAME, params, new DyadAsyncCallback() {
					public void onFailure(Throwable arg0) {
						try {
							arg0.printStackTrace();
							ExceptionDialog.show(arg0);
						} finally {	
							enableClientProcess( clientProcess );
						}	
					}

					public void onSuccess(Object arg0) {
						try {	
							HashMap result = (HashMap)arg0;
							HashMap resultMap = (HashMap)result.get("clientSincronizerMap");
							clientProcess.synchronizeProcessWithServerInformation(resultMap);
						} catch (Exception e) {
							e.printStackTrace();
							ExceptionDialog.show(e);
						} finally {								
							//clientProcess.doLayout();
							//enableClientProcess( clientProcess );
							//System.out.println("Sucesso ao dar o set value. Ja no client!");
						}	
					}
				});
	}	

	@SuppressWarnings("unchecked")
	public void dispatchGetFieldInformation(String serverWindowId, String serverGridId, String serverFieldId){
		String actionToExecute = SincronizerActionTypes.GetFieldInformation.toString();
		
		HashMap params = DyadInfrastructure.getRpcParams();		
		params.put("actionToExecute", actionToExecute);

		params.put("serverWindowId", serverWindowId);
		params.put("serverGridId", serverGridId);
		params.put("serverFieldId", serverFieldId);
		
		GenericServiceAsync proxy = DyadInfrastructure.getGenericServiceProxy();	
		proxy.getServiceValue( ClientObjectSincronizer.SINCRONIZER_SERVICE_NAME, params, new DyadAsyncCallback() {
					public void onFailure(Throwable arg0) {
						arg0.printStackTrace();
						ExceptionDialog.show(arg0);
					}

					public void onSuccess(Object arg0) {
						try {	
							HashMap result = (HashMap)arg0;
							HashMap resultMap = (HashMap)result.get("clientSincronizerMap");
							ClientField.showFieldInformation(resultMap);
							System.out.println("Sucesso ao dar o get field information. Ja no client!");							
						} catch (Exception e) { 
							e.printStackTrace();
							ExceptionDialog.show(e);
						}
					}
				});
	}	


	
	@SuppressWarnings("unchecked")
	public void dispatchServerInteractionBack(String serverWindowId) {
		String actionToExecute = SincronizerActionTypes.InteractionBack.toString();
		
		HashMap params = DyadInfrastructure.getRpcParams();		
		params.put("actionToExecute", actionToExecute);

		params.put("serverWindowId", serverWindowId);
				
		disableClientProcess( clientProcess );
		
		GenericServiceAsync proxy = DyadInfrastructure.getGenericServiceProxy();	
		proxy.getServiceValue( ClientObjectSincronizer.SINCRONIZER_SERVICE_NAME, params, new DyadAsyncCallback() {
					public void onFailure(Throwable arg0) {
						try {
							arg0.printStackTrace();
							ExceptionDialog.show(arg0);
						} finally {	
							enableClientProcess( clientProcess );
						}	
					}

					public void onSuccess(Object arg0) {
						try {	
							HashMap result = (HashMap)arg0;
							HashMap resultMap = (HashMap)result.get("clientSincronizerMap");
							clientProcess.synchronizeProcessWithServerInformation(resultMap);
							clientProcess.getWindow().doLayout();
							System.out.println("Sucesso ao dar o Back. Ja no client!");
						} catch (Exception e) {
							e.printStackTrace();
							ExceptionDialog.show(e);
						} finally {
							enableClientProcess( clientProcess );
						}
					}
				});
	}		


	@SuppressWarnings("unchecked")
	public void dispatchServerInteractionGo(String serverWindowId) {
		String actionToExecute = SincronizerActionTypes.InteractionGo.toString();
		
		HashMap params = DyadInfrastructure.getRpcParams();		
		params.put("actionToExecute", actionToExecute);

		params.put("serverWindowId", serverWindowId);
				
		disableClientProcess( clientProcess );
		
		GenericServiceAsync proxy = DyadInfrastructure.getGenericServiceProxy();	
		proxy.getServiceValue( ClientObjectSincronizer.SINCRONIZER_SERVICE_NAME, params, new DyadAsyncCallback() {
					public void onFailure(Throwable arg0) {
						try {
							arg0.printStackTrace();
							ExceptionDialog.show(arg0);
						} finally { 	
							enableClientProcess( clientProcess );
						}	
					}

					public void onSuccess(Object arg0) {
						try {	
							HashMap result = (HashMap)arg0;
							//String serverProcessId = (String)result.get("serverProcessId");						
							HashMap resultMap = (HashMap)result.get("clientSincronizerMap");
							clientProcess.synchronizeProcessWithServerInformation(resultMap);
							clientProcess.getWindow().doLayout();
							System.out.println("Sucesso ao dar o Back. Ja no client!");
						} catch (Exception e) {
							e.printStackTrace();
							ExceptionDialog.show(e);
						} finally {
							enableClientProcess( clientProcess );
						}
					}
				});
	}

	@SuppressWarnings("unchecked")
	public void dispatchServerDataGridFirst(String serverWindowId, String serverDataGridId) {
		String actionToExecute = SincronizerActionTypes.GridFirst.toString();
		
		HashMap params = DyadInfrastructure.getRpcParams();		
		params.put("actionToExecute", actionToExecute);

		params.put("serverWindowId", serverWindowId);
		params.put("serverDataGridId", serverDataGridId);
				
		disableClientProcess( clientProcess );
		
		GenericServiceAsync proxy = DyadInfrastructure.getGenericServiceProxy();	
		proxy.getServiceValue( ClientObjectSincronizer.SINCRONIZER_SERVICE_NAME, params, new DyadAsyncCallback() {
					public void onFailure(Throwable arg0) {
						try {
							arg0.printStackTrace();
							ExceptionDialog.show(arg0);
						} finally {	
							enableClientProcess( clientProcess );
						}	
					}

					public void onSuccess(Object arg0) {
						try {	
							HashMap result = (HashMap)arg0;
							//String serverProcessId = (String)result.get("serverProcessId");						
							HashMap resultMap = (HashMap)result.get("clientSincronizerMap");
							clientProcess.synchronizeProcessWithServerInformation(resultMap);
							clientProcess.getWindow().doLayout();
							System.out.println("Sucesso ao dar o first da grid. Ja no client!");
						} catch (Exception e) {
							e.printStackTrace();
							ExceptionDialog.show(e);
						} finally {
							enableClientProcess( clientProcess );
						}
					}
				});
	}		
	
	@SuppressWarnings("unchecked")
	public void dispatchServerDataGridPrevious(String serverWindowId, String serverDataGridId) {
		String actionToExecute = SincronizerActionTypes.GridPrevious.toString();
		
		HashMap params = DyadInfrastructure.getRpcParams();		
		params.put("actionToExecute", actionToExecute);

		params.put("serverWindowId", serverWindowId);
		params.put("serverDataGridId", serverDataGridId);
				
		disableClientProcess( clientProcess );
		
		GenericServiceAsync proxy = DyadInfrastructure.getGenericServiceProxy();	
		proxy.getServiceValue( ClientObjectSincronizer.SINCRONIZER_SERVICE_NAME, params, new DyadAsyncCallback() {
					public void onFailure(Throwable arg0) {
						try {
							arg0.printStackTrace();
							ExceptionDialog.show(arg0);
						} finally {	
							enableClientProcess( clientProcess );
						}	
					}

					public void onSuccess(Object arg0) {
						try {	
							HashMap result = (HashMap)arg0;
							//String serverProcessId = (String)result.get("serverProcessId");						
							HashMap resultMap = (HashMap)result.get("clientSincronizerMap");
							clientProcess.synchronizeProcessWithServerInformation(resultMap);
							clientProcess.getWindow().doLayout();
							System.out.println("Sucesso ao dar o previous da grid. Ja no client!");
						} catch (Exception e) {
							e.printStackTrace();
							ExceptionDialog.show(e);
						} finally {
							enableClientProcess( clientProcess );
						}
					}
				});
	}	
	
	@SuppressWarnings("unchecked")
	public void dispatchServerDataGridNext(String serverWindowId, String serverDataGridId) {
		String actionToExecute = SincronizerActionTypes.GridNext.toString();
		
		HashMap params = DyadInfrastructure.getRpcParams();		
		params.put("actionToExecute", actionToExecute);

		params.put("serverWindowId", serverWindowId);
		params.put("serverDataGridId", serverDataGridId);
				
		disableClientProcess( clientProcess );
		
		GenericServiceAsync proxy = DyadInfrastructure.getGenericServiceProxy();	
		proxy.getServiceValue( ClientObjectSincronizer.SINCRONIZER_SERVICE_NAME, params, new DyadAsyncCallback() {
					public void onFailure(Throwable arg0) {
						try {
							arg0.printStackTrace();
							ExceptionDialog.show(arg0);
						} finally {	
							enableClientProcess( clientProcess );
						}	
					}

					public void onSuccess(Object arg0) {
						try {	
							HashMap result = (HashMap)arg0;
							//String serverProcessId = (String)result.get("serverProcessId");						
							HashMap resultMap = (HashMap)result.get("clientSincronizerMap");
							clientProcess.synchronizeProcessWithServerInformation(resultMap);
							clientProcess.getWindow().doLayout();
							System.out.println("Sucesso ao dar o Next da grid. Ja no client!");
						} catch (Exception e) {
							e.printStackTrace();
							ExceptionDialog.show(e);
						} finally {
							enableClientProcess( clientProcess );
						}
					}
				});
	}	
	
	@SuppressWarnings("unchecked")
	public void dispatchServerDataGridLast(String serverWindowId, String serverDataGridId) {
		String actionToExecute = SincronizerActionTypes.GridLast.toString();
		
		HashMap params = DyadInfrastructure.getRpcParams();		
		params.put("actionToExecute", actionToExecute);

		params.put("serverWindowId", serverWindowId);
		params.put("serverDataGridId", serverDataGridId);
				
		disableClientProcess( clientProcess );
		
		GenericServiceAsync proxy = DyadInfrastructure.getGenericServiceProxy();	
		proxy.getServiceValue( ClientObjectSincronizer.SINCRONIZER_SERVICE_NAME, params, new DyadAsyncCallback() {
					public void onFailure(Throwable arg0) {
						try {
							arg0.printStackTrace();
							ExceptionDialog.show(arg0);
						} finally {	
							enableClientProcess( clientProcess );
						}	
					}

					public void onSuccess(Object arg0) {
						try {	
							HashMap result = (HashMap)arg0;
							//String serverProcessId = (String)result.get("serverProcessId");						
							HashMap resultMap = (HashMap)result.get("clientSincronizerMap");
							clientProcess.synchronizeProcessWithServerInformation(resultMap);
							clientProcess.getWindow().doLayout();
							System.out.println("Sucesso ao dar o last da grid. Ja no client!");
						} catch (Exception e) {
							e.printStackTrace();
							ExceptionDialog.show(e);
						} finally {
							enableClientProcess( clientProcess );
						}
					}
				});
	}

	@SuppressWarnings("unchecked")
	public void dispatchServerDataGridInsert(String serverWindowId, String serverDataGridId) {
		String actionToExecute = SincronizerActionTypes.GridInsert.toString();
		
		HashMap params = DyadInfrastructure.getRpcParams();		
		params.put("actionToExecute", actionToExecute);

		params.put("serverWindowId", serverWindowId);
		params.put("serverDataGridId", serverDataGridId);
				
		disableClientProcess( clientProcess );
		
		GenericServiceAsync proxy = DyadInfrastructure.getGenericServiceProxy();	
		proxy.getServiceValue( ClientObjectSincronizer.SINCRONIZER_SERVICE_NAME, params, new DyadAsyncCallback() {
					public void onFailure(Throwable arg0) {
						try {
							arg0.printStackTrace();
							ExceptionDialog.show(arg0);
						} finally {	
							enableClientProcess( clientProcess );
						}	
					}

					public void onSuccess(Object arg0) {
						try {	
							HashMap result = (HashMap)arg0;
							//String serverProcessId = (String)result.get("serverProcessId");						
							HashMap resultMap = (HashMap)result.get("clientSincronizerMap");
							clientProcess.synchronizeProcessWithServerInformation(resultMap);
							clientProcess.getWindow().doLayout();
							System.out.println("Sucesso ao dar o insert da grid. Ja no client!");
						} catch (Exception e) {
							e.printStackTrace();
							ExceptionDialog.show(e);
						} finally {
							enableClientProcess( clientProcess );
						}
					}
				});
	}

	@SuppressWarnings("unchecked")
	public void dispatchServerDataGridCancel(String serverWindowId, String serverDataGridId) {
		String actionToExecute = SincronizerActionTypes.GridCancel.toString();
		
		HashMap params = DyadInfrastructure.getRpcParams();		
		params.put("actionToExecute", actionToExecute);

		params.put("serverWindowId", serverWindowId);
		params.put("serverDataGridId", serverDataGridId);
				
		disableClientProcess( clientProcess );
		
		GenericServiceAsync proxy = DyadInfrastructure.getGenericServiceProxy();	
		proxy.getServiceValue( ClientObjectSincronizer.SINCRONIZER_SERVICE_NAME, params, new DyadAsyncCallback() {
					public void onFailure(Throwable arg0) {
						try {
							arg0.printStackTrace();
							ExceptionDialog.show(arg0);
						} finally {	
							enableClientProcess( clientProcess );
						}	
					}

					public void onSuccess(Object arg0) {
						try {	
							HashMap result = (HashMap)arg0;
							//String serverProcessId = (String)result.get("serverProcessId");						
							HashMap resultMap = (HashMap)result.get("clientSincronizerMap");
							clientProcess.synchronizeProcessWithServerInformation(resultMap);
							clientProcess.getWindow().doLayout();
							System.out.println("Sucesso ao dar o cancel da grid. Ja no client!");
						} catch (Exception e) {
							e.printStackTrace();
							ExceptionDialog.show(e);
						} finally {
							enableClientProcess( clientProcess );
						}
					}
				});
	}

	@SuppressWarnings("unchecked")
	public void dispatchServerDataGridPost(String serverWindowId, String serverDataGridId ){
		String actionToExecute = SincronizerActionTypes.GridPost.toString();
		
		HashMap params = DyadInfrastructure.getRpcParams();		
		params.put("actionToExecute", actionToExecute);

		params.put("serverWindowId", serverWindowId);
		params.put("serverDataGridId", serverDataGridId);
				
		disableClientProcess( clientProcess );
		
		GenericServiceAsync proxy = DyadInfrastructure.getGenericServiceProxy();	
		proxy.getServiceValue( ClientObjectSincronizer.SINCRONIZER_SERVICE_NAME, params, new DyadAsyncCallback() {
					public void onFailure(Throwable arg0) {
						try {
							arg0.printStackTrace();
							ExceptionDialog.show(arg0);
						} finally {	
							enableClientProcess( clientProcess );
						}	
					}

					public void onSuccess(Object arg0) {
						try {	
							HashMap result = (HashMap)arg0;
							//String serverProcessId = (String)result.get("serverProcessId");						
							HashMap resultMap = (HashMap)result.get("clientSincronizerMap");
							clientProcess.synchronizeProcessWithServerInformation(resultMap);
							clientProcess.getWindow().doLayout();
							System.out.println("Sucesso ao dar o post da grid. Ja no client!");
						} catch (Exception e) {
							e.printStackTrace();
							ExceptionDialog.show(e);
						} finally {
							enableClientProcess( clientProcess );
						}
					}
				});		
	}

	@SuppressWarnings("unchecked")
	public void dispatchServerDataGridDelete(String serverWindowId, String serverDataGridId ){
		String actionToExecute = SincronizerActionTypes.GridDelete.toString();
		
		HashMap params = DyadInfrastructure.getRpcParams();		
		params.put("actionToExecute", actionToExecute);

		params.put("serverWindowId", serverWindowId);
		params.put("serverDataGridId", serverDataGridId);
				
		disableClientProcess( clientProcess );
		
		GenericServiceAsync proxy = DyadInfrastructure.getGenericServiceProxy();	
		proxy.getServiceValue( ClientObjectSincronizer.SINCRONIZER_SERVICE_NAME, params, new DyadAsyncCallback() {
					public void onFailure(Throwable arg0) {
						try {
							arg0.printStackTrace();
							ExceptionDialog.show(arg0);
						} finally {	
							enableClientProcess( clientProcess );
						}	
					}

					public void onSuccess(Object arg0) {
						try {	
							HashMap result = (HashMap)arg0;
							//String serverWindowId = (String)result.get("serverWindowId");						
							HashMap resultMap = (HashMap)result.get("clientSincronizerMap");
							clientProcess.synchronizeProcessWithServerInformation(resultMap);
							clientProcess.getWindow().doLayout();
							System.out.println("Sucesso ao dar o delete da grid. Ja no client!");
						} catch (Exception e) {
							e.printStackTrace();
							ExceptionDialog.show(e);
						} finally {
							enableClientProcess( clientProcess );
						}
					}
				});		
	}

	@SuppressWarnings("unchecked")
	public void dispatchServerDataGridChangeView(String serverWindowId, String serverDataGridId) {
		String actionToExecute = SincronizerActionTypes.GridChangeView.toString();
		
		HashMap params = DyadInfrastructure.getRpcParams();		
		params.put("actionToExecute", actionToExecute);

		params.put("serverWindowId", serverWindowId);
		params.put("serverDataGridId", serverDataGridId);
				
		disableClientProcess( clientProcess );
		
		GenericServiceAsync proxy = DyadInfrastructure.getGenericServiceProxy();	
		proxy.getServiceValue( ClientObjectSincronizer.SINCRONIZER_SERVICE_NAME, params, new DyadAsyncCallback() {
					public void onFailure(Throwable arg0) {
						try {
							arg0.printStackTrace();
							ExceptionDialog.show(arg0);
						} finally {	
							enableClientProcess( clientProcess );
						}	
					}

					public void onSuccess(Object arg0) {
						try {	
							HashMap result = (HashMap)arg0;
							HashMap resultMap = (HashMap)result.get("clientSincronizerMap");
							clientProcess.synchronizeProcessWithServerInformation(resultMap);
							clientProcess.getWindow().doLayout();
							System.out.println("Sucesso ao dar o ChangeView da grid. Ja no client!");
						} catch (Exception e) {
							e.printStackTrace();
							ExceptionDialog.show(e);
						} finally {
							enableClientProcess( clientProcess );
						}
					}
				});			
		}


	@SuppressWarnings("unchecked")
	public void dispatchTableViewSelectRecord(String serverWindowId, String serverGridId, Long objectId){
		String actionToExecute = SincronizerActionTypes.TableViewGridSelectRecord.toString();
		
		HashMap params = DyadInfrastructure.getRpcParams();		
		params.put("actionToExecute", actionToExecute);

		params.put("serverWindowId", serverWindowId);
		params.put("serverGridId", serverGridId);
		params.put("objectId", objectId);				
		
		GenericServiceAsync proxy = DyadInfrastructure.getGenericServiceProxy();	
		proxy.getServiceValue( ClientObjectSincronizer.SINCRONIZER_SERVICE_NAME, params, new DyadAsyncCallback() {
					public void onFailure(Throwable arg0) {
						try {
							arg0.printStackTrace();
							ExceptionDialog.show(arg0);
						} finally {
							//enableClientProcess( clientProcess );
						}
					}

					public void onSuccess(Object arg0) {
						try {	
							HashMap result = (HashMap)arg0;
							HashMap resultMap = (HashMap)result.get("clientSincronizerMap");
							clientProcess.synchronizeProcessWithServerInformation(resultMap);
							System.out.println("Sucesso ao dar o select record . Ja no client!");
						} catch (Exception e) {
							e.printStackTrace();
							ExceptionDialog.show(e);
						} finally {
							//enableClientProcess( clientProcess );
						}

					}
				});
	}
	
	@SuppressWarnings("unchecked")
	public void dispatchServerDataGridLicense(String serverWindowId, String serverDataGridId, Long licenseId) {
		String actionToExecute = SincronizerActionTypes.GridLicense.toString();
		
		HashMap params = DyadInfrastructure.getRpcParams();		
		params.put("actionToExecute", actionToExecute);

		params.put("serverWindowId", serverWindowId);
		params.put("serverDataGridId", serverDataGridId);
		params.put("serverDataGridLicenseId", licenseId);
				
		disableClientProcess( clientProcess );
		
		GenericServiceAsync proxy = DyadInfrastructure.getGenericServiceProxy();	
		proxy.getServiceValue( ClientObjectSincronizer.SINCRONIZER_SERVICE_NAME, params, new DyadAsyncCallback() {
					public void onFailure(Throwable arg0) {
						arg0.printStackTrace();
						ExceptionDialog.show(arg0);
						enableClientProcess( clientProcess );
					}

					public void onSuccess(Object arg0) {
						try {	
							HashMap result = (HashMap)arg0;						
							HashMap resultMap = (HashMap)result.get("clientSincronizerMap");
							clientProcess.synchronizeProcessWithServerInformation(resultMap);
							enableClientProcess( clientProcess );
							System.out.println("Sucesso ao dar o insert da grid. Ja no client!");
						} catch (Exception e) {
							e.printStackTrace();
							ExceptionDialog.show(e);
						}
					}
				});
	}
	
	@SuppressWarnings("unchecked")
	public void dispatchServerDataGridSearch(String serverWindowId, String serverDataGridId, String fieldName, String searchToken) {
		String actionToExecute = SincronizerActionTypes.GridSearch.toString();
		
		HashMap params = DyadInfrastructure.getRpcParams();		
		params.put("actionToExecute", actionToExecute);

		params.put("serverWindowId", serverWindowId);
		params.put("serverDataGridId", serverDataGridId);
		params.put("serverDataGridSearchToken", searchToken);
		params.put("serverDataGridFieldName", fieldName);
				
		disableClientProcess( clientProcess );
		
		GenericServiceAsync proxy = DyadInfrastructure.getGenericServiceProxy();	
		proxy.getServiceValue( ClientObjectSincronizer.SINCRONIZER_SERVICE_NAME, params, new DyadAsyncCallback() {
					public void onFailure(Throwable arg0) {
						arg0.printStackTrace();
						ExceptionDialog.show(arg0);
						enableClientProcess( clientProcess );
					}

					public void onSuccess(Object arg0) {
						try {	
							HashMap result = (HashMap)arg0;						
							HashMap resultMap = (HashMap)result.get("clientSincronizerMap");
							clientProcess.synchronizeProcessWithServerInformation(resultMap);
							enableClientProcess( clientProcess );
							System.out.println("Sucesso ao fazer a busca da grid. Ja no client!");
						} catch (Exception e) {
							e.printStackTrace();
							ExceptionDialog.show(e);
						}
					}
				});
	}
	
	@SuppressWarnings("unchecked")
	public void dispatchServerDataSearchNextOccur(String serverWindowId, String serverDataGridId, String fieldName, String searchToken) {
		String actionToExecute = SincronizerActionTypes.GridSearchNextOccur.toString();
		
		HashMap params = DyadInfrastructure.getRpcParams();		
		params.put("actionToExecute", actionToExecute);

		params.put("serverWindowId", serverWindowId);
		params.put("serverDataGridId", serverDataGridId);
		params.put("serverDataGridSearchToken", searchToken);
		params.put("serverDataGridFieldName", fieldName);
				
		disableClientProcess( clientProcess );
		
		GenericServiceAsync proxy = DyadInfrastructure.getGenericServiceProxy();	
		proxy.getServiceValue( ClientObjectSincronizer.SINCRONIZER_SERVICE_NAME, params, new DyadAsyncCallback() {
					public void onFailure(Throwable arg0) {
						arg0.printStackTrace();
						ExceptionDialog.show(arg0);
						enableClientProcess( clientProcess );
					}

					public void onSuccess(Object arg0) {
						try {	
							HashMap result = (HashMap)arg0;						
							HashMap resultMap = (HashMap)result.get("clientSincronizerMap");
							clientProcess.synchronizeProcessWithServerInformation(resultMap);
							enableClientProcess( clientProcess );
							System.out.println("Sucesso ao fazer a busca da próxima ocorrência da grid. Ja no client!");
						} catch (Exception e) {
							e.printStackTrace();
							ExceptionDialog.show(e);
						}
					}
				});
	}
	
	@SuppressWarnings("unchecked")
	public void dispatchServerDataExport(String serverWindowId, String serverDataGridId, String format ) {
		String actionToExecute = SincronizerActionTypes.GridExport.toString();
		
		HashMap params = DyadInfrastructure.getRpcParams();		
		params.put("actionToExecute", actionToExecute);

		params.put("serverWindowId", serverWindowId);
		params.put("serverDataGridId", serverDataGridId);
		params.put("format", format);		
		disableClientProcess( clientProcess );
		
		GenericServiceAsync proxy = DyadInfrastructure.getGenericServiceProxy();	
		proxy.getServiceValue( ClientObjectSincronizer.SINCRONIZER_SERVICE_NAME, params, new DyadAsyncCallback() {
					public void onFailure(Throwable arg0) {
						arg0.printStackTrace();
						ExceptionDialog.show(arg0);
						enableClientProcess( clientProcess );
					}

					public void onSuccess(Object arg0) {
						try {	
							HashMap result = (HashMap)arg0;						
							HashMap resultMap = (HashMap)result.get("clientSincronizerMap");
							clientProcess.synchronizeProcessWithServerInformation(resultMap);
							enableClientProcess( clientProcess );
							System.out.println("Sucesso ao exportar dados da grid. Ja no client!");
						} catch (Exception e) {
							e.printStackTrace();
							ExceptionDialog.show(e);
						}
					}
				});
	}
}
