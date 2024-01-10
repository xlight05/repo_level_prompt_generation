Function.prototype.wrap = function(sc) {
	var fn = this;
	var rez = function(){
		return fn.apply(sc||this, arguments);
	};
	return rez;
};

Function.prototype.delay = function(time,sc,args) {
	var fn = this,sc=sc||window,args = args||[];
	return setTimeout(function(){
		fn.apply(sc, args);
	}, time);
};