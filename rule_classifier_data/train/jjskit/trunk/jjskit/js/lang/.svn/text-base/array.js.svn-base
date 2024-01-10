if (!Array.prototype.indexOf) {
	Array.prototype.indexOf = function(val) {
		var i = this.length;
		while(i-->0 && this[i] != val);
		return i;
	};
}
Array.prototype.has = function(val) {
	return Array.prototype.indexOf.call(this, val) >=0 ;
};

Array.prototype.remove = function(val) {
	var rez = [];
	if (arguments.length>1) {
		var i = arguments.length;
		while(i-->0){
			rez.push.apply(rez,this.remove(arguments[i]));
		}
		return rez;
	}
	var i = this.length;
	while(i-->0){
		if (this[i] == val) {
			rez.push(this.splice(i, 1));
		}
	}
	return rez;
};

Array.prototype.removeAll = function(other) {
	this.remove.apply(this,other);
};

Array.prototype.unique = function(cmp) {
	if (typeof cmp !== 'function'){
		return this.unique(function(o1,o2){return o1===o2;});
	}
	var rez = [];
	A: for (var i = 0,len=this.length;i<len;i++) {
		var it = this[i];
		for (var j = rez.length;j--;) {
			if (cmp(rez[j],it)) {
				continue A;
			}
		}
		rez.push(it);
	}
	return rez;
};

Array.toArray = function(ar){
	if (ar instanceof Array) {
		return ar;
	}
	var rez = [];
	for (var i = ar.length;i--;rez[i]=ar[i]) ;
	return rez;
};

function newArray() {
	return [];
};