if(typeof String.prototype.trim !== 'function') {
	String.prototype.trim = function() {
	  return this.replace(/^\s+|\s+$/g, ''); 
	};
}
String.prototype.escapeHtml = function() {
	return this.replace(/&/g,'&amp;').                                         
    	replace(/>/g,'&gt;').                                           
    	replace(/</g,'&lt;');
};
String.prototype.ellips = function(from) {
	if (isNaN(from)||from<4)return;
	return this.length>from?this.substring(0,from-3)+'...':this;
};
String.prototype.startsWith = function(str) {
	return this.indexOf(str)===0;
};