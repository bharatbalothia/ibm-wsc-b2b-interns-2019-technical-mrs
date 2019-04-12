<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:xs="http://www.w3.org/2001/XMLSchema" exclude-result-prefixes="xs" 
    xmlns:si="http://www.stercomm.com/SI/Map" version="2.0">
    <xsl:output method="html" indent="yes" encoding="UTF-8"/>
  <!--  <xsl:template match="@*|node()">
        <xsl:copy>
            <xsl:apply-templates select="@*|node()"/>
        </xsl:copy>
    </xsl:template> -->
    <xsl:template match="/">
        <xsl:variable name="newline">
            <xsl:text>
            </xsl:text>
        </xsl:variable>
        <xsl:element name="Result">
            
            <xsl:for-each select="//si:Field[count(./si:StoreLimit/si:Format) > 0]">
                <xsl:choose>
                <xsl:when test="./si:StoreLimit/si:Format/text() != '' " > 
               
                </xsl:when>
                    <xsl:otherwise>
                        
                        <xsl:variable name="FIELDNAME" select="./si:Name"/>
                        <xsl:variable name="DataType" select="./si:StoreLimit/si:DataType"/>
                        <xsl:variable name="Format" select="./si:StoreLimit/si:Format"/>
                        
                        <xsl:element name="FIELDNAME">
                            <xsl:value-of select="$FIELDNAME"></xsl:value-of>
                        </xsl:element>
                        <xsl:element name="DataType">
                            <xsl:value-of select="$DataType"></xsl:value-of>
                        </xsl:element>
                        <xsl:element name="Format">
                            <xsl:value-of select="$Format"></xsl:value-of>
                        </xsl:element>
                        
                    </xsl:otherwise>
                </xsl:choose>
                <xsl:value-of select="$newline"/>
            </xsl:for-each>
        </xsl:element>
    </xsl:template>
</xsl:stylesheet>
