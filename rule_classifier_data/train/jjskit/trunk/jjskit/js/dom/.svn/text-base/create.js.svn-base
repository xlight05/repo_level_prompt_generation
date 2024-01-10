var createElement = function(name, cfg, html){
	var el = document.createElement(name);
	var events = {};
	if (typeof cfg === 'object') {
		for (var k in cfg) {
			if (k=='html'||k=='events'||_allowedEvents[k]){
				continue;
			}
			if (k=='cls') {
				el.className = cfg.cls;
			} else if (k=='tip') {
				if(cfg.tip)el.title = cfg.tip;
			} else if (k=='visible') {
				if (!cfg.visible) el.style.visibility = 'hidden';
			} else {
				el[k] = cfg[k];
			}
		}
		html = cfg.html || html;
		events = cfg.events||cfg;
		
	} else if (typeof cfg === 'function') {
		events.click = cfg;
	} else if (typeof cfg === 'string') {
		el.className = cfg;
	}
	
	if (html||html===false) {
		el.innerHTML = html;
	}
	
	var rez = element(el);
	rez.on(events);
	return rez;
};

function createDiv() {
	var args = ['div'];
	for (var i=arguments.length;i>0;args[i] = arguments[--i]);
	return createElement.apply(this, args);
};