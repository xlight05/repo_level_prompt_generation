/*
 * ${copyright}
 */
package ${java.model.javaPackage};

<#list java.model.javaImports as import>
import ${import};
</#list>
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.xfuze.model.SequenceAwareModel;
import org.xfuze.model.TraceAwareModel;

/**
 * @author ${author}
 *
 */
public class ${java.model.javaName} implements SequenceAwareModel, TraceAwareModel {
	/**
	 * Determines if a de-serialized file is compatible with this class. Maintainers must change this value if and only
	 * if the new version of this class is not compatible with old versions.
	 */

	<#list java.model.modelFields as field>
	private ${field.fieldType} ${field.fieldName};
	</#list>

	<#list java.model.modelFields as field>
	public ${field.fieldType} get${field.propertyName}() {
		return ${field.fieldName};
	}

	public void set${field.propertyName}(${field.fieldType} ${field.fieldName}) {
		this.${field.fieldName} = ${field.fieldName};
	}

	</#list>

	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
			<#list java.model.modelFields as field>
			.append("${field.fieldName}", this.${field.fieldName})
			</#list>
			.toString();
	}
}
