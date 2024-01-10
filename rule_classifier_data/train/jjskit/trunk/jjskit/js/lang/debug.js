var __dbgLine = -1;
var __lastFn = null;
var __callStack = [];

function debugCall(owner,method) {
	__callStack.push(args?args.callee:null);
	return __callStack.length;
}

function debugReturn() {
	if(__callStack.length) {
		__callStack.pop();
	}
}

function debugCatch(len) {
	var rez = __callStack;
	__callStack = [];
	if (len<0)return;
	for (var i=len;i--;__callStack[i] = rez[i]);
	return rez;
}

function debugLine(ln,args) {
	if (arguments.length == 0) {
		return __dbgLine;
	}
	__dbgLine = ln;
	__lastFn = args?args.callee:null;
};

/**
 * Return the array of all object properties.
 * May be slow for large objects if full scan is performed.
 */
function dumpObject(obj, full, rez, values, prefix) {
	rez = rez||[];
	values  = values||[];
	prefix = prefix||'';
	if (!obj || values.has(obj))return rez;
	values.push(obj);
	for(var k in obj) {
		if (typeof obj[k] === 'function') {
			k+='('+obj[k].length+' args)';
		}
		rez.push(prefix+k+' => '+obj[k]);
		if (full && typeof obj[k] === 'object') {
			dumpObject(obj[k], true, rez, values, prefix+k+'.');
		}
	}
	return rez;
};