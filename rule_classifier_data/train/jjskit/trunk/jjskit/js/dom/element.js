var _allowedEvents = {
	blur:'HTMLEvents',
	change:'HTMLEvents',
	click:'MouseEvents',
	dblclick:'MouseEvents',
	error:'HTMLEvents',
	focus:'HTMLEvents',
	keydown:'UIEvents',
	keypress:'UIEvents',
	keyup:'UIEvents',
	load:'HTMLEvents',
	mousedown:'MouseEvents',
	mousemove:'MouseEvents',
	mouseout:'MouseEvents',
	mouseover:'MouseEvents',
	mouseup:'MouseEvents',
	resize:'HTMLEvents',
	select:'HTMLEvents',
	unload:'HTMLEvents'	
};
function _addListener(el,ev,handler,sc) {
	if(!_allowedEvents[ev])return;
	if(typeof handler === 'object') {
		sc = handler.scope || sc; 
		handler = handler.fn;
	}
	if (typeof handler != 'function') {
		throw new J.error("Handler should be a function: "+ev);
	}
	if (sc) {
		handler = handler.wrap(sc);
	}
	_addEventListener(el,ev,handler);
	return handler;
};
function _removeListener(listeners,el,ev,handler) {
	if(!_allowedEvents[ev]||!listeners)return;
	var fns = [];
	for (var i=listeners.length;i--;) {
		if (!handler || listeners[i].fn === handler){
			fns.push(listeners[i].wrapper);
		}
	}
	for (var i=fns.length;i--;) {
		_removeEventListener(el,ev,fns[i]);
	}
};
function element(el) {
	if (el._initialized){
		return el;
	}
	return apply(el, _element_proto);
}
var _element_proto = {
	_initialized: true,
	on:function(event,fn, sc){
		var dom = this;
		var listeners = dom._listeners;
		if (!listeners) {
			listeners = dom._listeners = {};
		}
		if (typeof(event) === 'string') {
			if (!listeners[event])listeners[event]=[];
			listeners[event].push({
				fn:fn,
				wrapper:_addListener(dom, event, fn, sc)
			});
			return;
		}
		if (typeof(event) !== 'object') {
			throw new J.error("Event name should be a string");
		}
		for (var ev in event) {
			if (!listeners[ev])listeners[ev]=[];
			var fn2 = event[ev] || fn;
			listeners[ev].push({
				fn:fn2,
				wrapper:_addListener(dom, ev, fn2, sc)
			});
		}
	},
	un:function(event, fn){
		var dom = this;
		var listeners = dom._listeners;
		if (!listeners) {
			return;
		}
		if (typeof(event) === 'string') {
			_removeListener(listeners[event],dom, event, fn);
			return;
		}
		if (typeof(event) !== 'object') {
			throw new J.error("Event name should be a string");
		}
		
		for (var ev in event) {
			_removeListener(listeners[ev], dom, ev, event[ev]||fn);
		}
	},
	fire:function(ev){
		var type = _allowedEvents[ev];
		if(!type)return;
		_fireEvent(this, type, ev);
	},
	add:function() {
		var target = this;
		var len = arguments.length;
		if (len>2) {
			target = document.createDocumentFragment();
		}
		for (var i = 0;i<len;i++) {
			var other=arguments[i];
			target.appendChild(other);
		}
		if (target!==this) {
			this.appendChild(target);
		}
	},
	find:function(){
		var cn = this.childNodes;
		var rez = [];
		for (var i = cn.length; i--;) {
			var node = element(cn[i]);
			rez.push(node);
			rez.push.apply(rez, node.getAll());
		}
		return rez;
	},
	render:function(parent) {
		if(!parent)parent = document.body;
		parent.appendChild(this);
	},
	show:function(val) {
		if(this.displayed())return;
		this.style.display = val||'';
	},
	hide:function() {
		this.style.display = 'none';
	},
	displayed:function() {
		return this.getStyle('display')!='none';
	},
	getStyle:function(name) {
		var me=this,cstyle = me.currentStyle;
		if (!cstyle) {
			cstyle = e = document.defaultView.getComputedStyle(me, null);
		}
		return name?cstyle[name]:cstyle;
	},
	addCls:function() {
		var itr = this.className||'';
		itr = itr.split(' ');
		for (var i=arguments.length;i-->0;) {
			var cls = arguments[i];
			if (itr.indexOf(cls)<0) {
				itr.push(cls);
			}
		}
		this.className = itr.join(' ');
	},
	removeCls:function() {
		var itr = this.className||'';
		itr = itr.split(' ');
		itr.removeAll(arguments);
		this.className = itr.join(' ');
	},
	hasCls:function(cls) {
		var itr = this.className||'',args = arguments;
		itr = itr.split(' ');
		for (var i=args.length;i--;) {
			if (itr.indexOf(args[i])<0) {
				return false;
			}
		}
		return true;
	},
	remove:function() {
		this.parentNode.removeChild(this);
	}
};