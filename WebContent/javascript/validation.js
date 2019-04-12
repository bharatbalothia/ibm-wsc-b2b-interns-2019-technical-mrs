//com.ibm.mapping.Validation = function(){
//**** Function to validate the browse file type based on the utility

   var validate = function(ext){
	var utility =document.getElementById("reportType").getAttribute("value");
	
	if(ext != ".zip")
	{
   
	if(utility == "accumulator" || utility == "codeList" || /*utility == "error20001" ||*/ utility == "mrsgenerator" || utility == "downgrade" || utility == "empty_tag_generator" || utility == "mapfunctions"){
		//alert("ext: "+ext);
	   if((ext != ".map") && (ext != ".mxl"))
	    {
		  
   		  alert("Please select .map, .mxl or .zip file");
   		   return false;
	    }
	   else
		  return true;
     }
	else if(utility == "xsdcreate"){
		if(ext != ".xml")
	    {
   		  alert("Please select .xml or .zip file");
   		   return false;
	    }
	   else
		  return true;
	  
	}
	else if(utility == "xmlSplitter"){
		if(ext != ".dat")
	    {
   		  alert("Please select .dat or .zip file");
   		  return false;
	    }
	   else
		  return true;
	  
	}
	else if(utility == "ddfgenerate"){
		if(ext != ".xls" && ext != ".xlsx")
	    {
		  alert("Please select .xls, .xlsx or .zip file");
	   	   return false;
		}
		   else
			  return true;
	}
	else if(utility == "envelope_creator"){
		if(ext != ".xls")
	    {
		  alert("Please select .xls or .zip file");
	   	   return false;
		}
		   else
			  return true;
	}
	else if(utility == "idoctagUpdater"){
		if(ext != ".mxl")
	    {
   		  alert("Please select .mxl or .zip file");
   		  return false;
	    }
	   else
		  return true;	  
	}
	else if(utility == "codelistcreate"){
		if(ext != ".xls" && ext != ".xml")
	    {
		  alert("Please select .xls, .xml or .zip file");
	   	   return false;
		}
		   else
			  return true;
	}
	else
	  {
		alert("Uknown utility");
		return false;
	  }
	}
		
    return true;   
    
   };
  
  // this.validate = validate;
//};