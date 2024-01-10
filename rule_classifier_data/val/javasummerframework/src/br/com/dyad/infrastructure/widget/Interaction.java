package br.com.dyad.infrastructure.widget;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import br.com.dyad.infrastructure.annotations.SendToClient;
import br.com.dyad.infrastructure.widget.grid.DataGrid;
import br.com.dyad.infrastructure.widget.grid.Grid;
import br.com.dyad.infrastructure.widget.grid.VariableGrid;

public abstract class Interaction extends BaseServerWidget{
	
	@SendToClient
	private String name;
	private List<Grid> grids;
	private Window window;
	@SendToClient
	private String removedGrids = null;
		
	public Interaction(Window window){
		window.add(this);
		this.initialize();
	}
	public Interaction(Window window, String name) {
		this.setName(name);
		window.add(this);
		this.initialize();
	}
	
	private void initialize(){
		grids = new ArrayList<Grid>();
	}
	
	public abstract void defineInteraction() throws Exception;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}	
	
	public String getRemovedGrids() {
		return removedGrids;
	}

	public void setRemovedGrids(String removedGrids) {
		this.removedGrids = removedGrids;
	}

	@SuppressWarnings("unchecked")
	public boolean findGrid(Grid grid){
    	Grid foundGrid = (Grid)this.getObjectByServerId((ArrayList)this.getGrids(), grid.getObjectId());
    	return foundGrid != null;
    }
	
	public Grid getGridByName( String gridName ){
		for (Grid grid : this.getGrids()) {
			if ( grid.equals( gridName )){
				return grid;
			}
		}					
		return null;
	}    
    
    public boolean add(Grid grid) throws Exception{
    	if ( ! findGrid(grid) ){
    		this.getWindow().add(grid);    		
    		grid.setHttpSession(this.getHttpSession());
    		grid.defineFieldsAndDefineGrid();
    		this.grids.add(grid);    
    		return true;
    	}
    	return false;
    }	
	
	public void remove(Grid grid) throws Exception {
		if ( grid != null ){
			if ( findGrid(grid) ){

				this.grids.remove(grid);
				if ( this.removedGrids == null ){
					this.setRemovedGrids( grid.getObjectId() );
				} else {
					this.setRemovedGrids( this.removedGrids + ";" + grid.getObjectId() );
				}
			
				//Be careful, always remove the grid from window. The grid can be add to other interaction.
				this.getWindow().getGrids().remove(grid);
			}	
		}	
	}
    
    public Window getWindow() {
		return window;
	}

	public void setWindow(Window window) {
		this.window = window;
	}	
	
	public List<Grid> getGrids() {
		return grids;
	}

	public void setGrids(List<Grid> Grids) {
		this.grids = Grids;
	}

	@SuppressWarnings("unchecked")
	protected void addPropsToClientSincronizer() throws Exception {
		List<Grid> grids = this.getGrids();
		HashMap gridConfig = null;
		int i = 0;		
		for (Grid grid : grids) {
			if ( grid instanceof VariableGrid ){
				gridConfig = ((VariableGrid)grid).toClientSynchronizer();
			} else if ( grid instanceof DataGrid ){
				gridConfig = ((DataGrid)grid).toClientSynchronizer();
			}	
			if ( gridConfig != null && ! gridConfig.isEmpty()) {
				clientProps.put( "GRID" + i, gridConfig );
				i++;
			}	
		}
		if ( i > 0){
			clientProps.put( "gridCount", i );
		}
	}	
}