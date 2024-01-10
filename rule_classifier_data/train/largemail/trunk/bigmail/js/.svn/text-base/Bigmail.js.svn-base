
/**
 * 
 */
 var ProgressBarWindow = null;
function OpenProgressBarWindow() {
	
    if ( ProgressBarWindow!=null )
        ProgressBarWindow.close();
	/* full screen =7 ; hide a close button of window ; valid for only IE.
	 *You cannot disbable that button with client side javascript. The user 
	 *remains in control of the window.
	 */
	 //alert("Notice: Click a close button when leaving a current window!");
	var resetFlg = document.getElementById("resetFlg").value;
	// alert(resetFlg);
	
    ProgressBarWindow = window.open( "/bigmail/progress_view.do?resetFlg="+resetFlg,"ProgressBarWindow",
    "width=625,height=400,scrollbars=no,status=no resizable=0 " );
    ProgressBarWindow.focus();
	
} // end function

/**
 * 
 */
function CloseProgressBarWindow() {
	// alert(ProgressBarWindow);
	if ( ProgressBarWindow !=null )
        ProgressBarWindow.close();
    //ProgressBarWindow.document.write('<H1>The Popup Window</H1>');

} // end function



function ConfirmFileCheck( childWin ) {

    openerForms = opener.document.forms;
	
    if ( openerForms.length == 0 )
        window.close();

    formIndex = -1;
    fileIndex = -1;
    sizeIndex = -1;
    uploadIndex = -1;
    totalSize = 0;
    flag = false;
	//openerForms.length='1'
	
    for ( i = 0; i < openerForms.length; i++ ) {

        openerForm = openerForms[ i ];
		
        formIndex = i;
        
		//openerForm.elements.length=10
       for ( j = 0; j < openerForm.elements.length; j++ ) {

            element = openerForm.elements[ j ];
			
			if ( element.name == "attachedFile" ) {

                fileIndex = j;
                
            } // end if
            
            if ( element.name =="totalSize"){
            	sizeIndex = j;
            	
            }
			
        } // end for j
		
        
   } // end for i
		
   for( i=0; i <childWin.elements.length; i++){
   		element = childWin.elements[i];
   		
   		if(element.name=="totalSize"){
   			
   			totalSize = convertSize(element.value);
   		
   		}
   		if(element.name=="attachedFile"){
   			uploadIndex = i;
   			   			
   		}
   		
   	}//end for
   		
   	 
	
    if ( formIndex > -1 && fileIndex > -1 && uploadIndex > -1 && sizeIndex >-1) {
		
        openerForms[formIndex].elements[fileIndex].value = childWin.elements[uploadIndex].value;
		openerForms[formIndex].elements[sizeIndex].value = totalSize;
      /* if ( openerForms[ formIndex ].elements.length > fileIndex )
        openerForms[formIndex].elements[fileIndex+1].focus();*/

  } // end if
	return true;
    //window.close();

} // end function

function initUploadForm(form){
	
	ConfirmFileCheck(form);
	showCheckbox(form);
	
}

//check file form field
function showSubmitButton(){
	document.getElementById('uploadButton').innerHTML="<table align='center'><tr><td align='center'><input type='hidden' name='orgFileName' /><html:submit property='action' styleClass='myForm' ><bean:message key='button.send' /></html:submit>&nbsp;</td></tr></table>";

}
//Add upload form field 
var click = 0;
function addFileForm(){
	
	if(click==0 && document.all['fileList1'].style.display=="none"){
		click++;
		document.all['fileList1'].style.display="block";
	}else if( click==1 && document.all['fileList2'].style.display=="none"){
		click++;
		document.all['fileList2'].style.display="block";
	}else if(click==2 && document.all['fileList3'].style.display=="none"){
		click++;
		document.all['fileList3'].style.display="block";
		document.all['addFileButton'].style.display="none";
	}else{
		//click==3 , document.all['fileList1'].style.display="none"
		click=0;
		document.all['fileList1'].sytle.display="block";
	}

	return true;
}
// select and unselect all checkboxes.
function set(flag){

	var element = document.getElementById("FileUploadForm");
	
	for (i=0; i<(element.length-3); i++){
		element[i].checked=flag;
	}
}
// toggle checkbox selection
function toggle(){

	var element = document.getElementById("FileUploadForm");
	
	for (i=0; i<(element.length-3); i++){
		
		if(element[i].checked==1){
		  element[i].checked=0;
		}else{
		  element[i].checked=1;
		}
	}
}
// return total number of upload files
function getNumberOfFiles(){
	var totalFiles=0;
	totalFiles = click + 1;
	return totalFiles;

}
// change seconds with 00:00:00
function convertSeconds(seconds)
{
	hours = parseInt(seconds/3600);
	if(hours ==0){
		mins = parseInt(seconds/60);
	}else{
		mins = parseInt((seconds-hours*3600)/60);
	}
	
		secs  = seconds - hours*3600 - mins*60;
	
	if(hours<10){ hours = '0'+hours; }
	if(mins<10){ mins  = '0'+mins; }
	if(secs<10){ secs  = '0'+secs; }
	return hours+':'+mins+':'+secs;
}

// add a suffix to file size
function convertSize(bytesize){
	var result="";
         
	if(bytesize>1024 && bytesize< 1024*1024){
		result = new Number((parseFloat(bytesize/1024))).toFixed(2)+" Kbytes";
	}else if(bytesize>1024*1024){
		result = new Number(parseFloat(bytesize/(1024*1024))).toFixed(2)+" Mbytes";
	
	}else{
		result = bytesize + " bytes";
	
	}
         return result;
}

//Insert comma		
function in_comma(str) {
    uncomm_str = String(str);
    comm_str = "";
		
    loop_j = uncomm_str.length - 3;
	
	for(j=loop_j; j>=1 ; j=j-3)
	{
		comm_str=","+uncomm_str.substring(j,j+3)+comm_str;
    }		 
	comm_str = uncomm_str.substring(0,j+3)+comm_str;
    
    	return comm_str;
}

// round a number in fixed point
function setFixedPoint(number, point){
	var result= new Number(number).toFixed(point);
	
	return result;

}

// show select-checkBoxes buttons(onload)
function showCheckbox(form){
	
	for(i=0; i<form.elements.length; i++){
	
	element = form.elements[i];
	
		if(element.name=="fileIndex[0]"){
			document.all['checkbox'].style.display="block";
			break;
		}else{
			document.all['checkbox'].style.display="none";
		}
	
	}
	
}

function showFrame(){
	if(document.all['resultFrame'].style.display=="none")
		document.all['resultFrame'].style.display="block";

}

function getFileName(form){
	var orgFileName="";
	var delim="|";
	for(i=0; i<4 ; i++){
		
		if(document.all['fileList'+i].style.display=="block"){
			
			if(form.elements[i+2].name=="fileList["+i+"]"){
				orgFileName+=trimPath(form.elements[i+2].value)+delim;
			}
		}//end if
	}
	//alert(orgFileName);
	form.orgFileName.value= orgFileName;
	return true;
}

// get a file name of selected fileindex
function getNameOfFileIndex(fileIndex){
	var fileName="";
	form = document.getElementById("FileUploadForm");
	
	for(i=0; i<form.elements.length; i++){
		element = form.elements[i];
		if(element.name=="fileList["+fileIndex+"]"){
			fileName = trimPath(element.value);
			break;
		}
	}
	return fileName;
}


//return file's name except path info
function trimPath(filename){
	//windows
	index = filename.lastIndexOf("\\");
	//linux
	if(index==-1){
		index = filename.lastIndexOf("/");
	}
	return filename.substring(index+1);
}

// check the number of upload files
function checkNumberOfUpload(){
	var number=0;

	elements = document.getElementById("FileUploadForm");
	
	for(i=0; i<elements.length; i++){
		element = elements[i];
		//check attached files
		for(j=0; j<4; j++){
			fileIndex = "fileIndex["+j+"]";
			formFile = "fileList["+j+"]";
			if(element.name==fileIndex)
				number++;
			//check multipart-file form
			if(element.name==formFile){
				if(element.value!="")
					number++;
			}
		}//end for
		
	}//end for
	//error handling when the number of upload files is over 4.
	if(number>4){
		alert("Max allowable number of upload files is exceeded.");
		window.close();
	}
		
}

function setResetFlg() {
	
	document.getElementById("resetFlg").value = "true";
	
}
