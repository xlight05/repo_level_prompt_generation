package br.com.dyad.client.portal;

import java.util.HashMap;

import br.com.dyad.client.DyadInfrastructure;
import br.com.dyad.client.gxt.widget.customized.ProcessWindowCustomized;
import br.com.dyad.client.widget.ClientProcess;

import com.extjs.gxt.ui.client.Style.LayoutRegion;
import com.extjs.gxt.ui.client.Style.Scroll;
import com.extjs.gxt.ui.client.data.ModelData;
import com.extjs.gxt.ui.client.widget.TabItem;
import com.extjs.gxt.ui.client.widget.TabPanel;
import com.extjs.gxt.ui.client.widget.Viewport;
import com.extjs.gxt.ui.client.widget.custom.Portlet;
import com.extjs.gxt.ui.client.widget.layout.BorderLayout;
import com.extjs.gxt.ui.client.widget.layout.BorderLayoutData;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.layout.FlowLayout;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Classe que representa a tela inicial do sistema
 * 
 * @author Eduardo
 */
public class Application {
    public static Application application = new Application();
	
	private ApplicationPortal portal;
	private Viewport viewport;
	
	public Application() {
		//Cria o portal
		portal = new ApplicationPortal();		
		
		//o viewport é usado para redimensionar e centralizar a tela inicial
		viewport = new Viewport();
	    viewport.setLayout(new BorderLayout());
	    
	    viewport.add(portal, new BorderLayoutData(LayoutRegion.CENTER));
	    RootPanel.get().add(viewport);
		
	}
	
	public static Application getInstance() {
		return application;
	}
	
	/**
	 * Cria uma tela em uma aba separada
	 * 
	 * @author Eduardo
	 */
	public void createWindow(Long processId, String iconStyle, String windowClass, 
			HashMap props, Boolean newWindow, Boolean showModal, Boolean showMesssageWindow){
		
		TabItem window = new TabItem((String)props.get("title"));
		window.setClosable(true);
	    window.setScrollMode(Scroll.AUTO);
	    window.setIconStyle(iconStyle);
	    window.setLayout(new FitLayout());
	    //window.setScrollMode(Scroll.NONE);
	    
	    ClientProcess clientProcess = new ClientProcess(newWindow, showModal, processId);
		clientProcess.getNewServerInstance(windowClass, processId, props, showMesssageWindow);

		clientProcess.getWindow().setLayout(new FlowLayout(10));
		if (iconStyle != null && !iconStyle.equals("")) {
			clientProcess.getWindow().setIconStyle(iconStyle);
		}
		ProcessWindowCustomized pr = clientProcess.getWindow();
		
		//Adiciona a nova janela no TabItem e em seguida no painel principal
		window.add(pr);
		portal.getPanel().add(window);
		portal.getPanel().setSelection(window);
	}
	
	public void createWindow(ModelData data) {
		String windowClass = (String)data.get("process");
		String title = (String)data.get("name");
		String toolTip = "Id: " + data.get("id") + "; product: " + data.get("licenseId") + ".";
		String iconStyle = (String)data.get("cssName");;
		String cssName = (String)data.get("cssName");  		
		Long processId = (Long)data.get("id");
																
		HashMap props = new HashMap();
		props.put("title", title);
		createWindow( processId, iconStyle, windowClass, props, true, false, true );
	}

	public ApplicationPortal getPortal() {
		return portal;
	}

	public void setPortal(ApplicationPortal portal) {
		this.portal = portal;
	}

	public Viewport getViewport() {
		return viewport;
	}

	public void setViewport(Viewport viewport) {
		this.viewport = viewport;
	}
	
	public TabItem getCurrentWindow() {
		return portal.getPanel().getSelectedItem(); 
	}

}
