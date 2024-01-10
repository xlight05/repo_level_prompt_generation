function compare(o1, o2){
	if (typeof o1.compareTo === 'function') {
		return o1.compareTo(o2);
	}
	return o1>o2 ? 1 : o1==o2 ? 0 : -1;
};

var getFnCallsFromCode = (function() {
	var tmp = ["break","continue","do","for","new","this","case","default",
	"else","function","in","return","typeof","while","delete",
	"if","switch","var","with","abstract","implements", "instanceOf","false","throws",
	"null","true","catch","throw","try","finally", "undefined"];
	var reserved = {};
	for (var i = tmp.length;i-->0;reserved[tmp[i]]=true);
	var regex = /[^A-Za-z0-9_$]/;
	var fn = function(code,pos,rez) {
		if(isNaN(pos))pos = 0;
		rez = rez||[];
		var i = code.indexOf('(', pos);
		if (i<0) {
			return rez;
		}
		if (i==0) {
			return fn(code,pos+1,rez);
		}
		var start = i-1;
		var end = i;
		if (code.charAt(start)==']') {
			start = code.lastIndexOf('[',i);
		} else {
			while (code.charAt(start)==' '&&start--)end--;
			for (;!regex.test(code.charAt(start))&&start--;);
			start++;
		}
		var id = code.substr(start,end-start);
		if (id&&!reserved[id]) {
			rez.push(id);
		}
		fn(code,i+1,rez);
		return rez;
	};
	return fn;
})();

var version = (function(ver) {
	var fn = function(ver) {
		if (!ver) {
			this.raw = '?';
			return;
		}
		this.raw = ver;
		var ver = /^(\d+)(?:\.(\d+))?(?:\.(\d+))?(?:\.(\d+))?\.?(.*)$/.exec(ver);
		if (!ver||isNaN(ver[1])) {
			return;
		}
		this.major = ver[1]<<0;
		if (ver.length>2&&!isNaN(ver[2])) {
			this.minor = ver[2]<<0;
		}
		if (ver.length>3&&!isNaN(ver[3])) {
			this.build = ver[3]<<0;
		}
		if (ver.length>4&&!isNaN(ver[4])&&ver[4]!='') {
			if (this.raw=='7.2.3.dev') alert(this.raw+':'+ver);
			this.patch = this.build<<0;
			this.build = ver[4]<<0;
		}
		this.minor = this.minor || 0;
		this.patch = this.patch || 0;
		this.build = this.build || 0;
		var mod = ver[ver.length-1];
		if (ver.length<6 && !isNaN(mod)){
			mod = '';
		}
		this.mod = mod;
		var mods = fn.MODS;
		for (var k in mods) {
			if (mods[k].regex.test(mod)) {
				this.stdMod = k;
				break;
			}
		}
		if (!this.stdMod) {
			this.stdMod = 'prod';
		}
		this.isDev = this.stdMod == 'dev';
		this.isAlfa = this.stdMod == 'alfa';
		this.isBeta = this.stdMod == 'beta';
		this.isRc = this.stdMod == 'rc';
		this.isProd = this.stdMod == 'prod';
	};
	fn.MODS = {
		dev:{
			regex:/dev|d|raw/i,
			pos:10
		},
		alfa:{
			regex:/\Wa|^a|alfa|alpha/i,
			pos:20
		},
		beta:{
			regex:/\Wb|^b|b|beta/i,
			pos:30
		},
		rc:{
			regex:/m|rc|milestone|candidate/i,
			pos:40
		},
		prod: {
			regex:/p|prod|release/i,
			pos:100
		}
	};
	fn.prototype = {
		lt:function(other){
			if (!(other instanceof fn)) {
				return this.lt(new fn(other));
			}
			if (isNaN(other.major)) return false;
			if (isNaN(this.major)) return !isNaN(other.major);
			
			return this.major<other.major || (this.major == other.major &&
					(this.minor<other.minor || (this.minor == other.minor &&
					(this.patch<other.patch || (this.patch == other.patch &&
					 (this.build<other.build || (this.build == other.build &&
					fn.MODS[this.stdMod].pos<fn.MODS[other.stdMod].pos)))))));
				
		},
		gt:function(other){
			if (!(other instanceof fn)) {
				return this.gt(new fn(other));
			}
			return !this.eq(other) && !this.lt(other);
		},
		eq:function(other){
			if (!(other instanceof fn)) {
				return this.eq(new fn(other));
			}
			if (isNaN(other.major)||isNaN(this.major)) return isNaN(other.major)&&isNaN(this.major);
			return this.major == other.major &&
				this.minor == other.minor &&
				this.patch == other.patch &&
				this.build == other.build &&
				this.stdMod == other.stdMod;
		},
		toStandard:function(){
			if (isNaN(this.major)) {
				return '?';
			}
			var rez = [this.major];
			rez.push(this.minor);
			rez.push(this.patch);
			rez.push(this.build);
			rez.push(this.mod||this.stdMod);
			return rez.join('.');
		},
		toString:function(){
			return this.raw;
		}
	};
	return fn;
})();
var jjsVersion = '?';
jjsVersion//@Version
;
version.jjs = new version(jjsVersion);

version.toString = function() {
	return version.jjs.toStandard();
};
