package br.com.dyad.infrastructure.widget.field;

import br.com.dyad.infrastructure.annotations.SendToClient;
import br.com.dyad.infrastructure.widget.grid.Grid;

/**
 * @enterprise Dyad & Associados (http://www.dyad.com.br)
 * @author Helton Gonçalves (helton@dyad.com.br;heltongoncalves@gmail.com)
 */
public abstract class FieldAbstractNumber extends Field{
	
	public FieldAbstractNumber(Grid grid) throws Exception{
		super(grid);
	}

	public FieldAbstractNumber(Grid grid, String name ) throws Exception{
		super(grid, name);
	}

	@SendToClient
	private Double min;
	@SendToClient
	private Double max;
	@SendToClient
	private Integer decimalPrecision;
	@SendToClient
	private Boolean negativeInRed;                   

	@Override
	protected void initializeField() {
		super.initializeField();
		this.setDecimalPrecision(2);
		this.setNegativeInRed(true);
		this.setNegativeInRed(true);
	}
	
	public Double getMin() {
		return min;
	}

	public void setMin(Double min) {
		this.min = min;
	}

	public Double getMax() {
		return max;
	}

	public void setMax(Double max) {
		this.max = max;
	}

	public Integer getDecimalPrecision() {
		return decimalPrecision;
	}

	public void setDecimalPrecision(Integer decimalPrecision) {
		this.decimalPrecision = decimalPrecision;
	}


	public Boolean getNegativeInRed() {
		return negativeInRed;
	}

	public void setNegativeInRed(Boolean negativeInRed) {
		this.negativeInRed = negativeInRed;
	}
}
