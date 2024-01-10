-- ======================================================================
-- Table: ${schema.tableName} (log)
-- ======================================================================
DROP TABLE IF EXISTS ${schema.tableName}_log;

CREATE TABLE ${schema.tableName}_log (
	change_type char(1) NOT NULL,

	<#list schema.schemaFields as field>
		<#assign isPrimaryKey = false>
		<#list schema.primaryKeyFields as pkField>
			<#if "${pkField.databaseFieldName}" == "${field.databaseFieldName}">
				<#assign isPrimaryKey = true>
			</#if>
		</#list>
		<#if isPrimaryKey>
	${field.databaseFieldName} ${field.databaseFieldType},
		</#if>
	</#list>

	<#list schema.schemaFields as field>
		<#assign isPrimaryKey = false>
		<#list schema.primaryKeyFields as pkField>
			<#if "${pkField.databaseFieldName}" == "${field.databaseFieldName}">
				<#assign isPrimaryKey = true>
			</#if>
		</#list>
		<#if !isPrimaryKey>
	${field.databaseFieldName}_old ${field.databaseFieldType} NULL,
	${field.databaseFieldName}_new ${field.databaseFieldType} NULL<#if field_has_next>,</#if>
		</#if>
	</#list>
);

