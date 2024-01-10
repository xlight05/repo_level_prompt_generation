var printTestResults = (function(){
	function printSummary(rez){
		var msg = [];
		msg.push(rez.total);
		msg.push(" tests have been run. (");
		var cls = 'j-unit-summary';
		if (rez.passed.length == rez.total) {
			msg.push("All passed)");
		} else {
			cls = 'j-unit-summary-error';
			msg.push(rez.passed.length);
			msg.push(" passed");
			if (rez.skiped.length>0) {
				msg.push(", ");
				msg.push(rez.skiped.length);
				msg.push(" skipped");
			}
			if (rez.failed.length>0) {
				msg.push(", ");
				msg.push(rez.failed.length);
				msg.push(" failed");
			}
			msg.push(")");
		}
		return J.div(cls,msg.join(''));
	};
	
	function printFailedTests(cnt, fail) {
		if (!fail||fail.length==0) return;
		var rez = J.div('j-unit-fail');
		rez.add(J.div('j-unit-fail-head','Failed:'));
		for(var i=fail.length;i-->0;){
			mkTestDiv(rez,'j-unit-fail-item',fail[i].name, fail[i].message, fail[i].desc);
		}
		cnt.add(rez);
	};
	
	function printSkipedTests(cnt, skip) {
		if (!skip||skip.length==0) return;
		var rez = J.div('j-unit-skip');
		rez.add(J.div('j-unit-skip-head','Skiped:'));
		for(var i=skip.length;i-->0;){
			mkTestDiv(rez,'j-unit-skip-item',skip[i].name, skip[i].message, skip[i].desc);
		}
		cnt.add(rez);
	};
	
	function mkTestDiv(cnt, cls, html, msg, desc) {
		var tooltip;
		if (msg) {
			html+=': <span>'+msg.ellips(75)+'</span>';
			if (msg.length>75) {
				tooltip = msg;
			}
		}
		var div = J.div({
			tip:tooltip,
			cls:cls,
			html:html
		});
		var descEl = J.div({
			html:desc||'',
			cls:'j-unit-desc'
		});
		descEl.hide();
		var btn = J.div({
			cls:'j-unit-expand',
			visible:!!desc,
			click:function() {
				if(!desc)return;
				if (descEl.displayed()) {
					descEl.hide();
					btn.addCls('j-unit-expand');
					btn.removeCls('j-unit-collapse');
				} else {
					descEl.show();
					btn.removeCls('j-unit-expand');
					btn.addCls('j-unit-collapse');
				}
			}
		});
		cnt.add(btn);
		cnt.add(div);
		cnt.add(descEl);
	};
	
	function printPassedTests(cnt, pass) {
		if (!pass||pass.length==0) return;
		var rez = J.div('j-unit-pass');
		rez.add(J.div('j-unit-pass-head','Passed:'));
		for(var i=pass.length;i-->0;){
			mkTestDiv(rez,'j-unit-pass-item',pass[i].name, pass[i].message, pass[i].desc);
		}
		cnt.add(rez);
	};
	
	var impl = function(rez) {
		if (!rez.total) {
			window.alert('No tests have been found');
			return;
		}
		var win = J.div('j-unit');
		var cnt = J.div('j-unit-content');
		win.add(J.div({
				cls:'j-unit-title',
				html:'Unit tests results:' 
			}),
			J.div({
				cls:'j-unit-close', 
				click: function() {
					win.remove();
				}
			}), cnt);
		cnt.add(printSummary(rez));
		printFailedTests(cnt, rez.failed);
		printSkipedTests(cnt, rez.skiped);
		printPassedTests(cnt, rez.passed);
		win.render();
	};
	return impl;
})();