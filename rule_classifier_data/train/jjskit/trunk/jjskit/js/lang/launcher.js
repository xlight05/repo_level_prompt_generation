var __readyQueue = [];

function ready() {
	// sometimes IE call this before actual ready state :(
	if (!document.body) {
		ready.delay(1);
		return;
	}
	var qu = __readyQueue;
	if (!qu){
		return;
	}
	__readyQueue = null;
	while(qu.length) {
		qu.pop()();
	}
};

function onReady(fn,sc){
	if (typeof(fn) != 'function') {
		throw new J.error("First argument should be a function");
	}
	if (sc) {
		fn = fn.wrap(sc);
	}
	if (!__readyQueue) {
		fn();
	} else {
		__readyQueue.push(fn);
	}
};

function doLaunch(main,args,cp){
	try {
		var baseClassLoader = J.lc('java.lang.ClassLoader');
		var appClassLoader = J.lc('jjc.internal.AppClassLoader');
		var loaderInstance = new appClassLoader(cp);
		baseClassLoader.SYSTEM_LOADER = loaderInstance;
		var fn = loaderInstance['loadClass(Ljava/lang/String;)'](main)['main([Ljava/lang/String;)'];
		if (typeof fn != 'function') {
			throw new J.error('There is no main(String[]) static method on class '+main);
		}
		fn(args);
	} catch (e) {
		// @TODO handle error
		alert('Uncatched error occurs : '+e);
	}
};

function launcher(main) {
	var args = arguments,sc = this;
	onReady(function(){
		doLaunch.apply(sc,args);
	});
}
/**
 * The base structure of this function was taken from jQuery.
 */
(function () {
	if (document.readyState === "complete") {
		// Handle it asynchronously to allow scripts the opportunity to delay ready
		return ready.delay(1);
	}
	var loadHanlder;
	if(document.addEventListener){
		// standard-compatible browsers
		loadHanlder = function() {
			document.removeEventListener("DOMContentLoaded", loadHanlder, false);
			document.removeEventListener("load", loadHanlder, false);
			ready();
		};
		document.addEventListener("DOMContentLoaded", loadHanlder, false);
		window.addEventListener("load", loadHanlder, false);
	} else if (document.attachEvent) {
		// IE and his friends
		loadHanlder = function() {
			if (document.readyState === "complete") {
				document.detachEvent("onreadystatechange", loadHanlder);
				document.detachEvent("onload", ready);
				ready();
			}
		};
		document.attachEvent("onreadystatechange", loadHanlder);
		window.attachEvent("onload", ready);
		var toplevel = false;
		try {
			toplevel = window.frameElement == null;
		} catch(e) {}

		if ( document.documentElement.doScroll && toplevel ) {
			// Use the trick by Diego Perini
			// http://javascript.nwbox.com/IEContentLoaded/
			(function () {
				try {
					document.documentElement.doScroll("left");
				} catch(e) {
					arguments.callee.delay(1);
					return;
				}
				ready();
			})();
		}
	} else {
		return ready.delay(1);
	}
})();