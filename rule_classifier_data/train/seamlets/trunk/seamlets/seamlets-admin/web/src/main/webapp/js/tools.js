function checkApplication() {
	var oldApplication = jQuery(".oldApplication").val();
	var confirmMessage = jQuery(".oldApplicationConfirm").val();
	
	var newApplication = jQuery("div.myinplace").text();
	
	if(oldApplication != newApplication)
		return confirm(confirmMessage);
	
	return true;
}