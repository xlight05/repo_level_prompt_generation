var _addEventListener = null;
if (window.addEventListener) {
	_addEventListener = function(el,event,handler) {
		el.addEventListener(event,handler,false);
	};
} else if (window.attachEvent) {
	_addEventListener = function(el,event,handler) {
		el.attachEvent('on'+event,handler);
	};
} else {
	_addEventListener = function(el,event,handler) {
		var proc = el['on'+event];
		if (!proc) {
			proc = function () {
				var i = proc._handlers.length;
				while(i--) {
					if (proc._handlers[i]()===false){
						return false;
					}
				}
			};
			el['on'+event] = proc;
			proc._handlers = [];
		}
		proc._handlers.unshift(handler);
	};
}
var _removeEventListener = null;
if (window.removeEventListener) {
	_removeEventListener = function(el,event,handler) {
		el.removeEventListener(event, handler, false);
	};
} else if (window.detachEvent) {
	_removeEventListener = function(el,event,handler) {
		el.detachEvent('on'+event, handler);
	};
} else {
	_removeEventListener = function(el,event,handler) {
		var proc = el['on'+event];
		if (!proc) return;
		var handlers = proc._handlers;
		handlers.remove(handler);
		if (handlers.length==0) {
			delete el['on'+event];
		}
	};
}
var _fireEvent = function(el, type, name){
	if (document.createEvent) {
		var ev = document.createEvent(type);
		ev.initEvent(name, true, false);
		el.dispatchEvent(ev);
	} else if (document.createEventObject) {
		el.fireEvent('on'+name);
	} else {
		throw new J.error("Fire event is not supported by your browser");
	}
};