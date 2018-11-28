function nTabs(thisObj,Num){ 
if(thisObj.className == "active")return; 
var tabObj = thisObj.parentNode.id; 
var tabList = document.getElementById(tabObj).getElementsByTagName("li"); 
for(i=0; i <tabList.length; i++){ 
	if (i == Num){ 
		thisObj.className = "active"; 
		document.getElementById(tabObj+"_Content"+i).style.display = "block"; 
	}else{ 
		tabList[i].className = "normal"; 
		document.getElementById(tabObj+"_Content"+i).style.display = "none"; 
	} 
	} 
} 
  function fc_01(){
      document.getElementById("fc_div01").style.display="block";
  }
  function fc_02(){
      document.getElementById("fc_div01").style.display="none";
  }
  function fc_03(){
      document.getElementById("fc_div02").style.display="block";
  }
  function fc_04(){
      document.getElementById("fc_div02").style.display="none";
  }
  function fc_05(){
      document.getElementById("fc_grzx").style.display="block";
  }
  function fc_06(){
      document.getElementById("fc_grzx").style.display="none";
  }