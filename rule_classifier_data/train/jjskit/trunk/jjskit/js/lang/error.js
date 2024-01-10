var jError = extend(function(e){
	if (typeof(e)==='string') {
		this.stack = getStack();
		this.message = e;
		return;
	}
	this.stack = getStack(e);
	this.message = e.message;
	if(e.description&&e.description!=e.message) {
		this.message+=e.description;
	}
}, {
	toString:function(){
		return this.message + ' \n\tat '+(this.stack||[]).join('\n\tat ');
	}
});

var ANON = '{anonymous}';
var stackTraceElement = extend(function(fn,args,file,ln,code){
	this.name = fn; 
	this.args = args;
	this.file = file; 
	this.line = ln;
	this.code = code;
});
stackTraceElement.prototype.toString = function() {
	var rez = [this.name||ANON,this.args||'(...)'];
	if (this.file) {
		rez.push(' in ');
		rez.push(this.file);
	}
	if (this.line) {
		rez.push(' at line ');
		rez.push(this.line);
	} 
	if (this.code) {
		rez.push(' \/\/ ');
		rez.push(this.code);
	} 
	return rez.join('');
};

function getStack(e) {
	if (e) {
		return fillStack(e,true);
	}
	return fillStack(makeError(),false); 
}; 

function makeError(){
	try {
		undef000();// something undefined
	} catch(e) {
		return e;
	}
};

function fillStack(e,nativeOnly) {
	if (window.opera && e.message) { //Opera
    	if(e.stacktrace&&!e.stacktrace.startsWith('n/a')) {
    		// Opera 10 and greater
    		return fillStackOpera10(e, !nativeOnly);
    	}
    	return fillStackOpera(e, !nativeOnly);
    }
    else if (e.stack) { //Firefox or Chrome
    	if (typeof(e.stack) !=='string') {
    		return e.stack;
    	}
		if (e['arguments']) {
			// Chrome
			return fillStackChrome(e, !nativeOnly);
		}
		return fillStackFF(e, !nativeOnly);
	}
	// Safari, IE
	if (!nativeOnly && getStack.caller) {
		return fillStackOther(null, getStack.caller);		
	}
	// Not native and early Chrome
	if (typeof __lastFn === 'function') {
		var rez = fillStackOther(__lastFn);
		rez.unshift(createStackTraceElementFromFn(__lastFn,window,'',__dbgLine));
		rez.unshift("Generated trace (J.ln):");
		return rez;
	}
	return ['Native stack is not available for '+browser.name+
		  ' browser. Consider useing J.ln function to provide additional stack info'];
}

function fillStackChrome(e, skipGetStack) {
	var stack = e.stack.split('\n'), rez = [],
		lineRe = /^\s*at\s+(?:Object.|\[object Object\].)?([a-zA-Z0-9_$]*)[^\(]*\((.*):(\d+):\d+\)\s*$/i,
		anonimousRe = /^\s*at\s+(.*):(\d+):\d+\s*$/i;
	for (var i = stack.length;i--;) {
		var parts = lineRe.exec(stack[i]);
		if (parts) {
			var fn = parts[1];
			if (skipGetStack && (fn=='getStack' || fn=='J.stack')) {
				return rez;
			}
			rez.unshift(new stackTraceElement(fn, '(...)', parts[2], parts[3]));
			continue;
		}
		parts = anonimousRe.exec(stack[i]);
		if (parts) {
			rez.unshift(new stackTraceElement(null, '(...)', parts[1], parts[2]));
		}
	}
	return rez;
};

function fillStackFF(e, skipGetStack){
	var stack = e.stack.replace(/(?:\n@:0)?\s+$/m, '').split('\n');
	var rez = [];
	while(skipGetStack && stack.length>0 && 
		!stack.shift().startsWith('getStack'));
	for (var i = stack.length;i-->0;) {
		var el = stack[i];
		var argsStart = el.indexOf('(');
		var fileStart = el.lastIndexOf('@');
		var lineStart = el.lastIndexOf(':');
		rez[i] = new stackTraceElement(el.substr(0,argsStart),
				el.substr(argsStart,fileStart++-argsStart),
				fileStart>-1?el.substr(fileStart,lineStart-fileStart):'',
				lineStart>-1?el.substr(lineStart+1):'');
	} 
	return rez;
};

function fillStackOpera10(e, skipGetStack) {
	var stack = e.stacktrace, lines = stack.split('\n'),rez = [], 
    	lineRE = /.*line (\d+), column \d+ in (?:(?:<anonymous function\:?\s*(\S+)>)|([^\(]+))(\([^\)]*\))(?: in )?(.*)\s*:$/i;
    for (var i = 0, j = 0, len = lines.length; i < len - 2; i++) {
    	var parts = lineRE.exec(lines[i]);
        if (parts) {
        	var fn = parts[2]||parts[3];
        	if (skipGetStack) {
        		i++;
        		skipGetStack = fn!='getStack'&&fn!='J.stack';
        		continue;
        	}
            rez.push(new stackTraceElement(fn, parts[4], parts[5], parts[1], lines[++i]));
        }
    }
    return rez;
};

function fillStackOpera(e,skipGetStack) {
	var rez = [];
	var lines = e.message.split('\n'), 
	lineRE = /Line\s+(\d+).*script\s+(?:(http\S+)\:\s*)?(?:.*in\s+function\s+(\S+))?/i;
    for (var i = 0, len = lines.length; i < len; i++) {
    	var line = lineRE.exec(lines[i]);
    	if (!line) {
    		continue;
    	}
    	var code = lines[++i].replace(/^\s+/, '');
    	if(skipGetStack && code.indexOf('getStack')<0 && code.indexOf('J.stack') < 0){
    		continue;
    	}
    	skipGetStack = false;
    	var fn = line[3];
    	var file = line[2];
    	var line = line[1];
    	if (rez.length&&!rez[rez.length-1].name) {
    		var calls = getFnCallsFromCode(code);
    		if (calls.length==1) {
    			rez[rez.length-1].name = calls[0];
    		}
    	}
        rez.push(new stackTraceElement(fn, '(...)', file, line, code));
    }
    return rez;
};

function fillStackOther(e, curr, helper) {
	var stack = [], maxStackSize = 25, helper = helper||window;
    while (curr && curr.caller && curr.caller!=curr && maxStackSize-->0) {
    	curr = curr.caller;
        stack.push(createStackTraceElementFromFn(curr,helper));
    }
    if (e && e.sourceURL && stack.length>0) {
    	stack[0] += ' ('+e.sourceURL+' line '+e.line+')';
    }
    return stack;
};
var __fnRE = /function\s*([\w\-$]+)?\s*(\([^\)]*\))/i;
function createStackTraceElementFromFn(fn,helper,file,line){
	var name = __fnRE.test(fn.toString())&&RegExp.$1, args;
    if (!name) {
    	name = findInObject(fn,helper);
    }
    if (fn['arguments']) {
    	args = '(' + stringifyArguments(fn['arguments']) + ')';
    } else {
    	args = RegExp.$2;
    }
    if (!helper.caller && name == 'anonymous') {
    	name = '';
    }
    return new stackTraceElement(name, args, file, line);
};

function findInObject(obj,heap,processed,prefix) {
	processed = processed||[];
	prefix =  prefix||'';
	if (processed.indexOf(heap)>-1){
		return null;
	}
	processed.push(heap);
	for(var k in heap) {
		if (heap[k]===obj) {
			return prefix+k;
		}
		if(typeof heap[k] === 'object') {
			try {
				var rez = findInObject(obj,heap[k],processed, prefix+k+'.');
				if (rez) return rez;
			} catch(e){}// Ignore IE bug with some window properties 
		}
	}
	return null;
};

function stringifyArguments(args) {
    var slice = Array.prototype.slice;
    if (!(args instanceof Array)) {
    	args = slice.call(args);
    }
    for (var i = 0; i < args.length; ++i) {
        var arg = args[i];
        if (arg === undefined) {
            args[i] = 'undefined';
        } else if (arg === null) {
            args[i] = 'null';
        } else if (arg.constructor) {
            if (arg.constructor === Array) {
                if (arg.length < 3) {
                    args[i] = '[' + stringifyArguments(arg) + ']';
                } else {
                    args[i] = '[' + stringifyArguments(slice.call(arg, 0, 1)) + '...' + stringifyArguments(slice.call(arg, -1)) + ']';
                }
            } else if (arg.constructor === Object) {
                args[i] = '#object';
            } else if (arg.constructor === Function) {
                args[i] = '#function';
            } else if (arg.constructor === String) {
                args[i] = '"' + arg.ellips(8) + '"';
            }
        }
    }
    return args.join(',');
}