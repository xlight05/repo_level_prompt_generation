package br.com.dyad.infrastructure.navigation.tests;

import br.com.dyad.commons.data.AppTempEntity;
import br.com.dyad.infrastructure.annotations.MetaField;
import br.com.dyad.infrastructure.entity.BaseEntity;

public class BeanEditor extends BaseEntity implements AppTempEntity {
	
	private String descricao;
	private String textEditor;
	
	public String getDescricao() {
		return descricao;
	}
	
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	public String getTextEditor() {
		return textEditor;
	}
	
	public void setTextEditor(String textEditor) {
		this.textEditor = textEditor;
	}
	
	@Override
	public void defineFields() {
		super.defineFields();
		
		defineField("textEditor", 
			MetaField.htmlEditor, true,
			MetaField.width, 700,
			MetaField.height, 500);
	}

}
