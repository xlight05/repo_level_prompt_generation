package br.com.dyad.client.grid;

import com.google.gwt.user.client.rpc.IsSerializable;

public enum FieldType implements IsSerializable {
	
	INTEGER,
	DOUBLE,
	STRING,
	CLASS,
	ENUM,
	DETAIL,
	DATE,
	TIME,
	TIMESTAMP,
	BOOLEAN,
	HTML_EDITOR,
	ARRAY;
	
}