/*
 * ${copyright}
 */
package ${audit.model.javaPackage};

<#list audit.model.javaImports as import>
import ${import};
</#list>
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.ringus.rinex.bo.server.vo.AuditAware;

/**
 * @author ${author}
 *
 */
public class ${audit.model.javaName} implements AuditAware {
	/**
	 * Determines if a de-serialized file is compatible with this class. Maintainers must change this value if and only
	 * if the new version of this class is not compatible with old versions.
	 */

	private String changeType;

	<#list audit.model.modelFields as field>
		<#assign isPrimaryKey = false>
		<#list  audit.model.primaryKeyFields as pkField>
			<#if "${pkField.fieldName}" == "${field.fieldName}">
				<#assign isPrimaryKey = true>
			</#if>
		</#list>
		<#if isPrimaryKey>
	private ${field.fieldType} ${field.fieldName};
		</#if>
	</#list>

	<#list audit.model.modelFields as field>
		<#assign isPrimaryKey = false>
		<#list  audit.model.primaryKeyFields as pkField>
			<#if "${pkField.fieldName}" == "${field.fieldName}">
				<#assign isPrimaryKey = true>
			</#if>
		</#list>
		<#if !isPrimaryKey>
	private ${field.fieldType} old${field.propertyName};
	private ${field.fieldType} new${field.propertyName};
		</#if>
	</#list>

	public String getChangeType() {
		return this.changeType;
	}

	public void setChangeType(String changeType) {
		this.changeType = changeType;
	}

	<#list audit.model.modelFields as field>
		<#assign isPrimaryKey = false>
		<#list  audit.model.primaryKeyFields as pkField>
			<#if "${pkField.fieldName}" == "${field.fieldName}">
				<#assign isPrimaryKey = true>
			</#if>
		</#list>
		<#if isPrimaryKey>
	public ${field.fieldType} get${field.propertyName}() {
		return ${field.fieldName};
	}

	public void set${field.propertyName}(${field.fieldType} ${field.fieldName}) {
		this.${field.fieldName} = ${field.fieldName};
	}
		<#else>
	public ${field.fieldType} getOld${field.propertyName}() {
		return old${field.propertyName};
	}

	public void setOld${field.propertyName}(${field.fieldType} old${field.propertyName}) {
		this.old${field.propertyName} = old${field.propertyName};
	}

	public ${field.fieldType} getNew${field.propertyName}() {
		return new${field.propertyName};
	}

	public void setNew${field.propertyName}(${field.fieldType} new${field.propertyName}) {
		this.new${field.propertyName} = new${field.propertyName};
	}
		</#if>

	</#list>
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
			.append("changeType", this.changeType)
		<#list audit.model.modelFields as field>
			<#assign isPrimaryKey = false>
			<#list  audit.model.primaryKeyFields as pkField>
				<#if "${pkField.fieldName}" == "${field.fieldName}">
					<#assign isPrimaryKey = true>
				</#if>
			</#list>
			<#if isPrimaryKey>
			.append("${field.fieldName}", this.${field.fieldName})
			<#else>
			.append("old${field.propertyName}", this.old${field.propertyName})
			.append("new${field.propertyName}", this.new${field.propertyName})
			</#if>
			</#list>
			.toString();
	}
}
