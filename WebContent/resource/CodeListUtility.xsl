<?xml version="1.0" encoding="UTF-8"?>
<!--
Created by Bharath Kumar/Akhila Desai
banapart@in.ibm.com / akhiladesai@in.ibm.com
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
		 <!-- Code to Extract UseCode rule  -->
            <xsl:for-each select="/si:Mapper/si:CodeListTables/si:Table">
                <xsl:call-template name="Process">
                    <xsl:with-param name="TableID">
                        <xsl:value-of select="si:TableID"/>
                    </xsl:with-param>
                    <xsl:with-param name="TableDescription">
                        <xsl:value-of select="si:TableDescription"/>
                    </xsl:with-param>
                </xsl:call-template>
                <xsl:value-of select="$newline"/>
            </xsl:for-each>
			<!-- Code to Extract UseSelect rule  -->
            
		<xsl:for-each select="//si:Field[count(./si:ImplicitRuleDef/si:UseSelect) > 0]">
			 <xsl:variable name="VarFieldName" select="./si:Name"/>
			 
		    <xsl:element name="FieldEntry">
			   <xsl:for-each select="./si:ImplicitRuleDef/si:UseSelect"> 
			
			   <xsl:variable name="VarSubTableNameConstantID"  select="./si:SubTableNameConstantID"/>   
			     
			      <xsl:element name="FieldName">
			          <xsl:value-of select="$VarFieldName"/>
			      </xsl:element>
			       <xsl:element name="CodeList_Type">
			           <xsl:value-of select="'EXTERNAL CODELIST'"/>
			       </xsl:element>
			       <xsl:for-each select="/si:Mapper/si:ConstantMap/si:Constant">
			           <xsl:variable name="ConstantID_1" select="./si:ConstantID"></xsl:variable>
			           <xsl:variable name="ConstantID" select="substring-after($ConstantID_1,'Search Key ')"/> 	
			           <xsl:if test="$VarSubTableNameConstantID = $ConstantID + 1">
			               <xsl:variable name="CODELIST-NAME" select="./si:Value"></xsl:variable>
			               <xsl:element name="Codelist_Name">
			                   <xsl:value-of select="./si:Value"/>
			               </xsl:element> 
			           </xsl:if>
			       </xsl:for-each>
			      		      
			   </xsl:for-each>
		    
		    
            <xsl:for-each select="./si:ImplicitRuleDef/si:UseSelect/si:Mapping">
                <xsl:element name="Codelist_Values">
                    <xsl:value-of select="./si:MapFrom"/>
                </xsl:element>
           </xsl:for-each>
        </xsl:element>
			<xsl:value-of select="$newline"/>
		</xsl:for-each>
             
            <!-- Code to Extract ExtendRule from fields   -->
          
		     <xsl:for-each select="//si:Field[count(./si:ExplicitRule) > 0]">
		           <xsl:variable name="VarExplicitRule" select="./si:ExplicitRule"/>
		      
		        	    <xsl:variable name="smallcase" select="'abcdefghijklmnopqrstuvwxyz'" />
                   <xsl:variable name="uppercase" select="'ABCDEFGHIJKLMNOPQRSTUVWXYZ'" />
		         <xsl:variable name="VarExplicitRule_upper" select="translate($VarExplicitRule, $smallcase, $uppercase)" />
		         <xsl:if test="//si:Field[contains($VarExplicitRule_upper,'CODELIST')]"> 
            <xsl:call-template name="Process2">
                <xsl:with-param name="VarExplicitRule_upper" select="$VarExplicitRule_upper"/>
            </xsl:call-template>
		             </xsl:if>
			<xsl:value-of select="$newline"/>
		     </xsl:for-each>
            
            <!--Entend Rule for Group layout-->
            <xsl:for-each select="//si:Group[count(./si:ExplicitRule) > 0]">
                <xsl:variable name="VarExplicitRule" select="./si:ExplicitRule"/>
               <!--<xsl:element name="BHARA">bharata</xsl:element>-->
                <xsl:variable name="smallcase" select="'abcdefghijklmnopqrstuvwxyz'" />
                <xsl:variable name="uppercase" select="'ABCDEFGHIJKLMNOPQRSTUVWXYZ'" />
                <xsl:variable name="VarExplicitRule_upper" select="translate($VarExplicitRule, $smallcase, $uppercase)" />
                <xsl:if test="//si:Field[contains($VarExplicitRule_upper,'CODELIST')]"> 
                    <xsl:call-template name="Process2">
                        <xsl:with-param name="VarExplicitRule_upper" select="$VarExplicitRule_upper"/>
                    </xsl:call-template>
                </xsl:if>
            </xsl:for-each>
            
            <!-- Extend rule for delimeted layout -->
            <xsl:for-each select="//si:Segment[count(./si:ExplicitRule) > 0]">
                <xsl:variable name="VarExplicitRule" select="./si:ExplicitRule"/>
                <xsl:variable name="smallcase" select="'abcdefghijklmnopqrstuvwxyz'" />
                <xsl:variable name="uppercase" select="'ABCDEFGHIJKLMNOPQRSTUVWXYZ'" />
                <xsl:variable name="VarExplicitRule_upper" select="translate($VarExplicitRule, $smallcase, $uppercase)" />
                <xsl:if test="//si:Segment[contains($VarExplicitRule_upper,'CODELIST')]"> 
                    <xsl:call-template name="Process2">
                        <xsl:with-param name="VarExplicitRule_upper" select="$VarExplicitRule_upper"/>
                    </xsl:call-template>
                </xsl:if>
            </xsl:for-each>  
            <!-- Extend rule for Positonal layout -->
            <xsl:for-each select="//si:PosRecord[count(./si:ExplicitRule) > 0]">
                <xsl:variable name="VarExplicitRule" select="./si:ExplicitRule"/>
                <xsl:variable name="smallcase" select="'abcdefghijklmnopqrstuvwxyz'" />
                <xsl:variable name="uppercase" select="'ABCDEFGHIJKLMNOPQRSTUVWXYZ'" />
                <xsl:variable name="VarExplicitRule_upper" select="translate($VarExplicitRule, $smallcase, $uppercase)" />
                <xsl:if test="//si:PosRecord[contains($VarExplicitRule_upper,'CODELIST')]"> 
                    <xsl:call-template name="Process2">
                        <xsl:with-param name="VarExplicitRule_upper" select="$VarExplicitRule_upper"/>
                    </xsl:call-template>
                </xsl:if>
            </xsl:for-each> 
            
            <!-- Pre-ssession rules -->
            <xsl:for-each select="//si:MapDetails[count(./si:ExplicitRule) > 0]">
                <xsl:variable name="VarExplicitRule" select="./si:ExplicitRule"/>
                <xsl:variable name="smallcase" select="'abcdefghijklmnopqrstuvwxyz'" />
                <xsl:variable name="uppercase" select="'ABCDEFGHIJKLMNOPQRSTUVWXYZ'" />
                <xsl:variable name="VarExplicitRule_upper" select="translate($VarExplicitRule, $smallcase, $uppercase)" />
                <xsl:if test="//si:MapDetails[contains($VarExplicitRule_upper,'CODELIST')]"> 
                    <xsl:call-template name="Process2">
                        <xsl:with-param name="VarExplicitRule_upper" select="$VarExplicitRule_upper"/>
                    </xsl:call-template>
                </xsl:if>
            </xsl:for-each> 
                </xsl:element>
	</xsl:template>
    
<!-- display-->
    <xsl:template name="Process">
        <xsl:param name="TableID"/>
        <xsl:param name="TableDescription"/>
		<!-- Code to Extract UseCode rule  -->
        <xsl:for-each select="//si:Field[count(./si:ImplicitRuleDef/si:UseCode) > 0]">
            <xsl:variable name="VarFieldName" select="./si:Name"/>
      
			 

           <xsl:for-each select="./si:ImplicitRuleDef/si:UseCode"> 
                <xsl:call-template name="Process3">
                    <xsl:with-param name="TableID" select="$TableID"/>
                    <xsl:with-param name="TableDescription" select="$TableDescription"/>
                    <xsl:with-param name="FieldName" select="$VarFieldName"/>
         
                </xsl:call-template>
            </xsl:for-each> 

        </xsl:for-each>
		
    </xsl:template>

    <xsl:template name="Process3">
        <xsl:param name="TableID"/>
        <xsl:param name="FieldName"/>
        <xsl:param name="TableDescription"/>
      
        
        <xsl:if test="./si:TableID/text() = $TableID"> 
         <xsl:element name="FieldEntry">
            <xsl:element  name="FieldName">
                <xsl:value-of select="$FieldName"/>
            </xsl:element>
            <xsl:element name="CodeList_Type">
                <xsl:value-of select="'INTERNAL CODELIST'"/>
            </xsl:element>
            
            <xsl:element name="CodeList_Name">
                <xsl:value-of select="./si:TableID"/>
            </xsl:element>
            <xsl:element name="CodeList_Description">
                <xsl:value-of select="$TableDescription"/>
            </xsl:element>
         </xsl:element>
       </xsl:if> 
    </xsl:template>
	<!-- -->
	
    <xsl:template name="Process2">
        <xsl:param name="VarExplicitRule_upper"/>
        
        <xsl:element name="FieldEntry">
            <xsl:element name="FieldName">
            <xsl:value-of select="./si:Name"/>
            </xsl:element>
	         <xsl:element name="CodeList_Type">
	             <xsl:value-of select="'EXTERNAL CODELIST'"/>
	         </xsl:element>
            <xsl:element name="Codelist_Name">
                <xsl:variable name="var_ExplicitRule" select="substring-before(substring-after(substring-after($VarExplicitRule_upper,'SELECT'),'='),'AND')"/>
                <xsl:value-of select="$var_ExplicitRule"/>
            </xsl:element>
            
            <xsl:element name="Codelist_Values">
	             <xsl:variable name="var_ExplicitRule" select="substring-before(substring-after($VarExplicitRule_upper,'SELECT'),'INTO')"/>
	             <xsl:value-of select="$var_ExplicitRule"/>
	         </xsl:element>
	         
	         <xsl:element name="CodeListExtendedRule">
	             <xsl:value-of select="./si:ExplicitRule"/>
	         </xsl:element>
	         
	     </xsl:element>
        
    </xsl:template>
  
</xsl:stylesheet>
