(function(window, document, undefined) {
	var _J = undefined;
	if (typeof window.J !== 'undefined') {
		_J = window.J;// map old main point
	}
	// @Include lang/loadClass.js
	// @Include lang/extend.js
	// @Include lang/instanceOf.js
	// @Include lang/debug.js
	// @Include lang/array.js
	// @Include lang/string.js
	// @Include lang/function.js
	// @Include lang/utils.js
	// @Include lang/error.js
	// @Include lang/launcher.js
	// @Include bom/browser.js
	// @Include bom/browserEvents.js
	// @Include dom/element.js
	// @Include dom/query.js
	// @Include dom/create.js
	 
	window.J = apply(execQuery, {
		browser: browser,
		
		apply: apply,
		
		ext:extend,
		
		is: isInstanceOf,
		
		lc:loadClass,
		
		ln:debugLine,
		
		run: launcher,
		
		onReady: onReady,
		
		dump: dumpObject,
		
		cmp:compare,
		
		array: newArray,
		
		cast: checkCast,
		
		stack: getStack,
		
		error:jError,
		// steal form jquery source (noConflict):
		// jquery.com
		safe: function() {
			if (_J !== undefined) {
				var tmp = window.J;
				window.J = _J;
				return tmp;
			}
		},
		
		version: version,
		
		div: createDiv,
		
		el: createElement
	});
})(window, document);

J.run('java.lang.MyMainClass', [], ['http://localhost:8080/index.js', 'http://localhost:8080/ss.js']); 
