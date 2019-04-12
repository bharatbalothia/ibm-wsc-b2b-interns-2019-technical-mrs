<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:map="http://www.stercomm.com/SI/Map" xmlns:xls="http://schemas.openxmlformats.org/spreadsheetml/2006/main" xmlns:r="http://schemas.openxmlformats.org/officeDocument/2006/relationships" xmlns:xalan="http://xml.apache.org/xalan" exclude-result-prefixes="xalan">

  <xsl:param name="mapFileName" />
  
  <xsl:param name="binaryDate">
    <xsl:call-template name="decimalToBinary">
      <xsl:with-param name="decimal" select="/map:Mapper/map:MapDetails/map:VersionControl/map:CompiledDate"/>
    </xsl:call-template>
  </xsl:param>

  <xsl:param name="Codelists">
    <xsl:for-each select="//map:PreSessionRule[contains(translate(text(),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'codelist')] | //map:PostSessionRule[contains(translate(text(),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'codelist')]">
      <xsl:call-template name="parseCodelists">
        <xsl:with-param name="inputString" select="translate(.,'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz')"/>
      </xsl:call-template>
    </xsl:for-each>
    <xsl:for-each select="//map:OnBegin[contains(translate(text(),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'codelist')] | //map:OnEnd[contains(translate(text(),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'codelist')]">
      <xsl:call-template name="parseCodelists">
        <xsl:with-param name="inputString" select="translate(.,'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz')"/>
      </xsl:call-template>
    </xsl:for-each>
    <xsl:for-each select="//map:ExplicitRule[contains(translate(text(),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'), 'codelist')]">
      <xsl:call-template name="parseCodelists">
        <xsl:with-param name="inputString" select="translate(.,'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz')"/>
      </xsl:call-template>
    </xsl:for-each>
    <xsl:for-each select="//map:UseSelect[map:TableName='Trading Partner Code List by Sender Code' or map:TableName='Trading Partner Code List by Receiver Code']">
      <xsl:call-template name="implicitRuleCodelist"/>
    </xsl:for-each>
  </xsl:param>
  
  <xsl:param name="XPathLocations">
    <xsl:for-each select="//map:Field[map:ExplicitRule[contains(text(),'TranslationOutput') or contains(text(),'BPN')]]">
      <xsl:call-template name="parseXPaths">
        <xsl:with-param name="inputString" select="map:ExplicitRule"/>
        <xsl:with-param name="Record">
          <xsl:if test="not(ancestor::map:XMLSyntax)">
            <xsl:value-of select="ancestor::map:Segment[1]/map:Name | ancestor::map:PosRecord[1]/map:Name | ancestor::map:VarDelimRecord[1]/map:Name | ancestor::map:ODBCInputRecord[1]/map:Name"/>
          </xsl:if>
        </xsl:with-param>
        <xsl:with-param name="Name">
          <xsl:choose>
            <xsl:when test="ancestor::map:XMLSyntax">
              <xsl:apply-templates select="." mode="buildName"/>
            </xsl:when>
            <xsl:otherwise>
              <xsl:apply-templates select="." mode="buildName"/>
            </xsl:otherwise>
          </xsl:choose>
        </xsl:with-param>
      </xsl:call-template>
    </xsl:for-each>
    <xsl:for-each select="//map:OnBegin[contains(text(),'TranslationOutput') or contains(text(),'BPN')] | //map:OnEnd[contains(text(),'TranslationOutput') or contains(text(),'BPN')]">
      <xsl:call-template name="parseXPaths">
        <xsl:with-param name="inputString" select="."/>
        <xsl:with-param name="Record">
          <xsl:choose>
            <xsl:when test="ancestor::map:XMLSyntax">
              <xsl:apply-templates select="preceding::map:Field[1]" mode="buildName"/>
            </xsl:when>
            <xsl:otherwise>
              <xsl:value-of select="ancestor::node()[2]/map:Name"/>
            </xsl:otherwise>
          </xsl:choose>
        </xsl:with-param>
        <xsl:with-param name="Name">
          <xsl:value-of select="local-name(.)"/>
        </xsl:with-param>
      </xsl:call-template>
    </xsl:for-each>
    <xsl:for-each select="//map:PreSessionRule[contains(text(),'TranslationOutput') or contains(text(),'BPN')] | //map:PostSessionRule[contains(text(),'TranslationOutput') or contains(text(),'BPN')]">
      <xsl:call-template name="parseXPaths">
        <xsl:with-param name="inputString" select="."/>
        <xsl:with-param name="Name">
          <xsl:value-of select="local-name()"/>
        </xsl:with-param>
      </xsl:call-template>
    </xsl:for-each>
    <xsl:for-each select="//map:Field[map:ImplicitRuleDef/map:UseUpdate[map:TableName='Process Data']]">
      <xsl:call-template name="implicitRuleXPath"/>
    </xsl:for-each>
  </xsl:param>
  
  <xsl:output method="xml" indent="yes" encoding="UTF-8"/>
  <xsl:template match="/">
    <xsl:param name="compileYear">
      <xsl:call-template name="binaryToDecimal">
        <xsl:with-param name="binary" select="substring($binaryDate, string-length($binaryDate)-15)"/>
        <xsl:with-param name="sum" select="0"/>
        <xsl:with-param name="index" select="0"/>
      </xsl:call-template>
    </xsl:param>
    <xsl:param name="compileMonth">
      <xsl:call-template name="binaryToDecimal">
        <xsl:with-param name="binary" select="substring($binaryDate, string-length($binaryDate)-23,8)"/>
        <xsl:with-param name="sum" select="0"/>
        <xsl:with-param name="index" select="0"/>
      </xsl:call-template>
    </xsl:param>
    <xsl:param name="compileDay">
      <xsl:call-template name="binaryToDecimal">
        <xsl:with-param name="binary" select="substring($binaryDate,0, string-length($binaryDate)-23)"/>
        <xsl:with-param name="sum" select="0"/>
        <xsl:with-param name="index" select="0"/>
      </xsl:call-template>
    </xsl:param>
    <xsl:element name="worksheet" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
      <xsl:element name="cols" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
        <xsl:element name="col" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
          <xsl:attribute name="min">1</xsl:attribute>
          <xsl:attribute name="max">5</xsl:attribute>
          <xsl:attribute name="width">25</xsl:attribute>
          <xsl:attribute name="bestFit">1</xsl:attribute>
          <xsl:attribute name="customWidth">1</xsl:attribute>
        </xsl:element>
      </xsl:element>
      <xsl:element name="sheetData" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
        <xsl:element name="row" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
          <xsl:element name="c" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
            <xsl:attribute name="t">inlineStr</xsl:attribute>
            <xsl:attribute name="s">3</xsl:attribute>
            <xsl:element name="is" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
              <xsl:element name="t" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
                <xsl:text>Name and Author</xsl:text>
              </xsl:element>
            </xsl:element>
          </xsl:element>
          <xsl:element name="c" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
            <xsl:attribute name="t">inlineStr</xsl:attribute>
            <xsl:attribute name="s">3</xsl:attribute>
            <xsl:element name="is" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
              <xsl:element name="t" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main"/>
            </xsl:element>
          </xsl:element>
        </xsl:element>
        <xsl:element name="row" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
          <xsl:element name="c" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
            <xsl:attribute name="t">inlineStr</xsl:attribute>
            <xsl:attribute name="s">4</xsl:attribute>
            <xsl:element name="is" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
              <xsl:element name="t" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
                <xsl:text>Author</xsl:text>
              </xsl:element>
            </xsl:element>
          </xsl:element>
          <xsl:element name="c" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
            <xsl:attribute name="t">inlineStr</xsl:attribute>
            <xsl:attribute name="s">1</xsl:attribute>
            <xsl:element name="is" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
              <xsl:element name="t" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
                <xsl:value-of select="/map:Mapper/map:MapDetails/map:Summary/map:Author"/>
              </xsl:element>
            </xsl:element>
          </xsl:element>
        </xsl:element>
        <xsl:element name="row" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
          <xsl:element name="c" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
            <xsl:attribute name="t">inlineStr</xsl:attribute>
            <xsl:attribute name="s">4</xsl:attribute>
            <xsl:element name="is" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
              <xsl:element name="t" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
                <xsl:text>Map Name</xsl:text>
              </xsl:element>
            </xsl:element>
          </xsl:element>
          <xsl:element name="c" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
            <xsl:attribute name="t">inlineStr</xsl:attribute>
            <xsl:attribute name="s">1</xsl:attribute>
            <xsl:element name="is" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
              <xsl:element name="t" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
                <xsl:value-of select="/map:Mapper/map:MapDetails/map:Summary/map:Description"/>
              </xsl:element>
            </xsl:element>
          </xsl:element>
        </xsl:element>
        <xsl:element name="row" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
          <xsl:element name="c" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
            <xsl:attribute name="t">inlineStr</xsl:attribute>
            <xsl:attribute name="s">4</xsl:attribute>
            <xsl:element name="is" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
              <xsl:element name="t" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
                <xsl:text>Compilation date</xsl:text>
              </xsl:element>
            </xsl:element>
          </xsl:element>
          <xsl:element name="c" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
            <xsl:attribute name="t">inlineStr</xsl:attribute>
            <xsl:attribute name="s">1</xsl:attribute>
            <xsl:element name="is" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
              <xsl:element name="t" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
                <xsl:value-of select="$compileYear"/>
                <xsl:text>-</xsl:text>
                <xsl:value-of select="format-number($compileMonth, '00')"/>
                <xsl:text>-</xsl:text>
                <xsl:value-of select="format-number($compileDay, '00')"/>
              </xsl:element>
            </xsl:element>
          </xsl:element>
        </xsl:element>
        <xsl:element name="row" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
          <xsl:element name="c" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
            <xsl:attribute name="t">inlineStr</xsl:attribute>
            <xsl:attribute name="s">4</xsl:attribute>
            <xsl:element name="is" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
              <xsl:element name="t" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
                <xsl:text>Map Name matches File Name</xsl:text>
              </xsl:element>
            </xsl:element>
          </xsl:element>
          <xsl:element name="c" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
            <xsl:attribute name="t">inlineStr</xsl:attribute>
            <xsl:attribute name="s">1</xsl:attribute>
            <xsl:element name="is" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
              <xsl:element name="t" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
                <xsl:choose>
                  <xsl:when test="substring-before($mapFileName, '.mxl') = /map:Mapper/map:MapDetails/map:Summary/map:Description">
                    <xsl:text>Yes</xsl:text>
                  </xsl:when>
                  <xsl:otherwise>
                    <xsl:text>No</xsl:text>
                  </xsl:otherwise>
                </xsl:choose>
              </xsl:element>
            </xsl:element>
          </xsl:element>
        </xsl:element>
        <xsl:element name="row" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
          <xsl:element name="c" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
            <xsl:attribute name="t">inlineStr</xsl:attribute>
            <xsl:attribute name="s">4</xsl:attribute>
            <xsl:element name="is" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
              <xsl:element name="t" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
                <xsl:text>X Syntax Token (0x01-0xFF)</xsl:text>
              </xsl:element>
            </xsl:element>
          </xsl:element>
          <xsl:element name="c" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
            <xsl:attribute name="t">inlineStr</xsl:attribute>
            <xsl:attribute name="s">1</xsl:attribute>
            <xsl:element name="is" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
              <xsl:element name="t" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
            <xsl:choose>
              <xsl:when test="/map:Mapper/map:SyntaxTokens/map:Token[map:Code='X' and map:Range/map:End='0x01' and map:Range/map:Start='0xFF']">
                <xsl:text>Yes</xsl:text>
              </xsl:when>
              <xsl:otherwise>
                <xsl:text>No</xsl:text>
              </xsl:otherwise>
            </xsl:choose>
              </xsl:element>
            </xsl:element>
          </xsl:element>
        </xsl:element>
        <xsl:element name="row" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
          <xsl:element name="c" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
            <xsl:attribute name="t">inlineStr</xsl:attribute>
            <xsl:attribute name="s">4</xsl:attribute>
            <xsl:element name="is" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
              <xsl:element name="t" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
                <xsl:text>Input Encoding</xsl:text>
              </xsl:element>
            </xsl:element>
          </xsl:element>
          <xsl:element name="c" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
            <xsl:attribute name="t">inlineStr</xsl:attribute>
            <xsl:attribute name="s">1</xsl:attribute>
            <xsl:element name="is" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
              <xsl:element name="t" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
                <xsl:choose>
                  <xsl:when test="/map:Mapper/map:INPUT/descendant::map:CharacterEncoding/text()">
                    <xsl:value-of select="/map:Mapper/map:INPUT/descendant::map:CharacterEncoding"/>
                  </xsl:when>
                  <xsl:otherwise>
                    <xsl:text>None Set</xsl:text>
                  </xsl:otherwise>
                </xsl:choose>
              </xsl:element>
            </xsl:element>
          </xsl:element>
        </xsl:element>
        <xsl:element name="row" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
          <xsl:element name="c" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
            <xsl:attribute name="t">inlineStr</xsl:attribute>
            <xsl:attribute name="s">4</xsl:attribute>
            <xsl:element name="is" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
              <xsl:element name="t" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
                <xsl:text>Output Encoding</xsl:text>
              </xsl:element>
            </xsl:element>
          </xsl:element>
          <xsl:element name="c" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
            <xsl:attribute name="t">inlineStr</xsl:attribute>
            <xsl:attribute name="s">1</xsl:attribute>
            <xsl:element name="is" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
              <xsl:element name="t" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
                <xsl:choose>
                  <xsl:when test="/map:Mapper/map:OUTPUT/descendant::map:CharacterEncoding/text()">
                    <xsl:value-of select="/map:Mapper/map:OUTPUT/descendant::map:CharacterEncoding"/>
                  </xsl:when>
                  <xsl:otherwise>
                    <xsl:text>None Set</xsl:text>
                  </xsl:otherwise>
                </xsl:choose>
              </xsl:element>
            </xsl:element>
          </xsl:element>
        </xsl:element>
        <xsl:element name="row" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main"/>

        <!-- EDI enveloping and syntax record checks -->
        <xsl:if test="/map:Mapper/map:INPUT/map:EDISyntax or /map:Mapper/map:OUTPUT/map:EDISyntax">
          <xsl:element name="row" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
            <xsl:if test="/map:Mapper/map:INPUT/map:EDISyntax">
              <xsl:element name="c" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
                <xsl:attribute name="t">inlineStr</xsl:attribute>
                <xsl:attribute name="s">3</xsl:attribute>
                <xsl:element name="is" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
                  <xsl:element name="t" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
                    <xsl:text>Input EDI</xsl:text>
                  </xsl:element>
                </xsl:element>
              </xsl:element>
              <xsl:element name="c" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
                <xsl:attribute name="t">inlineStr</xsl:attribute>
                <xsl:attribute name="s">3</xsl:attribute>
                <xsl:element name="is" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
                  <xsl:element name="t" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main"/>
                </xsl:element>
              </xsl:element>
              <xsl:element name="c" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
                <xsl:attribute name="t">inlineStr</xsl:attribute>
                <xsl:element name="is" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
                  <xsl:element name="t" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main"/>
                </xsl:element>
              </xsl:element>
            </xsl:if>
            <xsl:if test="/map:Mapper/map:OUTPUT/map:EDISyntax">
              <xsl:element name="c" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
                <xsl:attribute name="t">inlineStr</xsl:attribute>
                <xsl:attribute name="s">3</xsl:attribute>
                <xsl:element name="is" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
                  <xsl:element name="t" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
                    <xsl:text>Output EDI</xsl:text>
                  </xsl:element>
                </xsl:element>
              </xsl:element>
              <xsl:element name="c" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
                <xsl:attribute name="t">inlineStr</xsl:attribute>
                <xsl:attribute name="s">3</xsl:attribute>
                <xsl:element name="is" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
                  <xsl:element name="t" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main"/>
                </xsl:element>
              </xsl:element>
            </xsl:if>
          </xsl:element>
          
          <xsl:element name="row" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
            <xsl:if test="/map:Mapper/map:INPUT/map:EDISyntax">
              <xsl:element name="c" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
                <xsl:attribute name="t">inlineStr</xsl:attribute>
                <xsl:attribute name="s">4</xsl:attribute>
                <xsl:element name="is" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
                  <xsl:element name="t" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
                    <xsl:text>Enveloping Present</xsl:text>
                  </xsl:element>
                </xsl:element>
              </xsl:element>
              <xsl:element name="c" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
                <xsl:attribute name="t">inlineStr</xsl:attribute>
                <xsl:attribute name="s">1</xsl:attribute>
                <xsl:element name="is" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
                  <xsl:element name="t" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
                    <xsl:choose>
                      <xsl:when test="/map:Mapper/map:MapDetails/map:EDIAssociations_IN/map:AgencyID='X'">
                        <xsl:choose>
                          <xsl:when test="/map:Mapper/map:INPUT/descendant::map:Segment[map:Name='ISA']">
                            <xsl:text>Yes</xsl:text>
                          </xsl:when>
                          <xsl:otherwise>
                            <xsl:text>No</xsl:text>
                          </xsl:otherwise>
                        </xsl:choose>
                      </xsl:when>
                      <xsl:when test="/map:Mapper/map:MapDetails/map:EDIAssociations_IN/map:AgencyID='E'">
                        <xsl:choose>
                          <xsl:when test="/map:Mapper/map:INPUT/descendant::map:Segment[map:Name='UNA']">
                            <xsl:text>Yes</xsl:text>
                          </xsl:when>
                          <xsl:otherwise>
                            <xsl:text>No</xsl:text>
                          </xsl:otherwise>
                        </xsl:choose>
                      </xsl:when>
                    </xsl:choose>
                  </xsl:element>
                </xsl:element>
              </xsl:element>
              <xsl:element name="c" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
                <xsl:attribute name="t">inlineStr</xsl:attribute>
                <xsl:element name="is" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
                  <xsl:element name="t" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main"/>
                </xsl:element>
              </xsl:element>
            </xsl:if>
            <xsl:if test="/map:Mapper/map:OUTPUT/map:EDISyntax">
              <xsl:element name="c" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
                <xsl:attribute name="t">inlineStr</xsl:attribute>
                <xsl:attribute name="s">4</xsl:attribute>
                <xsl:element name="is" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
                  <xsl:element name="t" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
                    <xsl:text>Delimiters Set</xsl:text>
                  </xsl:element>
                </xsl:element>
              </xsl:element>
              <xsl:element name="c" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
                <xsl:attribute name="t">inlineStr</xsl:attribute>
                <xsl:attribute name="s">1</xsl:attribute>
                <xsl:element name="is" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
                  <xsl:element name="t" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
                    <xsl:choose>
                      <xsl:when test="/map:Mapper/map:OUTPUT/map:EDISyntax/map:TagDelimiterUsed='yes' or /map:Mapper/map:OUTPUT/map:EDISyntax/map:SubElementDelimiterUsed='yes' or /map:Mapper/map:OUTPUT/map:EDISyntax/map:SegmentDelimiterUsed='yes'">
                        <xsl:text>Yes</xsl:text>
                      </xsl:when>
                      <xsl:otherwise>
                        <xsl:text>No</xsl:text>
                      </xsl:otherwise>
                    </xsl:choose>
                    </xsl:element>
                </xsl:element>
              </xsl:element>
            </xsl:if>
          </xsl:element>
          <xsl:element name="row" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
            <xsl:if test="/map:Mapper/map:INPUT/map:EDISyntax">
              <xsl:element name="c" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
                <xsl:attribute name="t">inlineStr</xsl:attribute>
                <xsl:attribute name="s">4</xsl:attribute>
                <xsl:element name="is" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
                  <xsl:element name="t" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
                    <xsl:text>ISA or UNA Composite Present</xsl:text>
                  </xsl:element>
                </xsl:element>
              </xsl:element>
              <xsl:element name="c" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
                <xsl:attribute name="t">inlineStr</xsl:attribute>
                <xsl:attribute name="s">1</xsl:attribute>
                <xsl:element name="is" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
                  <xsl:element name="t" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
                    <xsl:choose>
                      <xsl:when test="/map:Mapper/map:INPUT/descendant::map:Segment[map:Name='ISA']/map:Composite or /map:Mapper/map:INPUT/descendant::map:Segment[map:Name='UNA']/map:Composite">
                        <xsl:text>Yes</xsl:text>
                      </xsl:when>
                      <xsl:otherwise>
                        <xsl:text>No</xsl:text>
                      </xsl:otherwise>
                    </xsl:choose>
                  </xsl:element>
                </xsl:element>
              </xsl:element>
              <xsl:element name="c" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
                <xsl:attribute name="t">inlineStr</xsl:attribute>
                <xsl:element name="is" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
                  <xsl:element name="t" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main"/>
                </xsl:element>
              </xsl:element>
            </xsl:if>
          </xsl:element>
          <xsl:element name="row" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
            <xsl:if test="/map:Mapper/map:INPUT/map:EDISyntax">
              <xsl:element name="c" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
                <xsl:attribute name="t">inlineStr</xsl:attribute>
                <xsl:attribute name="s">4</xsl:attribute>
                <xsl:element name="is" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
                  <xsl:element name="t" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
                    <xsl:text>Syntax Record Present</xsl:text>
                  </xsl:element>
                </xsl:element>
              </xsl:element>
              <xsl:element name="c" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
                <xsl:attribute name="t">inlineStr</xsl:attribute>
                <xsl:attribute name="s">1</xsl:attribute>
                <xsl:element name="is" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
                  <xsl:element name="t" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
                    <xsl:choose>
                      <xsl:when test="/map:Mapper/map:INPUT/map:EDISyntax/map:SyntaxRecordUsed='yes'">
                        <xsl:text>Yes</xsl:text>
                      </xsl:when>
                      <xsl:otherwise>
                        <xsl:text>No</xsl:text>
                      </xsl:otherwise>
                    </xsl:choose>
                  </xsl:element>
                </xsl:element>
              </xsl:element>
              <xsl:element name="c" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
                <xsl:attribute name="t">inlineStr</xsl:attribute>
                <xsl:element name="is" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
                  <xsl:element name="t" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main"/>
                </xsl:element>
              </xsl:element>
            </xsl:if>
          </xsl:element>
        </xsl:if>
        <xsl:element name="row" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main"/>
        
        <!-- XPaths -->
        <xsl:element name="row" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
          <xsl:element name="c" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
            <xsl:attribute name="t">inlineStr</xsl:attribute>
            <xsl:attribute name="s">3</xsl:attribute>
            <xsl:element name="is" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
              <xsl:element name="t" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
                <xsl:text>XPaths</xsl:text>
              </xsl:element>
            </xsl:element>
          </xsl:element>
          <xsl:element name="c" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
            <xsl:attribute name="t">inlineStr</xsl:attribute>
            <xsl:attribute name="s">3</xsl:attribute>
            <xsl:element name="is" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
              <xsl:element name="t" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main"/>
            </xsl:element>
          </xsl:element>
          <xsl:element name="c" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
            <xsl:attribute name="t">inlineStr</xsl:attribute>
            <xsl:attribute name="s">3</xsl:attribute>
            <xsl:element name="is" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
              <xsl:element name="t" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main"/>
            </xsl:element>
          </xsl:element>
        </xsl:element>
        <xsl:for-each select="xalan:nodeset($XPathLocations)/ProcessDataXPath">
          <xsl:element name="row" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
            <xsl:element name="c" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
              <xsl:attribute name="t">inlineStr</xsl:attribute>
              <xsl:attribute name="s">4</xsl:attribute>
              <xsl:element name="is" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
                <xsl:element name="t" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
                  <xsl:value-of select="@XPathValue"/>
                </xsl:element>
              </xsl:element>
            </xsl:element>
            <xsl:element name="c" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
              <xsl:attribute name="t">inlineStr</xsl:attribute>
              <xsl:attribute name="s">1</xsl:attribute>
              <xsl:element name="is" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
                <xsl:element name="t" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
                    <xsl:value-of select="@Record"/>
                </xsl:element>
              </xsl:element>
            </xsl:element>
            <xsl:element name="c" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
              <xsl:attribute name="t">inlineStr</xsl:attribute>
              <xsl:attribute name="s">1</xsl:attribute>
              <xsl:element name="is" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
                <xsl:element name="t" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
                  <xsl:value-of select="@Name"/>
                </xsl:element>
              </xsl:element>
            </xsl:element>
          </xsl:element>
        </xsl:for-each>

        <xsl:element name="row" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main"/>
        
        <!-- Codelists -->
        <xsl:element name="row" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
          <xsl:element name="c" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
            <xsl:attribute name="t">inlineStr</xsl:attribute>
            <xsl:attribute name="s">3</xsl:attribute>
            <xsl:element name="is" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
              <xsl:element name="t" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
                <xsl:text>Codelists</xsl:text>
              </xsl:element>
            </xsl:element>
          </xsl:element>
        </xsl:element>
        <xsl:for-each select="xalan:nodeset($Codelists)/Codelist">
          <xsl:sort select="."/>
            <xsl:element name="row" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
              <xsl:element name="c" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
                <xsl:attribute name="t">inlineStr</xsl:attribute>
                <xsl:attribute name="s">1</xsl:attribute>
                <xsl:element name="is" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
                  <xsl:element name="t" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
                    <xsl:value-of select="."/>
                  </xsl:element>
                </xsl:element>
              </xsl:element>
            </xsl:element>
        </xsl:for-each>
        
        <xsl:apply-templates/>
        
      </xsl:element><!--end of the worksheet element which encloses all row elements -->
    </xsl:element>
  </xsl:template>
  

  <xsl:template name="implicitRuleCodelist">
    <xsl:param name="codelistPosition" select="map:SubTableNameConstantID+1"/>
    <xsl:element name="Codelist">
      <xsl:value-of select="/map:Mapper/map:ConstantMap/map:Constant[position()=$codelistPosition]/map:Value"/>
    </xsl:element>
  </xsl:template>
  
  <xsl:template name="implicitRuleXPath"> 
    <xsl:param name="XPathIndex"><xsl:value-of select="map:ImplicitRuleDef/map:UseUpdate/map:SubTableNameConstantID" /></xsl:param>
    <xsl:element name="ProcessDataXPath">
      <xsl:attribute name="XPathValue"><xsl:value-of select="/map:Mapper/map:ConstantMap/map:Constant[count(preceding-sibling::map:Constant)=$XPathIndex]/map:Value"/></xsl:attribute>
      <xsl:attribute name="Record"><xsl:value-of select="ancestor::map:Segment[1]/map:Name | ancestor::map:PosRecord[1]/map:Name | ancestor::map:VarDelimRecord[1]/map:Name | ancestor::map:ODBCInputRecord[1]/map:Name"/></xsl:attribute>
      <xsl:attribute name="Name">
        <xsl:choose>
          <xsl:when test="ancestor::map:XMLSyntax">
            <xsl:apply-templates select="." mode="buildName"/>
          </xsl:when>
          <xsl:otherwise>
            <xsl:apply-templates select="." mode="buildName"/>
          </xsl:otherwise>
        </xsl:choose>
      </xsl:attribute>
    </xsl:element>
  </xsl:template>


  <!-- buildRecord -->
  <xsl:template match="map:Field" mode="buildRecord">
    <xsl:choose>
      <xsl:when test="parent::map:Composite">
        <xsl:value-of select="ancestor::map:Segment[1]/map:Name"/><xsl:text disable-output-escaping="yes"><![CDATA[&#10;]]></xsl:text>
      </xsl:when>
      <xsl:otherwise>
        <xsl:value-of select="parent::node()/map:Name" /><xsl:text disable-output-escaping="yes"><![CDATA[&#10;]]></xsl:text>
      </xsl:otherwise>
    </xsl:choose>
  </xsl:template>
  
  <!-- buildName -->
  <xsl:template match="map:Field" mode="buildName">
    <xsl:choose>
      <xsl:when test="ancestor::map:INPUT[map:XMLSyntax] or ancestor::map:OUTPUT[map:XMLSyntax]">
        <xsl:apply-templates select="./ancestor::map:XMLElementGroup" mode="buildPath"/>
        <xsl:if test="preceding-sibling::map:RecordType='attribute'">
          <xsl:text>/@</xsl:text><xsl:value-of select="map:Tag"/>
        </xsl:if>
      </xsl:when>
      <xsl:when test="ancestor::map:INPUT[map:EDISyntax] or ancestor::map:OUTPUT[map:EDISyntax]">
        <xsl:choose>
          <!-- build the Segment position reference, e.g. BEG01 or REF03 -->
          <!-- if the input is EDI element in a composite -->
          <xsl:when test="parent::map:Composite">
            <xsl:value-of select="ancestor::map:Segment[1]/map:Name"/>
            <xsl:number level="multiple" count="map:Field | map:Composite" from="map:Segment" format="01"/>
            <xsl:text> [</xsl:text>
            <xsl:value-of select="map:Name"/>
            <xsl:text>]</xsl:text>
          </xsl:when>
          <xsl:otherwise>
            <xsl:choose>
              <xsl:when test="starts-with(translate(map:Name, 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'temp_')">
                <xsl:value-of select="map:Name"/>
                <xsl:text disable-output-escaping="yes"><![CDATA[&#10;]]></xsl:text>
              </xsl:when>
              <xsl:otherwise>
                <xsl:choose>
                  <!-- if the input is EDI element (non-composite) with colon in name -->
                  <xsl:when test="contains(parent::node()/map:Name, ':')">
                    <xsl:value-of select="substring-before(parent::node()/map:Name, ':')" />
                    <xsl:number level="single" count="map:Field | map:Composite" from="map:Segment" format="01"/>
                    <xsl:text> [</xsl:text>
                    <xsl:value-of select="map:Name"/>
                    <xsl:text>]</xsl:text>
                  </xsl:when>
                  <!-- if the input is EDI element (non-composite) without colon in name -->
                  <xsl:otherwise>
                    <xsl:value-of select="parent::node()/map:Name" />
                    <xsl:number level="single" count="map:Field | map:Composite" from="map:Segment" format="01"/>
                    <xsl:text> [</xsl:text>
                    <xsl:value-of select="map:Name"/>
                    <xsl:text>]</xsl:text>
                  </xsl:otherwise>
                </xsl:choose>
              </xsl:otherwise>
            </xsl:choose>
          </xsl:otherwise>
        </xsl:choose>
      </xsl:when>
      <!-- non-EDI -->
      <xsl:otherwise>
        <xsl:value-of select="map:Name" />
      </xsl:otherwise>
    </xsl:choose>
  </xsl:template>
  <!-- buildName -->

  <xsl:template match="map:XMLElementGroup" mode="buildPath">
    <xsl:text>/</xsl:text>
    <xsl:value-of select="map:XMLTag"/>
  </xsl:template>
  
  <xsl:template name="parseCodelists">
    <xsl:param name="inputString"/>

    <!-- this is from "select" to the final character of the input string -->
    <xsl:param name="selectToEnd">
      <xsl:value-of select="substring($inputString,string-length(substring-before($inputString,'select'))+1,string-length($inputString) - string-length(substring-before($inputString,'select')))"/>
    </xsl:param>

    <!-- this is just the codelist name portion of the select statement at the beginning of the input string -->
    <xsl:if test="contains(substring-before($selectToEnd, ';'), 'codelist')">
    <xsl:element name="Codelist">
      <!--
      <xsl:value-of select="translate(substring-after(substring-after(substring-before($selectToEnd, ';'),'='),'='),' &quot;','')"/>
      -->
      <xsl:value-of select="substring-before(substring-after(substring-after(substring-before($selectToEnd, ';'),'name'),'&quot;'),'&quot;')"/>
    </xsl:element>
    </xsl:if>
    
    <xsl:if test="contains(substring-after($selectToEnd, ';'),'select')">    
    <xsl:call-template name="parseCodelists">
      <!-- call this template recursively with the portion of the extended rule after the current update statement -->
      <xsl:with-param name="inputString" select="substring-after($selectToEnd, ';')"/>
    </xsl:call-template>
    </xsl:if>
  </xsl:template>

  <xsl:template name="parseXPaths">
    <xsl:param name="inputString"/>
    <xsl:param name="Record"/>
    <xsl:param name="Name"/>

    <!-- this is from "update" to the final character of the input string -->
    <xsl:param name="updateToEnd">
      <xsl:value-of select="substring($inputString,string-length(substring-before(translate($inputString,'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'update'))+1,string-length($inputString) - string-length(substring-before(translate($inputString,'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'update')))"/>
    </xsl:param>
    
    <xsl:if test="contains($updateToEnd,'processdata')">
    <!-- this is just the XPath portion of the update statement at the beginning of the input string -->
      <xsl:element name="ProcessDataXPath">
        <xsl:attribute name="XPathValue"><xsl:value-of select="translate(substring-after(substring-after(substring-before($updateToEnd, ';'),'='),'='),' &quot;','')"/></xsl:attribute>
        <xsl:attribute name="Record"><xsl:value-of select="$Record"/></xsl:attribute>
        <xsl:attribute name="Name"><xsl:value-of select="$Name"/></xsl:attribute>
      </xsl:element>
    </xsl:if>
    
    <xsl:if test="contains(substring-after($updateToEnd, ';'),'update')">    
    <xsl:call-template name="parseXPaths">
      <!-- call this template recursively with the portion of the extended rule after the current update statement -->
      <xsl:with-param name="inputString" select="substring-after($updateToEnd, ';')"/>
      <xsl:with-param name="Record" select="$Record"/>
      <xsl:with-param name="Name" select="$Name"/>
    </xsl:call-template>
    </xsl:if>
  </xsl:template>
  
	<!-- Template to convert a binary number to decimal representation; this template calls template pow -->
	<xsl:template name="binaryToDecimal">
		<xsl:param name="binary"/>
		<xsl:param name="sum"/>
		<xsl:param name="index"/>
		<xsl:if test="substring($binary,string-length($binary) - 1) != ''">
			<xsl:variable name="power">
				<xsl:call-template name="pow">

					<xsl:with-param name="m" select="2"/>
					<xsl:with-param name="n" select="$index"/>
					<xsl:with-param name="result" select="1"/>
				</xsl:call-template>
			</xsl:variable>
			<xsl:call-template name="binaryToDecimal">
				<xsl:with-param name="binary" select="substring($binary, 1, string-length($binary) - 1)"/>
				<xsl:with-param name="sum" select="$sum + substring($binary,string-length($binary) ) * $power"/>
				<xsl:with-param name="index" select="$index + 1"/>

			</xsl:call-template>			
		</xsl:if>
		<xsl:if test="substring($binary,string-length($binary) - 1) = ''">
			<xsl:value-of select="$sum"/>
		</xsl:if>
	</xsl:template>
	
	<!-- Template to calculate m to the power n -->
	<xsl:template name="pow">
		<xsl:param name="m"/>

		<xsl:param name="n"/>
		<xsl:param name="result"/>
		<xsl:if test="$n &gt;= 1">
			<xsl:call-template name="pow">
				<xsl:with-param name="m" select="$m"/>
				<xsl:with-param name="n" select="$n - 1"/>
				<xsl:with-param name="result" select="$result * $m"/>
			</xsl:call-template>
		</xsl:if>

		<xsl:if test="$n = 0">
			<xsl:value-of select="$result"/>
		</xsl:if>
	</xsl:template>
	
	<!-- Template to convert a decimal number to binary representation -->
	<xsl:template name="decimalToBinary">
		<xsl:param name="decimal"/>
		<xsl:param name="prev"/>
		
		<xsl:variable name="divresult" select="floor($decimal div 2)"/>
		<xsl:variable name="modresult" select="$decimal mod 2"/>
		<xsl:choose>
			<xsl:when test="$divresult &gt; 1">
				<xsl:call-template name="decimalToBinary">

					<xsl:with-param name="decimal" select="$divresult"/>
					<xsl:with-param name="prev" select="concat($modresult, $prev)"/>
				</xsl:call-template>
			</xsl:when>
			<xsl:when test="$divresult = 0">
				<xsl:value-of select="concat($modresult, $prev)"/>
			</xsl:when>
			<xsl:when test="$divresult = 1">
				<xsl:text>1</xsl:text>

				<xsl:value-of select="concat($modresult, $prev)"/>
			</xsl:when>
		</xsl:choose>
	</xsl:template>

  <xsl:template match="node() | @*">
    <xsl:apply-templates select="node() | @*" />
  </xsl:template>

</xsl:stylesheet>

