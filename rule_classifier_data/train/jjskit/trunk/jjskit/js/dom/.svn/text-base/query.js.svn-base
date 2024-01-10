var execQuery = (function(){
	var _getElementByClass = function(node, cls){
		if (node.getElementsByClassName) {
			return node.getElementsByClassName(cls);
		}
		if (node === document) {
			node = document.documentElement;
		}
		var cn = node.childNodes,rez = [];
		for (var i = 0,len=cn.length;i<len;i++) {
			var el = element(cn[i]);
			if (el.hasCls(cls)){
				rez.push(el);
			}
			rez.push.apply(rez,_getElementByClass(el,cls));
		}
		return rez;
	};
	var selectorRegex = /^([.#]?[^.#\[\> :]+)/i;
	function select(selector, result, o4single, context) {
		selector = selector.trim();
		var rez = selectorRegex.exec(selector);
		if (!rez) {
			throw new J.error("Wrong selector syntax: "+selector);
		}
		rez = rez[1];
		var type = rez.charAt(0);
		if (type == '#') {
			var node = document.getElementById(rez.substr(1));
			if (context != document) {
				var el = node;
				while(el&&el!==context) el = el.parentNode;
				if(!el) return [];
			}
			if (!node) return [];
			if (result.length>0) {
				if (result.indexOf(node)<0) {
					return [];
				}
				result = [element(node)];
			} else {
				result.push(element(node));
			}
		} else if (type == '.') {
			result = _getElementByClass(context, rez.substr(1));
			if (!result||!result.length) return [];
		} else {
			if (rez == '*') {
				if (context===document) {
					result =  context.all || context.getElementsByTagName('*');
				} else {
					result =  context.find();
				}
				
			} else {
				result = context.getElementsByTagName(rez);
			}
		}
		if (selector.length == rez.length){
			return result||[];
		}
		return subSelect(selector.substr(rez.length), result, o4single, context)||[];
		
	};
	
	function subSelect(selector, result, o4single, context) {
		var del = selector.charAt(0);
		if (del == '.') {
			var cls = [];
			do {
				selector = selector.substr(1);
				if (!selectorRegex.exec(selector)) {
					throw new J.error("Wrong selector syntax: ."+selector);
				}
				cls.push(RegExp.$1);
				selector = selector.substr(RegExp.$1.length);
			} while(selector.length&&selector.charAt(0)=='.');
			
			var rez = [];
			for (var i = 0,len = result.length;i<len;i++) {
				var parent = element(result[i]);
				if (!parent.hasCls.apply(parent,cls))continue;
				if (selector.length) {
					rez.push.apply(rez, select(selector,[],o4single, parent));
				} else {
					rez.push(parent);
				}
			}
			return rez;
		} else if (del == '[') {
			var eq = selector.indexOf('=');
			var close = selector.indexOf(']');
			if (!eq || !close || (eq < 0 && close < 0)) {
				throw new J.error("Wrong selector syntax: "+selector);
			}
			var name = selector.substr(1,(eq<0?close:eq)-1);
			if(name=='class'&&browser.isIE)name='className';
			var val = null;
			if (eq>0) {
				var quot = selector.charAt(++eq);
				if (quot == '"' || quot == "'") {
					var end = selector.indexOf(quot,++eq);
					val = selector.substr(eq,end-eq);
					close = selector.indexOf(']',end);
				} else {
					val = selector.substr(eq,close-eq);
				}
			}
			var rez = [];
			var subsel = selector.substr(close + 1);
			for (var i = 0, len = result.length;i<len;i++) {
				var el = result[i];
				if (el.nodeType!=1)continue;
				if ((val === null && el.hasAttribute(name)) || 
					(val !== null && el.getAttribute(name) == val)) {
					rez.push(el);
				}
			}
			return rez;
		} else if (del==':') {
			selector = selector.substr(1).trim();
			if (selector.startsWith('first-child')) {
				selector = selector.substr(11);
				var rez = [];
				for (var i = 0,len = result.length;i<len;i++) {
					var parent = result[i];
					if (!parent.firstChild)continue;
					parent = parent.firstChild;
					if (selector.length) {
						rez.push.apply(rez, select(selector,[],o4single, parent));
					} else {
						rez.push(parent);
					}
				}
				return rez;
			}
			throw new J.error("Unknown pseudoclass: "+selector);
		} else if (del=='>') {
			selector = selector.substr(1).trim();
			var rez = [];
			for (var i = 0,len = result.length;i<len;i++) {
				var parent = result[i];
				var tmp = select(selector,[],o4single, parent);
				for (var j=0,len2=tmp.length;j<len2;j++) {
					if (tmp[j].parentNode == parent) {
						rez.push(tmp[j]);
					}
				}
			}
			return rez;
		} else if (del==' ') {
			selector = selector.trim();
			var first = selector.charAt(0);
			if (first=='>' || first =='[' || first==':') {
				return subSelect(selector, result, o4single, context);
			}
			var rez = [];
			for (var i = 0,len = result.length;i<len;i++) {
				rez.push.apply(rez, select(selector,[],o4single, result[i]));
			}
			return rez;
		} else {
			throw new J.error("Wrong selector syntax: "+selector);
		}
	}
	
	function doQueryImpl(query,singleResult) {
		if (!query) {
			return singleResult===false?[]:null;
		}
		if (query.nodeType) {
			query = element(query);
			return singleResult===false?[query]:query;
		}
		
		var rez = [], toArray = Array.toArray;
		if (query.indexOf(',')<0) {
			rez.push.apply(rez,toArray(select(query, [], singleResult===true,document)));
		} else {
			query = query.split(',');
			for (var i = 0, len = query.length;i<len;i++) {
				rez.push.apply(rez,toArray(select(query[i], [], singleResult===true,document)));
			}
		}
		rez = rez.unique();
		for (var j = doQueryImpl.count||0;j-->0;doQueryImpl[j] = null) ;
		var len = rez.length;
		if (len>1&&singleResult === true)len=1;
		for (i=len;i--;doQueryImpl[i] = rez[i]);
		doQueryImpl.count = len;
		
		if (singleResult === false) {
			return rez;
		}
		if (singleResult === true || rez.length < 2) {
			return rez.length?rez[0]:null;
		}
		return rez;
	};
	return doQueryImpl;
})();