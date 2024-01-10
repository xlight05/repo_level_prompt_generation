package br.com.dyad.infrastructure.persistence.export;

import java.util.List;

/**
 * @author Danilo Sampaio
 * Essa interface é utilizada pelo DataExport para exportar os dados de uma lista no formato PDF.
 * A criação dessa interface tira a implementação da exportação do DataExport, facilitando
 * uma eventual mudança de API para exportação de dados.
 */
public interface PDFExporter {
	/**
	 * @author Danilo Sampaio
	 * @param datalist
	 * @return
	 * Exporta os dados da lista no formato PDF.
	 */
	public Object export( List datalist ) throws Exception;
}
