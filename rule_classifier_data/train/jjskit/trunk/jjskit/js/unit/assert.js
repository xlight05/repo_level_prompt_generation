var doFail = function(msg) {
	if (msg) {
		this.info(msg);
	}
	this._fillTraces();
	throw this;
};
J.assert = J.ext(function() {
	var message = '';
	var skiped = false;
	var traces;
	var customTraces;
	
	this.info = function(msg) {
		if (!msg)msg = '(no message)';
		msg = msg.toString().escapeHtml();
		if (message) {
			msg = '<div>'+msg+'</div>';
		}
		message += msg;
	};
	
	this.trace = function(msg) {
		if(!customTraces)customTraces = [msg];
		else customTraces.push(msg);
	};
	
	this.getMessage = function() {
		return message;
	};
	
	this._fillTraces = function(){
		traces = J.stack();
	};
	
	this.getTraces = function() {
		return traces||[];
	};
	
	this.getCustomTraces = function() {
		return customTraces;
	};
	
	this.skip = function(msg) {
		skiped = true;
		doFail.call(this, msg);
	};
	
	this.isSkiped = function() {
		return skiped;
	};
},{
	assertTrue : function(val, msg){
		if (val!==true) doFail.call(this,msg||"Expected true but was '"+val+"'");
	},
	
	assertFalse : function(val, msg) {
		if (val!==false) doFail.call(this,msg||"Expected false but was '"+val+"'");
	},
	
	assert : function(val, msg) {
		if (!val) doFail.call(this,msg||"Expected true, not zero, non-empty string or object but was '"+val+"'");
	},
	
	assertNot : function(val, msg) {
		if (val) doFail.call(this,msg||"Expected 0, null,undefined,false or empty string but was '"+val+"'");
	},
	
	assertEquals : function(expected, actual, msg) {
		if (expected!=actual) doFail.call(this,msg||("Expected '"+expected+"' but was '"+actual+"'"));
	},
	
	assertSame : function(expected, actual, msg) {
		if (expected!==actual) doFail.call(this,msg||"Expected '"+expected+"' but was '"+actual+"'");
	},
	
	assertUndefined : function(val, msg) {
		if (typeof(val)!=='undefined') doFail.call(this,msg||"Expected undefined but was '"+val+"'");
	},
	
	fail : function(msg) {
		doFail.call(this,msg);
	}
});