<?xml version='1.0' encoding="UTF-8"?>

<xsl:stylesheet version="1.0" 
	xmlns="http://www.w3.org/1999/xhtml"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xmlns:s="http://jboss.com/products/seam/taglib"
	xmlns:ui="http://java.sun.com/jsf/facelets"
	xmlns:f="http://java.sun.com/jsf/core"
	xmlns:h="http://java.sun.com/jsf/html"
	xmlns:rich="http://richfaces.org/rich"
	xmlns:com="http://seamlets.org/components">

	<!-- Output-Methode -->
	<xsl:output
        method="xml"
        doctype-system="http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd"
        doctype-public="-//W3C//DTD XHTML 1.1//EN"/>


	<!-- Variablen und Konstanten -->
	<xsl:variable name="pageId" select="//com:page/@id" />	
	
	<xsl:template match="//com:page">
		<xsl:variable name="templatePath">
			<xsl:value-of select="com:template/com:templateViewId" />
		</xsl:variable>
		<ui:composition xmlns="http://www.w3.org/1999/xhtml"
			xmlns:s="http://jboss.com/products/seam/taglib"
			xmlns:ui="http://java.sun.com/jsf/facelets"
			xmlns:f="http://java.sun.com/jsf/core"
			xmlns:h="http://java.sun.com/jsf/html"
			xmlns:rich="http://richfaces.org/rich"
			template="{$templatePath}">
			<xsl:apply-templates select="com:title" />
			<xsl:apply-templates select="com:viewParts" />
		</ui:composition>
	</xsl:template>
	
	<xsl:template match="com:title">
		<xsl:variable name="value_part1" select="'#{pageDefinitionProvider.loadPageDefinition('" />
		<xsl:variable name="value_part2" select="').title}'" />
		<ui:define name="head">
			<title><h:outputText value="{$value_part1}{$pageId}{$value_part2}"/></title>
		</ui:define>
	</xsl:template>
	
	<xsl:template match="com:viewParts">
		<xsl:for-each select="com:viewPart">
			<xsl:variable name="viewPartName">
				<xsl:value-of select="@name" />
			</xsl:variable>
			<ui:define name="{$viewPartName}">
				 <xsl:apply-templates />
			</ui:define>
		</xsl:for-each>
	</xsl:template>

	<xsl:template match="com:htmlContent">
		<xsl:variable name="id" select="@id" />
		<xsl:variable name="value_part1" select="'#{pageContentEntityProvider.loadPageContent('"/>
		<xsl:variable name="value_part2" select="').html}'" />
		<s:div>
			<xsl:if test="@tagId != ''">
				<xsl:attribute name="id"><xsl:value-of select="@tagId" /></xsl:attribute>
			</xsl:if>
			<xsl:if test="@styleClass != ''">
				<xsl:attribute name="styleClass"><xsl:value-of select="@styleClass" /></xsl:attribute>
			</xsl:if>
			<xsl:if test="@style != ''">
				<xsl:attribute name="style"><xsl:value-of select="@style" /></xsl:attribute>
			</xsl:if>
			<h:outputText value="{$value_part1}{$id}{$value_part2}" escape="false"/>
		</s:div>
	</xsl:template>
	
</xsl:stylesheet>
