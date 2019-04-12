<?xml version="1.0" encoding="UTF-8"?>

<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:map="http://www.stercomm.com/SI/Map" xmlns:xls="http://schemas.openxmlformats.org/spreadsheetml/2006/main" xmlns:r="http://schemas.openxmlformats.org/officeDocument/2006/relationships">
  <xsl:key name="Fields" match="map:Field" use="map:ID"/>
  <xsl:key name="InputXMLRecords" match="map:XMLRecord[map:RecordType='pcdata' and ancestor::map:INPUT]" use="map:ID"/>
  <xsl:param name="withoutFieldDefs" select="'no'"/>
  <xsl:param name="withInactiveFields" select="'no'"/>
  <xsl:param name="crossLinked" select="'no'"/>

  <xsl:output method="xml" indent="yes" encoding="UTF-8" />
  
  <!-- XML -->
  <xsl:template match="map:OUTPUT[map:XMLSyntax]">
    <xsl:element name="worksheet" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
      <xsl:element name="sheetViews" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
        <xsl:element name="sheetView" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
          <xsl:attribute name="tabSelected">1</xsl:attribute>
          <xsl:attribute name="workbookViewId">0</xsl:attribute>
          <xsl:element name="pane" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
            <xsl:attribute name="xSplit">3</xsl:attribute>
            <xsl:attribute name="ySplit">7</xsl:attribute>
            <xsl:attribute name="topLeftCell">D8</xsl:attribute>
            <xsl:attribute name="activePane">bottomRight</xsl:attribute>
            <xsl:attribute name="state">frozen</xsl:attribute>
          </xsl:element>
        </xsl:element>
      </xsl:element>
      <xsl:choose>
        <xsl:when test="not(/map:Mapper/map:INPUT/map:XMLSyntax) and (/map:Mapper/map:OUTPUT/map:XMLSyntax/map:EnableNamespaces = 'yes')">
          <xsl:element name="cols" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
            <xsl:element name="col" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
              <xsl:attribute name="min">1</xsl:attribute>
              <xsl:attribute name="max">2</xsl:attribute>
              <xsl:attribute name="width">9</xsl:attribute>
              <xsl:attribute name="bestFit">1</xsl:attribute>
              <xsl:attribute name="customWidth">1</xsl:attribute>
            </xsl:element>
            <xsl:element name="col" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
              <xsl:attribute name="min">3</xsl:attribute>
              <xsl:attribute name="max">3</xsl:attribute>
              <xsl:attribute name="width">70</xsl:attribute>
              <xsl:attribute name="bestFit">1</xsl:attribute>
              <xsl:attribute name="customWidth">1</xsl:attribute>
            </xsl:element>
            <xsl:element name="col" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
              <xsl:attribute name="min">4</xsl:attribute>
              <xsl:attribute name="max">4</xsl:attribute>
              <xsl:attribute name="width">45</xsl:attribute>
              <xsl:attribute name="bestFit">1</xsl:attribute>
              <xsl:attribute name="customWidth">1</xsl:attribute>
            </xsl:element>
            <xsl:element name="col" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
              <xsl:attribute name="min">5</xsl:attribute>
              <xsl:attribute name="max">5</xsl:attribute>
              <xsl:attribute name="width">12</xsl:attribute>
              <xsl:attribute name="bestFit">1</xsl:attribute>
              <xsl:attribute name="customWidth">1</xsl:attribute>
            </xsl:element>
            <xsl:element name="col" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
              <xsl:attribute name="min">6</xsl:attribute>
              <xsl:attribute name="max">6</xsl:attribute>
              <xsl:attribute name="width">12</xsl:attribute>
              <xsl:attribute name="bestFit">1</xsl:attribute>
              <xsl:attribute name="customWidth">1</xsl:attribute>
            </xsl:element>
            <xsl:element name="col" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
              <xsl:attribute name="min">7</xsl:attribute>
              <xsl:attribute name="max">7</xsl:attribute>
              <xsl:attribute name="width">35</xsl:attribute>
              <xsl:attribute name="bestFit">1</xsl:attribute>
              <xsl:attribute name="customWidth">1</xsl:attribute>
            </xsl:element>
            <xsl:element name="col" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
              <xsl:attribute name="min">8</xsl:attribute>
              <xsl:attribute name="max">8</xsl:attribute>
              <xsl:attribute name="width">60</xsl:attribute>
              <xsl:attribute name="bestFit">1</xsl:attribute>
              <xsl:attribute name="customWidth">1</xsl:attribute>
            </xsl:element>
            <xsl:element name="col" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
              <xsl:attribute name="min">9</xsl:attribute>
              <xsl:attribute name="max">9</xsl:attribute>
              <xsl:attribute name="width">60</xsl:attribute>
              <xsl:attribute name="bestFit">1</xsl:attribute>
              <xsl:attribute name="customWidth">1</xsl:attribute>
            </xsl:element>
            <xsl:element name="col" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
              <xsl:attribute name="min">10</xsl:attribute>
              <xsl:attribute name="max">10</xsl:attribute>
              <xsl:attribute name="width">40</xsl:attribute>
              <xsl:attribute name="bestFit">1</xsl:attribute>
              <xsl:attribute name="customWidth">1</xsl:attribute>
            </xsl:element>
            <xsl:element name="col" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
              <xsl:attribute name="min">11</xsl:attribute>
              <xsl:attribute name="max">11</xsl:attribute>
              <xsl:attribute name="width">20</xsl:attribute>
              <xsl:attribute name="bestFit">1</xsl:attribute>
              <xsl:attribute name="customWidth">1</xsl:attribute>
            </xsl:element>
            <xsl:element name="col" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
              <xsl:attribute name="min">12</xsl:attribute>
              <xsl:attribute name="max">12</xsl:attribute>
              <xsl:attribute name="width">8</xsl:attribute>
              <xsl:attribute name="bestFit">1</xsl:attribute>
              <xsl:attribute name="customWidth">1</xsl:attribute>
            </xsl:element>
            <xsl:element name="col" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
              <xsl:attribute name="min">13</xsl:attribute>
              <xsl:attribute name="max">13</xsl:attribute>
              <xsl:attribute name="width">11</xsl:attribute>
              <xsl:attribute name="bestFit">1</xsl:attribute>
              <xsl:attribute name="customWidth">1</xsl:attribute>
            </xsl:element>
            <xsl:element name="col" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
              <xsl:attribute name="min">14</xsl:attribute>
              <xsl:attribute name="max">14</xsl:attribute>
              <xsl:attribute name="width">5</xsl:attribute>
              <xsl:attribute name="bestFit">1</xsl:attribute>
              <xsl:attribute name="customWidth">1</xsl:attribute>
            </xsl:element>
            <xsl:element name="col" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
              <xsl:attribute name="min">15</xsl:attribute>
              <xsl:attribute name="max">15</xsl:attribute>
              <xsl:attribute name="width">4</xsl:attribute>
              <xsl:attribute name="bestFit">1</xsl:attribute>
              <xsl:attribute name="customWidth">1</xsl:attribute>
            </xsl:element>
            <xsl:element name="col" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
              <xsl:attribute name="min">16</xsl:attribute>
              <xsl:attribute name="max">16</xsl:attribute>
              <xsl:attribute name="width">11</xsl:attribute>
              <xsl:attribute name="bestFit">1</xsl:attribute>
              <xsl:attribute name="customWidth">1</xsl:attribute>
            </xsl:element>
            <xsl:element name="col" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
              <xsl:attribute name="min">17</xsl:attribute>
              <xsl:attribute name="max">18</xsl:attribute>
              <xsl:attribute name="width">10</xsl:attribute>
              <xsl:attribute name="bestFit">1</xsl:attribute>
              <xsl:attribute name="customWidth">1</xsl:attribute>
            </xsl:element>
          </xsl:element>
        </xsl:when>
        <xsl:when test="/map:Mapper/map:INPUT/map:XMLSyntax and (/map:Mapper/map:OUTPUT/map:XMLSyntax/map:EnableNamespaces = 'yes')">
          <xsl:element name="cols" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
            <xsl:element name="col" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
              <xsl:attribute name="min">1</xsl:attribute>
              <xsl:attribute name="max">2</xsl:attribute>
              <xsl:attribute name="width">9</xsl:attribute>
              <xsl:attribute name="bestFit">1</xsl:attribute>
              <xsl:attribute name="customWidth">1</xsl:attribute>
            </xsl:element>
            <xsl:element name="col" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
              <xsl:attribute name="min">3</xsl:attribute>
              <xsl:attribute name="max">3</xsl:attribute>
              <xsl:attribute name="width">70</xsl:attribute>
              <xsl:attribute name="bestFit">1</xsl:attribute>
              <xsl:attribute name="customWidth">1</xsl:attribute>
            </xsl:element>
            <xsl:element name="col" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
              <xsl:attribute name="min">4</xsl:attribute>
              <xsl:attribute name="max">4</xsl:attribute>
              <xsl:attribute name="width">45</xsl:attribute>
              <xsl:attribute name="bestFit">1</xsl:attribute>
              <xsl:attribute name="customWidth">1</xsl:attribute>
            </xsl:element>
            <xsl:element name="col" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
              <xsl:attribute name="min">5</xsl:attribute>
              <xsl:attribute name="max">5</xsl:attribute>
              <xsl:attribute name="width">60</xsl:attribute>
              <xsl:attribute name="bestFit">1</xsl:attribute>
              <xsl:attribute name="customWidth">1</xsl:attribute>
            </xsl:element>
            <xsl:element name="col" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
              <xsl:attribute name="min">6</xsl:attribute>
              <xsl:attribute name="max">6</xsl:attribute>
              <xsl:attribute name="width">35</xsl:attribute>
              <xsl:attribute name="bestFit">1</xsl:attribute>
              <xsl:attribute name="customWidth">1</xsl:attribute>
            </xsl:element>
            <xsl:element name="col" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
              <xsl:attribute name="min">7</xsl:attribute>
              <xsl:attribute name="max">7</xsl:attribute>
              <xsl:attribute name="width">60</xsl:attribute>
              <xsl:attribute name="bestFit">1</xsl:attribute>
              <xsl:attribute name="customWidth">1</xsl:attribute>
            </xsl:element>
            <xsl:element name="col" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
              <xsl:attribute name="min">8</xsl:attribute>
              <xsl:attribute name="max">8</xsl:attribute>
              <xsl:attribute name="width">60</xsl:attribute>
              <xsl:attribute name="bestFit">1</xsl:attribute>
              <xsl:attribute name="customWidth">1</xsl:attribute>
            </xsl:element>
            <xsl:element name="col" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
              <xsl:attribute name="min">9</xsl:attribute>
              <xsl:attribute name="max">9</xsl:attribute>
              <xsl:attribute name="width">40</xsl:attribute>
              <xsl:attribute name="bestFit">1</xsl:attribute>
              <xsl:attribute name="customWidth">1</xsl:attribute>
            </xsl:element>
            <xsl:element name="col" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
              <xsl:attribute name="min">10</xsl:attribute>
              <xsl:attribute name="max">10</xsl:attribute>
              <xsl:attribute name="width">20</xsl:attribute>
              <xsl:attribute name="bestFit">1</xsl:attribute>
              <xsl:attribute name="customWidth">1</xsl:attribute>
            </xsl:element>
            <xsl:element name="col" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
              <xsl:attribute name="min">11</xsl:attribute>
              <xsl:attribute name="max">11</xsl:attribute>
              <xsl:attribute name="width">8</xsl:attribute>
              <xsl:attribute name="bestFit">1</xsl:attribute>
              <xsl:attribute name="customWidth">1</xsl:attribute>
            </xsl:element>
            <xsl:element name="col" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
              <xsl:attribute name="min">12</xsl:attribute>
              <xsl:attribute name="max">12</xsl:attribute>
              <xsl:attribute name="width">11</xsl:attribute>
              <xsl:attribute name="bestFit">1</xsl:attribute>
              <xsl:attribute name="customWidth">1</xsl:attribute>
            </xsl:element>
            <xsl:element name="col" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
              <xsl:attribute name="min">13</xsl:attribute>
              <xsl:attribute name="max">13</xsl:attribute>
              <xsl:attribute name="width">5</xsl:attribute>
              <xsl:attribute name="bestFit">1</xsl:attribute>
              <xsl:attribute name="customWidth">1</xsl:attribute>
            </xsl:element>
            <xsl:element name="col" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
              <xsl:attribute name="min">14</xsl:attribute>
              <xsl:attribute name="max">14</xsl:attribute>
              <xsl:attribute name="width">4</xsl:attribute>
              <xsl:attribute name="bestFit">1</xsl:attribute>
              <xsl:attribute name="customWidth">1</xsl:attribute>
            </xsl:element>
            <xsl:element name="col" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
              <xsl:attribute name="min">15</xsl:attribute>
              <xsl:attribute name="max">15</xsl:attribute>
              <xsl:attribute name="width">11</xsl:attribute>
              <xsl:attribute name="bestFit">1</xsl:attribute>
              <xsl:attribute name="customWidth">1</xsl:attribute>
            </xsl:element>
            <xsl:element name="col" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
              <xsl:attribute name="min">16</xsl:attribute>
              <xsl:attribute name="max">17</xsl:attribute>
              <xsl:attribute name="width">10</xsl:attribute>
              <xsl:attribute name="bestFit">1</xsl:attribute>
              <xsl:attribute name="customWidth">1</xsl:attribute>
            </xsl:element>
          </xsl:element>
        </xsl:when>
        <xsl:when test="not(/map:Mapper/map:INPUT/map:XMLSyntax) and (/map:Mapper/map:OUTPUT/map:XMLSyntax/map:EnableNamespaces = 'no')">
          <xsl:element name="cols" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
            <xsl:element name="col" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
              <xsl:attribute name="min">1</xsl:attribute>
              <xsl:attribute name="max">2</xsl:attribute>
              <xsl:attribute name="width">9</xsl:attribute>
              <xsl:attribute name="bestFit">1</xsl:attribute>
              <xsl:attribute name="customWidth">1</xsl:attribute>
            </xsl:element>
            <xsl:element name="col" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
              <xsl:attribute name="min">3</xsl:attribute>
              <xsl:attribute name="max">3</xsl:attribute>
              <xsl:attribute name="width">70</xsl:attribute>
              <xsl:attribute name="bestFit">1</xsl:attribute>
              <xsl:attribute name="customWidth">1</xsl:attribute>
            </xsl:element>
            <xsl:element name="col" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
              <xsl:attribute name="min">4</xsl:attribute>
              <xsl:attribute name="max">4</xsl:attribute>
              <xsl:attribute name="width">45</xsl:attribute>
              <xsl:attribute name="bestFit">1</xsl:attribute>
              <xsl:attribute name="customWidth">1</xsl:attribute>
            </xsl:element>
            <xsl:element name="col" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
              <xsl:attribute name="min">5</xsl:attribute>
              <xsl:attribute name="max">5</xsl:attribute>
              <xsl:attribute name="width">12</xsl:attribute>
              <xsl:attribute name="bestFit">1</xsl:attribute>
              <xsl:attribute name="customWidth">1</xsl:attribute>
            </xsl:element>
            <xsl:element name="col" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
              <xsl:attribute name="min">6</xsl:attribute>
              <xsl:attribute name="max">6</xsl:attribute>
              <xsl:attribute name="width">12</xsl:attribute>
              <xsl:attribute name="bestFit">1</xsl:attribute>
              <xsl:attribute name="customWidth">1</xsl:attribute>
            </xsl:element>
            <xsl:element name="col" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
              <xsl:attribute name="min">7</xsl:attribute>
              <xsl:attribute name="max">7</xsl:attribute>
              <xsl:attribute name="width">35</xsl:attribute>
              <xsl:attribute name="bestFit">1</xsl:attribute>
              <xsl:attribute name="customWidth">1</xsl:attribute>
            </xsl:element>
            <xsl:element name="col" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
              <xsl:attribute name="min">8</xsl:attribute>
              <xsl:attribute name="max">8</xsl:attribute>
              <xsl:attribute name="width">60</xsl:attribute>
              <xsl:attribute name="bestFit">1</xsl:attribute>
              <xsl:attribute name="customWidth">1</xsl:attribute>
            </xsl:element>
            <xsl:element name="col" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
              <xsl:attribute name="min">9</xsl:attribute>
              <xsl:attribute name="max">9</xsl:attribute>
              <xsl:attribute name="width">60</xsl:attribute>
              <xsl:attribute name="bestFit">1</xsl:attribute>
              <xsl:attribute name="customWidth">1</xsl:attribute>
            </xsl:element>
            <xsl:element name="col" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
              <xsl:attribute name="min">10</xsl:attribute>
              <xsl:attribute name="max">10</xsl:attribute>
              <xsl:attribute name="width">8</xsl:attribute>
              <xsl:attribute name="bestFit">1</xsl:attribute>
              <xsl:attribute name="customWidth">1</xsl:attribute>
            </xsl:element>
            <xsl:element name="col" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
              <xsl:attribute name="min">11</xsl:attribute>
              <xsl:attribute name="max">11</xsl:attribute>
              <xsl:attribute name="width">11</xsl:attribute>
              <xsl:attribute name="bestFit">1</xsl:attribute>
              <xsl:attribute name="customWidth">1</xsl:attribute>
            </xsl:element>
            <xsl:element name="col" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
              <xsl:attribute name="min">12</xsl:attribute>
              <xsl:attribute name="max">12</xsl:attribute>
              <xsl:attribute name="width">5</xsl:attribute>
              <xsl:attribute name="bestFit">1</xsl:attribute>
              <xsl:attribute name="customWidth">1</xsl:attribute>
            </xsl:element>
            <xsl:element name="col" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
              <xsl:attribute name="min">13</xsl:attribute>
              <xsl:attribute name="max">13</xsl:attribute>
              <xsl:attribute name="width">4</xsl:attribute>
              <xsl:attribute name="bestFit">1</xsl:attribute>
              <xsl:attribute name="customWidth">1</xsl:attribute>
            </xsl:element>
            <xsl:element name="col" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
              <xsl:attribute name="min">14</xsl:attribute>
              <xsl:attribute name="max">14</xsl:attribute>
              <xsl:attribute name="width">11</xsl:attribute>
              <xsl:attribute name="bestFit">1</xsl:attribute>
              <xsl:attribute name="customWidth">1</xsl:attribute>
            </xsl:element>
            <xsl:element name="col" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
              <xsl:attribute name="min">15</xsl:attribute>
              <xsl:attribute name="max">16</xsl:attribute>
              <xsl:attribute name="width">10</xsl:attribute>
              <xsl:attribute name="bestFit">1</xsl:attribute>
              <xsl:attribute name="customWidth">1</xsl:attribute>
            </xsl:element>
          </xsl:element>
        </xsl:when>
        <xsl:when test="/map:Mapper/map:INPUT/map:XMLSyntax and (/map:Mapper/map:OUTPUT/map:XMLSyntax/map:EnableNamespaces = 'no')">
          <xsl:element name="cols" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
            <xsl:element name="col" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
              <xsl:attribute name="min">1</xsl:attribute>
              <xsl:attribute name="max">2</xsl:attribute>
              <xsl:attribute name="width">9</xsl:attribute>
              <xsl:attribute name="bestFit">1</xsl:attribute>
              <xsl:attribute name="customWidth">1</xsl:attribute>
            </xsl:element>
            <xsl:element name="col" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
              <xsl:attribute name="min">3</xsl:attribute>
              <xsl:attribute name="max">3</xsl:attribute>
              <xsl:attribute name="width">70</xsl:attribute>
              <xsl:attribute name="bestFit">1</xsl:attribute>
              <xsl:attribute name="customWidth">1</xsl:attribute>
            </xsl:element>
            <xsl:element name="col" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
              <xsl:attribute name="min">4</xsl:attribute>
              <xsl:attribute name="max">4</xsl:attribute>
              <xsl:attribute name="width">45</xsl:attribute>
              <xsl:attribute name="bestFit">1</xsl:attribute>
              <xsl:attribute name="customWidth">1</xsl:attribute>
            </xsl:element>
            <xsl:element name="col" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
              <xsl:attribute name="min">5</xsl:attribute>
              <xsl:attribute name="max">5</xsl:attribute>
              <xsl:attribute name="width">60</xsl:attribute>
              <xsl:attribute name="bestFit">1</xsl:attribute>
              <xsl:attribute name="customWidth">1</xsl:attribute>
            </xsl:element>
            <xsl:element name="col" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
              <xsl:attribute name="min">6</xsl:attribute>
              <xsl:attribute name="max">6</xsl:attribute>
              <xsl:attribute name="width">35</xsl:attribute>
              <xsl:attribute name="bestFit">1</xsl:attribute>
              <xsl:attribute name="customWidth">1</xsl:attribute>
            </xsl:element>
            <xsl:element name="col" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
              <xsl:attribute name="min">7</xsl:attribute>
              <xsl:attribute name="max">7</xsl:attribute>
              <xsl:attribute name="width">60</xsl:attribute>
              <xsl:attribute name="bestFit">1</xsl:attribute>
              <xsl:attribute name="customWidth">1</xsl:attribute>
            </xsl:element>
            <xsl:element name="col" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
              <xsl:attribute name="min">8</xsl:attribute>
              <xsl:attribute name="max">8</xsl:attribute>
              <xsl:attribute name="width">60</xsl:attribute>
              <xsl:attribute name="bestFit">1</xsl:attribute>
              <xsl:attribute name="customWidth">1</xsl:attribute>
            </xsl:element>
            <xsl:element name="col" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
              <xsl:attribute name="min">9</xsl:attribute>
              <xsl:attribute name="max">9</xsl:attribute>
              <xsl:attribute name="width">8</xsl:attribute>
              <xsl:attribute name="bestFit">1</xsl:attribute>
              <xsl:attribute name="customWidth">1</xsl:attribute>
            </xsl:element>
            <xsl:element name="col" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
              <xsl:attribute name="min">10</xsl:attribute>
              <xsl:attribute name="max">10</xsl:attribute>
              <xsl:attribute name="width">11</xsl:attribute>
              <xsl:attribute name="bestFit">1</xsl:attribute>
              <xsl:attribute name="customWidth">1</xsl:attribute>
            </xsl:element>
            <xsl:element name="col" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
              <xsl:attribute name="min">11</xsl:attribute>
              <xsl:attribute name="max">11</xsl:attribute>
              <xsl:attribute name="width">5</xsl:attribute>
              <xsl:attribute name="bestFit">1</xsl:attribute>
              <xsl:attribute name="customWidth">1</xsl:attribute>
            </xsl:element>
            <xsl:element name="col" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
              <xsl:attribute name="min">12</xsl:attribute>
              <xsl:attribute name="max">12</xsl:attribute>
              <xsl:attribute name="width">4</xsl:attribute>
              <xsl:attribute name="bestFit">1</xsl:attribute>
              <xsl:attribute name="customWidth">1</xsl:attribute>
            </xsl:element>
            <xsl:element name="col" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
              <xsl:attribute name="min">13</xsl:attribute>
              <xsl:attribute name="max">13</xsl:attribute>
              <xsl:attribute name="width">11</xsl:attribute>
              <xsl:attribute name="bestFit">1</xsl:attribute>
              <xsl:attribute name="customWidth">1</xsl:attribute>
            </xsl:element>
            <xsl:element name="col" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
              <xsl:attribute name="min">14</xsl:attribute>
              <xsl:attribute name="max">15</xsl:attribute>
              <xsl:attribute name="width">10</xsl:attribute>
              <xsl:attribute name="bestFit">1</xsl:attribute>
              <xsl:attribute name="customWidth">1</xsl:attribute>
            </xsl:element>
          </xsl:element>
        </xsl:when>
      </xsl:choose>
      <xsl:element name="sheetData" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
        <xsl:element name="row" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
          <xsl:attribute name="s">3</xsl:attribute>
          <xsl:attribute name="customFormat">1</xsl:attribute>
          <xsl:element name="c" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
            <xsl:attribute name="t">inlineStr</xsl:attribute>
            <xsl:attribute name="s">3</xsl:attribute>
            <xsl:element name="is" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
              <xsl:element name="t" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
                <xsl:text>Output Format</xsl:text>
              </xsl:element>
            </xsl:element>
          </xsl:element>
          <xsl:element name="c" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
            <xsl:attribute name="t">inlineStr</xsl:attribute>
            <xsl:attribute name="s">3</xsl:attribute>
          </xsl:element>
          <xsl:element name="c" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
            <xsl:attribute name="t">inlineStr</xsl:attribute>
            <xsl:attribute name="s">3</xsl:attribute>
          </xsl:element>
          <xsl:element name="c" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
            <xsl:attribute name="t">inlineStr</xsl:attribute>
            <xsl:attribute name="s">3</xsl:attribute>
          </xsl:element>
          <xsl:element name="c" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
            <xsl:attribute name="t">inlineStr</xsl:attribute>
            <xsl:attribute name="s">3</xsl:attribute>
            <xsl:element name="is" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
              <xsl:element name="t" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
                <xsl:value-of select="map:XMLSyntax/map:OutputFormat"/>
              </xsl:element>
            </xsl:element>
          </xsl:element>
        </xsl:element>
        <xsl:element name="row" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
          <xsl:attribute name="s">3</xsl:attribute>
          <xsl:attribute name="customFormat">1</xsl:attribute>
          <xsl:element name="c" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
            <xsl:attribute name="t">inlineStr</xsl:attribute>
            <xsl:attribute name="s">3</xsl:attribute>
            <xsl:element name="is" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
              <xsl:element name="t" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
                <xsl:text>Output Header</xsl:text>
              </xsl:element>
            </xsl:element>
          </xsl:element>
          <xsl:element name="c" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
            <xsl:attribute name="t">inlineStr</xsl:attribute>
            <xsl:attribute name="s">3</xsl:attribute>
          </xsl:element>
          <xsl:element name="c" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
            <xsl:attribute name="t">inlineStr</xsl:attribute>
            <xsl:attribute name="s">3</xsl:attribute>
          </xsl:element>
          <xsl:element name="c" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
            <xsl:attribute name="t">inlineStr</xsl:attribute>
            <xsl:attribute name="s">3</xsl:attribute>
          </xsl:element>
          <xsl:element name="c" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
            <xsl:attribute name="t">inlineStr</xsl:attribute>
            <xsl:attribute name="s">3</xsl:attribute>
            <xsl:element name="is" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
              <xsl:element name="t" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
                <xsl:value-of select="map:XMLSyntax/map:OutputHeader"/>
              </xsl:element>
            </xsl:element>
          </xsl:element>
        </xsl:element>
        <xsl:element name="row" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
          <xsl:attribute name="s">3</xsl:attribute>
          <xsl:attribute name="customFormat">1</xsl:attribute>
          <xsl:element name="c" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
            <xsl:attribute name="t">inlineStr</xsl:attribute>
            <xsl:attribute name="s">3</xsl:attribute>
            <xsl:element name="is" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
              <xsl:element name="t" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
                <xsl:text>Use Character Entity Reference</xsl:text>
              </xsl:element>
            </xsl:element>
          </xsl:element>
          <xsl:element name="c" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
            <xsl:attribute name="t">inlineStr</xsl:attribute>
            <xsl:attribute name="s">3</xsl:attribute>
          </xsl:element>
          <xsl:element name="c" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
            <xsl:attribute name="t">inlineStr</xsl:attribute>
            <xsl:attribute name="s">3</xsl:attribute>
          </xsl:element>
          <xsl:element name="c" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
            <xsl:attribute name="t">inlineStr</xsl:attribute>
            <xsl:attribute name="s">3</xsl:attribute>
          </xsl:element>
          <xsl:element name="c" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
            <xsl:attribute name="t">inlineStr</xsl:attribute>
            <xsl:attribute name="s">3</xsl:attribute>
            <xsl:element name="is" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
              <xsl:element name="t" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
                <xsl:value-of select="map:XMLSyntax/map:UseCharEntityRef"/>
              </xsl:element>
            </xsl:element>
          </xsl:element>
        </xsl:element>
        <xsl:element name="row" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
          <xsl:attribute name="s">3</xsl:attribute>
          <xsl:attribute name="customFormat">1</xsl:attribute>
          <xsl:element name="c" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
            <xsl:attribute name="t">inlineStr</xsl:attribute>
            <xsl:attribute name="s">3</xsl:attribute>
            <xsl:element name="is" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
              <xsl:element name="t" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
                <xsl:text>Use Decimal Point Character</xsl:text>
              </xsl:element>
            </xsl:element>
          </xsl:element>
          <xsl:element name="c" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
            <xsl:attribute name="t">inlineStr</xsl:attribute>
            <xsl:attribute name="s">3</xsl:attribute>
          </xsl:element>
          <xsl:element name="c" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
            <xsl:attribute name="t">inlineStr</xsl:attribute>
            <xsl:attribute name="s">3</xsl:attribute>
          </xsl:element>
          <xsl:element name="c" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
            <xsl:attribute name="t">inlineStr</xsl:attribute>
            <xsl:attribute name="s">3</xsl:attribute>
          </xsl:element>
          <xsl:element name="c" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
            <xsl:attribute name="t">inlineStr</xsl:attribute>
            <xsl:attribute name="s">3</xsl:attribute>
            <xsl:element name="is" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
              <xsl:element name="t" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
                <xsl:value-of select="map:XMLSyntax/map:UseDecimalPointChar"/>
              </xsl:element>
            </xsl:element>
          </xsl:element>
        </xsl:element>
        <xsl:element name="row" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
          <xsl:attribute name="s">3</xsl:attribute>
          <xsl:attribute name="customFormat">1</xsl:attribute>
          <xsl:element name="c" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
            <xsl:attribute name="t">inlineStr</xsl:attribute>
            <xsl:attribute name="s">3</xsl:attribute>
            <xsl:element name="is" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
              <xsl:element name="t" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
                <xsl:text>Decimal Point Character</xsl:text>
              </xsl:element>
            </xsl:element>
          </xsl:element>
          <xsl:element name="c" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
            <xsl:attribute name="t">inlineStr</xsl:attribute>
            <xsl:attribute name="s">3</xsl:attribute>
          </xsl:element>
          <xsl:element name="c" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
            <xsl:attribute name="t">inlineStr</xsl:attribute>
            <xsl:attribute name="s">3</xsl:attribute>
          </xsl:element>
          <xsl:element name="c" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
            <xsl:attribute name="t">inlineStr</xsl:attribute>
            <xsl:attribute name="s">3</xsl:attribute>
          </xsl:element>
          <xsl:element name="c" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
            <xsl:attribute name="t">inlineStr</xsl:attribute>
            <xsl:attribute name="s">3</xsl:attribute>
            <xsl:element name="is" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
              <xsl:element name="t" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
                <xsl:value-of select="map:XMLSyntax/map:DecimalPointChar"/>
              </xsl:element>
            </xsl:element>
          </xsl:element>
        </xsl:element>
        <xsl:element name="row" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
          <xsl:attribute name="s">3</xsl:attribute>
          <xsl:attribute name="customFormat">1</xsl:attribute>
          <xsl:element name="c" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
            <xsl:attribute name="t">inlineStr</xsl:attribute>
            <xsl:attribute name="s">3</xsl:attribute>
            <xsl:element name="is" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
              <xsl:element name="t" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
                <xsl:text>Enable Namespace Support</xsl:text>
              </xsl:element>
            </xsl:element>
          </xsl:element>
          <xsl:element name="c" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
            <xsl:attribute name="t">inlineStr</xsl:attribute>
            <xsl:attribute name="s">3</xsl:attribute>
          </xsl:element>
          <xsl:element name="c" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
            <xsl:attribute name="t">inlineStr</xsl:attribute>
            <xsl:attribute name="s">3</xsl:attribute>
          </xsl:element>
          <xsl:element name="c" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
            <xsl:attribute name="t">inlineStr</xsl:attribute>
            <xsl:attribute name="s">3</xsl:attribute>
          </xsl:element>
          <xsl:element name="c" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
            <xsl:attribute name="t">inlineStr</xsl:attribute>
            <xsl:attribute name="s">3</xsl:attribute>
            <xsl:element name="is" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
              <xsl:element name="t" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
                <xsl:value-of select="map:XMLSyntax/map:EnableNamespaces"/>
              </xsl:element>
            </xsl:element>
          </xsl:element>
        </xsl:element>
        <xsl:element name="row" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
          <xsl:attribute name="s">4</xsl:attribute>
          <xsl:attribute name="customFormat">1</xsl:attribute>
          <!-- active -->
          <xsl:element name="c" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
            <xsl:attribute name="t">inlineStr</xsl:attribute>
            <xsl:attribute name="s">4</xsl:attribute>
            <xsl:element name="is" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
              <xsl:element name="t" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
                <xsl:text>Active</xsl:text>
              </xsl:element>
            </xsl:element>
          </xsl:element>
          <!-- node type -->
          <xsl:element name="c" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
            <xsl:attribute name="t">inlineStr</xsl:attribute>
            <xsl:attribute name="s">4</xsl:attribute>
            <xsl:element name="is" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
              <xsl:element name="t" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
                <xsl:text>Node Type</xsl:text>
              </xsl:element>
            </xsl:element>
          </xsl:element>
          <!-- name -->
          <xsl:element name="c" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
            <xsl:attribute name="t">inlineStr</xsl:attribute>
            <xsl:attribute name="s">4</xsl:attribute>
            <xsl:element name="is" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
              <xsl:element name="t" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
                <xsl:text>Name</xsl:text>
              </xsl:element>
            </xsl:element>
          </xsl:element>
          <!-- description -->
          <xsl:element name="c" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
            <xsl:attribute name="t">inlineStr</xsl:attribute>
            <xsl:attribute name="s">4</xsl:attribute>
            <xsl:element name="is" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
              <xsl:element name="t" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
                <xsl:text>Description</xsl:text>
              </xsl:element>
            </xsl:element>
          </xsl:element>
          <xsl:if test="not(/map:Mapper/map:INPUT/map:XMLSyntax)">
            <!-- linked segment/record -->
            <xsl:element name="c" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
              <xsl:attribute name="t">inlineStr</xsl:attribute>
              <xsl:attribute name="s">4</xsl:attribute>
              <xsl:element name="is" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
                <xsl:element name="t" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
                  <xsl:choose>
                    <xsl:when test="/map:Mapper/map:INPUT/map:EDISyntax">
                      <xsl:text>Segment</xsl:text>
                    </xsl:when>
                    <xsl:otherwise>
                      <xsl:text>Record</xsl:text>
                    </xsl:otherwise>
                  </xsl:choose>
                </xsl:element>
              </xsl:element>
            </xsl:element>
          </xsl:if>
          <!-- linked element/field -->
            <xsl:element name="c" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
              <xsl:attribute name="t">inlineStr</xsl:attribute>
              <xsl:attribute name="s">4</xsl:attribute>
              <xsl:element name="is" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
                <xsl:element name="t" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
                  <xsl:choose>
                    <xsl:when test="/map:Mapper/map:INPUT/map:EDISyntax">
                      <xsl:text>Element</xsl:text>
                    </xsl:when>
                    <xsl:when test="/map:Mapper/map:INPUT/map:XMLSyntax">
                      <xsl:text>XPath</xsl:text>
                    </xsl:when>
                    <xsl:otherwise>
                      <xsl:text>Field</xsl:text>
                    </xsl:otherwise>
                  </xsl:choose>
                </xsl:element>
              </xsl:element>
            </xsl:element>
          <!-- standard rules -->
            <xsl:element name="c" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
              <xsl:attribute name="t">inlineStr</xsl:attribute>
              <xsl:attribute name="s">4</xsl:attribute>
              <xsl:element name="is" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
                <xsl:element name="t" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
                  <xsl:text>Standard Rules</xsl:text>
                </xsl:element>
              </xsl:element>
            </xsl:element>
          <!-- extended rules -->
            <xsl:element name="c" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
              <xsl:attribute name="t">inlineStr</xsl:attribute>
              <xsl:attribute name="s">4</xsl:attribute>
              <xsl:element name="is" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
                <xsl:element name="t" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
                  <xsl:text>Extended Rules</xsl:text>
                </xsl:element>
              </xsl:element>
            </xsl:element>
          <!-- notes -->
            <xsl:element name="c" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
              <xsl:attribute name="t">inlineStr</xsl:attribute>
              <xsl:attribute name="s">4</xsl:attribute>
              <xsl:element name="is" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
                <xsl:element name="t" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
                  <xsl:text>Notes</xsl:text>
                </xsl:element>
              </xsl:element>
            </xsl:element>
          <xsl:if test="/map:Mapper/map:OUTPUT/map:XMLSyntax/map:EnableNamespaces/text() = 'yes'" >
            <!-- namespace -->
            <xsl:element name="c" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
              <xsl:attribute name="t">inlineStr</xsl:attribute>
              <xsl:attribute name="s">4</xsl:attribute>
              <xsl:element name="is" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
                <xsl:element name="t" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
                  <xsl:text>Namespace</xsl:text>
                </xsl:element>
              </xsl:element>
            </xsl:element>
            <xsl:element name="c" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
              <xsl:attribute name="t">inlineStr</xsl:attribute>
              <xsl:attribute name="s">4</xsl:attribute>
              <xsl:element name="is" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
                <xsl:element name="t" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
                  <xsl:text>Use Parent Namespace</xsl:text>
                </xsl:element>
              </xsl:element>
            </xsl:element>
          </xsl:if>
          <xsl:if test="$withoutFieldDefs!='yes'">
          <!-- data type -->
            <xsl:element name="c" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
              <xsl:attribute name="t">inlineStr</xsl:attribute>
              <xsl:attribute name="s">4</xsl:attribute>
              <xsl:element name="is" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
                <xsl:element name="t" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
                  <xsl:text>Data type</xsl:text>
                </xsl:element>
              </xsl:element>
            </xsl:element>
          <!-- format -->
            <xsl:element name="c" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
              <xsl:attribute name="t">inlineStr</xsl:attribute>
              <xsl:attribute name="s">4</xsl:attribute>
              <xsl:element name="is" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
                <xsl:element name="t" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
                  <xsl:text>Format</xsl:text>
                </xsl:element>
              </xsl:element>
            </xsl:element>
            <!-- C/M -->
            <xsl:element name="c" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
              <xsl:attribute name="t">inlineStr</xsl:attribute>
              <xsl:attribute name="s">4</xsl:attribute>
              <xsl:element name="is" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
                <xsl:element name="t" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
                  <xsl:text>C/M</xsl:text>
                </xsl:element>
              </xsl:element>
            </xsl:element>
          <!-- min -->
            <xsl:element name="c" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
              <xsl:attribute name="t">inlineStr</xsl:attribute>
              <xsl:attribute name="s">4</xsl:attribute>
              <xsl:element name="is" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
                <xsl:element name="t" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
                  <xsl:text>Min</xsl:text>
                </xsl:element>
              </xsl:element>
            </xsl:element>
          <!-- max -->
            <xsl:element name="c" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
              <xsl:attribute name="t">inlineStr</xsl:attribute>
              <xsl:attribute name="s">4</xsl:attribute>
              <xsl:element name="is" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
                <xsl:element name="t" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
                  <xsl:text>Max</xsl:text>
                </xsl:element>
              </xsl:element>
            </xsl:element>
          <!-- min length -->
            <xsl:element name="c" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
              <xsl:attribute name="t">inlineStr</xsl:attribute>
              <xsl:attribute name="s">4</xsl:attribute>
              <xsl:element name="is" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
                <xsl:element name="t" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
                  <xsl:text>Min Lenth</xsl:text>
                </xsl:element>
              </xsl:element>
            </xsl:element>
          <!-- max length -->
            <xsl:element name="c" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
              <xsl:attribute name="t">inlineStr</xsl:attribute>
              <xsl:attribute name="s">4</xsl:attribute>
              <xsl:element name="is" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
                <xsl:element name="t" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
                  <xsl:text>Max Length</xsl:text>
                </xsl:element>
              </xsl:element>
            </xsl:element>
            </xsl:if>
        </xsl:element>
        <xsl:apply-templates />
        <xsl:element name="row" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
          <xsl:attribute name="s">1</xsl:attribute>
          <xsl:attribute name="customFormat">1</xsl:attribute>
          <!-- active -->
          <xsl:element name="c" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
            <xsl:attribute name="t">inlineStr</xsl:attribute>
            <xsl:attribute name="s">1</xsl:attribute>
            <xsl:element name="is" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
              <xsl:element name="t" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main"/>
            </xsl:element>
          </xsl:element>
          <!-- node type -->
          <xsl:element name="c" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
            <xsl:attribute name="t">inlineStr</xsl:attribute>
            <xsl:attribute name="s">1</xsl:attribute>
            <xsl:element name="is" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
              <xsl:element name="t" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main"/>
            </xsl:element>
          </xsl:element>
          <!-- name -->
            <xsl:element name="c" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
              <xsl:attribute name="t">inlineStr</xsl:attribute>
              <xsl:attribute name="s">1</xsl:attribute>
              <xsl:element name="is" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
                <xsl:element name="t" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
                  <xsl:text>Post-session Rule</xsl:text>
              </xsl:element>
            </xsl:element>
          </xsl:element>
          <!-- description -->
          <xsl:element name="c" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
            <xsl:attribute name="t">inlineStr</xsl:attribute>
            <xsl:attribute name="s">1</xsl:attribute>
            <xsl:element name="is" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
              <xsl:element name="t" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main"/>
            </xsl:element>
          </xsl:element>
          <xsl:if test="not(/map:Mapper/map:INPUT/map:XMLSyntax)">  
            <!-- segment or record -->
            <xsl:element name="c" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
              <xsl:attribute name="t">inlineStr</xsl:attribute>
              <xsl:attribute name="s">2</xsl:attribute>
              <xsl:element name="is" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
                <xsl:element name="t" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main"/>
              </xsl:element>
            </xsl:element>
          </xsl:if>
          <!-- element or field -->
            <xsl:element name="c" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
              <xsl:attribute name="t">inlineStr</xsl:attribute>
              <xsl:attribute name="s">2</xsl:attribute>
              <xsl:element name="is" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
                <xsl:element name="t" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main"/>
              </xsl:element>
            </xsl:element>
          <!-- standard rules -->
            <xsl:element name="c" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
              <xsl:attribute name="t">inlineStr</xsl:attribute>
              <xsl:attribute name="s">2</xsl:attribute>
              <xsl:element name="is" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
                <xsl:element name="t" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main"/>
              </xsl:element>
            </xsl:element>
          <!-- extended rules -->
            <xsl:element name="c" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
              <xsl:attribute name="t">inlineStr</xsl:attribute>
              <xsl:attribute name="s">2</xsl:attribute>
              <xsl:element name="is" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
                <xsl:element name="t" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
                  <xsl:value-of select="/map:Mapper/map:MapDetails/map:ExplicitRule/map:PostSessionRule" />
                </xsl:element>
              </xsl:element>
            </xsl:element>
          <!-- note -->
          <xsl:element name="c" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
            <xsl:attribute name="t">inlineStr</xsl:attribute>
            <xsl:attribute name="s">2</xsl:attribute>
            <xsl:element name="is" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
              <xsl:element name="t" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main"/>
            </xsl:element>
          </xsl:element>
          <xsl:if test="/map:Mapper/map:OUTPUT/map:XMLSyntax/map:EnableNamespaces/text() = 'yes'" >
            <!-- namespace -->
            <xsl:element name="c" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
              <xsl:attribute name="t">inlineStr</xsl:attribute>
              <xsl:attribute name="s">1</xsl:attribute>
              <xsl:element name="is" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
                <xsl:element name="t" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main"/>
              </xsl:element>
            </xsl:element>
            <xsl:element name="c" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
              <xsl:attribute name="t">inlineStr</xsl:attribute>
              <xsl:attribute name="s">1</xsl:attribute>
              <xsl:element name="is" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
                <xsl:element name="t" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main"/>
              </xsl:element>
            </xsl:element>
          </xsl:if>
          <xsl:if test="$withoutFieldDefs!='yes'">
          <!-- data type -->
            <xsl:element name="c" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
              <xsl:attribute name="t">inlineStr</xsl:attribute>
              <xsl:attribute name="s">1</xsl:attribute>
              <xsl:element name="is" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
                <xsl:element name="t" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main"/>
              </xsl:element>
            </xsl:element>
          <!-- format -->
            <xsl:element name="c" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
              <xsl:attribute name="t">inlineStr</xsl:attribute>
              <xsl:attribute name="s">1</xsl:attribute>
              <xsl:element name="is" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
                <xsl:element name="t" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main"/>
              </xsl:element>
            </xsl:element>
          <!-- min -->
            <xsl:element name="c" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
              <xsl:attribute name="t">inlineStr</xsl:attribute>
              <xsl:attribute name="s">1</xsl:attribute>
              <xsl:element name="is" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
                <xsl:element name="t" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main"/>
              </xsl:element>
            </xsl:element>
          <!-- max -->
            <xsl:element name="c" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
              <xsl:attribute name="t">inlineStr</xsl:attribute>
              <xsl:attribute name="s">1</xsl:attribute>
              <xsl:element name="is" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
                <xsl:element name="t" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main"/>
              </xsl:element>
            </xsl:element>
          <!-- min length -->
            <xsl:element name="c" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
              <xsl:attribute name="t">inlineStr</xsl:attribute>
              <xsl:attribute name="s">1</xsl:attribute>
              <xsl:element name="is" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
                <xsl:element name="t" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main"/>
              </xsl:element>
            </xsl:element>
          <!-- max length -->
            <xsl:element name="c" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
              <xsl:attribute name="t">inlineStr</xsl:attribute>
              <xsl:attribute name="s">1</xsl:attribute>
              <xsl:element name="is" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
                <xsl:element name="t" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main"/>
              </xsl:element>
            </xsl:element>
            </xsl:if>
        </xsl:element>
      </xsl:element>
    </xsl:element>
  </xsl:template>
  <!-- XML -->

  <!-- Positional -->
  <xsl:template match="map:OUTPUT[map:PosSyntax]">
      <xsl:element name="worksheet" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
        <xsl:element name="sheetViews" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
          <xsl:element name="sheetView" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
            <xsl:attribute name="tabSelected">1</xsl:attribute>
            <xsl:attribute name="workbookViewId">0</xsl:attribute>
            <xsl:element name="pane" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
              <xsl:attribute name="xSplit">4</xsl:attribute>
              <xsl:attribute name="ySplit">2</xsl:attribute>
              <xsl:attribute name="topLeftCell">E3</xsl:attribute>
              <xsl:attribute name="activePane">bottomRight</xsl:attribute>
              <xsl:attribute name="state">frozen</xsl:attribute>
            </xsl:element>
          </xsl:element>
        </xsl:element>
        <xsl:if test="not(/map:Mapper/map:INPUT/map:XMLSyntax)" >
          <xsl:element name="cols" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
            <xsl:element name="col" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
              <xsl:attribute name="min">1</xsl:attribute>
              <xsl:attribute name="max">1</xsl:attribute>
              <xsl:attribute name="width">9.2</xsl:attribute>
              <xsl:attribute name="bestFit">1</xsl:attribute>
              <xsl:attribute name="customWidth">1</xsl:attribute>
            </xsl:element>
            <xsl:element name="col" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
              <xsl:attribute name="min">2</xsl:attribute>
              <xsl:attribute name="max">2</xsl:attribute>
              <xsl:attribute name="width">6</xsl:attribute>
              <xsl:attribute name="bestFit">1</xsl:attribute>
              <xsl:attribute name="customWidth">1</xsl:attribute>
            </xsl:element>
            <xsl:element name="col" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
              <xsl:attribute name="min">3</xsl:attribute>
              <xsl:attribute name="max">3</xsl:attribute>
              <xsl:attribute name="width">20</xsl:attribute>
              <xsl:attribute name="bestFit">1</xsl:attribute>
              <xsl:attribute name="customWidth">1</xsl:attribute>
            </xsl:element>
            <xsl:element name="col" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
              <xsl:attribute name="min">4</xsl:attribute>
              <xsl:attribute name="max">4</xsl:attribute>
              <xsl:attribute name="width">45</xsl:attribute>
              <xsl:attribute name="bestFit">1</xsl:attribute>
              <xsl:attribute name="customWidth">1</xsl:attribute>
            </xsl:element>
            <xsl:element name="col" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
              <xsl:attribute name="min">5</xsl:attribute>
              <xsl:attribute name="max">5</xsl:attribute>
              <xsl:attribute name="width">25</xsl:attribute>
              <xsl:attribute name="bestFit">1</xsl:attribute>
              <xsl:attribute name="customWidth">1</xsl:attribute>
            </xsl:element>
            <xsl:element name="col" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
              <xsl:attribute name="min">6</xsl:attribute>
              <xsl:attribute name="max">6</xsl:attribute>
              <xsl:attribute name="width">25</xsl:attribute>
              <xsl:attribute name="bestFit">1</xsl:attribute>
              <xsl:attribute name="customWidth">1</xsl:attribute>
            </xsl:element>
            <xsl:element name="col" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
              <xsl:attribute name="min">7</xsl:attribute>
              <xsl:attribute name="max">7</xsl:attribute>
              <xsl:attribute name="width">45</xsl:attribute>
              <xsl:attribute name="bestFit">1</xsl:attribute>
              <xsl:attribute name="customWidth">1</xsl:attribute>
            </xsl:element>
            <xsl:element name="col" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
              <xsl:attribute name="min">8</xsl:attribute>
              <xsl:attribute name="max">8</xsl:attribute>
              <xsl:attribute name="width">70</xsl:attribute>
              <xsl:attribute name="bestFit">1</xsl:attribute>
              <xsl:attribute name="customWidth">1</xsl:attribute>
            </xsl:element>
            <xsl:element name="col" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
              <xsl:attribute name="min">9</xsl:attribute>
              <xsl:attribute name="max">9</xsl:attribute>
              <xsl:attribute name="width">50</xsl:attribute>
              <xsl:attribute name="bestFit">1</xsl:attribute>
              <xsl:attribute name="customWidth">1</xsl:attribute>
            </xsl:element>
            <xsl:element name="col" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
              <xsl:attribute name="min">10</xsl:attribute>
              <xsl:attribute name="max">10</xsl:attribute>
              <xsl:attribute name="width">10</xsl:attribute>
              <xsl:attribute name="bestFit">1</xsl:attribute>
              <xsl:attribute name="customWidth">1</xsl:attribute>
            </xsl:element>
            <xsl:element name="col" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
              <xsl:attribute name="min">11</xsl:attribute>
              <xsl:attribute name="max">11</xsl:attribute>
              <xsl:attribute name="width">25</xsl:attribute>
              <xsl:attribute name="bestFit">1</xsl:attribute>
              <xsl:attribute name="customWidth">1</xsl:attribute>
            </xsl:element>
            <xsl:element name="col" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
              <xsl:attribute name="min">12</xsl:attribute>
              <xsl:attribute name="max">12</xsl:attribute>
              <xsl:attribute name="width">5</xsl:attribute>
              <xsl:attribute name="bestFit">1</xsl:attribute>
              <xsl:attribute name="customWidth">1</xsl:attribute>
            </xsl:element>
            <xsl:element name="col" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
              <xsl:attribute name="min">13</xsl:attribute>
              <xsl:attribute name="max">14</xsl:attribute>
              <xsl:attribute name="width">19</xsl:attribute>
              <xsl:attribute name="bestFit">1</xsl:attribute>
              <xsl:attribute name="customWidth">1</xsl:attribute>
            </xsl:element>
            <xsl:element name="col" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
              <xsl:attribute name="min">15</xsl:attribute>
              <xsl:attribute name="max">15</xsl:attribute>
              <xsl:attribute name="width">7</xsl:attribute>
              <xsl:attribute name="bestFit">1</xsl:attribute>
              <xsl:attribute name="customWidth">1</xsl:attribute>
            </xsl:element>
            <xsl:element name="col" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
              <xsl:attribute name="min">16</xsl:attribute>
              <xsl:attribute name="max">16</xsl:attribute>
              <xsl:attribute name="width">5</xsl:attribute>
              <xsl:attribute name="bestFit">1</xsl:attribute>
              <xsl:attribute name="customWidth">1</xsl:attribute>
            </xsl:element>
            <xsl:element name="col" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
              <xsl:attribute name="min">17</xsl:attribute>
              <xsl:attribute name="max">17</xsl:attribute>
              <xsl:attribute name="width">25</xsl:attribute>
              <xsl:attribute name="bestFit">1</xsl:attribute>
              <xsl:attribute name="customWidth">1</xsl:attribute>
            </xsl:element>
          </xsl:element>
        </xsl:if>
        <xsl:if test="/map:Mapper/map:INPUT/map:XMLSyntax" >
          <xsl:element name="cols" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
            <xsl:element name="col" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
              <xsl:attribute name="min">1</xsl:attribute>
              <xsl:attribute name="max">1</xsl:attribute>
              <xsl:attribute name="width">9.2</xsl:attribute>
              <xsl:attribute name="bestFit">1</xsl:attribute>
              <xsl:attribute name="customWidth">1</xsl:attribute>
            </xsl:element>
            <xsl:element name="col" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
              <xsl:attribute name="min">2</xsl:attribute>
              <xsl:attribute name="max">2</xsl:attribute>
              <xsl:attribute name="width">6</xsl:attribute>
              <xsl:attribute name="bestFit">1</xsl:attribute>
              <xsl:attribute name="customWidth">1</xsl:attribute>
            </xsl:element>
            <xsl:element name="col" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
              <xsl:attribute name="min">3</xsl:attribute>
              <xsl:attribute name="max">3</xsl:attribute>
              <xsl:attribute name="width">20</xsl:attribute>
              <xsl:attribute name="bestFit">1</xsl:attribute>
              <xsl:attribute name="customWidth">1</xsl:attribute>
            </xsl:element>
            <xsl:element name="col" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
              <xsl:attribute name="min">4</xsl:attribute>
              <xsl:attribute name="max">4</xsl:attribute>
              <xsl:attribute name="width">45</xsl:attribute>
              <xsl:attribute name="bestFit">1</xsl:attribute>
              <xsl:attribute name="customWidth">1</xsl:attribute>
            </xsl:element>
            <xsl:element name="col" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
              <xsl:attribute name="min">5</xsl:attribute>
              <xsl:attribute name="max">5</xsl:attribute>
              <xsl:attribute name="width">25</xsl:attribute>
              <xsl:attribute name="bestFit">1</xsl:attribute>
              <xsl:attribute name="customWidth">1</xsl:attribute>
            </xsl:element>
            <xsl:element name="col" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
              <xsl:attribute name="min">6</xsl:attribute>
              <xsl:attribute name="max">6</xsl:attribute>
              <xsl:attribute name="width">45</xsl:attribute>
              <xsl:attribute name="bestFit">1</xsl:attribute>
              <xsl:attribute name="customWidth">1</xsl:attribute>
            </xsl:element>
            <xsl:element name="col" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
              <xsl:attribute name="min">7</xsl:attribute>
              <xsl:attribute name="max">7</xsl:attribute>
              <xsl:attribute name="width">70</xsl:attribute>
              <xsl:attribute name="bestFit">1</xsl:attribute>
              <xsl:attribute name="customWidth">1</xsl:attribute>
            </xsl:element>
            <xsl:element name="col" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
              <xsl:attribute name="min">8</xsl:attribute>
              <xsl:attribute name="max">8</xsl:attribute>
              <xsl:attribute name="width">6</xsl:attribute>
              <xsl:attribute name="bestFit">1</xsl:attribute>
              <xsl:attribute name="customWidth">1</xsl:attribute>
            </xsl:element>
            <xsl:element name="col" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
              <xsl:attribute name="min">9</xsl:attribute>
              <xsl:attribute name="max">9</xsl:attribute>
              <xsl:attribute name="width">10</xsl:attribute>
              <xsl:attribute name="bestFit">1</xsl:attribute>
              <xsl:attribute name="customWidth">1</xsl:attribute>
            </xsl:element>
            <xsl:element name="col" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
              <xsl:attribute name="min">10</xsl:attribute>
              <xsl:attribute name="max">10</xsl:attribute>
              <xsl:attribute name="width">25</xsl:attribute>
              <xsl:attribute name="bestFit">1</xsl:attribute>
              <xsl:attribute name="customWidth">1</xsl:attribute>
            </xsl:element>
            <xsl:element name="col" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
              <xsl:attribute name="min">11</xsl:attribute>
              <xsl:attribute name="max">11</xsl:attribute>
              <xsl:attribute name="width">5</xsl:attribute>
              <xsl:attribute name="bestFit">1</xsl:attribute>
              <xsl:attribute name="customWidth">1</xsl:attribute>
            </xsl:element>
            <xsl:element name="col" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
              <xsl:attribute name="min">12</xsl:attribute>
              <xsl:attribute name="max">13</xsl:attribute>
              <xsl:attribute name="width">19</xsl:attribute>
              <xsl:attribute name="bestFit">1</xsl:attribute>
              <xsl:attribute name="customWidth">1</xsl:attribute>
            </xsl:element>
            <xsl:element name="col" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
              <xsl:attribute name="min">14</xsl:attribute>
              <xsl:attribute name="max">14</xsl:attribute>
              <xsl:attribute name="width">7</xsl:attribute>
              <xsl:attribute name="bestFit">1</xsl:attribute>
              <xsl:attribute name="customWidth">1</xsl:attribute>
            </xsl:element>
            <xsl:element name="col" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
              <xsl:attribute name="min">15</xsl:attribute>
              <xsl:attribute name="max">15</xsl:attribute>
              <xsl:attribute name="width">5</xsl:attribute>
              <xsl:attribute name="bestFit">1</xsl:attribute>
              <xsl:attribute name="customWidth">1</xsl:attribute>
            </xsl:element>
            <xsl:element name="col" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
              <xsl:attribute name="min">16</xsl:attribute>
              <xsl:attribute name="max">16</xsl:attribute>
              <xsl:attribute name="width">25</xsl:attribute>
              <xsl:attribute name="bestFit">1</xsl:attribute>
              <xsl:attribute name="customWidth">1</xsl:attribute>
            </xsl:element>
          </xsl:element>
        </xsl:if>
        <xsl:element name="sheetData" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
          <xsl:element name="row" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
            <xsl:attribute name="s">3</xsl:attribute>
            <xsl:attribute name="customFormat">1</xsl:attribute>
            <xsl:element name="c" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
              <xsl:attribute name="t">inlineStr</xsl:attribute>
              <xsl:attribute name="s">3</xsl:attribute>
              <xsl:element name="is" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
                <xsl:element name="t" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
                  <xsl:text>Positional:</xsl:text>
                </xsl:element>
              </xsl:element>
            </xsl:element>
          </xsl:element>
          <xsl:element name="row" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
            <xsl:attribute name="s">4</xsl:attribute>
            <xsl:attribute name="customFormat">1</xsl:attribute>
            <!-- active -->
            <xsl:element name="c" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
              <xsl:attribute name="t">inlineStr</xsl:attribute>
              <xsl:attribute name="s">4</xsl:attribute>
              <xsl:element name="is" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
                <xsl:element name="t"  namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
                  <xsl:text>Active</xsl:text>
                </xsl:element>
              </xsl:element>
            </xsl:element>
            <!-- level -->
            <xsl:element name="c" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
              <xsl:attribute name="t">inlineStr</xsl:attribute>
              <xsl:attribute name="s">4</xsl:attribute>
              <xsl:element name="is" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
                <xsl:element name="t"  namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
                  <xsl:text>Level</xsl:text>
                </xsl:element>
              </xsl:element>
            </xsl:element>
            <!-- name -->
            <xsl:element name="c" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
              <xsl:attribute name="t">inlineStr</xsl:attribute>
              <xsl:attribute name="s">4</xsl:attribute>
              <xsl:element name="is" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
                <xsl:element name="t"  namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
                  <xsl:text>Name</xsl:text>
                </xsl:element>
              </xsl:element>
            </xsl:element>
            <!-- description -->
            <xsl:element name="c" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
              <xsl:attribute name="t">inlineStr</xsl:attribute>
              <xsl:attribute name="s">4</xsl:attribute>
              <xsl:element name="is" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
                <xsl:element name="t"  namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
                  <xsl:text>Description</xsl:text>
                </xsl:element>
              </xsl:element>
            </xsl:element>
            <!-- Record -->
            <xsl:if test="not(/map:Mapper/map:INPUT/map:XMLSyntax)" >
              <xsl:element name="c" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
                <xsl:attribute name="t">inlineStr</xsl:attribute>
                <xsl:attribute name="s">4</xsl:attribute>
                <xsl:element name="is" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
                  <xsl:element name="t"  namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
                    <xsl:choose>
                      <xsl:when test="/map:Mapper/map:INPUT/map:EDISyntax">
                        <xsl:text>Segment</xsl:text>
                      </xsl:when>
                      <xsl:otherwise>
                        <xsl:text>Record</xsl:text>
                      </xsl:otherwise>
                    </xsl:choose>
                  </xsl:element>
                </xsl:element>
              </xsl:element>
            </xsl:if>
            <!-- Element/Field/XPath -->
            <xsl:element name="c" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
              <xsl:attribute name="t">inlineStr</xsl:attribute>
              <xsl:attribute name="s">4</xsl:attribute>
              <xsl:element name="is" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
                <xsl:element name="t"  namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
                  <xsl:choose>
                    <xsl:when test="/map:Mapper/map:INPUT/map:EDISyntax">
                      <xsl:text>Element</xsl:text>
                    </xsl:when>
                    <xsl:when test="/map:Mapper/map:INPUT/map:XMLSyntax">
                      <xsl:text>XPath</xsl:text>
                    </xsl:when>
                    <xsl:otherwise>
                      <xsl:text>Field</xsl:text>
                    </xsl:otherwise>
                  </xsl:choose>
                </xsl:element>
              </xsl:element>
            </xsl:element>
            <!-- Standard rule -->
            <xsl:element name="c" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
              <xsl:attribute name="t">inlineStr</xsl:attribute>
              <xsl:attribute name="s">4</xsl:attribute>
              <xsl:element name="is" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
                <xsl:element name="t"  namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
                  <xsl:text>Std. Rule</xsl:text>
                </xsl:element>
              </xsl:element>
            </xsl:element>
            <!-- Extended rules -->
            <xsl:element name="c" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
              <xsl:attribute name="t">inlineStr</xsl:attribute>
              <xsl:attribute name="s">4</xsl:attribute>
              <xsl:element name="is" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
                <xsl:element name="t"  namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
                  <xsl:text>Ext. Rule</xsl:text>
                </xsl:element>
              </xsl:element>
            </xsl:element>
            <!-- Notes -->
            <xsl:element name="c" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
              <xsl:attribute name="t">inlineStr</xsl:attribute>
              <xsl:attribute name="s">4</xsl:attribute>
              <xsl:element name="is" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
                <xsl:element name="t"  namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
                  <xsl:text>Notes</xsl:text>
                </xsl:element>
              </xsl:element>
            </xsl:element>
            
            <xsl:if test="$withoutFieldDefs!='yes'">
            <!-- Data Type -->
            <xsl:element name="c" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
              <xsl:attribute name="t">inlineStr</xsl:attribute>
              <xsl:attribute name="s">4</xsl:attribute>
              <xsl:element name="is" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
                <xsl:element name="t"  namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
                  <xsl:text>Data Type</xsl:text>
                </xsl:element>
              </xsl:element>
            </xsl:element>
            <!-- Format -->
            <xsl:element name="c" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
              <xsl:attribute name="t">inlineStr</xsl:attribute>
              <xsl:attribute name="s">4</xsl:attribute>
              <xsl:element name="is" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
                <xsl:element name="t"  namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
                  <xsl:text>Format</xsl:text>
                </xsl:element>
              </xsl:element>
            </xsl:element>
            <!-- Conditional/Mandatory -->
            <xsl:element name="c" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
              <xsl:attribute name="t">inlineStr</xsl:attribute>
              <xsl:attribute name="s">4</xsl:attribute>
              <xsl:element name="is" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
                <xsl:element name="t"  namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
                  <xsl:text>C/M</xsl:text>
                </xsl:element>
              </xsl:element>
            </xsl:element>
            <!-- Min -->
            <xsl:element name="c" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
              <xsl:attribute name="t">inlineStr</xsl:attribute>
              <xsl:attribute name="s">4</xsl:attribute>
              <xsl:element name="is" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
                <xsl:element name="t"  namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
                  <xsl:text>Min (groups/records)</xsl:text>
                  <xsl:text disable-output-escaping="yes"><![CDATA[&#10;]]></xsl:text>
                  <xsl:text>Start (fields)</xsl:text>
                </xsl:element>
              </xsl:element>
            </xsl:element>
            <!-- Max -->
            <xsl:element name="c" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
              <xsl:attribute name="t">inlineStr</xsl:attribute>
              <xsl:attribute name="s">4</xsl:attribute>
              <xsl:element name="is" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
                <xsl:element name="t"  namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
                  <xsl:text>Max (groups/records)</xsl:text>
                  <xsl:text disable-output-escaping="yes"><![CDATA[&#10;]]></xsl:text>
                  <xsl:text>Length (fields)</xsl:text>
                </xsl:element>
              </xsl:element>
            </xsl:element>
            <!-- Justify -->
            <xsl:element name="c" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
              <xsl:attribute name="t">inlineStr</xsl:attribute>
              <xsl:attribute name="s">4</xsl:attribute>
              <xsl:element name="is" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
                <xsl:element name="t"  namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
                  <xsl:text>Justify</xsl:text>
                </xsl:element>
              </xsl:element>
            </xsl:element>
            <!-- Pad -->
            <xsl:element name="c" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
              <xsl:attribute name="t">inlineStr</xsl:attribute>
              <xsl:attribute name="s">4</xsl:attribute>
              <xsl:element name="is" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
                <xsl:element name="t"  namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
                  <xsl:text>Pad</xsl:text>
                </xsl:element>
              </xsl:element>
            </xsl:element>
            <!-- Conditions -->
            <xsl:element name="c" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
              <xsl:attribute name="t">inlineStr</xsl:attribute>
              <xsl:attribute name="s">4</xsl:attribute>
              <xsl:element name="is" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
                <xsl:element name="t"  namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
                  <xsl:text>Conditions</xsl:text>
                </xsl:element>
              </xsl:element>
            </xsl:element>
            </xsl:if>
          </xsl:element>
          <xsl:apply-templates />
          <!-- post-session rule -->
          <xsl:element name="row" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
            <xsl:attribute name="s">1</xsl:attribute>
            <xsl:attribute name="customFormat">1</xsl:attribute>
            <xsl:element name="c" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
              <xsl:attribute name="t">inlineStr</xsl:attribute>
              <xsl:attribute name="s">1</xsl:attribute>
              <xsl:element name="is" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
                <xsl:element name="t"  namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main"/>
              </xsl:element>
            </xsl:element>
            <xsl:element name="c" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
              <xsl:attribute name="t">inlineStr</xsl:attribute>
              <xsl:attribute name="s">1</xsl:attribute>
              <xsl:element name="is" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
                <xsl:element name="t"  namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main"/>
              </xsl:element>
            </xsl:element>
            <xsl:element name="c" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
              <xsl:attribute name="t">inlineStr</xsl:attribute>
              <xsl:attribute name="s">1</xsl:attribute>
              <xsl:element name="is" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
                <xsl:element name="t"  namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
                  <xsl:text>Post-Session</xsl:text>
                </xsl:element>
              </xsl:element>
            </xsl:element>
            <xsl:element name="c" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
              <xsl:attribute name="t">inlineStr</xsl:attribute>
              <xsl:attribute name="s">1</xsl:attribute>
              <xsl:element name="is" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
                <xsl:element name="t"  namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main"/>
              </xsl:element>
            </xsl:element>
            <xsl:if test="not(/map:Mapper/map:INPUT/map:XMLSyntax)" >
              <xsl:element name="c" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
                <xsl:attribute name="t">inlineStr</xsl:attribute>
                <xsl:attribute name="s">1</xsl:attribute>
                <xsl:element name="is" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
                  <xsl:element name="t"  namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main"/>
                </xsl:element>
              </xsl:element>
            </xsl:if>
            <xsl:element name="c" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
              <xsl:attribute name="t">inlineStr</xsl:attribute>
              <xsl:attribute name="s">1</xsl:attribute>
              <xsl:element name="is" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
                <xsl:element name="t"  namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main"/>
              </xsl:element>
            </xsl:element>
            <xsl:element name="c" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
              <xsl:attribute name="t">inlineStr</xsl:attribute>
              <xsl:attribute name="s">1</xsl:attribute>
              <xsl:element name="is" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
                <xsl:element name="t"  namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main"/>
              </xsl:element>
            </xsl:element>
            <xsl:element name="c" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
              <xsl:attribute name="t">inlineStr</xsl:attribute>
                <xsl:attribute name="s">2</xsl:attribute>
                <xsl:element name="is" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
                  <xsl:element name="t"  namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
                    <xsl:value-of select="/map:Mapper/map:MapDetails/map:ExplicitRule/map:PostSessionRule" />
                 </xsl:element>
               </xsl:element>
             </xsl:element>
          </xsl:element>
        </xsl:element>
        <xsl:element name="mergeCells"  namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
          <xsl:attribute name="count">1</xsl:attribute>
          <xsl:element name="mergeCell" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
            <xsl:attribute name="ref">B1:D1</xsl:attribute>
          </xsl:element>
        </xsl:element>
      </xsl:element>
  </xsl:template>
  <!-- Positional -->

  <!-- Delimited or EDI -->
  <xsl:template match="map:OUTPUT[map:EDISyntax or map:VarDelimSyntax]">
      <xsl:element name="worksheet" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
        <xsl:element name="sheetViews" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
          <xsl:element name="sheetView" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
            <xsl:attribute name="tabSelected">1</xsl:attribute>
            <xsl:attribute name="workbookViewId">0</xsl:attribute>
            <xsl:element name="pane" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
              <xsl:attribute name="xSplit">4</xsl:attribute>
              <xsl:attribute name="ySplit">2</xsl:attribute>
              <xsl:attribute name="topLeftCell">E3</xsl:attribute>
              <xsl:attribute name="activePane">bottomRight</xsl:attribute>
              <xsl:attribute name="state">frozen</xsl:attribute>
            </xsl:element>
          </xsl:element>
        </xsl:element>
        <xsl:if test="not(/map:Mapper/map:INPUT/map:XMLSyntax)" >
          <xsl:element name="cols" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
            <xsl:element name="col" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
              <xsl:attribute name="min">1</xsl:attribute>
              <xsl:attribute name="max">1</xsl:attribute>
              <xsl:attribute name="width">9.2</xsl:attribute>
              <xsl:attribute name="bestFit">1</xsl:attribute>
              <xsl:attribute name="customWidth">1</xsl:attribute>
            </xsl:element>
            <xsl:element name="col" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
              <xsl:attribute name="min">2</xsl:attribute>
              <xsl:attribute name="max">2</xsl:attribute>
              <xsl:attribute name="width">6</xsl:attribute>
              <xsl:attribute name="bestFit">1</xsl:attribute>
              <xsl:attribute name="customWidth">1</xsl:attribute>
            </xsl:element>
            <xsl:element name="col" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
              <xsl:attribute name="min">3</xsl:attribute>
              <xsl:attribute name="max">3</xsl:attribute>
              <xsl:attribute name="width">20</xsl:attribute>
              <xsl:attribute name="bestFit">1</xsl:attribute>
              <xsl:attribute name="customWidth">1</xsl:attribute>
            </xsl:element>
            <xsl:element name="col" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
              <xsl:attribute name="min">4</xsl:attribute>
              <xsl:attribute name="max">4</xsl:attribute>
              <xsl:attribute name="width">45</xsl:attribute>
              <xsl:attribute name="bestFit">1</xsl:attribute>
              <xsl:attribute name="customWidth">1</xsl:attribute>
            </xsl:element>
            <xsl:element name="col" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
              <xsl:attribute name="min">5</xsl:attribute>
              <xsl:attribute name="max">5</xsl:attribute>
              <xsl:attribute name="width">25</xsl:attribute>
              <xsl:attribute name="bestFit">1</xsl:attribute>
              <xsl:attribute name="customWidth">1</xsl:attribute>
            </xsl:element>
            <xsl:element name="col" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
              <xsl:attribute name="min">6</xsl:attribute>
              <xsl:attribute name="max">6</xsl:attribute>
              <xsl:attribute name="width">25</xsl:attribute>
              <xsl:attribute name="bestFit">1</xsl:attribute>
              <xsl:attribute name="customWidth">1</xsl:attribute>
            </xsl:element>
            <xsl:element name="col" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
              <xsl:attribute name="min">7</xsl:attribute>
              <xsl:attribute name="max">7</xsl:attribute>
              <xsl:attribute name="width">45</xsl:attribute>
              <xsl:attribute name="bestFit">1</xsl:attribute>
              <xsl:attribute name="customWidth">1</xsl:attribute>
            </xsl:element>
            <xsl:element name="col" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
              <xsl:attribute name="min">8</xsl:attribute>
              <xsl:attribute name="max">8</xsl:attribute>
              <xsl:attribute name="width">70</xsl:attribute>
              <xsl:attribute name="bestFit">1</xsl:attribute>
              <xsl:attribute name="customWidth">1</xsl:attribute>
            </xsl:element>
            <xsl:element name="col" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
              <xsl:attribute name="min">9</xsl:attribute>
              <xsl:attribute name="max">9</xsl:attribute>
              <xsl:attribute name="width">50</xsl:attribute>
              <xsl:attribute name="bestFit">1</xsl:attribute>
              <xsl:attribute name="customWidth">1</xsl:attribute>
            </xsl:element>
            <xsl:element name="col" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
              <xsl:attribute name="min">10</xsl:attribute>
              <xsl:attribute name="max">10</xsl:attribute>
              <xsl:attribute name="width">10</xsl:attribute>
              <xsl:attribute name="bestFit">1</xsl:attribute>
              <xsl:attribute name="customWidth">1</xsl:attribute>
            </xsl:element>
            <xsl:element name="col" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
              <xsl:attribute name="min">11</xsl:attribute>
              <xsl:attribute name="max">11</xsl:attribute>
              <xsl:attribute name="width">25</xsl:attribute>
              <xsl:attribute name="bestFit">1</xsl:attribute>
              <xsl:attribute name="customWidth">1</xsl:attribute>
            </xsl:element>
            <xsl:element name="col" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
              <xsl:attribute name="min">12</xsl:attribute>
              <xsl:attribute name="max">12</xsl:attribute>
              <xsl:attribute name="width">5</xsl:attribute>
              <xsl:attribute name="bestFit">1</xsl:attribute>
              <xsl:attribute name="customWidth">1</xsl:attribute>
            </xsl:element>
            <xsl:element name="col" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
              <xsl:attribute name="min">13</xsl:attribute>
              <xsl:attribute name="max">14</xsl:attribute>
              <xsl:attribute name="width">6</xsl:attribute>
              <xsl:attribute name="bestFit">1</xsl:attribute>
              <xsl:attribute name="customWidth">1</xsl:attribute>
            </xsl:element>
            <xsl:element name="col" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
              <xsl:attribute name="min">15</xsl:attribute>
              <xsl:attribute name="max">15</xsl:attribute>
              <xsl:attribute name="width">25</xsl:attribute>
              <xsl:attribute name="bestFit">1</xsl:attribute>
              <xsl:attribute name="customWidth">1</xsl:attribute>
            </xsl:element>
          </xsl:element>
        </xsl:if>
        <xsl:if test="/map:Mapper/map:INPUT/map:XMLSyntax" >
          <xsl:element name="cols" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
            <xsl:element name="col" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
              <xsl:attribute name="min">1</xsl:attribute>
              <xsl:attribute name="max">1</xsl:attribute>
              <xsl:attribute name="width">9.2</xsl:attribute>
              <xsl:attribute name="bestFit">1</xsl:attribute>
              <xsl:attribute name="customWidth">1</xsl:attribute>
            </xsl:element>
            <xsl:element name="col" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
              <xsl:attribute name="min">2</xsl:attribute>
              <xsl:attribute name="max">2</xsl:attribute>
              <xsl:attribute name="width">6</xsl:attribute>
              <xsl:attribute name="bestFit">1</xsl:attribute>
              <xsl:attribute name="customWidth">1</xsl:attribute>
            </xsl:element>
            <xsl:element name="col" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
              <xsl:attribute name="min">3</xsl:attribute>
              <xsl:attribute name="max">3</xsl:attribute>
              <xsl:attribute name="width">20</xsl:attribute>
              <xsl:attribute name="bestFit">1</xsl:attribute>
              <xsl:attribute name="customWidth">1</xsl:attribute>
            </xsl:element>
            <xsl:element name="col" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
              <xsl:attribute name="min">4</xsl:attribute>
              <xsl:attribute name="max">4</xsl:attribute>
              <xsl:attribute name="width">45</xsl:attribute>
              <xsl:attribute name="bestFit">1</xsl:attribute>
              <xsl:attribute name="customWidth">1</xsl:attribute>
            </xsl:element>
            <xsl:element name="col" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
              <xsl:attribute name="min">5</xsl:attribute>
              <xsl:attribute name="max">5</xsl:attribute>
              <xsl:attribute name="width">25</xsl:attribute>
              <xsl:attribute name="bestFit">1</xsl:attribute>
              <xsl:attribute name="customWidth">1</xsl:attribute>
            </xsl:element>
            <xsl:element name="col" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
              <xsl:attribute name="min">6</xsl:attribute>
              <xsl:attribute name="max">6</xsl:attribute>
              <xsl:attribute name="width">45</xsl:attribute>
              <xsl:attribute name="bestFit">1</xsl:attribute>
              <xsl:attribute name="customWidth">1</xsl:attribute>
            </xsl:element>
            <xsl:element name="col" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
              <xsl:attribute name="min">7</xsl:attribute>
              <xsl:attribute name="max">7</xsl:attribute>
              <xsl:attribute name="width">70</xsl:attribute>
              <xsl:attribute name="bestFit">1</xsl:attribute>
              <xsl:attribute name="customWidth">1</xsl:attribute>
            </xsl:element>
            <xsl:element name="col" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
              <xsl:attribute name="min">8</xsl:attribute>
              <xsl:attribute name="max">8</xsl:attribute>
              <xsl:attribute name="width">6</xsl:attribute>
              <xsl:attribute name="bestFit">1</xsl:attribute>
              <xsl:attribute name="customWidth">1</xsl:attribute>
            </xsl:element>
            <xsl:element name="col" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
              <xsl:attribute name="min">9</xsl:attribute>
              <xsl:attribute name="max">9</xsl:attribute>
              <xsl:attribute name="width">10</xsl:attribute>
              <xsl:attribute name="bestFit">1</xsl:attribute>
              <xsl:attribute name="customWidth">1</xsl:attribute>
            </xsl:element>
            <xsl:element name="col" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
              <xsl:attribute name="min">10</xsl:attribute>
              <xsl:attribute name="max">10</xsl:attribute>
              <xsl:attribute name="width">25</xsl:attribute>
              <xsl:attribute name="bestFit">1</xsl:attribute>
              <xsl:attribute name="customWidth">1</xsl:attribute>
            </xsl:element>
            <xsl:element name="col" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
              <xsl:attribute name="min">11</xsl:attribute>
              <xsl:attribute name="max">11</xsl:attribute>
              <xsl:attribute name="width">5</xsl:attribute>
              <xsl:attribute name="bestFit">1</xsl:attribute>
              <xsl:attribute name="customWidth">1</xsl:attribute>
            </xsl:element>
            <xsl:element name="col" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
              <xsl:attribute name="min">12</xsl:attribute>
              <xsl:attribute name="max">13</xsl:attribute>
              <xsl:attribute name="width">6</xsl:attribute>
              <xsl:attribute name="bestFit">1</xsl:attribute>
              <xsl:attribute name="customWidth">1</xsl:attribute>
            </xsl:element>
            <xsl:element name="col" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
              <xsl:attribute name="min">14</xsl:attribute>
              <xsl:attribute name="max">14</xsl:attribute>
              <xsl:attribute name="width">25</xsl:attribute>
              <xsl:attribute name="bestFit">1</xsl:attribute>
              <xsl:attribute name="customWidth">1</xsl:attribute>
            </xsl:element>
          </xsl:element>
        </xsl:if>
        <xsl:element name="sheetData" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
          <xsl:element name="row" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
            <xsl:attribute name="s">3</xsl:attribute>
            <xsl:attribute name="customFormat">1</xsl:attribute>
            <xsl:element name="c" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
              <xsl:attribute name="t">inlineStr</xsl:attribute>
              <xsl:attribute name="s">3</xsl:attribute>
              <xsl:element name="is" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
                <xsl:element name="t" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
                  <xsl:choose>
                    <xsl:when test="map:EDISyntax">
                      <xsl:text>EDI: </xsl:text>
                    </xsl:when>
                    <xsl:when test="map:VarDelimSyntax">
                      <xsl:text>Variable Delimited: </xsl:text>
                    </xsl:when>
                  </xsl:choose>
                </xsl:element>
              </xsl:element>
            </xsl:element>
            <xsl:element name="c" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
              <xsl:attribute name="t">inlineStr</xsl:attribute>
              <xsl:attribute name="s">3</xsl:attribute>
              <xsl:element name="is" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
                <xsl:element name="t" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
                  <xsl:value-of select="/map:Mapper/map:MapDetails/map:EDIAssociations_OUT/map:BindingID"/>
                  <xsl:text> </xsl:text>
                  <xsl:value-of select="/map:Mapper/map:MapDetails/map:EDIAssociations_OUT/map:BindingDescription"/>
                  <xsl:text> </xsl:text>
                  <xsl:value-of select="/map:Mapper/map:MapDetails/map:EDIAssociations_OUT/map:VersionID"/>
                  <xsl:text> </xsl:text>
                  <xsl:value-of select="/map:Mapper/map:MapDetails/map:EDIAssociations_OUT/map:AgencyDescription"/>
                </xsl:element>
              </xsl:element>
            </xsl:element>
          </xsl:element>
          <xsl:element name="row" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
            <xsl:attribute name="s">4</xsl:attribute>
            <xsl:attribute name="customFormat">1</xsl:attribute>
            <!-- active -->
            <xsl:element name="c" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
              <xsl:attribute name="t">inlineStr</xsl:attribute>
              <xsl:attribute name="s">4</xsl:attribute>
              <xsl:element name="is" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
                <xsl:element name="t"  namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
                  <xsl:text>Active</xsl:text>
                </xsl:element>
              </xsl:element>
            </xsl:element>
            <!-- level -->
            <xsl:element name="c" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
              <xsl:attribute name="t">inlineStr</xsl:attribute>
              <xsl:attribute name="s">4</xsl:attribute>
              <xsl:element name="is" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
                <xsl:element name="t"  namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
                  <xsl:text>Level</xsl:text>
                </xsl:element>
              </xsl:element>
            </xsl:element>
            <!-- name -->
            <xsl:element name="c" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
              <xsl:attribute name="t">inlineStr</xsl:attribute>
              <xsl:attribute name="s">4</xsl:attribute>
              <xsl:element name="is" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
                <xsl:element name="t"  namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
                  <xsl:text>Name</xsl:text>
                </xsl:element>
              </xsl:element>
            </xsl:element>
            <!-- description -->
            <xsl:element name="c" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
              <xsl:attribute name="t">inlineStr</xsl:attribute>
              <xsl:attribute name="s">4</xsl:attribute>
              <xsl:element name="is" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
                <xsl:element name="t"  namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
                  <xsl:text>Description</xsl:text>
                </xsl:element>
              </xsl:element>
            </xsl:element>
            <!-- Record -->
            <xsl:if test="not(/map:Mapper/map:INPUT/map:XMLSyntax)" >
              <xsl:element name="c" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
                <xsl:attribute name="t">inlineStr</xsl:attribute>
                <xsl:attribute name="s">4</xsl:attribute>
                <xsl:element name="is" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
                  <xsl:element name="t"  namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
                    <xsl:choose>
                      <xsl:when test="/map:Mapper/map:INPUT/map:EDISyntax">
                        <xsl:text>Segment</xsl:text>
                      </xsl:when>
                      <xsl:otherwise>
                        <xsl:text>Record</xsl:text>
                      </xsl:otherwise>
                    </xsl:choose>
                  </xsl:element>
                </xsl:element>
              </xsl:element>
            </xsl:if>
            <!-- Element/Field/XPath -->
            <xsl:element name="c" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
              <xsl:attribute name="t">inlineStr</xsl:attribute>
              <xsl:attribute name="s">4</xsl:attribute>
              <xsl:element name="is" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
                <xsl:element name="t"  namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
                  <xsl:choose>
                    <xsl:when test="/map:Mapper/map:INPUT/map:EDISyntax">
                      <xsl:text>Element</xsl:text>
                    </xsl:when>
                    <xsl:when test="/map:Mapper/map:INPUT/map:XMLSyntax">
                      <xsl:text>XPath</xsl:text>
                    </xsl:when>
                    <xsl:otherwise>
                      <xsl:text>Field</xsl:text>
                    </xsl:otherwise>
                  </xsl:choose>
                </xsl:element>
              </xsl:element>
            </xsl:element>
            <!-- Standard rule -->
            <xsl:element name="c" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
              <xsl:attribute name="t">inlineStr</xsl:attribute>
              <xsl:attribute name="s">4</xsl:attribute>
              <xsl:element name="is" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
                <xsl:element name="t"  namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
                  <xsl:text>Std. Rule</xsl:text>
                </xsl:element>
              </xsl:element>
            </xsl:element>
            <!-- Extended rules -->
            <xsl:element name="c" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
              <xsl:attribute name="t">inlineStr</xsl:attribute>
              <xsl:attribute name="s">4</xsl:attribute>
              <xsl:element name="is" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
                <xsl:element name="t"  namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
                  <xsl:text>Ext. Rule</xsl:text>
                </xsl:element>
              </xsl:element>
            </xsl:element>
            <!-- Notes -->
            <xsl:element name="c" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
              <xsl:attribute name="t">inlineStr</xsl:attribute>
              <xsl:attribute name="s">4</xsl:attribute>
              <xsl:element name="is" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
                <xsl:element name="t"  namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
                  <xsl:text>Notes</xsl:text>
                </xsl:element>
              </xsl:element>
            </xsl:element>
            
            <xsl:if test="$withoutFieldDefs!='yes'">
            <!-- Data Type -->
            <xsl:element name="c" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
              <xsl:attribute name="t">inlineStr</xsl:attribute>
              <xsl:attribute name="s">4</xsl:attribute>
              <xsl:element name="is" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
                <xsl:element name="t"  namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
                  <xsl:text>Data Type</xsl:text>
                </xsl:element>
              </xsl:element>
            </xsl:element>
            <!-- Format -->
            <xsl:element name="c" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
              <xsl:attribute name="t">inlineStr</xsl:attribute>
              <xsl:attribute name="s">4</xsl:attribute>
              <xsl:element name="is" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
                <xsl:element name="t"  namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
                  <xsl:text>Format</xsl:text>
                </xsl:element>
              </xsl:element>
            </xsl:element>
            <!-- Conditional/Mandatory -->
            <xsl:element name="c" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
              <xsl:attribute name="t">inlineStr</xsl:attribute>
              <xsl:attribute name="s">4</xsl:attribute>
              <xsl:element name="is" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
                <xsl:element name="t"  namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
                  <xsl:text>C/M</xsl:text>
                </xsl:element>
              </xsl:element>
            </xsl:element>
            <!-- Min -->
            <xsl:element name="c" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
              <xsl:attribute name="t">inlineStr</xsl:attribute>
              <xsl:attribute name="s">4</xsl:attribute>
              <xsl:element name="is" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
                <xsl:element name="t"  namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
                  <xsl:text>Min</xsl:text>
                </xsl:element>
              </xsl:element>
            </xsl:element>
            <!-- Max -->
            <xsl:element name="c" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
              <xsl:attribute name="t">inlineStr</xsl:attribute>
              <xsl:attribute name="s">4</xsl:attribute>
              <xsl:element name="is" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
                <xsl:element name="t"  namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
                  <xsl:text>Max</xsl:text>
                </xsl:element>
              </xsl:element>
            </xsl:element>
            <!-- Conditions -->
            <xsl:element name="c" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
              <xsl:attribute name="t">inlineStr</xsl:attribute>
              <xsl:attribute name="s">4</xsl:attribute>
              <xsl:element name="is" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
                <xsl:element name="t"  namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
                  <xsl:text>Conditions</xsl:text>
                </xsl:element>
              </xsl:element>
            </xsl:element>
            </xsl:if>
          </xsl:element>
          <xsl:apply-templates />
          <!-- post-session rule -->
          <xsl:element name="row" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
            <xsl:attribute name="s">1</xsl:attribute>
            <xsl:attribute name="customFormat">1</xsl:attribute>
            <xsl:element name="c" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
              <xsl:attribute name="t">inlineStr</xsl:attribute>
              <xsl:attribute name="s">1</xsl:attribute>
              <xsl:element name="is" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
                <xsl:element name="t"  namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main"/>
              </xsl:element>
            </xsl:element>
            <xsl:element name="c" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
              <xsl:attribute name="t">inlineStr</xsl:attribute>
              <xsl:attribute name="s">1</xsl:attribute>
              <xsl:element name="is" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
                <xsl:element name="t"  namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main"/>
              </xsl:element>
            </xsl:element>
            <xsl:element name="c" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
              <xsl:attribute name="t">inlineStr</xsl:attribute>
              <xsl:attribute name="s">1</xsl:attribute>
              <xsl:element name="is" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
                <xsl:element name="t"  namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
                  <xsl:text>Post-Session</xsl:text>
                </xsl:element>
              </xsl:element>
            </xsl:element>
            <xsl:element name="c" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
              <xsl:attribute name="t">inlineStr</xsl:attribute>
              <xsl:attribute name="s">1</xsl:attribute>
              <xsl:element name="is" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
                <xsl:element name="t"  namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main"/>
              </xsl:element>
            </xsl:element>
            <xsl:if test="not(/map:Mapper/map:INPUT/map:XMLSyntax)" >
              <xsl:element name="c" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
                <xsl:attribute name="t">inlineStr</xsl:attribute>
                <xsl:attribute name="s">1</xsl:attribute>
                <xsl:element name="is" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
                  <xsl:element name="t"  namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main"/>
                </xsl:element>
              </xsl:element>
            </xsl:if>
            <xsl:element name="c" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
              <xsl:attribute name="t">inlineStr</xsl:attribute>
              <xsl:attribute name="s">1</xsl:attribute>
              <xsl:element name="is" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
                <xsl:element name="t"  namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main"/>
              </xsl:element>
            </xsl:element>
            <xsl:element name="c" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
              <xsl:attribute name="t">inlineStr</xsl:attribute>
              <xsl:attribute name="s">1</xsl:attribute>
              <xsl:element name="is" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
                <xsl:element name="t"  namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main"/>
              </xsl:element>
            </xsl:element>
            <xsl:element name="c" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
              <xsl:attribute name="t">inlineStr</xsl:attribute>
                <xsl:attribute name="s">2</xsl:attribute>
                <xsl:element name="is" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
                  <xsl:element name="t"  namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
                    <xsl:value-of select="/map:Mapper/map:MapDetails/map:ExplicitRule/map:PostSessionRule" />
                 </xsl:element>
               </xsl:element>
             </xsl:element>
          </xsl:element>
        </xsl:element>
      </xsl:element>
  </xsl:template>
  <!-- Delimited or EDI -->
  
  <!-- Group -->
    <xsl:template match=" map:Group[ancestor::map:OUTPUT]">
    <xsl:if test="$withInactiveFields='yes' or map:Active='1'">
    <xsl:element name="row" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
      <xsl:attribute name="s">14</xsl:attribute>
      <xsl:attribute name="customFormat">1</xsl:attribute>
      <xsl:if test="map:Active = 0" >
        <xsl:attribute name="hidden">1</xsl:attribute>
      </xsl:if>
      <!-- active -->
      <xsl:element name="c" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
        <xsl:attribute name="t">inlineStr</xsl:attribute>
        <xsl:attribute name="s">5</xsl:attribute>
        <xsl:element name="is" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
          <xsl:element name="t" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main" >
            <xsl:choose>
              <xsl:when test="map:Active = 1">
                <xsl:text>Yes</xsl:text>
              </xsl:when>
              <xsl:otherwise>
                <xsl:text>No</xsl:text>
              </xsl:otherwise>
            </xsl:choose>
          </xsl:element>
        </xsl:element>
      </xsl:element>
      <!-- level -->
      <xsl:element name="c"  namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
        <xsl:attribute name="t">inlineStr</xsl:attribute>
        <xsl:attribute name="s">5</xsl:attribute>
        <xsl:element name="is" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
          <xsl:element name="t" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
            <xsl:value-of select="count(ancestor::map:Group | ancestor::map:Segment)" />
          </xsl:element>
        </xsl:element>
      </xsl:element>
      <!-- name -->
      <xsl:element name="c" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
        <xsl:attribute name="t">inlineStr</xsl:attribute>
        <xsl:attribute name="s">14</xsl:attribute>
        <xsl:element name="is" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
          <xsl:element name="t" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
            <xsl:attribute name="space" namespace="http://www.w3.org/XML/1998/namespace">preserve</xsl:attribute>
            <!--
            Gets confusing quickly for EDI and flat files
            <xsl:for-each select="ancestor::map:Group">
              <xsl:choose>
                <xsl:when test="following-sibling::map:Group or following-sibling::map:Segment or following-sibling::map:PosRecord or following-sibling::map:VarDelimRecord">
                  <xsl:text>| </xsl:text>
                </xsl:when>
                <xsl:otherwise>
                  <xsl:text>  </xsl:text>
                </xsl:otherwise>
              </xsl:choose>
            </xsl:for-each>
            <xsl:text>|-</xsl:text>
            -->
            <xsl:value-of select="map:Name" />
          </xsl:element>
        </xsl:element>
      </xsl:element>
      <!-- description -->
      <xsl:element name="c" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
        <xsl:attribute name="t">inlineStr</xsl:attribute>
        <xsl:attribute name="s">5</xsl:attribute>
        <xsl:element name="is" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
          <xsl:element name="t" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
            <xsl:value-of select="map:Description" />
          </xsl:element>
        </xsl:element>
      </xsl:element>
      <!-- record -->
      <xsl:if test="not(/map:Mapper/map:INPUT/map:XMLSyntax)">
        <xsl:element name="c" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
          <xsl:attribute name="t">inlineStr</xsl:attribute>
          <xsl:attribute name="s">5</xsl:attribute>
          <xsl:element name="is" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
            <xsl:element name="t" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main" />
          </xsl:element>
        </xsl:element>
      </xsl:if>
      <!-- field -->
      <xsl:element name="c" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
        <xsl:attribute name="t">inlineStr</xsl:attribute>
        <xsl:attribute name="s">5</xsl:attribute>
        <xsl:element name="is" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
          <xsl:element name="t" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main" />
        </xsl:element>
      </xsl:element>
      <!-- standard rules -->
      <xsl:element name="c" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
        <xsl:attribute name="t">inlineStr</xsl:attribute>
        <xsl:attribute name="s">5</xsl:attribute>
        <xsl:element name="is" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
          <xsl:element name="t" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main" />
        </xsl:element>
      </xsl:element>
      <!-- extended rules -->
      <xsl:element name="c" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
        <xsl:attribute name="t">inlineStr</xsl:attribute>
        <xsl:attribute name="s">6</xsl:attribute>
        <xsl:element name="is" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
          <xsl:element name="t" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
            <xsl:if test="map:ExplicitRule/map:OnBegin[text()]">
              <xsl:text>On Begin: </xsl:text>
              <xsl:text disable-output-escaping="yes"><![CDATA[&#10;]]></xsl:text>
              <xsl:value-of select="map:ExplicitRule/map:OnBegin" />
            </xsl:if>
            <xsl:if test="map:ExplicitRule/map:OnEnd[text()]">
              <xsl:text>On End: </xsl:text>
              <xsl:text disable-output-escaping="yes"><![CDATA[&#10;]]></xsl:text>
              <xsl:value-of select="map:ExplicitRule/map:OnEnd" />
            </xsl:if>
          </xsl:element>
        </xsl:element>
      </xsl:element>
      <!-- notes -->
      <xsl:element name="c" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
        <xsl:attribute name="t">inlineStr</xsl:attribute>
        <xsl:attribute name="s">6</xsl:attribute>
        <xsl:element name="is" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
          <xsl:element name="t" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
            <xsl:value-of select="map:Note" />
          </xsl:element>
        </xsl:element>
      </xsl:element>
      
      <xsl:if test="$withoutFieldDefs!='yes'">
      <!-- data type -->
      <xsl:element name="c" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
        <xsl:attribute name="t">inlineStr</xsl:attribute>
        <xsl:attribute name="s">5</xsl:attribute>
        <xsl:element name="is" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
          <xsl:element name="t" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
            <xsl:text>Group</xsl:text>
          </xsl:element>
        </xsl:element>
      </xsl:element>
      <!-- format -->
      <xsl:element name="c" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
        <xsl:attribute name="t">inlineStr</xsl:attribute>
        <xsl:attribute name="s">5</xsl:attribute>
        <xsl:element name="is" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
          <xsl:element name="t" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main" />
        </xsl:element>
      </xsl:element>
      <!-- C/M -->
      <xsl:element name="c" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
        <xsl:attribute name="t">inlineStr</xsl:attribute>
        <xsl:attribute name="s">5</xsl:attribute>
        <xsl:element name="is" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
          <xsl:element name="t" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main" >
            <xsl:choose>
              <xsl:when test="map:Min > 0">
                <xsl:text>M</xsl:text>
              </xsl:when>
              <xsl:otherwise>
                <xsl:text>C</xsl:text>
              </xsl:otherwise>
            </xsl:choose>
          </xsl:element>
        </xsl:element>
      </xsl:element>
      <!-- Min -->
      <xsl:element name="c" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
        <xsl:attribute name="t">inlineStr</xsl:attribute>
        <xsl:attribute name="s">5</xsl:attribute>
        <xsl:element name="is" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
          <xsl:element name="t" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
            <xsl:value-of select="map:Min" />
          </xsl:element>
        </xsl:element>
      </xsl:element>
      <!-- Max -->
      <xsl:element name="c" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
        <xsl:attribute name="t">inlineStr</xsl:attribute>
        <xsl:attribute name="s">5</xsl:attribute>
        <xsl:element name="is" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
          <xsl:element name="t" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
            <xsl:value-of select="map:Max" />
          </xsl:element>
        </xsl:element>
      </xsl:element>
      </xsl:if>
    </xsl:element>
    <xsl:apply-templates/>
    </xsl:if>
  </xsl:template>
  <!-- Group -->
  
  <!-- Segment or Record -->
  <xsl:template match="map:Segment[ancestor::map:OUTPUT] | map:PosRecord[ancestor::map:OUTPUT] | map:VarDelimRecord[ancestor::map:OUTPUT]">
    <xsl:if test="$withInactiveFields='yes' or map:Active='1'">
    <xsl:element name="row" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
      <xsl:attribute name="s">12</xsl:attribute>
      <xsl:attribute name="customFormat">1</xsl:attribute>
      <xsl:if test="map:Active = 0" >
        <xsl:attribute name="hidden">1</xsl:attribute>
      </xsl:if>
      <!-- active -->
      <xsl:element name="c"  namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
        <xsl:attribute name="t">inlineStr</xsl:attribute>
        <xsl:attribute name="s">7</xsl:attribute>
        <xsl:element name="is" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
          <xsl:element name="t" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
            <xsl:choose>
              <xsl:when test="map:Active = 1">
                <xsl:text>Yes</xsl:text>
              </xsl:when>
              <xsl:otherwise>
                <xsl:text>No</xsl:text>
              </xsl:otherwise>
            </xsl:choose>
          </xsl:element>
        </xsl:element>
      </xsl:element>
      <!-- level -->
      <xsl:element name="c"  namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
        <xsl:attribute name="t">inlineStr</xsl:attribute>
        <xsl:attribute name="s">7</xsl:attribute>
        <xsl:element name="is" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
          <xsl:element name="t" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
            <xsl:value-of select="count(ancestor::map:Group | ancestor::map:Segment)" />
          </xsl:element>
        </xsl:element>
      </xsl:element>
      <!-- name -->
      <xsl:element name="c"  namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
        <xsl:attribute name="t">inlineStr</xsl:attribute>
        <xsl:attribute name="s">12</xsl:attribute>
        <xsl:element name="is" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
          <xsl:element name="t" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
            <xsl:attribute name="space" namespace="http://www.w3.org/XML/1998/namespace">preserve</xsl:attribute>
            <!--
            <xsl:for-each select="ancestor::map:Group">
              <xsl:choose>
                <xsl:when test="following-sibling::map:Group or following-sibling::map:Segment or following-sibling::map:PosRecord or following-sibling::map:VarDelimRecord">
                  <xsl:text>| </xsl:text>
                </xsl:when>
                <xsl:otherwise>
                  <xsl:text>  </xsl:text>
                </xsl:otherwise>
              </xsl:choose>
            </xsl:for-each>
            <xsl:text>|-</xsl:text>
            -->
            <xsl:value-of select="map:Name"/>
          </xsl:element>
        </xsl:element>
      </xsl:element>
      <!-- description -->
      <xsl:element name="c"  namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
        <xsl:attribute name="t">inlineStr</xsl:attribute>
        <xsl:attribute name="s">7</xsl:attribute>
        <xsl:element name="is" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
          <xsl:element name="t" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main" />
        </xsl:element>
      </xsl:element>
      <xsl:if test="not(/map:Mapper/map:INPUT/map:XMLSyntax)" >
        <!-- linked segment/record -->
        <xsl:element name="c"  namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
          <xsl:attribute name="t">inlineStr</xsl:attribute>
          <xsl:attribute name="s">7</xsl:attribute>
          <xsl:element name="is" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
            <xsl:element name="t" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main" />
          </xsl:element>
        </xsl:element>
      </xsl:if>
      <!-- linked element/field -->
      <xsl:element name="c"  namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
        <xsl:attribute name="t">inlineStr</xsl:attribute>
        <xsl:attribute name="s">7</xsl:attribute>
        <xsl:element name="is" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
          <xsl:element name="t" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main" />
        </xsl:element>
      </xsl:element>
      <!-- standard rules -->
      <xsl:element name="c"  namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
        <xsl:attribute name="t">inlineStr</xsl:attribute>
        <xsl:attribute name="s">8</xsl:attribute>
        <xsl:element name="is" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
          <xsl:element name="t" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main" />
        </xsl:element>
      </xsl:element>
      <!-- extended rules -->
      <xsl:element name="c"  namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
        <xsl:attribute name="t">inlineStr</xsl:attribute>
        <xsl:attribute name="s">8</xsl:attribute>
        <xsl:element name="is" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
          <xsl:element name="t" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
            <xsl:if test="map:ExplicitRule/map:OnBegin[text()]">
              <xsl:text>On Begin: </xsl:text>
              <xsl:text disable-output-escaping="yes"><![CDATA[&#10;]]></xsl:text>
              <xsl:value-of select="map:ExplicitRule/map:OnBegin" />
            </xsl:if>
            <xsl:if test="map:ExplicitRule/map:OnEnd[text()]">
              <xsl:text>On End: </xsl:text>
              <xsl:text disable-output-escaping="yes"><![CDATA[&#10;]]></xsl:text>
              <xsl:value-of select="map:ExplicitRule/map:OnEnd" />
            </xsl:if>
          </xsl:element>
        </xsl:element>
      </xsl:element>
      <!-- notes -->
      <xsl:element name="c"  namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
        <xsl:attribute name="t">inlineStr</xsl:attribute>
        <xsl:attribute name="s">8</xsl:attribute>
        <xsl:element name="is" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
          <xsl:element name="t" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
            <xsl:value-of select="map:Note" />
          </xsl:element>
        </xsl:element>
      </xsl:element>
      <xsl:if test="$withoutFieldDefs!='yes'">
      <!-- data type -->
      <xsl:element name="c"  namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
        <xsl:attribute name="t">inlineStr</xsl:attribute>
        <xsl:attribute name="s">7</xsl:attribute>
        <xsl:element name="is" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
          <xsl:element name="t" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main" >
            <xsl:choose>
              <xsl:when test="ancestor::map:EDISyntax">
                <xsl:text>Segment</xsl:text>
              </xsl:when>
              <xsl:otherwise>
                <xsl:text>Record</xsl:text>
              </xsl:otherwise>
            </xsl:choose>
          </xsl:element>
        </xsl:element>
      </xsl:element>
      <!-- format -->
      <xsl:element name="c"  namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
        <xsl:attribute name="t">inlineStr</xsl:attribute>
        <xsl:attribute name="s">8</xsl:attribute>
        <xsl:element name="is" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
          <xsl:element name="t" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
            <xsl:if test="ancestor::map:PosSyntax">
              <xsl:text>Tag "</xsl:text><xsl:value-of select="map:BlockSig/map:Tag[1]"/><xsl:text>"</xsl:text>
              <xsl:text disable-output-escaping="yes"><![CDATA[&#10;]]></xsl:text>
              <xsl:text>at </xsl:text><xsl:value-of select="map:BlockSig/map:TagPos[1] + 1" /><xsl:text> for </xsl:text><xsl:value-of select="string-length(map:BlockSig/map:Tag[1])" />
            </xsl:if>
            <xsl:if test="ancestor::map:VarDelimSyntax">
              <xsl:text>Tag "</xsl:text><xsl:value-of select="map:BlockSig/map:Tag[1]"/><xsl:text>"</xsl:text>
              <xsl:text disable-output-escaping="yes"><![CDATA[&#10;]]></xsl:text>
              <xsl:text>in field </xsl:text><xsl:value-of select="map:BlockSig/map:TagFieldNum[1] + 1" />
            </xsl:if>
          </xsl:element>
        </xsl:element>
      </xsl:element>
      <!-- conditional status -->
      <xsl:element name="c"  namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
        <xsl:attribute name="t">inlineStr</xsl:attribute>
        <xsl:attribute name="s">7</xsl:attribute>
        <xsl:element name="is" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
          <xsl:element name="t" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
            <xsl:if test="map:Min = 0">
              <xsl:text>C</xsl:text>
            </xsl:if>
            <xsl:if test="map:Min &gt; 0">
              <xsl:text>M</xsl:text>
            </xsl:if>
          </xsl:element>
        </xsl:element>
      </xsl:element>
      <!-- min -->
      <xsl:element name="c"  namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
        <xsl:attribute name="t">inlineStr</xsl:attribute>
        <xsl:attribute name="s">7</xsl:attribute>
        <xsl:element name="is" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
          <xsl:element name="t" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
            <xsl:value-of select="map:Min" />
          </xsl:element>
        </xsl:element>
      </xsl:element>
      <!-- max -->
      <xsl:element name="c"  namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
        <xsl:attribute name="t">inlineStr</xsl:attribute>
        <xsl:attribute name="s">7</xsl:attribute>
        <xsl:element name="is" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
          <xsl:element name="t" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
            <xsl:value-of select="map:Max" />
          </xsl:element>
        </xsl:element>
      </xsl:element>
      </xsl:if>
    </xsl:element>
    <xsl:apply-templates/>
    </xsl:if>
  </xsl:template>
  <!-- Segment or Record -->
  
  <!-- XML Particle -->
    <xsl:template match="map:XMLParticleGroup[ancestor::map:OUTPUT]">
    <xsl:element name="row" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
      <xsl:attribute name="s">7</xsl:attribute>
      <xsl:attribute name="customFormat">1</xsl:attribute>
      <xsl:if test="map:Active = 0" >
        <xsl:attribute name="hidden">1</xsl:attribute>
      </xsl:if>
      <!-- active -->
      <xsl:element name="c"  namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
        <xsl:attribute name="t">inlineStr</xsl:attribute>
        <xsl:attribute name="s">7</xsl:attribute>
        <xsl:element name="is" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
          <xsl:element name="t" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
            <xsl:choose>
              <xsl:when test="map:Active = 1">
                <xsl:text>Yes</xsl:text>
              </xsl:when>
              <xsl:otherwise>
                <xsl:text>No</xsl:text>
              </xsl:otherwise>
            </xsl:choose>
          </xsl:element>
        </xsl:element>
      </xsl:element>
      <!-- node type -->
      <xsl:element name="c"  namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
        <xsl:attribute name="t">inlineStr</xsl:attribute>
        <xsl:attribute name="s">7</xsl:attribute>
        <xsl:element name="is" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
          <xsl:element name="t" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
            <xsl:text>complexType</xsl:text>
          </xsl:element>
        </xsl:element>
      </xsl:element>
      <!-- name -->
      <xsl:element name="c"  namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
        <xsl:attribute name="t">inlineStr</xsl:attribute>
        <xsl:attribute name="s">12</xsl:attribute>
        <xsl:element name="is" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
          <xsl:element name="t" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
            <xsl:attribute name="space" namespace="http://www.w3.org/XML/1998/namespace">preserve</xsl:attribute>
            <!-- indents -->
            <xsl:for-each select="ancestor::map:XMLElementGroup | ancestor::map:XMLParticleGroup">
              <xsl:choose>
                <xsl:when test="following-sibling::map:XMLElementGroup or following-sibling::map:XMLParticleGroup">
                  <xsl:text>| </xsl:text>
                </xsl:when>
                <xsl:otherwise>
                  <xsl:text>  </xsl:text>
                </xsl:otherwise>
              </xsl:choose>
            </xsl:for-each>
            <xsl:text>|-</xsl:text><xsl:value-of select="map:ParticleType" /><xsl:text>[</xsl:text><xsl:value-of select="map:Name"/><xsl:text>]</xsl:text>
          </xsl:element>
        </xsl:element>
      </xsl:element>
      <!-- description -->
      <xsl:element name="c" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
        <xsl:attribute name="t">inlineStr</xsl:attribute>
        <xsl:attribute name="s">7</xsl:attribute>
        <xsl:element name="is" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
          <xsl:element name="t" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
            <xsl:value-of select="map:Description" />
          </xsl:element>
        </xsl:element>
      </xsl:element>
      <xsl:if test="not(/map:Mapper/map:INPUT/map:XMLSyntax)" >
        <!-- linked segment/record -->
        <xsl:element name="c"  namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
          <xsl:attribute name="t">inlineStr</xsl:attribute>
          <xsl:attribute name="s">8</xsl:attribute>
          <xsl:element name="is" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
            <xsl:element name="t" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main" />
          </xsl:element>
        </xsl:element>
      </xsl:if>
      <!-- linked element/field -->
      <xsl:element name="c"  namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
        <xsl:attribute name="t">inlineStr</xsl:attribute>
        <xsl:attribute name="s">8</xsl:attribute>
        <xsl:element name="is" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
          <xsl:element name="t" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main" />
        </xsl:element>
      </xsl:element>
      <!-- standard rules -->
      <xsl:element name="c"  namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
        <xsl:attribute name="t">inlineStr</xsl:attribute>
        <xsl:attribute name="s">8</xsl:attribute>
        <xsl:element name="is" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
          <xsl:element name="t" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main" />
        </xsl:element>
      </xsl:element>
      <!-- extended rules -->
      <xsl:element name="c"  namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
        <xsl:attribute name="t">inlineStr</xsl:attribute>
        <xsl:attribute name="s">8</xsl:attribute>
        <xsl:element name="is" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
          <xsl:element name="t" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
            <xsl:if test="map:ExplicitRule/map:OnBegin[text()]">
              <xsl:text>On Begin: </xsl:text>
              <xsl:text disable-output-escaping="yes"><![CDATA[&#10;]]></xsl:text>
              <xsl:value-of select="map:ExplicitRule/map:OnBegin" />
              <xsl:text disable-output-escaping="yes"><![CDATA[&#10;]]></xsl:text>
            </xsl:if>
            <xsl:if test="map:ExplicitRule/map:OnEnd[text()]">
              <xsl:text>On End: </xsl:text>
              <xsl:text disable-output-escaping="yes"><![CDATA[&#10;]]></xsl:text>
              <xsl:value-of select="map:ExplicitRule/map:OnEnd" />
            </xsl:if>
          </xsl:element>
        </xsl:element>
      </xsl:element>
      <!-- notes -->
      <xsl:element name="c" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
        <xsl:attribute name="t">inlineStr</xsl:attribute>
        <xsl:attribute name="s">8</xsl:attribute>
        <xsl:element name="is" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
          <xsl:element name="t" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
            <xsl:value-of select="map:Note" />
          </xsl:element>
        </xsl:element>
      </xsl:element>
      <xsl:if test="ancestor::map:OUTPUT/map:XMLSyntax/map:EnableNamespaces/text() = 'yes' or ancestor::map:OUTPUT/map:XMLSyntax/map:EnableNamespaces/text() = 'yes'" >
        <!-- namespace -->
        <xsl:element name="c"  namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
          <xsl:attribute name="t">inlineStr</xsl:attribute>
          <xsl:attribute name="s">7</xsl:attribute>
          <xsl:element name="is" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
            <xsl:element name="t" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main" />
          </xsl:element>
        </xsl:element>
        <xsl:element name="c"  namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
          <xsl:attribute name="t">inlineStr</xsl:attribute>
          <xsl:attribute name="s">7</xsl:attribute>
          <xsl:element name="is" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
            <xsl:element name="t" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main" />
          </xsl:element>
        </xsl:element>
      </xsl:if>
      <xsl:if test="$withoutFieldDefs!='yes'">
      <!-- data type -->
      <xsl:element name="c"  namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
        <xsl:attribute name="t">inlineStr</xsl:attribute>
        <xsl:attribute name="s">7</xsl:attribute>
        <xsl:element name="is" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
          <xsl:element name="t" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main" />
        </xsl:element>
      </xsl:element>
      <!-- format -->
      <xsl:element name="c"  namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
        <xsl:attribute name="t">inlineStr</xsl:attribute>
        <xsl:attribute name="s">7</xsl:attribute>
        <xsl:element name="is" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
          <xsl:element name="t" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main" />
        </xsl:element>
      </xsl:element>
      <!-- C/M -->
      <xsl:element name="c" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
        <xsl:attribute name="t">inlineStr</xsl:attribute>
        <xsl:attribute name="s">7</xsl:attribute>
        <xsl:element name="is" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
          <xsl:element name="t" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main" >
            <xsl:choose>
              <xsl:when test="map:Min > 0">
                <xsl:text>M</xsl:text>
              </xsl:when>
              <xsl:otherwise>
                <xsl:text>C</xsl:text>
              </xsl:otherwise>
            </xsl:choose>
          </xsl:element>
        </xsl:element>
      </xsl:element>
      <!-- min -->
      <xsl:element name="c"  namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
        <xsl:attribute name="t">inlineStr</xsl:attribute>
        <xsl:attribute name="s">7</xsl:attribute>
        <xsl:element name="is" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
          <xsl:element name="t" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
            <xsl:value-of select="map:Min" />
          </xsl:element>
        </xsl:element>
      </xsl:element>
      <!-- max -->
      <xsl:element name="c"  namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
        <xsl:attribute name="t">inlineStr</xsl:attribute>
        <xsl:attribute name="s">7</xsl:attribute>
        <xsl:element name="is" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
          <xsl:element name="t" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
            <xsl:choose>
              <xsl:when test="map:Max='-1'">
                <xsl:text>Unbounded</xsl:text>
              </xsl:when>
              <xsl:otherwise>
                <xsl:value-of select="map:Max" />
              </xsl:otherwise>
            </xsl:choose>
          </xsl:element>
        </xsl:element>
      </xsl:element>
      <!-- min length -->
      <xsl:element name="c"  namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
        <xsl:attribute name="t">inlineStr</xsl:attribute>
        <xsl:attribute name="s">7</xsl:attribute>
        <xsl:element name="is" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
          <xsl:element name="t" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main" />
        </xsl:element>
      </xsl:element>
      <!-- max length -->
      <xsl:element name="c"  namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
        <xsl:attribute name="t">inlineStr</xsl:attribute>
        <xsl:attribute name="s">7</xsl:attribute>
        <xsl:element name="is" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
          <xsl:element name="t" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main" />
        </xsl:element>
      </xsl:element>
      </xsl:if>
    </xsl:element>
    <xsl:apply-templates />
  </xsl:template>
  <!-- XML Particle -->
  
  <!-- Field -->
  <xsl:template match="map:Field[ancestor::map:OUTPUT and ( ancestor::map:Segment | ancestor::map:PosRecord | ancestor::map:VarDelimRecord | ancestor::map:ODBCOutputRecord ) ]">
    <xsl:param name="linkedFieldID" select="map:Link"/>
    <xsl:if test="$withInactiveFields='yes' or map:Active='1'">
    <xsl:element name="row" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
      <xsl:attribute name="s">13</xsl:attribute>
      <xsl:attribute name="customFormat">1</xsl:attribute>
      <xsl:if test="map:Active = 0" >
        <xsl:attribute name="hidden">1</xsl:attribute>
      </xsl:if>
      <!-- active -->
      <xsl:element name="c"  namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
        <xsl:attribute name="t">inlineStr</xsl:attribute>
        <xsl:attribute name="s">1</xsl:attribute>
        <xsl:element name="is" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
          <xsl:element name="t" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
            <xsl:choose>
              <xsl:when test="map:Active = 1">
                <xsl:text>Yes</xsl:text>
              </xsl:when>
              <xsl:otherwise>
                <xsl:text>No</xsl:text>
              </xsl:otherwise>
            </xsl:choose>
          </xsl:element>
        </xsl:element>
      </xsl:element>
      <!-- level -->
      <xsl:element name="c"  namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
        <xsl:attribute name="t">inlineStr</xsl:attribute>
        <xsl:attribute name="s">1</xsl:attribute>
        <xsl:element name="is" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
          <xsl:element name="t" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
            <xsl:value-of select="count(ancestor::map:Group | ancestor::map:Segment)" />
          </xsl:element>
        </xsl:element>
      </xsl:element>
      <!-- name -->
      <xsl:element name="c"  namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
        <xsl:attribute name="t">inlineStr</xsl:attribute>
        <xsl:attribute name="s">13</xsl:attribute>
        <xsl:element name="is" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
          <xsl:element name="t" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
            <xsl:attribute name="space" namespace="http://www.w3.org/XML/1998/namespace">preserve</xsl:attribute>
            <!--
            <xsl:for-each select="ancestor::map:Group | ancestor::map:Segment | ancestor::map:PosRecord | map:VarDelimRecord">
              <xsl:choose>
                <xsl:when test="following-sibling::map:Group or following-sibling::map:Segment or following-sibling::map:PosRecord or following-sibling::map:VarDelimRecord">
                  <xsl:text>| </xsl:text>
                </xsl:when>
                <xsl:otherwise>
                  <xsl:text>  </xsl:text>
                </xsl:otherwise>
              </xsl:choose>
            </xsl:for-each>
            <xsl:text>|-</xsl:text>
            -->
            <xsl:apply-templates select="." mode="buildName"/>
          </xsl:element>
        </xsl:element>
      </xsl:element>
      <!-- description -->
      <xsl:element name="c"  namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
        <xsl:attribute name="t">inlineStr</xsl:attribute>
        <xsl:attribute name="s">1</xsl:attribute>
        <xsl:element name="is" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
          <xsl:element name="t" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
            <xsl:value-of select="map:Description" />
          </xsl:element>
        </xsl:element>
      </xsl:element>
      <!-- linked segment or record -->
      <xsl:if test="not(/map:Mapper/map:INPUT/map:XMLSyntax)">
        <xsl:element name="c"  namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
          <xsl:attribute name="t">inlineStr</xsl:attribute>
          <xsl:attribute name="s">2</xsl:attribute>
          <xsl:element name="is" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
            <xsl:element name="t" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
              <xsl:apply-templates select="key('Fields', map:Link)" mode="buildRecord"/>
            </xsl:element>
          </xsl:element>
        </xsl:element>
      </xsl:if>
      <!-- linked element or field -->
      <xsl:element name="c"  namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
        <xsl:attribute name="t">inlineStr</xsl:attribute>
        <xsl:attribute name="s">2</xsl:attribute>
        <xsl:element name="is" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
          <xsl:element name="t" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
            <xsl:apply-templates select="key('Fields', map:Link)" mode="buildName"/>
            <xsl:apply-templates select="key('InputXMLRecords', map:Link)/map:Field" mode="buildName"/>
          </xsl:element>
        </xsl:element>
      </xsl:element>
      <!-- standard rule -->
      <xsl:element name="c"  namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
        <xsl:attribute name="t">inlineStr</xsl:attribute>
        <xsl:attribute name="s">2</xsl:attribute>
        <xsl:element name="is" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
          <xsl:element name="t" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main"><xsl:apply-templates /></xsl:element>
        </xsl:element>
      </xsl:element>
      <!-- extended rule -->
      <xsl:element name="c"  namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
        <xsl:attribute name="t">inlineStr</xsl:attribute>
        <xsl:attribute name="s">2</xsl:attribute>
        <xsl:element name="is" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
          <xsl:element name="t" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main"><xsl:value-of select="map:ExplicitRule" /></xsl:element>
        </xsl:element>
      </xsl:element>
      <!-- notes -->
      <xsl:element name="c"  namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
        <xsl:attribute name="t">inlineStr</xsl:attribute>
        <xsl:attribute name="s">2</xsl:attribute>
        <xsl:element name="is" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
          <xsl:element name="t" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
            <xsl:value-of select="map:Note" />
          </xsl:element>
        </xsl:element>
      </xsl:element>
      <xsl:if test="$withoutFieldDefs!='yes'">
      <!-- datatype -->
      <xsl:element name="c"  namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
        <xsl:attribute name="t">inlineStr</xsl:attribute>
        <xsl:attribute name="s">1</xsl:attribute>
        <xsl:element name="is" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
          <xsl:element name="t" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
            <xsl:value-of select="map:StoreLimit/map:DataType" />
          </xsl:element>
        </xsl:element>
      </xsl:element>
      <!-- format -->
      <xsl:element name="c"  namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
        <xsl:attribute name="t">inlineStr</xsl:attribute>
        <xsl:attribute name="s">1</xsl:attribute>
        <xsl:element name="is" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
          <xsl:element name="t" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
            <xsl:choose>
              <xsl:when test="map:StoreLimit/map:Format/text()">
                <xsl:value-of select="map:StoreLimit/map:Format" />
              </xsl:when>
              <xsl:otherwise>
                <xsl:text>Free-Format</xsl:text>
              </xsl:otherwise>
            </xsl:choose>
          </xsl:element>
        </xsl:element>
      </xsl:element>
      <!-- conditional status -->
      <xsl:element name="c"  namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
        <xsl:attribute name="t">inlineStr</xsl:attribute>
        <xsl:attribute name="s">1</xsl:attribute>
        <xsl:element name="is" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
          <xsl:element name="t" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
            <xsl:choose>
              <xsl:when test="map:Mandatory = 'yes'">
                <xsl:text>M</xsl:text>
              </xsl:when>
              <xsl:otherwise>
                <xsl:text>C</xsl:text>
              </xsl:otherwise>
            </xsl:choose>
          </xsl:element>
        </xsl:element>
      </xsl:element>
      <!-- min length or start position -->
      <xsl:element name="c"  namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
        <xsl:attribute name="t">inlineStr</xsl:attribute>
        <xsl:attribute name="s">1</xsl:attribute>
        <xsl:element name="is" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
          <xsl:element name="t" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
            <xsl:choose>
              <xsl:when test="ancestor::map:PosSyntax">
                <xsl:value-of select="map:StartPos + 1"/>
              </xsl:when>
              <xsl:when test="ancestor::map:EDISyntax | ancestor::map:VarDelimSyntax | ancestor::map:ODBCOutputRecord">
                <xsl:value-of select="map:StoreLimit/map:MinLen"/>
              </xsl:when>
            </xsl:choose>
          </xsl:element>
        </xsl:element>
      </xsl:element>
      <!-- max length or length -->
      <xsl:element name="c"  namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
        <xsl:attribute name="t">inlineStr</xsl:attribute>
        <xsl:attribute name="s">1</xsl:attribute>
        <xsl:element name="is" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
          <xsl:element name="t" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
            <xsl:choose>
              <xsl:when test="ancestor::map:PosSyntax">
                <xsl:value-of select="map:Length"/>
              </xsl:when>
              <xsl:when test="ancestor::map:EDISyntax | ancestor::map:VarDelimSyntax | ancestor::map:ODBCOutputRecord">
                <xsl:value-of select="map:StoreLimit/map:MaxLen"/>
              </xsl:when>
            </xsl:choose>
          </xsl:element>
        </xsl:element>
      </xsl:element>
      <xsl:if test="/map:Mapper/map:OUTPUT/map:PosSyntax">
      <!-- justification -->
      <xsl:element name="c"  namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
        <xsl:attribute name="t">inlineStr</xsl:attribute>
        <xsl:attribute name="s">1</xsl:attribute>
        <xsl:element name="is" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
          <xsl:element name="t" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
            <xsl:value-of select="map:Justify" />
          </xsl:element>
        </xsl:element>
      </xsl:element>
      <!-- padding character -->
      <xsl:element name="c"  namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
        <xsl:attribute name="t">inlineStr</xsl:attribute>
        <xsl:attribute name="s">1</xsl:attribute>
        <xsl:element name="is" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
          <xsl:element name="t" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
            <xsl:value-of select="map:PadChar" />
          </xsl:element>
        </xsl:element>
      </xsl:element>
      </xsl:if>
      <!-- conditions -->
      <xsl:element name="c"  namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
        <xsl:attribute name="t">inlineStr</xsl:attribute>
        <xsl:attribute name="s">2</xsl:attribute>
        <xsl:element name="is" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
          <xsl:element name="t" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
            <xsl:if test="map:ConditionalRuleDef">
              <xsl:value-of select="map:ConditionalRuleDef/map:RelationCode" /><xsl:text>(</xsl:text>
              <xsl:choose>
                <xsl:when test="map:ConditionalRuleDef/map:RelationCode='paired/multiple'">
                  <xsl:text>If any of the below fields are present, all fields must be present</xsl:text>
                </xsl:when>
                <xsl:when test="map:ConditionalRuleDef/map:RelationCode='required'">
                  <xsl:text>At least one of the below fields must be present</xsl:text>
                </xsl:when>
                <xsl:when test="map:ConditionalRuleDef/map:RelationCode='exclusion'">
                  <xsl:text>No more than one of the below fields can be present</xsl:text>
                </xsl:when>
                <xsl:when test="map:ConditionalRuleDef/map:RelationCode='conditional'">
                  <xsl:text> If the first condition field is present, the rest of the fields must also be present</xsl:text>
                </xsl:when>
                <xsl:when test="map:ConditionalRuleDef/map:RelationCode='listconditional'">
                  <xsl:text> If the first condition field is present, at least one of the specified fields must also be present</xsl:text>
                </xsl:when>
              </xsl:choose>
              <xsl:text>)</xsl:text>
              <xsl:text disable-output-escaping="yes"><![CDATA[&#10;]]></xsl:text>
              <xsl:for-each select="map:ConditionalRuleDef/map:SubjectElement/map:FieldID">
                <xsl:apply-templates select="key('Fields', current())" mode="buildName"/>
              </xsl:for-each>
            </xsl:if>
          </xsl:element>
        </xsl:element>
      </xsl:element>
      </xsl:if>
    </xsl:element>
    </xsl:if>
  </xsl:template>
  <!-- Field -->
  
  <!-- XML Element -->
  <xsl:template match="map:XMLElementGroup[ancestor::map:OUTPUT]">
    <xsl:param name="linkedFieldID">
      <xsl:if test="map:XMLRecord[map:RecordType='pcdata']">
        <xsl:value-of select="map:XMLRecord[map:RecordType='pcdata']/map:Field/map:Link"/>
      </xsl:if>
    </xsl:param>
    <xsl:param name="styleID">
      <xsl:choose>
        <xsl:when test="map:XMLRecord/map:RecordType='pcdata'">
          <xsl:text>1</xsl:text>
        </xsl:when>
        <xsl:otherwise>
          <xsl:text>5</xsl:text>
        </xsl:otherwise>
      </xsl:choose>
    </xsl:param>
    <xsl:element name="row" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
      <xsl:attribute name="s"><xsl:value-of select="$styleID"/></xsl:attribute>
      <xsl:attribute name="customFormat">1</xsl:attribute>
      <xsl:if test="map:Active = 0" >
        <xsl:attribute name="hidden">1</xsl:attribute>
      </xsl:if>
      <!-- active -->
      <xsl:element name="c"  namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
        <xsl:attribute name="t">inlineStr</xsl:attribute>
        <xsl:attribute name="s"><xsl:value-of select="$styleID"/></xsl:attribute>
        <xsl:element name="is" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
          <xsl:element name="t" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
            <xsl:choose>
              <xsl:when test="map:Active = 1">
                <xsl:text>Yes</xsl:text>
              </xsl:when>
              <xsl:otherwise>
                <xsl:text>No</xsl:text>
              </xsl:otherwise>
            </xsl:choose>
          </xsl:element>
        </xsl:element>
      </xsl:element>
      <!-- element type -->
      <xsl:element name="c"  namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
        <xsl:attribute name="t">inlineStr</xsl:attribute>
        <xsl:attribute name="s"><xsl:value-of select="$styleID"/></xsl:attribute>
        <xsl:element name="is" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
          <xsl:element name="t" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
            <xsl:text>element</xsl:text>
          </xsl:element>
        </xsl:element>
      </xsl:element>
      <!-- name -->
      <xsl:element name="c"  namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
        <xsl:attribute name="t">inlineStr</xsl:attribute>
        <xsl:attribute name="s">
          <xsl:choose>
            <xsl:when test="$styleID='1'">
              <xsl:text>13</xsl:text>
            </xsl:when>
            <xsl:otherwise>
              <xsl:text>14</xsl:text>
            </xsl:otherwise>
          </xsl:choose>
        </xsl:attribute>
        <xsl:element name="is" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
          <xsl:element name="t" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
            <xsl:attribute name="space" namespace="http://www.w3.org/XML/1998/namespace">preserve</xsl:attribute>
            <xsl:for-each select="ancestor::map:XMLElementGroup | ancestor::map:XMLParticleGroup">
              <xsl:choose>
                <xsl:when test="following-sibling::map:XMLElementGroup or following-sibling::map:XMLParticleGroup">
                  <xsl:text>| </xsl:text>
                </xsl:when>
                <xsl:otherwise>
                  <xsl:text>  </xsl:text>
                </xsl:otherwise>
              </xsl:choose>
            </xsl:for-each>
            <xsl:text>|-</xsl:text>
            <xsl:value-of select="map:XMLTag"/>
          </xsl:element>
        </xsl:element>
      </xsl:element>
      <!-- description -->
      <xsl:element name="c"  namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
        <xsl:attribute name="t">inlineStr</xsl:attribute>
        <xsl:attribute name="s"><xsl:value-of select="$styleID"/></xsl:attribute>
        <xsl:element name="is" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
          <xsl:element name="t" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
            <xsl:value-of select="map:Description"/>
          </xsl:element>
        </xsl:element>
      </xsl:element>
      <xsl:if test="not(/map:Mapper/map:INPUT/map:XMLSyntax)">
        <!-- linked segment or record -->
        <xsl:element name="c"  namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
          <xsl:attribute name="t">inlineStr</xsl:attribute>
          <xsl:attribute name="s"><xsl:value-of select="$styleID + 1"/></xsl:attribute>
          <xsl:element name="is" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
            <xsl:element name="t" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
              <xsl:apply-templates select="key('Fields', map:XMLRecord[map:RecordType='pcdata']/map:Field/map:Link)" mode="buildRecord"/>
            </xsl:element>
          </xsl:element>
        </xsl:element>
      </xsl:if>
      <!-- linked element or field -->
      <xsl:element name="c"  namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
        <xsl:attribute name="t">inlineStr</xsl:attribute>
        <xsl:attribute name="s"><xsl:value-of select="$styleID + 1"/></xsl:attribute>
        <xsl:element name="is" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
          <xsl:element name="t" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
            <xsl:apply-templates select="key('Fields', map:XMLRecord[map:RecordType='pcdata']/map:Field/map:Link)" mode="buildName"/>
            <xsl:apply-templates select="key('InputXMLRecords', map:XMLRecord[map:RecordType='pcdata']/map:Field/map:Link)/map:Field" mode="buildName"/>
          </xsl:element>
        </xsl:element>
      </xsl:element>
      <!-- standard rules -->
      <xsl:element name="c"  namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
        <xsl:attribute name="t">inlineStr</xsl:attribute>
        <xsl:attribute name="s"><xsl:value-of select="$styleID + 1"/></xsl:attribute>
        <xsl:element name="is" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
          <xsl:element name="t" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
            <xsl:apply-templates select="map:XMLRecord[map:RecordType='pcdata']/map:Field"/>
          </xsl:element>
        </xsl:element>
      </xsl:element>
      <!-- extended rules -->
      <xsl:element name="c"  namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
        <xsl:attribute name="t">inlineStr</xsl:attribute>
        <xsl:attribute name="s"><xsl:value-of select="$styleID + 1"/></xsl:attribute>
        <xsl:element name="is" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
          <xsl:element name="t" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
            <xsl:if test="map:ExplicitRule/map:OnBegin[text()]">
              <xsl:text>On Begin: </xsl:text>
              <xsl:text disable-output-escaping="yes"><![CDATA[&#10;]]></xsl:text>
              <xsl:value-of select="map:ExplicitRule/map:OnBegin" />
              <xsl:text disable-output-escaping="yes"><![CDATA[&#10;]]></xsl:text>
            </xsl:if>
            <xsl:if test="map:ExplicitRule/map:OnEnd[text()]">
              <xsl:text>On Begin: </xsl:text>
              <xsl:text disable-output-escaping="yes"><![CDATA[&#10;]]></xsl:text>
              <xsl:value-of select="map:ExplicitRule/map:OnEnd" />
              <xsl:text disable-output-escaping="yes"><![CDATA[&#10;]]></xsl:text>
            </xsl:if>
            <xsl:if test="map:XMLRecord[map:RecordType='pcdata']/map:Field/map:ExplicitRule">
              <xsl:value-of select="map:XMLRecord[map:RecordType='pcdata']/map:Field/map:ExplicitRule" />
            </xsl:if>
          </xsl:element>
        </xsl:element>
      </xsl:element>
      <!-- notes -->
      <xsl:element name="c"  namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
        <xsl:attribute name="t">inlineStr</xsl:attribute>
        <xsl:attribute name="s"><xsl:value-of select="$styleID + 1"/></xsl:attribute>
        <xsl:element name="is" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
          <xsl:element name="t" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
            <xsl:value-of select="map:Note"/>
          </xsl:element>
        </xsl:element>
      </xsl:element>

      <xsl:if test="/map:Mapper/map:OUTPUT/map:XMLSyntax/map:EnableNamespaces/text() = 'yes'">
        <!-- namespace -->
        <xsl:element name="c"  namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
          <xsl:attribute name="t">inlineStr</xsl:attribute>
        <xsl:attribute name="s"><xsl:value-of select="$styleID"/></xsl:attribute>
          <xsl:element name="is" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
            <xsl:element name="t" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
              <xsl:value-of select="map:Namespace"/>
            </xsl:element>
          </xsl:element>
        </xsl:element>
        <xsl:element name="c"  namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
          <xsl:attribute name="t">inlineStr</xsl:attribute>
        <xsl:attribute name="s"><xsl:value-of select="$styleID"/></xsl:attribute>
          <xsl:element name="is" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
            <xsl:element name="t" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
              <xsl:value-of select="map:UseParentNamespace"/>
            </xsl:element>
          </xsl:element>
        </xsl:element>
      </xsl:if>
      <xsl:if test="$withoutFieldDefs!='yes'">
      <!-- datatype -->
      <xsl:element name="c"  namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
        <xsl:attribute name="t">inlineStr</xsl:attribute>
        <xsl:attribute name="s"><xsl:value-of select="$styleID"/></xsl:attribute>
        <xsl:element name="is" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
          <xsl:element name="t" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
            <xsl:value-of select="map:XMLRecord[map:RecordType='pcdata']/map:Field/map:StoreLimit/map:DataType"/>
          </xsl:element>
        </xsl:element>
      </xsl:element>
      <!-- format -->
      <xsl:element name="c"  namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
        <xsl:attribute name="t">inlineStr</xsl:attribute>
        <xsl:attribute name="s"><xsl:value-of select="$styleID"/></xsl:attribute>
        <xsl:element name="is" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
          <xsl:element name="t" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
            <xsl:if test="map:XMLRecord/map:RecordType='pcdata'">
            <xsl:choose>
              <xsl:when test="map:XMLRecord[map:RecordType='pcdata']/map:Field/map:StoreLimit/map:Format/text()">
                <xsl:value-of select="map:XMLRecord[map:RecordType='pcdata']/map:Field/map:StoreLimit/map:Format/text()" />
              </xsl:when>
              <xsl:otherwise>
                <xsl:text>Free-Format</xsl:text>
              </xsl:otherwise>
            </xsl:choose>
            </xsl:if>
          </xsl:element>
        </xsl:element>
      </xsl:element>
      <!-- C/M -->
      <xsl:element name="c"  namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
        <xsl:attribute name="t">inlineStr</xsl:attribute>
        <xsl:attribute name="s"><xsl:value-of select="$styleID"/></xsl:attribute>
        <xsl:element name="is" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
          <xsl:element name="t" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
            <xsl:choose>
              <xsl:when test="map:Min > 0">
                <xsl:text>M</xsl:text>
              </xsl:when>
              <xsl:otherwise>
                <xsl:text>C</xsl:text>
              </xsl:otherwise>
            </xsl:choose>
            <xsl:text>/</xsl:text>
            <xsl:choose>
              <xsl:when test="map:XMLRecord[map:RecordType='pcdata']/map:Field/map:Mandatory='yes'">
                <xsl:text>M</xsl:text>
              </xsl:when>
              <xsl:otherwise>
                <xsl:text>C</xsl:text>
              </xsl:otherwise>
            </xsl:choose>
          </xsl:element>
        </xsl:element>
      </xsl:element>
      <!-- min -->
      <xsl:element name="c"  namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
        <xsl:attribute name="t">inlineStr</xsl:attribute>
        <xsl:attribute name="s"><xsl:value-of select="$styleID"/></xsl:attribute>
        <xsl:element name="is" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
          <xsl:element name="t" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
            <xsl:value-of select="map:Min"/>
          </xsl:element>
        </xsl:element>
      </xsl:element>
      <!-- max -->
      <xsl:element name="c"  namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
        <xsl:attribute name="t">inlineStr</xsl:attribute>
        <xsl:attribute name="s"><xsl:value-of select="$styleID"/></xsl:attribute>
        <xsl:element name="is" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
          <xsl:element name="t" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
            <xsl:choose>
              <xsl:when test="map:Max='-1'">
                <xsl:text>Unbounded</xsl:text>
              </xsl:when>
              <xsl:otherwise>
                <xsl:value-of select="map:Max" />
              </xsl:otherwise>
            </xsl:choose>
          </xsl:element>
        </xsl:element>
      </xsl:element>
      <!-- min length -->
      <xsl:element name="c"  namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
        <xsl:attribute name="t">inlineStr</xsl:attribute>
        <xsl:attribute name="s"><xsl:value-of select="$styleID"/></xsl:attribute>
        <xsl:element name="is" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
          <xsl:element name="t" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
            <xsl:value-of select="map:XMLRecord[map:RecordType='pcdata']/map:Field/map:StoreLimit/map:MinLen"/>
          </xsl:element>
        </xsl:element>
      </xsl:element>
      <!-- max length -->
      <xsl:element name="c"  namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
        <xsl:attribute name="t">inlineStr</xsl:attribute>
        <xsl:attribute name="s"><xsl:value-of select="$styleID"/></xsl:attribute>
        <xsl:element name="is" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
          <xsl:element name="t" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
            <xsl:value-of select="map:XMLRecord[map:RecordType='pcdata']/map:Field/map:StoreLimit/map:MaxLen"/>
          </xsl:element>
        </xsl:element>
      </xsl:element>
      </xsl:if>
    </xsl:element>
    <xsl:apply-templates/>
  </xsl:template>
  <!-- XML Element -->
  
  <!-- XML Attribute -->
  <xsl:template match="map:XMLRecord[ancestor::map:OUTPUT and map:RecordType='attribute']/map:Field">
    <xsl:param name="linkedFieldID" select="map:Link"/>
    <xsl:element name="row" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
      <xsl:attribute name="s">1</xsl:attribute>
      <xsl:attribute name="customFormat">1</xsl:attribute>
      <xsl:if test="map:Active = 0" >
        <xsl:attribute name="hidden">1</xsl:attribute>
      </xsl:if>
      <!-- active -->
      <xsl:element name="c"  namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
        <xsl:attribute name="t">inlineStr</xsl:attribute>
        <xsl:attribute name="s">1</xsl:attribute>
        <xsl:element name="is" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
          <xsl:element name="t" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
            <xsl:choose>
              <xsl:when test="map:Active = 1">
                <xsl:text>Yes</xsl:text>
              </xsl:when>
              <xsl:otherwise>
                <xsl:text>No</xsl:text>
              </xsl:otherwise>
            </xsl:choose>
          </xsl:element>
        </xsl:element>
      </xsl:element>
      <!-- node type -->
      <xsl:element name="c"  namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
        <xsl:attribute name="t">inlineStr</xsl:attribute>
        <xsl:attribute name="s">1</xsl:attribute>
        <xsl:element name="is" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
          <xsl:element name="t" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
            <xsl:text>attribute</xsl:text>
          </xsl:element>
        </xsl:element>
      </xsl:element>
      <!-- name -->
      <xsl:element name="c"  namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
        <xsl:attribute name="t">inlineStr</xsl:attribute>
        <xsl:attribute name="s">13</xsl:attribute>
        <xsl:element name="is" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
          <xsl:element name="t" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
            <xsl:attribute name="space" namespace="http://www.w3.org/XML/1998/namespace">preserve</xsl:attribute>
            <xsl:for-each select="ancestor::map:XMLElementGroup | ancestor::map:XMLParticleGroup">
              <xsl:choose>
                <xsl:when test="following-sibling::map:XMLElementGroup or following-sibling::map:XMLParticleGroup">
                  <xsl:text>| </xsl:text>
                </xsl:when>
                <xsl:otherwise>
                  <xsl:text>  </xsl:text>
                </xsl:otherwise>
              </xsl:choose>
            </xsl:for-each>
            <xsl:text>|-@</xsl:text>
            <xsl:value-of select="map:Tag" />
          </xsl:element>
        </xsl:element>
      </xsl:element>
      <!-- description -->
      <xsl:element name="c"  namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
        <xsl:attribute name="t">inlineStr</xsl:attribute>
        <xsl:attribute name="s">1</xsl:attribute>
        <xsl:element name="is" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
          <xsl:element name="t" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
            <xsl:value-of select="map:Description" />
          </xsl:element>
        </xsl:element>
      </xsl:element>
      <xsl:if test="not(/map:Mapper/map:INPUT/map:XMLSyntax)">
        <!-- linked segment or record -->
        <xsl:element name="c"  namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
          <xsl:attribute name="t">inlineStr</xsl:attribute>
          <xsl:attribute name="s">2</xsl:attribute>
          <xsl:element name="is" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
            <xsl:element name="t" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
              <xsl:apply-templates select="key('Fields', map:Link)" mode="buildRecord"/>
            </xsl:element>
          </xsl:element>
        </xsl:element>
      </xsl:if>
      <!-- linked element or field -->
      <xsl:element name="c"  namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
        <xsl:attribute name="t">inlineStr</xsl:attribute>
        <xsl:attribute name="s">2</xsl:attribute>
        <xsl:element name="is" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
          <xsl:element name="t" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
            <xsl:apply-templates select="key('Fields', map:Link)" mode="buildName"/>
            <xsl:apply-templates select="key('InputXMLRecords', map:Link)/map:Field" mode="buildName"/>
          </xsl:element>
        </xsl:element>
      </xsl:element>
      <!-- standard rules -->
      <xsl:element name="c"  namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
        <xsl:attribute name="t">inlineStr</xsl:attribute>
        <xsl:attribute name="s"><xsl:value-of select="2"/></xsl:attribute>
        <xsl:element name="is" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
          <xsl:element name="t" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
            <xsl:apply-templates/>
          </xsl:element>
        </xsl:element>
      </xsl:element>
      <!-- extended rules -->
      <xsl:element name="c"  namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
        <xsl:attribute name="t">inlineStr</xsl:attribute>
        <xsl:attribute name="s">2</xsl:attribute>
        <xsl:element name="is" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
          <xsl:element name="t" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
            <xsl:value-of select="map:ExplicitRule"/>
          </xsl:element>
        </xsl:element>
      </xsl:element>
      <!-- Notes -->
      <xsl:element name="c"  namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
        <xsl:attribute name="t">inlineStr</xsl:attribute>
        <xsl:attribute name="s">2</xsl:attribute>
        <xsl:element name="is" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
          <xsl:element name="t" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
            <xsl:value-of select="map:Note"/>
          </xsl:element>
        </xsl:element>
      </xsl:element>
      <xsl:if test="/map:Mapper/map:OUTPUT/map:XMLSyntax/map:EnableNamespaces/text() = 'yes'" >
        <!-- namespace -->
        <xsl:element name="c"  namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
          <xsl:attribute name="t">inlineStr</xsl:attribute>
          <xsl:attribute name="s">1</xsl:attribute>
          <xsl:element name="is" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
            <xsl:element name="t" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
              <xsl:value-of select="../../map:Namespace"/>
            </xsl:element>
          </xsl:element>
        </xsl:element>
        <xsl:element name="c"  namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
          <xsl:attribute name="t">inlineStr</xsl:attribute>
          <xsl:attribute name="s">1</xsl:attribute>
          <xsl:element name="is" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
            <xsl:element name="t" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
              <xsl:value-of select="map:UseParentNamespace"/>
            </xsl:element>
          </xsl:element>
        </xsl:element>
      </xsl:if>
      <xsl:if test="$withoutFieldDefs!='yes'">
      <!-- datatype -->
      <xsl:element name="c"  namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
        <xsl:attribute name="t">inlineStr</xsl:attribute>
        <xsl:attribute name="s">1</xsl:attribute>
        <xsl:element name="is" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
          <xsl:element name="t" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
            <xsl:value-of select="map:StoreLimit/map:DataType"/>
          </xsl:element>
        </xsl:element>
      </xsl:element>
      <!-- format -->
      <xsl:element name="c"  namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
        <xsl:attribute name="t">inlineStr</xsl:attribute>
        <xsl:attribute name="s">1</xsl:attribute>
        <xsl:element name="is" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
          <xsl:element name="t" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
            <xsl:value-of select="map:StoreLimit/map:Format"/>
          </xsl:element>
        </xsl:element>
      </xsl:element>
      <!-- C/M -->
      <xsl:element name="c"  namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
        <xsl:attribute name="t">inlineStr</xsl:attribute>
        <xsl:attribute name="s">1</xsl:attribute>
        <xsl:element name="is" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
          <xsl:element name="t" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
            <xsl:choose>
              <xsl:when test="map:Mandatory = 'yes'">
                <xsl:text>M</xsl:text>
              </xsl:when>
              <xsl:otherwise>
                <xsl:text>C</xsl:text>
              </xsl:otherwise>
            </xsl:choose>
          </xsl:element>
        </xsl:element>
      </xsl:element>
      <!-- min -->
      <xsl:element name="c"  namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
        <xsl:attribute name="t">inlineStr</xsl:attribute>
        <xsl:attribute name="s">1</xsl:attribute>
        <xsl:element name="is" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
          <xsl:element name="t" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main" >
            <xsl:value-of select="map:Min"/>
          </xsl:element>
        </xsl:element>
      </xsl:element>
      <!-- max -->
      <xsl:element name="c"  namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
        <xsl:attribute name="t">inlineStr</xsl:attribute>
        <xsl:attribute name="s">1</xsl:attribute>
        <xsl:element name="is" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
          <xsl:element name="t" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
            <xsl:choose>
              <xsl:when test="map:Max='-1'">
                <xsl:text>Unbounded</xsl:text>
              </xsl:when>
              <xsl:otherwise>
                <xsl:value-of select="map:Max" />
              </xsl:otherwise>
            </xsl:choose>
          </xsl:element>
        </xsl:element>
      </xsl:element>
      <!-- min length-->
      <xsl:element name="c"  namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
        <xsl:attribute name="t">inlineStr</xsl:attribute>
        <xsl:attribute name="s">1</xsl:attribute>
        <xsl:element name="is" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
          <xsl:element name="t" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
            <xsl:value-of select="map:StoreLimit/map:MinLen"/>
          </xsl:element>
        </xsl:element>
      </xsl:element>
      <!-- max length -->
      <xsl:element name="c"  namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
        <xsl:attribute name="t">inlineStr</xsl:attribute>
        <xsl:attribute name="s">1</xsl:attribute>
        <xsl:element name="is" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
          <xsl:element name="t" namespace="http://schemas.openxmlformats.org/spreadsheetml/2006/main">
            <xsl:value-of select="map:StoreLimit/map:MaxLen"/>
          </xsl:element>
        </xsl:element>
      </xsl:element>
      </xsl:if>
    </xsl:element>
  </xsl:template>
  <!-- XML Attribute -->

  <!-- buildRecord -->
  <xsl:template match="map:Field" mode="buildRecord">
    <xsl:choose>
      <xsl:when test="parent::map:Composite">
        <xsl:value-of select="ancestor::map:Segment[1]/map:Name"/>
      </xsl:when>
      <xsl:otherwise>
        <xsl:value-of select="parent::node()/map:Name" />
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
        <xsl:text disable-output-escaping="yes"><![CDATA[&#10;]]></xsl:text>
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
            <xsl:text disable-output-escaping="yes"><![CDATA[&#10;]]></xsl:text>
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
                    <xsl:text disable-output-escaping="yes"><![CDATA[&#10;]]></xsl:text>
                  </xsl:when>
                  <!-- if the input is EDI element (non-composite) without colon in name -->
                  <xsl:otherwise>
                    <xsl:value-of select="parent::node()/map:Name" />
                    <xsl:number level="single" count="map:Field | map:Composite" from="map:Segment" format="01"/>
                    <xsl:text> [</xsl:text>
                    <xsl:value-of select="map:Name"/>
                    <xsl:text>]</xsl:text>
                    <xsl:text disable-output-escaping="yes"><![CDATA[&#10;]]></xsl:text>
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
        <xsl:text disable-output-escaping="yes"><![CDATA[&#10;]]></xsl:text>
      </xsl:otherwise>
    </xsl:choose>
  </xsl:template>
  <!-- buildName -->
  
  <!-- standard rule templates -->
  <xsl:template match="map:UseConstant[ancestor::map:OUTPUT]">
    <xsl:param name="useConstantID" select="map:ConstantID"/>
    <xsl:text>Use Constant: </xsl:text>
    <xsl:text disable-output-escaping="yes"><![CDATA[&#10;]]></xsl:text>
    <xsl:value-of select="/map:Mapper/map:ConstantMap/map:Constant[position()=$useConstantID+1]/map:Value" />
  </xsl:template>

  <xsl:template match="map:UseUpdate[ancestor::map:OUTPUT]">
    <xsl:param name="useConstantID" select="map:SubTableNameConstantID"/>
    <xsl:text>Update: Table [</xsl:text>
    <xsl:value-of select="map:TableName"/>
    <xsl:text>]</xsl:text>
    <xsl:if test="map:SubTableNameConstantID[text()]">
      <xsl:text disable-output-escaping="yes"><![CDATA[&#10;]]></xsl:text>
      <xsl:text> Subtable [</xsl:text>
      <xsl:value-of select="/map:Mapper/map:ConstantMap/map:Constant[position()=($useConstantID + 1)]/map:Value" />
      <xsl:text>]</xsl:text>
    </xsl:if>
    <xsl:text disable-output-escaping="yes"><![CDATA[&#10;]]></xsl:text>
    <xsl:text> Map From [</xsl:text>
    <xsl:value-of select="map:MapFrom"/>
    <xsl:text>]</xsl:text>
  </xsl:template>

  <xsl:template match="map:UseSystemVariable[ancestor::map:OUTPUT]">
    <xsl:text>Use System Variable:</xsl:text>
    <xsl:text disable-output-escaping="yes"><![CDATA[&#10;]]></xsl:text>
    <xsl:text>Current Date and Time</xsl:text>
  </xsl:template>

  <xsl:template match="map:UseSelect[ancestor::map:OUTPUT]">
    <xsl:param name="useConstantID" select="map:SubTableNameConstantID"/>
    <xsl:param name="selectIntoFieldID" select="map:Mapping/map:ToFieldID"/>
    <xsl:value-of select="map:TableName"/>
    <xsl:text>: SELECT </xsl:text>
    <xsl:value-of select="map:Mapping/map:MapFrom"/>
    <xsl:text disable-output-escaping="yes"><![CDATA[&#10;]]></xsl:text>
    <xsl:text> INTO </xsl:text>
    <xsl:value-of select="//map:Field[map:ID=$selectIntoFieldID]/map:Name"/>
    <xsl:text disable-output-escaping="yes"><![CDATA[&#10;]]></xsl:text>
    <xsl:text> FROM CODELIST WHERE TABLE = </xsl:text>
    <xsl:value-of select="/map:Mapper/map:ConstantMap/map:Constant[position()=$useConstantID + 1]/map:Value" />
  </xsl:template>

  <xsl:template match="map:UseAccumulator[ancestor::map:OUTPUT]/map:Accumulator">
    <xsl:param name="useAccumulatorID" select="map:AccumulatorID"/>
    <xsl:text>Use Accumulator: </xsl:text>
    <xsl:text disable-output-escaping="yes"><![CDATA[&#10;]]></xsl:text>
    <xsl:value-of select="$useAccumulatorID" /> - <xsl:value-of select="/map:Mapper/map:Accumulators/map:Accumulator[map:AccumulatorID=$useAccumulatorID]/map:AccumulatorName"/>
    <xsl:text disable-output-escaping="yes"><![CDATA[&#10;]]></xsl:text>
    <xsl:for-each select="map:AccumulatorAction">
      <xsl:value-of select="."/>
      <!-- don't put a return on the last line -->
      <xsl:if test="not(position()=last())">
        <xsl:text disable-output-escaping="yes"><![CDATA[&#10;]]></xsl:text>
      </xsl:if>
    </xsl:for-each>
  </xsl:template>

  <xsl:template match="map:UseCode[ancestor::map:OUTPUT]">
    <xsl:param name="useCodeListTableID" select="map:TableID[ancestor::map:OUTPUT]"/>
    <xsl:text>Use Code: From </xsl:text>
    <xsl:value-of select="/map:Mapper/map:CodeListTables/map:Table[map:TableID=$useCodeListTableID]/map:TableDescription"/>
    <xsl:if test="map:DestinationFieldID">
      <xsl:text disable-output-escaping="yes"><![CDATA[&#10;]]></xsl:text>
      <xsl:text> place text into: </xsl:text>
      <xsl:value-of select="map:DestinationFieldID"/>
    </xsl:if>
    <xsl:if test="map:Member='yes'">
      <xsl:text disable-output-escaping="yes"><![CDATA[&#10;]]></xsl:text>
      <xsl:text> and raise compliance error if code not found in list</xsl:text>
    </xsl:if>
  </xsl:template>
  <!-- standard rule templates -->
  
  <xsl:template match="node() | @*">
    <xsl:apply-templates select="node() | @*" />
  </xsl:template>

  <xsl:template match="map:XMLElementGroup" mode="buildPath">
    <xsl:text>/</xsl:text>
    <xsl:value-of select="map:XMLTag"/>
  </xsl:template>

</xsl:stylesheet>
