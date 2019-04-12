<?xml version="1.0" encoding="UTF-8"?>
<!--
    Created by Malagouda Patil/Manoj Bansal
    Malagouda_patil@in.ibm.com/ManojBansal@in.ibm.com
-->
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:xs="http://www.w3.org/2001/XMLSchema" exclude-result-prefixes="xs" version="1.0"
    xmlns:si="http://www.stercomm.com/SI/Map">
    <xsl:output method="html" indent="yes" encoding="UTF-8"/>
    <xsl:template match="/">
        <xsl:variable name="newline">
            <xsl:text>
            </xsl:text>
        </xsl:variable>
        <xsl:element name="Result">
            <xsl:for-each select="/si:Mapper/si:Accumulators/si:Accumulator">
                <xsl:call-template name="Process">
                    <xsl:with-param name="AccumID">
                        <xsl:value-of select="si:AccumulatorID"/>
                    </xsl:with-param>
                    <xsl:with-param name="AccumName">
                        <xsl:value-of select="si:AccumulatorName"/>
                    </xsl:with-param>
                </xsl:call-template>
                <xsl:value-of select="$newline"/>
                
            </xsl:for-each>
            
        </xsl:element>
    </xsl:template>
    
    <xsl:template name="Process">
        <xsl:param name="AccumID"/>
        <xsl:param name="AccumName"/>
        <xsl:for-each select="//si:Field[count(./si:ImplicitRuleDef/si:UseAccumulator) > 0]">
            <xsl:variable name="VarFieldName" select="./si:Name"/>
            <xsl:variable name="VarFieldDescription" select="./si:Description"/>
            
            <xsl:for-each select="./si:ImplicitRuleDef/si:UseAccumulator/si:Accumulator">
                <xsl:call-template name="Process3">
                    <xsl:with-param name="AccumID" select="$AccumID"/>
                    <xsl:with-param name="AccumName" select="$AccumName"/>
                    <xsl:with-param name="FieldName" select="$VarFieldName"/>
                    <xsl:with-param name="FieldDescription" select="$VarFieldDescription"/>
                </xsl:call-template>
            </xsl:for-each>
            
        </xsl:for-each>
        <!--START CHANGE-->
        
        <xsl:for-each select="//si:Field[count(./si:ExplicitRule) > 0]">
            <xsl:variable name="VarExplicitRule" select="./si:ExplicitRule"/>
          
            <xsl:variable name="smallcase" select="'abcdefghijklmnopqrstuvwxyz'" />
            <xsl:variable name="uppercase" select="'ABCDEFGHIJKLMNOPQRSTUVWXYZ'" />
            <xsl:variable name="VarExplicitRule_UPPER" select="translate($VarExplicitRule, $smallcase, $uppercase)" />
            <xsl:if  test="//si:Field[contains($VarExplicitRule_UPPER,concat('ACCUM(',$AccumID,')'))]">
                <xsl:call-template name="Process2"/>     
            </xsl:if>
        </xsl:for-each>
    </xsl:template>
    
    <xsl:template name="Process3">
        <xsl:param name="AccumID"/>
        <xsl:param name="FieldName"/>
        <xsl:param name="AccumName"/>
        <xsl:param name="FieldDescription"/>
        
        <xsl:if test="./si:AccumulatorID/text() = $AccumID">
            <xsl:element  name="FieldName">
                <xsl:value-of select="$FieldName"/>
            </xsl:element>
            <xsl:element  name="FieldDescription">
                <xsl:value-of select="$FieldDescription"/>
            </xsl:element>
            <xsl:element name="AccumulatorID">
                <xsl:value-of select="./si:AccumulatorID"/>
            </xsl:element>
            <xsl:element name="AccumulatorName">
                <xsl:value-of select="$AccumName"/>
            </xsl:element>
            <xsl:for-each select="./si:AccumulatorAction">
                <xsl:element name="AccumulatorAction">
                    <xsl:value-of select="./text()"/>
                </xsl:element>
            </xsl:for-each>
            <xsl:element name="AccumulatorAlternate">
                <xsl:value-of select="./si:AccumulatorAlternate"/>
            </xsl:element>
        </xsl:if>
    </xsl:template>
    
    <xsl:template name="Process2">
        <xsl:element name="FieldName">
            <xsl:value-of select="./si:Name"/>
        </xsl:element>
        <xsl:element name="AccumulatorExtendedRule">
            <xsl:value-of select="./si:ExplicitRule"/>
        </xsl:element>
    </xsl:template>
    
</xsl:stylesheet>
